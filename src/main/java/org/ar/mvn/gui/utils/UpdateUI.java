package org.ar.mvn.gui.utils;

import javax.swing.JComponent;
import javax.swing.SwingWorker;

public class UpdateUI {

  private UpdateUI() {}

  public static void update(final JComponent component) {
    SwingWorker<Boolean, Void> uiWorker =
        new SwingWorker<Boolean, Void>() {
          @Override
          protected Boolean doInBackground() {
            component.updateUI();
            return true;
          }
        };
    uiWorker.execute();
  }
}
