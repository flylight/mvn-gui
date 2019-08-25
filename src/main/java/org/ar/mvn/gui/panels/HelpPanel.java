package org.ar.mvn.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.ar.mvn.gui.utils.OSUtil;

public class HelpPanel extends JPanel {

  public HelpPanel() {
    setLayout(new BorderLayout());
    add(getHelpComponent(), BorderLayout.CENTER);
  }

  private Component getHelpComponent() {
    JTextArea helpText = new JTextArea();
    helpText.setText(OSUtil.readHelpFile());

    helpText.setBackground(Color.GRAY);
    helpText.setForeground(Color.WHITE);
    helpText.setFont(new Font("arial", Font.PLAIN, 15));
    helpText.setEditable(false);
    helpText.setAutoscrolls(true);

    return new JScrollPane(helpText);
  }
}
