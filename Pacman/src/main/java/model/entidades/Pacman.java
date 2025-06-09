package model.entidades;

import estructuras.Vertice;
import model.entidades.Movimiento;

public class Pacman {
    private Vertice posicion;
    private int vidas;
    private boolean poderActivo;

    public Pacman(Vertice inicio) {
        this.posicion = inicio;
        this.vidas = 3; // valor inicial por defecto
        this.poderActivo = false;
    }

    public Vertice getPosicion() {
        return posicion;
    }
    public void setPosicion(Vertice nuevaPosicion) {
        this.posicion = nuevaPosicion;
    }

    public int getVidas() {
        return vidas;
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
    public javafx.scene.image.ImageView getNode() {
        return new javafx.scene.image.ImageView(new javafx.scene.image.Image("/pictures/pacmanRight.png"));
    }

}
