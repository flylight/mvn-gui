package org.ar.mvn.gui.utils;

import java.awt.Component;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import org.ar.mvn.gui.state.ApplicationStateManager;

public final class OSUtil {

  private OSUtil() {
    throw new UnsupportedOperationException();
  }

  public static void openInOSFileManager(String path) {
    try {
      Desktop.getDesktop().open(new File(path));
    } catch (IOException e) {
      // TODO add logger
      e.printStackTrace();
    }
  }

  public static String showPathChooser(String title, Component parent) {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle(title);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);
    if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
      return VerificationUtil.nrmalizePath(chooser.getSelectedFile().getAbsolutePath());
    }
    return "";
  }

  public static String readHelpFile() {

    StringBuilder result = new StringBuilder();
    try {
      String fileName = "help";
      if (ApplicationStateManager.INSTANCE().getSettings().getLocale().equals("EN"))
        fileName += "_en";
      else if (ApplicationStateManager.INSTANCE().getSettings().getLocale().equals("TR"))
        fileName += "_tr";
      BufferedReader reader =
          new BufferedReader(new FileReader(new File(Thread.currentThread().getContextClassLoader()
              .getResource(fileName).toURI())));

      String buffer;
      while ((buffer = reader.readLine()) != null) {
        result.append(buffer);
        result.append("\n");
      }
      reader.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result.toString();
  }
}
