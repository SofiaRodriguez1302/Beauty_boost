package Pruebas;

import Controlador.TipoDocumentoDAO;
import Modelo.TipoDocumento;
import java.util.Scanner;

public class PruebaEliminarTipoDocumento {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();

        System.out.print("Ingrese el ID del tipo de documento a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el registro existe previamente
            TipoDocumento tipoExistente = tipoDocumentoDAO.consultarTipoDoc(idBusqueda);

            if (tipoExistente != null) {
                System.out.println("\nTipo de documento encontrado:");
                System.out.println("ID: " + tipoExistente.getIdTipoDocumento());
                System.out.println("Descripción: " + tipoExistente.getDescripcionTipoDocumento());

                System.out.print("\n¿Está seguro de eliminar este tipo de documento? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = tipoDocumentoDAO.eliminarTipoDoc(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Tipo de documento eliminado exitosamente!");
                    } else {
                        System.out.println("No se pudo eliminar el tipo de documento.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
                }

            } else {
                System.out.println("\nNo existe ningún tipo de documento con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}