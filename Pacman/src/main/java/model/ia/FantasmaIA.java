package model.ia;

import estructuras.Vertice;
import model.entidades.Movimiento;

public interface FantasmaIA {
    Movimiento calcularMovimiento(Vertice origen, Vertice destino);
}
