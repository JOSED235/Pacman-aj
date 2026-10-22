package model.entidades;

import estructuras.Vertice;
import model.ia.FantasmaIA;

public class Fantasma {
    private final Vertice inicio;
    // Se lee desde el hilo del fantasma y se escribe en el hilo de JavaFX
    private volatile Vertice posicion;
    private Vertice posicionAnterior;
    private FantasmaIA logica;
    private volatile boolean asustado;
    private Movimiento ultimoMovimiento;

    public Fantasma(Vertice inicio, FantasmaIA logica) {
        this.inicio = inicio;
        this.posicion = inicio;
        this.logica = logica;
    }

    public Vertice getPosicion() {
        return posicion;
    }

    public void setPosicion(Vertice nuevaPosicion) {
        this.posicion = nuevaPosicion;
    }

    public Vertice getInicio() {
        return inicio;
    }

    /** Vuelve a la casa: al perder Pac-Man una vida o al ser comido. */
    public void reiniciar() {
        posicion = inicio;
        posicionAnterior = null;
        asustado = false;
        ultimoMovimiento = null;
    }

    public void setLogica(FantasmaIA logica) {
        this.logica = logica;
    }

    public FantasmaIA getLogica() {
        return logica;
    }

    public boolean isAsustado() {
        return asustado;
    }

    public void setAsustado(boolean asustado) {
        this.asustado = asustado;
    }

    public Movimiento getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public boolean puedeMover(Movimiento direccion) {
        return !posicion.isNull(direccion);
    }

    public void mover(Movimiento direccion) {
        if (puedeMover(direccion)) {
            posicionAnterior = posicion;
            posicion = (Vertice) posicion.getArista(direccion).getDestino();
            ultimoMovimiento = direccion;
        }
    }

    /** Casilla de la que venía en su último paso (para detectar cruces con Pac-Man). */
    public Vertice getPosicionAnterior() {
        return posicionAnterior;
    }
}
