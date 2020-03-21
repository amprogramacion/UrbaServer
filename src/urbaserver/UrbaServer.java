package urbaserver;
import java.net.*;
public class UrbaServer {
    static final ConexionDB conector = new ConexionDB();
    Socket cliente;
    static final int Puerto = 5145;
    public static void main(String[] args) {
        if(conector.Conectar() != true) {
            System.out.println("NO SE PUEDE CONECTAR A LA BASE DE DATOS");
        } else {
            MensajesChat mensajes = new MensajesChat();
            try {
                ServerSocket socketServidor = new ServerSocket(Puerto);
                System.out.println("SERVIDOR DE URBALOCA 0.1 INICIADO EN PUERTO "+Puerto);
                while (true) {
                    Socket cliente = socketServidor.accept();
                    System.out.println("Nuevo cliente conectado: "+cliente);
                    UrbaUsers clientes = new UrbaUsers(cliente, mensajes);
                    clientes.start();
                }
            } catch (Exception e) {
                System.out.println("Error al conectar un cliente"+e.getStackTrace());
            }
        }
    }
}
