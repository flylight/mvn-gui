package org.ar.mvn.gui;

import com.apple.eawt.Application;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.ar.mvn.gui.constants.PanelSources;
import org.ar.mvn.gui.constants.Sources;
import org.ar.mvn.gui.panels.NavigationMenuPanel;
import org.ar.mvn.gui.utils.OSUtil;
import org.ar.mvn.gui.utils.UpdateUI;

public class GeneralFrame extends JFrame {

  private JPanel topPanel;
  private JPanel centerPanel;
  private JPanel bottomPanel;

  public GeneralFrame() {
    setTitle("MVN GUI");
    setMinimumSize(new Dimension(800, 600));
    setLocationRelativeTo(null);
    setIcon();
    initialize();
    addToCenter(PanelSources.DESKTOP);
  }

  private void setIcon() {
    try {
      setIconImage(Sources.APPLICATION_ICON.getImage());
      // check OS name and show doc icon if it is MAC
      if (OSUtil.isMac()) {
        Application application = Application.getApplication();
        application.setDockIconImage(Sources.APPLICATION_ICON.getImage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initialize() {
    setLayout(new BorderLayout());

    topPanel = new JPanel();
    centerPanel = new JPanel();
    bottomPanel = new JPanel();

    topPanel.setPreferredSize(Sources.TOP_PANEL_SIZE);
    topPanel.setBackground(Color.DARK_GRAY);
    configureTopPanel();

    centerPanel.setBackground(Color.GRAY);
    centerPanel.setLayout(new BorderLayout());

    bottomPanel.setPreferredSize(Sources.BOTTOM_PANEL_SIZE);
    bottomPanel.setBackground(Color.DARK_GRAY);
    configureBottomPanel();

    add(topPanel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  private void configureTopPanel() {
    topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    topPanel.add(new NavigationMenuPanel());
  }

  private void configureBottomPanel() {
    bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    JLabel introLabel = new JLabel(Sources.INTRO);
    introLabel.setForeground(Color.WHITE);
    bottomPanel.add(introLabel);
  }

  public void addToCenter(JPanel panel) {
    centerPanel.removeAll();
    centerPanel.add(panel, BorderLayout.CENTER);
    UpdateUI.update(centerPanel);
  }
}
