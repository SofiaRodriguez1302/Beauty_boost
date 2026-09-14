package Pruebas;

import Controlador.ResenaUsuarioDAO;
import Modelo.ResenaUsuario;
import java.util.Date;
import java.util.Scanner;

public class PruebaActualizarResenaUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ResenaUsuarioDAO resenaDAO = new ResenaUsuarioDAO();

        System.out.print("Ingrese el ID de la reseña a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la reseña existe
            ResenaUsuario resenaExistente = resenaDAO.consultarResena(idBusqueda);

            if (resenaExistente != null) {
                System.out.println("\nReseña encontrada:");
                System.out.println("ID: " + resenaExistente.getIdResena());
                System.out.println("Observación Actual: " + resenaExistente.getObservacion());
                System.out.println("Calificación Actual: " + resenaExistente.getCalificacion());
                System.out.println("ID Usuario Actual: " + resenaExistente.getUsuarioIdUsuario());

                // 2. Solicitamos los nuevos datos
                System.out.print("\nIngrese la nueva observación / comentario: ");
                String nuevaObservacion = teclado.nextLine().trim();

                System.out.print("Ingrese la nueva calificación (1 a 5): ");
                int nuevaCalificacion = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID de usuario asociado: ");
                int nuevoIdUsuario = Integer.parseInt(teclado.nextLine());

                if (!nuevaObservacion.isEmpty()) {
                    resenaExistente.setObservacion(nuevaObservacion);
                    resenaExistente.setCalificacion(nuevaCalificacion);
                    resenaExistente.setFecha(new Date()); // Actualiza la fecha a la hora actual
                    resenaExistente.setUsuarioIdUsuario(nuevoIdUsuario);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = resenaDAO.actualizarResena(resenaExistente);

                    if (actualizado) {
                        System.out.println("¡Reseña actualizada con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar la reseña.");
                    }
                } else {
                    System.out.println("\nError: La observación no puede estar vacía.");
                }

            } else {
                System.out.println("\nNo existe ninguna reseña con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar números válidos para los campos numéricos.");
        }

        teclado.close();
    }
}