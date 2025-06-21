<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách nhân viên chờ phê duyệt</title>

    <!-- Thư viện toastr -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

    <!-- FontAwesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,300,400,700" rel="stylesheet">

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <!-- DataTables CSS -->
    <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>

<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <main class="app-content">
                    <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Nhân Viên Chờ Phê Duyệt</h1>

                    <!-- Nút quay lại -->
                    <div class="row">
                        <div class="col-lg-3 float-left mb-4">
                            <button class="btn btn-primary" onclick="redirect()">Danh sách nhân viên</button>
                        </div>
                    </div>

                    <!-- Hiển thị lỗi -->
                    <c:if test="${not empty requestScope.error}">
                        <div style="color: red; font-weight: bold;">${requestScope.error}</div>
                    </c:if>
                    <c:if test="${empty waitlistpersonnel}">
                        <div style="color: orange;">Không có nhân sự nào đang chờ phê duyệt.</div>
                    </c:if>

                    <!-- Bảng danh sách -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Danh Sách Nhân Viên Chờ Phê Duyệt</h6>
                        </div>

                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Ảnh</th>
                                        <th>Mã Nhân Viên</th>
                                        <th>Tên</th>
                                        <th>Giới Tính</th>
                                        <th>Ngày sinh</th>
                                        <th>Chức vụ</th>
                                        <th>Trạng thái</th>
                                        <th>Chi tiết</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${waitlistpersonnel}" var="p" varStatus="status">
                                        <tr>
                                            <th scope="row">${status.index + 1}</th>
                                            <td>
                                                <c:if test="${not empty p.getAvatar() and p.getAvatar() != 'null' and p.getAvatar() != 'undefined'}">
                                                    <img class="profile_img" src="${pageContext.request.contextPath}/images/${p.getAvatar()}" alt="ảnh nhân viên"
                                                         width="191px" height="263px" style="object-fit: cover;">
                                                </c:if>
                                                <c:if test="${empty p.getAvatar() or p.getAvatar() == 'null' or p.getAvatar() == 'undefined'}">
                                                    <span>Không có ảnh</span>
                                                </c:if>
                                            </td>
                                            <td>${p.getId()}</td>
                                            <td>${p.getLastName()} ${p.getFirstName()}</td>
                                            <td>
                                                <c:if test="${p.getGender() == true}">Nam</c:if>
                                                <c:if test="${p.getGender() == false}">Nữ</c:if>
                                            </td>
                                            <td><fmt:formatDate value="${p.getBirthday()}" pattern="yyyy/MM/dd"/> </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${p.getRoleId() == 0}">Nhân viên IT</c:when>
                                                    <c:when test="${p.getRoleId() == 1}">Hiệu trưởng</c:when>
                                                    <c:when test="${p.getRoleId() == 2}">Giáo vụ</c:when>
                                                    <c:when test="${p.getRoleId() == 3}">Nhân viên kế toán</c:when>
                                                    <c:when test="${p.getRoleId() == 4}">Giáo viên</c:when>
                                                    <c:otherwise>Chưa xác định</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <c:if test="${not empty p.getStatus()}">
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${p.getStatus() == 'đang làm việc'}">
                                                            <span class="badge badge-success">${p.getStatus()}</span>
                                                        </c:when>
                                                        <c:when test="${p.getStatus() == 'đang chờ xử lý'}">
                                                            <span class="badge badge-warning">${p.getStatus()}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-info">${p.getStatus()}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </c:if>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/director/viewpersonnel?id=${p.getId()}&page=waitlist"
                                                   class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Thông tin chi tiết</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>

<!-- Toast message và redirect script -->
<script>
    $(document).ready(function () {
        var toastMessage = '<%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>';
        var toastType = '<%= request.getAttribute("type") != null ? request.getAttribute("type") : "" %>';
        if (toastMessage) {
            if (toastType === 'success') {
                toastr.success(toastMessage);
            } else if (toastType === 'fail') {
                toastr.error(toastMessage);
            }
        }

        // Khởi tạo DataTable
        $('#dataTable').DataTable();
    });

    function redirect() {
        window.location.href = "${pageContext.request.contextPath}/director/listpersonnel";
    }
</script>

<!-- Page level plugins -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap4.min.js"></script>
</body>
</html>