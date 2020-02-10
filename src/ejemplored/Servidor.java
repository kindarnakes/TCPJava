package ejemplored;

import utilidades.Conexion;

/**
 *
 * @author Ángel Serrano García
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexion conn = new Conexion();
        modelo.Message m = null;

        do {
            if (conn.serverOn()) {
                int a, b;
                m = (modelo.Message) conn.reciveObject();
                if (m != null) {
                    a = (int) ((Math.random() * 100) % 6) + 1;
                    b = (int) m.getNumero();
                    String s;
                    if (a > b) {
                        s = a + " > " + b + "\n¡Gana el servidor!\n";
                    } else if(a < b){
                        s = a + " < " + b + "\n¡Gana el cliente!\n";
                    }else{
                        s = a + " = " + b + " Empate";
                    }
                    if (!conn.sendObject(s)) {
                        utilidades.UIUtilities.P("¡Error de conexion!");
                    }
                    utilidades.UIUtilities.P(s);
                }
                conn.close();
            }
        } while (m != null && m.getNumero() < 7);
    }
}
