package Pruebas;

import Controlador.EmpresasEnvioDAO;
import Modelo.EmpresasEnvio;
import java.util.Scanner;

public class PruebaActualizarEmpresasEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EmpresasEnvioDAO empresaDAO = new EmpresasEnvioDAO();

        System.out.print("Ingrese el ID de la empresa de envío a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la empresa de envío existe
            EmpresasEnvio empExistente = empresaDAO.consultarEmpresa(idBusqueda);

            if (empExistente != null) {
                System.out.println("\nEmpresa de envío encontrada:");
                System.out.println("ID Empresa Envío: " + empExistente.getIdEmpresaEnvio());
                System.out.println("Nombre Actual: " + empExistente.getNombreEmpresa());
                System.out.println("NIT Actual: " + empExistente.getNit());

                // 2. Solicitamos los nuevos datos
                System.out.print("\nIngrese el nuevo nombre de la empresa: ");
                String nuevoNombre = teclado.nextLine().trim();

                System.out.print("Ingrese el nuevo NIT: ");
                String nuevoNit = teclado.nextLine().trim();

                if (!nuevoNombre.isEmpty() && !nuevoNit.isEmpty()) {
                    empExistente.setNombreEmpresa(nuevoNombre);
                    empExistente.setNit(nuevoNit);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = empresaDAO.actualizarEmpresa(empExistente);

                    if (actualizado) {
                        System.out.println("¡Empresa de envío actualizada con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar la empresa de envío.");
                    }
                } else {
                    System.out.println("\nError: El nombre y el NIT no pueden estar vacíos.");
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