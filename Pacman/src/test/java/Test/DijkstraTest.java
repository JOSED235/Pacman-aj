package Test;

import model.algoritmos.Dijkstra;
import model.grafo.GrafoListaAdyacencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import estructuras.ListaEnlazada;
import estructuras.Nodo;
import estructuras.TablaHash;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {

    private GrafoListaAdyacencia<String> grafo;
    private Dijkstra<String> dijkstra;

    @BeforeEach
    public void setUp() {
        grafo = new GrafoListaAdyacencia<>();

        // Agregar vértices
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        // Agregar aristas con pesos
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("A", "C", 4);
        grafo.agregarArista("B", "C", 2);
        grafo.agregarArista("B", "D", 6);
        grafo.agregarArista("C", "D", 3);

        dijkstra = new Dijkstra<>(grafo);
    }

    @Test
    public void testCalcularDistanciasMinimasDesdeA() {
        TablaHash<String, Double> distancias = dijkstra.calcularDistanciasMinimas("A");

        assertNotNull(distancias);
        assertEquals(0.0, distancias.get("A"));
        assertEquals(1.0, distancias.get("B"));
        assertEquals(3.0, distancias.get("C")); // A->B->C es mejor que A->C directo (4)
        assertEquals(6.0, distancias.get("D")); // A->B->C->D = 6 es menor que A->B->D(7)
    }

    @Test
    public void testVerticeNoExistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            dijkstra.calcularDistanciasMinimas("X");
        });
    }
}
