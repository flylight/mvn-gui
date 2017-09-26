package org.ar.mvn.gui.utils;

import java.io.File;

import org.ar.mvn.gui.entity.Project;

public final class VerificationUtil {

  private VerificationUtil() {
    throw new UnsupportedOperationException("It is util class");
  }

  public static boolean isThisFolderContainsActiveMavenProject(String path) {
    try {
      File pom = new File(path, "pom.xml");
      return pom.exists();
    } catch (Exception e) {
      // TODO logger
      // bad URL or file doesnt exist
      e.printStackTrace();
    }
    return false;
  }

  public static void checkProjectStatus(Project p) {
    if (isThisFolderContainsActiveMavenProject(p.getPath())) {
      p.setStatus(Project.STATUS_AVAILABLE);
    } else {
      p.setStatus(Project.STATUS_UNAVAILABLE);
    }
  }

  public static String nrmalizePath(String path) {
    if (path.endsWith("/") || path.endsWith("\\")) {
      return path.substring(0, path.length() - 1);
    }
    return path;
  }

  public static boolean checkMavenHome(String path) {
    try {
      File mvn = new File(path + "/bin", CommandExecutorUtil.getMavenExecutable());
      return mvn.exists();
    } catch (Exception e) {
      // TODO logger
      e.printStackTrace();
    }
    return false;
  }

  public static boolean checkFolder(String path) {
    try {
      File folder = new File(path);
      return folder.exists() && folder.isDirectory();
    } catch (Exception e) {
      // TODO logger
      e.printStackTrace();
    }
    return false;
  }
}
