package org.ar.mvn.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.ar.mvn.gui.entity.Setting;
import org.ar.mvn.gui.state.ApplicationStateManager;
import org.ar.mvn.gui.utils.ContentUtil;
import org.ar.mvn.gui.utils.DialogMessagesUtil;
import org.ar.mvn.gui.utils.OSUtil;
import org.ar.mvn.gui.utils.VerificationUtil;

public class SettingPanel extends JPanel {

  private JTextField mavenHome;
  private JComboBox<String> localeComboBox;
  private String[] locales = {"EN", "TR"};
  private JCheckBox projectSortCheckBox;
  private JComboBox<Integer> consoleTextSizeComboBox;
  private Integer[] consoleTextSizes = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

  public SettingPanel() {
    setBackground(Color.GRAY);
    setLayout(new BorderLayout());
    buildUI();
    loadSetting();
  }

  private void loadSetting() {
    ApplicationStateManager applicationStateManager = ApplicationStateManager.INSTANCE();
    applicationStateManager.loadSetting();
    mavenHome.setText(applicationStateManager.getSetting().getMavenHome());
    localeComboBox.setSelectedItem(applicationStateManager.getSetting().getLocale());
    projectSortCheckBox.setSelected(applicationStateManager.getSetting().isSortProjectsByName());
    consoleTextSizeComboBox
        .setSelectedItem(applicationStateManager.getSetting().getConsoleTextSize());
  }

  private void buildUI() {
    JPanel settingPanel = new JPanel();
    settingPanel.setBackground(Color.GRAY);
    settingPanel.setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 10;
    c.ipadx = 5;

    c.gridy = 0;
    c.gridx = 0;

    settingPanel.add(new JLabel(ContentUtil.getWord("SELECT_MAVEN_HOME_FOLDER")), c);

    c.gridx = 1;

    mavenHome = new JTextField();
    mavenHome.setPreferredSize(new Dimension(500, 20));
    settingPanel.add(mavenHome, c);

    c.gridx = 2;

    JButton selectMavenFolderBtn = new JButton(ContentUtil.getWord("EXTRA"));
    selectMavenFolderBtn.setPreferredSize(new Dimension(50, 20));
    selectMavenFolderBtn.addActionListener(
        e -> {
          String path =
              OSUtil.showPathChooser(
                  ContentUtil.getWord("SELECT_MAVEN_HOME_FOLDER"), SettingPanel.this);
          if (!path.isEmpty()) {
            mavenHome.setText(path);
            mavenHome.requestFocus();
          }
        });
    settingPanel.add(selectMavenFolderBtn, c);

    c.gridy = 1;
    c.gridx = 0;
    settingPanel.add(new JLabel(ContentUtil.getWord("SELECT_LANGUAGE")), c);

    c.gridx = 1;
    localeComboBox = new JComboBox<>(locales);
    settingPanel.add(localeComboBox, c);

    c.gridy = 2;
    c.gridx = 0;
    settingPanel.add(new JLabel(ContentUtil.getWord("SORT_PROJECTS_BY_NAME")), c);

    c.gridx = 1;
    projectSortCheckBox = new JCheckBox();
    settingPanel.add(projectSortCheckBox, c);

    c.gridy = 3;
    c.gridx = 0;
    settingPanel.add(new JLabel(ContentUtil.getWord("CONSOLE_TEXT_SIZE_LBL")), c);

    c.gridx = 1;
    consoleTextSizeComboBox = new JComboBox<>(consoleTextSizes);
    settingPanel.add(consoleTextSizeComboBox, c);

    add(getControlPanel(), BorderLayout.CENTER);
    add(settingPanel, BorderLayout.NORTH);
  }

  private Component getControlPanel() {
    JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));
    control.setBackground(Color.GRAY);

    JButton save = new JButton(ContentUtil.getWord("SAVE"));
    save.addActionListener(
        e -> {
          if (checkMavenHomeFolder()) {
            Setting setting = new Setting();
            setting.setMavenHome(mavenHome.getText());
            setting.setLocale((String) localeComboBox.getSelectedItem());
            setting.setSortProjectsByName(projectSortCheckBox.isSelected());
            setting.setConsoleTextSize((Integer) consoleTextSizeComboBox.getSelectedItem());

            if (ApplicationStateManager.INSTANCE().saveSetting(setting)) {
              DialogMessagesUtil.showInformationMessage(
                  SettingPanel.this, ContentUtil.getWord("SETTING_SAVED"));
            } else {
              DialogMessagesUtil.showErrorMessage(
                  SettingPanel.this, ContentUtil.getWord("ERROR_HAPPEN_WHEN_SAVE_SETTING"));
            }
          }
        });
    control.add(save);

    return control;
  }

  private boolean checkMavenHomeFolder() {
    if (mavenHome != null && !mavenHome.getText().isEmpty()) {
      if (VerificationUtil.checkMavenHome(mavenHome.getText())) {
        return true;
      } else {
        DialogMessagesUtil.showErrorMessage(
            this, ContentUtil.getWord("THIS_FOLDER_DO_NOT_CONTAINS_MAVEN"));
      }
    } else {
      DialogMessagesUtil.showErrorMessage(
          this, ContentUtil.getWord("MAVEN_HOME_FOLDER_CAN_NOT_BE_EMPTY"));
    }
    return false;
  }
}
