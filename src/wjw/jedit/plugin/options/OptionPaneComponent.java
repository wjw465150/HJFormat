package wjw.jedit.plugin.options;

import java.awt.Component;
import java.util.Properties;

import org.gjt.sp.jedit.jEdit;

abstract public class OptionPaneComponent {
	private String prop;

	abstract void init();

	abstract void save();

	abstract boolean isSingle();

	public abstract void saveTo(Properties props);

	private OptionPaneComponent() {
	}

	protected OptionPaneComponent(String prop) {
		this.prop = prop;
	}

	final String getJEditProp() {
		return "HJFormat." + this.prop;
	}

	final String getLabel() {
		return jEdit.getProperty("options.HJFormat." + this.prop, "");
	}

	final String getHelpText() {
		return jEdit.getProperty("options.HJFormat." + this.prop + ".text", "");
	}

	final String getHelpText(int index) {
		return jEdit.getProperty("options.HJFormat." + this.prop + "." + index + ".text", "");
	}

	void setHelper(Helper helper) {
	}

	abstract Component getComponent();
}
