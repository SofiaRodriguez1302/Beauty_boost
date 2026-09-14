package Controlador;

import Conexion.Conexion;
import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Acceso a datos del catálogo, incluyendo la URL de imagen persistida en MySQL. */
public class ProductoDAO {

    private final Conexion conect = new Conexion();

    public Producto consultarProducto(int id) {
        String sql = "SELECT id_producto, nombre_prod, descripcion_prod, precio, stock, categoria_id_categoria, imagen_url "
                + "FROM producto WHERE id_producto = ?";
        try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearProducto(rs);
            }
        } catch (SQLException e) {
            // Compatibilidad con instalaciones antiguas que aún no tienen imagen_url.
            String legacy = "SELECT id_producto, nombre_prod, descripcion_prod, precio, stock, categoria_id_categoria FROM producto WHERE id_producto = ?";
            try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(legacy)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Producto p = new Producto();
                        p.setIdProducto(rs.getInt("id_producto"));
                        p.setNombreProd(rs.getString("nombre_prod"));
                        p.setDescripcionProd(rs.getString("descripcion_prod"));
                        p.setPrecio(rs.getDouble("precio"));
                        p.setStock(rs.getInt("stock"));
                        p.setCategoriaIdCategoria(rs.getInt("categoria_id_categoria"));
                        return p;
                    }
                }
            } catch (SQLException legacyError) {
                System.out.println("Error consultando producto " + id + ": " + legacyError.getMessage());
            }
        }
        return null;
    }

    public boolean insertarProducto(Producto prod) {
        String sql = "INSERT INTO producto (nombre_prod, descripcion_prod, precio, stock, categoria_id_categoria, estado_producto, imagen_url) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prod.getNombreProd());
            ps.setString(2, prod.getDescripcionProd());
            ps.setDouble(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            ps.setInt(5, prod.getCategoriaIdCategoria());
            ps.setString(6, "Activo");
            ps.setString(7, normalizarImagen(prod.getImagen()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Permite que el proyecto siga funcionando antes de aplicar la migración imagen_url.
            String legacy = "INSERT INTO producto (nombre_prod, descripcion_prod, precio, stock, categoria_id_categoria) VALUES (?,?,?,?,?)";
            try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(legacy)) {
                ps.setString(1, prod.getNombreProd());
                ps.setString(2, prod.getDescripcionProd());
                ps.setDouble(3, prod.getPrecio());
                ps.setInt(4, prod.getStock());
                ps.setInt(5, prod.getCategoriaIdCategoria());
                boolean inserted = ps.executeUpdate() > 0;
                if (inserted) {
                    // Si la BD ya tiene estado_producto pero aún no tiene imagen_url,
                    // garantizamos que un producto nuevo quede visible en el catálogo.
                    try (PreparedStatement estado = conn.prepareStatement(
                            "UPDATE producto SET estado_producto = 'Activo' WHERE id_producto = LAST_INSERT_ID()")) {
                        estado.executeUpdate();
                    } catch (SQLException ignored) {
                        // Algunas BD antiguas no tienen estado_producto; el producto queda creado.
                    }
                }
                return inserted;
            } catch (SQLException legacyError) {
                System.out.println("Error insertando producto: " + legacyError.getMessage());
                return false;
            }
        }
    }

    public boolean actualizarProducto(Producto prod) {
        String sql = "UPDATE producto SET nombre_prod = ?, descripcion_prod = ?, precio = ?, stock = ?, categoria_id_categoria = ?, estado_producto = ?, imagen_url = ? WHERE id_producto = ?";
        try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, prod.getNombreProd());
            ps.setString(2, prod.getDescripcionProd());
            ps.setDouble(3, prod.getPrecio());
            ps.setInt(4, prod.getStock());
            ps.setInt(5, prod.getCategoriaIdCategoria());
            ps.setString(6, "Activo");
            ps.setString(7, normalizarImagen(prod.getImagen()));
            ps.setInt(8, prod.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            String legacy = "UPDATE producto SET nombre_prod = ?, descripcion_prod = ?, precio = ?, stock = ?, categoria_id_categoria = ? WHERE id_producto = ?";
            try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(legacy)) {
                ps.setString(1, prod.getNombreProd());
                ps.setString(2, prod.getDescripcionProd());
                ps.setDouble(3, prod.getPrecio());
                ps.setInt(4, prod.getStock());
                ps.setInt(5, prod.getCategoriaIdCategoria());
                ps.setInt(6, prod.getIdProducto());
                return ps.executeUpdate() > 0;
            } catch (SQLException legacyError) {
                System.out.println("Error actualizando producto: " + legacyError.getMessage());
                return false;
            }
        }
    }

    public boolean actualizarImagen(int idProducto, String imagenUrl) {
        String sql = "UPDATE producto SET imagen_url = ? WHERE id_producto = ?";
        try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, normalizarImagen(imagenUrl));
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizando imagen del producto: " + e.getMessage());
            return false;
        }
    }

    public boolean inactivarProducto(int id) {
        return cambiarEstado(id, "Inactivo");
    }

    public boolean activarProducto(int id) {
        return cambiarEstado(id, "Activo");
    }

    private boolean cambiarEstado(int id, String estado) {
        String sql = "UPDATE producto SET estado_producto = ? WHERE id_producto = ?";
        try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizando estado de producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProducto(int id) {
        try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement("DELETE FROM producto WHERE id_producto = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("No se pudo eliminar producto #" + id + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos los productos activos. Este método es la fuente completa del catálogo;
     * nunca se sobrescribe en sesión con un subconjunto.
     */
    public java.util.List<Producto> listarProductosActivos() {
        return listarProductosActivos(null, null);
    }

    /** Lista productos activos con filtros opcionales de categoría y texto. */
    public java.util.List<Producto> listarProductosActivos(Integer categoriaId) {
        return listarProductosActivos(categoriaId, null);
    }

    public java.util.List<Producto> listarProductosActivos(Integer categoriaId, String busqueda) {
        java.util.List<Producto> lista = new java.util.ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.id_producto, p.nombre_prod, p.descripcion_prod, p.precio, p.stock, "
                + "p.categoria_id_categoria, p.estado_producto, p.imagen_url "
                + "FROM producto p "
                + "WHERE (p.estado_producto IS NULL OR LOWER(TRIM(p.estado_producto)) = 'activo') "
        );
        java.util.List<Object> parametros = new java.util.ArrayList<>();

        if (categoriaId != null && categoriaId > 0) {
            sql.append("AND p.categoria_id_categoria = ? " );
            parametros.add(categoriaId);
        }
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            sql.append("AND (LOWER(p.nombre_prod) LIKE ? OR LOWER(p.descripcion_prod) LIKE ?) " );
            String like = "%" + busqueda.trim().toLowerCase(java.util.Locale.ROOT) + "%";
            parametros.add(like);
            parametros.add(like);
        }
        sql.append("ORDER BY p.id_producto DESC");

        try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) ps.setObject(i + 1, parametros.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            // Compatibilidad con BD antiguas que aún no tienen estado_producto/imagen_url.
            StringBuilder legacy = new StringBuilder(
                    "SELECT id_producto, nombre_prod, descripcion_prod, precio, stock, categoria_id_categoria "
                    + "FROM producto WHERE 1=1 "
            );
            java.util.List<Object> legacyParams = new java.util.ArrayList<>();
            if (categoriaId != null && categoriaId > 0) {
                legacy.append("AND categoria_id_categoria = ? " );
                legacyParams.add(categoriaId);
            }
            if (busqueda != null && !busqueda.trim().isEmpty()) {
                legacy.append("AND (LOWER(nombre_prod) LIKE ? OR LOWER(descripcion_prod) LIKE ?) " );
                String like = "%" + busqueda.trim().toLowerCase(java.util.Locale.ROOT) + "%";
                legacyParams.add(like); legacyParams.add(like);
            }
            legacy.append("ORDER BY id_producto DESC");
            try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(legacy.toString())) {
                for (int i = 0; i < legacyParams.size(); i++) ps.setObject(i + 1, legacyParams.get(i));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Producto p = new Producto();
                        p.setIdProducto(rs.getInt("id_producto"));
                        p.setNombreProd(rs.getString("nombre_prod"));
                        p.setDescripcionProd(rs.getString("descripcion_prod"));
                        p.setPrecio(rs.getDouble("precio"));
                        p.setStock(rs.getInt("stock"));
                        p.setCategoriaIdCategoria(rs.getInt("categoria_id_categoria"));
                        p.setEstadoProducto("Activo");
                        p.setImagen("img/default.jpg");
                        lista.add(p);
                    }
                }
            } catch (SQLException legacyError) {
                System.out.println("Error listando productos: " + legacyError.getMessage());
            }
        }
        return lista;
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt("id_producto"));
        p.setNombreProd(rs.getString("nombre_prod"));
        p.setDescripcionProd(rs.getString("descripcion_prod"));
        p.setPrecio(rs.getDouble("precio"));
        p.setStock(rs.getInt("stock"));
        p.setCategoriaIdCategoria(rs.getInt("categoria_id_categoria"));
        p.setImagen(normalizarImagen(rs.getString("imagen_url")));
        try { p.setEstadoProducto(rs.getString("estado_producto")); } catch (SQLException ignored) { p.setEstadoProducto("Activo"); }
        return p;
    }

    private String normalizarImagen(String imagen) {
        if (imagen == null) return "img/default.jpg";
        String value = imagen.trim().replace('\\', '/');
        if (value.isEmpty()) return "img/default.jpg";
        if (value.matches("(?i)^https?://.*")) return value;
        value = value.replaceFirst("^(?i)Vista/img/", "");
        value = value.replaceFirst("^(?i)web/img/", "");
        value = value.replaceFirst("^(?i)img/", "");
        value = value.replaceFirst("^/+", "");
        return "img/" + value;
    }
}
