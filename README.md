<html>
<head>
<title>README.md</title>
</head>
<body bgcolor="#ffffff">



<pre><font color="#000000"><span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">   1 </font></span>HJFormat
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">   2 </font></span>========
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">   3 </font></span>
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">   4 </font></span>jedit (http://www.jedit.org/) plugin,format,compress,encryption HTML and Javascrip;escape/unescape Java String statement.
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">   5 </font></span>
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">   6 </font></span>For example:
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">   7 </font></span>
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">   8 </font></span>1) format html
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">   9 </font></span>&lt;html&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  10 </font></span>&lt;head&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  11 </font></span> &lt;title&gt; New Document &lt;/title&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  12 </font></span>&lt;/head&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  13 </font></span> &lt;body&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  14 </font></span>  &lt;table&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  15 </font></span>  &lt;tr&gt;    &lt;td&gt;name&lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  16 </font></span>    &lt;td&gt;${data.name}&lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  17 </font></span>  &lt;/tr&gt;  &lt;tr&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  18 </font></span>    &lt;td&gt;val&lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  19 </font></span>    &lt;td&gt;${data.val}&lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  20 </font></span>  &lt;/tr&gt;  &lt;tpl if=&quot;'${data.name}'=='wjw'&quot;&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  21 </font></span>  &lt;tr&gt;    &lt;td&gt;123&lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  22 </font></span>    &lt;td&gt;456&lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  23 </font></span>  &lt;/tr&gt;  &lt;/tpl&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  24 </font></span>  &lt;/table&gt; &lt;/body&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  25 </font></span>&lt;/html&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  26 </font></span>======formated======
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  27 </font></span>&lt;html&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  28 </font></span>  &lt;head&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  29 </font></span>    &lt;title&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  30 </font></span>      New Document
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  31 </font></span>    &lt;/title&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  32 </font></span>  &lt;/head&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  33 </font></span>  &lt;body&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  34 </font></span>    &lt;table&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  35 </font></span>      &lt;tr&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  36 </font></span>        &lt;td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  37 </font></span>          name
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  38 </font></span>        &lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  39 </font></span>        &lt;td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  40 </font></span>          ${data.name}
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  41 </font></span>        &lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  42 </font></span>      &lt;/tr&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  43 </font></span>      &lt;tr&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  44 </font></span>        &lt;td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  45 </font></span>          val
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  46 </font></span>        &lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  47 </font></span>        &lt;td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  48 </font></span>          ${data.val}
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  49 </font></span>        &lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  50 </font></span>      &lt;/tr&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  51 </font></span>      &lt;tpl if= &quot;'${data.name}'=='wjw'&quot;&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  52 </font></span>        &lt;tr&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  53 </font></span>          &lt;td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  54 </font></span>            123
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  55 </font></span>          &lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  56 </font></span>          &lt;td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  57 </font></span>            456
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  58 </font></span>          &lt;/td&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  59 </font></span>        &lt;/tr&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  60 </font></span>      &lt;/tpl&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  61 </font></span>    &lt;/table&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  62 </font></span>  &lt;/body&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  63 </font></span>&lt;/html&gt;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  64 </font></span>2)escape Java String
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  65 </font></span>    int lineCount = textArea.getLineCount();
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  66 </font></span>    StringBuilder sb = new StringBuilder(buffer.getLength()); 
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  67 </font></span>    sb.append(&quot;String jStr = \&quot;&quot;);
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  68 </font></span>    for(int i=0;i&lt;lineCount;i++) {
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  69 </font></span>      if(i&gt;0) {
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  70 </font></span>        sb.append(&quot;+\&quot;&quot;);
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  71 </font></span>      }
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  72 </font></span>      sb.append(StringUtil.escapeJava(textArea.getLineText(i)));
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  73 </font></span>      sb.append(&quot;\&quot;\n&quot;);
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  74 </font></span>    }
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  75 </font></span>======formated======
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  76 </font></span>String jStr = &quot;    int lineCount = textArea.getLineCount();\n&quot;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  77 </font></span>+&quot;    StringBuilder sb = new StringBuilder(buffer.getLength()); \n&quot;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  78 </font></span>+&quot;    sb.append(\&quot;String jStr = \\\&quot;\&quot;);\n&quot;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  79 </font></span>+&quot;    for(int i=0;i&lt;lineCount;i++) {\n&quot;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  80 </font></span>+&quot;      if(i&gt;0) {\n&quot;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  81 </font></span>+&quot;        sb.append(\&quot;+\\\&quot;\&quot;);\n&quot;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  82 </font></span>+&quot;      }\n&quot;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  83 </font></span>+&quot;      sb.append(StringUtil.escapeJava(textArea.getLineText(i)));\n&quot;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  84 </font></span>+&quot;      sb.append(\&quot;\\\&quot;\\n\&quot;);\n&quot;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#990066">  85 </font></span>+&quot;    }\n&quot;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  86 </font></span>+&quot;&quot;;
<span style="background:#e9e9e9; border-right:solid 2px black; margin-right:5px; "><font color="#000000">  87 </font></span>
</font></pre>
</body>
</html>

