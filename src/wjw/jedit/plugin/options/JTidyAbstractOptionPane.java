package wjw.jedit.plugin.options;

import java.awt.Color;
import java.awt.Font;

import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.gjt.sp.jedit.AbstractOptionPane;

import wjw.jedit.plugin.HJFormatPlugin;

public abstract class JTidyAbstractOptionPane extends AbstractOptionPane implements Helper {
	private static Font helpFont = UIManager.getFont("Label.font");
	private static Color helpBackground = UIManager.getColor("Label.background");
	private static Color helpForeground = UIManager.getColor("Label.foreground");

	protected OptionPaneComponent[] components;

	private JTextArea helpArea;

	public JTidyAbstractOptionPane(String name) {
		super(name);
	}

	public void _init() {
		for (int i = 0; i < components.length; i++) {
			components[i].init();
			components[i].setHelper(this);
			if (components[i].isSingle()) {
				addComponent(components[i].getComponent());
			} else {
				addComponent(components[i].getLabel(), components[i].getComponent());
			}
		}

		addHelpArea();
	}

	public void _save() {
		Properties props = new Properties();
		for (int i = 0; i < components.length; i++) {
			components[i].save();
			components[i].saveTo(props);
		}
		HJFormatPlugin.save(props);
	}

	public OptionPaneComponent[] getOptionPaneComponents() {
		return this.components; // NOPMD
	}

	protected void addHelpArea() {
		this.helpArea = new JTextArea(12, 40);
		this.helpArea.setBorder(BorderFactory.createTitledBorder("Help"));
		// this.helpArea.setOpaque(true);
		this.helpArea.setEditable(false);
		this.helpArea.setLineWrap(true);
		this.helpArea.setWrapStyleWord(true);
		this.helpArea.setFont(helpFont);
		this.helpArea.setBackground(helpBackground);
		this.helpArea.setForeground(helpForeground);
		this.helpArea.setText("");

		addComponent(this.helpArea);
	}

	// Helper implementation
	public String getHelp() {
		return this.helpArea.getText();
	}

	public void showHelp(String text) {
		if (this.helpArea != null && text != null) {
			this.helpArea.setText(text);
		}
	}

	public void hideHelp() {
		if (this.helpArea != null) {
			this.helpArea.setText("");
		}
	}
}
