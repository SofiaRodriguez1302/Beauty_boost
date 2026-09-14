package Pruebas;

import Controlador.DetalleCarritoDAO;
import Modelo.DetalleCarrito;
import java.util.Scanner;

public class PruebaEliminarDetalleCarrito {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DetalleCarritoDAO detalleDAO = new DetalleCarritoDAO();

        System.out.print("Ingrese el ID del detalle de carrito a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el detalle de carrito existe
            DetalleCarrito dcExistente = detalleDAO.consultarDetalle(idBusqueda);

            if (dcExistente != null) {
                System.out.println("\nDetalle de carrito encontrado:");
                System.out.println("ID Detalle: " + dcExistente.getIdDetalleCarrito());
                System.out.println("Cantidad: " + dcExistente.getCantidad());
                System.out.println("Descripción: " + dcExistente.getDescripcion());
                System.out.println("ID Carrito: " + dcExistente.getCarritoIdCarrito());
                System.out.println("ID Producto: " + dcExistente.getProductoIdProducto());

                // 2. Confirmación previa antes de eliminar
                System.out.print("\n¿Está seguro de eliminar este detalle de carrito? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = detalleDAO.eliminarDetalle(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Detalle de carrito eliminado con éxito!");
                    } else {
                        System.out.println("No se pudo eliminar el detalle de carrito.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
                }

            } else {
                System.out.println("\nNo existe ningún detalle de carrito registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}