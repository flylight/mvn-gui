package org.ar.mvn.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;
import org.ar.mvn.gui.constants.PanelSources;
import org.ar.mvn.gui.constants.Sources;
import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.listeners.ITaskExecutorListener;
import org.ar.mvn.gui.listeners.LabelMouseClickListener;
import org.ar.mvn.gui.listeners.LabelMouseOverListener;
import org.ar.mvn.gui.state.ApplicationStateManager;
import org.ar.mvn.gui.utils.CommandExecutorUtil;
import org.ar.mvn.gui.utils.ContentUtil;
import org.ar.mvn.gui.utils.DialogMessagesUtil;
import org.ar.mvn.gui.utils.OSUtil;
import org.ar.mvn.gui.utils.UpdateUI;

public class ProjectPanel extends JPanel {

  private static final String COMPILE_CMD = "compile";
  private static final String CLEAN_INSTALL_CMD = "clean install";
  private static final String INSTALL_CMD = "install";
  private static final String CLEAN_CMD = "clean";
  private static final String DSKIP_TESTS = " -DskipTests";
  private static final String COMMAND_PREFIX = "-> ";
  private static final String NEW_LINE = "\n";

  private JCheckBox skipTests;
  private JTextArea consoleView;
  private JTextField commandLine;

  private Project currentProject;

  public ProjectPanel() {
    setBackground(Color.GRAY);
    setLayout(new BorderLayout());
  }

  public JPanel setProject(Project project) {
    this.currentProject = project;
    removeAll();

    buildPanelHeader();
    buildPanelBody();

    UpdateUI.update(this);
    return this;
  }

  private void buildPanelBody() {
    JPanel body = new JPanel(new BorderLayout());
    body.add(buildActionPanel(), BorderLayout.NORTH);
    body.add(buildConsolePanel(), BorderLayout.CENTER);
    body.add(buildCommandLinePanel(), BorderLayout.SOUTH);
    add(body, BorderLayout.CENTER);
  }

