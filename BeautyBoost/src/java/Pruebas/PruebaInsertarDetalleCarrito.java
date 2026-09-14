package Pruebas;

import Controlador.DetalleCarritoDAO;
import Modelo.DetalleCarrito;
import java.util.Scanner;

public class PruebaInsertarDetalleCarrito {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DetalleCarritoDAO detalleDAO = new DetalleCarritoDAO();
        DetalleCarrito nuevoDetalle = new DetalleCarrito();

        try {
            System.out.print("Ingrese la cantidad: ");
            int cantidad = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese la descripción del producto/detalle: ");
            String descripcion = teclado.nextLine().trim();

            System.out.print("Ingrese el ID del carrito: ");
            int idCarrito = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el ID del producto: ");
            int idProducto = Integer.parseInt(teclado.nextLine());

            if (cantidad > 0) {
                nuevoDetalle.setCantidad(cantidad);
                nuevoDetalle.setDescripcion(descripcion);
                nuevoDetalle.setCarritoIdCarrito(idCarrito);
                nuevoDetalle.setProductoIdProducto(idProducto);

                System.out.println("\nProcesando inserción...");
                boolean exito = detalleDAO.insertarDetalle(nuevoDetalle);

                if (exito) {
                    System.out.println("¡Detalle de carrito registrado con éxito!");
                } else {
                    System.out.println("No se pudo registrar el detalle de carrito.");
                }
            } else {
                System.out.println("\nError: La cantidad debe ser mayor a cero.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Ingrese valores numéricos válidos en los campos correspondientes.");
        }

        teclado.close();
    }
}