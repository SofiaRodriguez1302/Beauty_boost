package Pruebas;

import Controlador.ProductoDAO;
import Modelo.Producto;
import java.util.Scanner;

public class PruebaActualizarProducto {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ProductoDAO productoDAO = new ProductoDAO();

        System.out.print("Ingrese el ID del producto a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el producto existe
            Producto prodExistente = productoDAO.consultarProducto(idBusqueda);

            if (prodExistente != null) {
                System.out.println("\nProducto encontrado:");
                System.out.println("ID: " + prodExistente.getIdProducto());
                System.out.println("Nombre Actual: " + prodExistente.getNombreProd());
                System.out.println("Descripción Actual: " + prodExistente.getDescripcionProd());
                System.out.println("Precio Actual: $" + prodExistente.getPrecio());
                System.out.println("Stock Actual: " + prodExistente.getStock());
                System.out.println("ID Categoría Actual: " + prodExistente.getCategoriaIdCategoria());

                // 2. Solicitamos los nuevos datos
                System.out.print("\nIngrese el nuevo nombre del producto: ");
                String nuevoNombre = teclado.nextLine().trim();

                System.out.print("Ingrese la nueva descripción: ");
                String nuevaDescripcion = teclado.nextLine().trim();

                System.out.print("Ingrese el nuevo precio: ");
                double nuevoPrecio = Double.parseDouble(teclado.nextLine());

                System.out.print("Ingrese la nueva cantidad en stock: ");
                int nuevoStock = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID de la categoría: ");
                int nuevoIdCategoria = Integer.parseInt(teclado.nextLine());

                if (!nuevoNombre.isEmpty()) {
                    prodExistente.setNombreProd(nuevoNombre);
                    prodExistente.setDescripcionProd(nuevaDescripcion);
                    prodExistente.setPrecio(nuevoPrecio);
                    prodExistente.setStock(nuevoStock);
                    prodExistente.setCategoriaIdCategoria(nuevoIdCategoria);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = productoDAO.actualizarProducto(prodExistente);

                    if (actualizado) {
                        System.out.println("¡Producto actualizado con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar el producto.");
                    }
                } else {
                    System.out.println("\nError: El nombre del producto no puede estar vacío.");
                }

            } else {
                System.out.println("\nNo existe ningún producto con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar valores numéricos válidos en los campos correspondientes.");
        }

        teclado.close();
    }
}