package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import model.world.ReadOnlyGameViewInterface;

/**
 * Draws the world with spaces, players, and target.
 */
public class DrawWorld extends JPanel {
  private final ReadOnlyGameViewInterface readOnlyModel;
  private final MouseListener mouseListener;
  private final HashMap<String, JPanel> jPanelMap;
  private final CustomResizableLayout customResizableLayout;
  private JLabel infoDisplay;

  /**
   * DrawWorld constructor method initializes the object.
   *
   * @param readOnlyModel model that computes the logic.
   * @param mouseListener to listen to mouse events.
   */
  public DrawWorld(ReadOnlyGameViewInterface readOnlyModel, MouseListener mouseListener) {
    this.readOnlyModel = readOnlyModel;
    this.mouseListener = mouseListener;
    this.customResizableLayout = new CustomResizableLayout();
    this.jPanelMap = new HashMap<>();
    this.infoDisplay = null;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.setLayout(customResizableLayout);
    int roomScale = 3;
    int fontSize = roomScale / 2;
    jPanelMap.clear();
    String currentPlayer = readOnlyModel.getCurrentPlayerName();
    this.removeAll();
    infoDisplay.setText(String.format("It's %s's turn", currentPlayer));

    g.setFont(new Font("Aerial", Font.PLAIN, fontSize));
    Map<String, List<String>> spaceCoordinates = readOnlyModel.getSpaceCoordinates();
    for (Map.Entry<String, List<String>> entry : spaceCoordinates.entrySet()) {
      int row = (Integer.parseInt(
              String.valueOf(entry.getValue().get(0))) + 1) * roomScale;
      int col = (Integer.parseInt(
              String.valueOf(entry.getValue().get(1))) + 1) * roomScale;
      int width = (Integer.parseInt(
              String.valueOf(entry.getValue().get(3))) + 1) * roomScale
              - Integer.parseInt(
              String.valueOf(entry.getValue().get(1))) * roomScale;
      int height = (Integer.parseInt(
              String.valueOf(entry.getValue().get(2))) + 1) * roomScale
              - Integer.parseInt(
              String.valueOf(entry.getValue().get(0))) * roomScale;
      JPanel panel = new JPanel();
      this.add(panel, new Rectangle(col, row, width, height));
      JLabel label = new JLabel(entry.getKey());
      label.setFont(new Font("Aerial", Font.PLAIN, 12));
      panel.setLayout(new FlowLayout(0, 5, 0));
      panel.add(label, BorderLayout.WEST);
      panel.setBorder(new LineBorder(Color.black, 1));
      panel.setName(entry.getKey());
      panel.addMouseListener(mouseListener);
      panel.setVisible(true);
      jPanelMap.put(entry.getKey(), panel);
    }

    for (String s : readOnlyModel.getPlayerNames()) {
      JLabel label = new JLabel(String.valueOf(s.charAt(0)));
      if (readOnlyModel.getCurrentPlayerName().equals(s)) {
        label.setForeground(Color.blue);
      }
      label.setFont(new Font("Aerial", Font.PLAIN, 12));
      JPanel playerPanel = jPanelMap.get(readOnlyModel.getPlayerSpaceName(s));
      playerPanel.add(label, BorderLayout.CENTER);
      label.setHorizontalAlignment(JLabel.CENTER);
      label.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          try {
            JOptionPane.showMessageDialog(getRootPane(), readOnlyModel
                    .getPlayerDetails(s));
          } catch (IllegalArgumentException er) {
            JOptionPane.showMessageDialog(getRootPane(), er.getMessage());
          }
        }
      });
    }

    JLabel targetLabel = new JLabel("T");
    targetLabel.setForeground(Color.red);
    targetLabel.setFont(new Font("Aerial", Font.PLAIN, 12));
    targetLabel.setHorizontalAlignment(JLabel.CENTER);
    jPanelMap.get(readOnlyModel.getTargetSpace()).add(targetLabel, BorderLayout.EAST);
    targetLabel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {

        JOptionPane.showMessageDialog(getRootPane(), readOnlyModel
                .getTargetDetails());
      }
    });
    revalidate();
  }

  /**
   * Look around method shows details of neighboring spaces for the
   * current player. The details include items, pet ,and space neighbors.
   */
  public void lookAround() {
    List<String> neighborList = this.readOnlyModel.getCurrentPlayerNeighbors();
    neighborList.add(this.readOnlyModel.getPlayerSpaceName(readOnlyModel.getCurrentPlayerName()));
    if (neighborList.contains(readOnlyModel.getPetSpaceName())
            && !(readOnlyModel.getPetSpaceName().equals(readOnlyModel
            .getPlayerSpaceName(readOnlyModel.getCurrentPlayerName())))) {
      neighborList.remove(readOnlyModel.getPetSpaceName());
    }

    for (String s : neighborList) {
      JPanel panel = jPanelMap.get(s);
      Component[] comp = panel.getComponents();
      for (Component c : comp) {
        c.setVisible(false);
      }
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      int gm = (panel.getWidth() * panel.getHeight())
              / (5 * (panel.getWidth() + panel.getHeight()));
      String data = this.readOnlyModel.getSpaceDetails(s);
      String[] splitData = data.split(",");
      JLabel label;

      label = new JLabel(s);
      label.setFont(new Font("Aerial", Font.PLAIN,
              gm));
      panel.add(label);

      StringBuilder items = new StringBuilder();
      items.append(String.format("%s", splitData[2]));
      label = new JLabel(items.toString());
      label.setFont(new Font("Aerial", Font.PLAIN,
              gm));
      panel.add(label);

      StringBuilder players = new StringBuilder();
      players.append(String.format("%s", splitData[3]));
      label = new JLabel(players.toString());
      label.setFont(new Font("Aerial", Font.PLAIN,
              gm));
      panel.add(label);

      StringBuilder target = new StringBuilder();
      target.append(String.format("%s", splitData[4]));
      label = new JLabel(target.toString());
      label.setFont(new Font("Aerial", Font.PLAIN,
              gm));
      panel.add(label);
      panel.setVisible(true);
    }
    this.revalidate();

  }

  /**
   * Clears the details of neighboring spaces upon
   * release of a keyboard key.
   */
  public void clearLookAround() {
    for (Map.Entry<String, JPanel> entry : jPanelMap.entrySet()) {
      entry.getValue().removeAll();
    }
    this.repaint();
    this.revalidate();
  }

  /**
   * Updates displaying the player turn after every move taken by a player.
   *
   * @param label field on which player turn needs to be updated.
   */
  public void setInfoDisplay(JLabel label) {
    this.infoDisplay = label;
  }
}

