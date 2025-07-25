<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý Bảng Lương</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script>
            $(document).ready(function () {
                var toastMessage = '<%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>';
                var toastType = '<%= request.getAttribute("type") != null ? request.getAttribute("type") : "" %>';
                if (toastMessage && toastMessage !== 'null') {
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'fail') {
                        toastr.error(toastMessage);
                        $('#newSalaryModal').modal('show');
                    }
                }
            });
        </script>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Bảng Lương Của Nhân Viên</h1>
                        <div class="row">
                            <c:set var="sltedrole" value="${requestScope.selectedrole}"/>
                            <c:set var="sltedstatus" value="${requestScope.selectedstatus}"/>
                            <c:set var="vietnamesePattern"
                                   value="ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ"/>
                            <div class="col-lg-4">
                                <form action="listpersonnell" method="post" id="myForm">
                                    <div style="display: flex; justify-content: space-between">
                                        <div class="class-form">
                                            <label>Chức vụ
                                                <select name="role" onchange="submitForm()" class="custom-select">
                                                    <option value="" hidden>Chức vụ</option>
                                                    <c:forEach items="${requestScope.roles}" var="r">
                                                        <option ${sltedrole eq r.getId() ? "selected" : ""}
                                                            value="${r.getId()}">${r.getVNeseDescription()}</option>
                                                    </c:forEach>
                                                    <c:if test="${sltedrole eq 'all'}">
                                                        <option value="all" selected>Hiện toàn bộ chức vụ</option>
                                                    </c:if>
                                                    <c:if test="${sltedrole ne 'all'}">
                                                        <option value="all">Hiện toàn bộ chức vụ</option>
                                                    </c:if>
                                                </select>
                                            </label>
                                        </div>
                                        <div class="class-form">
                                            <label>Tháng
                                                <select name="month" onchange="submitForm()" class="custom-select">
                                                    <option value="" hidden>Chọn tháng</option>
                                                    <c:forEach begin="1" end="12" var="m">
                                                        <option value="${m}" ${param.month == m || param.month == m.toString() ? "selected" : ""}>Tháng ${m}</option>
                                                    </c:forEach>
                                                    <option value="all" ${param.month == 'all' ? "selected" : ""}>Tất cả các tháng</option>
                                                </select>
                                            </label>
                                        </div>
                                        <div class="class-form">
                                            <label>Trạng thái
                                                <select name="status" onchange="submitForm()" class="custom-select">
                                                    <option value="" hidden>Trạng thái</option>
                                                    <c:forEach items="${requestScope.statuss}" var="r">
                                                        <option value="${r}" ${sltedstatus eq r ? "selected" : ""}>
                                                            <c:choose>
                                                                <c:when test="${r == 'đã thanh toán'}">Đã thanh toán</c:when>
                                                                <c:when test="${r == 'chưa thanh toán'}">Chưa thanh toán</c:when>
                                                                <c:otherwise>${r}</c:otherwise>
                                                            </c:choose>
                                                        </option>
                                                    </c:forEach>
                                                    <option value="all" ${sltedstatus eq 'all' ? "selected" : ""}>Hiện toàn bộ trạng thái</option>
                                                </select>
                                            </label>
                                        </div>
                                    </div>
                                </form>
                                <style>
                                    .btn {
                                        margin: 0px 0px 0px 10px;
                                    }
                                    option[hidden] {
                                        display: none;
                                    }
                                </style>
                            </div>
                            <div class="card shadow mb-4 col-12">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">Danh Sách Bảng Lương Của Nhân Viên</h6>
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
                                                    <th>Năm Kinh Nghiệm</th>
                                                    <th>Trình Độ</th>
                                                    <th>Tháng</th>
                                                    <th>Năm</th>
                                                    <th>Số ngày công</th>                                                  
                                                    <th>Tổng lương</th>
                                                    <th>Trạng thái thanh toán</th>
                                                    <th>Ngày Thanh Toán</th>
                                                    <th>Chi tiết</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:if test="${not empty requestScope.error}">
                                                    <div style="color: red">${requestScope.error}</div>
                                                </c:if>
                                                <c:if test="${empty persons}">
                                                    <tr>
                                                        <td colspan="16" class="text-center text-muted">Không có dữ liệu nhân viên.</td>
                                                    </tr>
                                                </c:if>
                                                <c:forEach items="${persons}" var="p" varStatus="status">
                                                    <c:forEach items="${p.salaries}" var="salary">
                                                        <c:if test="${salary.salaryMonth != -1}">
                                                            <tr>
                                                                <th scope="row">${status.index + 1}</th>
                                                                <td><img class="profile_img" src="../images/${p.avatar}" alt="Ảnh nhân viên" width="191px" height="263px" style="object-fit: cover;"></td>
                                                                <td>${p.id}</td>
                                                                <td>${p.lastName} ${p.firstName}</td>
                                                                <td>
                                                                    <c:if test="${p.gender}">Nam</c:if>
                                                                    <c:if test="${!p.gender}">Nữ</c:if>
                                                                </td>
                                                                <td><fmt:formatDate value="${p.birthday}" pattern="yyyy/MM/dd"/></td>
                                                                <td>
                                                    <c:if test="${p.getRoleId()== 0}">
                                                        Nhân viên IT
                                                    </c:if>
                                                    <c:if test="${p.getRoleId()==1}">
                                                        Giám Đốc
                                                    </c:if>
                                                    <c:if test="${p.getRoleId()==2}">
                                                        Giáo vụ
                                                    </c:if>
                                                    <c:if test="${p.getRoleId()==3}">
                                                        Giáo viên
                                                    </c:if>
                                                    <c:if test="${p.getRoleId()==5}">
                                                        Nhân viên kế toán
                                                    </c:if>
                                                </td>
                                                                <td>${p.teaching_years != null ? p.teaching_years : '-'}</td>
                                                                <td>${p.qualification != null ? p.qualification : '-'}</td>
                                                                <td>${salary.salaryMonth}</td>
                                                                <td>${salary.salaryYear}</td>
                                                                <td>${salary.workingDays}</td>
                                                                <td>
                                                                    <fmt:formatNumber value="${salary.totalSalary}" type="number" groupingUsed="true"/> VND
                                                                </td>
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${salary.paymentStatus == 'đã thanh toán'}">
                                                                            <span class="badge badge-success">Đã thanh toán</span>
                                                                        </c:when>
                                                                        <c:when test="${salary.paymentStatus == 'chưa thanh toán'}">
                                                                            <span class="badge badge-warning">Chưa thanh toán</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span class="badge badge-info">${salary.paymentStatus}</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </td>
                                                                <td>${salary.paymentDate}</td>
                                                                <td>
                                                                    <a href="viewpersonnel?id=${p.id}&month=${salary.salaryMonth}&year=${salary.salaryYear}" class="btn btn-sm btn-primary shadow-sm">Thanh Toán Lương</a>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
                <script src="js/jquery-3.2.1.min.js"></script>
                <script src="js/popper.min.js"></script>
                <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
                <script src="js/bootstrap.min.js"></script>
                <script src="js/main.js"></script>
                <script src="js/plugins/pace.min.js"></script>
                <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
                <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>
                <script src="../js/demo/datatables-demo.js"></script>
    </body>
</html>