package Test;

import estructuras.Cola;
import estructuras.TablaHash;
import estructuras.Vertice;
import javafx.application.Platform;
import model.entidades.Movimiento;
import model.ia.FantasmaBFS;
import model.ia.FantasmaDijkstra;
import model.ia.FantasmaIA;
import model.juego.Laberinto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Verifica que el grafo del laberinto permita jugar: salida de la casa, túnel y persecución. */
public class LaberintoTest {

    private Laberinto laberinto;

    @BeforeAll
    public static void iniciarJavaFX() {
        // Los consumibles crean imágenes de JavaFX: el toolkit debe estar iniciado
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException yaIniciado) {
            // ya estaba iniciado por otra prueba
        }
    }

    @BeforeEach
    public void setUp() {
        laberinto = new Laberinto();
    }

    /** Distancias en pasos desde un vértice, recorriendo las aristas del laberinto (BFS). */
    private TablaHash<Vertice, Integer> distanciasDesde(Vertice origen) {
        TablaHash<Vertice, Integer> distancia = new TablaHash<>();
        Cola<Vertice> cola = new Cola<>();
        distancia.put(origen, 0);
        cola.encolar(origen);
        while (!cola.estaVacia()) {
            Vertice actual = cola.desencolar();
            for (Movimiento dir : Movimiento.getDirecciones()) {
                if (!actual.isNull(dir)) {
                    Vertice vecino = (Vertice) actual.getArista(dir).getDestino();
                    if (!distancia.containsKey(vecino)) {
                        distancia.put(vecino, distancia.get(actual) + 1);
                        cola.encolar(vecino);
                    }
                }
            }
        }
        return distancia;
    }

    @Test
    public void testCadaFantasmaPuedeSalirDeLaCasaYAlcanzarAPacman() {
        Vertice[] fantasmas = {laberinto.getGhost1(), laberinto.getGhost2(), laberinto.getGhost3(), laberinto.getGhost4()};
        for (Vertice fantasma : fantasmas) {
            assertTrue(distanciasDesde(fantasma).containsKey(laberinto.getPacman()),
                    "El fantasma en " + fantasma.getX() + "," + fantasma.getY() + " no puede llegar a Pac-Man");
        }
    }

    @Test
    public void testPacmanNoPuedeEntrarALaCasaDeLosFantasmas() {
        TablaHash<Vertice, Integer> alcanzables = distanciasDesde(laberinto.getPacman());
        assertFalse(alcanzables.containsKey(laberinto.getGhost1()));
        assertFalse(alcanzables.containsKey(laberinto.getGhost2()));
    }

    @Test
    public void testPacmanPuedeVolverASuCasillaInicial() {
        Vertice inicio = laberinto.getPacman();
        Vertice vecino = (Vertice) inicio.getLeft().getDestino();
        assertTrue(distanciasDesde(vecino).containsKey(inicio));
    }

    @Test
    public void testElTunelConectaAmbosLados() {
        Vertice derecha = laberinto.getGrafo().getVertice(14, 27);
        Vertice izquierda = laberinto.getGrafo().getVertice(14, 0);
        assertEquals(izquierda, derecha.getRight().getDestino());
        assertEquals(derecha, izquierda.getLeft().getDestino());
    }

    @Test
    public void testHayPuntosParaComer() {
        assertTrue(laberinto.cantidadComestibles() > 200);
    }

    private void assertSeAcerca(FantasmaIA ia, Vertice origen) {
        Vertice objetivo = laberinto.getPacman();
        Movimiento mov = ia.calcularMovimiento(origen, objetivo);
        assertNotNull(mov, "La IA no devolvió movimiento");
        assertFalse(origen.isNull(mov), "La IA eligió una dirección sin camino");
        Vertice siguiente = (Vertice) origen.getArista(mov).getDestino();
        // el grafo es dirigido (puerta de un sentido): se mide la distancia desde cada vértice hacia Pac-Man
        int desdeOrigen = distanciasDesde(origen).get(objetivo);
        int desdeSiguiente = distanciasDesde(siguiente).get(objetivo);
        assertEquals(desdeOrigen - 1, desdeSiguiente, "El movimiento no acorta el camino hacia Pac-Man");
    }

    @Test
    public void testDijkstraAcercaAlFantasmaAPacman() {
        assertSeAcerca(new FantasmaDijkstra(), laberinto.getGhost2());
        assertSeAcerca(new FantasmaDijkstra(), laberinto.getGrafo().getVertice(1, 1));
        assertSeAcerca(new FantasmaDijkstra(), laberinto.getGrafo().getVertice(29, 26));
    }

    @Test
    public void testBFSAcercaAlFantasmaAPacman() {
        assertSeAcerca(new FantasmaBFS(), laberinto.getGhost3());
        assertSeAcerca(new FantasmaBFS(), laberinto.getGrafo().getVertice(5, 21));
    }
}
