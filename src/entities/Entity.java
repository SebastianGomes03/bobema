package entities;

import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import utilz.Constants;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	protected int state;
	protected int aniIndex;
	protected int maxHealth;
	protected int currentHealth;

	public Entity(float x, float y, int width, int height) {
		this.x = x ;
		this.y = y;
		this.width = width;
		this.height = height;

	}

	protected void initHitbox(int width, int height) {
		//hitbox = new Rectangle2D.Float(x+3, y+16+3, (int) (width -6 * Game.SCALE), (int) (height - 16 -6 * Game.SCALE));
		hitbox = new Rectangle2D.Float(x + 16, y + 16, (width * Constants.ScreenConstants.SCALE) - 16, (height * Constants.ScreenConstants.SCALE) - 16);
	}

	protected void drawHitbox(Graphics g, int xLvlOffset){
		//debugging hitbox
		g.setColor(Color.PINK);
		g.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public int getState(){
        return state;
    }

	public int getAniIndex(){
        return aniIndex;
    }
}
