package ejemplored;

/**
 *
 * @author Ángel Serrano García
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        utilidades.Conexion conn = new utilidades.Conexion();
        
        if(conn.clientConnect(utilidades.UIUtilities.getString("Ip"))){
            int a = (int) (Math.random()*100 % 6) + 1;
            utilidades.UIUtilities.P("Has sacado: " + a);
            modelo.Message m = new modelo.Message();
            m.setNumero(a);
            if(conn.sendObject(m)){
                String s = (String) conn.reciveObject();
                if(s != null){
                    utilidades.UIUtilities.P(s);
                }else{
                    utilidades.UIUtilities.P("¡Algo no ha ido bien con la conexión!");
                }
            }else{
                utilidades.UIUtilities.P("¡Error de conexión!");
            }
        }
            conn.close();
    }
}
