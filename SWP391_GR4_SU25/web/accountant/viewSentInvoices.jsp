<%-- 
    Document   : viewSentInvoices
    Created on : Jul 21, 2025, 11:26:51 PM
    Author     : admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Xem Hóa Đơn Học Phí</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Toast CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

        <!-- DataTables CSS -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

        <%-- Toast message handling --%>
        <%
            String toastMessage = (String) session.getAttribute("toastMessage");
            String toastType = (String) session.getAttribute("toastType");
            session.removeAttribute("toastMessage");
            session.removeAttribute("toastType");
        %>

        <script>
            $(document).ready(function () {
                // Toast configuration
                toastr.options = {
                    timeOut: 4000,
                    positionClass: "toast-top-right",
                    showMethod: 'fadeIn',
                    hideMethod: 'fadeOut'
                };

                var toastMessage = '<%= toastMessage %>';
                var toastType = '<%= toastType %>';

                if (toastMessage && toastMessage !== 'null') {
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'error') {
                        toastr.error(toastMessage);
                    }
                }
            });
        </script>
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Xem Hóa Đơn Học Phí</h1>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Hóa Đơn Học Phí</h6>
                                <div class="text-muted">
                                    <small>Tổng: ${fn:length(requestScope.listPayment)} hóa đơn</small>
                                </div>
                            </div>
                            <div class="card-body">
                                <c:choose>
                                    <c:when test="${empty requestScope.listPayment}">
                                        <div class="text-center py-4">
                                            <p class="text-muted">Không có hóa đơn nào</p>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="table-responsive">
                                            <table class="table table-bordered table-hover" id="dataTable">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th width="5%">STT</th>
                                                        <th width="10%">Mã HS</th>
                                                        <th width="15%">Ảnh</th>
                                                        <th width="20%">Họ và tên</th>
                                                        <th width="15%">Email</th>
                                                        <th width="15%">Phụ huynh</th>
                                                        <th width="10%">Điện thoại</th>
                                                        <th width="8%">Tháng</th>
                                                        <th width="12%">Học phí</th>
                                                        <th width="10%">Trạng thái</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.listPayment}" var="payment" varStatus="status">
                                                        <tr>
                                                            <td class="text-center">${status.index + 1}</td>
                                                            <td><strong>${payment.studentId}</strong></td>
                                                            <td class="text-center">
                                                                <c:choose>
                                                                    <c:when test="${not empty payment.avatar}">
                                                                        <img src="../images/${payment.avatar}" 
                                                                             class="img-thumbnail"
                                                                             style="width: 120px; height: 160px; object-fit: cover;"
                                                                             alt="Avatar của ${payment.firstName}">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <div class="bg-light d-flex align-items-center justify-content-center" 
                                                                             style="width: 60px; height: 60px; border-radius: 5px;">
                                                                            <i class="fas fa-user text-muted"></i>
                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <strong>${payment.lastName} ${payment.firstName}</strong>
                                                            </td>
                                                            <td>
                                                                <span class="badge badge-info">${payment.email}</span>
                                                            </td>
                                                            <td>${payment.firstGuardianName}</td>
                                                            <td>${payment.firstGuardianPhoneNumber}</td>
                                                            <td class="text-center">
                                                                <span class="badge badge-success">Tháng ${payment.month}</span>
                                                            </td>
                                                            <td class="text-right">
                                                                <span class="bg-warning text-dark rounded px-2 py-1 d-inline-block">
                                                                    <fmt:formatNumber value="${payment.amount}" type="number" groupingUsed="true"/>VNĐ
                                                                </span>
                                                            </td>



                                                            <td class="text-center">
                                                                <c:choose>
                                                                    <c:when test="${payment.status == 'paid'}">
                                                                        <span class="badge badge-success">Đã thanh toán</span>
                                                                    </c:when>
                                                                    <c:when test="${payment.status == 'Not yet'}">
                                                                        <span class="badge badge-danger">Chưa thanh toán</span>
                                                                    </c:when>
                                                                    <c:when test="${payment.status == 'overdue'}">
                                                                        <span class="badge badge-warning">Quá hạn</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge badge-secondary">${payment.status}</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- DataTables JavaScript -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <script>
            $(document).ready(function () {
                if ($.fn.DataTable.isDataTable('#dataTable')) {
                    $('#dataTable').DataTable().destroy();
                }

                $('#dataTable').DataTable({
                    language: {
                        url: "//cdn.datatables.net/plug-ins/1.10.24/i18n/Vietnamese.json"
                    },
                    order: [[3, 'asc']], // Sort by student name
                    pageLength: 25
                });
            });

        </script>
    </body>
</html>