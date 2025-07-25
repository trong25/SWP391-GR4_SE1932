<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Đơn Từ</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Đơn Từ</h1>
                <form action="application" id="myForm">
                    <div class="row">
                        <div class="col-lg-2 mb-4">
                            <label for="selectStatus">Chọn trạng thái</label>
                            <select class="custom-select" id="selectStatus" aria-label="Default select example" onchange="submitForm()" name="status">
                                <option ${param.status eq 'all'? "selected" :""} value="all">Tất cả</option>
                                <option  ${param.status eq 'đang chờ xử lý'? "selected" :""} value="đang chờ xử lý">Đang chờ xử lý</option>
                                <option ${param.status eq 'đã được duyệt'? "selected" :""}  value="đã được duyệt">Đã được duyệt</option>
                                <option  ${param.status eq 'không được duyệt'? "selected" :""} value="không được duyệt">Không được duyệt</option>
                            </select>
                        </div>
                    </div>
                </form>

                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Danh Sách Đơn Từ</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <jsp:useBean id="bean" class="model.student.StudentDAO"/>
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Loại đơn</th>
                                    <th>Người gửi</th>
                                    <th>Ngày nghỉ</th>
                                    <th>Trạng thái</th>
                                    <th>Chi tiết</th>
                                </tr>
                                </thead>
                                <tbody>
                                <div style="color: red">${requestScope.error}</div> <c:forEach var="application" items="${requestScope.applications}" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td>${application.type.name}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty bean.getStudentByUserId(application.createdBy)}">
                                                    ${bean.getStudentByUserId(application.createdBy).lastName} ${bean.getStudentByUserId(application.createdBy).firstName}
                                                </c:when>
                                                <c:otherwise>
                                                    <span style="color:red;">Không tìm thấy</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:if test="${not empty application.startDate && not empty application.endDate}">
                                                <fmt:formatDate value="${application.startDate}" pattern="yyyy/MM/dd"/> đến <fmt:formatDate value="${application.endDate}" pattern="yyyy/MM/dd"/>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${application.status eq 'đã được duyệt'}">
                                                    <span class="badge badge-success">${application.status}</span>
                                                </c:when>
                                                <c:when test="${application.status eq 'đang chờ xử lý'}">
                                                    <span class="badge badge-warning">${application.status}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-danger">${application.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a href="applicationdetails?id=${application.id}" class="btn btn-primary">Chi tiết</a>
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
