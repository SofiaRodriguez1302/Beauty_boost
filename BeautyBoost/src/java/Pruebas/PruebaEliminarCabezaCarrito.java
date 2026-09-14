package Pruebas;

import Controlador.CabezaCarritoDAO;
import Modelo.CabezaCarrito;
import java.util.Scanner;

public class PruebaEliminarCabezaCarrito {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CabezaCarritoDAO carritoDAO = new CabezaCarritoDAO();

        System.out.print("Ingrese el ID del carrito a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el carrito existe
            CabezaCarrito ccExistente = carritoDAO.consultarCarrito(idBusqueda);

            if (ccExistente != null) {
                System.out.println("\nCarrito encontrado:");
                System.out.println("ID Carrito: " + ccExistente.getIdCarrito());
                System.out.println("Fecha Creación: " + ccExistente.getFechaCreacion());
                System.out.println("Fecha Actualización: " + ccExistente.getFechaActualizacion());

                // 2. Confirmación previa antes de eliminar
                System.out.print("\n¿Está seguro de eliminar este carrito? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = carritoDAO.eliminarCarrito(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Carrito eliminado con éxito!");
                    } else {
                        System.out.println("No se pudo eliminar el carrito.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
                }

            } else {
                System.out.println("\nNo existe ningún carrito registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}