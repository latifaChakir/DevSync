<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Bootstrap CRUD Data Table for Database with Modal Form | Appfinz Technologies</title>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-xl">
  <div class="table-responsive">
    <div class="table-wrapper">
      <div class="table-title">
        <div class="row">
          <div class="col-sm-6">
            <h2>Manage <b>Tags</b></h2>
          </div>
          <div class="col-sm-6">
            <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New tag</span></a>
            <a href="#deleteEmployeeModal" class="btn btn-danger" data-toggle="modal"><i class="material-icons">&#xE15C;</i> <span>Delete</span></a>
          </div>
        </div>
      </div>
      <table class="table table-striped table-hover">
        <thead>
        <tr>
          <th>
              <span class="custom-checkbox">
                  <input type="checkbox" id="selectAll">
                  <label for="selectAll"></label>
               </span>
          </th>
          <th>id</th>
          <th>Name</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tag" items="${tags}">
          <tr>
            <td>
				<span class="custom-checkbox">
					<input type="checkbox" id="checkbox5" name="options[]" value="1">
                    <label for="checkbox5"></label>
                </span>
            </td>
            <td><c:out value="${tag.id}" /></td>
            <td><c:out value="${tag.name}" /></td>
            <td>
              <a href="tags?id=${tag.id}&action=edit" class="edit" >
                <i class="material-icons"  title="Edit">&#xE254;</i>
              </a>
              <form action="tags?action=delete" method="post" style="display:inline;">
                <input type="hidden" name="id" value="${tag.id}">
                <button type="submit" class="delete-button" style="background:none; border:none; color:inherit; padding:0; font: inherit; cursor: pointer;">
                  <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i>
                </button>
              </form>

            </td>

          </tr>
        </c:forEach>
        </tbody>
      </table>
      <div class="clearfix">
        <div class="hint-text">Showing <b>5</b> out of <b>25</b> entries</div>
        <ul class="pagination">
          <li class="page-item disabled"><a href="#">Previous</a></li>
          <li class="page-item"><a href="#" class="page-link">1</a></li>
          <li class="page-item"><a href="#" class="page-link">2</a></li>
          <li class="page-item active"><a href="#" class="page-link">3</a></li>
          <li class="page-item"><a href="#" class="page-link">4</a></li>
          <li class="page-item"><a href="#" class="page-link">5</a></li>
          <li class="page-item"><a href="#" class="page-link">Next</a></li>
        </ul>
      </div>
    </div>
  </div>
</div>
<div id="addEmployeeModal" class="modal fade bd-example-modal-lg">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content">
      <form action="tags?action=add" method="post">
        <div class="modal-header">
          <h4 class="modal-title">Add Tag</h4>
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>Name</label>
            <input type="text" name="name" class="form-control" required>
          </div>

          </div>
        <div class="modal-footer">
          <input type="button" class="btn btn-primary" data-dismiss="modal" value="Cancel">
          <input type="submit" class="btn btn-success" value="Add">
        </div>

      </form>
    </div>
  </div>
