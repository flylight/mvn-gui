package org.ar.mvn.gui.utils;

import org.ar.mvn.gui.state.ApplicationStateManager;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public final class OSUtil {

  private final static String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();

  private OSUtil() {
    throw new UnsupportedOperationException();
  }

  public static boolean isWindows() {
    return OPERATING_SYSTEM.indexOf("win") >= 0;
  }

  public static boolean isMac() {
    return OPERATING_SYSTEM.indexOf("mac") >= 0;
  }

  public static boolean isUnix() {
    return OPERATING_SYSTEM.indexOf("nix") >= 0 || OPERATING_SYSTEM.indexOf("nux") >= 0 || OPERATING_SYSTEM.indexOf("aix") > 0;
  }

  public static boolean isSolaris() {
    return OPERATING_SYSTEM.indexOf("sunos") >= 0;
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
      InputStream inputStream = OSUtil.class.getClassLoader().getResourceAsStream(fileName);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

      String buffer;
      while ((buffer = bufferedReader.readLine()) != null) {
        result.append(buffer);
        result.append("\n");
      }
      bufferedReader.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result.toString();
  }
}
