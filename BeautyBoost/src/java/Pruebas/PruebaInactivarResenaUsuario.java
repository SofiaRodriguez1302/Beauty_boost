package Pruebas;

import Controlador.ResenaUsuarioDAO;
import Modelo.ResenaUsuario;
import java.util.Scanner;

public class PruebaInactivarResenaUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ResenaUsuarioDAO resenaDAO = new ResenaUsuarioDAO();

        System.out.print("Ingrese el ID de la reseña a inactivar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la reseña existe en la base de datos
            ResenaUsuario resenaExistente = resenaDAO.consultarResena(idBusqueda);

            if (resenaExistente != null) {
                System.out.println("\nReseña encontrada:");
                System.out.println("ID: " + resenaExistente.getIdResena());
                System.out.println("Observación: " + resenaExistente.getObservacion());
                System.out.println("Calificación: " + resenaExistente.getCalificacion());

                // 2. Procedemos a inactivarla
                System.out.println("\nProcesando inactivación...");
                boolean inactivado = resenaDAO.inactivarResena(idBusqueda);

                if (inactivado) {
                    System.out.println("¡La reseña ha sido inactivada con éxito!");
                } else {
                    System.out.println("No se pudo inactivar la reseña.");
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