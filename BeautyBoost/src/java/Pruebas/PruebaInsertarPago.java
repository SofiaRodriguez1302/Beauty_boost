package Pruebas;

import Controlador.PagoDAO;
import Modelo.Pago;
import java.util.Date;
import java.util.Scanner;

public class PruebaInsertarPago {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PagoDAO pagoDAO = new PagoDAO();
        Pago nuevoPago = new Pago();

        try {
            System.out.print("Ingrese el monto a pagar: ");
            double monto = Double.parseDouble(teclado.nextLine());

            System.out.print("Ingrese el ID del método de pago: ");
            int idMetodoPago = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el ID del pedido asociado: ");
            int idPedido = Integer.parseInt(teclado.nextLine());

            nuevoPago.setFechaPago(new Date()); // Asigna la fecha y hora actual del sistema
            nuevoPago.setMontoPagado(monto);
            nuevoPago.setMetodoDePagoIdMetodoPago(idMetodoPago);
            nuevoPago.setPedidoIdPedido(idPedido);

            System.out.println("\nProcesando inserción...");
            boolean exito = pagoDAO.insertarPago(nuevoPago);

            if (exito) {
                System.out.println("¡Pago registrado con éxito!");
            } else {
                System.out.println("No se pudo registrar el pago.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar valores numéricos válidos en los campos correspondientes.");
        }

        teclado.close();
    }
}