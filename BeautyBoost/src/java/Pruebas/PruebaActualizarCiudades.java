package Pruebas;

import Controlador.CiudadesDAO;
import Modelo.Ciudades;
import java.util.Scanner;

public class PruebaActualizarCiudades {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CiudadesDAO ciudadesDAO = new CiudadesDAO();

        System.out.print("Ingrese el ID de la ciudad a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificar si la ciudad existe
            Ciudades cidExistente = ciudadesDAO.consultarCiudad(idBusqueda);

            if (cidExistente != null) {
                System.out.println("\nCiudad encontrada:");
                System.out.println("ID Ciudad: " + cidExistente.getIdCiudades());
                System.out.println("Descripción Actual: " + cidExistente.getDescripcionCiudad());
                System.out.println("Código Postal Actual: " + cidExistente.getCodigoPostal());

                // 2. Pedir los nuevos datos
                System.out.print("\nIngrese la nueva descripción de la ciudad: ");
                String nuevaDescripcion = teclado.nextLine().trim();

                System.out.print("Ingrese el nuevo código postal: ");
                String nuevoCodigoPostal = teclado.nextLine().trim();

                if (!nuevaDescripcion.isEmpty()) {
                    cidExistente.setDescripcionCiudad(nuevaDescripcion);
                    cidExistente.setCodigoPostal(nuevoCodigoPostal);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = ciudadesDAO.actualizarCiudad(cidExistente);

                    if (actualizado) {
                        System.out.println("¡Ciudad actualizada con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar la ciudad.");
                    }
                } else {
                    System.out.println("\nError: La descripción de la ciudad no puede estar vacía.");
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