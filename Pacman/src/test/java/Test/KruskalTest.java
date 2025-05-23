package Test;

import model.algoritmos.Kruskal;
import model.grafo.GrafoListaAdyacencia;
import model.grafo.Arista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import estructuras.ListaEnlazada;
import estructuras.Nodo;

import static org.junit.jupiter.api.Assertions.*;

public class KruskalTest {

    private GrafoListaAdyacencia<String> grafo;
    private Kruskal<String> kruskal;

    @BeforeEach
    public void setUp() {
        grafo = new GrafoListaAdyacencia<>();

        // Agregar vértices
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        // Agregar aristas (grafo no dirigido, agrega ambas direcciones)
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("B", "A", 1);

        grafo.agregarArista("A", "C", 4);
        grafo.agregarArista("C", "A", 4);

        grafo.agregarArista("B", "C", 2);
        grafo.agregarArista("C", "B", 2);

        grafo.agregarArista("B", "D", 6);
        grafo.agregarArista("D", "B", 6);

        grafo.agregarArista("C", "D", 3);
        grafo.agregarArista("D", "C", 3);

        kruskal = new Kruskal<>(grafo);
    }

    @Test
    public void testCalcularMST() {
        ListaEnlazada<Arista<String>> mst = kruskal.calcularMST();

        // MST de grafo debe contener n-1 aristas
        assertEquals(3, mst.size());

        // Validar que el peso total es mínimo: 1 + 2 + 3 = 6
        double pesoTotal = 0;
        Nodo<Arista<String>> nodo = mst.getCabeza();
        while (nodo != null) {
            pesoTotal += nodo.getDato().peso;
            nodo = nodo.getSiguiente();
        }
        assertEquals(6.0, pesoTotal);
    }
}
