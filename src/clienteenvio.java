import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class clienteenvio {



    public static final String CLASS_NAME = clienteenvio.class.getSimpleName();


    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);



    public static final int PORT = 1818;



    public static void main(String[] args) {

        String nombrehost = null;
        int numport = 1818;

        if (args.length != 2) {
            System.err.println(



                    "Usage: java SendClient <host name> <port number>");
            nombrehost = "localhost";
            numport = 1818;

        } else {
            nombrehost = args[0];
            numport = Integer.parseInt(args[1]);
        }

        try {
            Socket socket = new Socket(nombrehost, numport);


            PrintWriter out
                    = new PrintWriter( socket.getOutputStream() , true);

            BufferedReader in
                    = new BufferedReader(
                    new InputStreamReader( socket.getInputStream() ));

            entradahandler inputHandler = new entradahandler(in);



            Thread t = new Thread(inputHandler);
            t.start();




            BufferedReader keys
                    = new BufferedReader(
                    new InputStreamReader(System.in));


            String userInput;
            while ((userInput = keys.readLine()) != null) {
                if( userInput.trim().equals(".") ) {
                    out.println(userInput);
                    break;
                }   else {
                    out.println(userInput);
                }
            }




        } catch (UnknownHostException e) {
            System.err.println("Se desconoce el host" + nombrehost);
            System.exit(1);






        } catch (IOException e) {
            System.err.println("no se consiguio la conexion I/O para el host " + nombrehost);
            System.exit(1);
        }

    }
}