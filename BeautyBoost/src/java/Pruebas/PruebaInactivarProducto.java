package Pruebas;

import Controlador.ProductoDAO;
import Modelo.Producto;
import java.util.Scanner;

public class PruebaInactivarProducto {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ProductoDAO productoDAO = new ProductoDAO();

        System.out.print("Ingrese el ID del producto a inactivar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el producto existe en la base de datos
            Producto prodExistente = productoDAO.consultarProducto(idBusqueda);

            if (prodExistente != null) {
                System.out.println("\nProducto encontrado:");
                System.out.println("ID: " + prodExistente.getIdProducto());
                System.out.println("Nombre: " + prodExistente.getNombreProd());
                System.out.println("Precio: $" + prodExistente.getPrecio());

                // 2. Procedemos a inactivarlo
                System.out.println("\nProcesando inactivación...");
                boolean inactivado = productoDAO.inactivarProducto(idBusqueda);

                if (inactivado) {
                    System.out.println("¡El producto ha sido inactivado con éxito!");
                } else {
                    System.out.println("No se pudo inactivar el producto.");
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