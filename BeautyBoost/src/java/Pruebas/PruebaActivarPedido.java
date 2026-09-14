package Pruebas;

import Controlador.PedidoDAO;
import Modelo.Pedido;
import java.util.Scanner;

public class PruebaActivarPedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PedidoDAO pedidoDAO = new PedidoDAO();

        System.out.print("Ingrese el ID del pedido a activar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el pedido existe en la base de datos
            Pedido pedExistente = pedidoDAO.consultarPedido(idBusqueda);

            if (pedExistente != null) {
                System.out.println("\nPedido encontrado:");
                System.out.println("ID: " + pedExistente.getIdPedido());
                System.out.println("Número de Pedido: " + pedExistente.getNumeroPedido());
                System.out.println("Total: $" + pedExistente.getTotal());

                // 2. Procedemos a activarlo
                System.out.println("\nProcesando activación...");
                boolean activado = pedidoDAO.activarPedido(idBusqueda);

                if (activado) {
                    System.out.println("¡El pedido ha sido activado con éxito!");
                } else {
                    System.out.println("No se pudo activar el pedido.");
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