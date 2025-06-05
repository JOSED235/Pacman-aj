package estructuras;

// Estructura Union-Find (Conjuntos Disjuntos) usando TablaHash propia.
// Permite operaciones para crear conjuntos, encontrar el representante y unir conjuntos.
public class UnionFind<T> {

    private TablaHash<T, T> parent;  // Mapea cada elemento a su padre en el conjunto
    private TablaHash<T, Integer> rank;  // Para optimizar unión por rango

    // Constructor: inicializa las tablas hash internas
    public UnionFind() {
        parent = new TablaHash<>();
        rank = new TablaHash<>();
    }

    // Crea un conjunto con un solo elemento si no existe
    public void makeSet(T item) {
        if (!parent.containsKey(item)) {
            parent.put(item, item);  // El padre de sí mismo
            rank.put(item, 0);       // Rango inicial 0
        }
    }

    // Encuentra el representante del conjunto al que pertenece el elemento
    // Aplica compresión de caminos para optimizar futuras búsquedas
    public T find(T item) {
        T p = parent.get(item);
        if (p == null) return null;  // Elemento no existe

        if (!p.equals(item)) {
            p = find(p);
            parent.put(item, p);  // Actualiza padre para compresión de caminos
        }
        return p;
    }

    // Une los conjuntos que contienen a los dos elementos dados
    // Usa unión por rango para mantener árboles balanceados
    public void union(T a, T b) {
        T rootA = find(a);
        T rootB = find(b);

        if (rootA == null || rootB == null) return;  // Alguno no existe
        if (rootA.equals(rootB)) return;              // Ya están en el mismo conjunto

        int rankA = rank.get(rootA);
        int rankB = rank.get(rootB);

        if (rankA < rankB) {
            parent.put(rootA, rootB);
        } else if (rankA > rankB) {
            parent.put(rootB, rootA);
        } else {
            parent.put(rootB, rootA);
            rank.put(rootA, rankA + 1);
        }
    }
}
