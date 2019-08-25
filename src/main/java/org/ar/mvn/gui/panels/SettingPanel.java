package org.ar.mvn.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
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

  public SettingPanel() {
    setBackground(Color.GRAY);
    setLayout(new BorderLayout());
    buildUI();
    loadSetting();
  }

  private void loadSetting() {
    ApplicationStateManager.INSTANCE().loadSetting();
    mavenHome.setText(ApplicationStateManager.INSTANCE().getSetting().getMavenHome());
    localeComboBox.setSelectedItem(ApplicationStateManager.INSTANCE().getSetting().getLocale());
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

    add(getControlPanel(), BorderLayout.CENTER);
    add(settingPanel, BorderLayout.NORTH);
  }

  private Component getControlPanel() {
    JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));
    control.setBackground(Color.GRAY);

    localeComboBox = new JComboBox<>(locales);
    JButton save = new JButton(ContentUtil.getWord("SAVE"));
    save.addActionListener(
        e -> {
          if (checkMavenHomeFolder()) {
            Setting setting = new Setting();
            setting.setMavenHome(mavenHome.getText());
            setting.setLocale((String) localeComboBox.getSelectedItem());

            if (ApplicationStateManager.INSTANCE().saveSetting(setting)) {
              DialogMessagesUtil.showInformationMessage(
                  SettingPanel.this, ContentUtil.getWord("SETTING_SAVED"));
            } else {
              DialogMessagesUtil.showErrorMessage(
                  SettingPanel.this, ContentUtil.getWord("ERROR_HAPPEN_WHEN_SAVE_SETTING"));
            }
          }
        });
    control.add(localeComboBox);
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
