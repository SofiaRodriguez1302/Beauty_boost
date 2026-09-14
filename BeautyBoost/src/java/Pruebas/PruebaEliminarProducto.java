package Pruebas;

import Controlador.ProductoDAO;
import Modelo.Producto;
import java.util.Scanner;

public class PruebaEliminarProducto {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ProductoDAO productoDAO = new ProductoDAO();

        System.out.print("Ingrese el ID del producto a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el producto existe en la base de datos
            Producto prodExistente = productoDAO.consultarProducto(idBusqueda);

            if (prodExistente != null) {
                System.out.println("\nProducto encontrado:");
                System.out.println("ID: " + prodExistente.getIdProducto());
                System.out.println("Nombre: " + prodExistente.getNombreProd());
                System.out.println("Precio: $" + prodExistente.getPrecio());

                System.out.print("\n¿Está seguro de eliminar este producto? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = productoDAO.eliminarProducto(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Producto eliminado exitosamente!");
                    } else {
                        System.out.println("No se pudo eliminar el producto.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
                }

            } else {
                System.out.println("\nNo existe ningún producto registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}