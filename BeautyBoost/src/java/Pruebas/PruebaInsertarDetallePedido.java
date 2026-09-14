package Pruebas;

import Controlador.DetallePedidoDAO;
import Modelo.DetallePedido;
import java.util.Scanner;

public class PruebaInsertarDetallePedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DetallePedidoDAO detalleDAO = new DetallePedidoDAO();
        DetallePedido nuevoDetalle = new DetallePedido();

        try {
            System.out.print("Ingrese la cantidad: ");
            int cantidad = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el precio unitario: ");
            double precioUnitario = Double.parseDouble(teclado.nextLine());

            System.out.print("Ingrese el ID del producto: ");
            int idProducto = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el ID del pedido: ");
            int idPedido = Integer.parseInt(teclado.nextLine());

            if (cantidad > 0 && precioUnitario > 0) {
                nuevoDetalle.setCantidad(cantidad);
                nuevoDetalle.setPrecioUnitario(precioUnitario);
                nuevoDetalle.setProductoIdProducto(idProducto);
                nuevoDetalle.setPedidoIdPedido(idPedido);

                System.out.println("\nProcesando inserción...");
                boolean exito = detalleDAO.insertarDetalle(nuevoDetalle);

                if (exito) {
                    System.out.println("¡Detalle de pedido registrado con éxito!");
                } else {
                    System.out.println("No se pudo registrar el detalle de pedido.");
                }
            } else {
                System.out.println("\nError: La cantidad y el precio unitario deben ser mayores a cero.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Ingrese valores numéricos válidos en los campos correspondientes.");
        }

        teclado.close();
    }
}