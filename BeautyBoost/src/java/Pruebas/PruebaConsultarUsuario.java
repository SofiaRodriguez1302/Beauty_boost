package Pruebas;

import Controlador.UsuarioDAO;
import Modelo.Usuario;
import java.util.List;
import java.util.Scanner;

public class PruebaConsultarUsuario {

    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("1. Consultar TODOS los usuarios");
            System.out.println("2. Consultar usuario por ID");
            System.out.println("3. Consultar usuario por CORREO");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    List<Usuario> lista = usuarioDAO.consultarUsuario();

                    if (lista != null && !lista.isEmpty()) {
                        for (Usuario u : lista) {
                            System.out.println("ID: " + u.getIdUsuario()
                                    + " | Nombre: " + u.getNombre() + " " + u.getApellido()
                                    + " | Correo: " + u.getCorreo()
                                    + " | Autorizacion: " + u.getAutorizacionDatos());
                        }
                    } else {
                        System.out.println("No se encontraron usuarios registrados.");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese el ID del usuario a buscar: ");
                    try {
                        int idBusqueda = Integer.parseInt(scanner.nextLine());
                        Usuario usuarioPorId = usuarioDAO.consultarUsuario(idBusqueda);

                        if (usuarioPorId != null) {
                            System.out.println("\nUsuario encontrado:");
                            System.out.println("ID: " + usuarioPorId.getIdUsuario());
                            System.out.println("Nombre: " + usuarioPorId.getNombre() + " " + usuarioPorId.getApellido());
                            System.out.println("Documento: " + usuarioPorId.getNumeroIdentificacion());
                            System.out.println("Telefono: " + usuarioPorId.getTelefono());
                            System.out.println("Correo: " + usuarioPorId.getCorreo());
                            System.out.println("Fecha Nacimiento: " + usuarioPorId.getFechaNacimiento());
                        } else {
                            System.out.println("No existe un usuario con el ID: " + idBusqueda);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: El ID debe ser un numero entero valido.");
                    }
                    break;

                case 3:

                    System.out.print("Ingrese el correo del usuario a buscar: ");
                    String correoBusqueda = scanner.nextLine().trim();

                    Usuario usuarioPorCorreo = usuarioDAO.consultarUsuario(correoBusqueda);

                    if (usuarioPorCorreo != null) {

                        System.out.println("ID: " + usuarioPorCorreo.getIdUsuario());
                        System.out.println("Nombre: " + usuarioPorCorreo.getNombre() + " " + usuarioPorCorreo.getApellido());
                        System.out.println("Correo: " + usuarioPorCorreo.getCorreo());
                        System.out.println("Tipo Usuario ID: " + usuarioPorCorreo.getTipoUsuarioIdTipoUsuario());
                    } else {
                        System.out.println("No existe un usuario registrado con el correo: " + correoBusqueda);
                    }
                    break;

                case 4:
                    System.out.println("\nSaliendo del sistema de consultas...");
                    break;

                default:
                    System.out.println("\nOpcion no valida. Intente de nuevo.");
                    break;
            }
        } while (opcion != 4);

        scanner.close();
    }
}