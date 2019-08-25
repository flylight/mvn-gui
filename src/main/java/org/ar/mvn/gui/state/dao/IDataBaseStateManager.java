package org.ar.mvn.gui.state.dao;

import java.sql.SQLException;
import java.util.List;
import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.entity.Setting;

public interface IDataBaseStateManager {

  Setting loadSetting() throws SQLException;

  List<Project> loadProjects() throws SQLException;

  int saveSetting(Setting s) throws SQLException;

  int saveProject(Project p) throws SQLException;

  int deleteProject(int id) throws SQLException;
}
