package org.ar.mvn.gui.entity;

import org.ar.mvn.gui.utils.VerificationUtil;

public class Project {
  public static final int STATUS_AVAILABLE = 1;
  public static final int STATUS_UNAVAILABLE = 0;
  public static final int STATUS_BUSY = 2;
  public static final int STATUS_ERROR = 3;

  private int id;
  private String name;
  private String path;



  // temporary data
  private StringBuilder consoleLog = new StringBuilder();
  private int status = 0;
  private boolean isSelected;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = VerificationUtil.nrmalizePath(path);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((path == null) ? 0 : path.hashCode());
    result = prime * result + status;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Project other = (Project) obj;
    if (id != other.id) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    if (path == null) {
      if (other.path != null) return false;
    } else if (!path.equals(other.path)) return false;
    if (status != other.status) return false;
    return true;
  }

  public StringBuilder getConsoleLog() {
    return consoleLog;
  }

  public void setConsoleLog(StringBuilder consoleLog) {
    this.consoleLog = consoleLog;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

}
