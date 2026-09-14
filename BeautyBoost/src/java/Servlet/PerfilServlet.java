package Servlet;

import Conexion.Conexion;
import Controlador.CiudadesDAO;
import Controlador.DireccionEnvioDAO;
import Controlador.PedidoDAO;
import Controlador.ProductoDAO;
import Controlador.FavoritosDAO;
import Modelo.DireccionEnvio;
import Modelo.Pedido;
import Modelo.Producto;
import Modelo.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "PerfilServlet", urlPatterns = {"/PerfilServlet"})
public class PerfilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            response.sendRedirect(request.getContextPath() + "/Vista/Login.jsp");
            return;
        }

        cargarDatosPerfil(request, usuarioLogueado);

        // Redirigir a la vista del perfil
        request.getRequestDispatcher("/Vista/perfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            response.sendRedirect(request.getContextPath() + "/Vista/Login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("toggleFavorito".equals(action)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            try {
                int productoId = Integer.parseInt(request.getParameter("productoId"));
                FavoritosDAO favoritosDAO = new FavoritosDAO();
                boolean favorito;
                if (favoritosDAO.existe(usuarioLogueado.getIdUsuario(), productoId)) {
                    favorito = false;
                    if (!favoritosDAO.eliminar(usuarioLogueado.getIdUsuario(), productoId)) {
                        throw new IllegalStateException("No se pudo eliminar el favorito");
                    }
                } else {
                    favorito = true;
                    if (!favoritosDAO.agregar(usuarioLogueado.getIdUsuario(), productoId)) {
                        throw new IllegalStateException("No se pudo guardar el favorito");
                    }
                }
                ArrayList<Producto> lista = favoritosDAO.obtenerPorUsuario(usuarioLogueado.getIdUsuario());
                Set<Integer> ids = new LinkedHashSet<>();
                for (Producto producto : lista) ids.add(producto.getIdProducto());
                session.setAttribute("favoritos", ids);
                response.getWriter().write("{\"ok\":true,\"favorito\":" + favorito + ",\"total\":" + lista.size() + "}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"ok\":false,\"message\":\"No fue posible actualizar el favorito\"}");
            }
            return;
        }

        if ("actualizarAutorizacion".equals(action)) {
            String autorizacion = "SI".equalsIgnoreCase(request.getParameter("autorizacionDatos")) ? "SI" : "NO";
            try {
                Controlador.UsuarioDAO usuarioDAO = new Controlador.UsuarioDAO();
                if (usuarioDAO.actualizarAutorizacionDatos(usuarioLogueado.getIdUsuario(), autorizacion)) {
                    usuarioLogueado.setAutorizacionDatos(autorizacion);
                    session.setAttribute("usuarioLogueado", usuarioLogueado);
                    session.setAttribute("mensajeExito", "Autorización de datos actualizada correctamente.");
                } else {
                    session.setAttribute("mensajeError", "No fue posible actualizar la autorización de datos.");
                }
            } catch (Exception e) {
                session.setAttribute("mensajeError", "No fue posible actualizar la autorización de datos.");
            }
            response.sendRedirect(request.getContextPath() + "/PerfilServlet");
            return;
        }

        if ("agregarDireccion".equals(action)) {
            String direccionEnvioTexto = request.getParameter("direccion_envio");
            String idCiudadStr = request.getParameter("id_ciudad");

            if (direccionEnvioTexto != null && !direccionEnvioTexto.trim().isEmpty() && idCiudadStr != null) {
                try {
                    int idCiudad = Integer.parseInt(idCiudadStr);
                    
                    DireccionEnvio nuevaDir = new DireccionEnvio();
                    nuevaDir.setDireccionEnvio(direccionEnvioTexto);
                    nuevaDir.setUsuarioIdUsuario(usuarioLogueado.getIdUsuario());
                    nuevaDir.setCiudadesIdCiudades(idCiudad);

                    DireccionEnvioDAO direccionDAO = new DireccionEnvioDAO();
                    boolean guardado = direccionDAO.insertarDireccion(nuevaDir);
                    
                    if (guardado) {
                        session.setAttribute("mensajeExito", "¡Dirección guardada exitosamente!");
                    } else {
                        session.setAttribute("mensajeError", "No se pudo guardar la dirección en la base de datos.");
                    }
                } catch (NumberFormatException e) {
                    session.setAttribute("mensajeError", "Ciudad seleccionada inválida.");
                }
            } else {
                session.setAttribute("mensajeError", "Por favor completa todos los campos de la dirección.");
            }
            response.sendRedirect(request.getContextPath() + "/PerfilServlet");
            return;
        }

        doGet(request, response);
    }

    private void cargarDatosPerfil(HttpServletRequest request, Usuario usuario) {
        PedidoDAO pedidoDAO = new PedidoDAO();
        CiudadesDAO ciudadesDAO = new CiudadesDAO();
        DireccionEnvioDAO direccionDAO = new DireccionEnvioDAO();

        // 1. Cargar Pedidos (en formato Mapa para el JSP), Ciudades y Direcciones
        List<Map<String, Object>> listaPedidosObj = pedidoDAO.listarPedidosMapaPorUsuario(usuario.getIdUsuario());
        List<Map<String, Object>> listaCiudades = ciudadesDAO.listarCiudades();
        List<Map<String, Object>> listaDirecciones = direccionDAO.listarDireccionesPorUsuario(usuario.getIdUsuario());

        request.setAttribute("listaPedidos", listaPedidosObj);
        request.setAttribute("listaCiudades", listaCiudades);
        request.setAttribute("listaDirecciones", listaDirecciones);
        request.setAttribute("totalPedidos", listaPedidosObj.size());

        // Reseñas reales del usuario.
        List<Map<String, Object>> listaResenas = new ArrayList<>();
        try (Connection conn = new Conexion().getConn()) {
            if (conn != null) {
                String sqlResenas = "SELECT id_resena, observacion, calificacion, fecha "
                        + "FROM resena_usuario WHERE usuario_id_usuario = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlResenas)) {
                    ps.setInt(1, usuario.getIdUsuario());
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Map<String, Object> r = new HashMap<>();
                            r.put("id", rs.getInt("id_resena"));
                            r.put("observacion", rs.getString("observacion"));
                            r.put("calificacion", rs.getInt("calificacion"));
                            r.put("fecha", rs.getTimestamp("fecha"));
                            listaResenas.add(r);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudieron cargar las reseñas: " + e.getMessage());
        }
        request.setAttribute("listaResenas", listaResenas);
        request.setAttribute("totalResenas", listaResenas.size());

        FavoritosDAO favoritosDAO = new FavoritosDAO();
        ArrayList<Producto> listaFavoritos = favoritosDAO.obtenerPorUsuario(usuario.getIdUsuario());
        Set<Integer> favoritosIds = new LinkedHashSet<>();
        for (Producto producto : listaFavoritos) {
            favoritosIds.add(producto.getIdProducto());
            producto.setImagen(resolverImagenFavorito(producto.getNombreProd()));
        }
        request.getSession().setAttribute("favoritos", favoritosIds);
        request.setAttribute("totalFavoritos", listaFavoritos.size());
        request.setAttribute("favoritosIds", favoritosIds);
        request.setAttribute("listaFavoritos", listaFavoritos);
        request.setAttribute("favoritesList", listaFavoritos);

        // --- 2. CARGAR DATOS DE INFORMACIÓN PERSONAL (Fecha, Tipo Doc, Rol) ---
        
        // Formatear Fecha de Nacimiento
        if (usuario.getFechaNacimiento() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            request.setAttribute("fechaNacimiento", sdf.format(usuario.getFechaNacimiento()));
        }

        List<Map<String, Object>> listaTiposDoc = new ArrayList<>();
        String tipoDocNombre = "Desconocido";
        String tipoUsuarioNombre = "CLIENTE";
        
        Conexion conect = new Conexion();
        try (Connection conn = conect.getConn()) {
            if (conn != null) {
                // Consultar todos los Tipos de Documento para llenar el select
                try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM tipo_documento");
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> td = new HashMap<>();
                        int id = rs.getInt(1); 
                        String desc = rs.getString(2); 
                        td.put("id", id);
                        td.put("descripcion", desc);
                        listaTiposDoc.add(td);
                        
                        // Validar si es el documento actual del usuario
                        if (usuario.getTipoDocumentoIdTipoDocumento() == id) {
                            tipoDocNombre = desc;
                        }
                    }
                } catch(Exception e) { System.out.println("Error consultando tipos de documento: " + e.getMessage()); }

                // Consultar el Tipo de Usuario (Rol)
                try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM tipo_usuario WHERE id_tipo_usuario = ?")) {
                    ps.setInt(1, usuario.getTipoUsuarioIdTipoUsuario());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            tipoUsuarioNombre = rs.getString(2); 
                        }
                    }
                } catch(Exception e) { System.out.println("Error consultando tipo de usuario: " + e.getMessage()); }
            }
        } catch (Exception e) {
            System.out.println("Error de conexión al cargar datos adicionales: " + e.getMessage());
        }

        // Datos por defecto por si las consultas fallan o las tablas están vacías
        if (listaTiposDoc.isEmpty()) {
            Map<String, Object> cc = new HashMap<>(); cc.put("id", 1); cc.put("descripcion", "Cédula de Ciudadanía");
            Map<String, Object> ti = new HashMap<>(); ti.put("id", 2); ti.put("descripcion", "Tarjeta de Identidad");
            Map<String, Object> ce = new HashMap<>(); ce.put("id", 3); ce.put("descripcion", "Cédula de Extranjería");
            listaTiposDoc.add(cc); listaTiposDoc.add(ti); listaTiposDoc.add(ce);
            
            if (usuario.getTipoDocumentoIdTipoDocumento() == 1) tipoDocNombre = "Cédula de Ciudadanía";
            else if (usuario.getTipoDocumentoIdTipoDocumento() == 2) tipoDocNombre = "Tarjeta de Identidad";
            else if (usuario.getTipoDocumentoIdTipoDocumento() == 3) tipoDocNombre = "Cédula de Extranjería";
        }

        request.setAttribute("listaTiposDoc", listaTiposDoc);
        request.setAttribute("tipoDocNombre", tipoDocNombre);
        request.setAttribute("tipoUsuarioNombre", tipoUsuarioNombre.toUpperCase());
        String autorizacionDatos = usuario.getAutorizacionDatos();
        if (autorizacionDatos == null || autorizacionDatos.trim().isEmpty()) autorizacionDatos = "SI";
        request.setAttribute("autorizacionDatos", autorizacionDatos);
    }

    private String resolverImagenFavorito(String nombre) {
        if (nombre == null) return "product-placeholder.svg";
        String n = nombre.trim();
        String[][] mapa = {
            {"Base Matte Pro", "BaseMattePro.png"},
            {"Base Fluida HD", "Base_Fluida_HD.png"},
            {"Labial Matte Chic", "Labial Matte Chic.png"},
            {"Labial Nude Soft", "Labial Nude Soft.png"},
            {"Lip Gloss Crystal", "Lip Gloss Crystal.png"},
            {"Máscara Mega Volume", "Mascara Mega Volume.png"},
            {"Mascara Mega Volume", "Mascara Mega Volume.png"},
            {"Máscara Waterproof Pro", "Mascara Waterproof Pro.png"},
            {"Mascara Waterproof Pro", "Mascara Waterproof Pro.png"},
            {"Paleta de Sombras Nude", "paleta de sombras Nude.png"},
            {"Iluminador Golden Glow", "iluminador golden glow.png"},
            {"Polvo Compacto Matte", "polvo compacto matte.png"},
            {"Spray Fijador Pro", "Spray Fijador Pro.png"},
            {"Lápiz para Cejas Brow Define", "Lapiz para Cejas Brow Define.png"},
            {"Brow Pencil Define", "Brow Pencil Define.png"},
            {"base hidratante", "base hidratante.png"},
            {"base natural", "base natural.png"},
            {"bronzer bronze sun", "bronzer bronze sun.png"},
            {"bronzer sun kiss", "bronzer sun kiss.png"},
            {"contorno stick pro", "contorno stick pro.png"},
            {"corrector bright touch", "corrector bright touch.png"},
            {"corrector full cover", "corrector full cover.png"},
            {"corrector perfect skin", "corrector perfect skin.png"},
            {"gel fijador de cejas", "gel fijador de cejas.png"},
            {"iluminador rose light", "iluminador rose light.png"},
            {"iluminador shine gold", "iluminador shine gold.png"},
            {"Labial Red Passion", "Labial Red Passion.png"},
            {"Labial Velvet", "Labial Velvet.png"},
            {"Labial Nude", "Labial_Nude.png"},
            {"Lip Gloss Shine", "Lip Gloss Shine.png"},
            {"Lip Tint Natural", "Lip Tint Natural.png"},
            {"Mascara Volume Max", "Mascara_Volumen_Max.png"},
            {"Máscara Volume Max", "Mascara_Volumen_Max.png"},
            {"Paleta de Sombras Escarchada", "paleta de sombras escarchada.png"},
            {"Pestañas Postizas Glam", "Pestanas Postizas Glam.png"},
            {"Polvo Suelto Soft Matte", "polvo suelto soft matte.png"},
            {"Primer", "primer.png"},
            {"Primer Hidratante", "primerhidratante.png"},
            {"Primer Matte", "primermatte.png"},
            {"Rubor", "rubor.png"},
            {"Rubor Coral", "ruborcoral.png"},
            {"Rubor Rosa", "ruborrosa.png"},
            {"Sombras Color", "sombras color.png"},
            {"Sombras Nude", "sombras nude.png"},
            {"Sombras", "sombras.png"},
            {"Spray Fix Makeup", "Spray Fix Makeup.png"}
        };
        for (String[] par : mapa) {
            if (par[0].equalsIgnoreCase(n)) return par[1];
        }
        return "product-placeholder.svg";
    }
}