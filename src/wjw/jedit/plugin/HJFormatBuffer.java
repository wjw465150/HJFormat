package wjw.jedit.plugin;

import jodd.util.StringUtil;

import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.EBMessage;
import org.gjt.sp.jedit.EditBus;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.Mode;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.msg.BufferUpdate;
import org.gjt.sp.jedit.textarea.JEditTextArea;

import org.gjt.sp.util.Log;

public class HJFormatBuffer {
	private static final String[] ChineseInterpunction = { "¡°", "¡±", "¡®", "¡¯", "¡£", "£¬", "£»", "£º", "£¿", "£¡", "¡­¡­", "¡ª",
	    "¡«", "£¨", "£©", "¡¶", "¡·", "¨”", "¨•" };
	private static final String[] EnglishInterpunction = { "\"", "\"", "'", "'", ".", ",", ";", ":", "?", "!", "¡­", "-",
	    "~", "(", ")", "<", ">", "\"", "\"" };
	private static final java.util.Map<String, String> mapC2E = new java.util.HashMap<String, String>(ChineseInterpunction.length);
	private static final java.util.Map<String, String> mapE2C = new java.util.HashMap<String, String>(ChineseInterpunction.length);
	static {
		for (int i = 0; i < ChineseInterpunction.length; i++) {
			mapC2E.put(ChineseInterpunction[i], EnglishInterpunction[i]);
			mapE2C.put(EnglishInterpunction[i], ChineseInterpunction[i]);
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

	public static void formatBuffer(View view) {

		Buffer buffer = view.getBuffer();
		Mode mode = buffer.getMode();

		// Setting tidy input
		JEditTextArea textArea = view.getTextArea();
		String text = textArea.getText();

		// Setting tidy standard output
		boolean new_buffer = jEdit.getBooleanProperty("HJFormat.new-buffer", false);

		text = HJFormatBeautifier.format(text);

		if (!new_buffer) {
			HJFormatBuffer.setBufferText(buffer, text);
			return;
		}

		// Create a new buffer and put the tidied content
		Buffer newBuffer = jEdit.newFile(view);

		if (newBuffer == null) {
			new WaitForBuffer(mode, text);
		} else {
			newBuffer.setMode(mode);

			cpoyBufferProperties(buffer, newBuffer);
			newBuffer.propertiesChanged();

			HJFormatBuffer.setBufferText(newBuffer, text);
		}

	}

	public static void compressBuffer(View view) {

		Buffer buffer = view.getBuffer();
		Mode mode = buffer.getMode();

		// Setting tidy input
		JEditTextArea textArea = view.getTextArea();
		String text = textArea.getText();

		text = HJFormatBeautifier.compress(text);

		// Create a new buffer and put the tidied content
		Buffer newBuffer = jEdit.newFile(view);

		if (newBuffer == null) {
			new WaitForBuffer(mode, text);
		} else {
			newBuffer.setMode(mode);

			cpoyBufferProperties(buffer, newBuffer);
			newBuffer.setProperty("wrap", "soft");
			newBuffer.propertiesChanged();

			HJFormatBuffer.setBufferText(newBuffer, text);
		}

	}

	public static void encryptionBuffer(View view) {

		Buffer buffer = view.getBuffer();
		Mode mode = buffer.getMode();

		// Setting tidy input
		JEditTextArea textArea = view.getTextArea();
		String text = textArea.getText();

		text = HJFormatBeautifier.encryption(text);

		// Create a new buffer and put the tidied content
		Buffer newBuffer = jEdit.newFile(view);

		if (newBuffer == null) {
			new WaitForBuffer(mode, text);
		} else {
			newBuffer.setMode(mode);

			cpoyBufferProperties(buffer, newBuffer);
			newBuffer.setProperty("wrap", "soft");
			newBuffer.propertiesChanged();

			HJFormatBuffer.setBufferText(newBuffer, text);
		}

	}

	public static void wrapJavaStringBuffer(View view) {
		Buffer buffer = view.getBuffer();
		Mode mode = buffer.getMode();

		JEditTextArea textArea = view.getTextArea();
		StringBuilder sb = new StringBuilder(buffer.getLength());

		String lineText = null;
		int lineCount = textArea.getLineCount();
		if (lineCount == 1) {
			lineText = textArea.getLineText(0);
			sb.append("String jStr = \"");
			sb.append(StringUtil.escapeJava(lineText));
			sb.append("\";");
		} else {
			sb.append("String jStr = \"");
			for (int i = 0; i < lineCount; i++) {
				lineText = textArea.getLineText(i);
				sb.append(StringUtil.escapeJava(lineText));
				if (i == (lineCount - 1)) {
					sb.append("\"");
				} else {
					sb.append("\\n\"+\n\"");
				}
			}
			sb.append(";");
		}

		String text = sb.toString();

		// Setting tidy standard output
		boolean new_buffer = jEdit.getBooleanProperty("HJFormat.new-buffer", false);

		if (!new_buffer) {
			HJFormatBuffer.setBufferText(buffer, text);
			return;
		}

		// Create a new buffer and put the tidied content
		Buffer newBuffer = jEdit.newFile(view);

		if (newBuffer == null) {
			new WaitForBuffer(mode, text);
		} else {
			newBuffer.setMode(mode);

			cpoyBufferProperties(buffer, newBuffer);
			newBuffer.setProperty("defaultMode", "java");
			newBuffer.propertiesChanged();

			HJFormatBuffer.setBufferText(newBuffer, text);
		}

	}

	public static void unwrapJavaStringBuffer(View view) {
		Buffer buffer = view.getBuffer();
		Mode mode = buffer.getMode();

		JEditTextArea textArea = view.getTextArea();
		int lineCount = textArea.getLineCount();
		StringBuilder sb = new StringBuilder(buffer.getLength());
		String lineText = null;
		int firstPos;
		int lastPos;
		for (int i = 0; i < lineCount; i++) {
			lineText = textArea.getLineText(i);
			firstPos = lineText.indexOf('"');
			lastPos = lineText.lastIndexOf('"');
			if (firstPos == -1 || lastPos == -1 || firstPos == lastPos) {
				sb.append(lineText);
				sb.append("\n");
			} else if (firstPos != lastPos) {
				lineText = lineText.substring(firstPos + 1, lastPos);
				sb.append(StringUtil.unescapeJava(lineText));
			}
		}

		String text = sb.toString();

		// Setting tidy standard output
		boolean new_buffer = jEdit.getBooleanProperty("HJFormat.new-buffer", false);

		if (!new_buffer) {
			HJFormatBuffer.setBufferText(buffer, text);
			return;
		}

		// Create a new buffer and put the tidied content
		Buffer newBuffer = jEdit.newFile(view);

		if (newBuffer == null) {
			new WaitForBuffer(mode, text);
		} else {
			newBuffer.setMode(mode);

			cpoyBufferProperties(buffer, newBuffer);
			newBuffer.setProperty("defaultMode", "java");
			newBuffer.propertiesChanged();

			HJFormatBuffer.setBufferText(newBuffer, text);
		}

	}

	public static void C2E(View view) {
		Buffer buffer = view.getBuffer();
		Mode mode = buffer.getMode();

		JEditTextArea textArea = view.getTextArea();
		String text = textArea.getText();
		char[] cc = text.toCharArray();
		int len = cc.length;
		String key;
		for (int i = 0; i < len; i++) {
			key = String.valueOf(cc[i]);
			if (mapC2E.containsKey(key)) {
				cc[i] = mapC2E.get(key).charAt(0);
			}
		}
		text = String.valueOf(cc);

		// Setting tidy standard output
		boolean new_buffer = jEdit.getBooleanProperty("HJFormat.new-buffer", false);

		if (!new_buffer) {
			HJFormatBuffer.setBufferText(buffer, text);
			return;
		}

		// Create a new buffer and put the tidied content
		Buffer newBuffer = jEdit.newFile(view);

		if (newBuffer == null) {
			new WaitForBuffer(mode, text);
		} else {
			newBuffer.setMode(mode);

			cpoyBufferProperties(buffer, newBuffer);
			newBuffer.propertiesChanged();

			HJFormatBuffer.setBufferText(newBuffer, text);
		}

	}

	public static void E2C(View view) {
		Buffer buffer = view.getBuffer();
		Mode mode = buffer.getMode();

		JEditTextArea textArea = view.getTextArea();
		String text = textArea.getText();
		char[] cc = text.toCharArray();
		int len = cc.length;
		String key;
		for (int i = 0; i < len; i++) {
			key = String.valueOf(cc[i]);
			if (mapE2C.containsKey(key)) {
				cc[i] = mapE2C.get(key).charAt(0);
			}
		}
		text = String.valueOf(cc);

		// Setting tidy standard output
		boolean new_buffer = jEdit.getBooleanProperty("HJFormat.new-buffer", false);

		if (!new_buffer) {
			HJFormatBuffer.setBufferText(buffer, text);
			return;
		}

		// Create a new buffer and put the tidied content
		Buffer newBuffer = jEdit.newFile(view);

		if (newBuffer == null) {
			new WaitForBuffer(mode, text);
		} else {
			newBuffer.setMode(mode);

			cpoyBufferProperties(buffer, newBuffer);
			newBuffer.propertiesChanged();

			HJFormatBuffer.setBufferText(newBuffer, text);
		}

	}

	private static void setBufferText(Buffer buffer, String text) {
		try {
			buffer.beginCompoundEdit();
			buffer.remove(0, buffer.getLength());
			buffer.insert(0, text);
		} finally {
			buffer.endCompoundEdit();
		}
	}

	private static class WaitForBuffer implements EBComponent {
		private Mode mode;
		private String text;

		public WaitForBuffer(Mode mode, String text) {
			this.mode = mode;
			this.text = text;

			EditBus.addToBus(this);
		}

		public void handleMessage(EBMessage message) {
			if (message instanceof BufferUpdate) {
				BufferUpdate bu = (BufferUpdate) message;
				if (bu.getWhat() == BufferUpdate.CREATED) {
					EditBus.removeFromBus(this);

					Buffer buffer = bu.getBuffer();
					Log.log(Log.DEBUG, this, "**** Buffer CREATED new file? [" + buffer.isNewFile() + "]");
					Log.log(Log.DEBUG, this, "**** Buffer CREATED length:   [" + buffer.getLength() + "]");
					buffer.setMode(this.mode);
					HJFormatBuffer.setBufferText(buffer, this.text);
				}
			}
		}
	}

	public static void main(String[] args) {
		String jStr = "£¿,£¡";
		char[] cc = jStr.toCharArray();
		int len = cc.length;
		for (int i = 0; i < len; i++) {
			if (mapC2E.containsKey(String.valueOf(cc[i]))) {
				cc[i] = mapC2E.get(String.valueOf(cc[i])).charAt(0);
			}
		}
		jStr = String.valueOf(cc);

		System.out.print(jStr);
	}
}
