package Modelo;

import java.util.Date;

public class Pago {

    private int idPago;
    private Date fechaPago;
    private double montoPagado;
    private int metodoDePagoIdMetodoPago;
    private int pedidoIdPedido;

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public int getMetodoDePagoIdMetodoPago() {
        return metodoDePagoIdMetodoPago;
    }

    public void setMetodoDePagoIdMetodoPago(int metodoDePagoIdMetodoPago) {
        this.metodoDePagoIdMetodoPago = metodoDePagoIdMetodoPago;
    }

    public int getPedidoIdPedido() {
        return pedidoIdPedido;
    }

    public void setPedidoIdPedido(int pedidoIdPedido) {
        this.pedidoIdPedido = pedidoIdPedido;
    }
}
