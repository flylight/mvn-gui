package org.ar.mvn.gui.state;

import java.util.ArrayList;
import java.util.List;
import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.entity.Setting;
import org.ar.mvn.gui.state.dao.HibernateManager;
import org.ar.mvn.gui.state.dao.IDataBaseStateManager;
import org.ar.mvn.gui.utils.ContentUtil;
import org.ar.mvn.gui.utils.VerificationUtil;

public class ApplicationStateManager {

  private static ApplicationStateManager instance;
  private IDataBaseStateManager dataBaseManager = new HibernateManager();
  private Setting setting = new Setting();

  private List<Project> projects = new ArrayList<Project>();

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

  public void addProject(Project project) {
    try {
      dataBaseManager.saveProject(project);
      projects.add(project);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void removeProject(Project project) {
    try {
      dataBaseManager.deleteProject(project.getId());
      projects.remove(project);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void loadProjects() {
    try {
      projects.addAll(dataBaseManager.loadProjects());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void checkProjectsStatuses() {
    for (Project project : projects) {
      VerificationUtil.checkProjectStatus(project);
    }
  }

  public void loadSetting() {
    try {
      setting = dataBaseManager.loadSetting();
      if (setting == null) createDummySetting();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void createDummySetting() {
    setting = new Setting();
    setting.setLocale("EN");
    setting.setMavenHome(ContentUtil.getWord("SELECT_MAVEN_PROJECT_FOLDER"));
    saveSetting(setting);
  }

  public boolean saveSetting(Setting setting) {
    try {
      if (dataBaseManager.saveSetting(setting) == 1) {
        this.setting = setting;
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public Setting getSetting() {
    return setting;
  }
}
