<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<%
String path = request.getContextPath();
 %>
<head>
<!-- 修改为你想要的标题或者就用这个 -->
<title>权限控制 - 无页面访问权限 </title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<!-- 修改以下CSS路径为你安装的目录-->
<link rel="stylesheet" type="text/css" href="<%=path %>/modules/errorMsg/css/error.css" media="screen" />
</head>
<body>
<!-- 修改以下图片路径为你安装的目录 -->
<div id="container"><img class="png" src="<%=path %>/modules/errorMsg/images/permission.png" /> <img class="png msg" src="<%=path %>/modules/errorMsg/images/no_page_per_msg.png" />
  <p><a href="#" onClick="javascript:history.go(-1);"><img class="png" src="<%=path %>/modules/errorMsg/images/back.png" /></a> </p>
</div>
<div id="cloud" class="png"></div>
<pre style="DISPLAY: none"></pre>
</body>
</html>