package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import objects.GameContainer;
import objects.Potion;
import utilz.HelpMethods;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetPlayerSpawn;


public class Level {

	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	private int[][] lvlData;
	private BufferedImage img;
	private Point playerSpawn;
	private ArrayList<Potion> potions;
	private ArrayList<GameContainer> containers;

	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createPotions();
		createContainers();
		calcPlayerSpawn();
	}

	private void createContainers() {
		containers = HelpMethods.GetContainers(img, lvlData);
	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}

	private void createPotions() {
		potions = HelpMethods.GetPotions(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

	public void setLevelData(int[][] lvlData) {
		this.lvlData = lvlData;
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

		public ArrayList<Potion> getPotions(){
		return potions;
	}

	public ArrayList<GameContainer> getContainers(){
		return containers;
	}
}
