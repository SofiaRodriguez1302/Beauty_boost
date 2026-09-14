package Pruebas;

import Controlador.EmpresasEnvioDAO;
import Modelo.EmpresasEnvio;
import java.util.Scanner;

public class PruebaEliminarEmpresasEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EmpresasEnvioDAO empresaDAO = new EmpresasEnvioDAO();

        System.out.print("Ingrese el ID de la empresa de envío a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la empresa de envío existe en la base de datos
            EmpresasEnvio empExistente = empresaDAO.consultarEmpresa(idBusqueda);

            if (empExistente != null) {
                System.out.println("\nEmpresa de envío encontrada:");
                System.out.println("ID Empresa Envío: " + empExistente.getIdEmpresaEnvio());
                System.out.println("Nombre Empresa: " + empExistente.getNombreEmpresa());
                System.out.println("NIT: " + empExistente.getNit());

                // 2. Confirmación antes de eliminar
                System.out.print("\n¿Está seguro de eliminar esta empresa de envío? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = empresaDAO.eliminarEmpresa(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Empresa de envío eliminada exitosamente!");
                    } else {
                        System.out.println("No se pudo eliminar la empresa de envío.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
                }

            } else {
                System.out.println("\nNo existe ninguna empresa de envío registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}