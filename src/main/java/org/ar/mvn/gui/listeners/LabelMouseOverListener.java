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
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {
    component.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
  }

  @Override
  public void mouseExited(MouseEvent e) {
    component.setBorder(BorderFactory.createEmptyBorder());
  }

}
