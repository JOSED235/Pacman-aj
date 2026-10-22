package controller;

import estructuras.Vertice;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.entidades.*;
import model.ia.*;
import model.juego.EstadoPartida;
import model.juego.Laberinto;
import model.juego.Nivel;
import model.juego.Puntuacion;
import ui.GameOverView;
import ui.HudView;
import ui.Menuview;
import ui.PausaView;
import ui.entidades.*;

/**
 * Controla una partida: ciclo de juego de Pac-Man, hilos de los fantasmas, puntaje,
 * vidas, pastillas de poder, pausa, niveles y fin del juego.
 * Todo el estado se modifica en el hilo de JavaFX; los hilos de los fantasmas solo calculan.
 */
public class JuegoController {

    private static final int COLUMNAS = 28;
    private static final int FILAS = 31;
    // Cada fantasma sale de la casa con unos segundos de diferencia
    private static final int[] RETRASO_SALIDA = {0, 2500, 5000, 7500};

    @FXML
    private StackPane root;

    private final HudView hud = new HudView();
    private final StackPane tablero = new StackPane();
    private final Label mensaje = new Label();

    private Laberinto laberinto;
    private Pacman pacman;
    private PacmanView pacmanView;
    private final Fantasma[] fantasmas = new Fantasma[4];
    private final FantasmaView[] vistas = new FantasmaView[4];
    private final FantasmaThread[] hilos = new FantasmaThread[4];

    private Puntuacion puntuacion = new Puntuacion();
    private Nivel nivel = new Nivel(1);
    private EstadoPartida estado = EstadoPartida.LISTO;
    private int comestiblesRestantes;

    private Movimiento direccionActual;
    private Movimiento direccionDeseada;
    private Vertice pacmanAnterior;

    private Timeline cicloPacman;
    private Timeline parpadeo;
    private PauseTransition finSusto;
    private PauseTransition avisoFinSusto;
    private PauseTransition espera;
    private Node capaSuperior;

    @FXML
    public void initialize() {
        tablero.setAlignment(Pos.TOP_LEFT);
        mensaje.getStyleClass().add("mensaje");
        mensaje.setMouseTransparent(true);

        VBox pantalla = new VBox(hud.getNode(), tablero);
        root.getChildren().setAll(pantalla);
        root.setFocusTraversable(true);
        root.setOnKeyPressed(this::onKeyPressed);
        root.setOnMouseClicked(e -> root.requestFocus());

        iniciarNivel(new Nivel(1), Pacman.VIDAS_INICIALES);
    }

    // ---------------------------------------------------------------- niveles

    private void iniciarNivel(Nivel nuevoNivel, int vidas) {
        detener();
        nivel = nuevoNivel;
        laberinto = new Laberinto();
        comestiblesRestantes = laberinto.cantidadComestibles();

        pacman = new Pacman(laberinto.getPacman());
        pacman.setVidas(vidas);
        pacmanView = new PacmanView(pacman);
        pacmanView.mirarHacia(Movimiento.LEFT);

        fantasmas[0] = new Fantasma(laberinto.getGhost1(), new FantasmaDijkstra()); // rojo: persigue con Dijkstra
        fantasmas[1] = new Fantasma(laberinto.getGhost2(), new FantasmaBFS());      // rosado: persigue con BFS
        fantasmas[2] = new Fantasma(laberinto.getGhost3(), new FantasmaAleatorio()); // azul: deambula
        fantasmas[3] = new Fantasma(laberinto.getGhost4(), new FantasmaAleatorio()); // naranja: deambula
        int[] colores = {4, 1, 2, 3};
        Vertice salida = laberinto.getGrafo().getVertice(11, 13);

        Pane capaEntidades = new Pane();
        capaEntidades.getChildren().add(pacmanView.getNode());
        for (int i = 0; i < 4; i++) {
            vistas[i] = new FantasmaView(fantasmas[i], colores[i]);
            capaEntidades.getChildren().add(vistas[i].getNode());
            hilos[i] = new FantasmaThread(fantasmas[i], vistas[i], pacman, salida,
                    nivel.tiempoFantasmas(), Integer.MAX_VALUE, this::revisarChoques);
            hilos[i].pausar();
            hilos[i].start();
        }

        // Los consumibles van debajo de los personajes; la grilla fija evita que filas vacías se colapsen
        GridPane consumibles = laberinto.getConsumibles();
        fijarCeldas(consumibles);
        tablero.getChildren().setAll(laberinto.obtenerGrid(), consumibles, capaEntidades, mensaje);
        StackPane.setAlignment(mensaje, Pos.CENTER);

        cicloPacman = new Timeline(new KeyFrame(Duration.millis(nivel.tiempoPacman()), e -> pasoPacman()));
        cicloPacman.setCycleCount(Timeline.INDEFINITE);
        cicloPacman.play();

        actualizarHud();
        mostrarListo();
    }

