<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25.11.2025
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Факультет</title>
  <style>
    body { font-family: Arial, sans-serif; }
    .form-field { margin-bottom: 10px; }
    label { display: inline-block; width: 120px; }
  </style>
</head>
<body>

<c:set var="f" value="${faculty}" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<h1>
  <c:choose>
    <c:when test="${not empty f}">Редактирование факультета</c:when>
    <c:otherwise>Новый факультет</c:otherwise>
  </c:choose>
</h1>

<c:if test="${not empty error}">
  <div style="color: red;">
    <c:out value="${error}"/>
  </div>
</c:if>

<form method="post">
  <input type="hidden" name="_csrf" value="${sessionScope.CSRF_TOKEN}"/>

  <c:if test="${not empty f}">
    <input type="hidden" name="id" value="${f.id}"/>
  </c:if>

  <div class="form-field">
    <label for="name">Название:</label>
    <input id="name" name="name"
           value="<c:out value='${not empty f ? f.name : ""}'/>"/>
  </div>

  <div class="form-field">
    <label for="dean">Декан:</label>
    <input id="dean" name="dean"
           value="<c:out value='${not empty f ? f.dean : ""}'/>"/>
  </div>

  <div class="form-field">
    <button type="submit">Сохранить</button>
    <a href="${pageContext.request.contextPath}/faculties">Отмена</a>
  </div>
</form>

</body>
</html>
