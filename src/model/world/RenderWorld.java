package model.world;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Renders world and saves its PNG in disk.
 */
public class RenderWorld {

  private final int scaleWorld;
  private final int scaleSpace;

  /**
   * RenderWorld initializes scale to render
   * the image dynamically.
   */
  public RenderWorld() {
    this.scaleWorld = 50;
    this.scaleSpace = scaleWorld - 10;
  }

  /**
   * Creates world and renders it in a JPEG file.
   *
   * @param facadeController input of facade controller to access all the objects.
   */
  public void createWorld(FacadeModelCollection facadeController) {
    if (facadeController == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    final BufferedImage bufferedImage;
    final Graphics2D g2d;
    final int imageWidth;
    final int imageHeight;
    imageWidth = facadeController.getWorldSize().get(1) * scaleWorld;
    imageHeight = facadeController.getWorldSize().get(0) * scaleWorld;
    bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    g2d = bufferedImage.createGraphics();
    Map<String, List<String>> worldMap;
    worldMap = facadeController.getSpaceCoordinates();
    for (Map.Entry<String, List<String>> entry : worldMap.entrySet()) {
      int upperLeftRow = Integer.parseInt(
              String.valueOf(entry.getValue().get(0))) * scaleSpace;
      int upperLeftCol = Integer.parseInt(
              String.valueOf(entry.getValue().get(1))) * scaleSpace;
      int lowerRightRow = (Integer.parseInt(
              String.valueOf(entry.getValue().get(2))) + 1) * scaleSpace;
      int lowerRightCol = (Integer.parseInt(
              String.valueOf(entry.getValue().get(3))) + 1) * scaleSpace;
      int width = lowerRightCol - upperLeftCol;
      int height = lowerRightRow - upperLeftRow;
      /*g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
              RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      g2d.setBackground(Color.WHITE);*/
      g2d.drawRect(upperLeftCol + scaleWorld, upperLeftRow + (scaleWorld / 10), width, height);
      g2d.setFont(new Font("Arial", 1, scaleSpace * 2 / 4));
      List spaceName = new ArrayList();
      spaceName = List.of(entry.getKey().split(" "));
      int upLeftCol = upperLeftCol + 70;
      int upRightCol = upperLeftRow + 50;
      for (int x = 0; x < spaceName.size(); x++) {
        g2d.drawString(String.valueOf(spaceName.get(x)), upLeftCol, upRightCol);
        upRightCol = upRightCol + scaleSpace * 2 / 3;
      }
    }
    File f = new File("World.png");
    try {
      ImageIO.write(bufferedImage, "PNG", f);
    } catch (IOException e) {
      throw new IllegalStateException("Input Error");
    }
  }
}
