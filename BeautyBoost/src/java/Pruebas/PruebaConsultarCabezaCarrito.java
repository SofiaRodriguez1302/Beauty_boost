package Pruebas;

import Controlador.CabezaCarritoDAO;
import Modelo.CabezaCarrito;
import java.util.Scanner;

public class PruebaConsultarCabezaCarrito {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CabezaCarritoDAO carritoDAO = new CabezaCarritoDAO();

        System.out.print("Ingrese el ID del carrito a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el carrito por su ID
            CabezaCarrito cc = carritoDAO.consultarCarrito(idBusqueda);

            if (cc != null) {
                System.out.println("\nCarrito encontrado:");
                System.out.println("ID Carrito: " + cc.getIdCarrito());
                System.out.println("Fecha Creación: " + cc.getFechaCreacion());
                System.out.println("Fecha Actualización: " + cc.getFechaActualizacion());
            } else {
                System.out.println("\nNo existe ningún carrito registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}