package org.ar.mvn.gui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.ar.mvn.gui.ApplciationInitializer;
import org.ar.mvn.gui.constants.PanelSources;
import org.ar.mvn.gui.constants.Sources;
import org.ar.mvn.gui.listeners.LabelMouseClickListener;
import org.ar.mvn.gui.listeners.LabelMouseOverListener;

public class NavigationMenuPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  public NavigationMenuPanel() {
    GridLayout layout = new GridLayout(1, 4);
    layout.setVgap(30);
    setLayout(layout);
    setBackground(Color.DARK_GRAY);
    setPreferredSize(new Dimension(300, 50));
    initializeUI();
  }

  private void initializeUI() {
    JLabel manage = new JLabel(Sources.FILES_IMAGE);
    JLabel newProject = new JLabel(Sources.NEW_PROJ_IMAGE);
    JLabel setting = new JLabel(Sources.SETTING_IMAGE);
    JLabel help = new JLabel(Sources.HELP_IMAGE);

    manage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    newProject.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    setting.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    help.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    manage.addMouseListener(new LabelMouseOverListener(manage));
    newProject.addMouseListener(new LabelMouseOverListener(newProject));
    setting.addMouseListener(new LabelMouseOverListener(setting));
    help.addMouseListener(new LabelMouseOverListener(help));

    manage.addMouseListener(
        new LabelMouseClickListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            ApplciationInitializer.GFRAME.addToCenter(PanelSources.DESKTOP);
          }
        });
    newProject.addMouseListener(
        new LabelMouseClickListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            ApplciationInitializer.GFRAME.addToCenter(PanelSources.CREATE_PROJECT);
          }
        });
    setting.addMouseListener(
        new LabelMouseClickListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            ApplciationInitializer.GFRAME.addToCenter(PanelSources.SETTING);
          }
        });
    help.addMouseListener(
        new LabelMouseClickListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            ApplciationInitializer.GFRAME.addToCenter(PanelSources.HELP);
          }
        });

    add(manage);
    add(newProject);
    add(setting);
    add(help);
  }
}
