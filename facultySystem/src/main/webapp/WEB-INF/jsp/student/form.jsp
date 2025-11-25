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
  <c:if test="${not isNew}">
    <input type="hidden" name="id" value="${student.id}"/>
  </c:if>

  <label>
    Фамилия:
    <input type="text" name="lastName" value="${student.lastName}"/>
  </label>

  <label>
    Имя:
    <input type="text" name="firstName" value="${student.firstName}"/>
  </label>

  <label>
    Отчество:
    <input type="text" name="patronymic" value="${student.patronymic}"/>
  </label>

  <label>
    Дата рождения:
    <input type="date" name="dateOfBirth" value="${student.dateOfBirth}"/>
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
    <input type="number" name="admissionYear" value="${student.admissionYear}"/>
  </label>

  <label>
    Код студента:
    <input type="text" name="studentCode" value="${student.studentCode}"/>
  </label>

  <label>
    Email:
    <input type="text" name="email" value="${student.email}"/>
  </label>

  <label>
    Телефон:
    <input type="text" name="phone" value="${student.phone}"/>
  </label>

  <label>
    Группа:
    <select name="groupId">
      <option value="">-- выберите группу --</option>
      <c:forEach var="g" items="${groups}">
        <option value="${g.id}"
                <c:if test="${student != null && student.groupId == g.id}">selected</c:if>>
            ${g.name} (курс ${g.year})
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

