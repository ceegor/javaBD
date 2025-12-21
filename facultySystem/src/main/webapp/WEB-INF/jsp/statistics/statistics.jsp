<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25.11.2025
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
  <title>Статистика студентов</title>
  <style>
    body { font-family: Arial, sans-serif; }
    table { border-collapse: collapse; margin: 15px 0; }
    th, td { border: 1px solid #aaa; padding: 4px 8px; }
    th { background: #eee; }
    .block { margin-bottom: 25px; }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<h1>Статистика студентов</h1>

<div class="block">
  <form method="get" action="${pageContext.request.contextPath}/statistics">
    <label>
      Возраст:
      <input type="number" name="age" value="<c:out value='${age}'/>" min="0" max="120"/>
    </label>
    <button type="submit">Пересчитать</button>
  </form>
  <p>
    Старше или равно <c:out value="${age}"/>: <b><c:out value="${olderCount}"/></b><br/>
    Младше <c:out value="${age}"/>: <b><c:out value="${youngerCount}"/></b>
  </p>
  <h3>Список студентов старше или равно <c:out value="${age}"/></h3>
  <ul>
    <c:forEach var="s" items="${olderStudents}">
      <li>
        <c:out value="${s.lastName}"/> <c:out value="${s.firstName}"/>
        (код: <c:out value="${s.studentCode}"/>,
        группа: <c:out value="${s.groupId}"/>)
      </li>
    </c:forEach>
  </ul>
  <h3>Список студентов младше <c:out value="${age}"/></h3>
  <ul>
    <c:forEach var="s" items="${youngerStudents}">
      <li>
        <c:out value="${s.lastName}"/> <c:out value="${s.firstName}"/>
        (код: <c:out value="${s.studentCode}"/>,
        группа: <c:out value="${s.groupId}"/>)
      </li>
    </c:forEach>
  </ul>
</div>

<div class="block">
  <h2>По факультетам</h2>
  <table>
    <tr><th>Факультет</th><th>Студентов</th></tr>
    <c:forEach var="e" items="${byFaculty}">
      <tr>
        <td><c:out value="${e.key}"/></td>
        <td><c:out value="${e.value}"/></td>
      </tr>
    </c:forEach>
  </table>
</div>

<div class="block">
  <h2>По кафедрам</h2>
  <table>
    <tr><th>Кафедра</th><th>Студентов</th></tr>
    <c:forEach var="e" items="${byDepartment}">
      <tr>
        <td><c:out value="${e.key}"/></td>
        <td><c:out value="${e.value}"/></td>
      </tr>
    </c:forEach>
  </table>
</div>

<div class="block">
  <h2>По группам</h2>
  <table>
    <tr><th>Группа</th><th>Студентов</th></tr>
    <c:forEach var="e" items="${byGroup}">
      <tr>
        <td><c:out value="${e.key}"/></td>
        <td><c:out value="${e.value}"/></td>
      </tr>
    </c:forEach>
  </table>
</div>

<div class="block">
  <h2>По годам поступления</h2>
  <table>
    <tr><th>Год</th><th>Студентов</th></tr>
    <c:forEach var="e" items="${byYear}">
      <tr>
        <td><c:out value="${e.key}"/></td>
        <td><c:out value="${e.value}"/></td>
      </tr>
    </c:forEach>
  </table>
</div>

<p><a href="${pageContext.request.contextPath}/students">⟵ К списку студентов</a></p>

</body>
</html>

