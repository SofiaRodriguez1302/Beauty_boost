package Pruebas;

import Controlador.TipoUsuarioDAO;
import Modelo.TipoUsuario;
import java.util.Scanner;

public class PruebaConsultarTipoUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();

        System.out.print("Ingrese el ID del tipo de usuario a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el tipo de usuario por su ID
            TipoUsuario tipoUsuario = tipoUsuarioDAO.consultarTipoUsuario(idBusqueda);

            if (tipoUsuario != null) {
                System.out.println("\nTipo de Usuario encontrado:");
                System.out.println("ID: " + tipoUsuario.getIdTipoUsuario());
                System.out.println("Nombre / Rol: " + tipoUsuario.getNombreTipoUsuario());
            } else {
                System.out.println("\nNo existe ningún tipo de usuario con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}