package Pruebas;

import Controlador.DireccionEnvioDAO;
import Modelo.DireccionEnvio;
import java.util.Scanner;

public class PruebaInsertarDireccionEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DireccionEnvioDAO direccionDAO = new DireccionEnvioDAO();
        DireccionEnvio nuevaDireccion = new DireccionEnvio();

        try {
            System.out.print("Ingrese la dirección de envío: ");
            String direccion = teclado.nextLine().trim();

            System.out.print("Ingrese el ID del usuario: ");
            int idUsuario = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el ID de la ciudad: ");
            int idCiudad = Integer.parseInt(teclado.nextLine());

            if (!direccion.isEmpty()) {
                nuevaDireccion.setDireccionEnvio(direccion);
                nuevaDireccion.setUsuarioIdUsuario(idUsuario);
                nuevaDireccion.setCiudadesIdCiudades(idCiudad);

                System.out.println("\nProcesando inserción...");
                boolean exito = direccionDAO.insertarDireccion(nuevaDireccion);

                if (exito) {
                    System.out.println("¡Dirección de envío registrada con éxito!");
                } else {
                    System.out.println("No se pudo registrar la dirección de envío.");
                }
            } else {
                System.out.println("\nError: La dirección no puede estar vacía.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Los ID de usuario y ciudad deben ser números enteros válidos.");
        }

        teclado.close();
    }
}