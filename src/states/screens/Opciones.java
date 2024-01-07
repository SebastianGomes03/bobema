package states.screens;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.Game;
import utilz.Constants;
import utilz.LoadSave;

public class Opciones extends JPanel{
    
    BufferedImage imgBackground = LoadSave.GetSpriteAtlas(LoadSave.SCREEN_BACKGROUNG);
    BufferedImage imgBack = LoadSave.GetSpriteAtlas(LoadSave.BTN_BACK);

    JButton btnBack = new JButton();

    public Opciones(){
        setPreferredSize(new Dimension(Constants.ScreenConstants.GAME_WIDTH, Constants.ScreenConstants.GAME_HEIGHT));
        setLayout(null); 

        initButtons();
        initPos();

        add(btnBack);

        setVisible(true);
    }

    private void btnBack(){
        Game.switchScreen(Constants.MenuScreens.MAIN_MENU);
    }

    private void initPos() {
        btnBack.setBounds(Constants.ScreenConstants.GAME_WIDTH / 2 - imgBack.getWidth() / 2, 650, imgBack.getWidth(), imgBack.getHeight());
    }

    private void initButtons() {
        btnBack.addActionListener(e -> btnBack());
    }

    public void paintComponent(Graphics g) {
        g.drawImage(imgBackground, 0, 0, Constants.ScreenConstants.GAME_WIDTH, Constants.ScreenConstants.GAME_HEIGHT,null);
        g.drawImage(imgBack, Constants.ScreenConstants.GAME_WIDTH / 2 - imgBack.getWidth() / 2, 650, null);

        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setOpaque(false);
    }
}
