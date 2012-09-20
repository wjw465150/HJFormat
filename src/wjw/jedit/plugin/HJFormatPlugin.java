package wjw.jedit.plugin;

import java.io.*;
import java.util.Properties;

import jodd.io.StreamUtil;

import org.gjt.sp.jedit.EditPlugin;
import org.gjt.sp.jedit.jEdit;

public class HJFormatPlugin extends EditPlugin {
	public static final String NAME = "wjw.jedit.plugin.HJFormatPlugin";
	public static String BASE_FORMAT;
	public static String JS_FORMAT;
	public static String HTML_FORMAT;
	public static String ALL_JS;

	public void start() {
		try {
			char[] chars = StreamUtil.readChars(HJFormatPlugin.class.getResourceAsStream("/js/base.js"), "UTF-8");
			BASE_FORMAT = String.valueOf(chars);

			chars = StreamUtil.readChars(HJFormatPlugin.class.getResourceAsStream("/js/jsformat.js"), "UTF-8");
			JS_FORMAT = String.valueOf(chars);

			chars = StreamUtil.readChars(HJFormatPlugin.class.getResourceAsStream("/js/htmlformat.js"), "UTF-8");
			HTML_FORMAT = String.valueOf(chars);

			ALL_JS = BASE_FORMAT + "\r\n" + JS_FORMAT + "\r\n" + HTML_FORMAT + "\r\n";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
	}

	// updates the current plugin properties with the given properties
	public static void save(Properties props) {
		if (props == null) {
			return;
		}
		try {
			File homeDir = jEdit.getPlugin(NAME).getPluginHome();
			if (!homeDir.exists()) {
				homeDir.mkdir();
			}
			Properties pluginProps = HJFormatPlugin.getProperties();
			pluginProps.putAll(props);
			File pluginPropsFile = new File(homeDir, "HJFormat.properties");
			BufferedWriter writer = new BufferedWriter(new FileWriter(pluginPropsFile));
			pluginProps.store(writer, "");
			writer.close();
		} catch (Exception ignored) { // NOPMD
		}
	}

	/**
	 * @return The currently set properties for this plugin or an empty Properties
	 *         if no settings are currently stored.
	 */
	public static Properties getProperties() {
		try {
			Properties pluginProps = new Properties();
			File homeDir = jEdit.getPlugin(NAME).getPluginHome();
			File pluginPropsFile = new File(homeDir, "HJFormat.properties");
			if (pluginPropsFile.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(pluginPropsFile));
				pluginProps.load(reader);
				reader.close();
				return pluginProps;
			}
		} catch (Exception ignored) { // NOPMD
		}
		return new Properties();
	}
}
