<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Tạo Hóa Đơn</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

        <%-- Toast xử lý --%>
        <%
            String toastMessage = (String) session.getAttribute("toastMessage");
            String toastType = (String) session.getAttribute("toastType");
            session.removeAttribute("toastMessage");
            session.removeAttribute("toastType");
        %>
        <script>
            $(document).ready(function () {
                var toastMessage = '<%= toastMessage %>';
                var toastType = '<%= toastType %>';
                if (toastMessage) {
                    toastr.options.timeOut = 3000;
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'error') {
                        toastr.error(toastMessage);
                        $('.create-pupil').modal('show');
                    }
                }
            });
        </script>

        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>
    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Tạo Học Phí</h1>

                        <div class="card shadow mb-4">
                            <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Học Sinh</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable">
                                        <thead>
                                            <tr class="table">
                                                <th>STT</th>
                                                <th>Mã học sinh</th>
                                                <th>Ảnh</th>
                                                <th>Họ và tên</th>
                                                <th>Mã Lớp học</th>
                                                <th>Tên Lớp Học</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${requestScope.listStudent}" var="student" varStatus="status">
                                                <tr>
                                                    <th scope="row">${status.index + 1}</th>
                                                    <td>${student.id}</td>
                                                    <td style="width: 20%;">
                                                        <img src="../images/${student.avatar}" class="mx-auto d-block"
                                                             style="width: 220px; height: 280px; object-fit: cover; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.2); border: 1px solid #ccc;">
                                                    </td>
                                                    <td>${student.lastName} ${student.firstName}</td>
                                                    <td>${student.classId}</td>
                                                    <td>${student.className}</td>
                                                    <td class="text-center">
                                                        <button class="btn btn-sm btn-primary open-modal"
                                                                data-toggle="modal"
                                                                data-target="#createInvoiceModal"
                                                                data-student-id="${student.id}"
                                                                data-student-name="${student.lastName} ${student.firstName}"
                                                                data-class-code="${student.classId}"
                                                                data-class-name="${student.className}">
                                                            Tạo Hóa Đơn
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <!-- Modal tạo hóa đơn -->
                        <div class="modal fade" id="createInvoiceModal" tabindex="-1" role="dialog" aria-labelledby="createInvoiceModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg" role="document">
                                <div class="modal-content">
                                    <form action="notify-payment" method="post">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="createInvoiceModalLabel">Tạo Hóa Đơn</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Đóng">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>

                                        <div class="modal-body">
                                            <!-- Input ẩn để gửi về Servlet -->
                                            <input type="hidden" name="code" id="code">
                                            <input type="hidden" name="student_id" id="student_id">
                                            <input type="hidden" name="class_id" id="class_id">
                                            <input type="hidden" name="day_id" id="day_id" value="DAY01">
                                            <input type="hidden" name="month" id="month">
                                            <input type="hidden" name="year" id="year">
                                            <input type="hidden" name="status" id="status" value="Chưa đóng">

                                            <!-- Thông tin hiển thị -->
                                            <div class="form-group">
                                                <label>Mã học sinh</label>
                                                <input type="text" id="displayStudentId" class="form-control" disabled>
                                            </div>

                                            <div class="form-group">
                                                <label>Họ và tên</label>
                                                <input type="text" id="studentName" class="form-control" disabled>
                                            </div>

                                            <div class="form-group">
                                                <label>Mã lớp học</label>
                                                <input type="text" id="classCode" class="form-control" disabled>
                                            </div>

                                            <div class="form-group">
                                                <label>Tên lớp học</label>
                                                <input type="text" id="className" class="form-control" disabled>
                                            </div>

                                            <div class="form-group">
                                                <label>Số tiền</label>
                                                <input type="number" name="amount" class="form-control" required>
                                            </div>

                                            <div class="form-group">
                                                <label>Hạn đóng tiền</label>
                                                <input type="date" name="dueDate" class="form-control" required>
                                            </div>

                                            <div class="form-group">
                                                <label>Ghi chú</label>
                                                <textarea name="note" class="form-control" rows="2"></textarea>
                                            </div>
                                        </div>

                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                            <button type="submit" class="btn btn-primary">Xác nhận</button>
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

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const buttons = document.querySelectorAll(".open-modal");

                buttons.forEach(button => {
                    button.addEventListener("click", function () {
                        const studentId = this.getAttribute("data-student-id");
                        const studentName = this.getAttribute("data-student-name");
                        const classId = this.getAttribute("data-class-code");
                        const className = this.getAttribute("data-class-name");

                        // Gán vào các ô hiển thị
                        document.getElementById("displayStudentId").value = studentId;
                        document.getElementById("studentName").value = studentName;
                        document.getElementById("classCode").value = classId;
                        document.getElementById("className").value = className;

                        // Gán vào các hidden input (dùng để submit)
                        document.getElementById("student_id").value = studentId;
                        document.getElementById("class_id").value = classId;

                        // Gán tháng, năm hiện tại
                        const today = new Date();
                        document.getElementById("month").value = today.getMonth() + 1;
                        document.getElementById("year").value = today.getFullYear();

                        // Tạo mã hóa đơn đơn giản theo timestamp
                        document.getElementById("code").value = "HD" + Date.now();
                    });
                });
            });
        </script>


        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <script src="../js/demo/datatables-demo.js"></script>
    </body>
</html>