package Modelo;

import java.util.Date;

public class ControlEnvio {

    private int idControl;
    private Date fechaEntrega;
    private String codigoSeguimiento;
    private int estadoPedidoIdEstadoPedido;
    private int empresasEnvioIdEmpresaEnvio;
    private int pedidoIdPedido;

    public int getIdControl() {
        return idControl;
    }

    public void setIdControl(int idControl) {
        this.idControl = idControl;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public void setCodigoSeguimiento(String codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }

    public int getEstadoPedidoIdEstadoPedido() {
        return estadoPedidoIdEstadoPedido;
    }

    public void setEstadoPedidoIdEstadoPedido(int estadoPedidoIdEstadoPedido) {
        this.estadoPedidoIdEstadoPedido = estadoPedidoIdEstadoPedido;
    }

    public int getEmpresasEnvioIdEmpresaEnvio() {
        return empresasEnvioIdEmpresaEnvio;
    }

    public void setEmpresasEnvioIdEmpresaEnvio(int empresasEnvioIdEmpresaEnvio) {
        this.empresasEnvioIdEmpresaEnvio = empresasEnvioIdEmpresaEnvio;
    }

    public int getPedidoIdPedido() {
        return pedidoIdPedido;
    }

    public void setPedidoIdPedido(int pedidoIdPedido) {
        this.pedidoIdPedido = pedidoIdPedido;
    }
}
