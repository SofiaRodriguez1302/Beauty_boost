package Pruebas;

import Controlador.PedidoDAO;
import Modelo.Pedido;
import java.util.Date;
import java.util.Scanner;

public class PruebaInsertarPedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido nuevoPedido = new Pedido();

        try {
            System.out.print("Ingrese el número de pedido: ");
            int numeroPedido = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el valor total del pedido: ");
            double total = Double.parseDouble(teclado.nextLine());

            System.out.print("Ingrese el ID del usuario asociado: ");
            int idUsuario = Integer.parseInt(teclado.nextLine());

            nuevoPedido.setFechaPedido(new Date()); // Asigna la fecha y hora actual
            nuevoPedido.setNumeroPedido(numeroPedido);
            nuevoPedido.setTotal(total);
            nuevoPedido.setUsuarioIdUsuario(idUsuario);

            System.out.println("\nProcesando inserción...");
            boolean exito = pedidoDAO.insertarPedido(nuevoPedido);

            if (exito) {
                System.out.println("¡Pedido registrado con éxito!");
            } else {
                System.out.println("No se pudo registrar el pedido.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar valores numéricos válidos para número de pedido, total e ID de usuario.");
        }

        teclado.close();
    }
}