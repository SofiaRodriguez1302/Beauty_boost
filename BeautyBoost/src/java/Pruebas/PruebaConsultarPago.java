package Pruebas;

import Controlador.PagoDAO;
import Modelo.Pago;
import java.util.Scanner;

public class PruebaConsultarPago {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PagoDAO pagoDAO = new PagoDAO();

        System.out.print("Ingrese el ID del pago a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el pago por su ID
            Pago pago = pagoDAO.consultarPago(idBusqueda);

            if (pago != null) {
                System.out.println("\nPago encontrado:");
                System.out.println("ID Pago: " + pago.getIdPago());
                System.out.println("Fecha de Pago: " + pago.getFechaPago());
                System.out.println("Monto Pagado: $" + pago.getMontoPagado());
                System.out.println("ID Método de Pago: " + pago.getMetodoDePagoIdMetodoPago());
                System.out.println("ID Pedido: " + pago.getPedidoIdPedido());
            } else {
                System.out.println("\nNo existe ningún pago registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}