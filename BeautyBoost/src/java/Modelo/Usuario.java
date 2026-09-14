package Modelo;

import java.util.Date;

public class Usuario {
    
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String numeroIdentificacion;
    private String telefono;
    private String correo;
    private String clave;
    private Date fechaNacimiento;
    private Date fechaVencimientoClave;
    private String autorizacionDatos;
    private int tipoDocumentoIdTipoDocumento;
    private int tipoUsuarioIdTipoUsuario;
    private int rolesIdRol;
    private String estadoUsuario;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellido, String numeroIdentificacion,
            String telefono, String correo, String clave, Date fechaNacimiento,
            Date fechaVencimientoClave, String autorizacionDatos,
            int tipoDocumentoIdTipoDocumento, int tipoUsuarioIdTipoUsuario, int rolesIdRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroIdentificacion = numeroIdentificacion;
        this.telefono = telefono;
        this.correo = correo;
        this.clave = clave;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaVencimientoClave = fechaVencimientoClave;
        this.autorizacionDatos = autorizacionDatos;
        this.tipoDocumentoIdTipoDocumento = tipoDocumentoIdTipoDocumento;
        this.tipoUsuarioIdTipoUsuario = tipoUsuarioIdTipoUsuario;
        this.rolesIdRol = rolesIdRol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaVencimientoClave() {
        return fechaVencimientoClave;
    }

    public void setFechaVencimientoClave(Date fechaVencimientoClave) {
        this.fechaVencimientoClave = fechaVencimientoClave;
    }

    public String getAutorizacionDatos() {
        return autorizacionDatos;
    }

    public void setAutorizacionDatos(String autorizacionDatos) {
        this.autorizacionDatos = autorizacionDatos;
    }

    public int getTipoDocumentoIdTipoDocumento() {
        return tipoDocumentoIdTipoDocumento;
    }

    public void setTipoDocumentoIdTipoDocumento(int tipoDocumentoIdTipoDocumento) {
        this.tipoDocumentoIdTipoDocumento = tipoDocumentoIdTipoDocumento;
    }

    public int getTipoUsuarioIdTipoUsuario() {
        return tipoUsuarioIdTipoUsuario;
    }

    public void setTipoUsuarioIdTipoUsuario(int tipoUsuarioIdTipoUsuario) {
        this.tipoUsuarioIdTipoUsuario = tipoUsuarioIdTipoUsuario;
    }

    public int getRolesIdRol() {
        return rolesIdRol;
    }

    public void setRolesIdRol(int rolesIdRol) {
        this.rolesIdRol = rolesIdRol;
    }

    public String getEstadoUsuario() { return estadoUsuario; }

    public void setEstadoUsuario(String estadoUsuario) { this.estadoUsuario = estadoUsuario; }
}