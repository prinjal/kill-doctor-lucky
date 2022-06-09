package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;


/**
 * The world map is created with this custom layout which will
 * place the spaces in rectangular boxes at specific coordinates
 * on the screen and will make it resizable.
 */
public class CustomResizableLayout implements LayoutManager2 {
  private final HashMap<Component, Rectangle2D> rectMap = new HashMap<>();

  @Override
  public void layoutContainer(Container parent) {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      insets.bottom = parent.getHeight() / 10;
      Dimension dimension = new Dimension(300, 300);
      int parentHeight = parent.getHeight();
      int parentWidth = parent.getWidth();
      if (parentHeight < dimension.getHeight()) {
        parentHeight = (int) dimension.getHeight();
      }
      if (parentWidth < dimension.getWidth()) {
        parentWidth = (int) dimension.getWidth();
      }
      dimension = new Dimension(1000, 1000);
      if (parentHeight > dimension.getHeight()) {
        parentHeight = (int) dimension.getHeight();
      }
      if (parentWidth > dimension.getWidth()) {
        parentWidth = (int) dimension.getWidth();
        insets.left = (int) (dimension.getWidth() / 5);
      }
      int clientHeight = (parentHeight - insets.bottom - insets.top) / 100;
      int clientWidth = (parentWidth - insets.right - insets.left) / 100;


      if (clientWidth > 0 && clientHeight > 0) {
        for (Component component : parent.getComponents()) {
          Rectangle2D rect = rectMap.get(component);
          if (rect != null) {
            component.setBounds(new Rectangle(insets.left + (int) (rect.getX() * clientWidth),
                    insets.top + (int) (rect.getY() * clientHeight),
                    (int) (rect.getWidth() * clientWidth),
                    (int) (rect.getHeight() * clientHeight)));
          }
        }
      }
    }
  }

  @Override
  public void addLayoutComponent(Component comp, Object constraints) {
    rectMap.put(comp, (Rectangle2D) constraints);
  }

  @Override
  public void addLayoutComponent(String name, Component comp) {
  }

  @Override
  public void removeLayoutComponent(Component comp) {
    rectMap.remove(comp);
  }

  @Override
  public Dimension minimumLayoutSize(Container parent) {
    return new Dimension(500, 500);
  }

  @Override
  public Dimension preferredLayoutSize(Container parent) {
    return new Dimension(720, 720);
  }

  @Override
  public Dimension maximumLayoutSize(Container target) {
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  @Override
  public float getLayoutAlignmentX(Container target) {
    return 0;
  }

  @Override
  public float getLayoutAlignmentY(Container target) {
    return 0;
  }

  @Override
  public void invalidateLayout(Container target) {
  }
}
