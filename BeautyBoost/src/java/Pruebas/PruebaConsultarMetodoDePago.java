package Pruebas;

import Controlador.MetodoDePagoDAO;
import Modelo.MetodoDePago;
import java.util.Scanner;

public class PruebaConsultarMetodoDePago {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        MetodoDePagoDAO metodoDAO = new MetodoDePagoDAO();

        System.out.print("Ingrese el ID del método de pago a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el método de pago por su ID
            MetodoDePago mp = metodoDAO.consultarMetodo(idBusqueda);

            if (mp != null) {
                System.out.println("\nMétodo de pago encontrado:");
                System.out.println("ID Método de Pago: " + mp.getIdMetodoDePago());
                System.out.println("Descripción: " + mp.getDescripcionMetodoDePago());
            } else {
                System.out.println("\nNo existe ningún método de pago registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}