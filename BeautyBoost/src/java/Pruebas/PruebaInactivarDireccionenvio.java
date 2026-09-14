package Pruebas;

import Controlador.DireccionEnvioDAO;
import Modelo.DireccionEnvio;
import java.util.Scanner;

public class PruebaInactivarDireccionenvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DireccionEnvioDAO direccionDAO = new DireccionEnvioDAO();

        System.out.print("Ingrese el ID de la dirección de envío a inactivar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la dirección de envío existe
            DireccionEnvio deExistente = direccionDAO.consultarDireccion(idBusqueda);

            if (deExistente != null) {
                System.out.println("\nDirección de envío encontrada:");
                System.out.println("ID Dirección: " + deExistente.getIdDireccion());
                System.out.println("Dirección: " + deExistente.getDireccionEnvio());
                System.out.println("ID Usuario: " + deExistente.getUsuarioIdUsuario());
                System.out.println("ID Ciudad: " + deExistente.getCiudadesIdCiudades());

                // 2. Procedemos a inactivarla
                System.out.println("\nProcesando inactivación...");
                boolean inactivado = direccionDAO.inactivarDireccion(idBusqueda);

                if (inactivado) {
                    System.out.println("¡La dirección de envío ha sido inactivada con éxito!");
                } else {
                    System.out.println("No se pudo inactivar la dirección de envío.");
                }

            } else {
                System.out.println("\nNo existe ninguna dirección de envío registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}