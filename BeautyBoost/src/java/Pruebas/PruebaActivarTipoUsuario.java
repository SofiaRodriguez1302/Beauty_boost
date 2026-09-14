package Pruebas;

import Controlador.TipoUsuarioDAO;
import Modelo.TipoUsuario;
import java.util.Scanner;

public class PruebaActivarTipoUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();

        System.out.print("Ingrese el ID del tipo de usuario a activar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el registro existe en la base de datos
            TipoUsuario tipoExistente = tipoUsuarioDAO.consultarTipoUsuario(idBusqueda);

            if (tipoExistente != null) {
                System.out.println("\nTipo de usuario encontrado:");
                System.out.println("ID: " + tipoExistente.getIdTipoUsuario());
                System.out.println("Nombre / Rol: " + tipoExistente.getNombreTipoUsuario());

                // 2. Procedemos a activarlo
                System.out.println("\nProcesando activación...");
                boolean activado = tipoUsuarioDAO.activarTipoUsuario(idBusqueda);

                if (activado) {
                    System.out.println("¡El tipo de usuario ha sido activado con éxito!");
                } else {
                    System.out.println("No se pudo activar el tipo de usuario.");
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