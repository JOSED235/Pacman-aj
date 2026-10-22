package model.entidades;

public enum Movimiento {
    UP, DOWN, LEFT, RIGHT;

    public static Movimiento[] getDirecciones() {
        return values();
    }

    public Movimiento opuesto() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
