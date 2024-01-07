package states.screens;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.Game;
import utilz.Constants;
import utilz.LoadSave;


public class MenuPrincipal extends JPanel {
    JButton btnSinglePlayer = new JButton();
    JButton btnMultiPlayer = new JButton();
    JButton btnOptions = new JButton();
    JButton btnHelp = new JButton();
    JButton btnStats = new JButton();
    JButton btnAbout = new JButton();
    JButton btnExit = new JButton();

    BufferedImage imgBackground = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);;
    BufferedImage imgSinglePlayer = LoadSave.GetSpriteAtlas(LoadSave.BTN_JUGA);;
    BufferedImage imgMultiPlayer = LoadSave.GetSpriteAtlas(LoadSave.BTN_MULT);
    BufferedImage imgOptions = LoadSave.GetSpriteAtlas(LoadSave.BTN_OPT);
    BufferedImage imgHelp = LoadSave.GetSpriteAtlas(LoadSave.BTN_HELP);
    BufferedImage imgStats = LoadSave.GetSpriteAtlas(LoadSave.BTN_STATS);
    BufferedImage imgAbout = LoadSave.GetSpriteAtlas(LoadSave.BTN_ABOUT);
    BufferedImage imgExit = LoadSave.GetSpriteAtlas(LoadSave.BTN_QUIT);

    public MenuPrincipal() {
        setPreferredSize(new Dimension(Constants.ScreenConstants.GAME_WIDTH, Constants.ScreenConstants.GAME_HEIGHT));
        setLayout(null);
        
        initButtons();
        initPos();
        
        add(btnSinglePlayer);
        add(btnMultiPlayer);
        add(btnOptions);
        add(btnHelp);
        add(btnStats);
        add(btnAbout);
        add(btnExit);

        setVisible(true);       
    }

    public void paintComponent(Graphics g) {
        g.drawImage(imgBackground, 0, 0, Constants.ScreenConstants.GAME_WIDTH, Constants.ScreenConstants.GAME_HEIGHT,null);


        g.drawImage(imgSinglePlayer, Constants.ScreenConstants.GAME_WIDTH/2 - imgSinglePlayer.getWidth()/2, 290, null);
        g.drawImage(imgMultiPlayer, Constants.ScreenConstants.GAME_WIDTH/2 - imgMultiPlayer.getWidth()/2, 350, null);
        g.drawImage(imgOptions, Constants.ScreenConstants.GAME_WIDTH/2 - imgOptions.getWidth()/2, 410, null);
        g.drawImage(imgHelp, Constants.ScreenConstants.GAME_WIDTH/2 - imgHelp.getWidth()/2, 470, null);
        g.drawImage(imgStats, Constants.ScreenConstants.GAME_WIDTH/2 - imgStats.getWidth()/2, 530, null);
        g.drawImage(imgAbout, Constants.ScreenConstants.GAME_WIDTH/2 - imgAbout.getWidth()/2, 590, null);
        g.drawImage(imgExit, Constants.ScreenConstants.GAME_WIDTH/2 - imgExit.getWidth()/2, 650, null);
    }

    private void initPos() {
        btnSinglePlayer.setBounds(Constants.ScreenConstants.GAME_WIDTH / 2 - imgSinglePlayer.getWidth() / 2, 290, imgSinglePlayer.getWidth(), imgSinglePlayer.getHeight());
        btnMultiPlayer.setBounds(Constants.ScreenConstants.GAME_WIDTH / 2 - imgMultiPlayer.getWidth() / 2, 350, imgMultiPlayer.getWidth(), imgMultiPlayer.getHeight());
        btnOptions.setBounds(Constants.ScreenConstants.GAME_WIDTH / 2 - imgOptions.getWidth() / 2, 410, imgOptions.getWidth(), imgOptions.getHeight());
        btnHelp.setBounds(Constants.ScreenConstants.GAME_WIDTH / 2 - imgHelp.getWidth() / 2, 470, imgHelp.getWidth(), imgHelp.getHeight());
        btnStats.setBounds(Constants.ScreenConstants.GAME_WIDTH / 2 - imgStats.getWidth() / 2, 530, imgStats.getWidth(), imgStats.getHeight());
        btnAbout.setBounds(Constants.ScreenConstants.GAME_WIDTH / 2 - imgAbout.getWidth() / 2, 590, imgAbout.getWidth(), imgAbout.getHeight());
        btnExit.setBounds(Constants.ScreenConstants.GAME_WIDTH / 2 - imgExit.getWidth() / 2, 650, imgExit.getWidth(), imgExit.getHeight());

        btnSinglePlayer.setBorderPainted(false);
        btnSinglePlayer.setFocusPainted(false);
        btnSinglePlayer.setContentAreaFilled(false);
        btnSinglePlayer.setOpaque(false);

        btnMultiPlayer.setBorderPainted(false);
        btnMultiPlayer.setFocusPainted(false);
        btnMultiPlayer.setContentAreaFilled(false);
        btnMultiPlayer.setOpaque(false);

        btnOptions.setBorderPainted(false);
        btnOptions.setFocusPainted(false);
        btnOptions.setContentAreaFilled(false);
        btnOptions.setOpaque(false);

        btnHelp.setBorderPainted(false);
        btnHelp.setFocusPainted(false);
        btnHelp.setContentAreaFilled(false);
        btnHelp.setOpaque(false);

        btnStats.setBorderPainted(false);
        btnStats.setFocusPainted(false);
        btnStats.setContentAreaFilled(false);
        btnStats.setOpaque(false);

        btnAbout.setBorderPainted(false);
        btnAbout.setFocusPainted(false);
        btnAbout.setContentAreaFilled(false);
        btnAbout.setOpaque(false);

        btnExit.setBorderPainted(false);
        btnExit.setFocusPainted(false);
        btnExit.setContentAreaFilled(false);
        btnExit.setOpaque(false);
    }

    private void btnSinglePlayer() {
        Game.switchState();
    }

    private void btnMultiPlayer(){
        Game.switchScreen(Constants.MenuScreens.MULTIPLAYER);
    }

    private void btnOptions(){
        Game.switchScreen(Constants.MenuScreens.OPTIONS);
    }

    private void btnHelp(){
        Game.switchScreen(Constants.MenuScreens.HELP);
    }

    private void btnStats(){
        Game.switchScreen(Constants.MenuScreens.STATS);
    }

    private void btnAbout(){
        Game.switchScreen(Constants.MenuScreens.ABOUT);
    }

    private void btnExit(){
        Game.exit();
    }

    private void initButtons() {

        btnSinglePlayer.addActionListener(e -> btnSinglePlayer());
        btnMultiPlayer.addActionListener(e -> btnMultiPlayer());
        btnOptions.addActionListener(e -> btnOptions());
        btnHelp.addActionListener(e -> btnHelp());
        btnStats.addActionListener(e -> btnStats());
        btnAbout.addActionListener(e -> btnAbout());
        btnExit.addActionListener(e -> btnExit());
    }

}
