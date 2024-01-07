package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entities.Player;
import states.GameState;
import main.Game;

public class KeyboardInputs implements KeyListener {

	public Player player;

	public KeyboardInputs(GameState gameState) {
		this.player = gameState.getPlayer();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setMovingLeft(false);
				break;
			case KeyEvent.VK_W:
				player.setMovingUp(false);
				break;
			case KeyEvent.VK_D:
				player.setMovingRight(false);
				break;
			case KeyEvent.VK_S:
				player.setMovingDown(false);
				break;
			case KeyEvent.VK_E:
				player.placeBomb();
				break;
			default:
				break;

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setMovingLeft(true);
				break;
			case KeyEvent.VK_W:
				player.setMovingUp(true);
				break;
			case KeyEvent.VK_D:
				player.setMovingRight(true);
				break;
			case KeyEvent.VK_S:
				player.setMovingDown(true);
				break;
			case KeyEvent.VK_ESCAPE:
				Game.switchState();
				break;
			default:
				break;
		}
	}
}