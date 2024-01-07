package objects;

import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.changeLevelData;

import utilz.Constants;

public class GameContainer extends GameObject {

    public GameContainer(int x, int y, int objType, int[][] lvlData) {
        super(x, y, objType);
        // System.out.println("x: " + x + " y: " + y);
        changeLevelData(x, y, 13, lvlData);
        initHitbox(32, 32);
        createHitbox();

    }

    private void createHitbox() {

        if (objType == BOX) {
            initHitbox(25, 18);

            xDrawOffset = (int) (7 * Constants.ScreenConstants.SCALE);
            yDrawOffset = (int) (12 * Constants.ScreenConstants.SCALE);

        } else {
            initHitbox(23, 25);
            xDrawOffset = (int) (8 * Constants.ScreenConstants.SCALE);
            yDrawOffset = (int) (5 * Constants.ScreenConstants.SCALE);
        }

        hitbox.y += yDrawOffset + (int) (2 * Constants.ScreenConstants.SCALE);
        hitbox.x += xDrawOffset / 2;
    }

    public void update() {
        if (doAnimation)
            updateAnimationTick();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
