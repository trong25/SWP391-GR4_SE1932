<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Đơn Từ</title>
    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
</head>
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="header-student.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Đơn Đã Gửi</h1>
                <form action="sentapplications" id="myForm">
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
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Loại đơn</th>
                                    <th>Chi tiết</th>
                                    <th>Ngày gửi</th>
                                    <th>Ghi chú của người xử lý đơn</th>
                                    <th>Trạng thái</th>
                                    <th>Ngày xử lý đơn</th>
                                </tr>
                                </thead>
                                <tbody>
                                <div style="color: red">${requestScope.error}</div>
                                <c:forEach var="application" items="${requestScope.sentApplications}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${application.type.name}</td>
                                        <td>${application.details}</td>
                                        <td><fmt:formatDate value="${application.createdAt}" pattern="yyyy/MM/dd"/></td>
                                        <td>${application.processNote}</td>
                                        <c:set value="${application.status}" var="s"/>
                                        <c:if test="${s eq 'đã được duyệt'}">
                                            <td><span class="badge badge-success">${s}</span></td>
                                        </c:if>
                                        <c:if test="${s eq 'đang chờ xử lý'}">
                                            <td><span class="badge badge-warning">${s}</span>  </td>
                                        </c:if>
                                        <c:if test="${s eq 'không được duyệt'}">
                                            <td><span class="badge badge-danger">${s}</span>  </td>
                                        </c:if>
                                        <td><fmt:formatDate value="${application.processedAt}" pattern="yyyy/MM/dd"/></td>
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
