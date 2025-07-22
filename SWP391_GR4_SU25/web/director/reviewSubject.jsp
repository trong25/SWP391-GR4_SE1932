<%-- 
    Document   : reviewSubject
    Created on : Jul 6, 2025, 11:37:55 PM
    Author     : ThanhNT
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

    <title>Quản Lý Lớp Học</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <script>
        $(document).ready(function() {
            var toastMessage = '<%= request.getAttribute("toastMessage") %>';
            var toastType = '<%= request.getAttribute("toastType") %>';
            if (toastMessage) {
                if (toastType === 'success') {
                    toastr.success(toastMessage);
                } else if (toastType === 'error') {
                    toastr.error(toastMessage);
                }
            }
        });
    </script>
    <style>
        .btn-custom-width {
            width: 120px; /* Adjust the width as needed */
        }
    </style>
    <script>
        function confirmAccept(formId, msg) {
            formIdToSubmit = formId;
            document.getElementById('confirmationMessage').innerText = msg;
            $('#confirmationModal').modal('show');
        }
        $(document).ready(function() {
            $('#confirmButton').click(function() {
                document.getElementById(formIdToSubmit).submit();

            });
        });
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
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh sách môn học đang chờ duyệt</h1>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Danh sách môn học đang chờ duyệt</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên Môn Học</th>
                                    <th>Khối</th>
                                    <th>Mô tả</th>
                                    <th>Hành Động</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="subject" items="${requestScope.listSubjectPending}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${subject.name}</td>
                                        <td>${subject.grade.name}</td>
                                        <td>${subject.description}</td>
                                        <td>
                                            <div class="d-flex flex-column align-items-center">
                                                <form method="post" action="reviewsubject" id="accept-form-${subject.id}" class="d-inline mb-2">
                                                    <input type="hidden" name="action" value="accept">
                                                    <input type="hidden" name="id" value="${subject.id}">
                                                    <button type="button" class="btn btn-sm btn-success shadow-sm btn-custom-width"
                                                            onclick="confirmAccept('accept-form-${subject.id}','Bạn có chắc chắn phê duyệt môn học này không ?')">Chấp nhận</button>
                                                </form>

                                                <form method="post" action="reviewsubject" id="decline-form-${subject.id}" class="d-inline mb-2">
                                                    <input type="hidden" name="action" value="decline">
                                                    <input type="hidden" name="id" value="${subject.id}">
                                                    <button type="button" class="btn btn-sm btn-danger shadow-sm btn-custom-width"
                                                            onclick="confirmAccept('decline-form-${subject.id}','Bạn có chắc chắn từ chối môn học này không?')">Từ chối</button>
                                                </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <%-- Begin confirmation modal--%>
                <div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="confirmationModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="confirmationModalLabel">Xác nhận thao tác</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body" id="confirmationMessage">
                                <!-- Dynamic message will be inserted here -->
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                <button type="button" class="btn btn-primary" id="confirmButton">Xác Nhận</button>
                            </div>
                        </div>
                    </div>
                </div>
                <%-- End confirmation modal--%>
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
