package view;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Shows a welcome screen.
 */
public class WelcomeScreen extends JPanel {

  /**
   * Initializes this object to show the relevant information
   * on the welcome screen.
   */
  public WelcomeScreen() {
    JLabel label = new JLabel("Welcome to Kill Doctor Lucky");
    JLabel label1 = new JLabel("Credits: Prinjal Dave, Meet Vora");
    label.setFont(new Font("Aerial", Font.BOLD, 24));
    label1.setFont(new Font("Aerial", Font.BOLD, 20));
    this.setLayout(new BorderLayout());
    label.setHorizontalAlignment(JLabel.CENTER);
    label1.setHorizontalAlignment(JLabel.CENTER);
    this.add(label, BorderLayout.CENTER);
    this.add(label1, BorderLayout.SOUTH);
  }
}
