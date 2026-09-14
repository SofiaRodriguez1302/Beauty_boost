package Pruebas;

import Controlador.ProductoDAO;
import Modelo.Producto;
import java.util.Scanner;

public class PruebaInsertarProducto {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ProductoDAO productoDAO = new ProductoDAO();
        Producto nuevoProducto = new Producto();

        try {
            System.out.print("Ingrese el nombre del producto: ");
            String nombre = teclado.nextLine().trim();

            System.out.print("Ingrese la descripción del producto: ");
            String descripcion = teclado.nextLine().trim();

            System.out.print("Ingrese el precio: ");
            double precio = Double.parseDouble(teclado.nextLine());

            System.out.print("Ingrese la cantidad en stock: ");
            int stock = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el ID de la categoría asociada: ");
            int idCategoria = Integer.parseInt(teclado.nextLine());

            if (!nombre.isEmpty()) {
                nuevoProducto.setNombreProd(nombre);
                nuevoProducto.setDescripcionProd(descripcion);
                nuevoProducto.setPrecio(precio);
                nuevoProducto.setStock(stock);
                nuevoProducto.setCategoriaIdCategoria(idCategoria);

                System.out.println("\nProcesando inserción...");
                boolean exito = productoDAO.insertarProducto(nuevoProducto);

                if (exito) {
                    System.out.println("¡Producto registrado con éxito!");
                } else {
                    System.out.println("No se pudo registrar el producto.");
                }
            } else {
                System.out.println("\nError: El nombre del producto no puede estar vacío.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar valores numéricos válidos para precio, stock e ID de categoría.");
        }

        teclado.close();
    }
}