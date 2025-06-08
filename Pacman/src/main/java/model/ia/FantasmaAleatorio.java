package model.ia;

import estructuras.Vertice;
import model.entidades.Movimiento;
import model.grafo.Arista;

import java.util.ArrayList;
import java.util.Random;

public class FantasmaAleatorio implements FantasmaIA {
    private final Random random = new Random();

    @Override
    public Movimiento calcularMovimiento(Vertice origen, Vertice destino) {
        ArrayList<Movimiento> posibles = new ArrayList<>();

        for (Movimiento dir : Movimiento.getDirecciones()) {
            Arista arista = origen.getArista(dir);
            if (arista != null) {
                posibles.add(dir);
            }
        }

        if (!posibles.isEmpty()) {
            return posibles.get(random.nextInt(posibles.size()));
        }

        return Movimiento.UP;
    }
}
