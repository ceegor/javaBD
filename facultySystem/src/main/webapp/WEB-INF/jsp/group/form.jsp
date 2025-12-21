<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25.11.2025
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Группа</title>
  <style>
    body { font-family: Arial, sans-serif; }
    .form-field { margin-bottom: 10px; }
    label { display: inline-block; width: 140px; }
    .error { color:red; }
  </style>
</head>
<body>

<c:set var="g" value="${group}" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<h1>
  <c:choose>
    <c:when test="${not empty g}">Редактирование группы</c:when>
    <c:otherwise>Новая группа</c:otherwise>
  </c:choose>
</h1>

<c:if test="${not empty error}">
  <div class="error"><c:out value="${error}"/></div>
</c:if>

<form method="post">
  <input type="hidden" name="_csrf" value="${sessionScope.CSRF_TOKEN}"/>

  <c:if test="${not empty g}">
    <input type="hidden" name="id" value="<c:out value='${g.id}'/>"/>
  </c:if>

  <div class="form-field">
    <label for="name">Название:</label>
    <input id="name" name="name"
           value="<c:out value='${not empty g ? g.name : ""}'/>"/>
  </div>

  <div class="form-field">
    <label for="year">Курс:</label>
    <input id="year" name="year" type="number" min="1" max="10"
           value="<c:out value='${not empty g ? g.year : ""}'/>"/>
  </div>

  <div class="form-field">
    <label for="departmentId">Кафедра:</label>
    <select id="departmentId" name="departmentId">
      <option value="">-- выберите кафедру --</option>
      <c:forEach items="${departments}" var="d">
        <option value="${d.id}"
                <c:if test="${not empty g && g.departmentId == d.id}">selected</c:if>>
          <c:out value="${d.name}"/>
        </option>
      </c:forEach>
    </select>
  </div>

  <div class="form-field">
    <button type="submit">Сохранить</button>
    <a href="${pageContext.request.contextPath}/groups">Отмена</a>
  </div>
</form>

</body>
</html>

