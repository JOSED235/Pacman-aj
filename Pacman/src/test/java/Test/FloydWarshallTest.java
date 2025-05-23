package Test;

import model.algoritmos.FloydWarshall;
import model.grafo.GrafoListaAdyacencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloydWarshallTest {

    private GrafoListaAdyacencia<String> grafo;
    private FloydWarshall<String> floydWarshall;

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

        floydWarshall = new FloydWarshall<>(grafo);
    }

    @Test
    public void testCalcularDistancias() {
        FloydWarshall.ResultadoFloyd<String> resultado = floydWarshall.calcularDistancias();

        assertNotNull(resultado);
        double[][] dist = resultado.distancias;
        Object[] vertices = resultado.vertices;

        // Buscar índices para los vértices
        int idxA = buscarIndice(vertices, "A");
        int idxB = buscarIndice(vertices, "B");
        int idxC = buscarIndice(vertices, "C");
        int idxD = buscarIndice(vertices, "D");

        assertEquals(0.0, dist[idxA][idxA]);
        assertEquals(1.0, dist[idxA][idxB]);
        assertEquals(3.0, dist[idxA][idxC]); // Mejor ruta A->B->C
        assertEquals(6.0, dist[idxA][idxD]); // Mejor ruta A->B->C->D

        assertEquals(0.0, dist[idxB][idxB]);
        assertEquals(2.0, dist[idxB][idxC]);
        assertEquals(5.0, dist[idxB][idxD]);

        assertEquals(Double.POSITIVE_INFINITY, dist[idxD][idxA]); // No hay camino D->A
    }

    private int buscarIndice(Object[] arr, String vertice) {
        for (int i = 0; i < arr.length; i++) {
            if (vertice.equals(arr[i])) {
                return i;
            }
        }
        return -1;
    }


}
