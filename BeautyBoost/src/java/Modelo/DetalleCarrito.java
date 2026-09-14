package Modelo;

public class DetalleCarrito {

    private int idDetalleCarrito;
    private int cantidad;
    private String descripcion;
    private int carritoIdCarrito;
    private int productoIdProducto;

    public int getIdDetalleCarrito() {
        return idDetalleCarrito;
    }

    public void setIdDetalleCarrito(int idDetalleCarrito) {
        this.idDetalleCarrito = idDetalleCarrito;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCarritoIdCarrito() {
        return carritoIdCarrito;
    }

    public void setCarritoIdCarrito(int carritoIdCarrito) {
        this.carritoIdCarrito = carritoIdCarrito;
    }

    public int getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(int productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }
}
