<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>TẠO THÔNG BÁO</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f8f9fc;
            }
            .container-fluid {
                padding: 2rem;
            }
            .card {
                border: none;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            .card-body {
                padding: 2rem;
            }
            h1.h3 {
                margin-bottom: 1rem;
            }
            form {
                width: 100%;
            }
            input[type="text"], textarea {
                width: 100%;
                padding: 0.5rem;
                border-radius: 5px;
                border: 1px solid #ccc;
                margin-bottom: 1rem;
            }
            input[type="text"]:focus, textarea:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.25);
                outline: none;
            }
            .btn {
                padding: 0.5rem 1rem;
                border-radius: 5px;
                border: none;
                cursor: pointer;
            }
            .btn-danger {
                background-color: #dc3545;
                color: white;
            }
            .btn-success {
                background-color: #28a745;
                color: white;
            }
            .btn-danger:hover {
                background-color: #c82333;
            }
            .btn-success:hover {
                background-color: #218838;
            }
            @media (max-width: 768px) {
                .container-fluid {
                    padding: 1rem;
                }
                .card-body {
                    padding: 1rem;
                }
            }
        </style>
        <script>
            $(document).ready(function () {
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
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
    </head>
    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">TẠO THÔNG BÁO</h1>
                        <div class="card shadow mb-4">
                            <form id="myForm" action="createnotifi" method="post" onsubmit="return validateForm()">
                                <div class="card-body">
                                    <div class="form-group">
                                        <h4 class="form-group h4 mb-2 text-gray-800" for="heading">TIÊU ĐỀ<span class="text-danger">*</span></h4>
                                        <input type="text" id="heading" name="heading" class="form-control" maxlength="50" placeholder="Tiêu đề không nhập quá 50 kí tự" required>
                                    </div>
                                    <h4 class="form-group h4 mb-2 text-gray-800">NGƯỜI NHẬN<span class="text-danger">*</span></h4>
                                    <div class="form-group d-inline">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="role_id" value="3" id="receiver2">
                                            <label class="form-check-label" for="receiver2">
                                                Kế toán
                                            </label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="role_id" value="4" id="receiver3">
                                            <label class="form-check-label" for="receiver3">
                                                Giáo viên
                                            </label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="role_id" value="5" id="receiver4">
                                            <label class="form-check-label" for="receiver4">
                                                Phụ huynh
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <h4 class="form-group h4 mb-2 text-gray-800" style="padding-top: 10px">NỘI DUNG<span class="text-danger">*</span></h4>
                                        <textarea id="content" name="content" class="form-control" rows="10" maxlength="2000" placeholder="Nội dung không nhập quá 2000 kí tự" required></textarea>
                                    </div>
                                </div>
                                <input type="hidden" name="userid" value="${sessionScope.personnel.id}">
                                <input type="hidden" id="submitDate" name="submitDate">
                                <div class="d-flex justify-content-around pb-3">
                                    <button onclick="submitForm()" type="submit" class="btn btn-success">Gửi</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <script src="../js/demo/datatables-demo.js"></script>
        <script>
                                        function validateForm() {
                                            const heading = document.getElementById('heading');
                                            const content = document.getElementById('content');

                                            heading.value = heading.value.trim();
                                            content.value = content.value.trim();

                                            if (heading.value === "") {
                                                alert("Tiêu đề không được bỏ trống. Vui lòng nhập lại!");
                                                heading.focus();
                                                return false;
                                            }

                                            if (content.value === "") {
                                                alert("Nội dung không được bỏ trống. Vui lòng nhập lại!");
                                                content.focus();
                                                return false;
                                            }

                                            return true;
                                        }

                                        function redirectToServlet() {
                                            var selectedRole = document.getElementById("roleSelect").value;
                                            if (selectedRole !== "") {
                                                window.location.href = "categoryRoleManager?role=" + selectedRole;
                                            }
                                        }

                                        function getQueryParam(param) {
                                            var urlParams = new URLSearchParams(window.location.search);
                                            return urlParams.get(param);
                                        }

                                        document.addEventListener('DOMContentLoaded', (event) => {
                                            var selectedRole = getQueryParam('role');
                                            if (selectedRole) {
                                                document.getElementById('roleSelect').value = selectedRole;
                                            }
                                        });

                                        function goBack() {
                                            window.location.href = 'listnotification';
                                        }


                                        function submitForm() {
                                            var now = new Date();
                                            now.setHours(now.getHours());
                                            var day = now.getDate();
                                            var month = now.getMonth() + 1;
                                            var year = now.getFullYear();
                                            var formattedDate = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
                                            document.getElementById('submitDate').value = formattedDate;
                                        }
        </script>
    </body>
</html>
