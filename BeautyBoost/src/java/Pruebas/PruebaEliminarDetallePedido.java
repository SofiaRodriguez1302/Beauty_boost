package Pruebas;

import Controlador.DetallePedidoDAO;
import Modelo.DetallePedido;
import java.util.Scanner;

public class PruebaEliminarDetallePedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DetallePedidoDAO detalleDAO = new DetallePedidoDAO();

        System.out.print("Ingrese el ID del detalle de pedido a eliminar: ");

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

                // 2. Confirmación previa antes de eliminar
                System.out.print("\n¿Está seguro de eliminar este detalle de pedido? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = detalleDAO.eliminarDetalle(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Detalle de pedido eliminado con éxito!");
                    } else {
                        System.out.println("No se pudo eliminar el detalle de pedido.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
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