<%@page import="java.io.StringWriter"%>
<%@page import="java.io.PrintWriter"%>
<%@page isErrorPage="true" %>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
<%
	String path = request.getContextPath();
 %>
<head>
	<!-- 修改为你想要的标题或者就用这个 -->
	<title>500错误 - 程序暂时无法处理您的访问请求 </title>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
	<!-- 修改以下CSS路径为你安装的目录-->
	<link rel="stylesheet" type="text/css" href="<%=path %>/modules/errorMsg/css/error.css" media="screen" />
</head>
<body>
	<% Exception e = (Exception)request.getAttribute("ex"); %>
	
	<!-- 修改以下图片路径为你安装的目录 -->
	<div id="container"><img class="png" src="<%=path %>/modules/errorMsg/images/500.png" /> <img class="png msg" src="<%=path %>/modules/errorMsg/images/500_msg.png" />
		<p><a href="#" onClick="javascript:history.go(-1);"><img class="png" src="<%=path %>/modules/errorMsg/images/back.png" /></a>&nbsp;&nbsp; <a href="#" onClick="javascript:show_dt()"><img class="png" src="<%=path %>/modules/errorMsg/images/500_details.png" /></a> </p>
	</div>
	<div id="cloud" class="png">
		<pre id="error_info" style="DISPLAY: none;margin-top:175px;overflow:scroll">
		<% if(e!=null){%>
			<H2>错误名称: <%= e.getClass().getSimpleName() %></H2>
			<hr>
			<P>错误描述: <%= e.getMessage() %></P>
			<hr>
			<% 
				StringWriter sw=new StringWriter();  
				PrintWriter pw=new PrintWriter(sw);  
				e.printStackTrace(pw);
			%>
			<P>错误信息: <%= sw.toString()  %></P>
		<% }else{ %>
			<H2>错误名称: <%= exception.getClass().getSimpleName() %></H2>
			<hr>
			<P>错误描述: <%= exception.getMessage() %></P>
			<hr>
			<%
				StringWriter sw=new StringWriter();
				PrintWriter pw=new PrintWriter(sw);
				exception.printStackTrace(pw);
			%>
			<P>错误信息:</P>
			<hr>
			<P><%= sw.toString()  %></P>
		<% } %>
	    </pre>
	</div>
	<script type="text/javascript">
		function show_dt() {
			var error_info = document.getElementById("error_info");
			if(error_info.style.display == "none"){
				error_info.style.display = "";
			}else{
				error_info.style.display = "none";
			}
		}
	</script>
</body>
</html>