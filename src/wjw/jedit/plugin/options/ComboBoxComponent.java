package wjw.jedit.plugin.options;

import java.awt.Component;

import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.gjt.sp.jedit.jEdit;

//import org.gjt.sp.util.Log;

public class ComboBoxComponent extends OptionPaneComponent {
	protected int defaultIndex;
	private JComboBox comboBox;
	private Helper helper;

	public ComboBoxComponent(String prop, int defaultIndex) {
		super(prop);
		this.defaultIndex = defaultIndex;
	}

	public void init() {
		this.comboBox = new JComboBox();
		this.comboBox.setRenderer(new ListCellRendererAdapter(this.comboBox.getRenderer()));
		this.comboBox.setEditable(false);

		String item = null;
		for (int i = 0; (item = jEdit.getProperty(this.getJEditProp() + "." + i)) != null; i++) {
			this.comboBox.addItem(item);
		}

		int selectedItem = this.defaultIndex;
		try {
			selectedItem = Integer.parseInt(jEdit.getProperty(this.getJEditProp()));
		} catch (NumberFormatException nfe) {
		} // NOPMD

		this.comboBox.setSelectedIndex(selectedItem);
	}

	public boolean isSingle() {
		return false;
	}

	public void save() {
		jEdit.setProperty(this.getJEditProp(), Integer.toString(this.comboBox.getSelectedIndex()));
	}

	public void saveTo(Properties props) {
		String prop = this.getJEditProp();
		int idx = this.defaultIndex;

		try {
			idx = Integer.parseInt(jEdit.getProperty(this.getJEditProp()));
		} catch (NumberFormatException nfe) {
		} // NOPMD

		props.put(prop, jEdit.getProperty(this.getJEditProp() + "." + idx));
	}

	public Component getComponent() {
		return this.comboBox;
	}

	void setHelper(Helper helper) {
		this.helper = helper;
	}

	private class ListCellRendererAdapter implements ListCellRenderer {
		private ListCellRenderer renderer;

		ListCellRendererAdapter(ListCellRenderer renderer) {
			this.renderer = renderer;
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
		    boolean cellHasFocus) {
			if (isSelected && ComboBoxComponent.this.helper != null) {
				String text = ComboBoxComponent.this.getHelpText(index);
				if (text == null) {
					text = ComboBoxComponent.this.getHelpText();
				}
				ComboBoxComponent.this.helper.showHelp(text);
			}

			return this.renderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
	}
}
