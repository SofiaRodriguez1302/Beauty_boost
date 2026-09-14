package Pruebas;

import Controlador.ResenaUsuarioDAO;
import Modelo.ResenaUsuario;
import java.util.Date;
import java.util.Scanner;

public class PruebaInsertarResenaUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ResenaUsuarioDAO resenaDAO = new ResenaUsuarioDAO();
        ResenaUsuario nuevaResena = new ResenaUsuario();

        try {
            System.out.print("Ingrese la observación / comentario: ");
            String observacion = teclado.nextLine().trim();

            System.out.print("Ingrese la calificación (1 a 5): ");
            int calificacion = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el ID del usuario que hace la reseña: ");
            int idUsuario = Integer.parseInt(teclado.nextLine());

            if (!observacion.isEmpty()) {
                nuevaResena.setObservacion(observacion);
                nuevaResena.setCalificacion(calificacion);
                nuevaResena.setFecha(new Date()); // Asigna la fecha y hora actual
                nuevaResena.setUsuarioIdUsuario(idUsuario);

                System.out.println("\nProcesando inserción...");
                boolean exito = resenaDAO.insertarResena(nuevaResena);

                if (exito) {
                    System.out.println("¡Reseña registrada con éxito!");
                } else {
                    System.out.println("No se pudo registrar la reseña.");
                }
            } else {
                System.out.println("\nError: La observación no puede estar vacía.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar números válidos para la calificación e ID de usuario.");
        }

        teclado.close();
    }
}