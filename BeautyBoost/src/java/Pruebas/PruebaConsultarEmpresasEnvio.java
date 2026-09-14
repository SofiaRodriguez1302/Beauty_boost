package Pruebas;

import Controlador.EmpresasEnvioDAO;
import Modelo.EmpresasEnvio;
import java.util.Scanner;

public class PruebaConsultarEmpresasEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EmpresasEnvioDAO empresaDAO = new EmpresasEnvioDAO();

        System.out.print("Ingrese el ID de la empresa de envío a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar la empresa de envío por su ID
            EmpresasEnvio emp = empresaDAO.consultarEmpresa(idBusqueda);

            if (emp != null) {
                System.out.println("\nEmpresa de envío encontrada:");
                System.out.println("ID Empresa Envío: " + emp.getIdEmpresaEnvio());
                System.out.println("Nombre Empresa: " + emp.getNombreEmpresa());
                System.out.println("NIT: " + emp.getNit());
            } else {
                System.out.println("\nNo existe ninguna empresa de envío registrada con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}