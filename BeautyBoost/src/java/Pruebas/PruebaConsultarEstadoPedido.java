package Pruebas;

import Controlador.EstadoPedidoDAO;
import Modelo.EstadoPedido;
import java.util.Scanner;

public class PruebaConsultarEstadoPedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EstadoPedidoDAO estadoDAO = new EstadoPedidoDAO();

        System.out.print("Ingrese el ID del estado de pedido a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el estado de pedido por su ID
            EstadoPedido est = estadoDAO.consultarEstado(idBusqueda);

            if (est != null) {
                System.out.println("\nEstado de pedido encontrado:");
                System.out.println("ID Estado Pedido: " + est.getIdEstadoPedido());
                System.out.println("Descripción: " + est.getDescripcionEstado());
            } else {
                System.out.println("\nNo existe ningún estado de pedido registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}