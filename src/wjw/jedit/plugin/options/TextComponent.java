package wjw.jedit.plugin.options;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Properties;

import javax.swing.JTextField;

import org.gjt.sp.jedit.jEdit;

public class TextComponent extends OptionPaneComponent implements MouseListener {
	private String defaultValue;
	private int columns;
	private JTextField textField;
	private Helper helper;

	public TextComponent(String prop, String defaultValue) {
		this(prop, defaultValue, -1);
	}

	public TextComponent(String prop, String defaultValue, int columns) {
		super(prop);
		this.defaultValue = defaultValue;
		this.columns = columns;
	}

	public void init() {
		if (this.columns > 0) {
			this.textField = new JTextField(jEdit.getProperty(this.getJEditProp(), this.defaultValue), this.columns);
		} else {
			this.textField = new JTextField(jEdit.getProperty(this.getJEditProp(), this.defaultValue));
		}

		this.textField.addMouseListener(this);
	}

	public boolean isSingle() {
		return false;
	}

	public void save() {
		jEdit.setProperty(this.getJEditProp(), this.textField.getText());
	}

	public void saveTo(Properties props) {
		props.put(this.getJEditProp(), jEdit.getProperty(this.getJEditProp(), this.defaultValue));
	}

	public Component getComponent() {
		return this.textField;
	}

	void setHelper(Helper helper) {
		this.helper = helper;
	}

	// MouseListener implementation
	public void mouseClicked(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent evt) {
		if (this.helper != null) {
			this.helper.showHelp(this.getHelpText());
		}
	}

	public void mouseExited(MouseEvent evt) {
		if (this.helper != null) {
			this.helper.hideHelp();
		}
	}

	public void mousePressed(MouseEvent evt) {
	}

	public void mouseReleased(MouseEvent evt) {
	}
}
