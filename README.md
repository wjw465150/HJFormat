HJFormat
========

jedit plugin,format,compress,encryption HTML and Javascrip;escape/unescape Java String statement.

For example:

1) format html
<html>
<head>
 <title> New Document </title>
</head>
 <body>
  <table>
  <tr>    <td>name</td>
    <td>${data.name}</td>
  </tr>  <tr>
    <td>val</td>
    <td>${data.val}</td>
  </tr>  <tpl if="'${data.name}'=='wjw'">
  <tr>    <td>123</td>
    <td>456</td>
  </tr>  </tpl>
  </table> </body>
</html>
======formated======
<html>
  <head>
    <title>
      New Document
    </title>
  </head>
  <body>
    <table>
      <tr>
        <td>
          name
        </td>
        <td>
          ${data.name}
        </td>
      </tr>
      <tr>
        <td>
          val
        </td>
        <td>
          ${data.val}
        </td>
      </tr>
      <tpl if= "'${data.name}'=='wjw'">
        <tr>
          <td>
            123
          </td>
          <td>
            456
          </td>
        </tr>
      </tpl>
    </table>
  </body>
</html>
2)escape Java String
    int lineCount = textArea.getLineCount();
    StringBuilder sb = new StringBuilder(buffer.getLength()); 
    sb.append("String jStr = \"");
    for(int i=0;i<lineCount;i++) {
      if(i>0) {
        sb.append("+\"");
      }
      sb.append(StringUtil.escapeJava(textArea.getLineText(i)));
      sb.append("\"\n");
    }
======formated======
String jStr = "    int lineCount = textArea.getLineCount();\n"
+"    StringBuilder sb = new StringBuilder(buffer.getLength()); \n"
+"    sb.append(\"String jStr = \\\"\");\n"
+"    for(int i=0;i<lineCount;i++) {\n"
+"      if(i>0) {\n"
+"        sb.append(\"+\\\"\");\n"
+"      }\n"
+"      sb.append(StringUtil.escapeJava(textArea.getLineText(i)));\n"
+"      sb.append(\"\\\"\\n\");\n"
+"    }\n"
+"";

