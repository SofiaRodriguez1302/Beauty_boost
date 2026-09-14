package Pruebas;

import Controlador.CiudadesDAO;
import Modelo.Ciudades;
import java.util.Scanner;

public class PruebaEliminarCiudades {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CiudadesDAO ciudadesDAO = new CiudadesDAO();

        System.out.print("Ingrese el ID de la ciudad a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la ciudad existe
            Ciudades cidExistente = ciudadesDAO.consultarCiudad(idBusqueda);

            if (cidExistente != null) {
                System.out.println("\nCiudad encontrada:");
                System.out.println("ID Ciudad: " + cidExistente.getIdCiudades());
                System.out.println("Descripción: " + cidExistente.getDescripcionCiudad());
                System.out.println("Código Postal: " + cidExistente.getCodigoPostal());

                // 2. Confirmación previa antes de eliminar
                System.out.print("\n¿Está seguro de eliminar esta ciudad? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = ciudadesDAO.eliminarCiudad(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Ciudad eliminada con éxito!");
                    } else {
                        System.out.println("No se pudo eliminar la ciudad.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
                }

            } else {
                System.out.println("\nNo existe ninguna ciudad registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}