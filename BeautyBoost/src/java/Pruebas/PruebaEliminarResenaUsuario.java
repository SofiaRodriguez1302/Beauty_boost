package Pruebas;

import Controlador.ResenaUsuarioDAO;
import Modelo.ResenaUsuario;
import java.util.Scanner;

public class PruebaEliminarResenaUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ResenaUsuarioDAO resenaDAO = new ResenaUsuarioDAO();

        System.out.print("Ingrese el ID de la reseña a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la reseña existe en la base de datos
            ResenaUsuario resenaExistente = resenaDAO.consultarResena(idBusqueda);

            if (resenaExistente != null) {
                System.out.println("\nReseña encontrada:");
                System.out.println("ID: " + resenaExistente.getIdResena());
                System.out.println("Observación: " + resenaExistente.getObservacion());
                System.out.println("Calificación: " + resenaExistente.getCalificacion());

                System.out.print("\n¿Está seguro de eliminar esta reseña? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = resenaDAO.eliminarResena(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Reseña eliminada exitosamente!");
                    } else {
                        System.out.println("No se pudo eliminar la reseña.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
                }

            } else {
                System.out.println("\nNo existe ninguna reseña registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}