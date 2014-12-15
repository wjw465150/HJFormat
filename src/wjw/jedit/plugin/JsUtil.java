package wjw.jedit.plugin;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;

import javax.swing.JOptionPane;

import jodd.io.StreamUtil;

import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.jEdit;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;

public abstract class JsUtil {
	private static final String MODE = "html";
	private static final String CODE = "UTF-8";

	private static final String jStr1 = "<!doctype html public '-//W3C//DTD HTML 4.0 Transitional //EN'>\n" + "<html>\n"
	    + "<head>\n" + "<title>\n";
	private static final String jStr2 = "</title>\n" + "<meta charset='UTF-8' />\n" + "<style type=\"text/css\">\n";
	private static final String jStr3 = "\n</style>\n";
	private static final String jStr4 = "\n<script language=\"JavaScript\" type=\"text/javascript\">\n";
	private static final String jStr5 = "\n</script>\n";
	private static final String jStr6 = "</head>\n" + "<body>\n" + "<div id='generated-toc'></div>\n" + "";

	private static final String jStr7 = "\n\n<script type='text/javascript'>\n" + "$(document).ready(function(){\n"
	    + "  if($('code').length>0){\n" + "    $('code').parent().addClass('prettyprint linenums');\n"
	    + "    prettyPrint();\n" + "};\n" + "});\n" + "</script>\n" + "</body>\n" + "</html>\n";

	public static String CSS_DEFAULT;
	public static String CSS_PRETTIFY;

	public static String JS_JQUERY;
	public static String JS_MARKDOWN_CONVERTER;
	public static String JS_MARKDOWN_EXTRA;
	public static String JS_PRETTIFY;
	public static String JS_TOC;

	public static String JS_EVAL;

	static {
		try {
			char[] chars = StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/default.css"), "GBK");
			CSS_DEFAULT = String.valueOf(chars);
			chars = StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/prettify.css"), CODE);
			CSS_PRETTIFY = String.valueOf(chars);

			chars = StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/jquery-1.10.2.min.js"), CODE);
			JS_JQUERY = String.valueOf(chars);

			chars = StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/Markdown.Converter.js"), CODE);
			JS_MARKDOWN_CONVERTER = String.valueOf(chars);

			chars = StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/Markdown.Extra.js"), CODE);
			JS_MARKDOWN_EXTRA = String.valueOf(chars);

			chars = StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/prettify.js"), CODE);
			JS_PRETTIFY = String.valueOf(chars);

			chars = StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/toc.js"), CODE);
			JS_TOC = String.valueOf(chars);

			JS_EVAL = JS_MARKDOWN_CONVERTER + "\r\n" + JS_MARKDOWN_EXTRA + "\r\n";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void cpoyBufferProperties(Buffer srcBuffer, Buffer destBuffer) {
		destBuffer.setProperty("lineSeparator", srcBuffer.getStringProperty("lineSeparator"));
		destBuffer.setProperty("encoding", srcBuffer.getStringProperty("encoding"));
		destBuffer.setProperty("defaultMode", srcBuffer.getStringProperty("defaultMode"));
		destBuffer.setProperty("folding", srcBuffer.getStringProperty("folding"));

		destBuffer.setProperty("elasticTabstops", srcBuffer.getStringProperty("elasticTabstops"));
		destBuffer.setProperty("wrap", srcBuffer.getStringProperty("wrap"));
		destBuffer.setProperty("maxLineLen", srcBuffer.getStringProperty("maxLineLen"));
		destBuffer.setProperty("tabSize", srcBuffer.getStringProperty("tabSize"));
		destBuffer.setProperty("indentSize", srcBuffer.getStringProperty("indentSize"));
	}

	public static String markdown2Html(String text) {
		Context cx = Context.enter();

		try {
			cx.setOptimizationLevel(-1);
			cx.setLanguageVersion(Context.VERSION_1_5);

			Global global = new Global(cx);
			Scriptable scope = cx.initStandardObjects(global /* 关键 */);

			//设置scope属性
			scope.put("___text", scope, text);

			int wrapLen = jEdit.getIntegerProperty("HJFormat.wrap", 120);
			int indentSize = jEdit.getIntegerProperty("HJFormat.indent-count" + "."
			    + jEdit.getIntegerProperty("HJFormat.indent-count"));
			String jsStr = JS_EVAL
			    + " var converter=new Markdown.Converter();  Markdown.Extra.init(converter); converter.makeHtml(___text);";

			// 执行js
			Object result = cx.evaluateString(scope, jsStr, null, 1, null);

			return Context.toString(result);
		} catch (Throwable thex) {
			thex.printStackTrace();
			return thex.toString();
		} finally {
			Context.exit();
		}
	}

	public static void saveToBuffer(final View view, final Buffer buffer, final String text) {
		view.showWaitCursor();
		try {
			final Buffer htmlBuffer = jEdit.newFile(view);

			String name;
			if (buffer.isUntitled()) {
				name = "Markdown text";
			} else {
				name = buffer.getName();
			}
			StringBuilder builder = new StringBuilder(text.length() * 2);
			builder.append(jStr1);
			builder.append(name);

			//CSS
			builder.append(jStr2);
			builder.append(CSS_DEFAULT);
			builder.append("\n\n");
			builder.append(CSS_PRETTIFY);
			builder.append(jStr3);

			//JS
			builder.append(jStr4);
			builder.append(JS_JQUERY);
			builder.append(jStr5);

			builder.append(jStr4);
			builder.append(JS_PRETTIFY);
			builder.append(jStr5);

			builder.append(jStr4);
			builder.append(JS_TOC);
			builder.append(jStr5);

			//body
			builder.append(jStr6);
			builder.append(text);
			builder.append(jStr7);

			htmlBuffer.insert(0, builder.toString());

			htmlBuffer.setMode(MODE);
			cpoyBufferProperties(buffer, htmlBuffer);
			htmlBuffer.propertiesChanged();

			view.setBuffer(htmlBuffer);
			view.setUserTitle(buffer.getName() + ".html");
			view.updateTitle();
		} finally {
			view.hideWaitCursor();
		}
	}

	public static void saveToFile(final View view, final Buffer buffer, final String text) {
		if (buffer.isUntitled()) {
			JOptionPane.showMessageDialog(null, "Buffer must saved.", "HJFormat Plugin", JOptionPane.WARNING_MESSAGE);
			return;
		}
		File htmlFile = new File(buffer.getPath() + ".html");

		BufferedWriter w = null;
		view.showWaitCursor();
		try {
			w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), CODE));

			w.write(jStr1);
			w.write(buffer.getName());

			//CSS
			w.write(jStr2);
			w.write(CSS_DEFAULT);
			w.write("\n\n");
			w.write(CSS_PRETTIFY);
			w.write(jStr3);

			//JS
			w.write(jStr4);
			w.write(JS_JQUERY);
			w.write(jStr5);

			w.write(jStr4);
			w.write(JS_PRETTIFY);
			w.write(jStr5);

			w.write(jStr4);
			w.write(JS_TOC);
			w.write(jStr5);

			//body
			w.write(jStr6);
			w.write(text);
			w.write(jStr7);

			BareBonesBrowserLaunch.openURL(htmlFile.toURI().toString());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex, "HJFormat Plugin", JOptionPane.ERROR_MESSAGE);
		} finally {
			view.hideWaitCursor();
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}

	public static void launchBrowser(String url) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URI(url));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex, "HJFormat Plugin", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("xdg-open " + url);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, ex, "HJFormat Plugin", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
