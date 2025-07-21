<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Tạo Hóa Đơn Học Phí</title>
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
                        <h1 class="h3 mb-4 text-gray-800 text-center">Tạo Hóa Đơn Học Phí</h1>

                        <div class="card shadow mb-4">
                            <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Học Sinh Đang Theo Học</h6>
                                <div class="text-muted">
                                    <small>Tổng: ${fn:length(requestScope.listStudent)} học sinh</small>
                                </div>
                            </div>
                            <div class="card-body">
                                <c:choose>
                                    <c:when test="${empty requestScope.listStudent}">
                                        <div class="text-center py-4">
                                            <p class="text-muted">Không có học sinh nào đang theo học.</p>
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
                                                        <th width="10%">Mã Lớp</th>
                                                        <th width="15%">Tên Lớp</th>
                                                        <th width="10%">Học phí</th>
                                                        <th width="10%">Điểm danh</th>
                                                        <th width="10%">Hành động</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.listStudent}" var="student" varStatus="status">
                                                        <tr>
                                                            <td class="text-center">${status.index + 1}</td>
                                                            <td><strong>${student.studentId}</strong></td>
                                                            <td class="text-center">
                                                                <c:choose>
                                                                    <c:when test="${not empty student.avatar}">
                                                                        <img src="../images/${student.avatar}" 
                                                                             class="img-thumbnail"
                                                                             style="width: 60px; height: 60px; object-fit: cover;"
                                                                             alt="Avatar của ${student.firstName}">
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
                                                                <strong>${student.lastName} ${student.firstName}</strong>
                                                            </td>
                                                            <td><span class="badge badge-info">${student.classId}</span></td>
                                                            <td>${student.className}</td>
                                                            <td class="text-right">
                                                                <strong class="text-success">
                                                                    <fmt:formatNumber value="${student.fee}" pattern="#,###" /> ₫
                                                                </strong>
                                                            </td>
                                                            <td class="text-center">
                                                                <small class="text-success">Có mặt: ${student.presentCount}</small><br>
                                                                <small class="text-danger">Vắng: ${student.absentCount}</small>
                                                            </td>
                                                            <td class="text-center">
                                                                <button class="btn btn-sm btn-primary open-modal"
                                                                        data-toggle="modal"
                                                                        data-target="#createInvoiceModal"
                                                                        data-student-id="${student.studentId}"
                                                                        data-student-name="${student.lastName} ${student.firstName}"
                                                                        data-class-code="${student.classId}"
                                                                        data-class-name="${student.className}"
                                                                        data-fee="${student.fee}">
                                                                    <i class="fas fa-file-invoice"></i> Tạo hóa đơn
                                                                </button>
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

                        <!-- Modal tạo hóa đơn -->
                        <div class="modal fade" id="createInvoiceModal" tabindex="-1" role="dialog" aria-labelledby="createInvoiceModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg" role="document">
                                <div class="modal-content">
                                    <form action="notify-payment" method="post" id="paymentForm">
                                        <div class="modal-header bg-primary text-white">
                                            <h5 class="modal-title" id="createInvoiceModalLabel">
                                                <i class="fas fa-file-invoice"></i> Tạo Hóa Đơn Học Phí
                                            </h5>
                                            <button type="button" class="close text-white" data-dismiss="modal" aria-label="Đóng">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>

                                        <div class="modal-body">
                                            <!-- Hidden inputs -->
                                            <input type="hidden" name="code" id="code">
                                            <input type="hidden" name="student_id" id="student_id">
                                            <input type="hidden" name="class_id" id="class_id">
                                            <input type="hidden" name="day_id" id="day_id" value="DAY01">
                                            <input type="hidden" name="status" id="status" value="Chưa đóng">

                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="font-weight-bold">Mã hóa đơn</label>
                                                        <input type="text" id="displayCode" class="form-control" readonly>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="font-weight-bold">Mã học sinh</label>
                                                        <input type="text" id="displayStudentId" class="form-control" readonly>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="font-weight-bold">Họ và tên học sinh</label>
                                                <input type="text" id="studentName" class="form-control" readonly>
                                            </div>

                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="font-weight-bold">Mã lớp học</label>
                                                        <input type="text" id="classCode" class="form-control" readonly>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="font-weight-bold">Tên lớp học</label>
                                                        <input type="text" id="className" class="form-control" readonly>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="font-weight-bold text-danger">Số tiền cần đóng *</label>
                                                        <div class="input-group">
                                                            <input type="number" name="amount" id="amount" class="form-control" required min="1" step="1000">
                                                            <div class="input-group-append">
                                                                <span class="input-group-text">₫</span>
                                                            </div>
                                                        </div>
                                                        <small class="text-muted">Học phí chuẩn: <span id="standardFee"></span></small>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label class="font-weight-bold text-danger">Hạn đóng tiền *</label>
                                                        <input type="date" name="dueDate" id="dueDate" class="form-control" required>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="font-weight-bold">Ghi chú</label>
                                                <textarea name="note" id="note" class="form-control" rows="3" 
                                                          placeholder="Nhập ghi chú cho hóa đơn (tùy chọn)..."></textarea>
                                            </div>
                                        </div>

                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                <i class="fas fa-times"></i> Hủy
                                            </button>
                                            <button type="submit" class="btn btn-primary" id="submitBtn">
                                                <i class="fas fa-check"></i> Tạo hóa đơn
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!-- /.container-fluid -->
                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <jsp:include page="../footer.jsp"/>
                <!-- End of Footer -->
            </div>
            <!-- End of Content Wrapper -->
        </div>
        <!-- End of Page Wrapper -->

        <!-- JavaScript -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const openModalButtons = document.querySelectorAll(".open-modal");
                const form = document.getElementById("paymentForm");
                
                // Set minimum date for due date (tomorrow)
                const tomorrow = new Date();
                tomorrow.setDate(tomorrow.getDate() + 1);
                const minDate = tomorrow.toISOString().split('T')[0];
                document.getElementById("dueDate").setAttribute("min", minDate);

                openModalButtons.forEach(button => {
                    button.addEventListener("click", function () {
                        const studentId = this.getAttribute("data-student-id");
                        const studentName = this.getAttribute("data-student-name");
                        const classId = this.getAttribute("data-class-code");
                        const className = this.getAttribute("data-class-name");
                        const fee = this.getAttribute("data-fee");

                        // Generate invoice code
                        const code = "HD" + Date.now();
                        
                        // Set display values
                        document.getElementById("displayCode").value = code;
                        document.getElementById("displayStudentId").value = studentId;
                        document.getElementById("studentName").value = studentName;
                        document.getElementById("classCode").value = classId;
                        document.getElementById("className").value = className;
                        document.getElementById("standardFee").textContent = new Intl.NumberFormat('vi-VN').format(fee) + " ₫";

                        // Set hidden values
                        document.getElementById("code").value = code;
                        document.getElementById("student_id").value = studentId;
                        document.getElementById("class_id").value = classId;
                        
                        // Set default amount to class fee
                        document.getElementById("amount").value = fee;
                        
                        // Set default due date (30 days from now)
                        const defaultDueDate = new Date();
                        defaultDueDate.setDate(defaultDueDate.getDate() + 30);
                        document.getElementById("dueDate").value = defaultDueDate.toISOString().split('T')[0];
                        
                        // Clear note
                        document.getElementById("note").value = "";
                    });
                });

                // Form submission handling
                form.addEventListener("submit", function(e) {
                    const submitBtn = document.getElementById("submitBtn");
                    const amount = document.getElementById("amount").value;
                    const dueDate = document.getElementById("dueDate").value;
                    
                    // Basic validation
                    if (!amount || parseFloat(amount) <= 0) {
                        e.preventDefault();
                        toastr.error("Vui lòng nhập số tiền hợp lệ!");
                        return;
                    }
                    
                    if (!dueDate) {
                        e.preventDefault();
                        toastr.error("Vui lòng chọn hạn đóng tiền!");
                        return;
                    }
                    
                    // Disable submit button to prevent double submission
                    submitBtn.disabled = true;
                    submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang xử lý...';
                });
            });
        </script>

        <!-- DataTables JavaScript -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <script>
            $(document).ready(function() {
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