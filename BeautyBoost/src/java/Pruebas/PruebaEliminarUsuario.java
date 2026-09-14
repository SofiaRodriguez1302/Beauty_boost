package Pruebas;

import Controlador.UsuarioDAO;
import Modelo.Usuario;
import java.util.Scanner;

public class PruebaEliminarUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        System.out.println(" ELIMINAR USUARIO SEGURO ");
        System.out.print("Ingrese el correo del usuario que desea eliminar: ");
        String correoBuscar = teclado.nextLine();

        Usuario miUsuario = usuarioDAO.consultarUsuario(correoBuscar);

        if (miUsuario != null) {
            int idEliminar = miUsuario.getIdUsuario();
            System.out.println("\n¿Está seguro de eliminar a " + miUsuario.getNombre() + " " + miUsuario.getApellido() + "?");
            System.out.print("Escriba 'SI' para confirmar: ");
            String confirmacion = teclado.nextLine();

            if (confirmacion.equalsIgnoreCase("SI")) {
                boolean exito = usuarioDAO.eliminarUsuario(idEliminar);
                System.out.println("¿Usuario eliminado con éxito?: " + exito);
            } else {
                System.out.println("Operación cancelada por el usuario.");
            }
        } else {
            System.out.println("No se encontró ningún usuario vinculado al correo: " + correoBuscar);
        }
    }
}
