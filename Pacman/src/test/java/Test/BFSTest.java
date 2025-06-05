package Test;

import model.algoritmos.BFS;
import model.grafo.GrafoListaAdyacencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import estructuras.ListaEnlazada;
import static org.junit.jupiter.api.Assertions.*;

public class BFSTest {

    private GrafoListaAdyacencia<String> grafo;
    private BFS<String> bfs;

    @BeforeEach
    public void setUp() {
        grafo = new GrafoListaAdyacencia<>();
        // Agregar vértices
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        // Agregar aristas
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("A", "C", 1);
        grafo.agregarArista("B", "D", 1);
        grafo.agregarArista("C", "D", 1);

        bfs = new BFS<>(grafo);
    }

    @Test
    public void testRecorridoBFSDesdeA() {
        ListaEnlazada<String> recorrido = bfs.recorridoBFS("A");

        // La lista debe contener todos los nodos
        assertEquals(4, recorrido.size());

        // El orden esperado BFS desde "A": A, B, C, D
        assertEquals("A", recorrido.getCabeza().getDato());
        assertEquals("B", recorrido.getCabeza().getSiguiente().getDato());
        assertEquals("C", recorrido.getCabeza().getSiguiente().getSiguiente().getDato());
        assertEquals("D", recorrido.getCabeza().getSiguiente().getSiguiente().getSiguiente().getDato());
    }

    @Test
    public void testBFSVerticeNoExiste() {
        assertThrows(IllegalArgumentException.class, () -> {
            bfs.recorridoBFS("X");
        });
    }
}