package estructuras;

import java.util.Iterator;

public class ListaEnlazada<T>  implements Iterable<T> {
    private Nodo<T> cabeza;
    private int size;

    public ListaEnlazada() {
        this.cabeza = null;
        this.size = 0;
    }

    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        size++;
    }

    public boolean eliminar(T dato) {
        if (cabeza == null) return false;
        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            size--;
            return true;
        }
        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                size--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    public int size() {

        return size;
    }

    // Método para iterar y obtener todos los datos en forma de array (opcional)
    public T[] toArray() {
        // Implementar si es necesario
        return null;
    }

    public Nodo<T> getCabeza() {
        return cabeza;
    }


    public boolean estaVacia() {
        return size == 0;
    }

    public T obtener(int index) {
        if (index < 0 || index >= size) return null;
        Nodo<T> actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                T dato = actual.getDato();
                actual = actual.getSiguiente();
                return dato;
            }
        };
    }
    public void limpiar() {
        cabeza = null;
        size = 0;
    }


}
