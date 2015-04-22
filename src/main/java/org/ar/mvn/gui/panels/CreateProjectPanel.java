package org.ar.mvn.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import org.ar.mvn.gui.constants.Text;
import org.ar.mvn.gui.listeners.IGenerateProjectExecutorListener;
import org.ar.mvn.gui.utils.CommandExecutorUtil;
import org.ar.mvn.gui.utils.DialogMessagesUtil;
import org.ar.mvn.gui.utils.OSUtil;
import org.ar.mvn.gui.utils.VerificationUtil;

public class CreateProjectPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private JTextField projectPathField;
  private JTextField arfitacIDField;
  private JTextField groupIDField;

  private JRadioButton custom;

  private Map<JRadioButton, String> radioToArchetype = new HashMap<JRadioButton, String>();

  private JTextField otherArchetype;

  private JTextArea consoleView;

  private StringBuilder log = new StringBuilder();

  public CreateProjectPanel() {
    setBackground(Color.GRAY);
    setLayout(new BorderLayout());
    add(perojectInfoPanel(), BorderLayout.NORTH);
    add(consolePanel(), BorderLayout.CENTER);
    add(actionPanel(), BorderLayout.SOUTH);
  }

  private JPanel actionPanel() {
    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    actionPanel.setBackground(Color.GRAY);

    final JButton generateProject = new JButton(Text.GENERATE);

    generateProject.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (projectPathField.getText() != null && !projectPathField.getText().isEmpty()
            && VerificationUtil.checkFolder(projectPathField.getText())) {
          if (groupIDField.getText() != null && !groupIDField.getText().isEmpty()) {
            if (arfitacIDField.getText() != null && !arfitacIDField.getText().isEmpty()) {
              if (!custom.isSelected()
                  || (otherArchetype.getText() != null && !otherArchetype.getText().isEmpty())) {
                // disable button
                generateProject.setEnabled(false);
                // prepare console
                log = new StringBuilder(Text.EMPTY);
                refreshConsole();
                // execute
                CommandExecutorUtil.executeGenerateProjectCommand(projectPathField.getText(),
                    groupIDField.getText(), arfitacIDField.getText(), getArchetype(),
                    new IGenerateProjectExecutorListener() {
                      @Override
                      public void updateConsole(String msg) {
                        log.append(msg);
                        refreshConsole();
                      }

                      @Override
                      public void executed() {
                        generateProject.setEnabled(true);
                      }
                    });
              } else {
                DialogMessagesUtil.showErrorMessage(CreateProjectPanel.this,
                    Text.ARCHETYPE_CAN_NOT_BE_EMPTY);
              }
            } else {
              //
              DialogMessagesUtil.showErrorMessage(CreateProjectPanel.this,
                  Text.ARTIFACT_ID_CAN_NOT_BE_EMPTY);
            }
          } else {
            //
            DialogMessagesUtil.showErrorMessage(CreateProjectPanel.this,
                Text.GROUP_ID_CAN_NOT_BE_EMPTY);
          }
        } else {
          //
          DialogMessagesUtil.showErrorMessage(CreateProjectPanel.this,
              Text.PROJECT_HOME_PATH_INCORRECT);
        }
      }
    });

    actionPanel.add(generateProject);

    return actionPanel;
  }

  private String getArchetype() {
    if (custom.isSelected()) {
      return otherArchetype.getText();
    } else {
      for (Entry<JRadioButton, String> val : radioToArchetype.entrySet()) {
        if (val.getKey().isSelected()) {
          return val.getValue();
        }
      }
    }
    return null;
  }

  private JScrollPane consolePanel() {
    consoleView = new JTextArea();
    //
    consoleView.setEditable(false);
    consoleView.setAutoscrolls(true);
    consoleView.setBackground(Color.BLACK);
    consoleView.setForeground(Color.YELLOW);
    // move caret always down
    DefaultCaret caret = (DefaultCaret) consoleView.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    refreshConsole();
    //
    JScrollPane console = new JScrollPane(consoleView);
    console.setPreferredSize(new Dimension(600, 300));
    console.setBorder(BorderFactory.createMatteBorder(5, 10, 5, 10, Color.GRAY));
    //
    return console;
  }

  public void refreshConsole() {
    consoleView.setText(log.toString());
  }

  private JPanel perojectInfoPanel() {
    JPanel configPanel = new JPanel(new BorderLayout());
    //

    // project info panel
    JPanel projectInfoPanel = new JPanel(new GridBagLayout());

    projectInfoPanel.setBackground(Color.GRAY);

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 10;
    c.ipadx = 5;

    c.gridy = 0;
    c.gridx = 0;

    JLabel projectInfoLanel = new JLabel(Text.PROJECT_INFO);
    projectInfoLanel.setFont(new Font("arial", Font.BOLD, 15));
    projectInfoLanel.setForeground(Color.WHITE);

    projectInfoPanel.add(projectInfoLanel, c);
    // project path

    c.gridy = 1;
    c.gridx = 0;

    JLabel projectPathLabel = new JLabel(Text.SELECT_FOLDER_FOR_NEW_PROJECT);
    projectPathField = new JTextField();
    JButton projectSelectPathBtn = new JButton(Text.EXTRA);

    projectPathLabel.setPreferredSize(new Dimension(200, 25));
    projectPathField.setPreferredSize(new Dimension(500, 25));
    projectSelectPathBtn.setPreferredSize(new Dimension(30, 25));

    projectSelectPathBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String path =
            OSUtil.showPathChooser(Text.SELECT_PARENT_FOLDER_FOR_NEW_PROJECT,
                CreateProjectPanel.this);
        if (!path.isEmpty()) {
          projectPathField.setText(path);
          projectPathField.requestFocus();
        }
      }
    });

    projectInfoPanel.add(projectPathLabel, c);
    c.gridx = 1;
    projectInfoPanel.add(projectPathField, c);
    c.gridx = 2;
    projectInfoPanel.add(projectSelectPathBtn, c);

    // project artifact

    c.gridy = 2;
    c.gridx = 0;

    JLabel artifactIDLabel = new JLabel(Text.PROJECT_NAME_ARTIFACT_ID);
    arfitacIDField = new JTextField();

    arfitacIDField.setPreferredSize(new Dimension(500, 25));

    projectInfoPanel.add(artifactIDLabel, c);
    c.gridx = 1;
    projectInfoPanel.add(arfitacIDField, c);

    // project packaging

    c.gridy = 3;
    c.gridx = 0;

    JLabel groupIDLabel = new JLabel(Text.PACKAGE_PATH_GROUP_ID);
    groupIDField = new JTextField();

    groupIDField.setPreferredSize(new Dimension(500, 25));

    projectInfoPanel.add(groupIDLabel, c);
    c.gridx = 1;
    projectInfoPanel.add(groupIDField, c);

    // project generation archetype
    JPanel archetypePanel = new JPanel(new GridLayout(2, 1));
    archetypePanel.setBackground(Color.GRAY);

    JLabel projectArchetypeLabel = new JLabel(Text.MAVEN_ARCHETYPE_ID);
    projectArchetypeLabel.setFont(new Font("arial", Font.BOLD, 15));
    projectArchetypeLabel.setForeground(Color.WHITE);
    projectArchetypeLabel.setBorder(BorderFactory.createMatteBorder(0, 26, 0, 0, Color.GRAY));

    archetypePanel.add(projectArchetypeLabel);

    JPanel radioArchetypePanel = new JPanel(new GridBagLayout());
    radioArchetypePanel.setBackground(Color.GRAY);

    archetypePanel.add(radioArchetypePanel);

    c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 10;
    c.ipadx = 5;

    c.gridy = 0;
    c.gridx = 0;


    final JRadioButton quickStartArchetype = new JRadioButton(Text.SIMPLE);
    final JRadioButton simpleWebJavaArchetype = new JRadioButton(Text.SIMPLE_WEB);
    // add to map with archetype value
    radioToArchetype.put(quickStartArchetype, "maven-archetype-quickstart");
    radioToArchetype.put(simpleWebJavaArchetype, "maven-archetype-webapp");
    //
    custom = new JRadioButton(Text.OTHER);


    quickStartArchetype.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        simpleWebJavaArchetype.setSelected(false);
        custom.setSelected(false);
        //
        otherArchetype.setEnabled(false);
      }
    });
    simpleWebJavaArchetype.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        quickStartArchetype.setSelected(false);
        custom.setSelected(false);
        //
        otherArchetype.setEnabled(false);
      }
    });

    custom.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        quickStartArchetype.setSelected(false);
        simpleWebJavaArchetype.setSelected(false);
        //
        otherArchetype.setEnabled(true);
      }
    });
    c.gridy = 1;
    radioArchetypePanel.add(quickStartArchetype, c);
    c.gridx = 1;
    radioArchetypePanel.add(simpleWebJavaArchetype, c);
    c.gridx = 2;
    radioArchetypePanel.add(custom, c);

    // Other archetype

    JPanel otherArchetypePanel = new JPanel(new FlowLayout());
    otherArchetypePanel.setBackground(Color.GRAY);

    JLabel otherArchetypeLabel = new JLabel(Text.TYPE_YOUR_ARCHETYPE_ID);


    otherArchetype = new JTextField();
    otherArchetype.setEnabled(false);
    otherArchetype.setPreferredSize(new Dimension(600, 25));
    //
    otherArchetypePanel.add(otherArchetypeLabel);
    otherArchetypePanel.add(otherArchetype);
    //

    configPanel.add(archetypePanel, BorderLayout.CENTER);
    configPanel.add(projectInfoPanel, BorderLayout.NORTH);
    configPanel.add(otherArchetypePanel, BorderLayout.SOUTH);

    return configPanel;
  }
}
