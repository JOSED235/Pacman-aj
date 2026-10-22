package model.entidades;

import estructuras.Vertice;

public class Pacman {
    public static final int VIDAS_INICIALES = 3;

    private final Vertice inicio;
    // Los hilos de los fantasmas leen esta posición para perseguir a Pac-Man
    private volatile Vertice posicion;
    private int vidas;
    private boolean poderActivo;

    public Pacman(Vertice inicio) {
        this.inicio = inicio;
        this.posicion = inicio;
        this.vidas = VIDAS_INICIALES;
        this.poderActivo = false;
    }

    public Vertice getPosicion() {
        return posicion;
    }
    public void setPosicion(Vertice nuevaPosicion) {
        this.posicion = nuevaPosicion;
    }

    /** Vuelve a la casilla de salida (al perder una vida o al empezar un nivel). */
    public void reiniciarPosicion() {
        posicion = inicio;
        poderActivo = false;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public void perderVida() {
        if (vidas > 0) {
            vidas--;
        }
    }

    public void ganarVida() {
        vidas++;
    }

    public boolean isPoderActivo() {
        return poderActivo;
    }

    public void activarPoder() {
        poderActivo = true;
    }

    public void desactivarPoder() {
        poderActivo = false;
    }

    public boolean puedeMover(Movimiento direccion) {
        return direccion != null && !posicion.isNull(direccion);
    }

    public void mover(Movimiento direccion) {
        if (puedeMover(direccion)) {
            posicion = (Vertice) posicion.getArista(direccion).getDestino();
        }
    }
}
