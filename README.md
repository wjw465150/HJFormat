HJFormat
========
jedit (http://www.jedit.org/) plugin,format,compress,encryption HTML and Javascrip;escape/unescape Java String statement;render Markdown to html!

**For example:**  

## 1. format html
```html
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
```  
out:  
```html
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
```
## 2. escape Java String  
```java
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
```      
out  
```
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
```
## 3. render markdown to html
```
##markdown有哪些功能？

* 方便的`导入导出`功能
    *  直接把一个markdown的文本文件拖放到当前这个页面就可以了
    *  导出为一个html格式的文件，样式一点也不会丢失
* 编辑和预览`同步滚动`，所见即所得（右上角设置）
* `VIM快捷键`支持，方便vim党们快速的操作 （右上角设置）
* 强大的`自定义CSS`功能，方便定制自己的展示
* 有数量也有质量的`主题`,编辑器和预览区域
* 完美兼容`Github`的markdown语法
* 预览区域`代码高亮`
* 所有选项自动记忆

##有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流
```
out
```
<h2 id="markdown有哪些功能？"><a name="markdown有哪些功能？" href="#markdown有哪些功能？"></a>markdown有哪些功能？</h2>
<ul>
<li>方便的<code>导入导出</code>功能<ul>
<li>直接把一个markdown的文本文件拖放到当前这个页面就可以了</li>
<li>导出为一个html格式的文件，样式一点也不会丢失</li>
</ul>
</li>
<li>编辑和预览<code>同步滚动</code>，所见即所得（右上角设置）</li>
<li><code>VIM快捷键</code>支持，方便vim党们快速的操作 （右上角设置）</li>
<li>强大的<code>自定义CSS</code>功能，方便定制自己的展示</li>
<li>有数量也有质量的<code>主题</code>,编辑器和预览区域</li>
<li>完美兼容<code>Github</code>的markdown语法</li>
<li>预览区域<code>代码高亮</code></li>
<li>所有选项自动记忆</li>
</ul>
<h2 id="有问题反馈"><a name="有问题反馈" href="#有问题反馈"></a>有问题反馈</h2>
<p>在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流</p>
```
