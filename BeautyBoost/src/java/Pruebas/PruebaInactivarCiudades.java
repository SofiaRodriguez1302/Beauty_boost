package Pruebas;

import Controlador.CiudadesDAO;
import Modelo.Ciudades;
import java.util.Scanner;

public class PruebaInactivarCiudades {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CiudadesDAO ciudadesDAO = new CiudadesDAO();

        System.out.print("Ingrese el ID de la ciudad a inactivar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la ciudad existe
            Ciudades cidExistente = ciudadesDAO.consultarCiudad(idBusqueda);

            if (cidExistente != null) {
                System.out.println("\nCiudad encontrada:");
                System.out.println("ID Ciudad: " + cidExistente.getIdCiudades());
                System.out.println("Descripción: " + cidExistente.getDescripcionCiudad());
                System.out.println("Código Postal: " + cidExistente.getCodigoPostal());

                // 2. Procedemos a inactivarla
                System.out.println("\nProcesando inactivación...");
                boolean inactivado = ciudadesDAO.inactivarCiudad(idBusqueda);

                if (inactivado) {
                    System.out.println("¡La ciudad ha sido inactivada con éxito!");
                } else {
                    System.out.println("No se pudo inactivar la ciudad.");
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