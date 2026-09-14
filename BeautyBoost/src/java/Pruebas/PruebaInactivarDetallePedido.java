package Pruebas;

import Controlador.DetallePedidoDAO;
import Modelo.DetallePedido;
import java.util.Scanner;

public class PruebaInactivarDetallePedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DetallePedidoDAO detalleDAO = new DetallePedidoDAO();

        System.out.print("Ingrese el ID del detalle de pedido a inactivar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el detalle de pedido existe
            DetallePedido dpExistente = detalleDAO.consultarDetalle(idBusqueda);

            if (dpExistente != null) {
                System.out.println("\nDetalle de pedido encontrado:");
                System.out.println("ID Detalle: " + dpExistente.getIdDetallePedido());
                System.out.println("Cantidad: " + dpExistente.getCantidad());
                System.out.println("Precio Unitario: $" + dpExistente.getPrecioUnitario());
                System.out.println("ID Producto: " + dpExistente.getProductoIdProducto());
                System.out.println("ID Pedido: " + dpExistente.getPedidoIdPedido());

                // 2. Procedemos a inactivarlo
                System.out.println("\nProcesando inactivación...");
                boolean inactivado = detalleDAO.inactivarDetalle(idBusqueda);

                if (inactivado) {
                    System.out.println("¡El detalle de pedido ha sido inactivado con éxito!");
                } else {
                    System.out.println("No se pudo inactivar el detalle de pedido.");
                }

            } else {
                System.out.println("\nNo existe ningún detalle de pedido registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}