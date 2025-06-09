package estructuras;

public class TablaHashGrafo {
    private Vertice[] tabla;
    private int capacidad;
    private int size;

    public TablaHashGrafo() {
        capacidad = 500;
        tabla = new Vertice[capacidad];
        size = 0;
    }

    private int hash(int key) {
        return Math.abs(key) % capacidad;
    }

    public void insert(int key, Vertice value) {
        int index = hash(key);
        Vertice actual = tabla[index];
        if (actual == null) {
            tabla[index] = value;
            size++;
            return;
        }
        index = (index + 1) % capacidad;
        while (tabla[index] != null) {
            index = (index + 1) % capacidad;
        }
        tabla[index] = value;
        size++;
    }

    public Vertice get(int key) {
        int index = hash(key);
        Vertice actual = tabla[index];
        while (actual != null) {
            if (actual.getDato() == key) {
                return actual;
            }
            index = (index + 1) % capacidad;
            actual = tabla[index];
        }
        return null;
    }

    public boolean containsKey(int key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }
}
