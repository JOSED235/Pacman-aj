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
    private final int tiempo;

    /**
     * Constructor para inicializar un fantasma con IA y vista.
     *
     * @param fantasma modelo del fantasma
     * @param vista vista del fantasma
     * @param logica lógica de movimiento (IA)
     * @param pacman referencia al pacman a perseguir
     *
     */
    public FantasmaThread(Fantasma fantasma, FantasmaView vista, FantasmaIA logica, Pacman pacman, int tiempo) {
        this.fantasma = fantasma;
        this.vista = vista;
        this.logica = logica;
        this.pacman = pacman;
        this.tiempo = tiempo;
    }

    @Override
    public void run() {
        System.out.println("Fantasma activo: " + fantasma);

        activo = true;
        while (activo) {
            try {
                Movimiento movimiento = logica.calcularMovimiento(fantasma.getPosicion(), pacman.getPosicion());
                System.out.println("Movimiento calculado: " + movimiento);


                if (movimiento != null) {
                    Platform.runLater(() -> vista.moverA(Duration.millis(300), null, movimiento));
                }

                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
