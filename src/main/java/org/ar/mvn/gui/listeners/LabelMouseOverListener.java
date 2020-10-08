package org.ar.mvn.gui.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class LabelMouseOverListener implements MouseListener {

  private JLabel component;

  public LabelMouseOverListener(JLabel label) {
    this.component = label;
  }

  @Override
  public void mouseClicked(MouseEvent event) {}

  @Override
  public void mousePressed(MouseEvent event) {}

  @Override
  public void mouseReleased(MouseEvent event) {}

  @Override
  public void mouseEntered(MouseEvent event) {
    component.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
  }

  @Override
  public void mouseExited(MouseEvent event) {
    component.setBorder(BorderFactory.createEmptyBorder());
  }
}
