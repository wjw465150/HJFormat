package wjw.jedit.plugin.options;

import java.util.Properties;

public class JTidyGeneralOptionPane extends JTidyAbstractOptionPane {
	{
		components = new OptionPaneComponent[] { 
				new CheckBoxComponent("new-buffer", false),
		    new TextComponent("wrap", "120", 3), 
		    new ComboBoxComponent("indent-count", 0),
		    new ComboBoxComponent("markdown-theme", 0) };
	}

	public JTidyGeneralOptionPane() {
		super("HJFormat.general");
	}
}
