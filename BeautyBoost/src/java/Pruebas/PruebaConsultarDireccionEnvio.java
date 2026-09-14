package Pruebas;

import Controlador.DireccionEnvioDAO;
import Modelo.DireccionEnvio;
import java.util.Scanner;

public class PruebaConsultarDireccionEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DireccionEnvioDAO direccionDAO = new DireccionEnvioDAO();

        System.out.print("Ingrese el ID de la dirección de envío a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar la dirección de envío por su ID
            DireccionEnvio de = direccionDAO.consultarDireccion(idBusqueda);

            if (de != null) {
                System.out.println("\nDirección de envío encontrada:");
                System.out.println("ID Dirección: " + de.getIdDireccion());
                System.out.println("Dirección: " + de.getDireccionEnvio());
                System.out.println("ID Usuario: " + de.getUsuarioIdUsuario());
                System.out.println("ID Ciudad: " + de.getCiudadesIdCiudades());
            } else {
                System.out.println("\nNo existe ninguna dirección de envío registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}