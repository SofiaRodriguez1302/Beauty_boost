package Pruebas;

import Controlador.CategoriaDAO;
import Modelo.Categoria;
import java.util.Scanner;

public class PruebaActualizarCategoria {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        System.out.print("Ingrese el ID de la categoría a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificar si la categoría existe
            Categoria catExistente = categoriaDAO.consultarCategoria(idBusqueda);

            if (catExistente != null) {
                System.out.println("\nCategoría encontrada:");
                System.out.println("ID Categoría: " + catExistente.getIdCategoria());
                System.out.println("Nombre Actual: " + catExistente.getNombreCategoria());

                // 2. Pedir el nuevo nombre
                System.out.print("\nIngrese el nuevo nombre de la categoría: ");
                String nuevoNombre = teclado.nextLine().trim();

                if (!nuevoNombre.isEmpty()) {
                    catExistente.setNombreCategoria(nuevoNombre);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = categoriaDAO.actualizarCategoria(catExistente);

                    if (actualizado) {
                        System.out.println("¡Categoría actualizada con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar la categoría.");
                    }
                } else {
                    System.out.println("\nError: El nombre de la categoría no puede estar vacío.");
                }

            } else {
                System.out.println("\nNo existe ninguna categoría registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}