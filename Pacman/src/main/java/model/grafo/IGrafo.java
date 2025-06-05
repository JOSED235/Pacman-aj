package model.grafo;

import estructuras.ListaEnlazada;

public interface IGrafo<T> {
    void agregarVertice(T vertice);
    void agregarArista(T origen, T destino, double peso);
    ListaEnlazada<T> obtenerAdyacentes(T vertice);
    boolean existeVertice(T vertice);
    boolean existeArista(T origen, T destino);
    double obtenerPesoArista(T origen, T destino);
    int cantidadVertices();
    int cantidadAristas();
    ListaEnlazada<T> obtenerVertices();
}
