package model.ia;

import estructuras.Vertice;
import javafx.application.Platform;
import javafx.util.Duration;
import model.entidades.Fantasma;
import model.entidades.Movimiento;
import model.entidades.Pacman;
import ui.entidades.FantasmaView;

/**
 * Hilo que maneja el comportamiento autónomo de un fantasma.
 * La decisión (Dijkstra, BFS o azar) se calcula en este hilo; el movimiento y la animación
 * se aplican en el hilo de JavaFX con Platform.runLater.
 */
public class FantasmaThread extends Thread {

    private final Fantasma fantasma;
    private final FantasmaView vista;
    private final Pacman pacman;
    private final Vertice salidaCasa;
    private final Runnable alMoverse;
    private final FantasmaIA salir = new FantasmaBFS();
    private final FantasmaIA huir = new FantasmaAleatorio();

    private volatile boolean activo = true;
    private volatile boolean pausado = false;
    private volatile int tiempo;
    private volatile long esperaHasta;

    /**
     * @param fantasma modelo del fantasma
     * @param vista vista del fantasma
     * @param pacman referencia al Pac-Man a perseguir
     * @param salidaCasa casilla justo afuera de la puerta de la casa
     * @param tiempo milisegundos entre movimientos
     * @param retrasoInicial milisegundos que espera en la casa antes de salir
     * @param alMoverse se ejecuta en el hilo de JavaFX después de cada paso (revisar choques)
     */
    public FantasmaThread(Fantasma fantasma, FantasmaView vista, Pacman pacman, Vertice salidaCasa,
                          int tiempo, int retrasoInicial, Runnable alMoverse) {
        this.fantasma = fantasma;
        this.vista = vista;
        this.pacman = pacman;
        this.salidaCasa = salidaCasa;
        this.tiempo = tiempo;
        this.alMoverse = alMoverse;
        this.esperaHasta = System.currentTimeMillis() + retrasoInicial;
        setDaemon(true); // no impide que la aplicación se cierre
        setName("Fantasma-" + fantasma.getLogica().getClass().getSimpleName());
    }

    @Override
    public void run() {
        while (activo) {
            try {
                if (!pausado && System.currentTimeMillis() >= esperaHasta) {
                    Movimiento movimiento = decidir();
                    if (movimiento != null) {
                        Platform.runLater(() -> aplicar(movimiento));
                    }
                }
                Thread.sleep(fantasma.isAsustado() ? tiempo * 3L / 2 : tiempo);
            } catch (InterruptedException e) {
                break; // detener() interrumpe el sueño del hilo
            }
        }
    }

    private Movimiento decidir() {
        Vertice actual = fantasma.getPosicion();
        if (actual.isCasaFantasmas()) {
            return salir.calcularMovimiento(actual, salidaCasa);
        }
        if (fantasma.isAsustado()) {
            return huir.calcularMovimiento(actual, pacman.getPosicion());
        }
        return fantasma.getLogica().calcularMovimiento(actual, pacman.getPosicion());
    }

    private void aplicar(Movimiento movimiento) {
        if (!activo || pausado || !fantasma.puedeMover(movimiento)) return;
        int duracion = fantasma.isAsustado() ? tiempo * 3 / 2 : tiempo;
        vista.moverA(Duration.millis(duracion * 0.9), null, movimiento);
        alMoverse.run();
    }

    /** Deja al fantasma quieto un tiempo (por ejemplo, después de volver a la casa). */
    public void esperar(int milisegundos) {
        esperaHasta = System.currentTimeMillis() + milisegundos;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void pausar() {
        pausado = true;
    }

    public void reanudar() {
        pausado = false;
    }

    public void detener() {
        activo = false;
        interrupt();
    }
}
