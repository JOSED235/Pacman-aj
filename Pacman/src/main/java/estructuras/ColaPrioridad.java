package estructuras;

public class ColaPrioridad<T> {
    private Nodo<T> cabeza;
    private int size;

    public ColaPrioridad() {
        cabeza = null;
        size = 0;
    }

    public void encolarConPrioridad(T dato, double prioridad) {
        NodoPrioridad<T> nuevo = new NodoPrioridad<>(dato, prioridad);
        if (cabeza == null || ((NodoPrioridad<T>)cabeza).prioridad > prioridad) {
            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;
        } else {
            NodoPrioridad<T> actual = (NodoPrioridad<T>) cabeza;
            while (actual.getSiguiente() != null && ((NodoPrioridad<T>)actual.getSiguiente()).prioridad <= prioridad) {
                actual = (NodoPrioridad<T>) actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
        }
        size++;
    }

    public T desencolar() {
        if (estaVacia()) {
            throw new RuntimeException("Cola de prioridad vacía");
        }
        T dato = cabeza.getDato();
        cabeza = cabeza.getSiguiente();
        size--;
        return dato;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    private static class NodoPrioridad<T> extends Nodo<T> {
        double prioridad;

        NodoPrioridad(T dato, double prioridad) {
            super(dato);
            this.prioridad = prioridad;
        }
    }
}
