package org.ar.mvn.gui.utils;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public final class DialogMessagesUtil {

  private DialogMessagesUtil() {
    throw new UnsupportedOperationException("This is util class");
  }

  public static void showInformationMessage(Component parent, String text) {
    JOptionPane pane = new JOptionPane(text, JOptionPane.INFORMATION_MESSAGE);
    JDialog dialog = pane.createDialog(parent, "Information");
    dialog.setVisible(true);
  }

  public static void showErrorMessage(Component parent, String text) {
    JOptionPane pane = new JOptionPane(text, JOptionPane.ERROR_MESSAGE);
    JDialog dialog = pane.createDialog(parent, "Error");
    dialog.setVisible(true);
  }

  public static void showWarningMessage(Component parent, String text) {
    JOptionPane pane = new JOptionPane(text, JOptionPane.WARNING_MESSAGE);
    JDialog dialog = pane.createDialog(parent, "Warning");
    dialog.setVisible(true);
  }

  public static boolean confirmationWindow(Component parent, String text) {
    // final JOptionPane optionPane =
    // new JOptionPane("The only way to close this dialog is by\n"
    // + "pressing one of the following buttons.\n" + "Do you understand?",
    // JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
    int n =
        JOptionPane.showConfirmDialog(parent, text, "Confirm your action",
            JOptionPane.YES_NO_OPTION);
    return n == 0;
  }
}
