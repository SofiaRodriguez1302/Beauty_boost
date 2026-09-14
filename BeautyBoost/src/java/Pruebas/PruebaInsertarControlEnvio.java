package Pruebas;

import Controlador.ControlEnvioDAO;
import Modelo.ControlEnvio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PruebaInsertarControlEnvio {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ControlEnvioDAO controlDAO = new ControlEnvioDAO();
        ControlEnvio nuevoControl = new ControlEnvio();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        try {
            System.out.print("Ingrese la fecha de entrega (yyyy-MM-dd) [Opcional, presione ENTER para omitir]: ");
            String fechaTexto = teclado.nextLine().trim();

            if (!fechaTexto.isEmpty()) {
                try {
                    Date fecha = formatoFecha.parse(fechaTexto);
                    nuevoControl.setFechaEntrega(fecha);
                } catch (ParseException e) {
                    System.out.println("Formato de fecha inválido. Se registrará sin fecha de entrega.");
                }
            }

            System.out.print("Ingrese el código de seguimiento: ");
            String codigoSeguimiento = teclado.nextLine().trim();

            System.out.print("Ingrese el ID del estado de pedido: ");
            int idEstadoPedido = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el ID de la empresa de envío: ");
            int idEmpresaEnvio = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el ID del pedido: ");
            int idPedido = Integer.parseInt(teclado.nextLine());

            nuevoControl.setCodigoSeguimiento(codigoSeguimiento);
            nuevoControl.setEstadoPedidoIdEstadoPedido(idEstadoPedido);
            nuevoControl.setEmpresasEnvioIdEmpresaEnvio(idEmpresaEnvio);
            nuevoControl.setPedidoIdPedido(idPedido);

            System.out.println("\nProcesando inserción...");
            boolean exito = controlDAO.insertarControl(nuevoControl);

            if (exito) {
                System.out.println("¡Control de envío registrado con éxito!");
            } else {
                System.out.println("No se pudo registrar el control de envío.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError: Ingrese valores numéricos válidos para los IDs.");
        }

        teclado.close();
    }
}