package Modelo;

public class DireccionEnvio {

    private int idDireccion;
    private String direccionEnvio;
    private int usuarioIdUsuario;
    private int ciudadesIdCiudades;

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public int getUsuarioIdUsuario() {
        return usuarioIdUsuario;
    }

    public void setUsuarioIdUsuario(int usuarioIdUsuario) {
        this.usuarioIdUsuario = usuarioIdUsuario;
    }

    public int getCiudadesIdCiudades() {
        return ciudadesIdCiudades;
    }

    public void setCiudadesIdCiudades(int ciudadesIdCiudades) {
        this.ciudadesIdCiudades = ciudadesIdCiudades;
    }
}
