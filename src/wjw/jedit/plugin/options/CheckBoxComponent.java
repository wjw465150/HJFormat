package wjw.jedit.plugin.options;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Properties;

import javax.swing.JCheckBox;

import org.gjt.sp.jedit.jEdit;

public class CheckBoxComponent extends OptionPaneComponent implements MouseListener {
	private boolean defaultValue;
	private JCheckBox checkBox;
	private Helper helper;

	public CheckBoxComponent(String prop, boolean defaultValue) {
		super(prop);
		this.defaultValue = defaultValue;
	}

	public void init() {
		this.checkBox = new JCheckBox(this.getLabel(), jEdit.getBooleanProperty(this.getJEditProp(), this.defaultValue));

		this.checkBox.addMouseListener(this);
	}

	public void save() {
		jEdit.setBooleanProperty(this.getJEditProp(), this.checkBox.isSelected());
	}

	public boolean isSingle() {
		return true;
	}

	public void saveTo(Properties props) {
		props.put(this.getJEditProp(), this.checkBox.isSelected() ? "yes" : "no");
	}

	public Component getComponent() {
		return this.checkBox;
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
