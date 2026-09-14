package Modelo;

public class Producto {
    private int idProducto;
    private String nombreProd;
    private String descripcionProd;
    private double precio;
    private int stock;
    private int categoriaIdCategoria;
    private String imagen;
    private String estadoProducto;

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public String getNombreProd() { return nombreProd; }
    public void setNombreProd(String nombreProd) { this.nombreProd = nombreProd; }
    public String getDescripcionProd() { return descripcionProd; }
    public void setDescripcionProd(String descripcionProd) { this.descripcionProd = descripcionProd; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public int getCategoriaIdCategoria() { return categoriaIdCategoria; }
    public void setCategoriaIdCategoria(int categoriaIdCategoria) { this.categoriaIdCategoria = categoriaIdCategoria; }
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public String getImagenUrl() { return (imagen == null || imagen.trim().isEmpty()) ? "img/default.jpg" : imagen; }
    public void setImagenUrl(String imagenUrl) { this.imagen = imagenUrl; }
    public String getEstadoProducto() { return estadoProducto; }
    public void setEstadoProducto(String estadoProducto) { this.estadoProducto = estadoProducto; }
}
