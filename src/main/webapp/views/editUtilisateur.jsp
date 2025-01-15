<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 02/10/2024
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
</head>
<body>
<div >
    <div>
        <div class="modal-content" style="padding: 0 4rem">
            <form action="utilisateurs?action=update" method="post">
                <div class="modal-header">
                    <h4 class="modal-title">Edit User</h4></div>
                <div class="modal-body">
                    <div class="form-group">
                        <input type="text" name="id" class="form-control" value="${utilisateur.id}" hidden="hidden" required>
                        <label>First Name</label>
                        <input type="text" class="form-control" name="prenom" value="${utilisateur.prenom}" required>
                    </div>
                    <div class="form-group">
                        <label>Last Name</label>
                        <input type="text" class="form-control" name="nom" value="${utilisateur.nom}"  required>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" class="form-control" value="${utilisateur.email}"  required>
                    </div>
                    <div class="form-group">
                        <label>UserName</label>
                        <input type="text" name="nom_utilisateur"  class="form-control" value="${utilisateur.nomUtilisateur}"  required>
                    </div>

                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="mot_de_passe" class="form-control" placeholder="Enter new password (leave blank to keep current)">
                    </div>
                    <div class="form-group">
                        <label>Manager</label>
                        <select name="role" class="form-control">
                            <option value="MANAGER" ${utilisateur.role.name() == 'MANAGER' ? 'selected' : ''}>MANAGER</option>
                            <option value="USER" ${utilisateur.role.name() == 'USER' ? 'selected' : ''}>User</option>
                        </select>
                    </div>

                </div>
                <div class="modal-footer">
                    <a href="/DevSync/utilisateurs"><input type="button" class="btn btn-primary" value="Cancel"></a>
                    <input type="submit" class="btn btn-success" value="Save">
                </div>

            </form>
        </div>
    </div>
</div>

</body>
</html>
