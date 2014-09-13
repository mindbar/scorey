<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
      <title>scorey</title>
      <link rel="stylesheet" type="text/css" href="WEB-INF/css/style.css">
  </head>
  <body>

  <form action="search" method="get">
      <input id="searchbox" type="text" name="q" value="${query}">
  </form>

  <c:if test="${not empty query}">
    <table>
      <c:forEach var="e" items="${result.scores}">
        <tr>
           <td>${e.key}</td>
           <td>${e.value}</td>
        </tr>
      </c:forEach>
    </table>
  </c:if>

  <hr/>

  </body>
</html>