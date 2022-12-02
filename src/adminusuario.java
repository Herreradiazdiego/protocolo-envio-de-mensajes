import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

public class adminusuario {

    public static final String CLASS_NAME = adminusuario.class.getSimpleName();
    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

    private HashMap<String, Socket> conexion;
    private HashMap<Socket, String> conexiontype2;


    public adminusuario() {
        super();
        conexion = new  HashMap<String, Socket>();
        conexiontype2 = new  HashMap<Socket, String>();

    }

    public boolean connect(String u, Socket socket) {
        boolean result = true;

        Socket s = conexion.put(u,socket);

        conexiontype2.put(socket,u);
        if( s != null) {
            result = false;
        }
        return result;
    }

    public void send(String message, String target) {

        Socket s = conexion.get(target);

        try {

            PrintWriter output = new PrintWriter(s.getOutputStream(), true);
            output.println(message);
        } catch(IOException e) {

            LOGGER.severe(e.getMessage());
            e.printStackTrace();

        }
    }
    public boolean disconnect(String nomuser) {

        boolean result = false;

        conexiontype2.remove(conexion.get(nomuser));
        Socket s = conexion.remove(nomuser);


        if( s == null) {
            result = true;
        }
        return result;
    }

    public Socket get(String user) {




        Socket s = conexion.get(user);




        return s;
    }

    public String consuser(Socket socket) {



        return conexiontype2.get(socket);



    }

    public Set<String> listus() {



        return conexion.keySet();



    }
}