package model.juego;

/** Dificultad de cada nivel: los fantasmas aceleran y el efecto de la pastilla dura menos. */
public class Nivel {
    private final int numero;

    public Nivel(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public Nivel siguiente() {
        return new Nivel(numero + 1);
    }

    /** Milisegundos entre pasos de Pac-Man. */
    public int tiempoPacman() {
        return 150;
    }

    /** Milisegundos entre pasos de los fantasmas (más rápido en cada nivel, con un límite). */
    public int tiempoFantasmas() {
        return Math.max(160, 230 - (numero - 1) * 20);
    }

    /** Milisegundos que los fantasmas permanecen asustados tras una pastilla de poder. */
    public int duracionSusto() {
        return Math.max(3000, 7000 - (numero - 1) * 1000);
    }
}
