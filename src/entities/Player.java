package entities;

//import static main.Game.SCALE;
import static utilz.Constants.PlayerConstants.*;
//import static utilz.Constants.Directions.*;
import static utilz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import levels.Level;
import objects.ObjectManager;
import states.GameState;
import utilz.Constants;
import utilz.LoadSave;

public class Player extends Entity {

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed;
	private int playerAction = IDLEDOWN;
	public boolean moving = false;
	public boolean dying = false;
	public boolean movingLeft, movingUp, movingRight, movingDown, bomb;
	public boolean facingLeft, facingUp, facingRight, facingDown;
	private float playerSpeed;
	private int maxBombs = 1;
	private int currentBombs = 0;
	private int currentPowerUp = Constants.BombConstants.SMALL_BOMB;

	private Level level;

	public Bomb bomba;

	public boolean pierce = false;

	private GameState playing;


	public Player(float x, float y, int width, int height, GameState playing, Level level) {
		super(x, y, width, height);
		facingDown = true;
		this.level = level;
		this.playing = playing;
		this.playerAction = IDLEDOWN;
		this.maxHealth = 1;
		this.currentHealth = maxHealth;
		this.playerSpeed = 0.15f * Constants.ScreenConstants.SCALE;
		loadAnimations();
		initHitbox(32, 34);

	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}

	public void update() {
		if (currentHealth <= 0) {
			dying = true;
			if (playerAction != DEAD) {
				playerAction = DEAD;
				aniTick = 0;
				aniIndex = 0;
				moving = false;
				maxBombs = 0;
				//playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
			} else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= 25 - 1) {
				// playing.setGameOver(true);
				// playing.getGame().getAudioPlayer().stopSong();
				// playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
			} else {
				updateAnimationTick();
			}

			return;
		}
		updatePos();
		checkObjectTouched();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[playerAction][aniIndex], (int) (-3 + hitbox.x), (int) (-22 + hitbox.y),
				(int) (6 + hitbox.width), (int) (22 + hitbox.height), null);

		// drawHitbox(g, lvlOffset);
	}

	private void updateAnimationTick() {
		aniTick++;
		if(playerSpeed < 0.5f * Constants.ScreenConstants.SCALE)
			aniSpeed = 50;
		else
			aniSpeed = 25;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
			}
			if (playerAction == IDLEDOWN || playerAction == IDLEUP || playerAction == IDLELEFT
					|| playerAction == IDLERIGHT)
				resetAniTick();
		}
	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving) {

			facingDown = false;
			facingUp = false;
			facingLeft = false;
			facingRight = false;

			if (isMovingLeft()) {
				playerAction = LEFTP;
				facingLeft = true;
			} else if (isMovingRight()) {
				playerAction = RIGHTP;
				facingRight = true;
			} else if (isMovingUp()) {
				playerAction = UPP;
				facingUp = true;
			} else if (isMovingDown()) {
				playerAction = DOWNP;
				facingDown = true;
			}
		} else {
			if (facingDown)
				playerAction = IDLEDOWN;
			else if (facingUp)
				playerAction = IDLEUP;
			else if (facingLeft)
				playerAction = IDLELEFT;
			else if (facingRight)
				playerAction = IDLERIGHT;
		}
		
		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;
		if (!movingLeft && !movingRight && !movingUp && !movingDown)
			return;

		float xSpeed = 0, ySpeed = 0;

		if (movingLeft)
			xSpeed -= playerSpeed;
		else if (movingRight)
			xSpeed += playerSpeed;

		if (movingUp && movingLeft)
			return;
		else if (movingUp && movingRight)
			return;

		if (movingDown && movingLeft)
			return;
		else if (movingDown && movingRight)
			return;

		if (movingUp && movingDown)
			return;

		if (movingUp)
			ySpeed -= playerSpeed;
		if (movingDown)
			ySpeed += playerSpeed;

		// if (CanMoveHere(x + xSpeed, y + ySpeed, width, height, lvlData)) {
		// this.x += xSpeed;
		// this.y += ySpeed;
		// moving = true;
		// }

		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, level.getLevelData())) {
			hitbox.x += xSpeed;
			hitbox.y += ySpeed;
			moving = true;
		}
	}

	private void loadAnimations() {

		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

		animations = new BufferedImage[9][4];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 32, j * 48, 32, 48);

	}

	public void placeBomb() {
		if (currentBombs < maxBombs) {

			ObjectManager.spawnBomb((int) hitbox.x, (int) hitbox.y, currentPowerUp, this);

			currentBombs++;
		}

	}

	public void decreaseBombCount() {
		currentBombs--;
	}

	public void loadLvlData(int[][] lvlData) {
		level.setLevelData(lvlData);
	}

	public void resetDirBooleans() {
		movingLeft = false;
		movingRight = false;
		movingUp = false;
		movingDown = false;
	}

	// #region Getters and Setters
	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean left) {
		this.movingLeft = left;
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(boolean up) {
		this.movingUp = up;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean right) {
		this.movingRight = right;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(boolean down) {
		this.movingDown = down;
	}

	public int[][] getLvlData() {
		return level.getLevelData();
	}

	private void checkObjectTouched() {
		playing.checkObjectTouched(hitbox);
	}

	public void resetAll() {
		resetDirBooleans();
		moving = false;
		playerAction = IDLEDOWN;

		hitbox.x = x;
		hitbox.y = y;
	}

	public boolean getMoving() {
		return moving;
	}

	// private boolean checkObjectCollision(){
	// return (playing.checkObjectCollision(hitbox, moving));
	// }

	public boolean getBomb() {
		return bomb;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public void increaseBombCount() {
		maxBombs++;

	}

	public void increaseRadius() {
		if (currentPowerUp == Constants.BombConstants.SMALL_BOMB)
			currentPowerUp = Constants.BombConstants.MID_BOMB;
		else if (currentPowerUp == Constants.BombConstants.MID_BOMB)
			currentPowerUp = Constants.BombConstants.BIG_BOMB;
	}

	public void die() {
		currentHealth = 0;
	}

    public void changeBomb() {
		pierce = true;
    }

    public void increaseSpeed() {
		playerSpeed += 0.05f * Constants.ScreenConstants.SCALE;
    }

    public void increaseTimer() {
		Constants.BombConstants.EXPLOSION_TICKS_BASE -= 0.25;
    }

	// #endregion

}
