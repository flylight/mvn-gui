package org.ar.mvn.gui.constants;

import java.awt.Dimension;
import javax.swing.ImageIcon;

public interface Sources {

  String INTRO = "Powered by Rymar Andriy (2019)";
  Dimension TOP_PANEL_SIZE = new Dimension(0, 60);
  Dimension BOTTOM_PANEL_SIZE = new Dimension(0, 30);
  String SYS_FILE_SEPARATOR = System.getProperty("file.separator");

  ImageIcon UNAVAILABLE_PROJECT_IMAGE =
      new ImageIcon(
          Thread.currentThread().getContextClassLoader().getResource("img/unavailable.png"));
  ImageIcon AVAILABLE_PROJECT_IMAGE =
      new ImageIcon(
          Thread.currentThread().getContextClassLoader().getResource("img/available.png"));
  ImageIcon BUSY_PROJECT_IMAGE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/busy.png"));
  ImageIcon DELETE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/delete.png"));
  ImageIcon ADD_BIG_IMANE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/add-big.png"));
  ImageIcon ADD_SMALL_IMAGE =
      new ImageIcon(
          Thread.currentThread().getContextClassLoader().getResource("img/add-small.png"));
  ImageIcon APPLICATION_ICON =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/icon.png"));
  ImageIcon ERROR_PROJECT_IMAGE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/error.png"));
  ImageIcon FOLDER_IMAGE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/folder.png"));
  ImageIcon EDIT_IMAGE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/edit.png"));

  ImageIcon FILES_IMAGE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/files.png"));
  ImageIcon NEW_PROJE_IMAGE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/new.png"));
  ImageIcon SETTING_IMAGE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/setting.png"));
  ImageIcon HELP_IMAGE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/help.png"));
  ImageIcon CLEAR_CMD_IMAGE =
      new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("img/clear.png"));
}
