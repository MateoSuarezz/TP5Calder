package colecciones.arbol;

import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;

/**
 * Árbol binario de busqueda (ABB), es una implementación de {@code Diccionario} mediante nodos encadenados que preserva las propiedades de Diccionario.
 * @param <T> Tipo del valor asociado a los nodos del árbol, debe ser posible definir un orden total para los mismos.
 * @see NodoBinario
 */
public class ABB<T> implements Diccionario<T>{
    private NodoBinario<T> raiz;

    /**
     * Comparador usado para mantener el orden en un ABB, o null.
     */
    private final Comparator<? super T> comparador;

    private int elementos;

    private int altura;
    /**
     * Construye un nuevo árbol vacío ordenado acorde al comparador dado.
     * @param comparador define una forma de comparar los valores insertados en el arbol.
     */
    public ABB(Comparator<? super T> comparador){
        this.comparador = comparador;
        this.raiz = null;
        this.elementos = 0;
    }

    /**
     * Construye un nuevo árbol con un elemento en la raiz, ordenado acorde al comparador dado.
     * @param comparador define una forma de comparar los valores insertados en el arbol.
     * @param valor de la raiz del nuevo arbol si no es null.
     */
    public ABB(Comparator<? super T> comparador, T valor){
       	this.comparador = comparador;
        raiz.setValor(valor);
        this.altura = 1;
    }

    public ABB(Comparator<? super T> comparador, NodoBinario<T> arb){
        this.raiz = arb;
        this.comparador = comparador;
    }

    /**
     * {@inheritDoc}
     */
    @Override
       public void insertar(T elem){
        insertarAuxiliar(raiz, elem);
        this.elementos = elementos + 1;
    }

   public NodoBinario <T> insertarAuxiliar (NodoBinario <T> nodo, T valor){
        if (nodo == null){
            NodoBinario<T> aux = new NodoBinario<T>(valor);
            aux.setAltura(1);
            return (aux);
        }
        else{
            if(comparador.compare(valor,nodo.getValor())<0){
                insertarAuxiliar(nodo.getIzquierdo(), valor);
            }
            else if (comparador.compare(valor,nodo.getValor())>0){
                insertarAuxiliar(nodo.getDerecho(), valor);
            }
            else if (comparador.compare(valor,nodo.getValor())==0){
                nodo.setValor(valor);
            }
        }
        return nodo;
   }

