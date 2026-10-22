package model.juego;

/** Puntaje de la partida, combo de fantasmas comidos y récord de la sesión. */
public class Puntuacion {
    public static final int PUNTOS_VIDA_EXTRA = 10000;
    private static final int VALOR_PRIMER_FANTASMA = 200;

    private static int mejorPuntaje = 0;

    private int puntos;
    private int fantasmasComidos;
    private boolean vidaExtraOtorgada;

    /**
     * Suma puntos.
     * @return true si con esta suma se alcanza la vida extra (solo ocurre una vez por partida)
     */
    public boolean sumar(int cantidad) {
        puntos += cantidad;
        mejorPuntaje = Math.max(mejorPuntaje, puntos);
        if (!vidaExtraOtorgada && puntos >= PUNTOS_VIDA_EXTRA) {
            vidaExtraOtorgada = true;
            return true;
        }
        return false;
    }

    /**
     * Cada fantasma comido durante una misma pastilla de poder vale el doble: 200, 400, 800, 1600.
     * @return puntos ganados por este fantasma
     */
    public int valorSiguienteFantasma() {
        int valor = VALOR_PRIMER_FANTASMA << Math.min(fantasmasComidos, 3);
        fantasmasComidos++;
        return valor;
    }

    /** Se llama al comer una pastilla de poder: el combo vuelve a empezar en 200. */
    public void reiniciarCombo() {
        fantasmasComidos = 0;
    }

    public int getPuntos() {
        return puntos;
    }

    public static int getMejorPuntaje() {
        return mejorPuntaje;
    }
}
