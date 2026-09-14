package Pruebas;

import Controlador.MetodoDePagoDAO;
import Modelo.MetodoDePago;
import java.util.Scanner;

public class PruebaInsertarMetodoDePago {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        MetodoDePagoDAO metodoDAO = new MetodoDePagoDAO();
        MetodoDePago nuevoMetodo = new MetodoDePago();

        System.out.print("Ingrese la descripción del método de pago (ej. Tarjeta, Efectivo, Nequi): ");
        String descripcion = teclado.nextLine().trim();

        if (!descripcion.isEmpty()) {
            nuevoMetodo.setDescripcionMetodoDePago(descripcion);

            System.out.println("\nProcesando inserción...");
            boolean exito = metodoDAO.insertarMetodo(nuevoMetodo);

            if (exito) {
                System.out.println("¡Método de pago registrado con éxito!");
            } else {
                System.out.println("No se pudo registrar el método de pago.");
            }
        } else {
            System.out.println("\nError: La descripción no puede estar vacía.");
        }

        teclado.close();
    }
}