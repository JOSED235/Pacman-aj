package model.entidades;

import estructuras.Vertice;
import model.ia.FantasmaIA;

public class Fantasma {
    private Vertice posicion;
    private FantasmaIA logica;

    public Fantasma(Vertice inicio) {
        this.posicion = inicio;
        this.logica = null;
    }

    public Vertice getPosicion() {
        return posicion;
    }

    public void setPosicion(Vertice nuevaPosicion) {
        this.posicion = nuevaPosicion;
    }

    public void setLogica(FantasmaIA logica) {
        this.logica = logica;
    }

    public FantasmaIA getLogica() {
        return logica;
    }

    public boolean puedeMover(Movimiento direccion) {
        return !posicion.isNull(direccion);
    }

    public void mover(Movimiento direccion) {
        if (puedeMover(direccion)) {
            switch (direccion) {
                case UP -> posicion = (Vertice) posicion.getUp().getDestino();
                case DOWN -> posicion = (Vertice) posicion.getDown().getDestino();
                case LEFT -> posicion = (Vertice) posicion.getLeft().getDestino();
                case RIGHT -> posicion = (Vertice) posicion.getRight().getDestino();
            }
        }
    }
}
