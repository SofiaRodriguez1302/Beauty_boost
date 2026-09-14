package Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio centralizado de envío de correos de Beauty Boost.
 *
 * Unifica la configuración SMTP (JavaMail / Jakarta Mail) que antes estaba
 * duplicada dentro de los Servlets, y expone métodos de alto nivel para:
 *   - Correo de bienvenida tras un registro exitoso.
 *   - Correo de restablecimiento de contraseña (usuario + notificación admin).
 *
 * Mantiene exactamente la misma configuración SMTP y estilos HTML que ya
 * estaban validados en el proyecto (remitente, credenciales de app,
 * paleta de color de marca #d87d6a).
 */
public class EmailService {

    private static final String REMITENTE = "store.beautyboost@gmail.com";
    private static final String PASSWORD_APP = "fvxpreiwrcchkujx"; // Contraseña de aplicación (sin espacios)
    private static final String CORREO_ADMIN = "store.beautyboost@gmail.com";

    private static Session crearSesion() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, PASSWORD_APP);
            }
        });
    }

    /**
     * Envía un correo HTML genérico usando la cuenta institucional de Beauty Boost.
     */
    private static void enviarCorreo(String destinatario, String asunto, String contenidoHTML) throws MessagingException {
        Session session = crearSesion();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(REMITENTE));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setContent(contenidoHTML, "text/html; charset=utf-8");
        Transport.send(message);
    }

    /**
     * Correo de BIENVENIDA enviado tras completar el registro (RegistroServlet).
     * No lanza excepción hacia el llamador: un fallo de envío nunca debe
     * impedir que el registro del usuario se complete.
     */
    public static void enviarCorreoBienvenida(String correoDestino, String nombre) {
        try {
            String nombreMostrado = (nombre == null || nombre.trim().isEmpty()) ? "" : nombre.trim();
            String contenidoHTML = "<div style='font-family: Arial, sans-serif; padding: 20px; color: #333;'>"
                    + "<h2 style='color: #d87d6a;'>¡Bienvenido(a) a Beauty Boost!</h2>"
                    + "<p>Hola, <b>" + nombreMostrado + "</b>.</p>"
                    + "<p>Tu cuenta ha sido creada exitosamente. Ahora puedes iniciar sesión y explorar "
                    + "nuestro catálogo de maquillaje, skincare y accesorios pensado para resaltar tu belleza natural.</p>"
                    + "<div style='background: #faf5f3; padding: 15px; border-radius: 8px; border: 1px solid #ebd5cd; margin: 20px 0; text-align: center;'>"
                    + "<b style='color: #d87d6a; font-size: 16px;'>Gracias por unirte a la familia Beauty Boost</b>"
                    + "</div>"
                    + "<p>Si tú no creaste esta cuenta, por favor ignora este mensaje.</p>"
                    + "</div>";

            enviarCorreo(correoDestino, "¡Bienvenida a Beauty Boost!", contenidoHTML);
        } catch (Exception e) {
            // El correo de bienvenida es informativo: no debe interrumpir el flujo de registro.
            System.out.println("Aviso: no se pudo enviar el correo de bienvenida a " + correoDestino + ": " + e.getMessage());
        }
    }

    /**
     * Correo enviado al USUARIO con su nueva contraseña temporal.
     */
    public static void enviarCorreoRecuperacion(String correoDestino, String nombreUsuario, String nuevaClave) throws MessagingException {
        String contenidoHTML = "<div style='font-family: Arial, sans-serif; padding: 20px; color: #333;'>"
                + "<h2 style='color: #d87d6a;'>Beauty Boost - Restablecimiento de Cuenta</h2>"
                + "<p>Hola, <b>" + nombreUsuario + "</b>.</p>"
                + "<p>Hemos recibido una solicitud para restablecer tu contraseña. Tu nueva contraseña temporal es:</p>"
                + "<div style='background: #faf5f3; padding: 15px; border-radius: 8px; border: 1px solid #ebd5cd; margin: 20px 0; text-align: center;'>"
                + "<b style='color: #d87d6a; font-size: 18px;'>" + nuevaClave + "</b>"
                + "</div>"
                + "<p>Te sugerimos iniciar sesión con esta clave y cambiarla posteriormente desde tu perfil si lo deseas.</p>"
                + "</div>";

        enviarCorreo(correoDestino, "Restablecimiento de Contraseña - Beauty Boost", contenidoHTML);
    }

    /**
     * Correo de NOTIFICACIÓN al correo institucional cuando un usuario
     * solicita recuperación de contraseña.
     */
    public static void enviarNotificacionAdminRecuperacion(String nombreUsuario, String correoUsuario) throws MessagingException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaActual = formatoFecha.format(new Date());

        String contenidoAdminHTML = "<div style='font-family: Arial, sans-serif; padding: 20px; color: #333;'>"
                + "<h2 style='color: #2c3e50;'>Registro de Recuperación de Contraseña</h2>"
                + "<p>Se ha solicitado la recuperación de contraseña de un usuario en el sistema con los siguientes datos:</p>"
                + "<ul>"
                + "<li><b>Fecha y Hora (Día/Mes/Año):</b> " + fechaActual + "</li>"
                + "<li><b>Nombre del Usuario:</b> " + nombreUsuario + "</li>"
                + "<li><b>Correo del Usuario:</b> " + correoUsuario + "</li>"
                + "</ul>"
                + "</div>";

        enviarCorreo(CORREO_ADMIN, "Nueva recuperación de contraseña - Beauty Boost", contenidoAdminHTML);
    }
}
