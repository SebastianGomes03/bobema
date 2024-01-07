package utilz;

public class Constants {

    public enum MenuScreens {
        MAIN_MENU,
        MULTIPLAYER,
        OPTIONS,
        HELP,
        STATS,
        ABOUT
    }

    public static class ScreenConstants {
        public final static int TILES_DEFAULT_SIZE = 32;
        public final static float SCALE = 1.5f;
        public final static int TILES_IN_WIDTH = 19;
        public final static int TILES_IN_HEIGHT = 15;
        public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
        public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
        public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    }

    public static class Directions {
        public static final int UP = 0;
        public static final int DOWN = 4;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;
    }

    public static class ObjectConstants {

        public static final int POWER_BOMB = 0;
        public static final int POWER_FIRE = 1;
        public static final int POWER_SPEED = 2;
        public static final int POWER_PIERCE = 3;
        public static final int POWER_TIMER = 4;
        
        public static final int BARREL = 2;
        public static final int BOX = 3;

        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 39;
        public static final int CONTAINER_HEIGHT_DEFAULT = 32;
        public static final int CONTAINER_WIDTH = (int) (ScreenConstants.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int) (ScreenConstants.SCALE * CONTAINER_HEIGHT_DEFAULT);

        public static final int POTION_WIDTH_DEFAULT = 16;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (ScreenConstants.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (ScreenConstants.SCALE * POTION_HEIGHT_DEFAULT);

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case BARREL:
                case BOX:
                    return 8;
            }
            return 1;
        }
    }

    public static class Ui {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * ScreenConstants.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * ScreenConstants.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * ScreenConstants.SCALE);
        }

        public static class UrmButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * ScreenConstants.SCALE);
        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * ScreenConstants.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * ScreenConstants.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * ScreenConstants.SCALE);
        }
    }

    public static class BombConstants {
        public static final int SMALL_BOMB = 1;
        public static final int MID_BOMB = 2;
        public static final int BIG_BOMB = 3;

        public static int EXPLOSION_TICKS_BASE = 3;
        public static int EXPLOSION_TICKS_UPGRADED = 2;
    }

    public static class PlayerConstants {
        public static final int UPP = 0;
        public static final int DOWNP = 1;
        public static final int LEFTP = 2;
        public static final int RIGHTP = 3;
        public static final int DEAD = 4;
        public static final int IDLEUP = 5;
        public static final int IDLEDOWN = 6;
        public static final int IDLELEFT = 7;
        public static final int IDLERIGHT = 8;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {

                case LEFTP:
                case RIGHTP:
                case UPP:
                case DOWNP:
                return 3;
                case DEAD:
                return 4;
                default:
                    return 1;
            }
        }

    }
}
