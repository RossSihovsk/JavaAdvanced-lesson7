<%--
  Created by IntelliJ IDEA.
  User: Ross
  Date: 6/2/2021
  Time: 11:55 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Авторизація</title>
</head>

<body>
<h1>Вхід в особистий кабінет</h1>
<form action="logging" method="post">
    <input name="login" type="text" placeholder="Логін"><br><br>
    <input name="password" type="password" placeholder="Пароль"><br><br>
    <input type="submit" value="Вхід">
</form>
</body>
</html>
