package Pruebas;

import Controlador.CabezaCarritoDAO;
import Modelo.CabezaCarrito;
import java.util.Scanner;

public class PruebaActivarCabezaCarrito {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CabezaCarritoDAO carritoDAO = new CabezaCarritoDAO();

        System.out.print("Ingrese el ID del carrito a activar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el carrito existe
            CabezaCarrito ccExistente = carritoDAO.consultarCarrito(idBusqueda);

            if (ccExistente != null) {
                System.out.println("\nCarrito encontrado:");
                System.out.println("ID Carrito: " + ccExistente.getIdCarrito());
                System.out.println("Fecha Creación: " + ccExistente.getFechaCreacion());
                System.out.println("Fecha Actualización: " + ccExistente.getFechaActualizacion());

                // 2. Procedemos a activarlo
                System.out.println("\nProcesando activación...");
                boolean activado = carritoDAO.activarCarrito(idBusqueda);

                if (activado) {
                    System.out.println("¡El carrito ha sido activado con éxito!");
                } else {
                    System.out.println("No se pudo activar el carrito.");
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