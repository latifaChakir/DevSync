<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 09/10/2024
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

</head>
<body>
<div class="container mb-4 main-container">
    <div class="row">
        <div class="col-lg-4 " style="padding-left: 0px !important;">
            <!-- Account Sidebar-->
            <div class="author-card pb-3">
                <div class="author-card-cover" style="background-image: url(https://bootdey.com/img/Content/flores-amarillas-wallpaper.jpeg);"><a class="btn btn-style-1 btn-white btn-sm" href="#" data-toggle="tooltip" title="" data-original-title="You currently have 290 Reward points to spend"><i class="fa fa-award text-md"></i>&nbsp;290 points</a></div>
                <div class="author-card-profile">
                    <div class="author-card-avatar"><img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="Daniel Adams">
                    </div>
                    <div class="author-card-details">
                        <h5 class="author-card-name text-lg">${currentUser.nom} ${currentUser.prenom}</h5><span class="author-card-position">${currentUser.email}</span>
                    </div>
                </div>
            </div>
            <div class="wizard">
                <nav class="list-group list-group-flush">
                    <a class="list-group-item active" href="#">
                        <div class="d-flex justify-content-between align-items-center">
                            <div><i class="fa fa-shopping-bag mr-1 text-muted"></i>
                                <div class="d-inline-block font-weight-medium text-uppercase">Orders List</div>
                            </div><span class="badge badge-secondary">6</span>
                        </div>
                    </a><a class="list-group-item" href="https://www.bootdey.com/snippets/view/bs4-profile-settings-page" target="__blank"><i class="fa fa-user text-muted"></i>Profile Settings</a><a class="list-group-item" href="#"><i class="fa fa-map-marker text-muted"></i>Addresses</a>
                    <a class="list-group-item" href="/DevSync/tasks" tagert="__blank">
                        <div class="d-flex justify-content-between align-items-center">
                            <div><i class="fa fa-heart mr-1 text-muted"></i>
                                <div class="d-inline-block font-weight-medium text-uppercase">Manage Tasks</div>
                            </div><span class="badge badge-secondary">3</span>
                        </div>
                    </a>
                    <a class="list-group-item" href="#">
                        <div class="d-flex justify-content-between align-items-center">
                            <div><i class="fa fa-tag mr-1 text-muted"></i>
                                <div class="d-inline-block font-weight-medium text-uppercase">My Tickets</div>
                            </div><span class="badge badge-secondary">4</span>
                        </div>
                    </a>
                </nav>
            </div>
        </div>
        <div class="col-lg-8 pb-5 " style="padding-left: 0px !important;">
            <h4>Tasks Assigned To Me :</h4>
            <div class="table-responsive">
                <table class="table table-hover mb-0 mt-4">
                    <thead>
                    <tr>
                        <th>Task</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>DeadLine</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="task" items="${myTasks}">
                            <tr>
                            <td><c:out value="${task.title}" /></td>
                            <td><c:out value="${task.description}" /></td>
                                <td>
                                    <form action="tasks?action=changeStatus" method="post" style="display: inline;">
                                        <input type="hidden" name="id" value="${task.id}" />
                                        <div class="d-flex ">
                                        <select name="status" class="badge m-0 form-control select-status
                                            <c:if test="${task.status == 'A_FAIRE'}">A_FAIRE</c:if>
                                            <c:if test="${task.status == 'EN_COURS'}">EN_COURS</c:if>
                                            <c:if test="${task.status == 'TERMINEE'}">TERMINEE</c:if>">
                                            <option value="A_FAIRE"  <c:if test="${task.status == 'A_FAIRE'}">selected</c:if>>A_FAIRE</option>
                                            <option value="EN_COURS" <c:if test="${task.status == 'EN_COURS'}">selected</c:if>>EN_COURS</option>
                                            <option value="TERMINEE" <c:if test="${task.status == 'TERMINEE'}">selected</c:if>>TERMINEE</option>
                                        </select>
                                        <button type="submit" style="border: none"><i class="material-icons" data-toggle="tooltip" title="Soumettre" style="color: green;">check</i>

                                        </button>
                                        </div>
                                    </form>
                                </td>

                                <td><c:out value="${task.deadLine}" /></td>
                                <c:if test="${!currentUser.getId().equals(task.createdBy.getId())}">
                                    <td>
                                        <a href="taskHistory?id=${task.id}&newUserId=${currentUser.getId()}&action=remplace" class="edit">
                                            <i class="material-icons" data-toggle="tooltip" title="Remplacer">swap_horiz</i>
                                        </a>


                                        <a href="taskHistory?id=${task.id}?newUserId=${currentUser.getId()}&action=supprimer" class="delete">
                                            <i class="material-icons" data-toggle="tooltip" title="Supprimer" style="color: darkred">&#xE872;</i>
                                        </a>
                                    </td>
                                </c:if>

                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>

