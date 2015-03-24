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

	private static final String jStr_fix_css = "\n" + "blockquote {\n" + "\tborder-left: 5px solid #40AA53;\n"
	    + "\tpadding: 0 2px;\n" + "\tcolor: #333;\n" + "\tbackground: #efe;\n" + "\tmargin: 2px 1px 2px 15px;\n" + "}\n"
	    + "\n" + "pre {\n" + "  margin: 0px 0px 0px 15px; \n" + "  /* background-color: #f8f8f8; */\n"
	    + "  /* border: 1px solid #cccccc; */\n" + "  font-size: 13px;\n" + "  line-height: 19px;\n"
	    + "  overflow: auto;\n" + "  padding: 0px 0px 0px 0px; \n" + "  border-radius: 3px;\n" + "}\n";

	private static final String jStr_fix_default1 = "\n" + "blockquote {\n" + "\tborder-left: 5px solid #40AA53;\n"
	    + "\tpadding: 0 2px;\n" + "\tcolor: #333;\n" + "\tbackground: #efe;\n" + "\tmargin: 2px 1px 2px 15px;\n" + "}\n"
	    + "\n" + "pre {\n" + "  margin: 0px 0px 0px 15px; \n" + "  background-color: #f6f6f6; \n"
	    + "  border: 1px solid #cccccc; \n" + "  font-size: 13px;\n" + "  line-height: 19px;\n" + "  overflow: auto;\n"
	    + "  padding: 0px 0px 0px 0px; \n" + "  border-radius: 3px;\n" + "}\n";

	private static final String jStr_fix_default2 = "\n" + ".syntaxhighlighter {\n"
	    + "  background-color: #f6f6f6 !important;\n" + "}\n" + ".syntaxhighlighter .line.alt1 {\n"
	    + "  background-color: #f6f6f6 !important;\n" + "}\n" + ".syntaxhighlighter .line.alt2 {\n"
	    + "  background-color: #f6f6f6 !important;\n" + "}";

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

	public static String JS_EVAL;

	public static String JS_TOC;
	public static String JS_shCore;

	public static String JS_MathJax = "\n<script type=\"text/x-mathjax-config\">\n"
	    + "MathJax.Hub.Config({\n"
	    + "  tex2jax: {inlineMath: [['$','$'], ['\\\\(','\\\\)']]}\n"
	    + "});\n"
	    + "</script>\n"
	    + "<script type=\"text/javascript\" src=\"http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS_HTML\"></script>\n";

	public static Map<String, String> JS_MAP = new HashMap<String, String>();
	static {
		try {
			CSS_DEFAULT = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/default.css"), CODE));

			JS_EVAL = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/marked.js"), CODE));

			JS_TOC = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/toc.js"), CODE));

			JS_shCore = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shCore.js"), CODE));

			//初始化brush的CSS
			String shBrushAppleScript = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushAppleScript.js"), CODE));
			JS_MAP.put("applescript", shBrushAppleScript);

			String shBrushAS3 = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushAS3.js"), CODE));
			JS_MAP.put("actionscript3", shBrushAS3);
			JS_MAP.put("as3", shBrushAS3);

			String shBrushBash = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushBash.js"), CODE));
			JS_MAP.put("bash", shBrushBash);
			JS_MAP.put("shell", shBrushBash);

			String shBrushColdFusion = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushColdFusion.js"), CODE));
			JS_MAP.put("coldfusion", shBrushColdFusion);
			JS_MAP.put("cf", shBrushColdFusion);

			String shBrushCpp = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushCpp.js"), CODE));
			JS_MAP.put("cpp", shBrushCpp);
			JS_MAP.put("c", shBrushCpp);

			String shBrushCSharp = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushCSharp.js"), CODE));
			JS_MAP.put("c#", shBrushCSharp);
			JS_MAP.put("c-sharp", shBrushCSharp);
			JS_MAP.put("csharp", shBrushCSharp);

			String shBrushCss = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushCss.js"), CODE));
			JS_MAP.put("css", shBrushCss);

			String shBrushDelphi = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushDelphi.js"), CODE));
			JS_MAP.put("delphi", shBrushDelphi);
			JS_MAP.put("pascal", shBrushDelphi);
			JS_MAP.put("pas", shBrushDelphi);

			String shBrushDiff = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushDiff.js"), CODE));
			JS_MAP.put("diff", shBrushDiff);
			JS_MAP.put("patch", shBrushDiff);

			String shBrushErlang = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushErlang.js"), CODE));
			JS_MAP.put("erl", shBrushErlang);
			JS_MAP.put("erlang", shBrushErlang);

			String shBrushGroovy = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushGroovy.js"), CODE));
			JS_MAP.put("groovy", shBrushGroovy);

			String shBrushJava = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushJava.js"), CODE));
			JS_MAP.put("java", shBrushJava);
			JS_MAP.put("jfx", shBrushJava);
			JS_MAP.put("javafx", shBrushJava);

			String shBrushJScript = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushJScript.js"), CODE));
			JS_MAP.put("js", shBrushJScript);
			JS_MAP.put("jscript", shBrushJScript);
			JS_MAP.put("javascript", shBrushJScript);

			String shBrushPerl = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushPerl.js"), CODE));
			JS_MAP.put("perl", shBrushPerl);
			JS_MAP.put("Perl", shBrushPerl);
			JS_MAP.put("pl", shBrushPerl);

			String shBrushPhp = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushPhp.js"), CODE));
			JS_MAP.put("php", shBrushPhp);

			String shBrushPlain = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushPlain.js"), CODE));
			JS_MAP.put("text", shBrushPlain);
			JS_MAP.put("plain", shBrushPlain);

			String shBrushPowerShell = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushPowerShell.js"), CODE));
			JS_MAP.put("powershell", shBrushPowerShell);
			JS_MAP.put("ps", shBrushPowerShell);

			String shBrushPython = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushPython.js"), CODE));
			JS_MAP.put("py", shBrushPython);
			JS_MAP.put("python", shBrushPython);

			String shBrushRuby = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushRuby.js"), CODE));
			JS_MAP.put("ruby", shBrushRuby);
			JS_MAP.put("rails", shBrushRuby);
			JS_MAP.put("ror", shBrushRuby);
			JS_MAP.put("rb", shBrushRuby);

			String shBrushSass = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushSass.js"), CODE));
			JS_MAP.put("sass", shBrushSass);
			JS_MAP.put("scss", shBrushSass);

			String shBrushScala = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushScala.js"), CODE));
			JS_MAP.put("scala", shBrushScala);

			String shBrushSql = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushSql.js"), CODE));
			JS_MAP.put("sql", shBrushSql);

			String shBrushVb = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushVb.js"), CODE));
			JS_MAP.put("vb", shBrushVb);
			JS_MAP.put("vbnet", shBrushVb);

			String shBrushXml = String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/shBrushXml.js"), CODE));
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

			String jsStr = JS_EVAL + " marked(___text);";

			// 执行js
			Object result = cx.evaluateString(scope, jsStr, null, 1, null);

			return Context.toString(result);
		} catch (Throwable thex) {
			JOptionPaneExt.showMessageDialog(null, thex.getMessage(), "HJFormat Plugin", JOptionPaneExt.WARNING_MESSAGE);
			return thex.toString();
		} finally {
			Context.exit();
		}
	}

	private static void prettifyHtml(final String cssName, final Writer writer, final String name, final String text)
	    throws Exception {
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
			if (attr.startsWith("lang-")) { //去掉"lang-"前缀
				attr = attr.substring(5);
			}
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
		writer.append(String.valueOf(StreamUtil.readChars(JsUtil.class.getResourceAsStream("/js/sh/" + cssName + ".css"), CODE)));
		if (cssName.toLowerCase().contains("default")) {
			writer.append(jStr_fix_default1);
			writer.append(jStr_fix_default2);
		} else {
			writer.append(jStr_fix_css);
		}
		writer.append(jStr3);

		//JS
		if (hasTeX(text) > -1) {
			writer.append(JS_MathJax);
		}

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

	public static void saveToBuffer(final String cssName, final View view, final Buffer buffer, final String text) {
		view.showWaitCursor();
		try {
			final Buffer htmlBuffer = jEdit.newFile(view);

			StringWriter writer = new StringWriter(text.length() * 2);
			prettifyHtml(cssName, writer, buffer.getName(), text);

			htmlBuffer.insert(0, writer.toString());

			htmlBuffer.setMode(MODE);
			cpoyBufferProperties(buffer, htmlBuffer);
			htmlBuffer.propertiesChanged();

			view.setBuffer(htmlBuffer);
			view.setUserTitle(buffer.getName() + ".html");
			view.updateTitle();
		} catch (Throwable ex) {
			JOptionPaneExt.showMessageDialog(null, ex, "HJFormat Plugin", JOptionPaneExt.ERROR_MESSAGE);
		} finally {
			view.hideWaitCursor();
		}
	}

	public static void saveToFile(final String cssName, final View view, final Buffer buffer, final String text) {
		if (buffer.isUntitled()) {
			JOptionPaneExt.showMessageDialog(null, "Buffer first must saved!", "HJFormat Plugin", JOptionPaneExt.WARNING_MESSAGE);
			return;
		}
		File htmlFile = new File(buffer.getPath() + ".html");

		BufferedWriter writer = null;
		view.showWaitCursor();
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), CODE));
			prettifyHtml(cssName, writer, buffer.getName(), text);
			writer.close();
			writer = null;

			BareBonesBrowserLaunch.openURL(htmlFile.toURI().toString());
		} catch (Throwable ex) {
			JOptionPaneExt.showMessageDialog(null, ex, "HJFormat Plugin", JOptionPaneExt.ERROR_MESSAGE);
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
				JOptionPaneExt.showMessageDialog(null, ex, "HJFormat Plugin", JOptionPaneExt.ERROR_MESSAGE);
			}
		} else {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("xdg-open " + url);
			} catch (IOException ex) {
				JOptionPaneExt.showMessageDialog(null, ex, "HJFormat Plugin", JOptionPaneExt.ERROR_MESSAGE);
			}
		}
	}

	public static int hasTeX(String txt) {
		int pos = posTeX_A(txt);
		if (pos > 0) {
			return pos;
		}
		pos = posTeX_B(txt);
		if (pos > 0) {
			return pos;
		}

		return -1;
	}

	private static int posTeX_A(String txt) {
		int pos = txt.indexOf("$$");
		if (pos >= 0) {
			pos = txt.indexOf("$$", pos + 3);
		}
		return pos;
	}

	private static int posTeX_B(String txt) {
		int pos = txt.indexOf("\\\\(");
		if (pos >= 0) {
			pos = txt.indexOf("\\\\)", pos + 4);
		}
		return pos;
	}

}
