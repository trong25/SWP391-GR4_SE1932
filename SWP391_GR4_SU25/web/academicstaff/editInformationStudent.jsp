<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>CHỈNH SỬA THÔNG TIN</title>
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
            function goBack() {
                window.history.back();
            }
        </script>
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
            function validateForm() {
                // Get the form fields
                var firstGuardianName = document.getElementsByName("first_guardian_name")[0].value.trim();
                var firstGuardianPhone = document.getElementsByName("firstGuardianPhoneNumber")[0].value.trim();
                var secondGuardianName = document.getElementsByName("second_guardian_name")[0].value.trim();
                var secondGuardianPhone = document.getElementsByName("secondGuardianPhoneNumber")[0].value.trim();
                var address = document.getElementsByName("address")[0].value.trim();
                var lastName = document.getElementsByName("lastName")[0].value.trim();
                var firstName = document.getElementsByName("firstName")[0].value.trim();
                var schoolName = document.getElementsByName("schoolName")[0].value.trim();
                // Validate address
                if (address === "") {
                    toastr.error("Địa chỉ không được bỏ trống");
                    return false;
                }
                if (schoolName === "") {
                    toastr.error(" không được bỏ trống");
                    return false;
                }
                // Validate first guardian's name
                if (firstGuardianName === "") {
                    toastr.error("Họ tên bố không được để trống");
                    return false;
                }
                if (!isValidName(firstGuardianName)) {
                    toastr.error("Họ tên bố chỉ được nhập chữ cái và không được chứa ký tự đặc biệt");
                    return false;
                }

                // Validate first guardian's phone
                if (firstGuardianPhone === "") {
                    toastr.error("Số điện thoại bố không được để trống");
                    return false;
                }

                // Validate second guardian's name and phone
                if (secondGuardianName !== "" && secondGuardianPhone === "") {
                    toastr.error("Vui lòng nhập số điện thoại của mẹ");
                    return false;
                }
                if (secondGuardianPhone !== "" && secondGuardianName === "") {
                    toastr.error("Vui lòng nhập họ tên mẹ");
                    return false;
                }
                if (secondGuardianName !== "" && !isValidName(secondGuardianName)) {
                    toastr.error("Họ tên người mẹ chỉ được nhập chữ cái và không được chứa ký tự đặc biệt");
                    return false;
                }

                // Validate first name
                if (firstName === "") {
                    toastr.error("Tên của học sinh không được để trống");
                    return false;
                }
                if (!isValidName(firstName)) {
                    toastr.error("Tên của học sinh chỉ được nhập chữ cái và không được chứa ký tự đặc biệt");
                    return false;
                }

                // Validate last name
                if (lastName === "") {
                    toastr.error("Họ của học sinh không được để trống");
                    return false;
                }
                if (!isValidName(lastName)) {
                    toastr.error("Họ của học sinh chỉ được nhập chữ cái và không được chứa ký tự đặc biệt");
                    return false;
                }

                return true;
            }

            function isValidName(name) {
                return /^[A-Za-z\s\-àáảãạâầấẩẫậăằắẳẵặèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳỹỷỹỵđĐ]+$/.test(name);
            }
        </script>
        <script>
    function validateForm() {
        // Lấy các trường cần kiểm tra
        var firstGuardianName = document.getElementsByName("first_guardian_name")[0].value.trim();
        var secondGuardianName = document.getElementsByName("second_guardian_name")[0].value.trim();
        var lastName = document.getElementsByName("lastName")[0].value.trim();
        var firstName = document.getElementsByName("firstName")[0].value.trim();
        var birthday = document.getElementsByName("birthday")[0].value;

        // Validate ngày sinh
        if (!isOlderThan11Years(birthday)) {
            toastr.error("Học sinh phải lớn hơn 11 tuổi");
            return false;
        }

        // Validate tên không chứa số hoặc ký tự đặc biệt
        if (!isValidPureName(firstGuardianName)) {
            toastr.error("Họ tên bố không được chứa số hoặc ký tự đặc biệt");
            return false;
        }

        if (secondGuardianName !== "" && !isValidPureName(secondGuardianName)) {
            toastr.error("Họ tên mẹ không được chứa số hoặc ký tự đặc biệt");
            return false;
        }

        if (!isValidPureName(firstName)) {
            toastr.error("Tên học sinh không được chứa số hoặc ký tự đặc biệt");
            return false;
        }

        if (!isValidPureName(lastName)) {
            toastr.error("Họ học sinh không được chứa số hoặc ký tự đặc biệt");
            return false;
        }

        return true;
    }

    function isValidPureName(name) {
        // Chỉ cho phép chữ cái (bao gồm dấu tiếng Việt), khoảng trắng và dấu gạch ngang
        return /^[A-Za-zÀ-ỹà-ỹ\s\-]+$/.test(name);
    }

    function isOlderThan11Years(birthday) {
        if (!birthday) return false;
        var birthDate = new Date(birthday);
        var today = new Date();
        var age = today.getFullYear() - birthDate.getFullYear();
        var m = today.getMonth() - birthDate.getMonth();
        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }
        return age > 11;
    }
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
                                <h3 class="m-0 font-weight-bold"><i class="fa fa-edit"></i>CHỈNH SỬA THÔNG TIN HỌC SINH</h3>
                            </div>
                        </div>
                        <div class="row gutters">
                            <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <div class="account-settings">
                                            <div class="user-profile">
                                                <div class="user-avatar">
                                                    <label for="customFile"><img class="app-sidebar__user-avatar" id="avatarDisplay" src="../images/${student.avatar}" ></label> 
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
                                        <form action="updatestudents" method="post" onsubmit="return validateForm()">
                                            <input type="hidden" value="${personnel.userId}"/>
                                            <input type="hidden" value="noedit" name="action"/>
                                            <input hidden name="currentAvatar" value="${student.avatar}"/>
                                            <input class="custom-file-input" id="customFile" type="file" name="image"/>
                                            <table>
                                                <tbody>
                                                    <tr>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>ID Người Dùng:</h5><input class="form-control-sm" value="${student.userId!=null?student.userId:"Chưa có tài khoản"}" type="text" name="userId" readonly=""/>
                                                            </div></td>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>ID: </h5> <input class="form-control-sm" value="${student.id}" type="text" name="id" readonly=""/><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Họ Tên Bố:</h5> 
                                                                <input class="form-control form-control-sm" type="text" name="first_guardian_name" value="${student.firstGuardianName}" maxlength="25" title="Nhập tối da 25 kí tự" required=""/><br />
                                                            </div></td>
                                                        <td><div class="form-group col-md-8">

                                                                <h5>Số Điện Thoại Bố<a style="color: red">(*)</a>:</h5> 
                                                                <input class="form-control form-control-sm" type="text" name="firstGuardianPhoneNumber" value="${student.firstGuardianPhoneNumber}" pattern="[0-9]+" minlength="10" maxlength="10" title="Chỉ nhập số" required=""/><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Họ Tên Mẹ<a style="color: red">(*)</a>:</h5> 
                                                                <input class="form-control form-control-sm" type="text" name="second_guardian_name" value="${student.secondGuardianName}" maxlength="25" title="Nhập tối da 25 kí tự"/><br />
                                                            </div></td>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Số Điện Thoại Mẹ<a style="color: red">(*)</a>:</h5> 
                                                                <input class="form-control form-control-sm" type="text" name="secondGuardianPhoneNumber" value="${student.secondGuardianPhoneNumber}" maxlength="10" minlength="10" pattern="[0-9]+" title="Chỉ nhập số"/><br />
                                                            </div></td>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Tên Trường<a style="color: red">(*)</a>:</h5> 
                                                                <input class="form-control form-control-sm" type="text" name="schoolName" value="${student.schoolName}" maxlength="25" title="Nhập tối da 25 kí tự"/><br />
                                                            </div></td>
                                                            <td><div class="form-group col-md-8">
                                                                <h5>ID Trường<a style="color: red">(*)</a>:</h5> 
                                                                <input class="form-control form-control-sm" type="text" name="school_id" value="${student.school_id}" maxlength="25" title="Nhập tối da 25 kí tự"/><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <div class="form-group col-md-8">
                                                                <h5>Họ Của Học Sinh<a style="color: red">(*)</a>:</h5> 
                                                                <input class="form-control form-control-sm" type="text" name="lastName" value="${student.lastName}" required="">
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="form-group col-md-8">
                                                                <h5>Tên Của Học Sinh<a style="color: red">(*)</a>:</h5> 
                                                                <input class="form-control form-control-sm" type="text" name="firstName" value="${student.firstName}" required="">
                                                            </div>
                                                        </td>

                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Ngày Sinh <a style="color: red">(*)</a>:</h5> 
                                                                <input class="form-control form-control-sm" type="date" name="birthday" value="${student.birthday}" title="Ngày sinh của bé" required=""/><br />
                                                            </div></td>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Email<a style="color: red">(*)</a>:</h5> 
                                                                <input class="form-control form-control-sm" type="email" name="email" value="${student.email}" title="email" required=""/><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-6">
                                                                <h5>Địa Chỉ<a style="color: red">(*)</a>:</h5> 
                                                                <textarea class="form-control form-control-sm" type="text" name="address" value="${student.address}" style="width: 200%" title="Tối đa 50 kí tự" required="" maxlength="50">${student.address}</textarea><br />
                                                            </div></td>


                                                        <td><div class="form-group col-md-6">
                                                                <h5>Cam kết của phụ huynh học sinh<a style="color: red">(*)</a>:</h5> 
                                                                <textarea class="form-control form-control-sm" name="note" style="width: 200%" maxlength="500">${student.parentSpecialNote}</textarea>
                                                            </div></td>


                                                    </tr>
                                                <p>Chú ý : Những Tiêu Đề Có Dấu (*) Là Những Tiêu Đề Được Chỉnh Sửa. Nhấn Vào Ảnh Để Thay Đổi.</p>
                                                </tbody>
                                            </table>
                                            <div class="d-flex justify-content-end">
                                                <div class="m-2">
                                                    <button type="button" onclick="redirectToPreviousPage()" class="btn btn-danger">Quay Lại</button></a>
                                                </div>
                                                <div class="m-2">
                                                    <button type="submit" class="btn btn-success">Lưu</button>
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
            function redirectToPreviousPage() {
                window.history.back();
            }
        </script>
    </body>
</html>
