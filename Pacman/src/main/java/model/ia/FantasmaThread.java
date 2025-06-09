package model.ia;

import javafx.application.Platform;
import javafx.util.Duration;
import model.entidades.Fantasma;
import model.entidades.Movimiento;
import model.entidades.Pacman;
import ui.entidades.FantasmaView;

/**
 * Hilo que maneja el comportamiento autónomo de un fantasma.
 */
public class FantasmaThread extends Thread {

    private final Fantasma fantasma;
    private final FantasmaView vista;
    private final FantasmaIA logica;
    private final Pacman pacman;
    private boolean activo;
    private final long tiempoMovimiento;

    /**
     * Constructor para inicializar un fantasma con IA y vista.
     *
     * @param fantasma modelo del fantasma
     * @param vista vista del fantasma
     * @param logica lógica de movimiento (IA)
     * @param pacman referencia al pacman a perseguir
     * @param tiempoMovimiento tiempo entre movimientos (en ms)
     */
    public FantasmaThread(Fantasma fantasma, FantasmaView vista, FantasmaIA logica, Pacman pacman, long tiempoMovimiento) {
        this.fantasma = fantasma;
        this.vista = vista;
        this.logica = logica;
        this.pacman = pacman;
        this.tiempoMovimiento = tiempoMovimiento;
        this.activo = true;
    }

    @Override
    public void run() {
        while (activo) {
            Movimiento mov = logica.calcularMovimiento(fantasma.getPosicion(), pacman.getPosicion());

            if (mov != null && fantasma.puedeMover(mov)) {
                // Mover modelo en el hilo de fondo
                fantasma.mover(mov);

                // Mover vista en el hilo de JavaFX
                Platform.runLater(() -> vista.moverA(Duration.millis(250), null, mov));
            }

            try {
                Thread.sleep(tiempoMovimiento);
            } catch (InterruptedException e) {
                System.out.println("Fantasma interrumpido");
                break;
            }
        }
    }

    public void detener() {
        activo = false;
    }

    public void reanudar() {
        activo = true;
        if (!this.isAlive()) {
            this.start();
        }
    }
}
