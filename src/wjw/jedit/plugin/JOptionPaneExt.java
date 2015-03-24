package wjw.jedit.plugin;

import java.io.*;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class JOptionPaneExt extends JOptionPane {

	public static void showMessageDialog(Component parentComponent, Object message) throws HeadlessException {
		showMessageDialog(parentComponent, message, "JOptionPaneExt", INFORMATION_MESSAGE);
	}

	public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType)
	    throws HeadlessException {
		showMessageDialog(parentComponent, message, title, messageType, null);
	}

	public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType,
	    Icon icon) throws HeadlessException {
		showOptionDialog(parentComponent, message, title, DEFAULT_OPTION, messageType, icon, null, null);
	}

	public static int showOptionDialog(Component parentComponent, Object message, String title, int optionType,
	    int messageType, Icon icon, Object[] options, Object initialValue) throws HeadlessException {

		if (message instanceof Throwable) {
			Throwable throwable = (Throwable) message;
			ByteArrayOutputStream ByteArrayOutputStreamA = new ByteArrayOutputStream();
			PrintStream PrintStreamA = new PrintStream(ByteArrayOutputStreamA);
			try {
				throwable.printStackTrace(PrintStreamA);
				message = ByteArrayOutputStreamA.toString();
			} finally {
				try {
					ByteArrayOutputStreamA.close();
				} catch (IOException ex) {
				}
				try {
					PrintStreamA.close();
				} catch (Exception ex1) {
				}
			}
		}

		if (message instanceof String) {
			if (((String) message).length() > 100) {
				JPanel jPanelA = new JPanel();
				JTextArea jTextAreaA = new JTextArea();
				JScrollPane jScrollPaneA = new JScrollPane();
				try {
					jPanelA.setLayout(new BorderLayout());
					jPanelA.setPreferredSize(new Dimension(500, 300));
					jScrollPaneA.setPreferredSize(jPanelA.getPreferredSize());
					jTextAreaA.setEditable(false);
					jScrollPaneA.setViewportView(jTextAreaA);
					jTextAreaA.setText((String) message);
					jTextAreaA.setCaretPosition(0);
					jPanelA.add(jScrollPaneA, BorderLayout.CENTER);
					return JOptionPane.showOptionDialog(parentComponent, jScrollPaneA, title, optionType, messageType, icon, options, initialValue);
				} finally {
					jTextAreaA = null;
					jScrollPaneA = null;
				}
			} else {
				return JOptionPane.showOptionDialog(parentComponent, message, title, optionType, messageType, icon, options, initialValue);
			}
		} else {
			return JOptionPane.showOptionDialog(parentComponent, message, title, optionType, messageType, icon, options, initialValue);
		}
	}
}
