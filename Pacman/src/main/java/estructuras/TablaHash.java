package estructuras;

public class TablaHash<K, V> {

    private static class Entrada<K, V> {
        K key;
        V value;
        Entrada<K, V> siguiente;

        Entrada(K key, V value) {
            this.key = key;
            this.value = value;
            this.siguiente = null;
        }
    }

    private Entrada<K, V>[] tabla;
    private int capacidad;
    private int size;

    @SuppressWarnings("unchecked")
    public TablaHash() {
        capacidad = 16;
        tabla = new Entrada[capacidad];
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacidad;
    }

    public void put(K key, V value) {
        int index = hash(key);
        Entrada<K, V> actual = tabla[index];
        if (actual == null) {
            tabla[index] = new Entrada<>(key, value);
            size++;
            return;
        }
        Entrada<K, V> prev = null;
        while (actual != null) {
            if (actual.key.equals(key)) {
                actual.value = value;
                return;
            }
            prev = actual;
            actual = actual.siguiente;
        }
        prev.siguiente = new Entrada<>(key, value);
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        Entrada<K, V> actual = tabla[index];
        while (actual != null) {
            if (actual.key.equals(key)) {
                return actual.value;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }
    public ListaEnlazada<K> obtenerClaves() {
        ListaEnlazada<K> claves = new ListaEnlazada<>();
        for (int i = 0; i < capacidad; i++) {
            Entrada<K, V> actual = tabla[i];
            while (actual != null) {
                claves.agregar(actual.key);
                actual = actual.siguiente;
            }
        }
        return claves;
    }
}