package wjw.jedit.plugin;

import jodd.util.CharUtil;

import org.mozilla.javascript.*;
import org.mozilla.javascript.tools.shell.Global;
import org.mozilla.javascript.tools.shell.Main;

public abstract class HJFormatBeautifier {
	public static boolean startsWithIgnoreWhitespace(String src, String subS) {
		int len = src.length();
		int st = 0;
		while ((st < len) && (CharUtil.isWhitespace(src.charAt(st)))) {
			st++;
		}
		return src.indexOf(subS, st) == st;
	}

	public static String format(String text) {
		Context cx = Context.enter();

		try {
			cx.setOptimizationLevel(-1);
			cx.setLanguageVersion(Context.VERSION_1_5);

			Global global = new Global(cx);
			Scriptable scope = cx.initStandardObjects(global /* 关键 */);

			//设置scope属性
			scope.put("___text", scope, text);

			String jsStr;
			if (startsWithIgnoreWhitespace(text,"<")) {
				jsStr = HJFormatPlugin.ALL_JS + " style_html(___text, " + 2 + ", ' ', " + 120 + ");";
			} else {
				jsStr = HJFormatPlugin.ALL_JS + " js_beautify(___text, " + 2 + ", ' ');";
			}

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

	public static String compress(String text) {
		Context cx = Context.enter();

		try {
			cx.setOptimizationLevel(-1);
			cx.setLanguageVersion(Context.VERSION_1_5);

			Global global = new Global(cx);
			Scriptable scope = cx.initStandardObjects(global /* 关键 */);

			//设置scope属性
			scope.put("___text", scope, text);

			String jsStr = HJFormatPlugin.ALL_JS + " var packer = new Packer; packer.pack(___text, 0, 0);";

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

	public static String encryption(String text) {
		Context cx = Context.enter();

		try {
			cx.setOptimizationLevel(-1);
			cx.setLanguageVersion(Context.VERSION_1_5);

			Global global = new Global(cx);
			Scriptable scope = cx.initStandardObjects(global /* 关键 */);

			//设置scope属性
			scope.put("___text", scope, text);

			String jsStr = HJFormatPlugin.ALL_JS + " var packer = new Packer; packer.pack(___text, 1, 0);";

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
}
