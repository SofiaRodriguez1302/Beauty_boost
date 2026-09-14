package Pruebas;

import Controlador.TipoUsuarioDAO;
import Modelo.TipoUsuario;
import java.util.Scanner;

public class PruebaInsertarTipoUsuario {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();
        TipoUsuario nuevoTipo = new TipoUsuario();

        System.out.print("Ingrese el Nombre del Tipo de Usuario / Rol: ");
        String nombreTipo = teclado.nextLine().trim();

        if (!nombreTipo.isEmpty()) {
            nuevoTipo.setNombreTipoUsuario(nombreTipo);

            System.out.println("\nProcesando inserción...");
            boolean exito = tipoUsuarioDAO.insertarTipoUsuario(nuevoTipo);

            if (exito) {
                System.out.println("¡Tipo de usuario '" + nombreTipo + "' registrado con éxito!");
            } else {
                System.out.println("No se pudo registrar el tipo de usuario.");
            }
        } else {
            System.out.println("\nError: El nombre del tipo de usuario no puede estar vacío.");
        }

        teclado.close();
    }
}