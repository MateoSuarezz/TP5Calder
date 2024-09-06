package colecciones.arbol;

import java.util.*;

/**
 * Una implementación de {@code Diccionario} mediante nodos encadenados que
 * preserva,
 * las propiedades de ABB y ademas mantiene el arbol balanceado, es decir,
 * las alturas de los subárboles izquierdo y derecho de cada nodo difieren como
 * máximo en uno.
 * 
 * @param <T> Tipo del valor asociado a los nodos del árbol, debe ser posible
 *            definir un orden total para los mismos.
 * @see NodoBinario
 */
public class Avl<T> implements Diccionario<T> {

    private NodoBinario<T> raiz;

    /**
     * Comparador usado para mantener el orden en un ABB, o null.
     */
    private final Comparator<? super T> comparador;

    private int elementos;
    /**
     * Construye un nuevo árbol vacío ordenado acorde al comparador dado.
     *
     * @param comparador define una forma de comparar los valores insertados en el
     *                   arbol.
     */
    public Avl(Comparator<? super T> comparador) {
        this.comparador = comparador;
        this.raiz = null;
        this.elementos = 0;
    }

    /**
     * Construye un nuevo árbol con un elemento en la raiz, ordenado acorde al
     * comparador dado.
     *
     * @param comparador define una forma de comparar los valores insertados en el
     *                   arbol.
     * @param valor      de la raiz del nuevo arbol si no es null.
     */
    public Avl(Comparator<? super T> comparador, T valor) {
        raiz = new NodoBinario<>(valor);
        this.comparador = comparador;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void insertar(T elem) {
        raiz = insertarAux(elem, raiz);
        this.elementos++;
    }

    private NodoBinario<T> insertarAux(T val, NodoBinario<T> arbol) {
        if (arbol == null) {
            return new NodoBinario<>(val);
        } else {
            int x = comparador.compare(arbol.getValor(), val);
            if (x < 0)
                arbol.setDerecho(subArbolDerecho().insertarAux(val, arbol.getDerecho()));
            else if (x > 0)
                arbol.setIzquierdo(subArbolIzquierdo().insertarAux(val, arbol.getIzquierdo()));
            else
                return arbol;
        }
        arbol = rotar(arbol);
        arbol.setAltura(1 + Math.max(subArbolIzquierdo().altura(), subArbolDerecho().altura()));
        return arbol;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean pertenece(T elem) {
        if (raiz == null)
            return false;
        int x = comparador.compare(raiz.getValor(), elem);
        if (x == 0)
            return true;
        else
            return (this.subArbolIzquierdo().pertenece(elem) || this.subArbolDerecho().pertenece(elem));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void borrar(T elem) {
        raiz = sacar(elem, raiz);
        this.elementos--;
    }

    private NodoBinario<T> sacar(T val, NodoBinario<T> aux) {
        if (aux == null)
            return null;
        int x = comparador.compare(aux.getValor(), val);
        if (x < 0)
            aux.setDerecho(subArbolDerecho().sacar(val, aux.getDerecho()));
        else if (x > 0)
            aux.setIzquierdo(subArbolIzquierdo().sacar(val, aux.getIzquierdo()));
        else {
            if (aux.getIzquierdo() == null)
                return aux.getDerecho();
            else if (aux.getDerecho() == null)
                return aux.getIzquierdo();
            aux.setValor(subArbolIzquierdo().mayorValor());
            aux.setIzquierdo(subArbolIzquierdo().sacar(aux.getValor(), aux.getIzquierdo()));
        }
        aux = rotar(aux);
        aux.setAltura(1 + Math.max(subArbolIzquierdo().altura(), subArbolDerecho().altura()));
        return aux;
    }

    /** {@inheritDoc} */
    @Override
    public void vaciar() {
        raiz = null;
    }

    /** {@inheritDoc} */
    @Override
    public T raiz() {
        if (raiz == null)
            return null;
        else
            return raiz.getValor();
    }

    /** {@inheritDoc} */
    @Override
    public Avl<T> subArbolIzquierdo() {
        Avl<T> subIzq = new Avl<T>(comparador);
        if (this.raiz == null)
            return subIzq;
        else
            subIzq.raiz = raiz.getIzquierdo();
        return subIzq;
    }

    /** {@inheritDoc} */
    @Override
    public Avl<T> subArbolDerecho() {
        Avl<T> subDer = new Avl<T>(comparador);
        if (this.raiz == null)
            return subDer;
        else
            subDer.raiz = raiz.getDerecho();
        return subDer;
    }

    /** {@inheritDoc} */
    @Override
    public int elementos() {
        return elementos;
    }

    /** {@inheritDoc} */
    @Override
    public int altura() {
        if (esVacio())
            return 0;
        else
            return 1 + Math.max(subArbolDerecho().altura(), subArbolIzquierdo().altura());
    }

    /** {@inheritDoc} */
    @Override
    public boolean esVacio() {
        return raiz == null;
    }

    /** {@inheritDoc} */
    @Override
    public T mayorValor() {
        if (esVacio())
            return null;
        if (raiz.getDerecho() == null)
            return raiz.getValor();
        else {
            return subArbolDerecho().mayorValor();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T menorValor() {
        if (esVacio())
            return null;
        if (raiz.getIzquierdo() == null)
            return raiz.getValor();
        else {
            return subArbolIzquierdo().menorValor();
        }
    }

    /** {@inheritDoc} */
    @Override
    public T sucesor(T elem) {
        LinkedList<T> lista = new LinkedList<>();
        lista = (LinkedList<T>) aLista();
        boolean fin = true;
        T retorno = null;

        for (int i = 1; i <= elementos() && fin; i++) {
            if (lista.getFirst() == elem) {
                lista.removeFirst();
                retorno = lista.getFirst();
                fin = false;
            } else
                lista.removeFirst();
        }
        return retorno;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T predecesor(T elem) {
        return predecesor(elem, this.raiz, null);
    }

    private T predecesor(T elem, NodoBinario<T> raiz, NodoBinario<T> padre) {
        if (raiz == null) {
            throw new IllegalArgumentException("No tiene predecesor");
        } else {
            int i = comparador.compare(raiz.getValor(), elem);
            if (i == 0) {
                if (raiz.getIzquierdo() != null) {
                    return maxValue(raiz.getIzquierdo());
                } else {
                    return padre.getValor();
                }
            }
            if (i < 0) {
                return predecesor(elem, raiz.getDerecho(), raiz);
            }
            if (i > 0) {
                return predecesor(elem, raiz.getIzquierdo(), padre);
            }
            return elem;
        }
    }

    private T maxValue(NodoBinario<T> raiz) {
        if (raiz == null) {
            throw new IllegalStateException("El árbol está vacío.");
        } else {
            while (raiz.getDerecho() != null) {
                raiz = raiz.getDerecho();
            }
            return raiz.getValor();
        }
    }

    /**
     * obtiene el balance del arbol, es decir, la diferencia de altura de sus
     * subarboles.
     * 
     * @return diferencia de altura de los subarboles.
     */
    public int balance() {
        if (esVacio())
            return 0;
        else
            return this.subArbolIzquierdo().altura() - this.subArbolDerecho().altura();
    }

    /**
     * Esta función es extra para poder generar con facilidad la búsqueda en mi Torneo.
     * @param elemento
     * @return el elemento buscado, si no se encuentra retorna null.
    */

    public T obtener(T elemento) {
        return obtener(this.raiz, elemento);
    }
    
    private T obtener(NodoBinario<T> nodo, T elemento) {
        if (nodo == null) {
            return null;
        }
        int comparacion = comparador.compare(elemento, nodo.getValor());
    
        if (comparacion == 0) {
            return nodo.getValor();
        } else if (comparacion < 0) {
            return obtener(nodo.getIzquierdo(), elemento);
        } else {
            return obtener(nodo.getDerecho(), elemento);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean repOK() {
      boolean resultado = true;
      
      if(!(subArbolIzquierdo() instanceof Diccionario) || !(subArbolDerecho() instanceof Diccionario))
        return false;
      if(esVacio())
        return elementos() == 0;
      if(balance() < -1 || balance() > 1)
        return false;
      Set nodoVisto = new HashSet();
      LinkedList nodoSiguiente = new LinkedList();
      nodoVisto.add(raiz);
      nodoSiguiente.add(raiz);
      
      while(!nodoSiguiente.isEmpty()){
        NodoBinario<T> nodoActual = (NodoBinario<T>) nodoSiguiente.removeFirst();
        if(nodoActual.getIzquierdo() != null){
          if(!nodoVisto.add(nodoActual.getIzquierdo()))
            return false;
          nodoSiguiente.add(nodoActual.getIzquierdo());
        }
        if(nodoActual.getDerecho() != null){
          if(!nodoVisto.add(nodoActual.getDerecho()))
            return false;
          nodoSiguiente.add(nodoActual.getDerecho());
        }
      }
      
      resultado &= elementos() == nodoVisto.size();
      resultado &= lugarCorrecto((Avl<T>) subArbolIzquierdo(), raiz.getValor(),(Avl<T>) subArbolDerecho());
      resultado &= subArbolDerecho().repOK() && subArbolIzquierdo().repOK();
      
      return resultado;
    }
    
    private boolean lugarCorrecto(Avl<T> a, T b, Avl<T> c){
        boolean val = true;
        
         LinkedList<T> lista = new LinkedList<>();
         lista = (LinkedList<T>) a.aLista();
      if(a != null){
        while(!lista.isEmpty()){
            val &= comparador.compare(b,lista.getFirst()) > 0;
          lista.removeFirst();
        }
      }
         lista = null;
         lista = (LinkedList<T>) c.aLista();
      if(c != null){
        while(!lista.isEmpty()){
            val &= comparador.compare(b,lista.getFirst()) < 0;
          lista.removeFirst();
        }
      }
      return val;
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        String xtr = "";
        if (!esVacio()) {
            LinkedList<T> lista = new LinkedList<>();
            lista = (LinkedList<T>) aLista();
            xtr = xtr + "\nInorder: ";
            for (int i = 1; i <= elementos(); i++) {
                xtr = xtr + lista.getFirst() + " ";
                lista.removeFirst();
            }

            lista = (LinkedList<T>) aLista(Orden.POSTORDER);
            xtr = xtr + "\nPostorder: ";
            for (int i = 1; i <= elementos(); i++) {
                xtr = xtr + lista.getFirst() + " ";
                lista.removeFirst();
            }

            lista = (LinkedList<T>) aLista(Orden.PREORDER);
            xtr = xtr + "\nPreorder: ";
            for (int i = 1; i <= elementos(); i++) {
                xtr = xtr + lista.getFirst() + " ";
                lista.removeFirst();
            }
        } else {
            return "Arbol vacio, no contiene elementos";
        }
        return xtr;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object other) {
        if (this == other) 
            return true;

        if (other == null) 
            return false;

        if (!(other instanceof Avl<?>)) 
            return false;

        Avl<T> otherAvl = (Avl<T>) other;

        if (this.elementos() != otherAvl.elementos()) 
            return false;

        if (altura() != otherAvl.altura()) 
            return false;

        return this.aLista().equals(otherAvl.aLista()) ;

    }

    /** {@inheritDoc} */
    @Override
    public List<T> aLista() {
        return aLista(Orden.INORDER);
    }

    /** {@inheritDoc} */
    @Override
    public List<T> aLista(Orden orden) {
        List<T> elementos = new LinkedList<>();
        switch (orden) {
            case INORDER:
                return aListaInOrder(raiz, elementos);
            case PREORDER:
                return aListaPreOrder(raiz, elementos);
            case POSTORDER:
                return aListaPostOrder(raiz, elementos);
        }
        return elementos;
    }

    private List<T> aListaInOrder(NodoBinario<T> raiz, List<T> elementos) {
        if (esVacio())
            return elementos;
        if (raiz.getIzquierdo() != null) {
            elementos = aListaInOrder(raiz.getIzquierdo(), elementos);
        }

        elementos.add(raiz.getValor());

        if (raiz.getDerecho() != null) {
            elementos = aListaInOrder(raiz.getDerecho(), elementos);
        }

        return elementos;
    }

    /*
     * (non-Javadoc)
     * Este método toma un nodo (que puede ser null), una lista de elementos (que no
     * puede ser null)
     * y va llenando la lista con los elementos del árbol según un recorrido pre
     * order.
     * Si bien el prefil está pensando para una implementación recursiva, puede
     * probar con una implementación iterativa.
     */
    private List<T> aListaPreOrder(NodoBinario<T> raiz, List<T> elementos) {
        if (esVacio())
            return elementos;

        elementos.add(raiz.getValor());

        if (raiz.getIzquierdo() != null) {
            elementos = aListaPreOrder(raiz.getIzquierdo(), elementos);
        }

        if (raiz.getDerecho() != null) {
            elementos = aListaPreOrder(raiz.getDerecho(), elementos);
        }

        return elementos;
    }

    private List<T> aListaPostOrder(NodoBinario<T> raiz, List<T> elementos) {
        if (esVacio())
            return elementos;
        if (raiz.getIzquierdo() != null) {
            elementos = aListaPostOrder(raiz.getIzquierdo(), elementos);
        }

        if (raiz.getDerecho() != null) {
            elementos = aListaPostOrder(raiz.getDerecho(), elementos);
        }

        elementos.add(raiz.getValor());

        return elementos;
    }

    private NodoBinario<T> rotar(NodoBinario<T> aux) {
        int balance = this.balance();
        if (balance > 1) {
            if (this.subArbolIzquierdo().balance() < 0) {
                aux.setIzquierdo(subArbolIzquierdo().rotarIzquierda(aux.getIzquierdo()));
            }
            return rotarDerecha(aux);
        }
        if (balance < -1) {
            if (this.subArbolDerecho().balance() > 0) {
                aux.setDerecho(subArbolDerecho().rotarDerecha(aux.getDerecho()));
            }
            return rotarIzquierda(aux);
        }
        return aux;
    }

    private NodoBinario<T> rotarDerecha(NodoBinario<T> aux) {
        if (aux == null)
            return null;
        NodoBinario<T> izq = aux.getIzquierdo();
        if (izq == null) {
            aux.setIzquierdo(null);
            return null;
        } else {
            NodoBinario<T> central = izq.getDerecho();
            aux.setIzquierdo(central);
            izq.setDerecho(aux);

            if (aux.getIzquierdo() == null && aux.getDerecho() != null)
                aux.setAltura(1 + aux.getDerecho().getAltura());
            else if (aux.getDerecho() == null && aux.getIzquierdo() != null)
                aux.setAltura(1 + aux.getIzquierdo().getAltura());
            else if (aux.getDerecho() != null && aux.getIzquierdo() != null)
                aux.setAltura(1 + Math.max(aux.getIzquierdo().getAltura(), aux.getDerecho().getAltura()));

            if (izq.getIzquierdo() == null && izq.getDerecho() != null)
                izq.setAltura(1 + izq.getDerecho().getAltura());
            else if (izq.getDerecho() == null && izq.getIzquierdo() != null)
                izq.setAltura(1 + izq.getIzquierdo().getAltura());
            else if (izq.getDerecho() != null && izq.getIzquierdo() != null)
                izq.setAltura(1 + Math.max(izq.getIzquierdo().getAltura(), izq.getDerecho().getAltura()));
            return izq;
        }
    }

    private NodoBinario<T> rotarIzquierda(NodoBinario<T> aux) {
        if (aux == null)
            return null;
        NodoBinario<T> der = aux.getDerecho();
        if (der == null) {
            aux.setDerecho(null);
            return null;
        } else {
            NodoBinario<T> central = der.getIzquierdo();
            der.setIzquierdo(aux);
            aux.setDerecho(central);
            if (aux.getIzquierdo() == null && aux.getDerecho() != null)
                aux.setAltura(1 + aux.getDerecho().getAltura());
            else if (aux.getDerecho() == null && aux.getIzquierdo() != null)
                aux.setAltura(1 + aux.getIzquierdo().getAltura());
            else if (aux.getDerecho() != null && aux.getIzquierdo() != null)
                aux.setAltura(1 + Math.max(aux.getIzquierdo().getAltura(), aux.getDerecho().getAltura()));

            if (der.getIzquierdo() == null && der.getDerecho() != null)
                der.setAltura(1 + der.getDerecho().getAltura());
            else if (der.getDerecho() == null && der.getIzquierdo() != null)
                der.setAltura(1 + der.getIzquierdo().getAltura());
            else if (der.getDerecho() != null && der.getIzquierdo() != null)
                der.setAltura(1 + Math.max(der.getIzquierdo().getAltura(), der.getDerecho().getAltura()));
            return der;
        }
    }
}