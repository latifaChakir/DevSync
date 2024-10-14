<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 13/10/2024
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">


</head>
<body>
<div class="col-md-2 sidebar">
    <h2>Menu</h2>
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link active" href="/DevSync/dashboard">Dashboard</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/DevSync/tasks">Manage Tasks</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/DevSync/tags?action=getAll">Manage Tags</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/DevSync/profil">User Profil</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/DevSync/utilisateurs">Manage Users</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/DevSync/login">Logout</a>
        </li>
    </ul>
</div>
<style>
    body {
        background: #eee;
    }
    .sidebar {
        background-color: #343a40; /* Couleur de fond de la sidebar */
        color: white; /* Couleur du texte */
        height: 100vh; /* Hauteur de la sidebar */
        padding: 15px; /* Espacement */
    }
    .sidebar h2 {
        color: white;
        margin-bottom: 20px;
    }
    .nav-link {
        color: white; /* Couleur des liens */
    }
    .nav-link:hover {
        background-color: #495057; /* Couleur de fond au survol */
    }
    .nav-item {
        margin-bottom: 10px; /* Espacement entre les éléments */
    }
</style>
</body>
</html>
