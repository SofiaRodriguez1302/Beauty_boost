package Pruebas;

import Controlador.TipoUsuarioDAO;
import Modelo.TipoUsuario;
import java.util.Scanner;

public class PruebaEliminarTipoUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();


        System.out.print("Ingrese el ID del tipo de usuario a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el registro existe
            TipoUsuario tipoExistente = tipoUsuarioDAO.consultarTipoUsuario(idBusqueda);

            if (tipoExistente != null) {
                System.out.println("\nTipo de usuario encontrado:");
                System.out.println("ID: " + tipoExistente.getIdTipoUsuario());
                System.out.println("Nombre / Rol: " + tipoExistente.getNombreTipoUsuario());

                System.out.print("\n¿Está seguro de eliminar este tipo de usuario? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = tipoUsuarioDAO.eliminarTipoUsuario(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Tipo de usuario eliminado exitosamente!");
                    } else {
                        System.out.println("No se pudo eliminar el tipo de usuario.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
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