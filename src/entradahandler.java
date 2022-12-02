import java.io.BufferedReader;

import java.io.IOException;

public class entradahandler implements Runnable {

    private BufferedReader in;

    public entradahandler(BufferedReader superzx) {
        in = superzx;
    }
    @Override


    public void run() {
        String msj =
                null;
        while (true) {

            try {

                if ((msj = in.readLine()) == null) break;

            } catch (IOException e) {

                e.printStackTrace();

            }

            if( msj.trim().equals(".") ) {

                System.out.println(msj);

                break;

            }   else {

                System.out.println(msj);
            }
        }
    }
}