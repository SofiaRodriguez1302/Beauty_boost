package Modelo;

import java.util.Date;

public class Favorito {
    private int idFavorito;
    private int usuarioIdUsuario;
    private int productoIdProducto;
    private Date fechaAgregado;
    private Producto producto;

    public int getIdFavorito() { return idFavorito; }
    public void setIdFavorito(int idFavorito) { this.idFavorito = idFavorito; }
    public int getUsuarioIdUsuario() { return usuarioIdUsuario; }
    public void setUsuarioIdUsuario(int usuarioIdUsuario) { this.usuarioIdUsuario = usuarioIdUsuario; }
    public int getProductoIdProducto() { return productoIdProducto; }
    public void setProductoIdProducto(int productoIdProducto) { this.productoIdProducto = productoIdProducto; }
    public Date getFechaAgregado() { return fechaAgregado; }
    public void setFechaAgregado(Date fechaAgregado) { this.fechaAgregado = fechaAgregado; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
}
