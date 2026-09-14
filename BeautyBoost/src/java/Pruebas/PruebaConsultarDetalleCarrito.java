package Pruebas;

import Controlador.DetalleCarritoDAO;
import Modelo.DetalleCarrito;
import java.util.Scanner;

public class PruebaConsultarDetalleCarrito {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        DetalleCarritoDAO detalleDAO = new DetalleCarritoDAO();

        System.out.print("Ingrese el ID del detalle de carrito a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el detalle de carrito por su ID
            DetalleCarrito dc = detalleDAO.consultarDetalle(idBusqueda);

            if (dc != null) {
                System.out.println("\nDetalle de carrito encontrado:");
                System.out.println("ID Detalle Carrito: " + dc.getIdDetalleCarrito());
                System.out.println("Cantidad: " + dc.getCantidad());
                System.out.println("Descripción: " + dc.getDescripcion());
                System.out.println("ID Carrito: " + dc.getCarritoIdCarrito());
                System.out.println("ID Producto: " + dc.getProductoIdProducto());
            } else {
                System.out.println("\nNo existe ningún detalle de carrito registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}