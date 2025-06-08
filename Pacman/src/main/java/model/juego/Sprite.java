package model.juego;

public class Sprite {
    private int valor;
    private int row;
    private int col;

    public Sprite(int valor, int row, int col) {
        this.valor = valor;
        this.row = row;
        this.col = col;
    }

    public int getValor() {
        return valor;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "type=" + valor +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}