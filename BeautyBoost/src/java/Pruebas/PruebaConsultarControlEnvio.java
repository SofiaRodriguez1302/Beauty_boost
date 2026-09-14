package Pruebas;

import Controlador.ControlEnvioDAO;
import Modelo.ControlEnvio;
import java.util.Scanner;

public class PruebaConsultarControlEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ControlEnvioDAO controlDAO = new ControlEnvioDAO();

        System.out.print("Ingrese el ID del control de envío a buscar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // Consultar el control de envío por su ID
            ControlEnvio ce = controlDAO.consultarControl(idBusqueda);

            if (ce != null) {
                System.out.println("\nControl de envío encontrado:");
                System.out.println("ID Control: " + ce.getIdControl());
                System.out.println("Fecha de Entrega: " + (ce.getFechaEntrega() != null ? ce.getFechaEntrega() : "No asignada"));
                System.out.println("Código de Seguimiento: " + ce.getCodigoSeguimiento());
                System.out.println("ID Estado Pedido: " + ce.getEstadoPedidoIdEstadoPedido());
                System.out.println("ID Empresa Envío: " + ce.getEmpresasEnvioIdEmpresaEnvio());
                System.out.println("ID Pedido: " + ce.getPedidoIdPedido());
            } else {
                System.out.println("\nNo existe ningún control de envío registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Debe ingresar un número entero válido para el ID.");
        }

        teclado.close();
    }
}