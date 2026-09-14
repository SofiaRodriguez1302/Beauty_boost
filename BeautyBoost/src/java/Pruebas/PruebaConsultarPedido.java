package Pruebas;

import Controlador.PedidoDAO;
import Modelo.Pedido;
import java.util.Scanner;

public class PruebaConsultarPedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PedidoDAO pedidoDAO = new PedidoDAO();

        System.out.print("Ingrese el ID del pedido a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el pedido por su ID
            Pedido ped = pedidoDAO.consultarPedido(idBusqueda);

            if (ped != null) {
                System.out.println("\nPedido encontrado:");
                System.out.println("ID Pedido: " + ped.getIdPedido());
                System.out.println("Fecha Pedido: " + ped.getFechaPedido());
                System.out.println("Número de Pedido: " + ped.getNumeroPedido());
                System.out.println("Total: $" + ped.getTotal());
                System.out.println("ID Usuario: " + ped.getUsuarioIdUsuario());
            } else {
                System.out.println("\nNo existe ningún pedido registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}