package utilidades;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ángel Serrano García
 */
public class Conexion {

    private ServerSocket _server;
    private Socket _sc;
    private static int PORT = 53000;
    private ObjectInputStream _objectin;
    private ObjectOutputStream _objectout;

    /**
     * Inicia la conexion arrancando el modo servidor.
     *
     * @return Devuelve si se ha completado la conexion o no.
     */
    public boolean serverOn() {
        boolean connect_on = false;
        boolean connect_port = false;

        try {
            _server = new ServerSocket(PORT);
            connect_on = true;

        } catch (IOException ex) {
            UIUtilities.P("No se ha podido conectar.");
        } catch (Exception e) {
            UIUtilities.P("Algo inesperado ocurrio ...");
        }

        UIUtilities.P("Esperando conexion ...");

        try {
            _sc = _server.accept();
            if (_sc != null) {
                
                OutputStream outputStream = _sc.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                _objectout = objectOutputStream;
                
                InputStream inputStream = _sc.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                _objectin = objectInputStream;
                
                UIUtilities.P((String) _objectin.readObject());
                _objectout.writeObject("Conectado");
                connect_port = true;
            }

        } catch (IOException ex) {
            UIUtilities.P("No se ha podido conectar.");
        } catch (Exception e) {
            UIUtilities.P("Algo inesperado ocurrio ...");
        }

        return connect_on && connect_port;
    }

    /**
     * Inicia la conexion en modo cliente.
     *
     * @param ip Representa la ip a la que queremos conectar.
     * @return Devuelve si ha podido completar la conexion o no.
     */
    public boolean clientConnect(String ip) {
        boolean connect_on = false;

        try {
            _sc = new Socket(ip, PORT);
            if (_sc != null) {
                
                OutputStream outputStream = _sc.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                _objectout = objectOutputStream;
                
                InputStream inputStream = _sc.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                _objectin = objectInputStream;
                
                _objectout.writeObject("Conectado");
                UIUtilities.P((String) _objectin.readObject());
                connect_on = true;
            }

        } catch (IOException ex) {
            UIUtilities.P("No se ha podido conectar.");
        } catch (Exception e) {
            UIUtilities.P("Algo inesperado ocurrio ...");
        }

        return connect_on;
    }

    /**
     * Envia un mensaje por red.
     *
     * @param s El mensaje a enviar.
     * @return Devuelve si ha podido enviarlo o no.
     */
    public boolean send(String s) {
        boolean send = false;

        try {
            _objectout.writeUTF(s);
            send = true;
        } catch (IOException ex) {
            UIUtilities.P("¡No estas conectado!");
        }

        return send;

    }

    /**
     * Recibe un mensaje por red.
     *
     * @return Devuelve el mensaje leido o cadena vacía si no se ha podido leer
     * nada.
     */
    public String recive() {
        String recived = "";

        try {
            recived = _objectin.readUTF();
        } catch (IOException ex) {
            UIUtilities.P("¡No estas conectado!");
        }

        return recived;
    }

    /**
     * Cierra una conexión.
     */
    public void close() {
        if (_server != null) {

            try {
                _server.close();
            } catch (IOException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (_sc != null) {

            try {
                _sc.close();
            } catch (IOException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Envia un objeto por red.
     *
     * @param o El objeto a enviar.
     * @return Devuelve si ha podido enviarlo o no.
     */
    public boolean sendObject(Object o) {
        boolean send = false;

        try {
            _objectout.writeObject(o);
            send = true;
        } catch (IOException ex) {
            UIUtilities.P("¡No estas conectado!");
        }

        return send;

    }

    /**
     * Recibe un objeto por red.
     *
     * @return el objeto recibido
     */
    public Object reciveObject() {
        Object recived = null;

        try {
            try {
                recived = _objectin.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            UIUtilities.P("¡No estas conectado!");
        }

        return recived;
    }

}
