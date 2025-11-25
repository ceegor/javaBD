<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25.11.2025
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
  <title>Студенты</title>
  <style>
    body { font-family: Arial, sans-serif; }
    table { border-collapse: collapse; margin-top: 15px; width: 95%; }
    th, td { border: 1px solid #aaa; padding: 6px 10px; }
    th { background: #eee; }
    .filters { margin-bottom: 10px; }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<h1>Студенты</h1>

<div class="filters">
  <form method="get" action="${pageContext.request.contextPath}/students">
    <input type="text" name="lastName"
           placeholder="Поиск по фамилии"
           value="${fn:escapeXml(lastName)}"/>

    <!-- Факультет -->
    <select name="facultyId">
      <option value="">Все факультеты</option>
      <c:forEach var="f" items="${faculties}">
        <option value="${f.id}"
                <c:if test="${facultyId != null && facultyId == f.id}">selected</c:if>>
            ${f.name}
        </option>
      </c:forEach>
    </select>

    <!-- Кафедра -->
    <select name="departmentId">
      <option value="">Все кафедры</option>
      <c:forEach var="d" items="${departments}">
        <option value="${d.id}"
                <c:if test="${departmentId != null && departmentId == d.id}">selected</c:if>>
            ${d.name}
        </option>
      </c:forEach>
    </select>

    <!-- Группа -->
    <select name="groupId">
      <option value="">Все группы</option>
      <c:forEach var="g" items="${groups}">
        <option value="${g.id}"
                <c:if test="${groupId != null && groupId == g.id}">selected</c:if>>
            ${g.name}
        </option>
      </c:forEach>
    </select>

    <!-- Сортировка -->
    <select name="sort">
      <option value="student_id"      ${sort == 'student_id'      ? 'selected' : ''}>ID</option>
      <option value="last_name"       ${sort == 'last_name'       ? 'selected' : ''}>Фамилия</option>
      <option value="first_name"      ${sort == 'first_name'      ? 'selected' : ''}>Имя</option>
      <option value="admission_year"  ${sort == 'admission_year'  ? 'selected' : ''}>Год поступления</option>
      <option value="group"           ${sort == 'group'           ? 'selected' : ''}>Группа ID</option>
    </select>

    <select name="asc">
      <option value="true"  ${asc ? 'selected' : ''}>По возрастанию</option>
      <option value="false" ${!asc ? 'selected' : ''}>По убыванию</option>
    </select>

    <select name="size">
      <option value="10" ${size == 10 ? 'selected' : ''}>10</option>
      <option value="20" ${size == 20 ? 'selected' : ''}>20</option>
      <option value="50" ${size == 50 ? 'selected' : ''}>50</option>
    </select>

    <button type="submit">Показать</button>
  </form>

  <p>
    <a href="${pageContext.request.contextPath}/student/edit">Новый студент</a>
  </p>
</div>


<table>
  <tr>
    <th>ID</th>
    <th>Фамилия</th>
    <th>Имя</th>
    <th>Код</th>
    <th>Email</th>
    <th>Группа</th>
    <th>Действия</th>
  </tr>

  <c:forEach var="s" items="${students}">
    <tr>
      <td>${s.id}</td>
      <td>${s.lastName}</td>
      <td>${s.firstName}</td>
      <td>${s.studentCode}</td>
      <td>${s.email}</td>
      <td>${s.groupId}</td>
      <td>
        <a href="${pageContext.request.contextPath}/student/edit?id=${s.id}">✏️</a>
        |
        <form method="post" action="${pageContext.request.contextPath}/student/delete"
              style="display:inline"
              onsubmit="return confirm('Удалить студента?');">
          <input type="hidden" name="id" value="${s.id}"/>
          <input type="hidden" name="_csrf" value="${sessionScope.CSRF_TOKEN}"/>
          <button type="submit">🗑️</button>
        </form>
      </td>
    </tr>
  </c:forEach>

  <c:if test="${empty students}">
    <tr><td colspan="7" style="text-align:center;color:gray;">Нет данных</td></tr>
  </c:if>
</table>


<!-- Пагинация -->
<c:if test="${pages > 1}">
  <div>
    Страницы:
    <c:forEach begin="1" end="${pages}" var="p">
      <c:choose>
        <c:when test="${p == page}">
          <b>[${p}]</b>
        </c:when>
        <c:otherwise>
          <a href="?page=${p}&size=${size}&lastName=${lastName}&groupId=${groupId}
                             &departmentId=${departmentId}&facultyId=${facultyId}
                             &sort=${sort}&asc=${asc}">
              ${p}
          </a>
        </c:otherwise>
      </c:choose>
    </c:forEach>
  </div>
</c:if>

</body>
</html>

