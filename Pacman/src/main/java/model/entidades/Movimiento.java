package model.entidades;

public enum Movimiento {
    UP, DOWN, LEFT, RIGHT;

    public static Movimiento[] getDirecciones() {
        return values();
    }

    }