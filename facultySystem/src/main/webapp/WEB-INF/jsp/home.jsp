<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25.11.2025
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
  <title>Главная страница</title>
  <style>
    body { font-family: Arial, sans-serif; }
    .container { padding: 15px; }
    .cards { display:flex; flex-wrap:wrap; gap:16px; margin-top:15px; }
    .card {
      border:1px solid #ccc;
      padding:12px 16px;
      border-radius:6px;
      width:220px;
      box-shadow:0 1px 3px rgba(0,0,0,0.1);
    }
    .card h3 { margin-top:0; }
    .card a { display:inline-block; margin-top:6px; }
  </style>
</head>
<body>

<div class="container">
  <h1>Главная страница</h1>
  <p>Добро пожаловать в систему управления факультетом. Выберите раздел:</p>

  <div class="cards">
    <div class="card">
      <h3>Факультеты</h3>
      <p>Просмотр и управление факультетами.</p>
      <a href="${pageContext.request.contextPath}/faculties">Перейти →</a>
    </div>

    <div class="card">
      <h3>Кафедры</h3>
      <p>Просмотр и управление кафедрами.</p>
      <a href="${pageContext.request.contextPath}/departments">Перейти →</a>
    </div>

    <div class="card">
      <h3>Группы</h3>
      <p>Просмотр и управление учебными группами.</p>
      <a href="${pageContext.request.contextPath}/groups">Перейти →</a>
    </div>

    <div class="card">
      <h3>Студенты</h3>
      <p>Поиск, просмотр и редактирование информации о студентах.</p>
      <a href="${pageContext.request.contextPath}/students">Перейти →</a>
    </div>

    <div class="card">
      <h3>Статистика</h3>
      <p>Общая статистика по студентам, факультетам и т.д.</p>
      <a href="${pageContext.request.contextPath}/statistics">Перейти →</a>
    </div>
  </div>
</div>

</body>
</html>

