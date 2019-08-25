package org.ar.mvn.gui.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.ar.mvn.gui.utils.VerificationUtil;

@Entity
public class Setting {

  @Id private int id;
  private String mavenHome = "";
  private String locale;
  private boolean sortProjectsByName;

  public String getMavenHome() {
    return mavenHome;
  }

  public void setMavenHome(String mavenHome) {
    this.mavenHome = VerificationUtil.normalizePath(mavenHome);
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public boolean isSortProjectsByName() {
    return sortProjectsByName;
  }

  public void setSortProjectsByName(boolean sortProjectsByName) {
    this.sortProjectsByName = sortProjectsByName;
  }
}
