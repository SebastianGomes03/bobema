package utilz;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import objects.GameContainer;
import objects.Potion;
import static utilz.Constants.ObjectConstants.*;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float heigth, int[][] lvlData){
		if(!IsSolid(x, y, lvlData))
			if(!IsSolid(x + width, y + heigth, lvlData))
				if(!IsSolid(x + width, y, lvlData))
					if(!IsSolid(x, y + heigth, lvlData))
							if(!IsSolid(x, y + heigth/2, lvlData))
								if(!IsSolid(x + width, y + heigth/2, lvlData))
									return true;
		return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData){
        int maxWidth = lvlData[0].length * Constants.ScreenConstants.TILES_SIZE;
        if(x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Constants.ScreenConstants.GAME_HEIGHT)
            return true;

        float xIndex = x / Constants.ScreenConstants.TILES_SIZE;
        float yIndex = y / Constants.ScreenConstants.TILES_SIZE;

        return IsTileSolid((int)xIndex, (int)yIndex, lvlData);
    }

	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];

		//System.out.println(value);
		switch (value) {
			case 11:
			case 48:
			case 49:
				return false;
			default:
				return true;
		}
	}
	
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed){
        int currentTile = (int)(hitbox.x / Constants.ScreenConstants.TILES_SIZE);
        if(xSpeed > 0){
            //right
            int tileXPos = currentTile * Constants.ScreenConstants.TILES_SIZE;
            int xOffset = (int)(Constants.ScreenConstants.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        }else
            //left
            return currentTile * Constants.ScreenConstants.TILES_SIZE;
    }  

	public static float GetEntityYPosNextToWall(Rectangle2D.Float hitbox, float ySpeed){
		int currentTile = (int)(hitbox.y / Constants.ScreenConstants.TILES_SIZE);
		if(ySpeed > 0){
			//down
			int tileYPos = currentTile * Constants.ScreenConstants.TILES_SIZE;
			int yOffset = (int)(Constants.ScreenConstants.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		}else
			//up
			return currentTile * Constants.ScreenConstants.TILES_SIZE;
	}

	public static int[][] GetLevelData(BufferedImage img) {
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;
	}

	public static Point GetPlayerSpawn(BufferedImage img){
        for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 100)
					return new Point(i * Constants.ScreenConstants.TILES_SIZE, j * Constants.ScreenConstants.TILES_SIZE);
			}
        return new Point(1 * Constants.ScreenConstants.TILES_SIZE, 1 * Constants.ScreenConstants.TILES_SIZE);
    }

	public static ArrayList<Potion> GetPotions(BufferedImage img){
		ArrayList<Potion> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == 0 || value == 1)
					list.add(new Potion(i * Constants.ScreenConstants.TILES_SIZE, j * Constants.ScreenConstants.TILES_SIZE, value));
			}
		return list;
	}

	public static ArrayList<GameContainer> GetContainers(BufferedImage img, int[][] lvlData){
        ArrayList<GameContainer> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == BOX || value == BARREL)
                    list.add(new GameContainer(i * Constants.ScreenConstants.TILES_SIZE, j * Constants.ScreenConstants.TILES_SIZE, value, lvlData));
            }
        return list;
    }

	public static void changeLevelData(int x, int y, int value, int[][] lvlData){
		lvlData[y/48][x/48]=value;
	}
}
