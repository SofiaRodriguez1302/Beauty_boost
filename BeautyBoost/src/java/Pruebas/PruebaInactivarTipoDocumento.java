package Pruebas;

import Controlador.TipoDocumentoDAO;
import Modelo.TipoDocumento;
import java.util.Scanner;

public class PruebaInactivarTipoDocumento {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();

        System.out.print("Ingrese el ID del tipo de documento a inactivar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el registro existe en la base de datos
            TipoDocumento tipoExistente = tipoDocumentoDAO.consultarTipoDoc(idBusqueda);

            if (tipoExistente != null) {
                System.out.println("\nTipo de documento encontrado:");
                System.out.println("ID: " + tipoExistente.getIdTipoDocumento());
                System.out.println("Descripción: " + tipoExistente.getDescripcionTipoDocumento());

                // 2. Procedemos a inactivarlo
                System.out.println("\nProcesando inactivación...");
                boolean inactivado = tipoDocumentoDAO.inactivarTipoDoc(idBusqueda);

                if (inactivado) {
                    System.out.println("¡El tipo de documento ha sido inactivado con éxito!");
                } else {
                    System.out.println("No se pudo inactivar el tipo de documento.");
                }

            } else {
                System.out.println("\nNo existe ningún tipo de documento registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}