package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import entities.Bomb;
import entities.Player;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.changeLevelData;

import states.GameState;

public class ObjectManager {

    private static GameState playing;
    private BufferedImage[][] containerImgs;
    private BufferedImage[] powerups;
    private static ArrayList<Potion> potions;
    private static ArrayList<GameContainer> containers;
    private static ArrayList<Bomb> bombs = new ArrayList<Bomb>();

    static Random rand = new Random();

    public ObjectManager(GameState playing) {
        ObjectManager.playing = playing;
        loadImgs();
    }

    public static void spawnBomb(int x, int y, int size, Player owner) {

        x = x - x % 48;
        y = y - y % 48;

        bombs.add(new Bomb((x - 10), (y - 8), size, owner));
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Potion p : potions)
            if (p.isActive()) {
                if (hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
    }

    public void applyEffectToPlayer(Potion p) {
        if (p.getObjType() == POWER_BOMB)
            playing.getPlayer().increaseBombCount();
        else if (p.getObjType() == POWER_FIRE)
            playing.getPlayer().increaseRadius();
        else if (p.getObjType() == POWER_PIERCE)
            playing.getPlayer().changeBomb();
        else if (p.getObjType() == POWER_SPEED)
            playing.getPlayer().increaseSpeed();
        else if (p.getObjType() == POWER_TIMER)
            playing.getPlayer().increaseTimer();

    }

    // checks if a bomb is placed near a container, siguiendo un patron
    public static void checkObjectExploded(float x, float y, int size) {
        ArrayList<Rectangle2D> toRemove = new ArrayList<Rectangle2D>();

        // Add blocks in a cross pattern to the toRemove list
        toRemove.add(new Rectangle2D.Float(x * 48, y * 48, 48, 48)); // Center block
        for (int i = 1; i <= size; i++) {
            toRemove.add(new Rectangle2D.Float(x - i * 48, y, 48, 48)); // Left block
            toRemove.add(new Rectangle2D.Float(x + i * 48, y, 48, 48)); // Right block
            toRemove.add(new Rectangle2D.Float(x, y - i * 48, 48, 48)); // Top block
            toRemove.add(new Rectangle2D.Float(x, y + i * 48, 48, 48)); // Bottom block
        }

        for (GameContainer gc : containers) {
            if (gc.isActive() && !gc.doAnimation) {
                for (Rectangle2D r : toRemove) {
                    if (playing.getPlayer().getHitbox().intersects(r))
                        playing.getPlayer().die();                        
                    if (gc.getHitbox().intersects(r)) {
                        changeLevelData((int) gc.getHitbox().x, (int) gc.getHitbox().y, 11,
                                playing.getLevelManager().getCurrentLevel().getLevelData());
                        gc.setAnimation(true);
                        int type = 0;
                        if (gc.getObjType() == BARREL)
                            type = rand.nextInt(7);
                        if (type <= 5)
                            potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2 - 8),
                                    (int) (gc.getHitbox().y - gc.getHitbox().height / 2) + 7, type));
                    }
                }
            }
        }
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
    }

    private void loadImgs() {
        BufferedImage powerupSprite = LoadSave.GetSpriteAtlas(LoadSave.POWERUP_ATLAS);
        powerups = new BufferedImage[5];

        for (int i = 0; i < powerups.length; i++)
            powerups[i] = powerupSprite.getSubimage(i * 32, 0, 32, 32);

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++)
            for (int i = 0; i < containerImgs[j].length; i++)
                containerImgs[j][i] = containerSprite.getSubimage(i * 40, j * 30, 40, 30);
    }

    public void update() {
        for (Potion p : potions)
            if (p.isActive())
                p.update();

        for (GameContainer gc : containers)
            if (gc.isActive())
                gc.update();

        if (bombs != null)
            for (Bomb b : bombs)
                if (b.isActive())
                    b.update();
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBombs(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawPotions(g, xLvlOffset);
    }

    private void drawBombs(Graphics g, int xLvlOffset) {
        for (Bomb b : bombs)
            if (b.isActive())
                b.render(g, xLvlOffset);
    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainer gc : containers)
            if (gc.isActive()) {
                int type = 0;
                if (gc.getObjType() == BOX)
                    type = 1;
                g.drawImage(containerImgs[type][gc.getAniIndex()],
                        (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset - 1),
                        (int) (gc.getHitbox().y - gc.getyDrawOffset() - 4), 59, 49, null);

            }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion p : potions)
            if (p.isActive()) {
                int type = 0;

                if (p.getObjType() == POWER_BOMB)
                    type = 0;
                else if (p.getObjType() == POWER_FIRE)
                    type = 1;
                else if (p.getObjType() == POWER_PIERCE)
                    type = 2;
                else if (p.getObjType() == POWER_SPEED)
                    type = 3;
                else if (p.getObjType() == POWER_TIMER)
                    type = 4;
                g.drawImage(powerups[type], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                        (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT, null);
            }
    }

    public void resetAllObjects() {

        loadObjects(playing.getLevelManager().getCurrentLevel());

        for (Potion p : potions)
            p.reset();
        ;

        for (GameContainer gc : containers)
            gc.reset();
    }

    public boolean checkObjectCollision(Float hitbox, boolean moving) {
        for (GameContainer gc : containers)
            if (gc.isActive() && !gc.doAnimation && moving) {
                if (gc.getHitbox().intersects(hitbox))
                    return true;
            }
        return false;
    }

}
