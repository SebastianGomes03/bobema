package states;

import javax.swing.JPanel;

import entities.Player;
import inputs.MouseInputs;
import inputs.KeyboardInputs;
import levels.LevelManager;
import objects.ObjectManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
//import java.awt.RenderingHints.Key;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
//import java.util.Random;

import utilz.Constants;

//import entities.Player;
//import levels.LevelManager;
//import objects.ObjectManager;

//import ui.PauseOverlay;
import utilz.LoadSave;

import audio.AudioPlayer;

public class GameState extends JPanel{
    private Player player;
    private ObjectManager objectManager;
    private LevelManager levelManager;
    //private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private boolean playerDying;
    
    private int xLvlOffset;
    private int leftBorder = (int)(0.2 * Constants.ScreenConstants.GAME_WIDTH);
    private int rightBorder = (int)(0.8 * Constants.ScreenConstants.GAME_WIDTH);
    private int maxLvlOffsetX;

    private BufferedImage backgroundImg;

    //private boolean playerDying, gameOver;

    KeyboardInputs keyboardInputs;
    private MouseInputs mouseInputs;

    public AudioPlayer audioPlayer;


    public GameState(){
        initClasses();
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND);
        loadStartLevel();

        setPreferredSize(new Dimension(Constants.ScreenConstants.GAME_WIDTH, Constants.ScreenConstants.GAME_HEIGHT));
        addKeyListener(keyboardInputs = new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
    }

    public void loadNextLevel(){
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel() {
        objectManager.loadObjects(levelManager.getCurrentLevel());
        //audioPlayer.playSong(AudioPlayer.LEVEL_MUSIC);
    }

    private void initClasses() {
        levelManager = new LevelManager(this);
        objectManager = new ObjectManager(this);
        //audioPlayer = new AudioPlayer();

        player = new Player(200, 200, (int)(32 * Constants.ScreenConstants.SCALE), (int)(48 * Constants.ScreenConstants.SCALE), this, levelManager.getCurrentLevel());
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        //pauseOverlay = new PauseOverlay(this);
    }

    public void update(){
        //if(paused){
        //    pauseOverlay.update();
        if(playerDying){
            player.update();

        }else{
            levelManager.update();
            objectManager.update();
            player.update();
            playEffects();
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        g.drawImage(backgroundImg, 0, 0, Constants.ScreenConstants.GAME_WIDTH, Constants.ScreenConstants.GAME_HEIGHT, null);

        levelManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Constants.ScreenConstants.GAME_WIDTH, Constants.ScreenConstants.GAME_HEIGHT);
            //pauseOverlay.draw(g);
        }
    }

    private void resetAll() {
        //gameOver = false;
        paused = false;
        player.resetAll();
        objectManager.resetAllObjects();
        audioPlayer.stopSong();
    }

    public void playEffects(){
        //if(player.moving)
        //    audioPlayer.playEffect(AudioPlayer.MOVING);
        
        //if(player.dying)
        //    audioPlayer.playEffect(AudioPlayer.DYING);

        //if(player.bomb)
        //    audioPlayer.playEffect(AudioPlayer.PLACE_BOMB);

    }

//    public void setPlayerDying(boolean playerDying) {
//        this.playerDying = playerDying;
//    }
//
//    public void setGameOver(boolean gameOver) {
//        this.gameOver = gameOver;  
//    }

    public void checkObjectTouched(Float hitbox) {
        objectManager.checkObjectTouched(player.getHitbox());
    }



    public boolean checkObjectCollision(Float hitbox, boolean moving) {
        return (objectManager.checkObjectCollision(player.getHitbox(), player.getMoving()));
    }

    public Player getPlayer(){
        return player;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public LevelManager getLevelManager(){
        return levelManager;
    }

    public void unpauseGame(){
        paused = false;
    }

    public void setMaxLvlOffset(int lvlOffset){
        this.maxLvlOffsetX = lvlOffset;
    }

    public void windowFocusLost(){
        player.resetDirBooleans();
    }
}
