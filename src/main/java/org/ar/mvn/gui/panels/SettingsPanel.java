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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.ar.mvn.gui.entity.Settings;
import org.ar.mvn.gui.state.ApplicationStateManager;
import org.ar.mvn.gui.utils.DialogMessagesUtil;
import org.ar.mvn.gui.utils.OSUtil;
import org.ar.mvn.gui.utils.VerificationUtil;

public class SettingsPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  private JTextField mavenHome;

  public SettingsPanel() {
    setBackground(Color.GRAY);
    setLayout(new BorderLayout());
    //
    buildUI();
    //
    loadSettings();
    //
  }

  // TODO refresh settings each hoe this panel

  private void loadSettings() {
    ApplicationStateManager.INSTANCE().loadSettings();
    mavenHome.setText(ApplicationStateManager.INSTANCE().getSettings().getMavenHome());
  }

  private void buildUI() {
    JPanel settings = new JPanel();
    settings.setBackground(Color.GRAY);
    settings.setLayout(new GridBagLayout());
    //
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 10;
    c.ipadx = 5;
    //
    c.gridy = 0;
    c.gridx = 0;
    //
    settings.add(new JLabel("Select maven home folder : "), c);
    //
    c.gridx = 1;
    //
    mavenHome = new JTextField();
    mavenHome.setPreferredSize(new Dimension(500, 20));
    settings.add(mavenHome, c);
    //
    c.gridx = 2;
    //
    JButton selectMavenFolderBtn = new JButton("...");
    selectMavenFolderBtn.setPreferredSize(new Dimension(50, 20));
    selectMavenFolderBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String path = OSUtil.showPathChooser("Select maven home folder", SettingsPanel.this);
        if (!path.isEmpty()) {
          mavenHome.setText(path);
        }
      }
    });
    settings.add(selectMavenFolderBtn, c);
    // //
    // control
    // //
    add(getControlPanel(), BorderLayout.CENTER);
    add(settings, BorderLayout.NORTH);
  }

  private Component getControlPanel() {
    JPanel control = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    control.setBackground(Color.GRAY);

    JButton save = new JButton("Save");
    save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (checkMavenHomeFolder()) {
          Settings s = new Settings();
          s.setMavenHome(mavenHome.getText());
          if (ApplicationStateManager.INSTANCE().saveSettings(s)) {
            DialogMessagesUtil.showInformationMessage(SettingsPanel.this, "Settings saved!");
          } else {
            DialogMessagesUtil.showErrorMessage(SettingsPanel.this,
                "Error happen when save settings!");
          }
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
        DialogMessagesUtil.showErrorMessage(this, "This folder do not contains maven!");
      }
    } else {
      DialogMessagesUtil.showErrorMessage(this, "Maven home folder can not be empty!");
    }
    return false;
  }
}
