package Pruebas;

import Controlador.EmpresasEnvioDAO;
import Modelo.EmpresasEnvio;
import java.util.Scanner;

public class PruebaInsertarEmpresasEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EmpresasEnvioDAO empresaDAO = new EmpresasEnvioDAO();
        EmpresasEnvio nuevaEmpresa = new EmpresasEnvio();

        System.out.print("Ingrese el nombre de la empresa de envío: ");
        String nombre = teclado.nextLine().trim();

        System.out.print("Ingrese el NIT de la empresa: ");
        String nit = teclado.nextLine().trim();

        if (!nombre.isEmpty() && !nit.isEmpty()) {
            nuevaEmpresa.setNombreEmpresa(nombre);
            nuevaEmpresa.setNit(nit);

            System.out.println("\nProcesando inserción...");
            boolean exito = empresaDAO.insertarEmpresa(nuevaEmpresa);

            if (exito) {
                System.out.println("¡Empresa de envío registrada con éxito!");
            } else {
                System.out.println("No se pudo registrar la empresa de envío.");
            }
        } else {
            System.out.println("\nError: El nombre y el NIT no pueden estar vacíos.");
        }

        teclado.close();
    }
}