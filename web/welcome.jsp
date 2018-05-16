<%--
  Created by IntelliJ IDEA.
  User: himedere
  Date: 15/05/2018
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h2>
    <%
        if (session.getAttribute("login") == null || session.getAttribute("login") == "") {
            response.sendRedirect("index.jsp");
        }
    %>
    Welcome, <%=session.getAttribute("login")%>
</h2>
<h3>
    <a href="logout.jsp">Logout</a>
</h3>
</body>
</html>
