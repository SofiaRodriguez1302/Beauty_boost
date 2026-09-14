package Pruebas;

import Controlador.EstadoPedidoDAO;
import Modelo.EstadoPedido;
import java.util.Scanner;

public class PruebaActualizarEstadoPedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EstadoPedidoDAO estadoDAO = new EstadoPedidoDAO();

        System.out.print("Ingrese el ID del estado de pedido a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el estado de pedido existe
            EstadoPedido estExistente = estadoDAO.consultarEstado(idBusqueda);

            if (estExistente != null) {
                System.out.println("\nEstado de pedido encontrado:");
                System.out.println("ID Estado Pedido: " + estExistente.getIdEstadoPedido());
                System.out.println("Descripción Actual: " + estExistente.getDescripcionEstado());

                // 2. Solicitamos la nueva descripción
                System.out.print("\nIngrese la nueva descripción del estado: ");
                String nuevaDescripcion = teclado.nextLine().trim();

                if (!nuevaDescripcion.isEmpty()) {
                    estExistente.setDescripcionEstado(nuevaDescripcion);

                    System.out.println("\nProcesando actualización...");
                    boolean actualizado = estadoDAO.actualizarEstado(estExistente);

                    if (actualizado) {
                        System.out.println("¡Estado de pedido actualizado con éxito!");
                    } else {
                        System.out.println("No se pudo actualizar el estado de pedido.");
                    }
                } else {
                    System.out.println("\nError: La descripción no puede estar vacía.");
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