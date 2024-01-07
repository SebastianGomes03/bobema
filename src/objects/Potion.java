package objects;

import utilz.Constants;

public class Potion extends GameObject{

    private float hoverOffset;
    private int maxHoverOffset, hoverDir = 1;

    public Potion(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(16, 16);
        xDrawOffset = (int)(3 * Constants.ScreenConstants.SCALE);
        yDrawOffset = (int)(2 * Constants.ScreenConstants.SCALE);

        maxHoverOffset = (int)(5 * Constants.ScreenConstants.SCALE);
    }

    public void update(){
        //updateAnimationTick();
        updateHover();
    }

    private void updateHover() {
        hoverOffset += (0.045 * Constants.ScreenConstants.SCALE * hoverDir);
        
        if(hoverOffset >= maxHoverOffset)
            hoverDir = -1;
        else if(hoverOffset < 0)
            hoverDir = 1;

        hitbox.y = y + hoverOffset + 15;
    }
}
