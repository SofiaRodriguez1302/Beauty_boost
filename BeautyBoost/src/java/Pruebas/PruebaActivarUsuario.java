package Pruebas;

import Controlador.UsuarioDAO;
import Modelo.Usuario;
import java.util.Scanner;

public class PruebaActivarUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        System.out.print("Ingrese el ID del usuario a activar: ");
        
        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos primero si el usuario existe en la base de datos
            Usuario usuarioActual = usuarioDAO.consultarUsuario(idBusqueda);

            if (usuarioActual != null) {
                System.out.println("\nUsuario encontrado:");
                System.out.println("ID: " + usuarioActual.getIdUsuario());
                System.out.println("Nombre: " + usuarioActual.getNombre() + " " + usuarioActual.getApellido());
                System.out.println("Estado actual (Autorización): " + usuarioActual.getAutorizacionDatos());

                // 2. Procedemos a activarlo
                System.out.println("\nProcesando activación...");
                boolean activado = usuarioDAO.activarUsuario(idBusqueda);

                if (activado) {
                    System.out.println("¡El usuario ha sido activado exitosamente!");
                } else {
                    System.out.println("No se pudo activar el usuario.");
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