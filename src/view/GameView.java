package view;

import controller.GameControllerInterface;
import java.awt.Dimension;
import java.io.File;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import model.world.ReadOnlyGameViewInterface;


/**
 * GameView displays the state of the game refreshes the
 * board with the latest updated state and displays error
 * if any.
 */
public class GameView extends JFrame implements GameViewInterface {

  private final ReadOnlyGameViewInterface readOnlyModel;
  private final WelcomeScreen welcomeScreen;
  private DrawWorld drawWorld;
  private AddPlayerPane addPlayerPane;
  private GameControllerInterface gameController;

  /**
   * GameView constructor method initializes the object while setting
   * all the class variables.
   * @param readOnlyModel model which shares the updated state of game.
   */
  public GameView(ReadOnlyGameViewInterface readOnlyModel) {
    super("Kill Dr. Lucky");
    if (readOnlyModel == null) {
      throw new IllegalArgumentException("ReadOnlyGameView model is null");
    }
    this.readOnlyModel = readOnlyModel;
    this.welcomeScreen = new WelcomeScreen();
    this.drawWorld = null;
    this.addPlayerPane = null;
    this.gameController = null;
    this.add(welcomeScreen);
    addMenuBar();

    this.setPreferredSize(new Dimension(720, 720));
    this.pack();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setMinimumSize(new Dimension(300, 300));
    this.setResizable(true);
    this.setVisible(true);
  }

  @Override
  public void makeVisible() {
    this.addPlayerPane.setVisible(false);
    this.add(drawWorld);
    JLabel textField = new JLabel();
    JPanel infoPanel = new JPanel();
    infoPanel.add(textField);
    this.add(infoPanel);
    drawWorld.setInfoDisplay(textField);
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setSize(100, 70);
    splitPane.setOneTouchExpandable(false);
    splitPane.setDividerSize(0);
    splitPane.setDividerLocation(splitPane.getHeight());
    splitPane.setLeftComponent(infoPanel);
    splitPane.setRightComponent(drawWorld);
    this.add(splitPane);
    this.drawWorld.grabFocus();
    this.setPreferredSize(new Dimension(800, 800));
  }

  @Override
  public void refresh() {
    drawWorld.repaint();
  }

  @Override
  public void setFeatures(GameControllerInterface controller) {
    if (controller == null) {
      throw new IllegalArgumentException("GameControllerInterface is null");
    }
    this.gameController = controller;
    this.drawWorld = new DrawWorld(readOnlyModel, new MouseListener(controller));
    this.drawWorld.addKeyListener(new KeyboardListener(this.drawWorld, controller));
  }

  @Override
  public String getActionInput(String title, String msg, List<String> data) {
    if (title == null || title.isEmpty()) {
      throw new IllegalArgumentException("title is invalid");
    }
    if (msg == null || msg.isEmpty()) {
      throw new IllegalArgumentException("msg is invalid");
    }
    if (data == null || data.isEmpty()) {
      throw new IllegalArgumentException("data is invalid");
    }
    JComboBox<String> list = new JComboBox(data.toArray());
    //using general object, as this list contains different type of variables to take the input
    //from the user.
    Object[] fields = {msg, list};
    JOptionPane.showConfirmDialog(this, fields,
            title, JOptionPane.OK_CANCEL_OPTION);
    return list.getSelectedItem().toString();
  }

  @Override
  public String getActionInput(String data) {
    if (data == null || data.isEmpty()) {
      throw new IllegalArgumentException("data is null");
    }
    return JOptionPane.showInputDialog(this, data);
  }

  @Override
  public PlayerDto getPlayerDto() {
    JTextField name = new JTextField();
    JComboBox<Integer> carryLimit = new JComboBox<>(new Integer[]{4, 5, 6, 7, 8});
    JComboBox<String> roomList =
            new JComboBox<>(this.readOnlyModel.getSpaceNames().toArray(new String[0]));
    //using general object, as this list contains different type of variables to take the input
    //from the user.
    Object[] fields = {"Enter your name", name, "Select a room", roomList,
      "Select a carry limit", carryLimit
    };
    JOptionPane.showConfirmDialog(this, fields,
            "Add Human Player", JOptionPane.OK_CANCEL_OPTION);
    return new PlayerDto(name.getText(), (String) roomList.getSelectedItem(),
            Integer.parseInt(String.valueOf(carryLimit.getSelectedItem())));
  }

  @Override
  public void displayError(String errorMessage) {
    if (errorMessage == null || errorMessage.isEmpty()) {
      throw new IllegalArgumentException("errorMessage is invalid");
    }
    JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showMsg(String message) {
    if (message == null || message.isEmpty()) {
      throw new IllegalArgumentException("message is invalid");
    }
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Method to setup the menu bar in the JFrame.
   */
  private void addMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu1 = new JMenu("Start Game");
    JMenu menu2 = new JMenu("Option");
    JMenuItem menuItem1 = new JMenuItem("Start Game with current specification");
    JMenuItem menuItem2 = new JMenuItem("Start Game with new specification");
    JMenuItem menuItem3 = new JMenuItem("Quit");
    JMenuItem menuItem4 = new JMenuItem("Help");
    this.setJMenuBar(menuBar);
    menuBar.add(menu1);
    menuBar.add(menu2);
    menu1.add(menuItem1);
    menu1.add(menuItem2);
    menu2.add(menuItem4);
    menu2.add(menuItem3);
    menuItem1.addActionListener(e -> {
      this.welcomeScreen.setVisible(false);
      this.addPlayerPane = new AddPlayerPane(this.gameController);
      this.add(addPlayerPane);
      menu1.setVisible(false);
    });
    menuItem2.addActionListener(e -> {
      this.welcomeScreen.setVisible(false);
      JFileChooser fc = new JFileChooser();
      int returnVal = fc.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fc.getSelectedFile();
        gameController.setModel(file.getPath());
        this.addPlayerPane = new AddPlayerPane(this.gameController);
        this.add(addPlayerPane);
      }
      menu1.setVisible(false);
    });
    menuItem3.addActionListener(e -> {
      System.exit(0);
    });
    menuItem4.addActionListener(e -> {
      String displayHelp =
              new StringBuilder("<html>")
                      .append("<ul>")
                      .append("<li>The current player will be shown in blue color<br>")
                      .append("<li>Press h to attack the target<br>")
                      .append("<li>Press p to pick an item<br>")
                      .append("<li>Press m to move the pet<br>")
                      .append("<li>Press l to look around<br>")
                      .append("<li>Click on any valid neighbor space to move the player<br>")
                      .append("<li>Click on player or target to see relevant information<br>")
                      .append("</ul>")
                      .append("</html>").toString();
      JOptionPane.showMessageDialog(getRootPane(), displayHelp, "Game rules",
              JOptionPane.INFORMATION_MESSAGE);
    });
  }
}
