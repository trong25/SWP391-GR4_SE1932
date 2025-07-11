<%-- 
    Document   : newjsp
    Created on : 12 thg 7, 2025
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Danh Sách Học Sinh</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Test CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Học Sinh</h1>
                        <div class="row">
                            <div class="col-lg-6 mb-4">
                                <c:set var="yearChecked" value="${requestScope.checkYear}"/>
                                <form action="liststudent"  id="myForm">
                                    <div>
                                        <label >Chọn năm học</label>
                                        <select class="custom-select" style="width: 25%"  aria-label="Default select example" onchange="submitForm()" name="schoolYear">
                                            <c:forEach items="${requestScope.listSchoolYear}" var="year" >
                                                <option ${yearChecked eq year.id ? "selected" : ""} value="${year.id}"  >${year.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </form>
                            </div>

                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Danh sách học sinh</h6>
                                <h6 class="m-0 font-weight-bold text-primary">Lớp : <a style="margin-right: 60px" >${requestScope.teacherClass == null ?"Chưa được phân công":requestScope.teacherClass}</a>    Khối : <a>${requestScope.teacherGrade == null ?"Chưa được phân công":requestScope.teacherGrade}</a></h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Mã học sinh</th>
                                                <th>Ảnh</th>
                                                <th>Họ và tên</th>
                                                <th>Ngày sinh</th>
                                                <th>Địa chỉ</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <div style="color: red">${requestScope.error}</div>
                                        <c:forEach var="student" items="${requestScope.liststudent}" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>
                                                <td>${student.id}</td>
                                                <td style="width: 20%;">
                                                    <img src="../images/${student.avatar}"
                                                         class="mx-auto d-block"
                                                         style="width:100px; height:100px; object-fit: cover;">
                                                </td>
                                                <td>${student.lastName} ${student.firstName}</td>
                                                <td><fmt:formatDate value="${student.birthday}" pattern="yyyy/MM/dd" /></td>
                                                <td>${student.address}</td>
                                                <td>
                                                    <form action="studentprofile" method="Post">
                                                        <input hidden value="view" name="action">
                                                        <input hidden value="${student.id}" name="id"/>
                                                        <button type="submit" class="btn btn-primary">Thông tin chi tiết</button>
                                                    </form>
                                                </td>
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



