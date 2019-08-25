package org.ar.mvn.gui;

import javax.swing.SwingUtilities;

public class ApplciationInitializer {

  public static final GeneralFrame GFRAME = new GeneralFrame();

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            GFRAME.setVisible(true);
          }
        });
  }
}
