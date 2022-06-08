package view;

import controller.GameControllerInterface;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MouseListener listens for mouse events if any
 * and computes relevant actions in the game accordingly.
 */

public class MouseListener extends MouseAdapter {

  private final GameControllerInterface controller;

  /**
   * Initializes mouse listener object to listen to mouse click events.
   * @param controller controller which drives the game.
   */
  public MouseListener(GameControllerInterface controller) {
    if (controller == null) {
      throw new IllegalArgumentException("DrawWorld and GameController object should be valid");
    }
    this.controller = controller;
  }

  /**
   * Mouse listener class handles the mouse clicks and
   * computes relevant logic accordingly.
   *
   * @param e event of the mouse to get the coordinates
   *          of the click.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    super.mouseClicked(e);
    controller.processMoves(3, e.getComponent().getName());
  }
}
