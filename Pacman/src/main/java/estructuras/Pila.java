package estructuras;

public class Pila<T> {
    private Nodo<T> cima;
    private int size;

    public Pila() {
        cima = null;
        size = 0;
    }

    public void push(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        size++;
    }

    public T pop() {
        if (estaVacia()) {
            throw new RuntimeException("Pila vacía");
        }
        T dato = cima.getDato();
        cima = cima.getSiguiente();
        size--;
        return dato;
    }

    public T peek() {
        if (estaVacia()) {
            throw new RuntimeException("Pila vacía");
        }
        return cima.getDato();
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int size() {
        return size;
    }
}
