package Pruebas;

import Controlador.PagoDAO;
import Modelo.Pago;
import java.util.Scanner;

public class PruebaActivarPago {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PagoDAO pagoDAO = new PagoDAO();

        System.out.print("Ingrese el ID del pago a activar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el pago existe en la base de datos
            Pago pagoExistente = pagoDAO.consultarPago(idBusqueda);

            if (pagoExistente != null) {
                System.out.println("\nPago encontrado:");
                System.out.println("ID Pago: " + pagoExistente.getIdPago());
                System.out.println("Monto Pagado: $" + pagoExistente.getMontoPagado());
                System.out.println("ID Pedido: " + pagoExistente.getPedidoIdPedido());

                // 2. Procedemos a activarlo
                System.out.println("\nProcesando activación...");
                boolean activado = pagoDAO.activarPago(idBusqueda);

                if (activado) {
                    System.out.println("¡El pago ha sido activado con éxito!");
                } else {
                    System.out.println("No se pudo activar el pago.");
                }

            } else {
                System.out.println("\nNo existe ningún pago registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}