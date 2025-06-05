<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="personnelBean" class="model.personnel.PersonnelDAO"/>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Cập nhật thông tin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Test CSS-->
        <link rel="stylesheet" type="text/css" href="../css/main.css">
        <link rel="stylesheet" type="text/css" href="../css/information-style.css">
        <link rel="stylesheet" type="text/css" href="../css/information-css.css">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
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

        <script>
            function submitForm() {
                var newPassword = document.getElementById("newPassword").value;
                var confirmPassword = document.getElementById("confirmPassword").value;

                if (newPassword !== confirmPassword) {
                    toastr.error('Mật khẩu không trùng khớp.');
                } else {
                    document.getElementById("changePasswordForm").submit();
                }
            }
        </script>
        <script>
            function resetForm() {
                document.getElementById('informationUpdate').reset();
            }
        </script>
        <style>
            .button-container {
                display: flex;
                width: 100%;
                justify-content: space-between;
            }

            .button-container div {
                flex: 1;
            }

            .button-container div:first-child {
                text-align: left;
            }

            .button-container div:last-child {
                text-align: right;
            }

        </style>
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
                                <h3 class="m-0 font-weight-bold"><i class="fa fa-edit"></i>Chỉnh sửa thông tin</h3>
                            </div>
                        </div>
                        <div class="row gutters">
                            <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <div class="account-settings">
                                            <div class="user-profile">
                                                <div class="user-avatar">
                                                    <c:set var="personnel" value="${personnelBean.getTeacherInfoByUserId(sessionScope.personnel.userId)}"/>
                                                    <img src="${pageContext.request.contextPath}/images/${personnel.avatar}" alt="Maxwell Admin">
                                                </div>
                                                <h5 class="user-name">${personnel.lastName} ${personnel.firstName}</h5>
                                                <button type="button" id="submit" name="submit" class="btn btn-primary" data-toggle="modal" data-target="#changePasswordModal">Đổi mật khẩu</button>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <c:set var="vietnamesePattern" value="aáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸ\s]+"/>
                                        <form action="${pageContext.request.contextPath}/update-information" id="informationUpdate" method="post">
                                            <div class="row gutters">
                                                <p>Chú ý : Những Tiêu Đề Có Dấu (*) Là Những Tiêu Đề Được Chỉnh Sửa</p>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="id">ID Người dùng</label>
                                                        <input class="form-control" placeholder="User Id" type="text" name="userId" value="${personnel.userId}" disabled style="width: 32%;"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="firstName">Tên <a style="color: red">(*)</a></label>
                                                        <input style="width: 50%;" class="form-control" placeholder="Tên" type="text" name="first_name" value="${personnel.firstName}" pattern="^[a-zA-Z${vietnamesePattern}\s]{1,50}$" title="Tên không được chứa số hoặc kí tự đặc biệt (Tối đa 50 kí tự)"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="lastName">Họ <a style="color: red">(*)</a></label>
                                                        <input style="width: 40%;" type="text" class="form-control" placeholder="Họ" name="last_name" value="${personnel.lastName}" pattern="^[A-Za-z${vietnamesePattern}\s]{1,20}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 20 kí tự)"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="gender">Giới tính <a style="color: red">(*)</a></label>
                                                        <select class="form-control" id="gender" name="gender" style="width: 30%;">
                                                            <option value="true" ${personnel.gender ? 'selected' : ''}>Nam</option>
                                                            <option value="false" ${!personnel.gender ? 'selected' : ''}>Nữ</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                                         <div class="row gutters">
                                                <!-- Trường -->
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="schoolName">Trường</label>
                                                        <input style="width: 60%;" type="text" class="form-control" placeholder="Trường" name="schoolName" value="${personnel.schoolName}" />
                                                    </div>
                                                </div>
                                                <!-- Lớp -->
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="className">Lớp</label>
                                                        <input style="width: 40%;" type="text" class="form-control" placeholder="Lớp" name="className" value="${personnel.className}" />
                                                    </div>
                                                </div>
                                            </div>     
                                            <div class="row gutters">
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="birthDay">Ngày sinh</label>
                                                        <input class="form-control" type="date" name="birthday" value="${personnel.birthday}" disabled style="width: 40%;"/>
                                                    </div>
                                                </div>

                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="email">Email <a style="color: red">(*)</a></label>
                                                        <input style="width: 60%;" class="form-control" type="email" placeholder="email" name="email" value="${personnel.email}"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="phoneNumber">Số điện thoại <a style="color: red">(*)</a></label>
                                                        <input style="width: 40%;" type="text" class="form-control" placeholder="Số điện thoại" name="phone_number" value="${personnel.phoneNumber}" pattern="^0\d{9}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại."/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="address">Địa chỉ <a style="color: red">(*)</a></label>
                                                        <textarea class="form-control" placeholder="Địa chỉ" name="address" rows="2" pattern="^[A-Za-z1-9,${vietnamesePattern}\s]{1,100}$" title="Địa chỉ không được quá 100 kí tự">${personnel.address}</textarea>
                                                    </div>
                                                </div>
                                            </div>
                                             
                                            <div class="row gutters">
                                                <div class="button-container">
                                                    <div>
                                                        <a href="view-information" class="btn btn-primary">Quay lại</a>
                                                    </div>
                                                    <div>
                                                        <button type="button" class="btn btn-danger" onclick="resetForm()">Hủy</button>
                                                        <button type="submit" id="submit" name="submit" class="btn btn-primary">Lưu</button>
                                                    </div>

                                                </div>
                                            </div>
                                        </form>
                                        <!-- Password Change Modal -->
                                        <div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
                                            <div class="modal-dialog" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-body">
                                                        <form id="changePasswordForm" action="${pageContext.request.contextPath}/new-password" method="post">
                                                            <div class="form-group">
                                                                <label for="oldPassword">Mật khẩu cũ:</label>
                                                                <input type="password" id="oldPassword" name="oldPassword" class="form-control" required>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="newPassword">Mật khẩu mới:</label>
                                                                <input type="password" id="newPassword" name="newPassword" class="form-control" pattern=".{8,12}" required title="Mật khẩu phải từ 8 đến 12 ký tự">
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="confirmPassword">Xác nhận mật khẩu:</label>
                                                                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                                                            </div>
                                                            <button type="button" onclick="submitForm()" class="btn btn-primary">Đổi mật khẩu</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- End Password Change Modal -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>


    </body>

</html>
