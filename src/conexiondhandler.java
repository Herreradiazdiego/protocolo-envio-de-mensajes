
import  java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

import java.util.logging.Logger;

public class conexiondhandler implements Runnable {

    public static final String CLASS_NAME = conexiondhandler.class.getSimpleName();

    public static final Logger LOGGER = Logger.getLogger(CLASS_NAME);


    private adminusuario users;

    private Socket socketcl = null;

    private BufferedReader entrada;

    private PrintWriter salida;


    public conexiondhandler(adminusuario ni, Socket mai) {
        users = ni;
        socketcl = mai;

        try {

            entrada = new BufferedReader


                    (new InputStreamReader(socketcl.getInputStream()));


            salida = new PrintWriter



                    (socketcl.getOutputStream(), true);



        } catch (IOException e) {



            LOGGER.severe(e.getMessage());



            e.printStackTrace();
        }
    }




    @Override
    public void run() {

        String buffer = null ;



        while (true) {

            try {

                buffer =

                        entrada.readLine();


            } catch (IOException e) {


                LOGGER.severe(e.getMessage());


                e.printStackTrace();


            }
            String ord = buffer.trim();



            if( ord.startsWith("CONNECT") ) {
                String usuarioNom =
                        ord.substring(ord.indexOf(' ')  ).trim();


                System.out.println(usuarioNom);


                boolean estable =  users.connect(usuarioNom, socketcl);





                if( estable ) { salida.println("Se ha conectado");




                } else {salida.println("Fallo de conexion");

                }
            }


            if( ord.startsWith("Enviar") ) {


                Set<String> userSet
                        = users.listus();


                String msj =
                        ord.substring(ord.indexOf('#')+1,
                        ord.indexOf('@') );
                System.out.println(msj);



                String nomusuar = ord.substring(ord.indexOf('@')+1 ).trim();



                System.out.println(nomusuar);



                if (userSet.contains(nomusuar)){



                    if (msj.length() < 200) {



                        salida.println(users.consuser(socketcl)
                                + "  : " + msj);


                        users.send(users.consuser(socketcl) + "  : "
                                + msj, nomusuar);

                    }
                    else {
                        salida.println("ha sobrepasado el limite de 200 caracteres");
                    }
                } else {



                    System.out.println(nomusuar
                            + " esta desconectado");


                    salida.println(nomusuar
                            + " esta desconectado");
                }

            }





            if( ord.startsWith
                    ("desconectar") ) {


                String userName = ord.substring(ord.indexOf(' ')  ).trim();


                boolean estaconectado=users.disconnect(userName);

                if(!estaconectado ) {
                    salida.println("si");
                } else {
                    salida.println("error");
                }
            }



            if( ord.startsWith("lista") ) {
                Set<String> userSet = users.listus();
                if (userSet.isEmpty())
                    salida.println("no hay nadie conectado para enviar mensaje");





                for (String user : userSet) {



                    salida.println(user);


                }
            }

        }
    }
}