package Pruebas;

import Controlador.ControlEnvioDAO;
import Modelo.ControlEnvio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PruebaActualizarControlEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ControlEnvioDAO controlDAO = new ControlEnvioDAO();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        System.out.print("Ingrese el ID del control de envío a actualizar: ");

        try {
            int idBusqueda = Integer.parseInt(teclado.nextLine());

            // 1. Verificar si el control de envío existe
            ControlEnvio ceExistente = controlDAO.consultarControl(idBusqueda);

            if (ceExistente != null) {
                System.out.println("\nControl de envío encontrado:");
                System.out.println("ID Control: " + ceExistente.getIdControl());
                System.out.println("Fecha Entrega Actual: " + (ceExistente.getFechaEntrega() != null ? ceExistente.getFechaEntrega() : "No asignada"));
                System.out.println("Código Seguimiento Actual: " + ceExistente.getCodigoSeguimiento());
                System.out.println("ID Estado Pedido Actual: " + ceExistente.getEstadoPedidoIdEstadoPedido());
                System.out.println("ID Empresa Envío Actual: " + ceExistente.getEmpresasEnvioIdEmpresaEnvio());
                System.out.println("ID Pedido Actual: " + ceExistente.getPedidoIdPedido());

                // 2. Pedir los nuevos datos
                System.out.print("\nIngrese la nueva fecha de entrega (yyyy-MM-dd) [Presione ENTER para omitir/mantener nulo]: ");
                String fechaTexto = teclado.nextLine().trim();

                if (!fechaTexto.isEmpty()) {
                    try {
                        Date nuevaFecha = formatoFecha.parse(fechaTexto);
                        ceExistente.setFechaEntrega(nuevaFecha);
                    } catch (ParseException e) {
                        System.out.println("Formato de fecha inválido. Se mantendrá sin cambios en la fecha.");
                    }
                }

                System.out.print("Ingrese el nuevo código de seguimiento: ");
                String nuevoCodigo = teclado.nextLine().trim();

                System.out.print("Ingrese el nuevo ID del estado de pedido: ");
                int nuevoIdEstado = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID de la empresa de envío: ");
                int nuevoIdEmpresa = Integer.parseInt(teclado.nextLine());

                System.out.print("Ingrese el nuevo ID del pedido: ");
                int nuevoIdPedido = Integer.parseInt(teclado.nextLine());

                ceExistente.setCodigoSeguimiento(nuevoCodigo);
                ceExistente.setEstadoPedidoIdEstadoPedido(nuevoIdEstado);
                ceExistente.setEmpresasEnvioIdEmpresaEnvio(nuevoIdEmpresa);
                ceExistente.setPedidoIdPedido(nuevoIdPedido);

                System.out.println("\nProcesando actualización...");
                boolean actualizado = controlDAO.actualizarControl(ceExistente);

                if (actualizado) {
                    System.out.println("¡Control de envío actualizado con éxito!");
                } else {
                    System.out.println("No se pudo actualizar el control de envío.");
                }

            } else {
                System.out.println("\nNo existe ningún control de envío registrado con el ID: " + idBusqueda);
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Ingrese valores numéricos válidos para los IDs.");
        }

        teclado.close();
    }
}