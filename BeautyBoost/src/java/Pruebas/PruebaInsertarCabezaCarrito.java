package Pruebas;

import Controlador.CabezaCarritoDAO;
import Modelo.CabezaCarrito;
import java.util.Date;
import java.util.Scanner;

public class PruebaInsertarCabezaCarrito {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CabezaCarritoDAO carritoDAO = new CabezaCarritoDAO();
        CabezaCarrito nuevoCarrito = new CabezaCarrito();

        try {
            System.out.print("¿Desea crear un nuevo carrito de compras? (SI/NO): ");
            String opcion = teclado.nextLine().trim();

            if (opcion.equalsIgnoreCase("SI")) {
                // Asignar fecha actual a la creación y actualización
                Date fechaActual = new Date();
                nuevoCarrito.setFechaCreacion(fechaActual);
                nuevoCarrito.setFechaActualizacion(fechaActual);

                System.out.println("\nProcesando inserción...");
                boolean exito = carritoDAO.insertarCarrito(nuevoCarrito);

                if (exito) {
                    System.out.println("¡Carrito registrado con éxito!");
                } else {
                    System.out.println("No se pudo registrar el carrito.");
                }
            } else {
                System.out.println("\nOperación cancelada por el usuario.");
            }

        } catch (Exception e) {
            System.out.println("\nError al procesar los datos: " + e.getMessage());
        }

        teclado.close();
    }
}