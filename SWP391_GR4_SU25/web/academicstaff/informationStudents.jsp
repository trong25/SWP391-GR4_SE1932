<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Thông Tin chi Tiết</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="../css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <style>
            .app-sidebar__user-avatar {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                cursor: pointer;
                object-fit: cover;
            }

            .avatar-input {
                display: none;
            }

            .form-buttons {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-top: 20px;
            }

            .update-btn, .cancel-btn {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                font-size: 16px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .update-btn:hover, .cancel-btn:hover {
                background-color: #0056b3;
            }

            .cancel-btn {
                background-color: #dc3545;
            }

            .cancel-btn:hover {
                background-color: #c82333;
            }
            input[readonly] {
                background-color: #e9ecf3; /* Màu nền giống với disabled */
                color: black; /* Màu chữ giống với disabled */
                cursor: not-allowed; /* Con trỏ chuột giống với disabled */
                border: 1px solid #ddd; /* Đường viền nhẹ */
            }

            /* Bỏ bóng và đường viền khi focus cho giống với disabled */
            input[readonly]:focus {
                outline: none;
                box-shadow: none;
            }

            textarea[readonly] {
                background-color: #e9ecf3; /* Màu nền giống với disabled */
                color: black; /* Màu chữ giống với disabled */
                cursor: not-allowed; /* Con trỏ chuột giống với disabled */
                border: 1px solid #ddd; /* Đường viền nhẹ */
            }

            /* Bỏ bóng và đường viền khi focus cho giống với disabled */
            textarea[readonly]:focus {
                outline: none;
                box-shadow: none;
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
        </script>
    </head>
    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>

                    <div class="container">
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h3 class="m-0 font-weight-bold"><i class="fa fa-edit"></i>THÔNG TIN HỌC SINH</h3>
                            </div>
                        </div>
                        <div class="row gutters">
                            <!-- Cột Avatar -->
                            <div class="col-xl-4 col-lg-4 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body text-center">
                                        <div class="user-avatar mb-3">
                                            <img class="app-sidebar__user-avatar" id="avatarDisplay" src="../images/${student.avatar}" alt="avatar">
                                        </div>
                                        <h5 class="user-name">${student.lastName} ${student.firstName}</h5>
                                    </div>
                                </div>
                            </div>

                            <!-- Cột Thông Tin 1 -->
                            <div class="col-xl-4 col-lg-4 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <form action="studentprofile" method="post">
                                            <input type="hidden" name="avatar" value="${student.avatar}"/>
                                            <input type="hidden" name="id" value="${student.id}"/>
                                            <input type="hidden" name="first_name" value="${student.firstName}"/>
                                            <input type="hidden" name="last_name" value="${student.lastName}"/>

                                            <div class="form-group">
                                                <label>Mã Người Dùng:</label>
                                                <input class="form-control-sm form-control" type="text" name="userId" value="${student.userId!=null?student.userId:'Chưa có tài khoản'}" readonly>
                                            </div>
                                            <div class="form-group">
                                                <label>Mã Học Sinh:</label>
                                                <input class="form-control-sm form-control" type="text" name="id" value="${student.id}" readonly>
                                            </div>
                                            <div class="form-group">
                                                <label>Họ Tên Học Sinh:</label>
                                                <input class="form-control-sm form-control" type="text" value="${student.lastName} ${student.firstName}" readonly>
                                            </div>
                                            <div class="form-group">
                                                <label>Ngày Sinh:</label>
                                                <input class="form-control-sm form-control" type="date" name="birthday" value="${student.birthday}" readonly>
                                            </div>
                                            <div class="form-group">
                                                <label>Email:</label>
                                                <input class="form-control-sm form-control" type="text" name="email" value="${student.email}" readonly>
                                            </div>
                                            <div class="form-group">
                                                <label>Địa Chỉ:</label>
                                                <textarea class="form-control-sm form-control" readonly>${student.address}</textarea>
                                            </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Cột Thông Tin 2 -->
                            <div class="col-xl-4 col-lg-4 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <div class="form-group">
                                            <label>Mã Trường Học:</label>
                                            <input class="form-control-sm form-control" type="text" name="school_id" value="${student.school_id.id}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label>Tên Trường Học:</label>
                                            <input class="form-control-sm form-control" type="text" name="schoolName" value="${student.school_id.schoolName}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label>Id Lớp Học:</label>
                                            <input class="form-control-sm form-control" type="text" name="school_class_id" value="${student.school_class_id.id}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label>Tên Lớp Học:</label>
                                            <input class="form-control-sm form-control" type="text" name="className" value="${student.school_class_id.className}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label>Họ Tên Bố:</label>
                                            <input class="form-control-sm form-control" type="text" name="first_guardian_name" value="${student.firstGuardianName}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label>SĐT Bố:</label>
                                            <input class="form-control-sm form-control" type="text" name="firstGuardianPhoneNumber" value="${student.firstGuardianPhoneNumber}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label>Họ Tên Mẹ:</label>
                                            <input class="form-control-sm form-control" type="text" name="second_guardian_name" value="${student.secondGuardianName}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label>SĐT Mẹ:</label>
                                            <input class="form-control-sm form-control" type="text" name="secondGuardianPhoneNumber" value="${student.secondGuardianPhoneNumber}" readonly>
                                        </div>
                                          <div class="form-group">
                                            <label>Địa chỉ Trường Học:</label>
                                            <input class="form-control-sm form-control" type="text" name="addressSchool" value="${student.school_id.addressSchool}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label>Cam kết của phụ huynh:</label>
                                            <textarea class="form-control-sm form-control" name="note" readonly>${student.parentSpecialNote}</textarea>
                                        </div>
                                        <div class="form-group d-flex justify-content-end">
                                            <button onclick="goBack()" type="button" class="btn btn-danger mr-2">Quay Lại</button>
                                            <input hidden value="edit" name="action"/>
                                            <button type="submit" class="btn btn-primary">Chỉnh Sửa</button>
                                        </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
        <script>
            function goBack() {
                window.location.href = 'student';
            }
        </script>
    </body>
</html>
