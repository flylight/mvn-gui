package org.ar.mvn.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.ar.mvn.gui.constants.PanelSources;
import org.ar.mvn.gui.constants.Sources;
import org.ar.mvn.gui.entity.Project;
import org.ar.mvn.gui.listeners.LabelMouseClickListener;
import org.ar.mvn.gui.listeners.LabelMouseOverListener;
import org.ar.mvn.gui.state.ApplicationStateManager;
import org.ar.mvn.gui.utils.ContentUtil;
import org.ar.mvn.gui.utils.DialogMessagesUtil;
import org.ar.mvn.gui.utils.UpdateUI;

public class DesktopPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  private JPanel projectTableDataPanel;
  private JPanel centerPanel;

  public DesktopPanel() {
    ApplicationStateManager.INSTANCE().loadSetting();
    ApplicationStateManager.INSTANCE().loadProjects();
    ApplicationStateManager.INSTANCE().checkProjectsStatuses();

    setBackground(Color.GRAY);
    setLayout(new BorderLayout());

    centerPanel = generateCenterPanel();
    JPanel leftPanel = generateLeftPanel();

    add(centerPanel, BorderLayout.CENTER);
    add(leftPanel, BorderLayout.WEST);

    refreshProjectTable();
  }

  private JPanel generateLeftPanel() {
    JPanel panel = new JPanel();
    panel.setBackground(Color.GRAY);
    panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
    panel.setPreferredSize(new Dimension(250, 0));

    panel.setLayout(new BorderLayout());
    panel.add(getProjectTableHeaderPanel(), BorderLayout.NORTH);
    panel.add(getProjectTableBodyPanel(), BorderLayout.CENTER);

    return panel;
  }

  private JScrollPane getProjectTableBodyPanel() {
    JPanel panel = new JPanel();
    panel.setBackground(Color.GRAY);

    projectTableDataPanel = new JPanel(new GridLayout(10, 1));
    projectTableDataPanel.setBackground(Color.GRAY);

    panel.add(projectTableDataPanel);

    JScrollPane scrollPanel = new JScrollPane(panel);
    scrollPanel.setBorder(BorderFactory.createEmptyBorder());
    scrollPanel.setBackground(Color.GRAY);

    return scrollPanel;
  }

  public void refreshProjectTable() {
    projectTableDataPanel.removeAll();

    projectTableDataPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 10;
    c.ipadx = 5;
    int gridY = 0;

    for (final Project project : ApplicationStateManager.INSTANCE().getProjects()) {
      c.gridy = gridY;
      c.gridx = 0;
      projectTableDataPanel.add(
          new JLabel(
              project.getStatus() == Project.STATUS_AVAILABLE
                  ? Sources.AVAILABLE_PROJECT_IMAGE
                  : project.getStatus() == Project.STATUS_BUSY
                      ? Sources.BUSY_PROJECT_IMAGE
                      : project.getStatus() == Project.STATUS_ERROR
                          ? Sources.ERROR_PROJECT_IMAGE
                          : Sources.UNAVAILABLE_PROJECT_IMAGE),
          c);

      c.gridx = 1;
      JLabel projectSelectButton = new JLabel(project.getName());
      // check color
      if (project.isSelected()) {
        projectSelectButton.setForeground(Color.GREEN);
      } else {
        projectSelectButton.setForeground(Color.WHITE);
      }

      projectSelectButton.addMouseListener(
          new LabelMouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
              if (project.getStatus() == Project.STATUS_UNAVAILABLE) {
                DialogMessagesUtil.showErrorMessage(
                    DesktopPanel.this,
                    ContentUtil.getWord(
                        "THIS_PROJECT_IS_UNAVAILABLE_MAYBE_IT_WAS_MOVED_OR_DELETED"));
              } else {
                // find selected and deselect
                ApplicationStateManager.INSTANCE().getProjects().stream()
                    .filter(e -> e.isSelected())
                    .forEach(e -> e.setSelected(false));

                project.setSelected(true);
                addToCenterPanel(PanelSources.DISPLAY_PROJECT.setProject(project));
                refreshProjectTable();
              }
            }
          });

      projectSelectButton.setPreferredSize(new Dimension(180, 17));
      projectSelectButton.setHorizontalAlignment(JLabel.CENTER);
      projectSelectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      projectSelectButton.addMouseListener(new LabelMouseOverListener(projectSelectButton));
      projectTableDataPanel.add(projectSelectButton, c);

      c.gridx = 2;
      JLabel deleteProjectButton = new JLabel(Sources.DELETE);
      deleteProjectButton.addMouseListener(
          new LabelMouseClickListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
              deleteProject(project);
            }
          });

      deleteProjectButton.setPreferredSize(new Dimension(17, 17));
      deleteProjectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      deleteProjectButton.addMouseListener(new LabelMouseOverListener(deleteProjectButton));
      projectTableDataPanel.add(deleteProjectButton, c);

      gridY++;
    }
    UpdateUI.update(projectTableDataPanel);
  }

  private JPanel getProjectTableHeaderPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panel.setBackground(Color.GRAY);
    panel.setPreferredSize(new Dimension(0, 35));
    panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

    JLabel addProject = new JLabel(Sources.ADD_BIG_IMANE);
    addProject.setPreferredSize(new Dimension(25, 25));

    addProject.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    addProject.addMouseListener(new LabelMouseOverListener(addProject));

    addProject.addMouseListener(
        new LabelMouseClickListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            PanelSources.ADD_PROJECT.setVisible(true);
          }
        });

    JLabel projectsLabel = new JLabel(ContentUtil.getWord("PROJECTS"));
    projectsLabel.setFont(new Font("arial", Font.BOLD, 15));
    projectsLabel.setForeground(Color.WHITE);
    projectsLabel.setPreferredSize(new Dimension(200, 35));

    panel.add(projectsLabel);
    panel.add(addProject);
    return panel;
  }

  private JPanel generateCenterPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.GRAY);
    return panel;
  }

  public void addNewProject(String name, String path) {
    Project project = new Project();
    project.setName(name);
    project.setPath(path);
    project.setStatus(Project.STATUS_AVAILABLE);
    ApplicationStateManager.INSTANCE().addProject(project);
    refreshProjectTable();
  }

  private void deleteProject(Project project) {
    if (DialogMessagesUtil.confirmationWindow(
        this, ContentUtil.getWord("ARE_YOU_SURE_WANT_TO_DELETE_THIS_PROJECT"))) {
      ApplicationStateManager.INSTANCE().removeProject(project);
      refreshProjectTable();
    }
  }

  private void addToCenterPanel(JPanel panel) {
    centerPanel.removeAll();
    centerPanel.add(panel, BorderLayout.CENTER);
    UpdateUI.update(centerPanel);
  }
}
