<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
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
                }
            }
        });
    </script>
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    <style>
        #style-span {
            padding: 11px 150px;
            margin-top: 10px;
            border-radius: 20px;
            margin-bottom: 15px;
        }
        table.table-bordered, table.table-bordered th, table.table-bordered td {
            border: 2px solid black;
            text-align: left;
        }
        .btn-primary, .btn-info {
            cursor: pointer;
            padding: 5px 0;
            display: block;
            width: 100%;
        }
        .btn-primary:hover, .btn-info:hover {
            background-color: white;
            border: 1px grey solid;
        }
    </style>
</head>
<body id="page-top">
    <div id="wrapper">
        <jsp:include page="navbar.jsp"/>
        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <jsp:include page="../header.jsp"/>
                <div class="container-fluid">
                    <div style="display: flex; justify-content: center">
                        <div style="display: block; border: 2px solid black; border-radius: 5px;">
                            <header style="background-color: #000; color: #fff; padding: 10px 200px;">THÔNG TIN NHÂN VIÊN</header>
                        </div>
                    </div>

                    <c:if test="${person == null}">
                        <div style="color: red; text-align: center; margin: 20px;">
                            Không tìm thấy thông tin nhân viên.
                        </div>
                        <div style="text-align: center;">
                            <a href="listpersonnell" class="btn btn-info">Quay lại danh sách</a>
                        </div>
                    </c:if>

                    <c:if test="${person != null}">
                        <section>
                            <div class="rt-container">
                                <div class="col-rt-12">
                                    <div class="Scriptcontent">
                                        <div class="student-profile py-4">
                                            <div class="container">
                                                <div class="row">
                                                    <div class="col-lg-4">
                                                        <div class="card shadow-sm">
                                                            <div class="card-header bg-transparent text-center">
                                                                <img class="profile_img" src="../images/${person.avatar}" alt="Ảnh nhân viên" width="191px" height="263px" style="object-fit: cover;">
                                                            </div>
                                                            <div class="card-body">
                                                                <p class="mb-0"><strong>Mã nhân viên:</strong> ${person.id}</p>
                                                                <p class="mb-0"><strong>Tên:</strong> ${person.lastName} ${person.firstName}</p>
                                                                <p class="mb-0"><strong>Chức vụ:</strong>
                                                                    <c:choose>
                                                                        <c:when test="${person.roleId == 0}">Nhân viên IT</c:when>
                                                                        <c:when test="${person.roleId == 2}">Giáo vụ</c:when>
                                                                        <c:when test="${person.roleId == 3}">Giáo viên</c:when>
                                                                        <c:when test="${person.roleId == 5}">Nhân viên kế toán</c:when>
                                                                        <c:otherwise>Khác</c:otherwise>
                                                                    </c:choose>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-8">
                                                        <div class="card shadow-sm">
                                                            <div class="card-header bg-transparent border-0">
                                                                <h3 class="mb-0">Thông tin chi tiết</h3>
                                                            </div>
                                                            <div class="card-body pt-0">
                                                                <table class="table table-bordered">
                                                                    <tr>
                                                                        <th>Ngày sinh</th>
                                                                        <td><fmt:formatDate value="${person.birthday}" pattern="yyyy/MM/dd"/></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Tình trạng thanh toán</th>
                                                                        <td>
                                                                            <c:set var="selectedSalary" value="${null}"/>
                                                                            <c:forEach items="${person.salaries}" var="salary">
                                                                                <c:if test="${salary.salaryMonth == selectedMonth && salary.salaryYear == selectedYear}">
                                                                                    <c:set var="selectedSalary" value="${salary}"/>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                            <c:choose>
                                                                                <c:when test="${selectedSalary != null}">
                                                                                    <c:choose>
                                                                                        <c:when test="${selectedSalary.paymentStatus == 'đã thanh toán'}">
                                                                                            <span class="badge badge-success">Đã thanh toán</span>
                                                                                        </c:when>
                                                                                        <c:when test="${selectedSalary.paymentStatus == 'chưa thanh toán'}">
                                                                                            <span class="badge badge-warning">Chưa thanh toán</span>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <span class="badge badge-info">${selectedSalary.paymentStatus}</span>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <span class="badge badge-info">Chưa có dữ liệu lương</span>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Giới tính</th>
                                                                        <td>
                                                                            <c:if test="${person.gender}">Nam</c:if>
                                                                            <c:if test="${!person.gender}">Nữ</c:if>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Trình độ</th>
                                                                        <td>${person.qualification != null ? person.qualification : '-'}</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Năm kinh nghiệm</th>
                                                                        <td>${person.teaching_years != null ? person.teaching_years : '-'}</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Lương cơ bản</th>
                                                                        <td>
                                                                            <c:if test="${selectedSalary != null}">
                                                                                <fmt:formatNumber value="${selectedSalary.baseSalary}" type="currency" currencySymbol="₫"/>
                                                                            </c:if>
                                                                            <c:if test="${selectedSalary == null}">-</c:if>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Tổng lương</th>
                                                                        <td>
                                                                            <c:if test="${selectedSalary != null}">
                                                                                <fmt:formatNumber value="${selectedSalary.totalSalary}" type="currency" currencySymbol="₫"/>
                                                                            </c:if>
                                                                            <c:if test="${selectedSalary == null}">-</c:if>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Tháng</th>
                                                                        <td>${selectedSalary != null ? selectedSalary.salaryMonth : '-'}</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Năm</th>
                                                                        <td>${selectedSalary != null ? selectedSalary.salaryYear : '-'}</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Địa chỉ</th>
                                                                        <td>${person.address != null ? person.address : '-'}</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Email</th>
                                                                        <td>${person.email != null ? person.email : '-'}</td>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>Số điện thoại</th>
                                                                        <td>${person.phoneNumber != null ? person.phoneNumber : '-'}</td>
                                                                    </tr>
                                                                </table>
                                                            </div>
                                                        </div>
                                                        <div style="height: 26px;"></div>
                                                        <div class="row text-center align-content-center my-3">
                                                            <form action="viewpersonnel" method="post" id="changeQuitStatus">
                                                                <input type="hidden" name="id" value="${person.id}">
                                                                <input type="hidden" name="month" value="${selectedMonth}">
                                                                <input type="hidden" name="year" value="${selectedYear}">
                                                            </form>
                                                            <div class="col-lg-4"></div>
                                                            <c:if test="${selectedSalary != null && selectedSalary.paymentStatus == 'chưa thanh toán'}">
                                                                <div class="col-lg-4">
                                                                    <button class="btn btn-primary w-100" onclick="document.getElementById('changeQuitStatus').submit()">Thanh Toán</button>
                                                                </div>
                                                            </c:if>
                                                            <div class="col-lg-4">
                                                                <a href="listpersonnell" class="btn btn-info w-100">Danh sách nhân viên</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </c:if>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>

        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/main.js"></script>
        <script src="js/plugins/pace.min.js"></script>
</body>
</html>