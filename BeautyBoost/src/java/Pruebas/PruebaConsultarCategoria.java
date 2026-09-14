package Pruebas;

import Controlador.CategoriaDAO;
import Modelo.Categoria;
import java.util.Scanner;

public class PruebaConsultarCategoria {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        System.out.print("Ingrese el ID de la categoría a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar la categoría por su ID
            Categoria cat = categoriaDAO.consultarCategoria(idBusqueda);

            if (cat != null) {
                System.out.println("\nCategoría encontrada:");
                System.out.println("ID Categoría: " + cat.getIdCategoria());
                System.out.println("Nombre Categoría: " + cat.getNombreCategoria());
            } else {
                System.out.println("\nNo existe ninguna categoría registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}