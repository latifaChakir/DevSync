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

    <style>
        body {
            background: #eee;
        }
        .card-box {
            position: relative;
            color: #fff;
            padding: 20px 10px 40px;
            margin: 20px 0px;
        }
        .card-box:hover {
            text-decoration: none;
            color: #f1f1f1;
        }
        .card-box:hover .icon i {
            font-size: 100px;
            transition: 1s;
            -webkit-transition: 1s;
        }
        .card-box .inner {
            padding: 5px 10px 0 10px;
        }
        .card-box h3 {
            font-size: 27px;
            font-weight: bold;
            margin: 0 0 8px 0;
            white-space: nowrap;
            padding: 0;
            text-align: left;
        }
        .card-box p {
            font-size: 15px;
        }
        .card-box .icon {
            position: absolute;
            top: auto;
            bottom: 5px;
            right: 5px;
            z-index: 0;
            font-size: 72px;
            color: rgba(0, 0, 0, 0.15);
        }
        .card-box .card-box-footer {
            position: absolute;
            left: 0px;
            bottom: 0px;
            text-align: center;
            padding: 3px 0;
            color: rgba(255, 255, 255, 0.8);
            background: rgba(0, 0, 0, 0.1);
            width: 100%;
            text-decoration: none;
        }
        .card-box:hover .card-box-footer {
            background: rgba(0, 0, 0, 0.3);
        }
        .bg-blue {
            background-color: #00c0ef !important;
        }
        .bg-green {
            background-color: #00a65a !important;
        }
        .bg-orange {
            background-color: #f39c12 !important;
        }
        .bg-red {
            background-color: #d9534f !important;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="sidebar.jsp" />
        <div class="col-md-10">
            <div class="col-lg-12">
                <h1>Dashboard</h1>
                <ol class="breadcrumb">
                    <li class="active"><i class="fa fa-dashboard"></i> Dashboard</li>
                </ol>
            </div>
            <div class="row">
                <div class="col-lg-3 col-sm-6">
                    <div class="card-box bg-blue">
                        <div class="inner">
                            <h3>${totalCreatedTasks}</h3>
                            <p> tâches créées </p>
                        </div>
                        <div class="icon">
                            <i class="fa fa-graduation-cap" aria-hidden="true"></i>
                        </div>
                        <a href="#" class="card-box-footer">View More <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>

                <div class="col-lg-3 col-sm-6">
                    <div class="card-box bg-green">
                        <div class="inner">
                            <h3> ${completedCreatedTasks} </h3>
                            <p> tâches terminées  </p>
                        </div>
                        <div class="icon">
                            <i class="fa fa-money" aria-hidden="true"></i>
                        </div>
                        <a href="#" class="card-box-footer">View More <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="card-box bg-orange">
                        <div class="inner">
                            <h3> ${inProgressCreatedTasks} </h3>
                            <p> en cours </p>
                        </div>
                        <div class="icon">
                            <i class="fa fa-user-plus" aria-hidden="true"></i>
                        </div>
                        <a href="#" class="card-box-footer">View More <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <div class="col-lg-3 col-sm-6">
                    <div class="card-box bg-red">
                        <div class="inner">
                            <h3> ${notStartedCreatedTasks} </h3>
                            <p>non commencées </p>
                        </div>
                        <div class="icon">
                            <i class="fa fa-users"></i>
                        </div>
                        <a href="#" class="card-box-footer">View More <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3 col-sm-6">
                    <div class="card-box bg-red">
                        <div class="inner">
                            <h3> ${notEffectuedCreatedTasks} </h3>
                            <p>non effectuées </p>
                        </div>
                        <div class="icon">
                            <i class="fa fa-users"></i>
                        </div>
                        <a href="#" class="card-box-footer">View More <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>
