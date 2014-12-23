package wjw.jedit.plugin;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import jodd.io.StreamUtil;
import jodd.jerry.Jerry;
import jodd.lagarto.dom.Node;

import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.jEdit;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;

public abstract class JsUtil {
	private static final String MODE = "html";
	private static final String CODE = "UTF-8";

	private static final String jStr1 = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n"
	    + "<html>\n" + "<head>\n" + "<title>";
	private static final String jStr2 = "</title>\n"
	    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" + "<style>\n<!--\n";
	private static final String jStr3 = "\n-->\n</style>\n";
	private static final String jStr4 = "\n<script language=\"JavaScript\" type=\"text/javascript\">\n<!--\n";
	private static final String jStr5 = "\n-->\n</script>\n";
	private static final String jStr6 = "\n</head>\n";
	private static final String jStr7 = "</html>\n";

	public static String CSS_DEFAULT;
	public static String CSS_shCoreDefault;

	public static String JS_MARKDOWN_CONVERTER;
	public static String JS_MARKDOWN_EXTRA;
	public static String JS_EVAL;

	public static String JS_TOC;
	public static String JS_shCore;

	public static Map<String, String> JS_MAP = new HashMap<String, String>();
	static {
		try {
			CSS_DEFAULT = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/default.css"), "GBK"));
			CSS_shCoreDefault = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shCoreDefault.css"), CODE));

			JS_MARKDOWN_CONVERTER = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/Markdown.Converter.js"), CODE));
			JS_MARKDOWN_EXTRA = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/Markdown.Extra.js"), CODE));
			JS_EVAL = JS_MARKDOWN_CONVERTER + "\r\n" + JS_MARKDOWN_EXTRA + "\r\n";

			JS_TOC = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/toc.js"), CODE));
			JS_TOC = HJFormatBeautifier.compress(JS_TOC);

			JS_shCore = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shCore.js"), CODE));
			JS_shCore = HJFormatBeautifier.compress(JS_shCore);

			//初始化brush的CSS
			String shBrushAppleScript = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushAppleScript.js"), CODE)));
			JS_MAP.put("applescript", shBrushAppleScript);

			String shBrushAS3 = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushAS3.js"), CODE)));
			JS_MAP.put("actionscript3", shBrushAS3);
			JS_MAP.put("as3", shBrushAS3);

			String shBrushBash = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushBash.js"), CODE)));
			JS_MAP.put("bash", shBrushBash);
			JS_MAP.put("shell", shBrushBash);

			String shBrushColdFusion = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushColdFusion.js"), CODE)));
			JS_MAP.put("coldfusion", shBrushColdFusion);
			JS_MAP.put("cf", shBrushColdFusion);

			String shBrushCpp = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushCpp.js"), CODE)));
			JS_MAP.put("cpp", shBrushCpp);
			JS_MAP.put("c", shBrushCpp);

			String shBrushCSharp = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushCSharp.js"), CODE)));
			JS_MAP.put("c#", shBrushCSharp);
			JS_MAP.put("c-sharp", shBrushCSharp);
			JS_MAP.put("csharp", shBrushCSharp);

			String shBrushCss = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushCss.js"), CODE)));
			JS_MAP.put("css", shBrushCss);

			String shBrushDelphi = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushDelphi.js"), CODE)));
			JS_MAP.put("delphi", shBrushDelphi);
			JS_MAP.put("pascal", shBrushDelphi);
			JS_MAP.put("pas", shBrushDelphi);

			String shBrushDiff = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushDiff.js"), CODE)));
			JS_MAP.put("diff", shBrushDiff);
			JS_MAP.put("patch", shBrushDiff);

			String shBrushErlang = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushErlang.js"), CODE)));
			JS_MAP.put("erl", shBrushErlang);
			JS_MAP.put("erlang", shBrushErlang);

			String shBrushGroovy = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushGroovy.js"), CODE)));
			JS_MAP.put("groovy", shBrushGroovy);

			String shBrushJava = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushJava.js"), CODE)));
			JS_MAP.put("java", shBrushJava);
			JS_MAP.put("jfx", shBrushJava);
			JS_MAP.put("javafx", shBrushJava);

			String shBrushJScript = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushJScript.js"), CODE)));
			JS_MAP.put("js", shBrushJScript);
			JS_MAP.put("jscript", shBrushJScript);
			JS_MAP.put("javascript", shBrushJScript);

			String shBrushPerl = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushPerl.js"), CODE)));
			JS_MAP.put("perl", shBrushPerl);
			JS_MAP.put("Perl", shBrushPerl);
			JS_MAP.put("pl", shBrushPerl);

			String shBrushPhp = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushPhp.js"), CODE)));
			JS_MAP.put("php", shBrushPhp);

			String shBrushPlain = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushPlain.js"), CODE)));
			JS_MAP.put("text", shBrushPlain);
			JS_MAP.put("plain", shBrushPlain);

			String shBrushPowerShell = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushPowerShell.js"), CODE)));
			JS_MAP.put("powershell", shBrushPowerShell);
			JS_MAP.put("ps", shBrushPowerShell);

			String shBrushPython = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushPython.js"), CODE)));
			JS_MAP.put("py", shBrushPython);
			JS_MAP.put("python", shBrushPython);

			String shBrushRuby = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushRuby.js"), CODE)));
			JS_MAP.put("ruby", shBrushRuby);
			JS_MAP.put("rails", shBrushRuby);
			JS_MAP.put("ror", shBrushRuby);
			JS_MAP.put("rb", shBrushRuby);

			String shBrushSass = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushSass.js"), CODE)));
			JS_MAP.put("sass", shBrushSass);
			JS_MAP.put("scss", shBrushSass);

			String shBrushScala = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushScala.js"), CODE)));
			JS_MAP.put("scala", shBrushScala);

			String shBrushSql = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushSql.js"), CODE)));
			JS_MAP.put("sql", shBrushSql);

			String shBrushVb = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushVb.js"), CODE)));
			JS_MAP.put("vb", shBrushVb);
			JS_MAP.put("vbnet", shBrushVb);

			String shBrushXml = HJFormatBeautifier.compress(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushXml.js"), CODE)));
			JS_MAP.put("xml", shBrushXml);
			JS_MAP.put("xhtml", shBrushXml);
			JS_MAP.put("xslt", shBrushXml);
			JS_MAP.put("html", shBrushXml);
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

			String jsStr = JS_EVAL
			    + " var converter=new Markdown.Converter();  Markdown.Extra.init(converter); converter.makeHtml(___text);";

			// 执行js
			Object result = cx.evaluateString(scope, jsStr, null, 1, null);

			return Context.toString(result);
		} catch (Throwable thex) {
			JOptionPane.showMessageDialog(null, thex.getMessage(), "HJFormat Plugin", JOptionPane.WARNING_MESSAGE);
			return thex.toString();
		} finally {
			Context.exit();
		}
	}

	private static void prettifyHtml(final Writer writer, final String name, final String text) throws Exception {
		//add class
		Jerry doc = Jerry.jerry("\n<body>\n<div id='generated-toc'></div>\n" + text + "\n</body>\n");
		Node[] roots = doc.$("pre>code").get();
		String attr;
		List<String> brushs = new ArrayList<String>();
		for (Node child : roots) {
			attr = child.getAttribute("class");
			if (attr == null) {
				attr = "text";
			}
			attr = attr.toLowerCase();
			if (JS_MAP.get(attr) == null) {
				attr = "text";
			}
			if (brushs.contains(attr) == false) {
				brushs.add(attr);
			}
			child.setAttribute("class", "brush: " + attr);
		}

		//Title
		writer.append(jStr1);
		writer.append(name);

		//CSS
		writer.append(jStr2);
		writer.append(CSS_DEFAULT);
		writer.append("\n\n");
		writer.append(CSS_shCoreDefault);
		writer.append(jStr3);

		//JS
		writer.append(jStr4);
		writer.append(JS_TOC);
		writer.append(jStr5);

		writer.append(jStr4);
		writer.append(JS_shCore);
		writer.append(jStr5);
		for (String brush : brushs) {
			writer.append(jStr4);
			writer.append(JS_MAP.get(brush));

			writer.append(jStr5);
		}
		writer.append("\n<script type=\"text/javascript\">\n  SyntaxHighlighter.config.tagName='code';\n  SyntaxHighlighter.defaults['toolbar']=false;\n  SyntaxHighlighter.all();\n</script>\n");

		//body
		writer.append(jStr6);
		writer.append(doc.html());
		writer.append(jStr7);

	}

	public static void saveToBuffer(final View view, final Buffer buffer, final String text) {
		view.showWaitCursor();
		try {
			final Buffer htmlBuffer = jEdit.newFile(view);

			StringWriter writer = new StringWriter(text.length() * 2);
			prettifyHtml(writer, buffer.getName(), text);

			htmlBuffer.insert(0, writer.toString());

			htmlBuffer.setMode(MODE);
			cpoyBufferProperties(buffer, htmlBuffer);
			htmlBuffer.propertiesChanged();

			view.setBuffer(htmlBuffer);
			view.setUserTitle(buffer.getName() + ".html");
			view.updateTitle();
		} catch (Throwable ex) {
			JOptionPane.showMessageDialog(null, ex, "HJFormat Plugin", JOptionPane.ERROR_MESSAGE);
		} finally {
			view.hideWaitCursor();
		}
	}

	public static void saveToFile(final View view, final Buffer buffer, final String text) {
		if (buffer.isUntitled()) {
			JOptionPane.showMessageDialog(null, "Buffer first must saved!", "HJFormat Plugin", JOptionPane.WARNING_MESSAGE);
			return;
		}
		File htmlFile = new File(buffer.getPath() + ".html");

		BufferedWriter writer = null;
		view.showWaitCursor();
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), CODE));
			prettifyHtml(writer, buffer.getName(), text);
			writer.close();
			writer = null;

			BareBonesBrowserLaunch.openURL(htmlFile.toURI().toString());
		} catch (Throwable ex) {
			JOptionPane.showMessageDialog(null, ex, "HJFormat Plugin", JOptionPane.ERROR_MESSAGE);
		} finally {
			view.hideWaitCursor();
			if (writer != null) {
				try {
					writer.close();
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
