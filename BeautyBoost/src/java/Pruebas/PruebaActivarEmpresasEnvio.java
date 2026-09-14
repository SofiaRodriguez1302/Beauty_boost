package Pruebas;

import Controlador.EmpresasEnvioDAO;
import Modelo.EmpresasEnvio;
import java.util.Scanner;

public class PruebaActivarEmpresasEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EmpresasEnvioDAO empresaDAO = new EmpresasEnvioDAO();

        System.out.print("Ingrese el ID de la empresa de envío a activar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si la empresa de envío existe en la base de datos
            EmpresasEnvio empExistente = empresaDAO.consultarEmpresa(idBusqueda);

            if (empExistente != null) {
                System.out.println("\nEmpresa de envío encontrada:");
                System.out.println("ID Empresa Envío: " + empExistente.getIdEmpresaEnvio());
                System.out.println("Nombre Empresa: " + empExistente.getNombreEmpresa());
                System.out.println("NIT: " + empExistente.getNit());

                // 2. Procedemos a activarla
                System.out.println("\nProcesando activación...");
                boolean activado = empresaDAO.activarEmpresa(idBusqueda);

                if (activado) {
                    System.out.println("¡La empresa de envío ha sido activada con éxito!");
                } else {
                    System.out.println("No se pudo activar la empresa de envío.");
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