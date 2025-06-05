package estructuras;
import model.grafo.Arista;

public class Vertice {
    private int dato;
    private Arista left;
    private Arista right;
    private Arista up;
    private Arista down;

    public Vertice(int dato) {
        this.dato = dato;
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

    public Arista getLeft() {return left;}
    public void setLeft(Arista left) {
        this.left = left;
    }

    public Arista getRight() {return right;}
    public void setRight(Arista right) { this.right = right; }

    public Arista getUp() {return up;}
    public void setUp(Arista up) { this.up = up; }

    public Arista getDown() {return down;}
    public void setDown(Arista down) { this.down = down; }
}
