package Pruebas;

import Controlador.EstadoPedidoDAO;
import Modelo.EstadoPedido;
import java.util.Scanner;

public class PruebaInactivarEstadoPedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EstadoPedidoDAO estadoDAO = new EstadoPedidoDAO();

        System.out.print("Ingrese el ID del estado de pedido a inactivar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el estado de pedido existe en la base de datos
            EstadoPedido estExistente = estadoDAO.consultarEstado(idBusqueda);

            if (estExistente != null) {
                System.out.println("\nEstado de pedido encontrado:");
                System.out.println("ID Estado Pedido: " + estExistente.getIdEstadoPedido());
                System.out.println("Descripción: " + estExistente.getDescripcionEstado());

                // 2. Procedemos a inactivarlo
                System.out.println("\nProcesando inactivación...");
                boolean inactivado = estadoDAO.inactivarEstado(idBusqueda);

                if (inactivado) {
                    System.out.println("¡El estado de pedido ha sido inactivado con éxito!");
                } else {
                    System.out.println("No se pudo inactivar el estado de pedido.");
                }

            } else {
                System.out.println("\nNo existe ningún estado de pedido registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}