package Pruebas;

import Controlador.CiudadesDAO;
import Modelo.Ciudades;
import java.util.Scanner;

public class PruebaInsertarCiudades {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CiudadesDAO ciudadesDAO = new CiudadesDAO();
        Ciudades nuevaCiudad = new Ciudades();

        try {
            System.out.print("Ingrese el nombre/descripción de la ciudad: ");
            String descripcion = teclado.nextLine().trim();

            System.out.print("Ingrese el código postal: ");
            String codigoPostal = teclado.nextLine().trim();

            if (!descripcion.isEmpty()) {
                nuevaCiudad.setDescripcionCiudad(descripcion);
                nuevaCiudad.setCodigoPostal(codigoPostal);

                System.out.println("\nProcesando inserción...");
                boolean exito = ciudadesDAO.insertarCiudad(nuevaCiudad);

                if (exito) {
                    System.out.println("¡Ciudad registrada con éxito!");
                } else {
                    System.out.println("No se pudo registrar la ciudad.");
                }
            } else {
                System.out.println("\nError: La descripción de la ciudad no puede estar vacía.");
            }

        } catch (Exception e) {
            System.out.println("\nError al procesar los datos: " + e.getMessage());
        }

        teclado.close();
    }
}