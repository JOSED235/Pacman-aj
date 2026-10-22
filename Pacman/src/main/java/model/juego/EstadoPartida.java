package model.juego;

/** Etapas de una partida. Solo en JUGANDO se mueven Pac-Man y los fantasmas. */
public enum EstadoPartida {
    LISTO,
    JUGANDO,
    PAUSA,
    MUERTE,
    NIVEL_COMPLETADO,
    FIN
}
