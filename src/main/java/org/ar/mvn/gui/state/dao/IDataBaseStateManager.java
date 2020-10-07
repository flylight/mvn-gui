package org.ar.mvn.gui.state.dao;

import java.util.List;
import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.entity.Setting;

public interface IDataBaseStateManager {

  Setting loadSetting();

  List<Project> loadProjects();

  int saveSetting(Setting s);

  int saveProject(Project p);

  int deleteProject(int id);
}
