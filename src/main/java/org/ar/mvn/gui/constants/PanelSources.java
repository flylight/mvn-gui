package org.ar.mvn.gui.constants;

import org.ar.mvn.gui.panels.AddNewProjectWindow;
import org.ar.mvn.gui.panels.CreateProjectPanel;
import org.ar.mvn.gui.panels.DesktopPanel;
import org.ar.mvn.gui.panels.HelpPanel;
import org.ar.mvn.gui.panels.ProjectPanel;
import org.ar.mvn.gui.panels.SettingPanel;

public interface PanelSources {
  DesktopPanel DESKTOP = new DesktopPanel();
  AddNewProjectWindow ADD_PROJECT = new AddNewProjectWindow();
  ProjectPanel DISPLAY_PROJECT = new ProjectPanel();
  CreateProjectPanel CREATE_PROJECT = new CreateProjectPanel();
  SettingPanel SETTING = new SettingPanel();
  HelpPanel HELP = new HelpPanel();
}
