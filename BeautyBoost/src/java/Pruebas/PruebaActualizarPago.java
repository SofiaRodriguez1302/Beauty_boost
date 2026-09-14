package Pruebas;

import Controlador.PagoDAO;
import Modelo.Pago;
import java.util.Date;
import java.util.Scanner;

public class PruebaActualizarPago {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PagoDAO pagoDAO = new PagoDAO();

        System.out.print("Ingrese el ID del pago a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el pago existe
            Pago pagoExistente = pagoDAO.consultarPago(idBusqueda);

            if (pagoExistente != null) {
                System.out.println("\nPago encontrado:");
                System.out.println("ID Pago: " + pagoExistente.getIdPago());
                System.out.println("Fecha Actual: " + pagoExistente.getFechaPago());
                System.out.println("Monto Actual: $" + pagoExistente.getMontoPagado());
                System.out.println("ID Método de Pago Actual: " + pagoExistente.getMetodoDePagoIdMetodoPago());
                System.out.println("ID Pedido Actual: " + pagoExistente.getPedidoIdPedido());

                // 2. Solicitamos los nuevos datos
                System.out.print("\nIngrese el nuevo monto pagado: ");
                double nuevoMonto = Double.parseDouble(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID del método de pago: ");
                int nuevoMetodoPago = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID del pedido asociado: ");
                int nuevoPedidoId = Integer.parseInt(teclado.nextLine());

                // Actualizamos con la fecha y hora actual del sistema
                pagoExistente.setFechaPago(new Date());
                pagoExistente.setMontoPagado(nuevoMonto);
                pagoExistente.setMetodoDePagoIdMetodoPago(nuevoMetodoPago);
                pagoExistente.setPedidoIdPedido(nuevoPedidoId);

                System.out.println("\nProcesando actualización...");
                boolean actualizado = pagoDAO.actualizarPago(pagoExistente);

                if (actualizado) {
                    System.out.println("¡Pago actualizado con éxito!");
                } else {
                    System.out.println("No se pudo actualizar el pago.");
                }

            } else {
                System.out.println("\nNo existe ningún pago registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar valores numéricos válidos en los campos correspondientes.");
        }

        teclado.close();
    }
}