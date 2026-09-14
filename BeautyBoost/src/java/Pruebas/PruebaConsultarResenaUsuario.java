package Pruebas;

import Controlador.ResenaUsuarioDAO;
import Modelo.ResenaUsuario;
import java.util.Scanner;

public class PruebaConsultarResenaUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ResenaUsuarioDAO resenaDAO = new ResenaUsuarioDAO();

        System.out.print("Ingrese el ID de la reseña a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar la reseña por ID
            ResenaUsuario resena = resenaDAO.consultarResena(idBusqueda);

            if (resena != null) {
                System.out.println("\nReseña encontrada:");
                System.out.println("ID: " + resena.getIdResena());
                System.out.println("Observación: " + resena.getObservacion());
                System.out.println("Calificación: " + resena.getCalificacion());
                System.out.println("Fecha: " + resena.getFecha());
                System.out.println("ID Usuario: " + resena.getUsuarioIdUsuario());
            } else {
                System.out.println("\nNo existe ninguna reseña registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}