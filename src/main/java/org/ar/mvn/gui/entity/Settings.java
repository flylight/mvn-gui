package org.ar.mvn.gui.entity;

import java.util.Locale;
import org.ar.mvn.gui.utils.VerificationUtil;

public class Settings {

  private String mavenHome = "";

  private String locale;

  public String getMavenHome() {
    return mavenHome;
  }

  public void setMavenHome(String mavenHome) {
    this.mavenHome = VerificationUtil.nrmalizePath(mavenHome);
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }
}
