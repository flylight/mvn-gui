package org.ar.mvn.gui.entity;

import org.ar.mvn.gui.utils.VerificationUtil;

public class Settings {

  private String mavenHome = "";

  public String getMavenHome() {
    return mavenHome;
  }

  public void setMavenHome(String mavenHome) {
    this.mavenHome = VerificationUtil.nrmalizePath(mavenHome);
  }

}
