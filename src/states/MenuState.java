package states;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import states.screens.AcercaDe;
import states.screens.Ayuda;
import states.screens.MenuPrincipal;
import states.screens.Multijugador;
import states.screens.Opciones;
import states.screens.Stats;
import utilz.Constants;
import audio.AudioPlayer;

public class MenuState extends JPanel {

    public static CardLayout cl = new CardLayout();
    public static MenuPrincipal menuPrincipal = new MenuPrincipal();
    public static Multijugador multijugador = new Multijugador();
    public static Opciones opciones = new Opciones();
    public static Ayuda ayuda = new Ayuda();
    public static Stats stats = new Stats();
    public static AcercaDe acercaDe = new AcercaDe();

    static int counter = 0;
    public BufferedImage aux;

    //public AudioPlayer audioPlayer = new AudioPlayer();

    public MenuState() {

        //audioPlayer.playSong(AudioPlayer.MENU_MUSIC);
        setPreferredSize(new Dimension(Constants.ScreenConstants.GAME_WIDTH, Constants.ScreenConstants.GAME_HEIGHT));
        setBackground(Color.BLACK);
        setLayout(cl);

        add(menuPrincipal, "MenuPrincipal");
        add(multijugador, "Multijugador");
        add(opciones, "Opciones");
        add(ayuda, "Ayuda");
        add(stats, "Stats");
        add(acercaDe, "AcercaDe");

        cl.show(this, "MenuPrincipal");



        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        menuPrincipal.paintComponent(g);
        multijugador.paintComponent(g);
        opciones.paintComponent(g);
        ayuda.paintComponent(g);
        stats.paintComponent(g);
        acercaDe.paintComponent(g);
    }
}
