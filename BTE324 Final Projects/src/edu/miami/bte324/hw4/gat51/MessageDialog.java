package edu.miami.bte324.hw4.gat51;

import javax.swing.JOptionPane;

/**
 * @author Grant Thorburn
 *
 */
public class MessageDialog {
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
