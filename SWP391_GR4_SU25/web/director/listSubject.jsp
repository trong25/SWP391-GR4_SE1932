<%-- 
    Document   : listSubject
    Created on : Jul 6, 2025, 11:20:55 PM
    Author     : ThanhNT
--%>


<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

        <title>Danh sách môn học</title>


        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Môn Học</h1>
                        <form method="post" action="listsubject" id="myForm">
                            <div class="col-lg-3 mb-4">
                                <label for="selectStatus">Chọn trạng thái</label>
                                <select class="custom-select" id="selectStatus" aria-label="Default select example" onchange="submitForm()" name="status">
                                    <option ${param.status eq 'approve'? "selected" :""}  value="approve">Đã được duyệt</option>
                                    <option  ${param.status eq 'pending'? "selected" :""} value="pending">Đang chờ xử lý</option>
                                    <option  ${param.status eq 'decline'? "selected" :""} value="decline">Không được phê duyệt</option>

                                </select>
                            </div>
                        </form>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Môn Học</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Tên môn học</th>
                                                <th>Khối</th>
                                                <th>Trạng thái</th>
                                                <th>Mô tả</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="subject" items="${requestScope.listSubject}" varStatus="status">
                                                <tr>
                                                    <th scope="row">${status.index + 1}</th>
                                                    <td>${subject.name}</td>
                                                    <td>${subject.grade.name}</td>
                                                    <c:set value="${subject.status}" var="status"/>
                                                    <c:if test="${status eq 'đã được duyệt'}">
                                                        <td><span class="badge badge-success">${status}</span></td>
                                                        </c:if>
                                                        <c:if test="${status eq 'đang chờ xử lý'}">
                                                        <td><span class="badge badge-warning">${status}</span>  </td>
                                                    </c:if>
                                                    <c:if test="${status eq 'không được duyệt'}">
                                                        <td><span class="badge badge-danger">${status}</span>  </td>
                                                    </c:if>
                                                    <td>${subject.description}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>
    </body>

</html>