  @SuppressWarnings("Convert2Lambda")
  private JPanel buildCommandLinePanel() {
    JPanel commandLinePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    commandLinePanel.setPreferredSize(new Dimension(0, 35));
    commandLinePanel.setBackground(Color.GRAY);

    // label
    commandLinePanel.add(new JLabel(ContentUtil.getWord("MVN_LB")));

    // manual command line
    commandLine = new JTextField();
    commandLine.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {}

          @Override
          public void keyReleased(KeyEvent e) {}

          @Override
          public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
              executeCommand(commandLine.getText());
            }
          }
        });

    commandLine.setBackground(Color.BLACK);
    commandLine.setForeground(Color.YELLOW);
    commandLine.setCaretColor(Color.YELLOW);
    commandLine.setPreferredSize(new Dimension(400, 25));
    commandLinePanel.add(commandLine);

    JButton executeCommand = new JButton(ContentUtil.getWord("EXECUTE"));
    executeCommand.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String command = commandLine.getText();
            executeCommand(command);
          }
        });

    commandLinePanel.add(executeCommand);
    return commandLinePanel;
  }

  private void executeCommand(String command) {
    if (command != null && !command.isEmpty()) {
      command += (skipTests.isSelected() ? DSKIP_TESTS : ContentUtil.getWord("EMPTY"));
      if (currentProject.getStatus() != Project.STATUS_BUSY) {

        currentProject.getConsoleLog().append(COMMAND_PREFIX + command);
        currentProject.getConsoleLog().append(NEW_LINE);
        commandLine.setText(ContentUtil.getWord("EMPTY"));
        refreshConsole();

        currentProject.setStatus(Project.STATUS_BUSY);
        // refresh
        PanelSources.DESKTOP.refreshProjectTable();
        // execute
        CommandExecutorUtil.executeCommand(
            currentProject,
            command,
            new ITaskExecutorListener() {
              @Override
              public void executed() {
                // refresh
                refreshConsole();
                PanelSources.DESKTOP.refreshProjectTable();
              }

              @Override
              public void updateConsole() {
                refreshConsole();
              }
            });
      } else {
        DialogMessagesUtil.showWarningMessage(
            this, ContentUtil.getWord("PROJECT_IS_BUSY_WAIT_WHILE_OTHER_OPERATION_WAS_FINISHED"));
      }
    }
  }

  public void refreshConsole() {
    consoleView.setText(currentProject.getConsoleLog().toString());
  }

  private JScrollPane buildConsolePanel() {
    consoleView = new JTextArea();

    consoleView.setFont(
        new Font(
            "arial",
            Font.PLAIN,
            ApplicationStateManager.INSTANCE().getSetting().getConsoleTextSize()));
    consoleView.setEditable(false);
    consoleView.setAutoscrolls(true);
    consoleView.setBackground(Color.BLACK);
    consoleView.setForeground(Color.YELLOW);

    // move caret always down
    DefaultCaret caret = (DefaultCaret) consoleView.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    refreshConsole();

    JScrollPane console = new JScrollPane(consoleView);
    console.setPreferredSize(new Dimension(425, 360));
    console.setBorder(BorderFactory.createMatteBorder(5, 10, 5, 10, Color.GRAY));
    return console;
  }

  @SuppressWarnings("Convert2Lambda")
  private JPanel buildActionPanel() {
    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    actionPanel.setBackground(Color.GRAY);

    JButton clean = new JButton(ContentUtil.getWord("CLEAN"));
    clean.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            executeCommand(CLEAN_CMD);
          }
        });
    actionPanel.add(clean);

    JButton install = new JButton(ContentUtil.getWord("INSTALL"));
    install.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            executeCommand(INSTALL_CMD);
          }
        });
    actionPanel.add(install);

    JButton cleanInstall = new JButton(ContentUtil.getWord("CLEAN_INSTALL"));
    cleanInstall.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            executeCommand(CLEAN_INSTALL_CMD);
          }
        });
    actionPanel.add(cleanInstall);

    JButton compile = new JButton(ContentUtil.getWord("COMPILE"));
    compile.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            executeCommand(COMPILE_CMD);
          }
        });
    actionPanel.add(compile);

    skipTests = new JCheckBox(ContentUtil.getWord("SKIP_TESTS"));
    actionPanel.add(skipTests);

    return actionPanel;
  }

  private void buildPanelHeader() {
    JPanel header = new JPanel();
    header.setBackground(Color.GRAY);
    header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
    header.setPreferredSize(new Dimension(0, 35));

    JLabel projectName = new JLabel(currentProject.getName());
    projectName.setForeground(Color.WHITE);
    projectName.setFont(new Font("arial", Font.PLAIN, 20));
    header.add(projectName);

    JLabel openInFolder = new JLabel(Sources.FOLDER_IMAGE);
    openInFolder.setPreferredSize(new Dimension(24, 24));
    openInFolder.setCursor(new Cursor(Cursor.HAND_CURSOR));
    openInFolder.addMouseListener(new LabelMouseOverListener(openInFolder));
    openInFolder.addMouseListener(
        new LabelMouseClickListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            OSUtil.openInOSFileManager(currentProject.getPath());
          }
        });
    header.add(openInFolder);

    JLabel editPomFile = new JLabel(Sources.EDIT_IMAGE);
    editPomFile.setPreferredSize(new Dimension(24, 24));
    editPomFile.setCursor(new Cursor(Cursor.HAND_CURSOR));
    editPomFile.addMouseListener(new LabelMouseOverListener(editPomFile));
    editPomFile.addMouseListener(
        new LabelMouseClickListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            OSUtil.openInOSFileManager(
                currentProject.getPath() + Sources.SYS_FILE_SEPARATOR + "pom.xml");
          }
        });
    header.add(editPomFile);

    JLabel clearCMD = new JLabel(Sources.CLEAR_CMD_IMAGE);
    clearCMD.setPreferredSize(new Dimension(24, 24));
    clearCMD.setCursor(new Cursor(Cursor.HAND_CURSOR));
    clearCMD.addMouseListener(new LabelMouseOverListener(clearCMD));
    clearCMD.addMouseListener(
        new LabelMouseClickListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            currentProject.setConsoleLog(new StringBuilder(ContentUtil.getWord("EMPTY")));
            refreshConsole();
          }
        });
    header.add(clearCMD);

    add(header, BorderLayout.NORTH);
  }
}
