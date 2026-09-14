package Pruebas;

import Controlador.MetodoDePagoDAO;
import Modelo.MetodoDePago;
import java.util.Scanner;

public class PruebaEliminarMetodoDePago {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        MetodoDePagoDAO metodoDAO = new MetodoDePagoDAO();

        System.out.print("Ingrese el ID del método de pago a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el método de pago existe en la base de datos
            MetodoDePago mpExistente = metodoDAO.consultarMetodo(idBusqueda);

            if (mpExistente != null) {
                System.out.println("\nMétodo de pago encontrado:");
                System.out.println("ID Método de Pago: " + mpExistente.getIdMetodoDePago());
                System.out.println("Descripción: " + mpExistente.getDescripcionMetodoDePago());

                // 2. Confirmación antes de eliminar
                System.out.print("\n¿Está seguro de eliminar este método de pago? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = metodoDAO.eliminarMetodo(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Método de pago eliminado exitosamente!");
                    } else {
                        System.out.println("No se pudo eliminar el método de pago.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
                }

            } else {
                System.out.println("\nNo existe ningún método de pago registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}