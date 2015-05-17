<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>login.jsp</title>
  </head>
  
  <body>
    <form action="${pageContext.request.contextPath}/ServletDemo1" method="get">
    	姓名：<input type="text" name="name">
    	<input type="submit" value="提交">
    </form>
    
    <c:url value="/ServletDemo1" scope="page" var="url">
    	<c:param name="name" value="中国"></c:param>
    </c:url>
    <a href="${url}">点点</a>
  </body>
</html>