    /**
     * {@inheritDoc}
     */
    public boolean pertenece(T elem){
        NodoBinario<T> aux = raiz;
       
        while(aux != null){
            if (comparador.compare(elem,aux.getValor())==0) {
                return true;
            }else if(comparador.compare(elem,aux.getValor())<0){
                aux = aux.getIzquierdo();
            }else{
                aux = aux.getDerecho();
            }
        }
        
        return false;
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void borrar(T elem){
        if(!pertenece(elem)) throw new NullPointerException("El elemento no se encuentra en el arbol");

        if(comparador.compare(elem, raiz.getValor()) == 0){
            NodoBinario<T> nodoPred = buscarNodo(predecesor(raiz.getValor()));
            raiz.setValor(nodoPred.getValor());
            nodoPred = null;
            return;
        }

        NodoBinario<T> aux = raiz;

        while((comparador.compare(elem, aux.getIzquierdo().getValor()) != 0) && (comparador.compare(elem, aux.getDerecho().getValor()) != 0)){
            if(comparador.compare(elem, aux.getValor()) < 0){
                aux = aux.getIzquierdo();
            }
            
            if(comparador.compare(elem, aux.getValor()) > 0){
                aux = aux.getDerecho();
            }
        }
        // termina el ciclo y aux es el padre del que se quiere eliminar

        if(comparador.compare(elem, aux.getValor()) < 0){
            borrado(aux.getIzquierdo(), aux);
        }
        
        if(comparador.compare(elem, aux.getValor()) > 0){
            borrado(aux.getDerecho(), aux);
        }
    }

    private void borrado(NodoBinario<T> nodo, NodoBinario<T> padre){
        if(nodo.getIzquierdo() == null && nodo.getDerecho() == null){   // es hoja
            nodo = null;
        }else if(nodo.getIzquierdo() != null && nodo.getDerecho() != null){     // tiene los dos hijos
            T x = predecesor(nodo.getValor());
            nodo.setValor(x);
            
            NodoBinario<T> aElim = nodo.getIzquierdo();
            while(aElim.getDerecho() != null){
                aElim = aElim.getDerecho();
            }
            aElim = null;
        }else{
            if(nodo.getIzquierdo() == null){        // no tiene hijo izquierdo
                NodoBinario<T> aux = nodo;
                padre.setDerecho(nodo.getDerecho());
                aux.setDerecho(null);
            }else{                                  // no tiene hijo derecho
                NodoBinario<T> aux = nodo;
                padre.setIzquierdo(nodo.getIzquierdo());
                aux.setIzquierdo(null);
            }
        }
    }

    private NodoBinario<T> buscarNodo(T elem){
        boolean perdido = true;
        NodoBinario<T> aux = raiz;

        while(perdido == true && aux != null){
            if(comparador.compare(elem, aux.getValor()) == 0){
                perdido = false;
            }
            
            if(comparador.compare(elem, aux.getValor()) > 0){
                aux = aux.getDerecho();
            }

            if(comparador.compare(elem, aux.getValor()) < 0){
                aux = aux.getIzquierdo();
            }
        }

        return aux;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void vaciar(){
        raiz = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T raiz(){
        return raiz.getValor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Diccionario<T> subArbolIzquierdo(){
        return new ABB<T>(comparador, raiz.getIzquierdo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Diccionario<T> subArbolDerecho(){
        return new ABB<T>(comparador, raiz.getDerecho());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int elementos(){
        return elementos2(this.raiz);
    }

    private int elementos2(NodoBinario<T> arbol){
        if(arbol == null){
            return 0;
        }else{
            return 1 + elementos2(arbol.getDerecho()) + elementos2(arbol.getIzquierdo());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int altura(){
        return raiz.getAltura();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean esVacio(){
        return raiz == null;
    }

    /**
     * {@inheritDoc}
     */
    public T mayorValor(){
        NodoBinario<T> aux = raiz;
        while(aux.getDerecho() != null){
            aux = aux.getDerecho();
        }
        return aux.getValor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T menorValor(){
        NodoBinario<T> aux = raiz;
        while(aux.getIzquierdo() != null){
            aux = aux.getIzquierdo();
        }
        return aux.getValor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T sucesor(T elem){
        List<T> listaArbol = aLista(Orden.INORDER);

        int i = 0;
        while(comparador.compare(listaArbol.get(i++), elem) < 0);

        if(comparador.compare(listaArbol.get(i), elem) == 0){
            if(i == listaArbol.size() -1) return null;
            
            return listaArbol.get(++i);
        }

        return listaArbol.get(i);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T predecesor(T elem){
        List<T> listaArbol = aLista(Orden.INORDER);

        int i = 0;
        while(comparador.compare(listaArbol.get(i++), elem) < 0);

        if(i == 0) return null;

        return listaArbol.get(--i);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean repOK(){
        throw new UnsupportedOperationException("TODO: implementar");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        throw new UnsupportedOperationException("TODO: implementar");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other){
        throw new UnsupportedOperationException("TODO: implementar");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> aLista(){
        return aLista(Orden.INORDER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> aLista(Orden orden){
        List<T> elementos = new LinkedList<>();
        switch(orden){
            case INORDER:
                return aListaInOrder(raiz, elementos);
            case PREORDER:
                return aListaPreOrder(raiz, elementos);
            case POSTORDER:
                return aListaPostOrder(raiz, elementos);
        }
        return elementos;
    }

    /* (non-Javadoc)
     * Este método toma un nodo (que puede ser null), una lista de elementos (que no puede ser null)
     * y va llenando la lista con los elementos del árbol según un recorrido in order.
     * Si bien el prefil está pensando para una implementación recursiva, puede probar con una implementación iterativa.
     */
    private List<T> aListaInOrder(NodoBinario<T> raiz, List<T> elementos){
        throw new UnsupportedOperationException("TODO: implementar");
    }

    /* (non-Javadoc)
     * Este método toma un nodo (que puede ser null), una lista de elementos (que no puede ser null)
     * y va llenando la lista con los elementos del árbol según un recorrido pre order.
     * Si bien el prefil está pensando para una implementación recursiva, puede probar con una implementación iterativa.
     */
    private List<T> aListaPreOrder(NodoBinario<T> raiz, List<T> elementos){
        throw new UnsupportedOperationException("TODO: implementar");
    }

    /* (non-Javadoc)
     * Este método toma un nodo (que puede ser null), una lista de elementos (que no puede ser null)
     * y va llenando la lista con los elementos del árbol según un recorrido post order.
     * Si bien el prefil está pensando para una implementación recursiva, puede probar con una implementación iterativa.
     */
    private List<T> aListaPostOrder(NodoBinario<T> raiz, List<T> elementos){
        throw new UnsupportedOperationException("TODO: implementar");
    }

    public void prinInOrder(NodoBinario<T> r){

        if(r != null){
            System.out.println(r.getValor());
            if (r.getIzquierdo()!= null) {
                prinInOrder(r.getIzquierdo());
                System.out.println("???????????");                    
            }else if(r.getDerecho()!= null){
                prinInOrder(r.getDerecho());
                System.out.println("!!!!!!!!!!!");
            }
        }
    }

    public NodoBinario<T> getRaiz(){
        return raiz;
    }
}