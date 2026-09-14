package Pruebas;

import Controlador.MetodoDePagoDAO;
import Modelo.MetodoDePago;
import java.util.Scanner;

public class PruebaActualizarMetodoDePago {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        MetodoDePagoDAO metodoDAO = new MetodoDePagoDAO();

        System.out.print("Ingrese el ID del método de pago a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el método de pago existe
            MetodoDePago mpExistente = metodoDAO.consultarMetodo(idBusqueda);

            if (mpExistente != null) {
                System.out.println("\nMétodo de pago encontrado:");
                System.out.println("ID Método de Pago: " + mpExistente.getIdMetodoDePago());
                System.out.println("Descripción Actual: " + mpExistente.getDescripcionMetodoDePago());

                // 2. Solicitamos la nueva descripción
                System.out.print("\nIngrese la nueva descripción: ");
                String nuevaDescripcion = teclado.nextLine().trim();

                if (!nuevaDescripcion.isEmpty()) {
                    mpExistente.setDescripcionMetodoDePago(nuevaDescripcion);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = metodoDAO.actualizarMetodo(mpExistente);

                    if (actualizado) {
                        System.out.println("¡Método de pago actualizado con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar el método de pago.");
                    }
                } else {
                    System.out.println("\nError: La descripción no puede estar vacía.");
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