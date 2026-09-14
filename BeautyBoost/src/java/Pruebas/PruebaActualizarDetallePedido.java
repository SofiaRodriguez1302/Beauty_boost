package Pruebas;

import Controlador.DetallePedidoDAO;
import Modelo.DetallePedido;
import java.util.Scanner;

public class PruebaActualizarDetallePedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DetallePedidoDAO detalleDAO = new DetallePedidoDAO();

        System.out.print("Ingrese el ID del detalle de pedido a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificar si el detalle de pedido existe
            DetallePedido dpExistente = detalleDAO.consultarDetalle(idBusqueda);

            if (dpExistente != null) {
                System.out.println("\nDetalle de pedido encontrado:");
                System.out.println("ID Detalle: " + dpExistente.getIdDetallePedido());
                System.out.println("Cantidad Actual: " + dpExistente.getCantidad());
                System.out.println("Precio Unitario Actual: $" + dpExistente.getPrecioUnitario());
                System.out.println("ID Producto Actual: " + dpExistente.getProductoIdProducto());
                System.out.println("ID Pedido Actual: " + dpExistente.getPedidoIdPedido());

                // 2. Pedir los nuevos datos
                System.out.print("\nIngrese la nueva cantidad: ");
                int nuevaCantidad = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese el nuevo precio unitario: ");
                double nuevoPrecio = Double.parseDouble(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID del producto: ");
                int nuevoIdProducto = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID del pedido: ");
                int nuevoIdPedido = Integer.parseInt(teclado.nextLine());

                if (nuevaCantidad > 0 && nuevoPrecio > 0) {
                    dpExistente.setCantidad(nuevaCantidad);
                    dpExistente.setPrecioUnitario(nuevoPrecio);
                    dpExistente.setProductoIdProducto(nuevoIdProducto);
                    dpExistente.setPedidoIdPedido(nuevoIdPedido);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = detalleDAO.actualizarDetalle(dpExistente);

                    if (actualizado) {
                        System.out.println("¡Detalle de pedido actualizado con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar el detalle de pedido.");
                    }
                } else {
                    System.out.println("\nError: La cantidad y el precio deben ser mayores a cero.");
                }

            } else {
                System.out.println("\nNo existe ningún detalle de pedido registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Ingrese valores numéricos válidos en los campos correspondientes.");
        }

        teclado.close();
    }
}