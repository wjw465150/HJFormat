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
		String jStr = "    int lineCount = textArea.getLineCount();\n"
		    + "    StringBuilder sb = new StringBuilder(buffer.getLength()); \n"
		    + "    sb.append(\"String jStr = \\\"\");\n" + "    for(int i=0;i<lineCount;i++) {\n" + "      if(i>0) {\n"
		    + "        sb.append(\"+\\\"\");\n" + "      }\n"
		    + "      sb.append(StringUtil.escapeJava(textArea.getLineText(i)));\n" + "      sb.append(\"\\\"\\n\");\n"
		    + "    }\n" + "";
		System.out.print(jStr);
	}
}
