<%-- 
    Document   : viewSentInvoices
    Created on : Jul 21, 2025, 11:26:51 PM
    Author     : ThanhNT
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

        <!-- Bootstrap CSS for Modal -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.0/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.0/js/bootstrap.min.js"></script>

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
        <style>
            body, html {
                height: 100%;
                margin: 0;
                overflow-x: hidden;
            }
            #wrapper {
                display: flex;
                min-height: 100vh;
            }
            #content-wrapper {
                flex: 1;
                display: flex;
                flex-direction: column;
                max-width: 100%;
                overflow-x: hidden;
            }
            #content {
                flex: 1;
            }
            .container-fluid {
                padding: 15px;
            }
            .table-responsive {
                overflow-x: auto;
                -webkit-overflow-scrolling: touch;
            }
            .table th, .table td {
                white-space: nowrap;
                text-overflow: ellipsis;
                overflow: hidden;
                vertical-align: middle;
            }
            .table .badge {
                font-size: 0.9rem;
            }
            .table .btn-sm {
                padding: 0.25rem 0.5rem;
                font-size: 0.8rem;
                margin-right: 5px;
            }
            .table .action-buttons {
                display: flex;
                flex-wrap: wrap;
                gap: 5px;
                justify-content: center;
            }
            @media (max-width: 768px) {
                .hide-on-mobile {
                    display: none;
                }
                .table th, .table td {
                    font-size: 0.85rem;
                    padding: 0.4rem;
                }
                .table .btn-sm {
                    padding: 0.2rem 0.4rem;
                    font-size: 0.75rem;
                }
                .container-fluid {
                    padding: 10px;
                }
            }
            .toast-success {
                background-color: #28a745 !important; /* Màu xanh lá cho thông báo thành công */
                color: white !important;
            }
            .toast-error {
                background-color: #dc3545 !important; /* Màu đỏ cho thông báo lỗi */
                color: white !important;
            }
            .toast {
                opacity: 1 !important; /* Đảm bảo thông báo rõ ràng */
                border-radius: 5px;
                font-size: 0.9rem;
            }
            /* Đảm bảo modal hiển thị trạng thái nhất quán */
            .badge-success {
                background-color: #28a745;
                color: white;
            }
            .badge-danger {
                background-color: #dc3545;
                color: white;
            }
            .badge-warning {
                background-color: #ffc107;
                color: black;
            }
            .badge-secondary {
                background-color: #6c757d;
                color: white;
            }
            /* Cải tiến phần thẻ bảng */
            .table th, .table td {
                vertical-align: middle;
                padding: 0.75rem;
                font-size: 0.92rem;
            }

            .table .badge {
                font-size: 0.85rem;
                padding: 0.4em 0.65em;
                border-radius: 0.4rem;
            }

            .card-header {
                background-color: #ffffff;
                color: #4e73df; /* hoặc #333 tùy bạn chọn màu chữ */
                border-bottom: 1px solid #e3e6f0;
                font-weight: 600;
            }


            .table-hover tbody tr:hover {
                background-color: #f8f9fc;
            }

            /* Nút hành động rõ hơn */
            .action-buttons .btn-sm {
                min-width: 120px;
                font-weight: 500;
            }

            /* Thẻ số tiền đẹp hơn */
            .payment-amount-box {
                background-color: #fffbea;
                border: 1px dashed #ffc107;
                padding: 10px;
                border-radius: 0.5rem;
                box-shadow: 0 0 5px rgba(255, 193, 7, 0.2);
            }

            /* Modal cải tiến */
            .modal-header {
                background-color: #4e73df;
                color: white;
                border-bottom: none;
            }

            .modal-content {
                border-radius: 0.6rem;
                border: none;
                box-shadow: 0 0 15px rgba(0,0,0,0.1);
            }

            .modal-footer .btn {
                min-width: 100px;
            }

            /* Responsive text wrap */
            .table td span,
            .table td p {
                word-break: break-word;
                white-space: normal !important;
            }

            /* Ẩn dữ liệu không quan trọng trên mobile */
            @media (max-width: 768px) {
                .table th:nth-child(2), .table td:nth-child(2), /* Mã HS */
                .table th:nth-child(4), .table td:nth-child(4)  /* Email/phone */
                {
                    display: none;
                }
            }
            .class-form {
                padding: 8px 12px;
                background-color: #f9f9f9;
                border: 1px solid #ddd;
                border-radius: 8px;
                display: inline-block;
            }

            .class-form label {
                font-weight: 600;
                font-size: 14px;
                color: #333;
            }

            .class-form select.custom-select {
                margin-top: 4px;
                border-radius: 6px;
                padding: 6px 10px;
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
                        <h1 class="h3 mb-4 text-gray-800 text-center">Xem Hóa Đơn Học Phí</h1>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Hóa Đơn Học Phí</h6>

                                <div  class="m-0 font-weight-bold text-primary">
                                    <small>Tổng: ${fn:length(requestScope.listPayment)} hóa đơn</small>
                                </div>
                            </div>

                            <div class="class-form">
                                <form method="get" class="form-inline">
                                    <label for="statusFilter" class="mr-2 font-weight-bold">Lọc trạng thái:</label>
                                    <select name="status" id="statusFilter" class="form-control form-control-sm mr-2" onchange="this.form.submit()">
                                        <option value="">Tất cả</option>
                                        <option value="paid" ${param.status == 'paid' ? 'selected' : ''}>Đã thanh toán</option>
                                        <option value="Not yet" ${param.status == 'Not yet' ? 'selected' : ''}>Chưa thanh toán</option>
                                        <option value="overdue" ${param.status == 'overdue' ? 'selected' : ''}>Quá hạn</option>
                                    </select>
                                </form>
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
                                            <table class="table table-bordered" id="dataTable">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th>STT</th>
                                                        <th>Mã HS</th>
                                                        <th>Họ và tên</th>
                                                        <th>Thông tin liên lạc</th>
                                                        <th>Tháng</th>
                                                        <th>Hạn Đóng</th>
                                                        <th>Học phí</th>    
                                                        <th>Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.listPayment}" var="payment" varStatus="status">
                                                        <tr>
                                                            <td class="text-center">${status.index + 1}</td>
                                                            <td><strong>${payment.studentId}</strong></td>
                                                            <td><strong>${payment.lastName} ${payment.firstName}</strong></td>
                                                            <td> 
                                                                <span class="badge badge-info">${payment.email}</span><br>
                                                                <span class="guardian-phone"> ${payment.firstGuardianPhoneNumber} </span>
                                                            </td>


                                                            <td class="text-center">
                                                                <span class="badge badge-success">Tháng ${payment.month}</span>
                                                            </td>
                                                            <td class="text-center">
                                                                <c:choose>
                                                                    <c:when test="${payment.dueDate != null && payment.dueDate != ''}">
                                                                        <span class="badge badge-success">${payment.dueDate}</span>
                                                                    </c:when>
                                                                    <c:when test="${payment.dueTo != null}">
                                                                        <!-- Sử dụng JSTL fmt để format ngày -->
                                                                        <span class="badge badge-success">
                                                                            <fmt:formatDate value="${payment.dueTo}" pattern="dd/MM/yyyy"/>
                                                                        </span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="badge badge-secondary">Chưa có</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>

                                                            <td class="text-center align-middle">
                                                                <div class="p-2 border rounded shadow-sm bg-light d-inline-block" style="min-width: 150px;">
                                                                    <!-- Số tiền -->
                                                                    <div class="fw-bold text-dark mb-1" style="font-size: 1.05rem;">
                                                                        <i class="fas fa-money-bill-wave text-success"></i>
                                                                        <fmt:formatNumber value="${payment.amount}" type="number" groupingUsed="true"/> VNĐ
                                                                    </div>

                                                                    <!-- Trạng thái -->
                                                                    <c:choose>
                                                                        <c:when test="${payment.status == 'paid'}">
                                                                            <span class="badge bg-success text-white px-3 py-1">Đã thanh toán</span>
                                                                        </c:when>
                                                                        <c:when test="${payment.status == 'Not yet'}">
                                                                            <span class="badge bg-danger text-white px-3 py-1">Chưa thanh toán</span>
                                                                        </c:when>
                                                                        <c:when test="${payment.status == 'overdue'}">
                                                                            <span class="badge bg-warning text-dark px-3 py-1">Quá hạn</span>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <span class="badge bg-secondary text-white px-3 py-1">${payment.status}</span>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </div>
                                                            </td>


                                                            <td class="text-center">
                                                                <div class="action-buttons">
                                                                    <c:if test="${payment.status != 'paid'}">
                                                                        <button type="button" 
                                                                                class="btn btn-success btn-sm d-block mb-1" 
                                                                                onclick="markAsPaid('${payment.id}', '${payment.studentId}')"
                                                                                title="Đánh dấu đã thanh toán">
                                                                            <i class="fas fa-check"></i> Đã Thanh Toán
                                                                        </button>
                                                                    </c:if>

                                                                    <button type="button" 
                                                                            class="btn btn-info btn-sm d-block" 
                                                                            onclick="showDetails('${payment.id}', '${payment.studentId}', '${payment.lastName} ${payment.firstName}', '${payment.email}', '${payment.firstGuardianPhoneNumber}', '${payment.month}', '${payment.amount}', '${payment.status}', '${payment.note != null ? payment.note : ""}')"
                                                                            title="Xem thông tin chi tiết">
                                                                        <i class="fas fa-info-circle"></i> Xem Chi Tiết
                                                                    </button>
                                                                </div>
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

        <!-- Modal Chi tiết thanh toán -->
        <!-- Modal Bootstrap -->
        <div class="modal fade" id="paymentDetailModal" tabindex="-1" role="dialog" aria-labelledby="paymentDetailModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title" id="paymentDetailModalLabel">Thông Tin Chi Tiết Thanh Toán</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="paymentDetailForm">
                            <input type="hidden" id="paymentId" name="paymentId">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="font-weight-bold">Mã học sinh:</label>
                                        <p id="detailStudentId" class="form-control-static"></p>
                                    </div>
                                    <div class="form-group">
                                        <label class="font-weight-bold">Họ và tên:</label>
                                        <p id="detailStudentName" class="form-control-static"></p>
                                    </div>
                                    <div class="form-group">
                                        <label class="font-weight-bold">Email:</label>
                                        <p id="detailEmail" class="form-control-static"></p>
                                    </div>
                                    <div class="form-group">
                                        <label class="font-weight-bold">Số điện thoại:</label>
                                        <p id="detailPhone" class="form-control-static"></p>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="font-weight-bold">Tháng:</label>
                                        <p id="detailMonth" class="form-control-static"></p>
                                    </div>
                                    <div class="form-group">
                                        <label class="font-weight-bold">Số tiền:</label>
                                        <p id="detailAmount" class="form-control-static"></p>
                                    </div>
                                    <div class="form-group">
                                        <label class="font-weight-bold">Trạng thái:</label>
                                        <p id="detailStatus" class="form-control-static"></p>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="font-weight-bold">Ghi chú:</label>
                                <textarea class="form-control" id="paymentNote" name="note" rows="4" placeholder="Nhập ghi chú..."></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                        <button type="button" class="btn btn-primary" onclick="saveNote()">Lưu ghi chú</button>
                    </div>
                </div>
            </div>
        </div>
        <!--===============================================================================================-->
        <script src="js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <!--===============================================================================================-->
        <script src="js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="js/main.js"></script>
        <!--===============================================================================================-->
        <script src="js/plugins/pace.min.js"></script>
        <!--===============================================================================================-->
        <!--===============================================================================================-->

        <script>
                            $(document).ready(function () {
                                // Cấu hình toastr
                                toastr.options = {
                                    closeButton: true,
                                    progressBar: true,
                                    positionClass: 'toast-top-right',
                                    timeOut: 3000,
                                    showMethod: 'fadeIn',
                                    hideMethod: 'fadeOut'
                                };

                                // Khởi tạo DataTable
                                if ($.fn.DataTable.isDataTable('#dataTable')) {
                                    $('#dataTable').DataTable().destroy();
                                }

                                $('#dataTable').DataTable({
                                    language: {
                                        url: '//cdn.datatables.net/plug-ins/1.10.24/i18n/Vietnamese.json'
                                    },
                                    order: [[3, 'asc']], // Sắp xếp theo tên học sinh
                                    pageLength: 25,
                                    responsive: true,
                                    scrollX: true,
                                    columnDefs: [
                                        {responsivePriority: 1, targets: [0, 1, 2, 7, 8]}, // Ưu tiên cột STT, Mã HS, Họ tên, Trạng thái, Hành động
                                        {responsivePriority: 2, targets: [-1]}, // Cột Hành động
                                        {orderable: true, targets: '_all'} // Kích hoạt sắp xếp cho tất cả cột
                                    ]
                                });
                            });

