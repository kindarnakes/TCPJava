package modelo;

import java.io.Serializable;

/**
 *
 * @author Ángel Serrano García
 */
public class Message implements Serializable{
    
    private String _s;
    private double _numero;

    
    public String getString() {
        return _s;
    }

    public void setString(String _s) {
        this._s = _s;
    }

    public double getNumero() {
        return _numero;
    }

    public void setNumero(double numero) {
        this._numero = numero;
    }
    
    public void setNumero(int numero) {
        this._numero = numero;
    }
    
    public void setNumero(float numero) {
        this._numero = numero;
    }
    
    
    
}
