package Test;

import model.algoritmos.Prim;
import model.grafo.GrafoListaAdyacencia;
import model.grafo.Arista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import estructuras.ListaEnlazada;
import estructuras.Nodo;

import static org.junit.jupiter.api.Assertions.*;

public class PrimTest {

    private GrafoListaAdyacencia<String> grafo;
    private Prim<String> prim;

    @BeforeEach
    public void setUp() {
        grafo = new GrafoListaAdyacencia<>();

        // Agregar vértices
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        // Agregar aristas (grafo no dirigido, ambas direcciones)
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

        prim = new Prim<>(grafo);
    }

    @Test
    public void testCalcularMSTDesdeA() {
        ListaEnlazada<Arista<String>> mst = prim.calcularMST("A");

        // MST debe contener n-1 aristas
        assertEquals(3, mst.size());

        // Validar peso total mínimo 6.0
        double pesoTotal = 0;
        Nodo<Arista<String>> nodo = mst.getCabeza();
        while (nodo != null) {
            pesoTotal += nodo.getDato().peso;
            nodo = nodo.getSiguiente();
        }
        assertEquals(6.0, pesoTotal);
    }

    @Test
    public void testVerticeInicialNoExiste() {
        assertThrows(IllegalArgumentException.class, () -> {
            prim.calcularMST("X");
        });
    }
}
