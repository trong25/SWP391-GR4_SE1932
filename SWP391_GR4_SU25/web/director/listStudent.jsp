<%--
  Created by IntelliJ IDEA.
  User: ThanhNT
  Date: 23/06/2025
  Time: 8:47 PM
 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

        <title>Danh sách học sinh</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <script>
            function resetClassAndSubmitForm() {
                document.getElementById("classes").selectedIndex = 0;
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
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Học Sinh</h1>
                        <div class="row align-items-center">
                            <c:set var="schoolYearSelect" value="${requestScope.schoolYearSelect}"/>
                            <c:set var="classesSelect" value="${requestScope.classSelect}"/>

                            <!-- Form section with select elements -->
                            <div class="col-lg-8 d-flex">
                                <form action="listpupil" id="myForm" class="d-flex w-100">
                                    <div class="flex-grow-1 mb-4">
                                        <label>Chọn năm học</label>
                                        <select class="custom-select " aria-label="Default select example" onchange="resetClassAndSubmitForm()" name="schoolYear" style="width: 40%">
                                            <c:forEach items="${requestScope.listSchoolYear}" var="year">
                                                <option ${schoolYearSelect eq year.id ? "selected" : ""} value="${year.id}">${year.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="me-3 flex-grow-1 mb-4 ">
                                        <label>Chọn lớp</label>
                                        <select id="classes" class="custom-select " aria-label="Default select example" onchange="submitForm()" name="classes" style="width: 40%">
                                            <option value="">Chọn lớp</option>
                                            <c:forEach items="${requestScope.listClass}" var="c">
                                                <option ${classesSelect eq c.id ? "selected" : ""} value="${c.id}">${c.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                </form>
                            </div>
                        </div>



                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Danh sách học sinh</h6>
                                <c:if test="${classesSelect!=null}">
                                    <c:choose>
                                        <c:when test="${requestScope.grade == null}">
                                            <h6 class="m-0 font-weight-bold text-primary">Lớp: <a
                                                    style="color: red">Chưa chọn lớp</a>
                                            </h6>
                                        </c:when>
                                        <c:otherwise>
                                            <h6 class="m-0 font-weight-bold text-primary">Lớp: <a
                                                    >${requestScope.grade}</a>
                                            </h6>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${requestScope.teacherName eq 'null null'}">
                                            <h6 class="m-0 font-weight-bold text-primary">Giáo viên: <a
                                                    style="color: red">Chưa được phân công</a>
                                            </h6>
                                        </c:when>
                                        <c:when test="${requestScope.teacherName == null}">
                                            <h6 class="m-0 font-weight-bold text-primary">Giáo viên: <a
                                                    style="color: red">Chưa được phân công</a>
                                            </h6>
                                        </c:when>
                                        <c:otherwise>
                                            <h6 class="m-0 font-weight-bold text-primary">Giáo viên: <a
                                                    >${requestScope.teacherName}</a>
                                            </h6>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
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
                                        <c:forEach var="pupil" items="${requestScope.listPupil}" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>
                                                <td>${pupil.id}</td>
                                                <td style="width: 20%;">
                                                    <img src="../images/${pupil.avatar}"
                                                         class="mx-auto d-block"
                                                         style=" width: 220px;
                                                         height: 280px;
                                                         object-fit: cover;
                                                         border-radius: 10px;
                                                         box-shadow: 0 4px 8px rgba(0,0,0,0.2);
                                                         border: 1px solid #ccc; ">
                                                </td>
                                                <td>${pupil.lastName} ${pupil.firstName}</td>
                                                <td><fmt:formatDate value="${pupil.birthday}" pattern="yyyy/MM/dd" /></td>
                                                <td>${pupil.address}</td>
                                                <td>
                                                    <form action="pupilprofile" method="post">
                                                        <input name="id" value="${pupil.id}" hidden/>
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



