<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25.11.2025
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Кафедра</title>
    <style>
        body { font-family: Arial, sans-serif; }
        .form-field { margin-bottom: 10px; }
        label { display: inline-block; width: 140px; }
    </style>
</head>
<body>

<c:set var="d" value="${department}" />
<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<h1>
    <c:choose>
        <c:when test="${not empty d}">Редактирование кафедры</c:when>
        <c:otherwise>Новая кафедра</c:otherwise>
    </c:choose>
</h1>

<c:if test="${not empty error}">
    <div style="color:red;">
        <c:out value="${error}"/>
    </div>
</c:if>

<form method="post">
    <input type="hidden" name="_csrf" value="${sessionScope.CSRF_TOKEN}"/>

    <c:if test="${not empty d}">
        <input type="hidden" name="id" value="${d.id}"/>
    </c:if>

    <div class="form-field">
        <label for="name">Название:</label>
        <input id="name" name="name"
               value="<c:out value='${not empty d ? d.name : ""}'/>"/>
    </div>

    <div class="form-field">
        <label for="faculty">Факультет:</label>
        <select id="faculty" name="facultyId">
            <option value="">-- выберите факультет --</option>
            <c:forEach items="${faculties}" var="f">
                <option value="${f.id}"
                        <c:if test="${not empty d && d.facultyId == f.id}">selected</c:if>>
                    <c:out value="${f.name}"/>
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-field">
        <button type="submit">Сохранить</button>
        <a href="${pageContext.request.contextPath}/departments">Отмена</a>
    </div>
</form>

</body>
</html>

