package org.ar.mvn.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.ar.mvn.gui.utils.OSUtil;

public class HelpPanel extends JPanel {
  private static final long serialVersionUID = 1L;

  public HelpPanel() {
    setBackground(Color.GRAY);
    setLayout(new BorderLayout());

    // add
    add(getHelpComponent(), BorderLayout.CENTER);
  }

  private Component getHelpComponent() {
    JTextArea helpText = new JTextArea();
    //
    helpText.setEditable(false);
    helpText.setAutoscrolls(true);
    helpText.setBackground(Color.DARK_GRAY);
    helpText.setForeground(Color.WHITE);

    helpText.setText(OSUtil.readHelpFile());

    return helpText;
  }
}
