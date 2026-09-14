package Pruebas;

import Controlador.MetodoDePagoDAO;
import Modelo.MetodoDePago;
import java.util.Scanner;

public class PruebaActivarMetodoDePago {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        MetodoDePagoDAO metodoDAO = new MetodoDePagoDAO();

        System.out.print("Ingrese el ID del método de pago a activar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el método de pago existe en la base de datos
            MetodoDePago mpExistente = metodoDAO.consultarMetodo(idBusqueda);

            if (mpExistente != null) {
                System.out.println("\nMétodo de pago encontrado:");
                System.out.println("ID Método de Pago: " + mpExistente.getIdMetodoDePago());
                System.out.println("Descripción: " + mpExistente.getDescripcionMetodoDePago());

                // 2. Procedemos a activarlo
                System.out.println("\nProcesando activación...");
                boolean activado = metodoDAO.activarMetodo(idBusqueda);

                if (activado) {
                    System.out.println("¡El método de pago ha sido activado con éxito!");
                } else {
                    System.out.println("No se pudo activar el método de pago.");
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