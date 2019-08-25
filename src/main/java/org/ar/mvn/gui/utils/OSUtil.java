package org.ar.mvn.gui.utils;

import java.awt.Component;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import org.ar.mvn.gui.state.ApplicationStateManager;

public final class OSUtil {

  private static final String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();

  private OSUtil() {
    throw new UnsupportedOperationException();
  }

  public static boolean isWindows() {
    return OPERATING_SYSTEM.indexOf("win") >= 0;
  }

  public static boolean isMac() {
    return (OPERATING_SYSTEM.indexOf("mac") >= 0);
  }

  public static void openInOSFileManager(String path) {
    try {
      Desktop.getDesktop().open(new File(path));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String showPathChooser(String title, Component parent) {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle(title);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);
    if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
      return VerificationUtil.normalizePath(chooser.getSelectedFile().getAbsolutePath());
    }
    return "";
  }

  public static String readHelpFile() {

    StringBuilder result = new StringBuilder();
    try {
      String fileName = "help";
      if (ApplicationStateManager.INSTANCE().getSetting().getLocale().equals("EN"))
        fileName += "_en";
      else if (ApplicationStateManager.INSTANCE().getSetting().getLocale().equals("TR"))
        fileName += "_tr";
      InputStream inputStream = OSUtil.class.getClassLoader().getResourceAsStream(fileName);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

      String buffer;
      while ((buffer = bufferedReader.readLine()) != null) {
        result.append(buffer);
        result.append("\n");
      }
      bufferedReader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result.toString();
  }
}
