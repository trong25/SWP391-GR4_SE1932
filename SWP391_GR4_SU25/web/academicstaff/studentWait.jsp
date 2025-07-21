<%-- 
    Document   : studentWait
    Created on : Jul 22, 2025, 12:04:51 AM
    Author     : MSI
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Quản lý học sinh</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>


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
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'error') {
                        toastr.error(toastMessage);
                        $('.create-pupil').modal('show'); // Show the modal if the toast type is fail
                    }
                }
            });
        </script>
        <style>
            #imagePreview img {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                object-fit: cover;
            }
        </style>
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
            function resetClassAndSubmitForm() {
                document.getElementById("myClass").selectedIndex = 0;
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
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Học Sinh</h1>

                        <c:set var="schoolYearSelect" value="${requestScope.schoolYearSelect}"/>
                        <c:set var="classesSelect" value="${requestScope.classSelect}"/>

                        <!-- Form section with select elements -->



                        <div class="card shadow mb-4">
                            <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Học Sinh</h6>
                                <button type="button" class="btn btn-outline-primary" data-toggle="modal"
                                        data-target=".create-pupil">
                                    <i class="fas fa-upload"></i> Thêm học sinh
                                </button>

                            </div>
                            <div class="card-body">
                                <div class="table-responsive">

                                    <table class="table table-bordered" id="dataTable">
                                        <thead>
                                            <tr class="table">
                                                <th>STT</th>                                  
                                                <th>Họ và tên</th>
                                                <th>Ngày sinh</th>
                                                <th>Địa chỉ</th>
                                                <th>Email</th>
                                                <th>Số điện Thoại</th>
                                                <th>Giới Tính</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${requestScope.listStudentWait}" var="student" varStatus="status">
                                                <tr>
                                                    <th scope="row">${status.index + 1}</th>                                                    
                                                    <td>${student.lastName} ${student.firstName}</td>
                                                    <td><fmt:formatDate value="${student.birthday}" pattern="dd/MM/yyyy"/></td>
                                                    <td>${student.address}</td>
                                                    <td>${student.email}</td>
                                                    <td>${student.phoneNumber}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${student.gender}">
                                                                Nam
                                                            </c:when>
                                                            <c:otherwise>
                                                                Nữ
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>

                                                </tr>
                                            </c:forEach>
                                        <tbody>
                                    </table>
                                </div>
                            </div>
                        </div>


                        <!-- New School Student Modal -->
                        <div class="modal fade create-pupil" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
                             aria-hidden="true" >
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="row">
                                        <div class="form-group col-md-12">
                                            <span class="thong-tin-thanh-toan">
                                                <h5 style="margin: 14px">Thêm học sinh mới</h5>
                                            </span>
                                        </div>
                                    </div>
                                    <form action="studentwait?action=create" method="post" id="create-form" onsubmit="return validateForm()">
                                        <div class="row">


                                            <div class="col-md-9">
                                                <c:set var="vietnamesePattern"
                                                       value="ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ"/>
                                                <div class="tile">
                                                    <div class="tile-body">

                                                        <div class="container-fluid pl-0">
                                                            <div class="row">

                                                                <p style="margin-left: 11px;font-weight: bold">Ghi chú: <a
                                                                        style="font-weight: normal">Các thông tin có dấu</a><a
                                                                        style="color: red"> (*) </a><a
                                                                        style="font-weight: normal">là thông tin bắt buộc phải
                                                                        nhập</a></p>

                                                                <div class="form-group col-md-6">
                                                                    <label for="address">Địa chỉ<a
                                                                            style="color: red">(*)</a></label>
                                                                    <input class="form-control" id="address" name="address"
                                                                           value="${param.address}">
                                                                </div>
                                                                <div class="form-group col-md-6">
                                                                    <label for="lastName">Họ học sinh<a
                                                                            style="color: red">(*)</a></label>
                                                                    <input type="text" class="form-control" id="lastName"
                                                                           name="lastName"
                                                                           value="${param.lastName}">
                                                                </div>
                                                                <div class="form-group col-md-6">
                                                                    <label for="firstName">Tên học sinh<a
                                                                            style="color: red">(*)</a></label>
                                                                    <input type="text" class="form-control" id="firstName" style="width: 70%"
                                                                           name="firstName" value="${param.firstName}">
                                                                </div>


                                                                <div class="form-group col-md-6">
                                                                    <label for="secondGuardianPhoneNumber">Số điện thoại
                                                                    </label><br>
                                                                    <input style="width: 50%" type="text" class="form-control"
                                                                           id="phoneNumber"
                                                                           name="phoneNumber"
                                                                           value="${param.phoneNumber}">
                                                                </div>

                                                                <div class=" form-group col-md-6">
                                                                    <label for="birth" class="form-label">Ngày sinh<a
                                                                            style="color: red">(*)</a></label><br>
                                                                    <input type="date" id="birth" class="form-control"
                                                                           style="width: 70%" name="birth"
                                                                           value="${param.birth}">
                                                                </div>
                                                                <div class="form-group col-md-6">
                                                                    <label for="gender" class="form-label">Giới tính<a
                                                                            style="color: red; margin-right: 60px">(*)</a></label>
                                                                    <select name="gender" id="gender"
                                                                            class="custom-select"
                                                                            aria-label="Default select example"
                                                                            style="width: 53%;height: 50%;">
                                                                        <option hidden="" value="-1">Chọn giới tính</option>
                                                                        <option ${param.gender==1?"selected":""} value="1">Nam
                                                                        </option>
                                                                        <option ${param.gender==0?"selected":""} value="0">Nữ
                                                                        </option>
                                                                    </select>
                                                                </div>

                                                                <div class="form-group col-md-6">
                                                                    <label for="email">Email<a
                                                                            style="color: red">(*)</a></label>
                                                                    <input type="text" class="form-control" id="email"
                                                                           name="email"  value="${param.email}">
                                                                    <div class="row" style="margin-top: 20px">
                                                                        <button style="margin:0px 10px" class="btn btn-success"
                                                                                type="submit">Lưu lại
                                                                        </button>
                                                                        <a class="btn btn-danger" data-dismiss="modal"
                                                                           id="cancel-button">Hủy bỏ</a>
                                                                    </div>
                                                                </div>
                                                                

                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>

                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>


        

        <script>
            function validateForm() {
                var vietnamesePattern = "ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ";
               
                var phoneNumber2 = document.getElementById('phoneNumber').value;
                var address = document.getElementById('address').value;
                
                var firstName = document.getElementById("firstName").value;
                var lastName = document.getElementById("lastName").value;
                var email = document.getElementById(("email")).value;
                // Perform the validation
                var firstNamePattern = new RegExp("^[A-Za-z\\s" + vietnamesePattern + "]{0,20}$")
                var lastNamePattern = new RegExp("^[A-Za-z\\s" + vietnamesePattern + "]{0,60}$")
                var fullNamePattern = new RegExp("^[A-Za-z\\s" + vietnamesePattern + "]{0,80}$")
                var phonePattern = /^(?:|(0[23578]|09)\d{8})$/;
                var addressPattern = new RegExp("^[A-Za-z1-9,\\s" + vietnamesePattern + "]{0,300}$");
                var emailPattern = new RegExp("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

                if (!firstNamePattern.test(firstName)) {
                    toastr.error("Tên không được chứa số hoặc kí tự đặc biệt (Tối đa 20 kí tự)");
                    return false; // Prevent form submission
                }
                if (!lastNamePattern.test(lastName)) {
                    toastr.error("Họ không được chứa số hoặc kí tự đặc biệt (Tối đa 60 kí tự)");
                    return false; // Prevent form submission
                }
                if (!(fullNamePattern.test(guardianName1) && fullNamePattern.test(guardianName2))) {
                    toastr.error("Họ và tên người giám hộ không được chứa số hoặc kí tự đặc biệt (Tối đa 80 kí tự)");
                    return false; // Prevent form submission
                }
                if (!(phonePattern.test(phoneNumber1) && phonePattern.test(phoneNumber2))) {
                    toastr.error("Vui lòng nhập đúng định dạng số điện thoại");
                    return false; // Prevent form submission
                }
                if (!addressPattern.test(address)) {
                    toastr.error("Địa chỉ không được bỏ trống hoặc quá 300 kí tự");
                    return false; // Prevent form submission
                }
                if (!emailPattern.test(email)) {
                    toastr.error("Vui lòng nhập đúng định dạng email!");
                    return false; // Prevent form submission
                }

                return true;
            }
        </script>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const today = new Date();
                const minDate = new Date(today.setFullYear(today.getFullYear() - 11));
                const minDateString = minDate.toISOString().split('T')[0]; // Format to YYYY-MM-DD

                // Set the minimum date on the input field
                const birthInput = document.getElementById("birth");

                // Add event listener to validate the date
                document.getElementById("create-form").addEventListener("submit", function (event) {
                    const selectedDateValue = birthInput.value;

                    // Check if the date field is empty
                    if (!selectedDateValue) {
                        toastr.error("Vui lòng chọn ngày sinh");
                        $('.create-pupil').modal('show'); // Show the modal if the toast type is fail
                        event.preventDefault(); // Prevent the form from being submitted
                        return;
                    }

                    const selectedDate = new Date(selectedDateValue);
                    if (selectedDate > minDate) {
                        toastr.error("Tuổi của học sinh phải lớn hơn hoặc bằng 11 tuổi !!!");
                        $('.create-pupil').modal('show'); // Show the modal if the toast type is fail
                        event.preventDefault(); // Prevent the form from being submitted
                    }
                });
            });
        </script>
        <script>
            document.getElementById('cancel-button').addEventListener('click', function () {
                document.getElementById('imageUpload').value = ''; // Reset the image selection
                document.getElementById('imagePreview').style.display = 'none'; // Hide the image preview
                document.getElementById('preview').src = ''; // Clear the image source
                document.getElementById('gender').value = '';
                document.getElementById('address').value = '';
                document.getElementById('lastName').value = '';
                document.getElementById('firstName').value = '';
               
                document.getElementById('phoneNumber').value = '';
                
                document.getElementById('birth').value = '';
                document.getElementById('email').value = '';
                document.getElementById('parentNote').value = '';

            });
        </script>
        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>

    </body>
</html>



