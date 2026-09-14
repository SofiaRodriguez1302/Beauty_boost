package Pruebas;

import Controlador.PedidoDAO;
import Modelo.Pedido;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PruebaActualizarPedido {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        PedidoDAO pedidoDAO = new PedidoDAO();

        System.out.print("Ingrese el ID del pedido a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el pedido existe
            Pedido pedExistente = pedidoDAO.consultarPedido(idBusqueda);

            if (pedExistente != null) {
                System.out.println("\nPedido encontrado:");
                System.out.println("ID: " + pedExistente.getIdPedido());
                System.out.println("Fecha Actual: " + pedExistente.getFechaPedido());
                System.out.println("Número de Pedido Actual: " + pedExistente.getNumeroPedido());
                System.out.println("Total Actual: $" + pedExistente.getTotal());
                System.out.println("ID Usuario Actual: " + pedExistente.getUsuarioIdUsuario());

                // 2. Solicitamos los nuevos datos
                System.out.print("\nIngrese el nuevo número de pedido: ");
                int nuevoNumero = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese el nuevo total del pedido: ");
                double nuevoTotal = Double.parseDouble(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID del usuario asociado: ");
                int nuevoIdUsuario = Integer.parseInt(teclado.nextLine());

                // Actualizamos a la fecha/hora actual del sistema
                pedExistente.setFechaPedido(new Date());
                pedExistente.setNumeroPedido(nuevoNumero);
                pedExistente.setTotal(nuevoTotal);
                pedExistente.setUsuarioIdUsuario(nuevoIdUsuario);

                System.out.println("\nProcesando actualización...");
                boolean actualizado = pedidoDAO.actualizarPedido(pedExistente);

                if (actualizado) {
                    System.out.println("¡Pedido actualizado con éxito!");
                } else {
                    System.out.println("No se pudo actualizar el pedido.");
                }

            } else {
                System.out.println("\nNo existe ningún pedido con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar valores numéricos válidos en los campos correspondientes.");
        }

        teclado.close();
    }
}