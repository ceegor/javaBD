<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 24.11.2025
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Факультеты</title>
    <style>
        body { font-family: Arial, sans-serif; }
        table { border-collapse: collapse; margin-top: 15px; width: 80%; }
        th, td { padding: 8px 12px; border: 1px solid #aaa; }
        th { background: #eee; }
        .pager a { margin: 0 4px; }
        .pager b { margin: 0 4px; }
        .toolbar { margin-bottom: 10px; }
    </style>
</head>
<body>

<h1>Факультеты</h1>

<div class="toolbar">
    <form method="get" action="${pageContext.request.contextPath}/faculties">
        <input type="text" name="q" value="${fn:escapeXml(q)}" placeholder="Поиск по названию"/>

        <select name="sort">
            <option value="id"   ${sort=='id'   ? 'selected' : ''}>ID</option>
            <option value="name" ${sort=='name' ? 'selected' : ''}>Название</option>
            <option value="dean" ${sort=='dean' ? 'selected' : ''}>Декан</option>
        </select>

        <select name="size">
            <option value="10" ${size==10 ? 'selected' : ''}>10</option>
            <option value="20" ${size==20 ? 'selected' : ''}>20</option>
            <option value="50" ${size==50 ? 'selected' : ''}>50</option>
        </select>

        <input type="hidden" name="asc" value="${asc}"/>

        <button type="submit">Показать</button>
    </form>

    <p>
        <a href="${pageContext.request.contextPath}/faculty/new">➕ Добавить факультет</a>
    </p>
</div>

<table>
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Декан</th>
        <th>Действия</th>
    </tr>

    <c:forEach items="${faculties}" var="f">
        <tr>
            <td><c:out value="${f.id}"/></td>
            <td><c:out value="${f.name}"/></td>
            <td><c:out value="${f.dean}"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/faculty/edit?id=${f.id}">✏️ Редактировать</a>

                <form method="post"
                      action="${pageContext.request.contextPath}/faculty/delete"
                      style="display:inline">
                    <input type="hidden" name="id" value="${f.id}"/>
                    <input type="hidden" name="_csrf" value="${sessionScope.CSRF_TOKEN}"/>
                    <button type="submit" onclick="return confirm('Удалить факультет?')">🗑️ Удалить</button>
                </form>
            </td>
        </tr>
    </c:forEach>

    <c:if test="${empty faculties}">
        <tr><td colspan="4" style="text-align:center;color:gray;">Нет данных</td></tr>
    </c:if>
</table>

<!-- Пагинация -->
<c:if test="${pages > 1}">
    <div class="pager">
        <p>Страницы:</p>
        <c:forEach begin="1" end="${pages}" var="p">
            <c:choose>
                <c:when test="${p == page}">
                    <b>[${p}]</b>
                </c:when>
                <c:otherwise>
                    <a href="?page=${p}&size=${size}&q=${fn:escapeXml(q)}&sort=${sort}&asc=${asc}">${p}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</c:if>

</body>
</html>
