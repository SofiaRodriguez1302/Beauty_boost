package Pruebas;

import Controlador.DetalleCarritoDAO;
import Modelo.DetalleCarrito;
import java.util.Scanner;

public class PruebaActualizarDetalleCarrito {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DetalleCarritoDAO detalleDAO = new DetalleCarritoDAO();

        System.out.print("Ingrese el ID del detalle de carrito a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificar si el detalle de carrito existe
            DetalleCarrito dcExistente = detalleDAO.consultarDetalle(idBusqueda);

            if (dcExistente != null) {
                System.out.println("\nDetalle de carrito encontrado:");
                System.out.println("ID Detalle: " + dcExistente.getIdDetalleCarrito());
                System.out.println("Cantidad Actual: " + dcExistente.getCantidad());
                System.out.println("Descripción Actual: " + dcExistente.getDescripcion());
                System.out.println("ID Carrito Actual: " + dcExistente.getCarritoIdCarrito());
                System.out.println("ID Producto Actual: " + dcExistente.getProductoIdProducto());

                // 2. Pedir los nuevos datos
                System.out.print("\nIngrese la nueva cantidad: ");
                int nuevaCantidad = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese la nueva descripción: ");
                String nuevaDescripcion = teclado.nextLine().trim();

                System.out.print("Ingrese el nuevo ID del carrito: ");
                int nuevoIdCarrito = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID del producto: ");
                int nuevoIdProducto = Integer.parseInt(teclado.nextLine());

                if (nuevaCantidad > 0) {
                    dcExistente.setCantidad(nuevaCantidad);
                    dcExistente.setDescripcion(nuevaDescripcion);
                    dcExistente.setCarritoIdCarrito(nuevoIdCarrito);
                    dcExistente.setProductoIdProducto(nuevoIdProducto);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = detalleDAO.actualizarDetalle(dcExistente);

                    if (actualizado) {
                        System.out.println("¡Detalle de carrito actualizado con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar el detalle de carrito.");
                    }
                } else {
                    System.out.println("\nError: La cantidad debe ser mayor a cero.");
                }

            } else {
                System.out.println("\nNo existe ningún detalle de carrito registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Ingrese valores numéricos válidos en los campos correspondientes.");
        }

        teclado.close();
    }
}