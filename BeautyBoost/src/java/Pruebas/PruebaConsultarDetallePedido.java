package Pruebas;

import Controlador.DetallePedidoDAO;
import Modelo.DetallePedido;
import java.util.Scanner;

public class PruebaConsultarDetallePedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DetallePedidoDAO detalleDAO = new DetallePedidoDAO();

        System.out.print("Ingrese el ID del detalle de pedido a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el detalle de pedido por su ID
            DetallePedido dp = detalleDAO.consultarDetalle(idBusqueda);

            if (dp != null) {
                System.out.println("\nDetalle de pedido encontrado:");
                System.out.println("ID Detalle Pedido: " + dp.getIdDetallePedido());
                System.out.println("Cantidad: " + dp.getCantidad());
                System.out.println("Precio Unitario: $" + dp.getPrecioUnitario());
                System.out.println("ID Producto: " + dp.getProductoIdProducto());
                System.out.println("ID Pedido: " + dp.getPedidoIdPedido());
            } else {
                System.out.println("\nNo existe ningún detalle de pedido registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}