import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class mensaje {
    public static final String CLASS_NAME = mensaje.class.getSimpleName();

    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);


    public static final int PORT = 1818;


    public static void main(String args[]) {

        int numport;
        if (args.length != 1) {

            System.err.println("Usage: java MessageServer <port number>");

            numport = PORT;

        } else {

            numport = Integer.parseInt(args[0]);

        }

        adminusuario users = new adminusuario();


        try {
            ServerSocket serverSocket = new ServerSocket( numport );

            while(true) {

                Socket clientSocket =
                        serverSocket.accept();

                LOGGER.info("conexion: " +
                        clientSocket.getRemoteSocketAddress().toString());

                Thread serviceProcess = new Thread( new conexiondhandler(users, clientSocket)) ;
                serviceProcess.start();
            }

        } catch (IOException e) {


            System.out.println("Excepción detectada al intentar esperar en el puerto o por conexion" + numport);


            System.out.println(e.getMessage());
        }
    }
}