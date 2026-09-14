package Pruebas;

import Controlador.UsuarioDAO;
import Modelo.Usuario;
import java.util.Scanner;

public class PruebaActualizarUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        System.out.println(" ACTUALIZAR USUARIO ");
        System.out.print("Ingrese el correo electrónico del usuario a modificar: ");
        String correoBuscar = teclado.nextLine();

       
        Usuario miUsuario = usuarioDAO.consultarUsuario(correoBuscar);

        if (miUsuario != null) {
            System.out.println("\nUsuario encontrado actualmente: " + miUsuario.getNombre() + " " + miUsuario.getApellido());
            System.out.println(" Ingrese los nuevos datos ");

            System.out.print("Nuevo Nombre: ");
            miUsuario.setNombre(teclado.nextLine());

            System.out.print("Nuevo Apellido: ");
            miUsuario.setApellido(teclado.nextLine());

            System.out.print("Nuevo Teléfono: ");
            miUsuario.setTelefono(teclado.nextLine());

            
            boolean exito = usuarioDAO.actualizarUsuario(miUsuario);
            System.out.println("\n¿Actualización exitosa?: " + exito);
        } else {
            System.out.println("Error: No se encontró ningún usuario con el correo: " + correoBuscar);
        }
    }
}
