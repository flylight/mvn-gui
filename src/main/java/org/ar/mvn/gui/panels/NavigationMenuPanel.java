package org.ar.mvn.gui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ar.mvn.gui.ApplciationInitializer;
import org.ar.mvn.gui.constants.PanelSources;
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
    //
    ImageIcon filesImage =
        new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("files.png"));
    final JLabel manage = new JLabel(filesImage);
    //
    ImageIcon newProjectImage =
        new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("new.png"));
    JLabel newProject = new JLabel(newProjectImage);
    //
    ImageIcon settingsImage =
        new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("settings.png"));
    JLabel settings = new JLabel(settingsImage);
    //
    ImageIcon helpImage =
        new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("help.png"));
    JLabel help = new JLabel(helpImage);
    //
    manage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    newProject.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    settings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    help.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    //
    manage.addMouseListener(new LabelMouseOverListener(manage));
    newProject.addMouseListener(new LabelMouseOverListener(newProject));
    settings.addMouseListener(new LabelMouseOverListener(settings));
    help.addMouseListener(new LabelMouseOverListener(help));
    //
    manage.addMouseListener(new LabelMouseClickListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        ApplciationInitializer.GFRAME.addToCenter(PanelSources.DESKTOP);
      }
    });
    newProject.addMouseListener(new LabelMouseClickListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        ApplciationInitializer.GFRAME.addToCenter(PanelSources.CREATE_PROJECT);
      }
    });
    settings.addMouseListener(new LabelMouseClickListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        ApplciationInitializer.GFRAME.addToCenter(PanelSources.SETTINGS);
      }
    });
    help.addMouseListener(new LabelMouseClickListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        ApplciationInitializer.GFRAME.addToCenter(PanelSources.HELP);
      }
    });
    //

    add(manage);
    add(newProject);
    add(settings);
    add(help);
  }

}
