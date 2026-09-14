package Pruebas;

import Controlador.CabezaCarritoDAO;
import Modelo.CabezaCarrito;
import java.util.Date;
import java.util.Scanner;

public class PruebaActualizarCabezaCarrito {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CabezaCarritoDAO carritoDAO = new CabezaCarritoDAO();

        System.out.print("Ingrese el ID del carrito a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificar si el carrito existe
            CabezaCarrito ccExistente = carritoDAO.consultarCarrito(idBusqueda);

            if (ccExistente != null) {
                System.out.println("\nCarrito encontrado:");
                System.out.println("ID Carrito: " + ccExistente.getIdCarrito());
                System.out.println("Fecha Creación: " + ccExistente.getFechaCreacion());
                System.out.println("Fecha Actualización Anterior: " + ccExistente.getFechaActualizacion());

                // 2. Actualizar la fecha de modificación al momento actual
                Date fechaActual = new Date();
                ccExistente.setFechaActualizacion(fechaActual);

                System.out.println("\nProcesando actualización...");
                boolean actualizado = carritoDAO.actualizarCarrito(ccExistente);

                if (actualizado) {
                    System.out.println("¡Carrito actualizado con éxito!");
                    System.out.println("Nueva Fecha Actualización: " + fechaActual);
                } else {
                    System.out.println("No se pudo actualizar el carrito.");
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