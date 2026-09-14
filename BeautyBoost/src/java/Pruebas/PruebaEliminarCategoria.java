package Pruebas;

import Controlador.CategoriaDAO;
import Modelo.Categoria;
import java.util.Scanner;

public class PruebaEliminarCategoria {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        System.out.print("Ingrese el ID de la categoría a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la categoría existe
            Categoria catExistente = categoriaDAO.consultarCategoria(idBusqueda);

            if (catExistente != null) {
                System.out.println("\nCategoría encontrada:");
                System.out.println("ID Categoría: " + catExistente.getIdCategoria());
                System.out.println("Nombre Categoría: " + catExistente.getNombreCategoria());

                // 2. Confirmación previa antes de eliminar
                System.out.print("\n¿Está seguro de eliminar esta categoría? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = categoriaDAO.eliminarCategoria(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Categoría eliminada con éxito!");
                    } else {
                        System.out.println("No se pudo eliminar la categoría.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
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