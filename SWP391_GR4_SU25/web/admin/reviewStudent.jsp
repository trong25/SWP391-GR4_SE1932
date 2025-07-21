<%-- 
    Document   : reviewStudent
    Created on : Jul 3, 2025, 3:49:54 PM
    Author     : ThanhNT
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Danh Sách Học Sinh Chờ Phê Duyệt</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>


        <%
            String toastMessage = (String) request.getAttribute("toastMessage");
            String toastType = (String) request.getAttribute("toastType");
        %>
        <script>
            $(document).ready(function () {
                var toastMessage = '<%= toastMessage %>';
                var toastType = '<%= toastType %>';
                if (toastMessage) {
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'error') {
                        toastr.error(toastMessage);
                    }
                }
            });
        </script>
        <script>
            function confirmAccept(formId, msg) {
                formIdToSubmit = formId;
                document.getElementById('confirmationMessage').innerText = msg;
                $('#confirmationModal').modal('show');
            }
            $(document).ready(function () {
                $('#confirmButton').click(function () {
                    document.getElementById(formIdToSubmit).submit();

                });
            });
        </script>
        <style>
            .btn-custom-width {

                width: 120px; /* Adjust the width as needed */
            }
            .profile_img {
                width: 220px;
                height: 280px;
                object-fit: cover;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
                border: 1px solid #ccc;
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
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Học Sinh Chờ Phê Duyệt</h1>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Danh sách học sinh</h6>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                            <thead>
                                                <tr>
                                                    <th>STT</th>
                                                    <th>Mã học sinh</th>
                                                    <th>Ảnh</th>
                                                    <th>Họ và tên</th>
                                                    <th>Ngày sinh</th>
                                                    <th>Địa chỉ</th>
                                                    <th>Hành động</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="students" items="${requestScope.listStudent}" varStatus="status">
                                                    <tr>
                                                        <th scope="row">${status.index + 1}</th>
                                                        <td>${students.id}</td>
                                                        <td style="width: 20%;">
                                                            <img src="../images/${students.avatar}"
                                                                 class="profile_img" width="191px"
                                                                 height="263px" object-fit: cover> 
                                                        </td>
                                                        <td>${students.lastName} ${students.firstName}</td>
                                                        <td><fmt:formatDate value="${students.birthday}" pattern="yyyy/MM/dd" /></td>
                                                        <td>${students.address}</td>
                                                        <td>
                                                            <div class="d-flex flex-column align-items-center">
                                                                <form method="post" action="reviewstudent" id="accept-form-${students.id}" class="d-inline mb-2">
                                                                    <input type="hidden" name="action" value="accept">
                                                                    <input type="hidden" name="id" value="${students.id}">
                                                                    <button type="button" class="btn btn-sm btn-success shadow-sm btn-custom-width" onclick="confirmAccept('accept-form-${students.id}', 'Bạn có chắc chắn duyệt học sinh này không ?')">Chấp nhận</button>
                                                                </form>

                                                                <form method="post" action="reviewstudent" id="decline-form-${students.id}" class="d-inline mb-2">
                                                                    <input type="hidden" name="action" value="decline">
                                                                    <input type="hidden" name="id" value="${students.id}">
                                                                    <button type="button" class="btn btn-sm btn-danger shadow-sm btn-custom-width" onclick="confirmAccept('decline-form-${students.id}', 'Bạn có chắc chắn từ chối học sinh này không ?')">Từ chối</button>
                                                                </form>
                                                                <form method="post" action="studentsprofile" >
                                                                    <input hidden name="id" value="${students.id}"/>
                                                                    <button class="btn btn-sm btn-primary shadow-sm btn-custom-width">Chi tiết</button>
                                                                </form>
                                                            </div>
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









