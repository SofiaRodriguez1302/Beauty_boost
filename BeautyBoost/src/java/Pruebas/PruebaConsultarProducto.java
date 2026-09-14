package Pruebas;

import Controlador.ProductoDAO;
import Modelo.Producto;
import java.util.Scanner;

public class PruebaConsultarProducto {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ProductoDAO productoDAO = new ProductoDAO();

        System.out.print("Ingrese el ID del producto a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el producto por su ID
            Producto prod = productoDAO.consultarProducto(idBusqueda);

            if (prod != null) {
                System.out.println("\nProducto encontrado:");
                System.out.println("ID: " + prod.getIdProducto());
                System.out.println("Nombre: " + prod.getNombreProd());
                System.out.println("Descripción: " + prod.getDescripcionProd());
                System.out.println("Precio: $" + prod.getPrecio());
                System.out.println("Stock: " + prod.getStock());
                System.out.println("ID Categoria: " + prod.getCategoriaIdCategoria());
            } else {
                System.out.println("\nNo existe ningún producto registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}