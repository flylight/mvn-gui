package org.ar.mvn.gui.state;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.entity.Settings;
import org.ar.mvn.gui.state.dao.HibernateManager;
import org.ar.mvn.gui.state.dao.IDataBaseStateManager;
import org.ar.mvn.gui.utils.LocaleUtil;
import org.ar.mvn.gui.utils.VerificationUtil;

public class ApplicationStateManager {

  private static ApplicationStateManager instance;
  private IDataBaseStateManager dataBaseManager = new HibernateManager();
  private Settings settings = new Settings();

  private List<Project> projectsList = new ArrayList<Project>();

  private ApplicationStateManager() {}

  public static ApplicationStateManager INSTANCE() {
    if (instance == null) {
      synchronized (ApplicationStateManager.class) {
        if (instance == null) {
          instance = new ApplicationStateManager();
        }
      }
    }
    return instance;
  }

  public void addToProjectsList(Project p) {
    try {
      dataBaseManager.saveNewProject(p);
      projectsList.add(p);
    } catch (Exception e) {
      // TODO add logger
      e.printStackTrace();
    }
  }

  public void removeFromProjectList(Project p) {
    try {
      dataBaseManager.deleteProject(p.getId());
      projectsList.remove(p);
    } catch (Exception e) {
      // TODO logger
      e.printStackTrace();
    }
  }

  public void loadProjectList() {
    try {
      projectsList.addAll(dataBaseManager.loadProjects());
    } catch (Exception e) {
      // TODO add logging and message box
      e.printStackTrace();
    }
  }

  public List<Project> getProjectsList() {
    return projectsList;
  }

  public void checkProjectsStatuses() {
    for (Project p : projectsList) {
      VerificationUtil.checkProjectStatus(p);
    }
  }

  public void loadSettings() {
    try {
      settings = dataBaseManager.loadSettings();
      if(settings == null) createDummySettings();
    } catch (Exception e) {
      // TODO add logger
      e.printStackTrace();
    }
  }

  public void createDummySettings() throws SQLException {
    settings = new Settings();
    settings.setLocale("EN");
    settings.setMavenHome(LocaleUtil.getWord("SELECT_MAVEN_PROJECT_FOLDER"));
    saveSettings(settings);
  }

  public boolean saveSettings(Settings s) {
    try {
      if (dataBaseManager.saveSettings(s) == 1) {
        this.settings = s;
        return true;
      }
    } catch (Exception e) {
      // TODO add logger
      e.printStackTrace();
    }
    return false;
  }

  public Settings getSettings() {
    return settings;
  }
}
