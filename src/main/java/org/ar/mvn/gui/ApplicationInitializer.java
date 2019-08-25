package org.ar.mvn.gui;

import javax.swing.SwingUtilities;

public class ApplicationInitializer {

  public static final GeneralFrame GFRAME = new GeneralFrame();

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> GFRAME.setVisible(true));
  }
}
