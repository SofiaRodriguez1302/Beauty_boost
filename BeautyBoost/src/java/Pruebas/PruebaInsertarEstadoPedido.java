package Pruebas;

import Controlador.EstadoPedidoDAO;
import Modelo.EstadoPedido;
import java.util.Scanner;

public class PruebaInsertarEstadoPedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EstadoPedidoDAO estadoDAO = new EstadoPedidoDAO();
        EstadoPedido nuevoEstado = new EstadoPedido();

        System.out.print("Ingrese la descripción del estado (ej. Pendiente, Enviado, Entregado): ");
        String descripcion = teclado.nextLine().trim();

        if (!descripcion.isEmpty()) {
            nuevoEstado.setDescripcionEstado(descripcion);

            System.out.println("\nProcesando inserción...");
            boolean exito = estadoDAO.insertarEstado(nuevoEstado);

            if (exito) {
                System.out.println("¡Estado de pedido registrado con éxito!");
            } else {
                System.out.println("No se pudo registrar el estado de pedido.");
            }
        } else {
            System.out.println("\nError: La descripción no puede estar vacía.");
        }

        teclado.close();
    }
}