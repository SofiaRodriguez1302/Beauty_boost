package Pruebas;

import Controlador.TipoDocumentoDAO;
import Modelo.TipoDocumento;
import java.util.Scanner;

public class PruebaConsultarTipoDocumento {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();

        System.out.print("Ingrese el ID del tipo de documento a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el tipo de documento por su ID
            TipoDocumento tipoDoc = tipoDocumentoDAO.consultarTipoDoc(idBusqueda);

            if (tipoDoc != null) {
                System.out.println("\nTipo de Documento encontrado:");
                System.out.println("ID: " + tipoDoc.getIdTipoDocumento());
                System.out.println("Descripción: " + tipoDoc.getDescripcionTipoDocumento());
            } else {
                System.out.println("\nNo existe ningún tipo de documento registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}