    private static void fijarCeldas(GridPane grid) {
        if (!grid.getColumnConstraints().isEmpty()) return;
        for (int c = 0; c < COLUMNAS; c++) grid.getColumnConstraints().add(new ColumnConstraints(Laberinto.CELL_SIZE));
        for (int f = 0; f < FILAS; f++) grid.getRowConstraints().add(new RowConstraints(Laberinto.CELL_SIZE));
    }

    /** "¡LISTO!" durante dos segundos; luego empieza a moverse todo y los fantasmas salen escalonados. */
    private void mostrarListo() {
        estado = EstadoPartida.LISTO;
        mostrarMensaje("¡LISTO!");
        esperarY(2000, () -> {
            ocultarMensaje();
            estado = EstadoPartida.JUGANDO;
            for (int i = 0; i < 4; i++) {
                hilos[i].esperar(RETRASO_SALIDA[i]);
                hilos[i].reanudar();
            }
        });
    }

    private void nivelCompletado() {
        estado = EstadoPartida.NIVEL_COMPLETADO;
        pausarFantasmas();
        terminarSusto();
        mostrarMensaje("¡NIVEL " + nivel.getNumero() + " COMPLETADO!");
        esperarY(2500, () -> iniciarNivel(nivel.siguiente(), pacman.getVidas()));
    }

    // ---------------------------------------------------------------- Pac-Man

    private void pasoPacman() {
        if (estado != EstadoPartida.JUGANDO) return;

        // Giro anticipado: la dirección pedida se aplica en cuanto el laberinto lo permite
        if (direccionDeseada != null && pacman.puedeMover(direccionDeseada)) {
            direccionActual = direccionDeseada;
        }
        if (direccionActual == null) return;

        Vertice antes = pacman.getPosicion();
        if (pacmanView.moverA(Duration.millis(nivel.tiempoPacman()), null, direccionActual)) {
            pacmanAnterior = antes;
            comer();
            revisarChoques();
        }
    }

    private void comer() {
        Consumible consumible = pacman.getPosicion().getConsumuble();
        if (consumible == null) return;

        if (puntuacion.sumar(consumible.getScore())) {
            pacman.ganarVida();
        }
        if (consumible.getTipo() != TypeOfConsumable.TROPHY) {
            comestiblesRestantes--;
        }
        if (consumible.getTipo() == TypeOfConsumable.PILL) {
            activarSusto();
        }
        actualizarHud();
        if (comestiblesRestantes == 0) {
            nivelCompletado();
        }
    }

    // ---------------------------------------------------------------- pastilla de poder

    private void activarSusto() {
        terminarSusto();
        pacman.activarPoder();
        puntuacion.reiniciarCombo();
        for (int i = 0; i < 4; i++) {
            fantasmas[i].setAsustado(true);
            vistas[i].setAsustado(true);
        }
        int duracion = nivel.duracionSusto();
        avisoFinSusto = new PauseTransition(Duration.millis(duracion - 2000));
        avisoFinSusto.setOnFinished(e -> iniciarParpadeo());
        avisoFinSusto.play();
        finSusto = new PauseTransition(Duration.millis(duracion));
        finSusto.setOnFinished(e -> terminarSusto());
        finSusto.play();
    }

    private void iniciarParpadeo() {
        parpadeo = new Timeline(
                new KeyFrame(Duration.millis(0), e -> parpadearFantasmas(false)),
                new KeyFrame(Duration.millis(200), e -> parpadearFantasmas(true)),
                new KeyFrame(Duration.millis(400)));
        parpadeo.setCycleCount(Timeline.INDEFINITE);
        parpadeo.play();
    }

    private void parpadearFantasmas(boolean visible) {
        for (int i = 0; i < 4; i++) {
            if (fantasmas[i].isAsustado()) vistas[i].parpadear(visible);
        }
    }

    private void terminarSusto() {
        if (avisoFinSusto != null) avisoFinSusto.stop();
        if (finSusto != null) finSusto.stop();
        if (parpadeo != null) parpadeo.stop();
        if (pacman != null) pacman.desactivarPoder();
        for (int i = 0; i < 4; i++) {
            if (fantasmas[i] != null && fantasmas[i].isAsustado()) {
                fantasmas[i].setAsustado(false);
                vistas[i].setAsustado(false);
            }
        }
    }

    // ---------------------------------------------------------------- choques

    /** Se llama tras cada paso de Pac-Man y de cada fantasma, siempre en el hilo de JavaFX. */
    private void revisarChoques() {
        if (estado != EstadoPartida.JUGANDO) return;
        Vertice pos = pacman.getPosicion();
        for (int i = 0; i < 4; i++) {
            Fantasma f = fantasmas[i];
            boolean mismaCasilla = f.getPosicion().equals(pos);
            // Se cruzaron: cada uno quedó en la casilla de la que venía el otro
            boolean seCruzaron = pos.equals(f.getPosicionAnterior()) && f.getPosicion().equals(pacmanAnterior);
            if (!mismaCasilla && !seCruzaron) continue;

            if (f.isAsustado()) {
                comerFantasma(i);
            } else {
                perderVida();
                return;
            }
        }
    }

