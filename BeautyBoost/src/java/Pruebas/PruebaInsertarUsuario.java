package Pruebas;

import Controlador.UsuarioDAO;
import Modelo.Usuario;
import java.util.Date;
import java.util.Scanner;

public class PruebaInsertarUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario miUsuario = new Usuario();

        System.out.println(" REGISTRO DE NUEVO USUARIO  ");

        System.out.print("Ingrese el Nombre: ");
        miUsuario.setNombre(teclado.nextLine());

        System.out.print("Ingrese el Apellido: ");
        miUsuario.setApellido(teclado.nextLine());

        System.out.print("Ingrese el Número de Identificación (Único): ");
        miUsuario.setNumeroIdentificacion(teclado.nextLine());

        System.out.print("Ingrese el Teléfono: ");
        miUsuario.setTelefono(teclado.nextLine());

        System.out.print("Ingrese el Correo Electrónico: ");
        miUsuario.setCorreo(teclado.nextLine());

        System.out.print("Ingrese la Clave: ");
        miUsuario.setClave(teclado.nextLine());

        miUsuario.setFechaNacimiento(new Date());
        
        // Asignación de fecha de vencimiento a 2 años a partir de hoy
        long dosAnios = System.currentTimeMillis() + (365L * 2 * 24 * 60 * 60 * 1000);
        miUsuario.setFechaVencimientoClave(new Date(dosAnios));
        
        miUsuario.setAutorizacionDatos("SI");

        // Asignación de IDs válidos para claves foráneas (FK)
        miUsuario.setTipoDocumentoIdTipoDocumento(1); // 1 = Cédula de Ciudadanía
        miUsuario.setTipoUsuarioIdTipoUsuario(4);   // 4 = Cliente Nuevo en tu base de datos

        System.out.println("\nProcesando inserción...");
        
        // Llamada corregida al método con minúscula 'insertarUsuario'
        boolean exito = usuarioDAO.insertarUsuario(miUsuario);
        System.out.println("¿Inserción de usuario exitosa?: " + exito);

        teclado.close();
    }
}