package Pruebas;

import Controlador.DireccionEnvioDAO;
import Modelo.DireccionEnvio;
import java.util.Scanner;

public class PruebaActualizarDireccionEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DireccionEnvioDAO direccionDAO = new DireccionEnvioDAO();

        System.out.print("Ingrese el ID de la dirección de envío a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la dirección de envío existe
            DireccionEnvio deExistente = direccionDAO.consultarDireccion(idBusqueda);

            if (deExistente != null) {
                System.out.println("\nDirección de envío encontrada:");
                System.out.println("ID Dirección: " + deExistente.getIdDireccion());
                System.out.println("Dirección Actual: " + deExistente.getDireccionEnvio());
                System.out.println("ID Usuario Actual: " + deExistente.getUsuarioIdUsuario());
                System.out.println("ID Ciudad Actual: " + deExistente.getCiudadesIdCiudades());

                // 2. Solicitamos los nuevos datos
                System.out.print("\nIngrese la nueva dirección de envío: ");
                String nuevaDireccion = teclado.nextLine().trim();

                System.out.print("Ingrese el nuevo ID del usuario: ");
                int nuevoIdUsuario = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID de la ciudad: ");
                int nuevoIdCiudad = Integer.parseInt(teclado.nextLine());

                if (!nuevaDireccion.isEmpty()) {
                    deExistente.setDireccionEnvio(nuevaDireccion);
                    deExistente.setUsuarioIdUsuario(nuevoIdUsuario);
                    deExistente.setCiudadesIdCiudades(nuevoIdCiudad);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = direccionDAO.actualizarDireccion(deExistente);

                    if (actualizado) {
                        System.out.println("¡Dirección de envío actualizada con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar la dirección de envío.");
                    }
                } else {
                    System.out.println("\nError: La dirección no puede estar vacía.");
                }

            } else {
                System.out.println("\nNo existe ninguna dirección de envío registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar números enteros válidos para los campos de ID.");
        }

        teclado.close();
    }
}