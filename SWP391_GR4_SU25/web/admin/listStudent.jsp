<%--
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
                            <c:set var="sltedstatus" value="${requestScope.selectedStatus}" />

                            <div class="class-form">
                                <form action="listpupil" method="get" class="class-form">
                                    <label>Trạng thái
                                        <select name="status" onchange="this.form.submit()" class="custom-select">
                                            <option value="" hidden>Trạng thái</option>

                                            <!-- Duyệt các trạng thái -->
                                            <c:forEach items="${requestScope.statuss}" var="r">
                                                <option value="${r}" ${sltedstatus eq r ? "selected" : ""}>${r}</option>
                                            </c:forEach>

                                            <!-- Tùy chọn: Hiện toàn bộ trạng thái -->
                                            <option value="all" ${sltedstatus eq 'all' or empty sltedstatus ? "selected" : ""}>Hiện toàn bộ trạng thái</option>
                                        </select>
                                    </label>
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
                                                <th>Trạng thái</th>
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
                                                    <c:choose>
                                                        <c:when test="${pupil.status == 'đang theo học'}">
                                                            <span class="badge badge-success">${pupil.status}</span>
                                                        </c:when>
                                                        <c:when test="${pupil.status == 'đang chờ xử lý'}">
                                                            <span class="badge badge-warning">${pupil.status}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-info">${pupil.status}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>

                                                <td>
                                                    <form action="studentsprofile" method="post">
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



