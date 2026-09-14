package Pruebas;

import Controlador.EstadoPedidoDAO;
import Modelo.EstadoPedido;
import java.util.Scanner;

public class PruebaEliminarEstadoPedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        EstadoPedidoDAO estadoDAO = new EstadoPedidoDAO();

        System.out.print("Ingrese el ID del estado de pedido a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el estado de pedido existe en la base de datos
            EstadoPedido estExistente = estadoDAO.consultarEstado(idBusqueda);

            if (estExistente != null) {
                System.out.println("\nEstado de pedido encontrado:");
                System.out.println("ID Estado Pedido: " + estExistente.getIdEstadoPedido());
                System.out.println("Descripción: " + estExistente.getDescripcionEstado());

                // 2. Confirmación antes de eliminar
                System.out.print("\n¿Está seguro de eliminar este estado de pedido? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = estadoDAO.eliminarEstado(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Estado de pedido eliminado exitosamente!");
                    } else {
                        System.out.println("No se pudo eliminar el estado de pedido.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
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