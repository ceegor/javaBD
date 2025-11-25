<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25.11.2025
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
  <title>Кафедры</title>
  <style>
    body { font-family: Arial, sans-serif; }
    table { border-collapse: collapse; margin-top: 15px; width: 90%; }
    th, td { padding: 6px 10px; border: 1px solid #aaa; }
    th { background: #eee; }
    .toolbar { margin-bottom: 10px; }
    .pager a { margin: 0 4px; }
    .pager b { margin: 0 4px; }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<h1>Кафедры</h1>

<div class="toolbar">
  <form method="get" action="${pageContext.request.contextPath}/departments">
    <input type="text" name="q" placeholder="Поиск по названию"
           value="${fn:escapeXml(q)}"/>

    <select name="facultyId">
      <option value="">Все факультеты</option>
      <c:forEach items="${faculties}" var="fac">
        <option value="${fac.id}"
                <c:if test="${facultyId != null && facultyId == fac.id}">selected</c:if>>
          <c:out value="${fac.name}"/>
        </option>
      </c:forEach>
    </select>

    <select name="sort">
      <option value="id"      ${sort == 'id'      ? 'selected' : ''}>ID</option>
      <option value="name"    ${sort == 'name'    ? 'selected' : ''}>Название</option>
      <option value="faculty" ${sort == 'faculty' ? 'selected' : ''}>Факультет (id)</option>
    </select>

    <select name="size">
      <option value="10" ${size == 10 ? 'selected' : ''}>10</option>
      <option value="20" ${size == 20 ? 'selected' : ''}>20</option>
      <option value="50" ${size == 50 ? 'selected' : ''}>50</option>
    </select>

    <select name="asc">
      <option value="true"  ${asc ? 'selected' : ''}>По возрастанию</option>
      <option value="false" ${!asc ? 'selected' : ''}>По убыванию</option>
    </select>

    <button type="submit">Показать</button>
  </form>

  <p>
    <a href="${pageContext.request.contextPath}/department/new">Добавить кафедру</a>
  </p>
</div>

<table>
  <tr>
    <th>ID</th>
    <th>Название</th>
    <th>ID факультета</th>
    <th>Действия</th>
  </tr>

  <c:forEach items="${departments}" var="d">
    <tr>
      <td><c:out value="${d.id}"/></td>
      <td><c:out value="${d.name}"/></td>
      <td><c:out value="${d.facultyId}"/></td>
      <td>
        <a href="${pageContext.request.contextPath}/department/edit?id=${d.id}">
          Редактировать
        </a>
        |
        <form method="post"
              action="${pageContext.request.contextPath}/department/delete"
              style="display:inline">
          <input type="hidden" name="id" value="${d.id}"/>
          <input type="hidden" name="_csrf" value="${sessionScope.CSRF_TOKEN}"/>
          <button type="submit"
                  onclick="return confirm('Удалить кафедру?')">
            Удалить
          </button>
        </form>
      </td>
    </tr>
  </c:forEach>

  <c:if test="${empty departments}">
    <tr>
      <td colspan="4" style="text-align:center;color:gray;">Нет данных</td>
    </tr>
  </c:if>
</table>

<!-- Пагинация -->
<c:if test="${pages > 1}">
  <div class="pager">
    Страницы:
    <c:forEach begin="1" end="${pages}" var="p">
      <c:choose>
        <c:when test="${p == page}">
          <b>[${p}]</b>
        </c:when>
        <c:otherwise>
          <a href="?page=${p}&size=${size}&q=${fn:escapeXml(q)}&facultyId=${facultyId}
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