<style>
    body{
        background:#eee;
    }
    .main-container{
        margin-top:40px;
    }
    .widget-author {
        margin-bottom: 58px;
    }
    .author-card {
        position: relative;
        padding-bottom: 48px;
        background-color: #fff;
        box-shadow: 0 12px 20px 1px rgba(64, 64, 64, .09);
    }
    .author-card .author-card-cover {
        position: relative;
        width: 100%;
        height: 100px;
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
    }
    .author-card .author-card-cover::after {
        display: block;
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        content: '';
        opacity: 0.5;
    }
    .author-card .author-card-cover > .btn {
        position: absolute;
        top: 12px;
        right: 12px;
        padding: 0 10px;
    }
    .author-card .author-card-profile {
        display: table;
        position: relative;
        margin-top: -22px;
        padding-right: 15px;
        padding-bottom: 16px;
        padding-left: 20px;
        z-index: 5;
    }
    .author-card .author-card-profile .author-card-avatar, .author-card .author-card-profile .author-card-details {
        display: table-cell;
        vertical-align: middle;
    }
    .author-card .author-card-profile .author-card-avatar {
        width: 85px;
        border-radius: 50%;
        box-shadow: 0 8px 20px 0 rgba(0, 0, 0, .15);
        overflow: hidden;
    }
    .author-card .author-card-profile .author-card-avatar > img {
        display: block;
        width: 100%;
    }
    .author-card .author-card-profile .author-card-details {
        padding-top: 20px;
        padding-left: 15px;
    }
    .author-card .author-card-profile .author-card-name {
        margin-bottom: 2px;
        font-size: 14px;
        font-weight: bold;
    }
    .author-card .author-card-profile .author-card-position {
        display: block;
        color: #8c8c8c;
        font-size: 12px;
        font-weight: 600;
    }
    .author-card .author-card-info {
        margin-bottom: 0;
        padding: 0 25px;
        font-size: 13px;
    }
    .author-card .author-card-social-bar-wrap {
        position: absolute;
        bottom: -18px;
        left: 0;
        width: 100%;
    }
    .author-card .author-card-social-bar-wrap .author-card-social-bar {
        display: table;
        margin: auto;
        background-color: #fff;
        box-shadow: 0 12px 20px 1px rgba(64, 64, 64, .11);
    }
    .btn-style-1.btn-white {
        background-color: #fff;
    }
    .list-group-item i {
        display: inline-block;
        margin-top: -1px;
        margin-right: 8px;
        font-size: 1.2em;
        vertical-align: middle;
    }
    .mr-1, .mx-1 {
        margin-right: .25rem !important;
    }

    .list-group-item.active:not(.disabled) {
        border-color: #e7e7e7;
        background: #fff;
        color: #ac32e4;
        cursor: default;
        pointer-events: none;
    }
    .list-group-flush:last-child .list-group-item:last-child {
        border-bottom: 0;
    }

    .list-group-flush .list-group-item {
        border-right: 0 !important;
        border-left: 0 !important;
    }

    .list-group-flush .list-group-item {
        border-right: 0;
        border-left: 0;
        border-radius: 0;
    }
    .list-group-item.active {
        z-index: 2;
        color: #fff;
        background-color: #007bff;
        border-color: #007bff;
    }
    .list-group-item:last-child {
        margin-bottom: 0;
        border-bottom-right-radius: .25rem;
        border-bottom-left-radius: .25rem;
    }
    a.list-group-item, .list-group-item-action {
        color: #404040;
        font-weight: 600;
    }
    .list-group-item {
        padding-top: 16px;
        padding-bottom: 16px;
        -webkit-transition: all .3s;
        transition: all .3s;
        border: 1px solid #e7e7e7 !important;
        border-radius: 0 !important;
        color: #404040;
        font-size: 12px;
        font-weight: 600;
        letter-spacing: .08em;
        text-transform: uppercase;
        text-decoration: none;
    }
    .list-group-item {
        position: relative;
        display: block;
        padding: .75rem 1.25rem;
        margin-bottom: -1px;
        background-color: #fff;
        border: 1px solid rgba(0,0,0,0.125);
    }
    .select-status {
        padding: 5px;
        border: none;
        border-radius: 5px;
        color: white;
    }

    .select-status.A_FAIRE {
        background-color: #ffc107; /* Jaune pour "A_FAIRE" */
    }

    .select-status.EN_COURS {
        background-color: #17a2b8; /* Bleu pour "EN_COURS" */
    }

    .select-status.TERMINEE {
        background-color: #28a745; /* Vert pour "TERMINEE" */
    }

</style>
</html>
