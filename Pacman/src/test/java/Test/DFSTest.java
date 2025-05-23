package Test;

import model.algoritmos.DFS;
import model.grafo.GrafoListaAdyacencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import estructuras.ListaEnlazada;
import static org.junit.jupiter.api.Assertions.*;

public class DFSTest {

    private GrafoListaAdyacencia<String> grafo;
    private DFS<String> dfs;

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

        dfs = new DFS<>(grafo);
    }

    @Test
    public void testRecorridoDFSDesdeA() {
        ListaEnlazada<String> recorrido = dfs.recorridoDFS("A");

        // La lista debe contener todos los nodos
        assertEquals(4, recorrido.size());

        // Orden posible DFS desde "A": A, C, D, B o A, B, D, C dependiendo del orden de los vecinos
        // Aquí validamos solo que el primer elemento sea A y que todos los nodos estén presentes
        assertEquals("A", recorrido.getCabeza().getDato());

        // Validamos que todos los nodos estén en la lista (sin importar orden)
        boolean contieneA = false, contieneB = false, contieneC = false, contieneD = false;
        var nodo = recorrido.getCabeza();
        while (nodo != null) {
            String val = nodo.getDato();
            if (val.equals("A")) contieneA = true;
            if (val.equals("B")) contieneB = true;
            if (val.equals("C")) contieneC = true;
            if (val.equals("D")) contieneD = true;
            nodo = nodo.getSiguiente();
        }
        assertTrue(contieneA && contieneB && contieneC && contieneD);
    }

    @Test
    public void testDFSVerticeNoExiste() {
        assertThrows(IllegalArgumentException.class, () -> {
            dfs.recorridoDFS("X");
        });
    }
}
