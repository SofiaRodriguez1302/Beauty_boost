package Pruebas;

import Controlador.DireccionEnvioDAO;
import Modelo.DireccionEnvio;
import java.util.Scanner;

public class PruebaEliminarDireccionEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DireccionEnvioDAO direccionDAO = new DireccionEnvioDAO();

        System.out.print("Ingrese el ID de la dirección de envío a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la dirección existe en la base de datos
            DireccionEnvio deExistente = direccionDAO.consultarDireccion(idBusqueda);

            if (deExistente != null) {
                System.out.println("\nDirección de envío encontrada:");
                System.out.println("ID Dirección: " + deExistente.getIdDireccion());
                System.out.println("Dirección: " + deExistente.getDireccionEnvio());
                System.out.println("ID Usuario: " + deExistente.getUsuarioIdUsuario());
                System.out.println("ID Ciudad: " + deExistente.getCiudadesIdCiudades());

                // 2. Confirmación previa antes de eliminar
                System.out.print("\n¿Está seguro de eliminar esta dirección de envío? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = direccionDAO.eliminarDireccion(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Dirección de envío eliminada exitosamente!");
                    } else {
                        System.out.println("No se pudo eliminar la dirección de envío.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
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