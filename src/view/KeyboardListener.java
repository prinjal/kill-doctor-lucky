package view;

import controller.GameControllerInterface;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Keyboard listener handles key presses and key clicks
 * while the game is running.
 */
public class KeyboardListener extends KeyAdapter {
  private boolean flag;
  private final DrawWorld drawWorld;
  private final GameControllerInterface controller;

  /**
   * Initializes the KeyBoard object to listen to keyboard press.
   *
   * @param drawWorld  displays the world map to the player.
   * @param controller controller which drives the game.
   */
  public KeyboardListener(DrawWorld drawWorld, GameControllerInterface controller) {
    if (drawWorld == null || controller == null) {
      throw new IllegalArgumentException("DrawWorld and GameController object should be valid");
    }
    this.drawWorld = drawWorld;
    this.flag = false;
    this.controller = controller;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == 'p') {
      controller.processMoves(2);
      drawWorld.repaint();
    } else if (e.getKeyChar() == 'h') {
      controller.processMoves(5);
      drawWorld.repaint();
    } else if (e.getKeyChar() == 'm') {
      controller.processMoves(1);
      drawWorld.repaint();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_L && !flag) {
      if (controller.isGameOver()) {
        return;
      }
      drawWorld.lookAround();
      flag = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_L) {
      flag = false;
      drawWorld.clearLookAround();
      controller.processMoves(4);
    }
  }
}
