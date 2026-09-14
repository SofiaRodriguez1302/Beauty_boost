package Pruebas;

import Controlador.TipoDocumentoDAO;
import Modelo.TipoDocumento;
import java.util.Scanner;

public class PruebaActualizarTipoDocumento {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();

        System.out.print("Ingrese el ID del tipo de documento a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el registro existe previamente
            TipoDocumento tipoExistente = tipoDocumentoDAO.consultarTipoDoc(idBusqueda);

            if (tipoExistente != null) {
                System.out.println("\nRegistro encontrado:");
                System.out.println("ID: " + tipoExistente.getIdTipoDocumento());
                System.out.println("Descripción Actual: " + tipoExistente.getDescripcionTipoDocumento());

                // 2. Solicitamos los nuevos datos
                System.out.print("\nIngrese la nueva descripción para este tipo de documento: ");
                String nuevaDescripcion = teclado.nextLine().trim();

                if (!nuevaDescripcion.isEmpty()) {
                    tipoExistente.setDescripcionTipoDocumento(nuevaDescripcion);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = tipoDocumentoDAO.actualizarTipoDoc(tipoExistente);

                    if (actualizado) {
                        System.out.println("¡Tipo de documento actualizado con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar el tipo de documento.");
                    }
                } else {
                    System.out.println("\nError: La descripción no puede estar vacía.");
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