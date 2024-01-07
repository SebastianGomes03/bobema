package entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import objects.ObjectManager;
import utilz.Constants;
import utilz.LoadSave;

public class Bomb extends Entity {

    private BufferedImage[] bombAnimation;
    private int size;
    private boolean isActive = true;
    private int timer = 0;
    private Player owner;
    private int aniTick, aniIndex;
    private BufferedImage img;

    public Bomb(float x, float y, int size, Player owner) {
        super(x, y, 32, 32);
        this.owner = owner;
        this.size = size;
        loadAnimations();
        initHitbox(32, 32);

        // Start the timer
    }

    private void loadAnimations() {
        if(!owner.pierce)
            img = LoadSave.GetSpriteAtlas(LoadSave.BOMB);
        else
            img = LoadSave.GetSpriteAtlas(LoadSave.BOMB_PIERCE);

        bombAnimation = new BufferedImage[15];
        for (int i = 0; i < bombAnimation.length; i++) {
            bombAnimation[i] = img.getSubimage(i * 32, 0, 32, 32);
        }



    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    public void update() {
        loadAnimations();
        updateAnimationTick();
        if (isActive) {
            timer++;
            if (timer > 200 * Constants.BombConstants.EXPLOSION_TICKS_BASE) {
                explode();
            }
        }
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(bombAnimation[aniIndex], (int) (hitbox.x - lvlOffset), (int) (hitbox.y), (int) (hitbox.width),
                (int) (hitbox.height), null);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= 10) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= bombAnimation.length) {
                aniIndex = 0;
            }
        }
    }

    public void explode() {

        ObjectManager.checkObjectExploded(x, y, size);

        owner.decreaseBombCount();
        setActive(false);
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
