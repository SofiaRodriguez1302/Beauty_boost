package Pruebas;

import Controlador.PagoDAO;
import Modelo.Pago;
import java.util.Scanner;

public class PruebaEliminarPago {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PagoDAO pagoDAO = new PagoDAO();

        System.out.print("Ingrese el ID del pago a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el pago existe en la base de datos
            Pago pagoExistente = pagoDAO.consultarPago(idBusqueda);

            if (pagoExistente != null) {
                System.out.println("\nPago encontrado:");
                System.out.println("ID Pago: " + pagoExistente.getIdPago());
                System.out.println("Monto Pagado: $" + pagoExistente.getMontoPagado());
                System.out.println("ID Pedido: " + pagoExistente.getPedidoIdPedido());

                System.out.print("\n¿Está seguro de eliminar este pago? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = pagoDAO.eliminarPago(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Pago eliminado exitosamente!");
                    } else {
                        System.out.println("No se pudo eliminar el pago.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
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