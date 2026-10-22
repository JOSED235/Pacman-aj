package model.ia;

import estructuras.Vertice;
import model.entidades.Movimiento;
import model.grafo.Arista;

import java.util.ArrayList;
import java.util.Random;

/**
 * Fantasma errante: en cada cruce elige un camino al azar. No se devuelve por donde vino
 * salvo en un callejón sin salida, para que no tiemble entre dos casillas.
 * Cada fantasma necesita su propia instancia porque recuerda su último movimiento.
 */
public class FantasmaAleatorio implements FantasmaIA {
    private final Random random = new Random();
    private Movimiento ultimo;

    @Override
    public Movimiento calcularMovimiento(Vertice origen, Vertice destino) {
        ArrayList<Movimiento> posibles = new ArrayList<>();

        for (Movimiento dir : Movimiento.getDirecciones()) {
            Arista arista = origen.getArista(dir);
            if (arista != null && (ultimo == null || dir != ultimo.opuesto())) {
                posibles.add(dir);
            }
        }
        if (posibles.isEmpty() && ultimo != null && origen.getArista(ultimo.opuesto()) != null) {
            posibles.add(ultimo.opuesto()); // callejón sin salida: única opción es devolverse
        }

        ultimo = posibles.isEmpty() ? null : posibles.get(random.nextInt(posibles.size()));
        return ultimo;
    }
}
