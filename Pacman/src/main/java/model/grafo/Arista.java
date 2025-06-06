package model.grafo;


public class Arista<T> {
    public T origen;
    public T destino;
    public double peso;

    public Arista(T origen, T destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }
}
