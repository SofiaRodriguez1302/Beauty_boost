package Pruebas;

import Controlador.UsuarioDAO;
import Modelo.Usuario;
import java.util.Scanner;

public class PruebaInactivarUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        System.out.print("Ingrese el ID del usuario a inactivar: ");
        
        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos primero si el usuario existe en la base de datos
            Usuario usuarioActual = usuarioDAO.consultarUsuario(idBusqueda);

            if (usuarioActual != null) {
                System.out.println("\nUsuario encontrado:");
                System.out.println("ID: " + usuarioActual.getIdUsuario());
                System.out.println("Nombre: " + usuarioActual.getNombre() + " " + usuarioActual.getApellido());
                System.out.println("Estado actual (Autorización): " + usuarioActual.getAutorizacionDatos());

                // 2. Procedemos a inactivarlo
                System.out.println("\nProcesando inactivación...");
                boolean inactivado = usuarioDAO.inactivarUsuario(idBusqueda);

                if (inactivado) {
                    System.out.println("¡El usuario ha sido inactivado exitosamente!");
                } else {
                    System.out.println("No se pudo inactivar el usuario.");
                }
            } else {
                System.out.println("\nNo existe ningún usuario registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}