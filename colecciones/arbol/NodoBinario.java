package colecciones.arbol;

/**
 * Clase auxiliar para diccionarios implementados con nodos encadenados.
 */
public class NodoBinario<T>{
    private T valor;
    private NodoBinario<T> izquierdo;
    private NodoBinario<T> derecho;
    private int altura;

    NodoBinario(T valor, NodoBinario<T> izquierdo, NodoBinario<T> derecho){
        this.valor = valor;
        this.izquierdo = izquierdo;
        this.derecho = derecho;

        altura = setAltura(izquierdo, derecho);
    }

    private int setAltura(NodoBinario<T> izquierdo, NodoBinario<T> derecho){
        int alt = 1 + max(setAltura(izquierdo, derecho), setAltura(izquierdo, derecho));

        return alt;
    }

    private int max(int x, int y){
        if(x < y) return y;

        return x;
    }

    public NodoBinario(){
        valor = null;
        izquierdo = null;
        derecho = null;
        altura = 0;
    }

    public NodoBinario(T valor){
        this.valor = valor;
        izquierdo = null;
        izquierdo = null;
        altura = 1;
    }

    public T getValor(){
        return valor;
    }

    void setValor(T nuevoValor){
        valor = nuevoValor;
    }

    public NodoBinario<T> getIzquierdo(){
        return izquierdo;
    }

    void setIzquierdo(NodoBinario<T> nuevoIzquierdo){
        izquierdo = nuevoIzquierdo;
    }

    public NodoBinario<T> getDerecho(){
        return derecho;
    }

    void setDerecho(NodoBinario<T> nuevoDerecho){
	    derecho = nuevoDerecho;
    }

    public int getAltura(){
        return altura;
    }

    public void setAltura(int altura){
        this.altura = altura;
    }

}
