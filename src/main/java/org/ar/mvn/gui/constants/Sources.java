package org.ar.mvn.gui.constants;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;

public interface Sources {
  Color DARK_GREY_COLOR = Color.DARK_GRAY;
  Color GREY_COLOR = Color.GRAY;
  Dimension TOP_PANEL_SIZE = new Dimension(0, 60);
  Dimension BOTTOM_PANEL_SIZE = new Dimension(0, 30);
  String INTRO = "Powered by Rymar Andriy (2013)";

  ImageIcon UNAVAILABLE_PROJECT_IMAGE = new ImageIcon(Thread.currentThread()
      .getContextClassLoader().getResource("unavailable.png"));
  ImageIcon AVAILABLE_PROJECT_IMAGE = new ImageIcon(Thread.currentThread().getContextClassLoader()
      .getResource("available.png"));
  ImageIcon BUSY_PROJECT_IMAGE = new ImageIcon(Thread.currentThread().getContextClassLoader()
      .getResource("busy.png"));
  ImageIcon DELETE = new ImageIcon(Thread.currentThread().getContextClassLoader()
      .getResource("delete.png"));
  ImageIcon ADD_BIG_IMANE = new ImageIcon(Thread.currentThread().getContextClassLoader()
      .getResource("add-big.png"));
  ImageIcon ADD_SMALL_IMAGE = new ImageIcon(Thread.currentThread().getContextClassLoader()
      .getResource("add-small.png"));
  ImageIcon APPLICATION_ICON = new ImageIcon(Thread.currentThread().getContextClassLoader()
      .getResource("icon.png"));
  ImageIcon ERROR_PROJECT_IMAGE = new ImageIcon(Thread.currentThread().getContextClassLoader()
      .getResource("error.png"));
  ImageIcon FOLDER_IMAGE = new ImageIcon(Thread.currentThread().getContextClassLoader()
      .getResource("folder.png"));
}
