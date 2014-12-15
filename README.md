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
##markdown����Щ���ܣ�

* �����`���뵼��`����
    *  ֱ�Ӱ�һ��markdown���ı��ļ��Ϸŵ���ǰ���ҳ��Ϳ�����
    *  ����Ϊһ��html��ʽ���ļ�����ʽһ��Ҳ���ᶪʧ
* �༭��Ԥ��`ͬ������`�����������ã����Ͻ����ã�
* `VIM��ݼ�`֧�֣�����vim���ǿ��ٵĲ��� �����Ͻ����ã�
* ǿ���`�Զ���CSS`���ܣ����㶨���Լ���չʾ
* ������Ҳ��������`����`,�༭����Ԥ������
* ��������`Github`��markdown�﷨
* Ԥ������`�������`
* ����ѡ���Զ�����

##�����ⷴ��
��ʹ�������κ����⣬��ӭ�������ң�������������ϵ��ʽ���ҽ���
```
out
```
<h2 id="markdown����Щ���ܣ�"><a name="markdown����Щ���ܣ�" href="#markdown����Щ���ܣ�"></a>markdown����Щ���ܣ�</h2>
<ul>
<li>�����<code>���뵼��</code>����<ul>
<li>ֱ�Ӱ�һ��markdown���ı��ļ��Ϸŵ���ǰ���ҳ��Ϳ�����</li>
<li>����Ϊһ��html��ʽ���ļ�����ʽһ��Ҳ���ᶪʧ</li>
</ul>
</li>
<li>�༭��Ԥ��<code>ͬ������</code>�����������ã����Ͻ����ã�</li>
<li><code>VIM��ݼ�</code>֧�֣�����vim���ǿ��ٵĲ��� �����Ͻ����ã�</li>
<li>ǿ���<code>�Զ���CSS</code>���ܣ����㶨���Լ���չʾ</li>
<li>������Ҳ��������<code>����</code>,�༭����Ԥ������</li>
<li>��������<code>Github</code>��markdown�﷨</li>
<li>Ԥ������<code>�������</code></li>
<li>����ѡ���Զ�����</li>
</ul>
<h2 id="�����ⷴ��"><a name="�����ⷴ��" href="#�����ⷴ��"></a>�����ⷴ��</h2>
<p>��ʹ�������κ����⣬��ӭ�������ң�������������ϵ��ʽ���ҽ���</p>
```
