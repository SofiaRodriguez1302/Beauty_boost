package Pruebas;

import Controlador.CategoriaDAO;
import Modelo.Categoria;
import java.util.Scanner;

public class PruebaInsertarCategoria {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Categoria nuevaCategoria = new Categoria();

        try {
            System.out.print("Ingrese el nombre de la categoría: ");
            String nombreCat = teclado.nextLine().trim();

            if (!nombreCat.isEmpty()) {
                nuevaCategoria.setNombreCategoria(nombreCat);

                System.out.println("\nProcesando inserción...");
                boolean exito = categoriaDAO.insertarCategoria(nuevaCategoria);

                if (exito) {
                    System.out.println("¡Categoría registrada con éxito!");
                } else {
                    System.out.println("No se pudo registrar la categoría.");
                }
            } else {
                System.out.println("\nError: El nombre de la categoría no puede estar vacío.");
            }

        } catch (Exception e) {
            System.out.println("\nError al procesar los datos: " + e.getMessage());
        }

        teclado.close();
    }
}