// [Các phần còn lại giữ nguyên như mã gốc]

                            //đổi định dạng số
                            document.addEventListener("DOMContentLoaded", function () {
                                const phoneElements = document.querySelectorAll('.guardian-phone');
                                phoneElements.forEach(function (el) {
                                    let phone = el.textContent.trim();
                                    if (phone.startsWith("+84")) {
                                        let localPhone = "0" + phone.substring(3); // Bỏ +84, thêm 0
                                        el.textContent = localPhone;
                                    }
                                });
                            });



                            // Hàm đánh dấu đã thanh toán
                            function markAsPaid(paymentId, studentId) {
                                console.log('Original paymentId:', paymentId);

                                // Kiểm tra null hoặc rỗng
                                if (!paymentId || paymentId.trim() === '' || paymentId === 'null') {
                                    alert('Payment ID không hợp lệ: ' + paymentId);
                                    return;
                                }

                                paymentId = paymentId.trim();

                                // KHÔNG kiểm tra xem có phải số nữa – vì là mã chuỗi như "HD1753194895753"
                                // KHÔNG parseInt

                                if (confirm('Bạn có chắc chắn muốn đánh dấu hóa đơn này đã thanh toán?')) {
                                    $.ajax({
                                        url: 'updatePaymentStatus',
                                        type: 'POST',
                                        data: {
                                            paymentId: paymentId, // Gửi chuỗi
                                            status: 'paid'
                                        },
                                        success: function (response) {
                                            console.log('Server response:', response);
                                            if (response.success) {
                                                toastr.success('Đã cập nhật trạng thái thanh toán thành công!', 'Thành công');
                                                setTimeout(function () {
                                                    location.reload();
                                                }, 1000);
                                            } else {
                                                toastr.error(response.message || 'Có lỗi xảy ra!', 'Lỗi');
                                            }
                                        },
                                        error: function (xhr, status, error) {
                                            console.error('AJAX Error:', error);
                                            toastr.error('Có lỗi xảy ra khi kết nối server!', 'Lỗi');
                                        }
                                    });
                                }
                            }

                            // Hàm hiển thị modal chi tiết
                            function showDetails(paymentId, studentId, studentName, email, phone, month, amount, status, note) {
                                $('#paymentId').val(paymentId);
                                $('#detailStudentId').text(studentId || 'N/A');
                                $('#detailStudentName').text(studentName || 'N/A');
                                $('#detailEmail').text(email || 'N/A');
                                $('#detailPhone').text(phone || 'N/A');
                                $('#detailMonth').text(month ? 'Tháng ' + month : 'N/A');
                                $('#detailAmount').text(amount ? parseFloat(amount).toLocaleString('vi-VN') + ' VNĐ' : 'N/A');

                                let statusText = '';
                                let statusClass = '';
                                switch (status) {
                                    case 'paid':
                                        statusText = 'Đã thanh toán';
                                        statusClass = 'badge badge-success';
                                        break;
                                    case 'Not yet':
                                        statusText = 'Chưa thanh toán';
                                        statusClass = 'badge badge-danger';
                                        break;
                                    case 'overdue':
                                        statusText = 'Quá hạn';
                                        statusClass = 'badge badge-warning';
                                        break;
                                    default:
                                        statusText = status || 'N/A';
                                        statusClass = 'badge badge-secondary';
                                }
                                $('#detailStatus').html(`<span class="${statusClass}">${statusText}</span>`);

                                $('#paymentNote').val(note || '');
                                $('#paymentDetailModal').modal('show');
                            }

                            // Hàm lưu ghi chú
                            function saveNote() {
                                var paymentId = $('#paymentId').val();
                                var note = $('#paymentNote').val();

                                $.ajax({
                                    url: 'updatePaymentNote',
                                    type: 'POST',
                                    data: {
                                        paymentId: paymentId,
                                        note: note
                                    },
                                    success: function (response) {
                                        if (response.success) {
                                            toastr.success('Đã lưu ghi chú thành công!', 'Thành công');
                                            $('#paymentDetailModal').modal('hide');
                                        } else {
                                            toastr.error('Có lỗi xảy ra khi lưu ghi chú!', 'Lỗi');
                                        }
                                    },
                                    error: function () {
                                        toastr.error('Có lỗi xảy ra khi kết nối server!', 'Lỗi');
                                    }
                                });
                            }
        </script>
        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>
    </body>
</html>