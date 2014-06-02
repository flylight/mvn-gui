package org.ar.mvn.gui.state.dao;

import java.sql.SQLException;
import java.util.List;

import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.entity.Settings;

public interface IDataBaseStateManager {

  int deleteProject(int id) throws SQLException;

  List<Project> loadProjects() throws SQLException;

  int saveNewProject(Project p) throws SQLException;

  int saveSettings(Settings s) throws SQLException;

  Settings loadSettings() throws SQLException;

}
