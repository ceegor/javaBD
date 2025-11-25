<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25.11.2025
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
  <title>Группы</title>
  <style>
    body { font-family: Arial, sans-serif; }
    table { border-collapse: collapse; margin-top: 15px; width: 90%; }
    th, td { padding: 6px 10px; border: 1px solid #aaa; }
    th { background: #eee; }
    .toolbar { margin-bottom: 10px; }
    .pager a, .pager b { margin: 0 4px; }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<h1>Группы</h1>

<div class="toolbar">
  <form method="get" action="${pageContext.request.contextPath}/groups">
    <!-- поиск по названию -->
    <input type="text" name="q" placeholder="Поиск по названию"
           value="${fn:escapeXml(q)}"/>

    <!-- фильтр по кафедре -->
    <select name="departmentId">
      <option value="">Все кафедры</option>
      <c:forEach items="${departments}" var="d">
        <option value="${d.id}"
                <c:if test="${departmentId != null && departmentId == d.id}">selected</c:if>>
          <c:out value="${d.name}"/>
        </option>
      </c:forEach>
    </select>

    <!-- фильтр по году -->
    <input type="number" name="year" min="1" max="10"
           value="${year != null ? year : ''}" placeholder="Курс"/>

    <!-- сортировка -->
    <select name="sort">
      <option value="id"        ${sort=='id'        ? 'selected' : ''}>ID</option>
      <option value="name"      ${sort=='name'      ? 'selected' : ''}>Название</option>
      <option value="year"      ${sort=='year'      ? 'selected' : ''}>Курс</option>
      <option value="department"${sort=='department'? 'selected' : ''}>Кафедра (id)</option>
    </select>

    <!-- направление сортировки -->
    <select name="asc">
      <option value="true"  ${asc ? 'selected' : ''}>По возрастанию</option>
      <option value="false" ${!asc ? 'selected' : ''}>По убыванию</option>
    </select>

    <!-- размер страницы -->
    <select name="size">
      <option value="10" ${size==10 ? 'selected' : ''}>10</option>
      <option value="20" ${size==20 ? 'selected' : ''}>20</option>
      <option value="50" ${size==50 ? 'selected' : ''}>50</option>
    </select>

    <button type="submit">Показать</button>
  </form>

  <p>
    <a href="${pageContext.request.contextPath}/group/new">➕ Добавить группу</a>
  </p>
</div>

<table>
  <tr>
    <th>ID</th>
    <th>Название</th>
    <th>Курс</th>
    <th>ID кафедры</th>
    <th>Действия</th>
  </tr>

  <c:forEach items="${groups}" var="g">
    <tr>
      <td><c:out value="${g.id}"/></td>
      <td><c:out value="${g.name}"/></td>
      <td><c:out value="${g.year}"/></td>
      <td><c:out value="${g.departmentId}"/></td>
      <td>
        <a href="${pageContext.request.contextPath}/group/edit?id=${g.id}">
          ✏️ Редактировать
        </a>
        |
        <form method="post"
              action="${pageContext.request.contextPath}/group/delete"
              style="display:inline">
          <input type="hidden" name="id" value="${g.id}"/>
          <input type="hidden" name="_csrf" value="${sessionScope.CSRF_TOKEN}"/>
          <button type="submit" onclick="return confirm('Удалить группу?')">
            🗑️ Удалить
          </button>
        </form>
      </td>
    </tr>
  </c:forEach>

  <c:if test="${empty groups}">
    <tr>
      <td colspan="5" style="text-align:center;color:gray;">Нет данных</td>
    </tr>
  </c:if>
</table>

<!-- пагинация -->
<c:if test="${pages > 1}">
  <div class="pager">
    Страницы:
    <c:forEach begin="1" end="${pages}" var="p">
      <c:choose>
        <c:when test="${p == page}">
          <b>[${p}]</b>
        </c:when>
        <c:otherwise>
          <a href="?page=${p}&size=${size}
                             &q=${fn:escapeXml(q)}
                             &departmentId=${departmentId}
                             &year=${year}
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

