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
        if(raiz == null){

            raiz = new NodoBinario<T>(elem);

        }else if(comparador.compare(elem,raiz.getValor())<0){

            ABB <T> arbol = (ABB <T>) this.subArbolIzquierdo();
            arbol.insertar(elem);
            raiz.setIzquierdo(arbol.getRaiz());

        }else{

            ABB arbol = (ABB) this.subArbolDerecho();
            arbol.insertar(elem);
            raiz.setDerecho(arbol.getRaiz());

        }
        this.elementos++;
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
    public void borrar(T elem) {
        raiz = borrarRecursivo(raiz, elem);
        elementos--;
    }

    private NodoBinario<T> borrarRecursivo(NodoBinario<T> nodo, T elem) {
        if (nodo == null) {
            return null;
        }

        int comparacion = comparador.compare(elem, nodo.getValor());

        if (comparacion < 0) {
            nodo.setIzquierdo(borrarRecursivo(nodo.getIzquierdo(), elem));
        }
        else if (comparacion > 0) {
            nodo.setDerecho(borrarRecursivo(nodo.getDerecho(), elem));
        }
        else {
            // Caso 1: El nodo es una hoja (no tiene hijos)
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                return null;
            }

            // Caso 2: El nodo tiene un solo hijo
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }

            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }

            // Caso 3: El nodo tiene dos hijos
            NodoBinario<T> sucesor = encontrarSucesor(nodo.getDerecho());
            nodo.setValor(sucesor.getValor());
            nodo.setDerecho(borrarRecursivo(nodo.getDerecho(), sucesor.getValor()));
        }

        return nodo;
    }

    private NodoBinario<T> encontrarSucesor(NodoBinario<T> nodo) {
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo;
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
        return elementos;
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
        return (elementos == 0);
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
        return repOKAux(raiz);
    }

    private boolean repOKAux(NodoBinario<T> nodo){
        if(nodo == null){
            return true;
        }

        T valor = nodo.getValor();
        NodoBinario<T> izquierdo = nodo.getIzquierdo();
        NodoBinario<T> derecho = nodo.getDerecho();

        if(valor == null){
            return false;
        }

        if(izquierdo != null && (comparador.compare(izquierdo.getValor(), valor) >= 0 || !repOKAux(izquierdo))){
            return false;
        }

        if(derecho != null && (comparador.compare(derecho.getValor(), valor) <= 0 || !repOKAux(derecho))){
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return toStringRecursivo(raiz);
    }

    private String toStringRecursivo(NodoBinario<T> nodo) {
        if (nodo == null) {
            return "";
        }

        String resultado = "";
        resultado += toStringRecursivo(nodo.getIzquierdo());
        resultado += nodo.getValor().toString() + " ";
        resultado += toStringRecursivo(nodo.getDerecho());

        return resultado;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other){
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        ABB<?> otherTree = (ABB<?>) other;

        if (elementos != otherTree.elementos) {
            return false;
        }

        List<T> thisList = this.aLista();
        List<?> otherList = otherTree.aLista();

        return thisList.equals(otherList);
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
    private List<T> aListaInOrder(NodoBinario<T> raiz, List<T> elementos) {
        if (raiz == null) {
            return elementos;
        }

        aListaInOrder(raiz.getIzquierdo(), elementos); // Recorrido in-order del subárbol izquierdo
        elementos.add(raiz.getValor()); // Agregar el elemento actual a la lista
        aListaInOrder(raiz.getDerecho(), elementos); // Recorrido in-order del subárbol derecho

        return elementos;
    }

    /* (non-Javadoc)
     * Este método toma un nodo (que puede ser null), una lista de elementos (que no puede ser null)
     * y va llenando la lista con los elementos del árbol según un recorrido pre order.
     * Si bien el prefil está pensando para una implementación recursiva, puede probar con una implementación iterativa.
     */
    private List<T> aListaPreOrder(NodoBinario<T> raiz, List<T> elementos){
        if (raiz == null) {
            return elementos;
        }

        elementos.add(raiz.getValor()); 
        aListaPreOrder(raiz.getIzquierdo(), elementos);
        aListaPreOrder(raiz.getDerecho(), elementos); 

        return elementos;
    }

    /* (non-Javadoc)
     * Este método toma un nodo (que puede ser null), una lista de elementos (que no puede ser null)
     * y va llenando la lista con los elementos del árbol según un recorrido post order.
     * Si bien el prefil está pensando para una implementación recursiva, puede probar con una implementación iterativa.
     */
    private List<T> aListaPostOrder(NodoBinario<T> raiz, List<T> elementos){
        if (raiz == null) {
            return elementos;
        }

        aListaPostOrder(raiz.getIzquierdo(), elementos);
        aListaPostOrder(raiz.getDerecho(), elementos);
        elementos.add(raiz.getValor());

        return elementos;
    }

    public void printInOrder(NodoBinario<T> r){
        if (r != null) {
            printInOrder(r.getIzquierdo());
            System.out.println(r.getValor());
            printInOrder(r.getDerecho());
        }
    }

    public NodoBinario<T> getRaiz(){
        return raiz;
    }
}
