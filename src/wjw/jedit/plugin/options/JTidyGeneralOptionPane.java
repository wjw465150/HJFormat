package wjw.jedit.plugin.options;

import java.util.Properties;

public class JTidyGeneralOptionPane extends JTidyAbstractOptionPane {
	{
		components = new OptionPaneComponent[] { new TextComponent("wrap", "120", 3),
		    new ComboBoxComponent("indent-count", 0), new CheckBoxComponent("new-buffer", false) };
	}

	public JTidyGeneralOptionPane() {
		super("HJFormat.general");
	}
}
