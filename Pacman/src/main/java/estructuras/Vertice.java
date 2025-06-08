package estructuras;

import model.entidades.Movimiento;
import model.grafo.Arista;

public class Vertice {

    private int dato;
    private Arista left;
    private Arista right;
    private Arista up;
    private Arista down;
    private int x;
    private int y;

    public Vertice(int dato, int x, int y) {
        this.dato = dato;
        this.x = x;
        this.y = y;
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
    }

    public int getDato() {
        return dato;
    }
    public void setDato(int dato) {
        this.dato = dato;
    }

    public Arista getLeft() {
        return left;
    }
    public void setLeft(Arista left) {
        this.left = left;
    }

    public Arista getRight() {
        return right;
    }
    public void setRight(Arista right) {
        this.right = right;
    }

    public Arista getUp() {
        return up;
    }
    public void setUp(Arista up) {
        this.up = up;
    }

    public Arista getDown() {
        return down;
    }
    public void setDown(Arista down) {
        this.down = down;
    }

    public int getX() {return  x;}
    public void setX(int x) {this.x = x;}

    public int getY() {return y;}
    public void setY(int y) {this.y = y;}

    public boolean isNull(Movimiento direction){
        boolean result = false;
        if(direction.equals(Movimiento.UP)){
            if(this.up == null){
                result = true;
            }
        } else if (direction.equals(Movimiento.DOWN)) {
            if(this.down == null){
                result = true;
            }
        } else if (direction.equals(Movimiento.LEFT)) {
            if(this.left == null){
                result = true;
            }
        } else if (direction.equals(Movimiento.RIGHT)) {
            if(this.right == null){
                result = true;
            }
        }
        return result;
    }

    public Arista getArista(Movimiento direction) {
        return switch (direction) {
            case UP -> getUp();
            case DOWN -> getDown();
            case LEFT -> getLeft();
            case RIGHT -> getRight();
        };
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertice v = (Vertice) obj;
        return this.x == v.x && this.y == v.y;
    }


}
