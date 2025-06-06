package model.juego;

public class Sprite {
    private SpriteType type;
    private int row;
    private int col;

    public Sprite(SpriteType type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
    }

    public SpriteType getType() {
        return type;
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
                "type=" + type +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}