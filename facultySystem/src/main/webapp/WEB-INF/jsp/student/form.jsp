<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25.11.2025
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="isNew" value="${student == null || student.id == null}" />

<html>
<head>
  <title><c:out value="${isNew ? 'Новый студент' : 'Редактирование студента'}"/></title>
  <style>
    body { font-family: Arial, sans-serif; }
    .error { color: red; }
    label { display:block; margin-top:6px; }
    input[type=text], input[type=date], input[type=number], select {
      min-width: 250px;
    }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<h1><c:out value="${isNew ? 'Новый студент' : 'Редактирование студента'}"/></h1>

<c:if test="${not empty errors}">
  <ul class="error">
    <c:forEach var="e" items="${errors}">
      <li><c:out value="${e.value}"/></li>
    </c:forEach>
  </ul>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/student/edit">
  <input type="hidden" name="_csrf" value="${sessionScope.CSRF_TOKEN}"/>

  <c:if test="${not isNew}">
    <input type="hidden" name="id" value="<c:out value='${student.id}'/>"/>
  </c:if>

  <label>
    Фамилия:
    <input type="text" name="lastName" value="<c:out value='${student.lastName}'/>"/>
  </label>

  <label>
    Имя:
    <input type="text" name="firstName" value="<c:out value='${student.firstName}'/>"/>
  </label>

  <label>
    Отчество:
    <input type="text" name="patronymic" value="<c:out value='${student.patronymic}'/>"/>
  </label>

  <label>
    Дата рождения:
    <input type="date" name="dateOfBirth" value="<c:out value='${student.dateOfBirth}'/>"/>
  </label>

  <label>
    Пол:
    <select name="gender">
      <option value="" ${student.gender == null ? 'selected' : ''}>— не указано —</option>
      <option value="M" ${student.gender == 'M' ? 'selected' : ''}>Мужской</option>
      <option value="F" ${student.gender == 'F' ? 'selected' : ''}>Женский</option>
    </select>
  </label>

  <label>
    Год поступления:
    <input type="number" name="admissionYear" value="<c:out value='${student.admissionYear}'/>"/>
  </label>

  <label>
    Код студента:
    <input type="text" name="studentCode" value="<c:out value='${student.studentCode}'/>"/>
  </label>

  <label>
    Email:
    <input type="text" name="email" value="<c:out value='${student.email}'/>"/>
  </label>

  <label>
    Телефон:
    <input type="text" name="phone" value="<c:out value='${student.phone}'/>"/>
  </label>

  <label>
    Группа:
    <select name="groupId">
      <option value="">-- выберите группу --</option>
      <c:forEach var="g" items="${groups}">
        <option value="${g.id}"
                <c:if test="${student != null && student.groupId == g.id}">selected</c:if>>
          <c:out value="${g.name}"/> (курс <c:out value="${g.year}"/>)
        </option>
      </c:forEach>
    </select>
  </label>

  <br/><br/>
  <button type="submit">Сохранить</button>
  <a href="${pageContext.request.contextPath}/students">Отмена</a>
</form>

</body>
</html>

