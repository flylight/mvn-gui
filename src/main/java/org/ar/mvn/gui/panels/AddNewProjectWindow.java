package org.ar.mvn.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.ar.mvn.gui.constants.PanelSources;
import org.ar.mvn.gui.constants.Sources;
import org.ar.mvn.gui.utils.ContentUtil;
import org.ar.mvn.gui.utils.DialogMessagesUtil;
import org.ar.mvn.gui.utils.OSUtil;
import org.ar.mvn.gui.utils.VerificationUtil;

public class AddNewProjectWindow extends JDialog {
  private static final long serialVersionUID = 1L;

  private JTextField projectPath;
  private JTextField projectName;

  public AddNewProjectWindow() {
    setTitle(ContentUtil.getWord("ADD_NEW_PROJECT"));
    setSize(400, 170);
    setLocationRelativeTo(null);
    setResizable(false);
    setAlwaysOnTop(true);
    setLayout(new BorderLayout());
    initializeUI();
  }

  private void initializeUI() {
    JPanel infoPanel = generateProjectInfoPanel();
    JPanel actionPanel = generateActionPanel();
    JPanel headerPanel = generateHeaderPanel();

    add(infoPanel, BorderLayout.CENTER);
    add(actionPanel, BorderLayout.SOUTH);
    add(headerPanel, BorderLayout.NORTH);
  }

  private JPanel generateHeaderPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panel.setBackground(Color.DARK_GRAY);
    panel.setPreferredSize(new Dimension(0, 15));
    return panel;
  }

  private JPanel generateActionPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panel.setBackground(Color.DARK_GRAY);
    panel.setPreferredSize(new Dimension(0, 35));

    JButton add = new JButton(ContentUtil.getWord("ADD"), Sources.ADD_SMALL_IMAGE);
    add.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (projectName.getText().isEmpty() || projectPath.getText().isEmpty()) {
              DialogMessagesUtil.showErrorMessage(
                  AddNewProjectWindow.this,
                  ContentUtil.getWord("PROJECT_NAME_AND_PATH_CAN_NOT_BE_EMPTY"));
            } else {
              if (VerificationUtil.isThisFolderContainsActiveMavenProject(projectPath.getText())) {
                PanelSources.DESKTOP.addNewProject(projectName.getText(), projectPath.getText());
                close();
              } else {
                DialogMessagesUtil.showWarningMessage(
                    AddNewProjectWindow.this,
                    projectPath.getText() + ContentUtil.getWord("NOT_CONTAINS_MAVEN_PROJECT"));
              }
            }
          }
        });
    panel.add(add);
    //
    return panel;
  }

  private void close() {
    this.setVisible(false);
  }

  private JPanel generateProjectInfoPanel() {
    JPanel panel = new JPanel();
    panel.setBackground(Color.GRAY);
    panel.setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 10;
    c.ipadx = 5;

    // LABEL
    c.gridx = 0;
    c.gridy = 0;
    JLabel nameLabel = new JLabel(ContentUtil.getWord("PROJECT_NAME"));
    nameLabel.setPreferredSize(new Dimension(100, 15));
    panel.add(nameLabel, c);
    // FIELD
    c.gridx = 1;
    projectName = new JTextField();
    projectName.setPreferredSize(new Dimension(200, 15));
    panel.add(projectName, c);

    // EMPTY LABEL (FOR SEPARATION)
    c.gridy = 1;
    c.gridx = 0;
    panel.add(new JLabel(ContentUtil.getWord("EMPTY")), c);

    // LABEL
    c.gridy = 2;
    JLabel pathLabel = new JLabel(ContentUtil.getWord("PROJECT_PATH"));
    pathLabel.setPreferredSize(new Dimension(100, 15));
    panel.add(pathLabel, c);
    // FIELD
    c.gridx = 1;
    projectPath = new JTextField();
    projectPath.setPreferredSize(new Dimension(200, 15));
    panel.add(projectPath, c);
    // BUTTON
    c.gridx = 2;
    JButton selectPath = new JButton(ContentUtil.getWord("EXTRA"));
    selectPath.setPreferredSize(new Dimension(30, 15));
    selectPath.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            showPathChooser();
          }
        });
    panel.add(selectPath, c);

    return panel;
  }

  private void showPathChooser() {
    String path = OSUtil.showPathChooser(ContentUtil.getWord("SELECT_MAVEN_PROJECT_FOLDER"), this);
    if (!path.isEmpty()) {
      String folderName = path.substring(path.lastIndexOf(Sources.SYS_FILE_SEPARATOR) + 1);
      projectName.setText(folderName);
      projectPath.setText(path);
      projectPath.requestFocus();
      projectName.requestFocus();
    }
  }
}
