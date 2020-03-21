package urbaserver;
import java.net.*;
import java.io.*;
import java.util.*;
public class UrbaUsers extends Thread implements Observer {
    Socket cliente;
    String NombreUsuario;
    String IdUsuario;
    MensajesChat mensajes;
    BufferedReader flujo_entrada;
    PrintStream flujo_salida;
    ConexionDB conector = new ConexionDB();
    UrbaUsers(Socket cliente, MensajesChat mensajes) {
        this.cliente = cliente;
        this.mensajes = mensajes;
    }
    public void run() {
        mensajes.addObserver(this);
        try {
            // De entrada del cliente al servidor
            flujo_entrada = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
            flujo_salida =  new PrintStream(this.cliente.getOutputStream()); 
            sendMsg("<?xml version=\"1.0\" encoding=\"UTF-8\"?><cross-domain-policy xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://www.adobe.com/xml/schemas/PolicyFileSocket.xsd\"><allow-access-from domain=\"*\" to-ports=\"*\" secure=\"false\" /><site-control permitted-cross-domain-policies=\"all\" /></cross-domain-policy> ");
            Boolean conectado = true;
            //Mientras el usuario este conectado, se interpretan los mensajes
            while(conectado) {
                String str = flujo_entrada.readLine();
                String[] str2 = str.split(","); //Se parte la cadena enviada por comas (,)
                if(str2[0].trim().equals("publico")) { //Si el mensaje enviado es Publico
                    mensajes.setMensaje(str2[1]);
                } else if(str2[0].trim().equals("privado")) { //Si el mensaje enviado es Privado
                    //Conexion a la base de datos
                    if(str2[1].equals("conectar")) {
                        String Usuario = str2[2];
                        String Password = str2[3];
                        String IPUser = cliente.getInetAddress().toString();
                        String Respuesta = conector.LoginServidor(Usuario, Password, IPUser, cliente.toString());
                        //Aqui se modifica el valor NombreUsuario, asignando un string al socket
                        String[] Respuesta2 = Respuesta.split(",");
                        if(Respuesta2[1].equals("OKPassword")) {
                            this.NombreUsuario = Respuesta2[2];
                            this.IdUsuario = Respuesta2[7];
                            System.out.println("Se ha conectado el usuario ["+this.IdUsuario+"]"+this.NombreUsuario);
                        }
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("ListarSalas")) {
                        String Respuesta = conector.ListarSalas();
                        sendMsg(Respuesta);
                     } else if(str2[1].equals("CrearSala")) {
                        String NombreSala = str2[2];
                        String Usuario = this.NombreUsuario;
                        String Pass = str2[3];
                        String MaxUsers = str2[4];
                        String Respuesta = conector.CrearSala(NombreSala, Usuario, MaxUsers, Pass);
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("ListarCategoriasCatalogo")) {
                        String Respuesta = conector.ListarCategoriasCatalogo();
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("ListarMueblesCatalogo")) {
                        String categoria = str2[2];
                        String Respuesta = conector.ListarMueblesCatalogo(categoria);
                        sendMsg(Respuesta);
                     } else if(str2[1].equals("ComprarMueble")) {
                        String idmueble = str2[2];
                        String precio = str2[3];
                        String Usuario = this.NombreUsuario;
                        String Respuesta = conector.ComprarMueble(idmueble, precio, Usuario);
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("MiInventario")) {
                        String Usuario = this.NombreUsuario;
                        String Respuesta = conector.MiInventario(Usuario);
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("ConsultarPlacas")) {
                        String Usuario = this.NombreUsuario;
                        String Respuesta = conector.ConsultarPlacas(Usuario);
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("PonerPlaca")) {
                        String Placa = str2[2];
                        String Usuario = this.NombreUsuario;
                        String Respuesta = conector.PonerPlaca(Placa, Usuario);
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("QuitarPlaca")) {
                        String Usuario = this.NombreUsuario;
                        String Respuesta = conector.QuitarPlaca(Usuario);
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("MostrarPlaca")) {
                        String Usuario = this.NombreUsuario;
                        String Visible = str2[2];
                        String Respuesta = conector.MostrarPlaca(Usuario, Visible);
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("CambiarRopa")) {
                        String Usuario = this.NombreUsuario;
                        String pelo = str2[2];
                        String camisa = str2[3];
                        String pants = str2[4];
                        String zapatos = str2[5];
                        String ide = this.IdUsuario;
                        String Respuesta = conector.CambiarRopa(Usuario, ide, pelo, camisa, pants, zapatos);
                        mensajes.setMensaje(Respuesta);
                    } else if(str2[1].equals("ActualizarMiSala")) {
                        String Usuario = this.NombreUsuario;
                        String Sala = str2[2];
                        String Respuesta = conector.ActualizarMiSala(Usuario, Sala);
                        sendMsg(Respuesta);
                     } else if(str2[1].equals("PosicionarMueble")) {
                        String Usuario = this.NombreUsuario;
                        String varx = str2[2];
                        String vary = str2[3];
                        String idmueble = str2[4];
                        String sala = str2[5];
                        String posicion = str2[6];
                        String Respuesta = conector.PosicionarMueble(Usuario, varx, vary, idmueble, sala, posicion);
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("PedirMuebles")) {
                        String sala = str2[2];
                        String todos = str2[3];
                        String Respuesta = conector.PedirMuebles(sala);
                        if(todos.equals("todos")) {
                            mensajes.setMensaje(Respuesta);
                        } else {
                            sendMsg(Respuesta);
                        }
                    } else if(str2[1].equals("ConsultarPlacaUsuario")) {
                        String Usuario = str2[2];
                        String Respuesta = conector.ConsultarPlacaUsuario(Usuario);
                        sendMsg(Respuesta);
                    } else if(str2[1].equals("Echar")) {
                        String Usuario = this.NombreUsuario;
                        String objetivo = str2[2];
                        String idu = str2[3];
                        String sala = str2[4];
                        String Respuesta = conector.Echar(Usuario, objetivo, idu, sala);
                        mensajes.setMensaje(Respuesta);
                    } else if(str2[1].equals("Alertar")) {
                        String Usuario = this.NombreUsuario;
                        String[] cadena = str2[2].split(":::");
                        String objetivo = cadena[0];
                        String mensaje = cadena[1];
                        String Respuesta = conector.Alertar(Usuario, objetivo, mensaje);
                        mensajes.setMensaje(Respuesta);
                    } else if(str2[1].equals("GirarMueble")) {
                        String Usuario = str2[2];
                        String sala = str2[3];
                        String mueble = str2[4];
                        String Respuesta = conector.GirarMueble(Usuario, sala, mueble);
                        mensajes.setMensaje(Respuesta);
                    } else if(str2[1].equals("RecogerMueble")) {
                        String Usuario = str2[2];
                        String sala = str2[3];
                        String mueble = str2[4];
                        String Respuesta = conector.RecogerMueble(Usuario, sala, mueble);
                        mensajes.setMensaje(Respuesta);
                    } else {
                        sendMsg(str2[1]);
                    }
                } else { //Si el mensaje no tiene el formato publico, o privado, se muestra a todo el mundo
                    mensajes.setMensaje(str);
                }
                if(this.NombreUsuario == null) {
                   System.out.println("[RECIBIDO] MENSAJE: "+str);
                } else {
                    System.out.println("[RECIBIDO] ["+this.NombreUsuario+"] MENSAJE: "+str);
                }
                if(str.trim().equals("salir")) {
                    conectado = false;
                    cliente.close();
                }
            }
        } catch (NullPointerException e) {
            Boolean DesconectarUsuario = conector.DesconectarUsuario(this.NombreUsuario);
            if(DesconectarUsuario == true) {
                System.out.println("El usuario "+this.NombreUsuario+" se ha desconectado correctamente.");
                mensajes.setMensaje("Desconectado,"+this.IdUsuario);
            } else {
                System.out.println("El usuario "+this.NombreUsuario+" se ha desconectado pero no se actualizo la BD.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        try {
            // Envia el mensaje al cliente
            sendMsg(arg.toString());
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
    }
    public void sendMsg(String msg) {
        flujo_salida.print(msg+"\u0000");
        flujo_salida.flush();
       System.out.println("[ENVIADO] MENSAJE: "+msg);
    }
}
