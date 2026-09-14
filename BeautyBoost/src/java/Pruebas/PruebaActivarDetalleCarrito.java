package Pruebas;

import Controlador.DetalleCarritoDAO;
import Modelo.DetalleCarrito;
import java.util.Scanner;

public class PruebaActivarDetalleCarrito {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DetalleCarritoDAO detalleDAO = new DetalleCarritoDAO();

        System.out.print("Ingrese el ID del detalle de carrito a activar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el detalle de carrito existe
            DetalleCarrito dcExistente = detalleDAO.consultarDetalle(idBusqueda);

            if (dcExistente != null) {
                System.out.println("\nDetalle de carrito encontrado:");
                System.out.println("ID Detalle: " + dcExistente.getIdDetalleCarrito());
                System.out.println("Cantidad: " + dcExistente.getCantidad());
                System.out.println("Descripción: " + dcExistente.getDescripcion());
                System.out.println("ID Carrito: " + dcExistente.getCarritoIdCarrito());
                System.out.println("ID Producto: " + dcExistente.getProductoIdProducto());

                // 2. Procedemos a activarlo
                System.out.println("\nProcesando activación...");
                boolean activado = detalleDAO.activarDetalle(idBusqueda);

                if (activado) {
                    System.out.println("¡El detalle de carrito ha sido activado con éxito!");
                } else {
                    System.out.println("No se pudo activar el detalle de carrito.");
                }

            } else {
                System.out.println("\nNo existe ningún detalle de carrito registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}