<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Title</title>
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
                            <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <div class="account-settings">
                                            <div class="user-profile">
                                                <div class="user-avatar">
                                                    <img class="app-sidebar__user-avatar" id="avatarDisplay" src="../images/${student.avatar}" > 
                                                </div>
                                                <h5 class="user-name">${student.lastName} ${student.firstName}</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <c:set var="vietnamesePattern" value="aáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸ\s]+"/>
                                        <form action="studentprofile" method="post">
                                            <input type="hidden" name="avatar" value="${student.avatar}"/>
                                            <input type="hidden" name="id" value="${student.id}"/>
                                            <table>
                                                <tbody>
                                                    <tr>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Mã Người Dùng:</h5><input class="form-control-sm" placeholder="ID Người Dùng" type="text" name="userId" value="${student.userId!=null?student.userId:"Chưa có tài khoản"}" readonly=""/>
                                                                <input  name="user_id" type="hidden" value="${student.userId!=null?student.userId:"Chưa có tài khoản"}" />
                                                            </div></td>
                                                        <td><div class="form-group col-md-6">
                                                                <h5>Mã Học Sinh:</h5> <input class="form-control-sm" placeholder="ID" type="text" name="id" value="${student.id}" readonly=""/><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-6">    
                                                                <h5>Họ Tên Học Sinh:</h5> <input class="form-control-sm" type="text" name="name_pupil" value="${student.lastName} ${student.firstName}"  readonly="" /><br />
                                                            </div></td>
                                                            <td><div class="form-group col-md-6">    
                                                                <h5>Tên Trường Học :</h5> <input class="form-control-sm" type="text" name="name_school" value="${student.schoolName}"  readonly="" /><br />
                                                            </div></td>
                                                        <td>
                                                            <div class="form-group col-md-8">
                                                                <h5>Ngày Sinh Của Học Sinh:</h5><input class="form-control-sm" type="date" name="birthday" value="${student.birthday}" readonly=""/><br />
                                                            </div>
                                                        </td>
                                                       
                                                       
                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Họ Tên Người Giám Hộ Thứ Nhất:</h5> <input class="form-control-sm" type="text" name="first_guardian_name" value="${student.firstGuardianName}" pattern="^[A-Za-z,${vietnamesePattern}\s]{1,20}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 20 kí tự)" readonly/><br />
                                                            </div></td>
                                                        <td><div class="form-group col-md-8">

                                                                <h5>Số Điện Thoại Người Giám Hộ Thứ Nhất:</h5> <input class="form-control-sm" type="text" name="firstGuardianPhoneNumber" value="${student.firstGuardianPhoneNumber}" pattern="^0\d{9}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại." readonly/><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Họ Tên Người Giám Hộ Thứ Hai:</h5> <input class="form-control-sm" type="text" name="second_guardian_name" value="${student.secondGuardianName}" pattern="^[A-Za-z,${vietnamesePattern}\s]{30}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 20 kí tự)" readonly/><br />
                                                            </div></td>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Số Điện Thoại Người Giám Hộ Thứ Hai:</h5> <input class="form-control-sm" type="text" name="secondGuardianPhoneNumber" value="${student.secondGuardianPhoneNumber}" pattern="^0\d{9}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại." readonly/><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>

                                                <input type="hidden" name="first_name" value="${student.firstName}"/>
                                                <input type="hidden" name="last_name" value="${student.lastName}"/>

                                                </tr>
                                                <tr>
                                                    <td><div class="form-group col-md-6">    
                                                            <h5>Email:</h5> <input class="form-control-sm" type="text" name="email" value="${student.email}"  readonly="" /><br />
                                                        </div></td>
                                                    <td><div class="form-group col-md-5">
                                                            <h5>Ghi Chú:</h5> <textarea class="form-control-sm" type="text" name="note" style="width: 200%" readonly>${student.parentSpecialNote}</textarea><br/>
                                                        </div></td>
                                                </tr>
                                                <tr>
                                                    <td><div class="form-group col-md-auto">
                                                            <h5>Địa Chỉ:</h5> <textarea class="form-control-sm" type="text" name="address" value="" style="width: 200%" pattern="^[A-Za-z1-9,${vietnamesePattern}\s]{1,100}$" title="Địa chỉ không được quá 100 kí tự" readonly>${student.address}</textarea><br />
                                                        </div></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <div class="d-flex justify-content-end">
                                                <div class="m-2">
                                                    <button onclick="goBack()" type="button" class="btn btn-danger">Quay Lại</button>
                                                </div>
                                                <div class="m-2">
                                                    <input hidden value="edit" name="action"/>
                                                    <button type="submit" class="btn btn-primary">Chỉnh Sửa</button>
                                                </div>

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