    private void comerFantasma(int i) {
        int valor = puntuacion.valorSiguienteFantasma();
        if (puntuacion.sumar(valor)) pacman.ganarVida();
        fantasmas[i].reiniciar();
        vistas[i].setAsustado(false);
        vistas[i].relocate();
        hilos[i].esperar(1500); // vuelve a la casa y sale de nuevo un momento después
        actualizarHud();
        mostrarMensajeBreve("+" + valor);
    }

    private void perderVida() {
        estado = EstadoPartida.MUERTE;
        pausarFantasmas();
        terminarSusto();
        pacman.perderVida();
        actualizarHud();
        esperarY(1200, () -> {
            if (pacman.getVidas() <= 0) {
                finDelJuego();
            } else {
                reiniciarPosiciones();
                mostrarListo();
            }
        });
    }

    private void reiniciarPosiciones() {
        pacman.reiniciarPosicion();
        pacmanView.updatePosition();
        pacmanView.mirarHacia(Movimiento.LEFT);
        direccionActual = null;
        direccionDeseada = null;
        pacmanAnterior = null;
        for (int i = 0; i < 4; i++) {
            fantasmas[i].reiniciar();
            vistas[i].setAsustado(false);
            vistas[i].relocate();
        }
    }

    private void finDelJuego() {
        estado = EstadoPartida.FIN;
        detener();
        mostrarCapa(GameOverView.crear(puntuacion.getPuntos(), Puntuacion.getMejorPuntaje(), nivel.getNumero(),
                this::nuevaPartida, this::volverAlMenu));
    }

    private void nuevaPartida() {
        quitarCapa();
        puntuacion = new Puntuacion();
        iniciarNivel(new Nivel(1), Pacman.VIDAS_INICIALES);
    }

    // ---------------------------------------------------------------- pausa y teclado

    @FXML
    public void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP, W -> direccionDeseada = Movimiento.UP;
            case DOWN, S -> direccionDeseada = Movimiento.DOWN;
            case LEFT, A -> direccionDeseada = Movimiento.LEFT;
            case RIGHT, D -> direccionDeseada = Movimiento.RIGHT;
            case P, ESCAPE -> alternarPausa();
            default -> { }
        }
        event.consume();
    }

    private void alternarPausa() {
        if (estado == EstadoPartida.JUGANDO) {
            estado = EstadoPartida.PAUSA;
            pausarFantasmas();
            pausarTemporizadores(true);
            mostrarCapa(PausaView.crear(this::alternarPausa, this::volverAlMenu));
        } else if (estado == EstadoPartida.PAUSA) {
            quitarCapa();
            pausarTemporizadores(false);
            estado = EstadoPartida.JUGANDO;
            for (FantasmaThread h : hilos) h.reanudar();
        }
    }

    private void pausarTemporizadores(boolean pausar) {
        for (javafx.animation.Animation a : new javafx.animation.Animation[]{avisoFinSusto, finSusto, parpadeo}) {
            if (a == null || a.getStatus() == javafx.animation.Animation.Status.STOPPED) continue;
            if (pausar) a.pause(); else a.play();
        }
    }

    private void volverAlMenu() {
        detener();
        Stage juego = (Stage) root.getScene().getWindow();
        Menuview.abrirMenu(new Stage());
        juego.close();
    }

    // ---------------------------------------------------------------- utilidades

    /** Detiene el ciclo, los temporizadores y los hilos de los fantasmas. */
    public void detener() {
        if (cicloPacman != null) cicloPacman.stop();
        if (espera != null) espera.stop();
        terminarSusto();
        for (int i = 0; i < 4; i++) {
            if (hilos[i] != null) hilos[i].detener();
        }
    }

    private void pausarFantasmas() {
        for (FantasmaThread h : hilos) {
            if (h != null) h.pausar();
        }
    }

    private void esperarY(int milisegundos, Runnable accion) {
        if (espera != null) espera.stop();
        espera = new PauseTransition(Duration.millis(milisegundos));
        espera.setOnFinished(e -> accion.run());
        espera.play();
    }

    private void actualizarHud() {
        hud.actualizar(puntuacion.getPuntos(), Puntuacion.getMejorPuntaje(), nivel.getNumero(), pacman.getVidas());
    }

    private void mostrarMensaje(String texto) {
        mensaje.setText(texto);
        mensaje.setVisible(true);
    }

    private void ocultarMensaje() {
        mensaje.setVisible(false);
    }

    private void mostrarMensajeBreve(String texto) {
        mostrarMensaje(texto);
        PauseTransition t = new PauseTransition(Duration.millis(700));
        t.setOnFinished(e -> {
            if (estado == EstadoPartida.JUGANDO) ocultarMensaje();
        });
        t.play();
    }

    private void mostrarCapa(Node capa) {
        quitarCapa();
        capaSuperior = capa;
        tablero.getChildren().add(capa);
    }

    private void quitarCapa() {
        if (capaSuperior != null) tablero.getChildren().remove(capaSuperior);
        capaSuperior = null;
        root.requestFocus();
    }
}
