package view;

import controller.GameControllerInterface;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Adds a panel to create human and computer player
 * and start the game.
 */
public class AddPlayerPane extends JPanel {

  /**
   * Constructor method creates buttons for adding human and computer player
   * along with a button for starting a game.
   * @param gameController controller class which creates players and starts the game.
   */
  public AddPlayerPane(GameControllerInterface gameController) {
    if (gameController == null) {
      throw new IllegalArgumentException("GameControllerInterface is null");
    }
    JButton j = new JButton("Add Computer Player");
    JButton j1 = new JButton("Add Human Player");
    JButton j2 = new JButton("Start Game");
    this.setLayout(new FlowLayout(FlowLayout.CENTER));
    this.add(j);
    this.add(j1);
    this.add(j2);
    j.addActionListener(e -> gameController.processMoves(7));
    j1.addActionListener(e -> gameController.processMoves(6));
    j2.addActionListener(e -> gameController.playGame());
  }
}
