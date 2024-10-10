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
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.1.0/css/select2.min.css" rel="stylesheet" />

    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
</head>
<body>
<div >
    <div>
        <div class="modal-content" style="padding: 0 4rem">
            <form action="tasks?action=update" method="post">
                <div class="modal-header">
                    <h4 class="modal-title">Edit Task</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Title</label>
                        <input type="text" name="id" class="form-control" value="${task.id}" hidden="hidden" required>
                        <input type="text" class="form-control" name="title" required value="${task.title}">
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea type="text" class="form-control" name="description"  required>${task.description}</textarea>
                    </div>
                    <div class="form-group">
                        <label>Status</label>
                        <select name="status" id="status" class="form-control" required>
                            <option value="A_FAIRE">A_FAIRE</option>
                            <option value="EN_COURS">EN_COURS</option>
                            <option value="TERMINEE">TERMINEE</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Deadline</label>
                        <input type="datetime-local" class="form-control" name="deadLine" value="${task.deadLine}" required>
                    </div>

                    <c:if test="${currentUser.getRole().MANAGER}" >
                        <div class="form-group">
                            <label>Assigned To</label>
                            <select name="assignedTo" class="form-control" required>
                                <c:forEach var="user" items="${utilisateurList}">
                                    <option value="${user.id}">${user.nom} ${user.prenom}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <label>Tags</label>
                        <select  class="form-control js-example-basic-multiple "  name="tags" multiple="multiple" data-placeholder="Choose tags" style="width: 100%">
                            <c:forEach var="tag" items="${tags}">
                                <option value="${tag.id}">${tag.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="/DevSync/tasks"><input type="button" class="btn btn-primary" data-dismiss="modal" value="Cancel"></a>
                    <input type="submit" class="btn btn-success" value="Add">
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $(document).ready(function() {
        $('.js-example-basic-multiple').select2();
    });
</script>
</body>
</html>
