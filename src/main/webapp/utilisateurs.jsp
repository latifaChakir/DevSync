<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Utilisateurs</title>
</head>
<body>
<h1>Liste des Utilisateurs pour gestion des tâches</h1>
<p>Nombre d'utilisateurs trouvés : <c:out value="${utilisateursList.size()}" /></p>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Email</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach var="utilisateur" items="${utilisateursList}">
            <tr>
                <td><c:out value="${utilisateur.id}" /></td>
                <td><c:out value="${utilisateur.nom}" /></td>
                <td><c:out value="${utilisateur.email}" /></td>
                <td>
                    <a href="modifierUtilisateur?id=${utilisateur.id}">Modifier</a> |
                    <a href="supprimerUtilisateur?id=${utilisateur.id}" onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?');">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>