package main;

import javax.swing.JFrame;

import states.GameState;
import states.MenuState;
import utilz.Constants;
import utilz.LoadSave;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.CardLayout;


public class Game extends Canvas implements Runnable {

    static final long serialVersionUID = 1L;

    static JFrame window;
    public static GameState screen;
    public static MenuState menu;
    public static AppState state = AppState.MENU;
    public static CardLayout cl = new CardLayout();

    public enum AppState {
        MENU, JUEGO
    }

    Thread hilo;

    float fps = 120, tact = 1000000000 / fps;
    long updateRef = System.nanoTime();

    public static volatile boolean Running = false;

    public Game() {
        window = new JFrame("Bomberman");
        window.setIconImage(LoadSave.GetSpriteAtlas(LoadSave.ICON));
        window.setSize(Constants.ScreenConstants.GAME_WIDTH, Constants.ScreenConstants.GAME_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(cl);
        window.setLocationRelativeTo(null);

        state = AppState.MENU;
        menu = new MenuState();

        window.getContentPane().add(menu, "Menu");
        cl.show(window.getContentPane(), "Menu");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.add(this, BorderLayout.CENTER);

        start();
    }

    public static void switchState(){
        if(state == AppState.JUEGO){
            //menu.audioPlayer.stopSong();
            state = AppState.MENU;
            window.remove(screen);
            screen = null;
            menu = new MenuState();
            window.add(menu, "Menu");
            cl.show(window.getContentPane(), "Menu");
        }else{
            window.remove(menu);
            menu = null;
            screen = new GameState();
            window.add(screen, "Juego");
            cl.show(window.getContentPane(), "Juego");
            screen.requestFocusInWindow();
            state = AppState.JUEGO;
        }
    }

    public static void switchScreen(Constants.MenuScreens screens){
        switch (screens) {
            case MAIN_MENU:
                MenuState.cl.show(menu, "MenuPrincipal");
                break;
            case MULTIPLAYER:
                MenuState.cl.show(menu, "Multijugador");
                break;
            case OPTIONS:
                MenuState.cl.show(menu, "Opciones");
                break;
            case HELP:
                MenuState.cl.show(menu, "Ayuda");
                break;
            case STATS:
                MenuState.cl.show(menu, "Estadisticas");
                break;
            case ABOUT:
                MenuState.cl.show(menu, "AcercaDe");
                break;
            default:
                break;
        }
    }

    public void start() {
        Running = true;
        hilo = new Thread(this, "Graficos");
        hilo.start();
    }

    public void stop() {
        Running = false;
        try {
            hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void draw() {
        window.repaint();
    }

    private void update() {
        if(state == AppState.JUEGO){
            screen.update();
        }
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / fps;
        double timePerUpdate = 1000000000.0 / 200;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                draw();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                window.setTitle("Bomberman | FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }

    }

    public static void exit(){
        System.exit(0);
    }

}
