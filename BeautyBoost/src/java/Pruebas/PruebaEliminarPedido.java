package Pruebas;

import Controlador.PedidoDAO;
import Modelo.Pedido;
import java.util.Scanner;

public class PruebaEliminarPedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PedidoDAO pedidoDAO = new PedidoDAO();

        System.out.print("Ingrese el ID del pedido a eliminar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el pedido existe en la base de datos
            Pedido pedExistente = pedidoDAO.consultarPedido(idBusqueda);

            if (pedExistente != null) {
                System.out.println("\nPedido encontrado:");
                System.out.println("ID: " + pedExistente.getIdPedido());
                System.out.println("Número de Pedido: " + pedExistente.getNumeroPedido());
                System.out.println("Total: $" + pedExistente.getTotal());

                System.out.print("\n¿Está seguro de eliminar este pedido? (SI/NO): ");
                String confirmacion = teclado.nextLine().trim();

                if (confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("\nProcesando eliminación...");
                    boolean eliminado = pedidoDAO.eliminarPedido(idBusqueda);

                    if (eliminado) {
                        System.out.println("¡Pedido eliminado exitosamente!");
                    } else {
                        System.out.println("No se pudo eliminar el pedido.");
                    }
                } else {
                    System.out.println("\nOperación cancelada por el usuario.");
                }

            } else {
                System.out.println("\nNo existe ningún pedido registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}