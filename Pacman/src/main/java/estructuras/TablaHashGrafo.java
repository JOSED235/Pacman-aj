package estructuras;

public class TablaHashGrafo {

    private Vertice[] tabla;
    private int capacidad;
    private int size;

    public TablaHashGrafo() {
        capacidad = 2000;
        tabla = new Vertice[capacidad];
        size = 0;
    }

    private int hash(int key) {
        return Math.abs(key) % capacidad;
    }

    public void insert(int key, Vertice value) {
        int index = hash(key);
        int originalIndex = index;

        while (tabla[index] != null) {
            if (tabla[index].getDato() == key) {
                // Ya existe el vértice, no lo duplicamos
                return;
            }
            index = (index + 1) % capacidad;
            if (index == originalIndex) {
                throw new RuntimeException("La tabla hash está llena");
            }
        }

        tabla[index] = value;
        size++;
    }

    public Vertice get(int key) {
        int index = hash(key);
        int originalIndex = index;

        while (tabla[index] != null) {
            if (tabla[index].getDato() == key) {
                return tabla[index];
            }
            index = (index + 1) % capacidad;
            if (index == originalIndex) {
                break; // Evita bucles infinitos
            }
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
