package Pruebas;

import Controlador.TipoUsuarioDAO;
import Modelo.TipoUsuario;
import java.util.Scanner;

public class PruebaActualizarTipoUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();

        System.out.print("Ingrese el ID del tipo de usuario a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Buscamos primero si el registro existe
            TipoUsuario tipoExistente = tipoUsuarioDAO.consultarTipoUsuario(idBusqueda);

            if (tipoExistente != null) {
                System.out.println("\nRegistro encontrado:");
                System.out.println("ID: " + tipoExistente.getIdTipoUsuario());
                System.out.println("Nombre Actual: " + tipoExistente.getNombreTipoUsuario());

                // 2. Solicitamos los nuevos datos
                System.out.print("\nIngrese el nuevo Nombre / Rol para este tipo de usuario: ");
                String nuevoNombre = teclado.nextLine().trim();

                if (!nuevoNombre.isEmpty()) {
                    tipoExistente.setNombreTipoUsuario(nuevoNombre);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = tipoUsuarioDAO.actualizarTipoUsuario(tipoExistente);

                    if (actualizado) {
                        System.out.println("¡Tipo de usuario actualizado con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar el tipo de usuario.");
                    }
                } else {
                    System.out.println("Error: El nombre no puede estar vacío.");
                }

            } else {
                System.out.println("\nNo existe ningún tipo de usuario con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}