</div>
<style>
  body {
    color: #566787;
    background: #f5f5f5;
    font-family: 'Varela Round', sans-serif;
    font-size: 13px;
  }
  .table-responsive {
    margin: 30px 0;
  }
  .table-wrapper {
    background: #fff;
    padding: 20px 25px;
    border-radius: 3px;
    min-width: 1000px;
    box-shadow: 0 1px 1px rgba(0,0,0,.05);
  }
  .table-title {
    padding-bottom: 15px;
    background: #435d7d;
    color: #fff;
    padding: 16px 30px;
    min-width: 100%;
    margin: -20px -25px 10px;
    border-radius: 3px 3px 0 0;
  }
  .table-title h2 {
    margin: 5px 0 0;
    font-size: 24px;
  }
  .table-title .btn-group {
    float: right;
  }
  .table-title .btn {
    color: #fff;
    float: right;
    font-size: 13px;
    border: none;
    min-width: 50px;
    border-radius: 2px;
    border: none;
    outline: none !important;
    margin-left: 10px;
  }
  .table-title .btn i {
    float: left;
    font-size: 21px;
    margin-right: 5px;
  }
  .table-title .btn span {
    float: left;
    margin-top: 2px;
  }
  table.table tr th, table.table tr td {
    border-color: #e9e9e9;
    padding: 12px 15px;
    vertical-align: middle;
  }
  table.table tr th:first-child {
    width: 60px;
  }
  table.table tr th:last-child {
    width: 100px;
  }
  table.table-striped tbody tr:nth-of-type(odd) {
    background-color: #fcfcfc;
  }
  table.table-striped.table-hover tbody tr:hover {
    background: #f5f5f5;
  }
  table.table th i {
    font-size: 13px;
    margin: 0 5px;
    cursor: pointer;
  }
  table.table td:last-child i {
    opacity: 0.9;
    font-size: 22px;
    margin: 0 5px;
  }
  table.table td a {
    font-weight: bold;
    color: #566787;
    display: inline-block;
    text-decoration: none;
    outline: none !important;
  }
  table.table td a:hover {
    color: #2196F3;
  }
  table.table td a.edit {
    color: #FFC107;
  }
  table.table td a.delete {
    color: #F44336;
  }
  table.table td i {
    font-size: 19px;
  }
  table.table .avatar {
    border-radius: 50%;
    vertical-align: middle;
    margin-right: 10px;
  }
  .pagination {
    float: right;
    margin: 0 0 5px;
  }
  .pagination li a {
    border: none;
    font-size: 13px;
    min-width: 30px;
    min-height: 30px;
    color: #999;
    margin: 0 2px;
    line-height: 30px;
    border-radius: 2px !important;
    text-align: center;
    padding: 0 6px;
  }
  .pagination li a:hover {
    color: #666;
  }
  .pagination li.active a, .pagination li.active a.page-link {
    background: #03A9F4;
  }
  .pagination li.active a:hover {
    background: #0397d6;
  }
  .pagination li.disabled i {
    color: #ccc;
  }
  .pagination li i {
    font-size: 16px;
    padding-top: 6px
  }
  .hint-text {
    float: left;
    margin-top: 10px;
    font-size: 13px;
  }
  /* Custom checkbox */
  .custom-checkbox {
    position: relative;
  }
  .custom-checkbox input[type="checkbox"] {
    opacity: 0;
    position: absolute;
    margin: 5px 0 0 3px;
    z-index: 9;
  }
  .custom-checkbox label:before{
    width: 18px;
    height: 18px;
  }
  .custom-checkbox label:before {
    content: '';
    margin-right: 10px;
    display: inline-block;
    vertical-align: text-top;
    background: white;
    border: 1px solid #bbb;
    border-radius: 2px;
    box-sizing: border-box;
    z-index: 2;
  }
  .custom-checkbox input[type="checkbox"]:checked + label:after {
    content: '';
    position: absolute;
    left: 6px;
    top: 3px;
    width: 6px;
    height: 11px;
    border: solid #000;
    border-width: 0 3px 3px 0;
    transform: inherit;
    z-index: 3;
    transform: rotateZ(45deg);
  }
  .custom-checkbox input[type="checkbox"]:checked + label:before {
    border-color: #03A9F4;
    background: #03A9F4;
  }
  .custom-checkbox input[type="checkbox"]:checked + label:after {
    border-color: #fff;
  }
  .custom-checkbox input[type="checkbox"]:disabled + label:before {
    color: #b8b8b8;
    cursor: auto;
    box-shadow: none;
    background: #ddd;
  }
  /* Modal styles */
  .modal .modal-dialog {
    max-width: 400px;
  }
  .modal .modal-header, .modal .modal-body, .modal .modal-footer {
    padding: 20px 30px;
  }
  .modal .modal-content {
    border-radius: 3px;
    font-size: 14px;
  }
  .modal .modal-footer {
    background: #ecf0f1;
    border-radius: 0 0 3px 3px;
  }
  .modal .modal-title {
    display: inline-block;
  }
  .modal .form-control {
    border-radius: 2px;
    box-shadow: none;
    border-color: #dddddd;
  }
  .modal textarea.form-control {
    resize: vertical;
  }
  .modal .btn {
    border-radius: 2px;
    min-width: 100px;
  }
  .modal form label {
    font-weight: normal;
  }
</style>
</body>
</html>