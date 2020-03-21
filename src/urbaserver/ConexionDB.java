package urbaserver;
import java.sql.*;

public class ConexionDB {
    public static Connection conexion;
    ResultSet rs;
    Statement cmd;
    public boolean Conectar() {
        Boolean conectado;
        try {
            DriverManager.registerDriver(new org.gjt.mm.mysql.Driver()); //?autoReconnect=true&failOverReadOnly=false&maxReconnects=99999
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/NOMBRE_BBDD?autoReconnect=true&failOverReadOnly=false&maxReconnects=99999", "USERNAME_BBDD", "CLAVE_BBDD");
            conectado = true;
            String query = "UPDATE users SET estoyen=''";
            String query2 = "UPDATE users SET idserver=''";
            String query3 = "UPDATE users SET logueado='0'";
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            PreparedStatement preparedStmt2 = conexion.prepareStatement(query2);
            PreparedStatement preparedStmt3 = conexion.prepareStatement(query3);
            preparedStmt.executeUpdate();
            preparedStmt2.executeUpdate();
            preparedStmt3.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error MYSQL: "+ex);
            conectado = false;
        }
        return conectado;
    }
    /*
    La función LoginServidor se llama al iniciar UrbaLoca.
    Comprueba las credenciales del usuario, y si está o no baneado.
    Si no lo está, envía los datos necesarios de la BD
    Se actualiza la IP del cliente y se marca como logueado.
    */
    public String LoginServidor(String Usr, String Pwd, String IP, String Cliente) {
        String resultado;
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT * FROM users WHERE nombre='"+Usr+"'");
            if (rs.next()) {
                String PwdBD = rs.getString("pass");
                String BaneadoBD = rs.getString("baneado");
                String FechabanBD = rs.getString("fechaban");
                String MotivoBD = rs.getString("motivo");
                String SocketBD = rs.getString("logueado");
                String ActivadoBD = rs.getString("activado");
                if(PwdBD.equals(Pwd)) {
                    if (BaneadoBD.equals("1")) {
                        resultado = "conectar,UserBanned,"+FechabanBD+","+MotivoBD;
                    } else if (SocketBD.equals("1")) {
                        resultado = "conectar,KOLogged,";
                    } else if (ActivadoBD.equals("0")) {
                        resultado = "conectar,NoActivate,";
                    } else {
                        String NombreBD = rs.getString("nombre");
                        String RangoBD = rs.getString("rango");
                        String FichasBD = rs.getString("fichas");
                        String PlacasBD = rs.getString("placas");
                        String EmailBD = rs.getString("email");
                        String IdBD = rs.getString("id");
                        String PeloBD = rs.getString("pelo");
                        String CamisaBD = rs.getString("camisa");
                        String PantsBD = rs.getString("pants");
                        String ZapatosBD = rs.getString("zapatos");
                        String MisionBD = rs.getString("mision");
                        resultado = "conectar,OKPassword,"+NombreBD+","+RangoBD+","+FichasBD+","+PlacasBD+","+EmailBD+","+IdBD+","+PeloBD+","+CamisaBD+","+PantsBD+","+ZapatosBD+":::"+MisionBD;
                        String query = "UPDATE users SET ip = ? WHERE nombre = ?";
                        String query2 = "UPDATE users SET logueado = 1 WHERE nombre = ?";
                        String query3 = "UPDATE users SET idserver = ? WHERE nombre = ?";
                        PreparedStatement preparedStmt = conexion.prepareStatement(query);
                        PreparedStatement preparedStmt2 = conexion.prepareStatement(query2);
                        PreparedStatement preparedStmt3 = conexion.prepareStatement(query3);
                        preparedStmt.setString(1, IP);
                        preparedStmt.setString(2, NombreBD);
                        preparedStmt2.setString(1, NombreBD);
                        preparedStmt3.setString(1, Cliente);
                        preparedStmt3.setString(2, NombreBD);
                        preparedStmt.executeUpdate();
                        preparedStmt2.executeUpdate();
                        preparedStmt3.executeUpdate();
                    }
                } else {
                    resultado = "conectar,KOPassword";
                }
            } else {
                resultado = "conectar,NoUserRegistered";
            }
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "conectar,NoQuery";
        }
        return resultado;
    }
    public boolean DesconectarUsuario(String user) {
        Boolean resultado;
        try {
            String query = "UPDATE users SET logueado = 0 WHERE nombre = ?";
            String query2 = "UPDATE users SET idserver = '' WHERE nombre = ?";
            String query3 = "UPDATE users SET estoyen = '' WHERE nombre = ?";
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            PreparedStatement preparedStmt2 = conexion.prepareStatement(query2);
            PreparedStatement preparedStmt3 = conexion.prepareStatement(query3);
            preparedStmt.setString(1, user);
            preparedStmt2.setString(1, user);
            preparedStmt3.setString(1, user);
            preparedStmt.executeUpdate();
            preparedStmt2.executeUpdate();
            preparedStmt3.executeUpdate();
            resultado = true;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = false;
        }
        return resultado;
    }
    public String ListarSalas() {
        String resultado = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT * FROM rooms ORDER BY name DESC");
            while(rs.next()) {
                Statement cmd2 = conexion.createStatement();
                ResultSet rs2 = cmd2.executeQuery("SELECT * FROM users WHERE logueado='1' AND estoyen='"+rs.getString("id")+"'");
                int NumUsuarios = 0;
                while (rs2.next()) {
                    NumUsuarios++;
                }
                resultado += rs.getString("id")+"%%"+rs.getString("owner")+"%%"+rs.getString("name")+"%%"+rs.getString("maxu")+"%%"+rs.getString("color")+"%%"+rs.getString("suelo")+"%%"+NumUsuarios+"%%"+rs.getString("pass")+";;";
                
            }
            return "listarsalas,"+resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "listarsalas,NoQuery";
        }
        return resultado;
    }
    //INSERT INTO rooms (name, owner, maxu) VALUES ('$name', '$owner', '$maxu')
    public String CrearSala(String NSala, String User, String Maxu, String pwd) {
        String resultado = "";
        try {
            String query = "INSERT INTO rooms (name, owner, maxu, muebles, color, suelo, pass) VALUES ('"+NSala+"', '"+User+"', '"+Maxu+"', '', '1', '1', '"+pwd+"')";
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            preparedStmt.executeUpdate();
            resultado = "crearsala,ok";
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "crearsala,NoQuery";
        }
        return resultado;
    }
    public String ListarCategoriasCatalogo() {
       String resultado = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT * FROM catalogo_categorias ORDER BY id ASC");
            while(rs.next()) {
                resultado += rs.getString("id")+":"+rs.getString("nombre")+";";
            }
            return "ListarCategoriasCatalogo,"+resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "ListarCategoriasCatalogo,NoQuery";
        }
        return resultado;
    }
    public String ListarMueblesCatalogo(String categoria) {
       String resultado = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT * FROM catalogo WHERE categoria='"+categoria+"'");
            while(rs.next()) {
                resultado += rs.getString("id")+":"+rs.getString("categoria")+":"+rs.getString("mueble")+":"+rs.getString("descripcion")+":"+rs.getString("precio")+":"+rs.getString("miniatura")+":"+rs.getString("imagen")+";";
            }
            return "ListarMueblesCatalogo,"+resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "ListarMueblesCatalogo,NoQuery";
        }
        return resultado;
    }
    public String ComprarMueble(String idmueble, String precio, String user) {
       String resultado = "";
       String mismuebles = "";
       String misfichas = "";
       int resta;
       String nueva = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT * FROM users WHERE nombre='"+user+"'");
            if(rs.next()) {
                mismuebles = rs.getString("muebles");
                misfichas = rs.getString("fichas");
            }
            resta = Integer.parseInt(misfichas)-Integer.parseInt(precio);
            Boolean encuentro = false;
            if(resta >= 0) {
                if(!"".equals(mismuebles)) {
                    String[] ArrayMuebles = mismuebles.split("::");
                    for(int a=0; a<ArrayMuebles.length; a++) {
                        String[] Separar = ArrayMuebles[a].split(";");
                        String SID = Separar[0];
                        String NUMERO = Separar[1];
                        if(idmueble.equals(SID)) {
                            nueva += SID+";"+(Integer.parseInt(NUMERO)+1)+"::";
                            encuentro = true;
                        } else {
                            nueva += SID+";"+NUMERO+"::";
                        }
                    }
                    if(encuentro == false) {
                        nueva += idmueble+";1::";
                    }
                } else {
                    nueva += idmueble+";1::";
                }
            } else {
                 return "ComprarMueble,NoMoney";
            }
            String finale = nueva.substring(0, nueva.length()-2);
            if(!finale.equals("mismuebles")) {
                String query = "UPDATE users SET muebles = '"+finale+"' WHERE nombre = '"+user+"'";
                String query2 = "UPDATE users SET fichas = '"+resta+"' WHERE nombre = '"+user+"'";
                PreparedStatement preparedStmt = conexion.prepareStatement(query);
                PreparedStatement preparedStmt2 = conexion.prepareStatement(query2);
                preparedStmt.executeUpdate();
                preparedStmt2.executeUpdate();
                resultado = "ComprarMueble,ok,"+resta;
            }
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "ComprarMueble,NoQuery";
        }
        return resultado;
    }
    public String MiInventario(String user) {
       String resultado = "";
       String miniatura = "";
       String nombre = "";
       String descripcion = "";
       String finale = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT muebles FROM users WHERE nombre='"+user+"'");
            if(rs.next()) {
                String muebles = rs.getString("muebles"); //1;1::4;1
                if(!"".equals(muebles)) {
                    String[] cadamueble = muebles.split("::"); //1;1
                    for(int a=0; a<cadamueble.length; a++) {
                        System.out.println("CADAMUEBLE :"+cadamueble[a]);
                        String[] datosmueble = cadamueble[a].split(";");
                        String idmueble = datosmueble[0];
                        String cantidad = datosmueble[1];
                        Statement cmd2 = conexion.createStatement();
                        ResultSet rs2 = cmd2.executeQuery("SELECT miniatura, mueble, descripcion FROM catalogo WHERE id='"+idmueble+"'");
                        if(rs2.next()) {
                            miniatura = rs2.getString("miniatura");
                            nombre = rs2.getString("mueble");
                            descripcion = rs2.getString("descripcion");
                        }
                        resultado += idmueble+";"+cantidad+";"+miniatura+";"+nombre+";"+descripcion+"::";
                    }
                }
            }
            if(resultado.length()>0) {
                finale = resultado.substring(0, resultado.length()-2);
            } else {
                finale = ",";
            }
            return "MiInventario,"+finale;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "MiInventario,NoQuery";
        }
        return resultado;
    }
    public String ConsultarPlacas(String user) {
       String resultado = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT placas, puesta, visible FROM users WHERE nombre='"+user+"'");
            while(rs.next()) {
                resultado += rs.getString("placas")+","+rs.getString("puesta")+","+rs.getString("visible");
            }
            return "ConsultarPlacas,"+resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "ConsultarPlacas,NoQuery";
        }
        return resultado;
    }
    public String PonerPlaca(String Placa, String User) {
        String resultado = "";
        try {
            String query = "UPDATE users SET puesta='"+Placa+"' WHERE nombre='"+User+"'";
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            preparedStmt.executeUpdate();
            resultado = "PonerPlaca,ok";
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "PonerPlaca,NoQuery";
        }
        return resultado;
    }
    public String QuitarPlaca(String User) {
        String resultado = "";
        try {
            String query = "UPDATE users SET puesta='' WHERE nombre='"+User+"'";
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            preparedStmt.executeUpdate();
            resultado = "QuitarPlaca,ok";
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "QuitarPlaca,NoQuery";
        }
        return resultado;
    }
    public String MostrarPlaca(String User, String Visible) {
        String resultado = "";
        try {
            String query = "UPDATE users SET visible='"+Visible+"' WHERE nombre='"+User+"'";
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            preparedStmt.executeUpdate();
            resultado = "MostrarPlaca,ok";
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "MostrarPlaca,NoQuery";
        }
        return resultado;
    }
    public String CambiarRopa(String User, String ide, String pelo, String camisa, String pants, String zapatos) {
        String resultado = "";
        try {
            String query = "UPDATE users SET pelo='"+pelo+"' WHERE nombre='"+User+"'";
            String query2 = "UPDATE users SET camisa='"+camisa+"' WHERE nombre='"+User+"'";
            String query3 = "UPDATE users SET pants='"+pants+"' WHERE nombre='"+User+"'";
            String query4 = "UPDATE users SET zapatos='"+zapatos+"' WHERE nombre='"+User+"'";
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            PreparedStatement preparedStmt2 = conexion.prepareStatement(query2);
            PreparedStatement preparedStmt3 = conexion.prepareStatement(query3);
            PreparedStatement preparedStmt4 = conexion.prepareStatement(query4);
            preparedStmt.executeUpdate();
            preparedStmt2.executeUpdate();
            preparedStmt3.executeUpdate();
            preparedStmt4.executeUpdate();
            resultado = "CambiarRopa,"+ide+","+pelo+","+camisa+","+pants+","+zapatos;
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "CambiarRopa,NoQuery";
        }
        return resultado;
    }
    public String ActualizarMiSala(String User, String Sala) {
        String resultado = "";
        try {
            String query = "UPDATE users SET estoyen='"+Sala+"' WHERE nombre='"+User+"'";
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            preparedStmt.executeUpdate();
            resultado = "ActualizarMiSala,ok";
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "ActualizarMiSala,NoQuery";
        }
        return resultado;
    }
    public String PosicionarMueble(String User, String varx, String vary, String idmueble, String sala, String posicion) {
        String resultado = "";
        String array = "";
        String MueblesSala = "";
        String MueblesUser = "";
        String NuevoArray = "";
        String finale = "";
        String finale2 = "";
        try {
            ////////////////////
            array = idmueble+";"+posicion+";"+varx+";"+vary;
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT * FROM rooms WHERE id='"+sala+"'");
            if(rs.next()) {
                MueblesSala = rs.getString("muebles");
            }
            Statement cmd2 = conexion.createStatement();
            ResultSet rs2 = cmd2.executeQuery("SELECT * FROM users WHERE nombre='"+User+"'");
            if(rs2.next()) {
                MueblesUser = rs2.getString("muebles");
            }
            if(!MueblesSala.equals("")) {
                NuevoArray = MueblesSala+"::"+array;
            } else {
                NuevoArray = array;
            }
            String query = "UPDATE rooms SET muebles='"+NuevoArray+"' WHERE id='"+sala+"'";
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            preparedStmt.executeUpdate();
            String[] CadaMueble = MueblesUser.split("::");
            for(int a=0; a<CadaMueble.length; a++) {
                String[] mirar = CadaMueble[a].split(";");
                if(idmueble.equals(mirar[0])) {
                    int cuantos = Integer.parseInt(mirar[1])-1;
                    if(cuantos >= 1) {
                        finale += idmueble+";"+cuantos+"::";
                    }
                } else {
                    finale += mirar[0]+";"+mirar[1]+"::";
                }
            }
            if(!"".equals(finale)) {
                finale2 = finale.substring(0, finale.length()-2);
            } else {
                finale2 = finale;
            }
            String query2 = "UPDATE users SET muebles='"+finale2+"' WHERE nombre='"+User+"'";
            PreparedStatement preparedStmt2 = conexion.prepareStatement(query2);
            preparedStmt2.executeUpdate();
            resultado = "PosicionarMueble,ok";
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "PosicionarMueble,NoQuery";
        }
        return resultado;
    }
    public String PedirMuebles(String sala) {
        String resultado = "";
        String MueblesSala = "";
        String Nombremueble = "";
        String Descripcionmueble = "";
        String Tipomueble = "";
        String finale = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT muebles FROM rooms WHERE id='"+sala+"'");
            if(rs.next()) {
                MueblesSala = rs.getString("muebles");
                String[] CadaMueble = MueblesSala.split("::");
                for(int a=0; a<CadaMueble.length; a++) {
                    String[] mirar = CadaMueble[a].split(";");
                    Statement cmd3 = conexion.createStatement();
                    ResultSet rs3 = cmd3.executeQuery("SELECT * FROM catalogo WHERE id='"+mirar[0]+"'");
                    if(rs3.next()) {
                        Nombremueble = rs3.getString("mueble");
                        Descripcionmueble = rs3.getString("descripcion");
                        Tipomueble = rs3.getString("tipo");
                        resultado += mirar[0]+";"+mirar[1]+";"+mirar[2]+";"+mirar[3]+";"+Nombremueble+";"+Descripcionmueble+";"+Tipomueble+"::";
                    }
                        
                }
            }
            return "SAL,"+sala+","+resultado+",";
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "SAL,NoQuery";
        }
        return resultado;
    }
    public String ConsultarPlacaUsuario(String User) {
        String resultado = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT puesta, visible FROM users WHERE nombre='"+User+"'");
            if(rs.next()) {
                resultado = rs.getString("puesta")+","+rs.getString("visible");
            }
            return "ConsultarPlacaUsuario,"+User+","+resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "ConsultarPlacaUsuario,NoQuery";
        }
        return resultado;
    }
    public String Echar(String User, String Objetivo, String idu, String Sala) {
        String resultado = "";
        String rango = "";
        String owner = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT rango FROM users WHERE nombre='"+User+"'");
            Statement cmd2 = conexion.createStatement();
            ResultSet rs2 = cmd2.executeQuery("SELECT id FROM rooms WHERE owner='"+User+"'");
            if(rs2.next()) {
                owner = rs2.getString("id");
            }
            if(rs.next()) {
                rango = rs.getString("rango");
                if(rango.equals("GUI") || rango.equals("MOD") || rango.equals("ADM") || owner.equals(Sala)) {
                    String query = "UPDATE users SET estoyen='' WHERE nombre='"+Objetivo+"'";
                    PreparedStatement preparedStmt = conexion.prepareStatement(query);
                    preparedStmt.executeUpdate();
                    resultado = "Echar,"+Objetivo+","+idu+","+Sala;
                } else {
                    resultado = "Echar,"+User+",NoPowers";
                }
            }
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "Echar,"+User+",NoQuery";
        }
        return resultado;
    }
    public String Alertar(String User, String Objetivo, String mensaje) {
        String resultado = "";
        String rango = "";
        String owner = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT rango FROM users WHERE nombre='"+User+"'");
            if(rs.next()) {
                rango = rs.getString("rango");
                if(rango.equals("GUI") || rango.equals("MOD") || rango.equals("ADM")) {
                    resultado = "Alertar,"+User+","+Objetivo+":::"+mensaje;
                } else {
                    resultado = "Alertar,"+User+",NoPowers";
                }
            }
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
            resultado = "Alertar,"+User+",NoQuery";
        }
        return resultado;
    }
    public String GirarMueble(String User, String sala, String mueble) {
        String resultado = "";
        String finale = "";
        String nuevapos = "";
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT * FROM rooms WHERE id='"+sala+"'");
            if(rs.next()) {
                if(rs.getString("owner").equals(User)) {
                    String[] partesmueble = mueble.split("_");
                    String idmueble = partesmueble[1];
                    String posmueble = partesmueble[2];
                    String lax = partesmueble[3];
                    String lay = partesmueble[4];
                    String busco = idmueble+";"+posmueble+";"+lax+";"+lay;
                    String cadenamuebles[] = rs.getString("muebles").split("::");
                    for(int a=0; a<cadenamuebles.length; a++) {
                        String[] posbd = cadenamuebles[a].split(";");
                        if(busco.equals(cadenamuebles[a])) {
                            if("1".equals(posbd[1])) {
                                nuevapos += idmueble+";2;"+lax+";"+lay+"::";
                            } else if("2".equals(posbd[1])) {
                                nuevapos += idmueble+";3;"+lax+";"+lay+"::";
                            } else if("3".equals(posbd[1])) {
                                nuevapos += idmueble+";4;"+lax+";"+lay+"::";
                            } else if("4".equals(posbd[1])) {
                                nuevapos += idmueble+";1;"+lax+";"+lay+"::";
                            }
                        } else {
                            nuevapos += cadenamuebles[a]+"::";
                        }
                    }
                    
                    if(nuevapos.length()>2) {
                        nuevapos = nuevapos.substring(0, nuevapos.length()-2);
                    }
                    String query = "UPDATE rooms SET muebles='"+nuevapos+"' WHERE id='"+sala+"'";
                    PreparedStatement preparedStmt = conexion.prepareStatement(query);
                    preparedStmt.executeUpdate();
                    resultado = "GirarMueble,"+User+","+sala+","+nuevapos;
                } else {
                    resultado = "GirarMueble,"+User+","+sala+",NoPowers";
                }
            }
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    public String RecogerMueble(String User, String sala, String mueble) {
        String resultado = "";
        String finale = "";
        String nuevapos = "";
        String MueblesUser = "";
        String finale2 = "";
        Boolean encontrado = false;
        try {
            cmd = conexion.createStatement();
            rs = cmd.executeQuery("SELECT * FROM rooms WHERE id='"+sala+"'");
            if(rs.next()) {
                if(rs.getString("owner").equals(User)) {
                    String[] partesmueble = mueble.split("_");
                    String idmueble = partesmueble[1];
                    Statement cmd2 = conexion.createStatement();
                    ResultSet rs2 = cmd2.executeQuery("SELECT * FROM users WHERE nombre='"+User+"'");
                    if(rs2.next()) {
                        MueblesUser = rs2.getString("muebles");
                        String[] CadaMueble = MueblesUser.split("::");
                        if(!"".equals(MueblesUser)) {
                            for(int a=0; a<CadaMueble.length; a++) {
                                String[] mirar = CadaMueble[a].split(";");
                                if(idmueble.equals(mirar[0])) {
                                    int cuantos = Integer.parseInt(mirar[1])+1;
                                    if(cuantos >= 1) {
                                        finale += idmueble+";"+cuantos+"::";
                                        encontrado = true;
                                    }
                                } else {
                                    finale += mirar[0]+";"+mirar[1]+"::";
                                }
                            }
                        } else {
                            finale += idmueble+";1::";
                        }
                        if(encontrado == false) {
                            finale += idmueble+";1::";
                        }
                        if(!"".equals(finale)) {
                            finale2 = finale.substring(0, finale.length()-2);
                        } else {
                            finale2 = finale;
                        }
                        String query2 = "UPDATE users SET muebles='"+finale2+"' WHERE nombre='"+User+"'";
                        PreparedStatement preparedStmt2 = conexion.prepareStatement(query2);
                        preparedStmt2.executeUpdate();
                    }
                    String posmueble = partesmueble[2];
                    String lax = partesmueble[3];
                    String lay = partesmueble[4];
                    String busco = idmueble+";"+posmueble+";"+lax+";"+lay;
                    String cadenamuebles[] = rs.getString("muebles").split("::");
                    for(int a=0; a<cadenamuebles.length; a++) {
                        String[] posbd = cadenamuebles[a].split(";");
                        if(!busco.equals(cadenamuebles[a])) {
                            nuevapos += cadenamuebles[a]+"::";
                        }
                    }
                    
                    if(nuevapos.length()>2) {
                        nuevapos = nuevapos.substring(0, nuevapos.length()-2);
                    }
                    String query = "UPDATE rooms SET muebles='"+nuevapos+"' WHERE id='"+sala+"'";
                    PreparedStatement preparedStmt = conexion.prepareStatement(query);
                    preparedStmt.executeUpdate();
                    resultado = "RecogerMueble,"+User+","+sala+","+nuevapos;
                } else {
                    resultado = "RecogerMueble,"+User+","+sala+",NoPowers";
                }
            }
            return resultado;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
