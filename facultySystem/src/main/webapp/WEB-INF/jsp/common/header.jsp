<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25.11.2025
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="background:#333;color:#fff;padding:10px 15px;">
    <span style="font-weight:bold;margin-right:20px;">
        Система управления факультетами
    </span>

  <a href="${pageContext.request.contextPath}/" style="color:#fff;margin-right:10px;">Главная</a>
  <a href="${pageContext.request.contextPath}/faculties" style="color:#fff;margin-right:10px;">Факультеты</a>
  <a href="${pageContext.request.contextPath}/departments" style="color:#fff;margin-right:10px;">Кафедры</a>
  <a href="${pageContext.request.contextPath}/groups" style="color:#fff;margin-right:10px;">Группы</a>
  <a href="${pageContext.request.contextPath}/students" style="color:#fff;margin-right:10px;">Студенты</a>
  <a href="${pageContext.request.contextPath}/statistics" style="color:#fff;margin-right:10px;">Статистика</a>
</div>

