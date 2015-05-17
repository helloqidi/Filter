<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>test.jsp</title>
    <style type="text/css">
    	table{
    		width: 260px;
    	}
    	tr{
    		height: 50px;
    	}
    	th{
    		font-size: x-large;
    	}
    </style>
  </head>
  
  <body>
  <table>
  	<th>留言板</th>
    <c:forEach items="${list}" var="message">
    	<tr><td><hr>${message}</td></tr>
    </c:forEach>
  </table>    
    <br><br>
    
    <hr>
    
    <br>
    
    <form action="${pageContext.request.contextPath}/ServletDemo2" method="post">
    	留言：<br>
    	<textarea rows="8" cols="30" name="message"></textarea>
    	<br>
    	<input type="submit" value="留言">
    </form>
  </body>
</html>
