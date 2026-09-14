package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private Connection conn;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String user = "root";
    private String password = "";
    private String basedatos = "script_beauty_boost";
    private String url = "jdbc:mysql://localhost:3307/" + basedatos + "?useTimezone=true&serverTimezone=UTC";

    public Conexion() {
        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);

            if (conn == null) {
                System.out.println("No se establecio la conexion " + url);
            } else {
                System.out.println("La conexion fue exitosa con la base de datos: " + basedatos);
            }
        } catch (Exception e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }

    public Connection getConn() {
        return conn;
    }

    public Connection getConnection() {
        return getConn();
    }
}
