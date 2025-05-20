package estructuras;

public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int size;

    public Cola() {
        frente = null;
        fin = null;
        size = 0;
    }

    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (fin != null) {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
        if (frente == null) {
            frente = nuevo;
        }
        size++;
    }

    public T desencolar() {
        if (estaVacia()) {
            throw new RuntimeException("Cola vacía");
        }
        T dato = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null;
        }
        size--;
        return dato;
    }

    public T peek() {
        if (estaVacia()) {
            throw new RuntimeException("Cola vacía");
        }
        return frente.getDato();
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int size() {
        return size;
    }
}
