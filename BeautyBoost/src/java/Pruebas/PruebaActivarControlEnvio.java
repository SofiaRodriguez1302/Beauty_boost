package Pruebas;

import Controlador.ControlEnvioDAO;
import Modelo.ControlEnvio;
import java.util.Scanner;

public class PruebaActivarControlEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ControlEnvioDAO controlDAO = new ControlEnvioDAO();

        System.out.print("Ingrese el ID del control de envío a activar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificamos si el control de envío existe
            ControlEnvio ceExistente = controlDAO.consultarControl(idBusqueda);

            if (ceExistente != null) {
                System.out.println("\nControl de envío encontrado:");
                System.out.println("ID Control: " + ceExistente.getIdControl());
                System.out.println("Fecha Entrega: " + (ceExistente.getFechaEntrega() != null ? ceExistente.getFechaEntrega() : "No asignada"));
                System.out.println("Código Seguimiento: " + ceExistente.getCodigoSeguimiento());
                System.out.println("ID Estado Pedido: " + ceExistente.getEstadoPedidoIdEstadoPedido());
                System.out.println("ID Empresa Envío: " + ceExistente.getEmpresasEnvioIdEmpresaEnvio());
                System.out.println("ID Pedido: " + ceExistente.getPedidoIdPedido());

                // 2. Procedemos a activarlo
                System.out.println("\nProcesando activación...");
                boolean activado = controlDAO.activarControl(idBusqueda);

                if (activado) {
                    System.out.println("¡El control de envío ha sido activado con éxito!");
                } else {
                    System.out.println("No se pudo activar el control de envío.");
                }

            } else {
                System.out.println("\nNo existe ningún control de envío registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}