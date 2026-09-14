package Pruebas;

import Controlador.CiudadesDAO;
import Modelo.Ciudades;
import java.util.Scanner;

public class PruebaConsultarCiudades {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CiudadesDAO ciudadesDAO = new CiudadesDAO();

        System.out.print("Ingrese el ID de la ciudad a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar la ciudad por su ID
            Ciudades cid = ciudadesDAO.consultarCiudad(idBusqueda);

            if (cid != null) {
                System.out.println("\nCiudad encontrada:");
                System.out.println("ID Ciudad: " + cid.getIdCiudades());
                System.out.println("Descripción: " + cid.getDescripcionCiudad());
                System.out.println("Código Postal: " + cid.getCodigoPostal());
            } else {
                System.out.println("\nNo existe ninguna ciudad registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}