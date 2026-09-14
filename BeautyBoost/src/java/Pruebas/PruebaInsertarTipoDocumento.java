package Pruebas;

import Controlador.TipoDocumentoDAO;
import Modelo.TipoDocumento;
import java.util.Scanner;

public class PruebaInsertarTipoDocumento {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();
        TipoDocumento nuevoTipoDoc = new TipoDocumento();

        System.out.print("Ingrese la descripción del Tipo de Documento: ");
        String descripcion = teclado.nextLine().trim();

        if (!descripcion.isEmpty()) {
            nuevoTipoDoc.setDescripcionTipoDocumento(descripcion);

            System.out.println("\nProcesando inserción...");
            boolean exito = tipoDocumentoDAO.insertarTipoDoc(nuevoTipoDoc);

            if (exito) {
                System.out.println("¡Tipo de documento '" + descripcion + "' registrado con éxito!");
            } else {
                System.out.println("No se pudo registrar el tipo de documento.");
            }
        } else {
            System.out.println("\nError: La descripción del tipo de documento no puede estar vacía.");
        }

        teclado.close();
    }
}