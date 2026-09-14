package Pruebas;

import Controlador.CategoriaDAO;
import Modelo.Categoria;
import java.util.Scanner;

public class PruebaActivarCategoria {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        System.out.print("Ingrese el ID de la categoría a activar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la categoría existe
            Categoria catExistente = categoriaDAO.consultarCategoria(idBusqueda);

            if (catExistente != null) {
                System.out.println("\nCategoría encontrada:");
                System.out.println("ID Categoría: " + catExistente.getIdCategoria());
                System.out.println("Nombre Categoría: " + catExistente.getNombreCategoria());

                // 2. Procedemos a activarla
                System.out.println("\nProcesando activación...");
                boolean activado = categoriaDAO.activarCategoria(idBusqueda);

                if (activado) {
                    System.out.println("¡La categoría ha sido activada con éxito!");
                } else {
                    System.out.println("No se pudo activar la categoría.");
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