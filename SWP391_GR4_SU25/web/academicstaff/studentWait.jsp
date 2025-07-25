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
    .modal-content {
        border-radius: 10px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
        background-color: #fff;
    }
    .modal-header {
        background-color: #f8f9fa;
        border-bottom: 1px solid #e9ecef;
        padding: 1rem 1.5rem;
    }
    .modal-title {
        font-weight: 600;
        color: #343a40;
    }
    .modal-body {
        padding: 1.5rem;
    }
    .form-label {
        font-weight: 500;
        color: #495057;
        margin-bottom: 0.5rem;
    }
    .form-control, .custom-select {
        border-radius: 5px;
        border: 1px solid #ced4da;
        padding: 0.375rem 0.75rem;
        transition: border-color 0.3s ease, box-shadow 0.3s ease;
    }
    .form-control:focus, .custom-select:focus {
        border-color: #007bff;
        box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
        outline: none;
    }
    .form-control.is-invalid, .custom-select.is-invalid {
        border-color: #dc3545;
    }
    .invalid-feedback {
        display: none;
        color: #dc3545;
        font-size: 0.875rem;
    }
    .is-invalid ~ .invalid-feedback {
        display: block;
    }
    .btn-success, .btn-danger {
        padding: 0.5rem 1.5rem;
        border-radius: 5px;
        font-weight: 500;
        transition: background-color 0.3s ease, transform 0.2s ease;
    }
    .btn-success:hover {
        background-color: #28a745;
        transform: translateY(-1px);
    }
    .btn-danger:hover {
        background-color: #dc3545;
        transform: translateY(-1px);
    }
    .text-danger {
        font-weight: bold;
    }
    .modal-footer {
        border-top: 1px solid #e9ecef;
        padding: 1rem 1.5rem;
        background-color: #f8f9fa;
    }
    @media (max-width: 768px) {
        .modal-dialog {
            margin: 0.5rem;
        }
        .form-control, .custom-select {
            font-size: 0.9rem;
        }
        .btn-success, .btn-danger {
            padding: 0.4rem 1rem;
            font-size: 0.9rem;
        }
    }
</style>
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
                        <div class="modal fade create-pupil" id="createPupilModal" tabindex="-1" role="dialog" aria-labelledby="createPupilModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="createPupilModalLabel">Thêm Học Sinh Mới</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="studentwait?action=create" method="post" id="create-form" onsubmit="return validateForm()">
                                            <div class="container-fluid">
                                                <p class="text-muted mb-4">
                                                    <strong>Ghi chú:</strong> Các trường có dấu <span class="text-danger">*</span> là bắt buộc.
                                                </p>
                                                <div class="row">
                                                    <div class="col-md-6 mb-3">
                                                        <label for="lastName" class="form-label">Họ học sinh <span class="text-danger">*</span></label>
                                                        <input type="text" class="form-control" id="lastName" name="lastName" value="${param.lastName}" required>
                                                        <div class="invalid-feedback">Vui lòng nhập họ học sinh.</div>
                                                    </div>
                                                    <div class="col-md-6 mb-3">
                                                        <label for="firstName" class="form-label">Tên học sinh <span class="text-danger">*</span></label>
                                                        <input type="text" class="form-control" id="firstName" name="firstName" value="${param.firstName}" required>
                                                        <div class="invalid-feedback">Vui lòng nhập tên học sinh.</div>
                                                    </div>
                                                    <div class="col-md-6 mb-3">
                                                        <label for="birth" class="form-label">Ngày sinh <span class="text-danger">*</span></label>
                                                        <input type="date" class="form-control" id="birth" name="birth" value="${param.birth}" required>
                                                        <div class="invalid-feedback">Vui lòng chọn ngày sinh.</div>
                                                    </div>
                                                    <div class="col-md-6 mb-3">
                                                        <label for="gender" class="form-label">Giới tính <span class="text-danger">*</span></label>
                                                        <select name="gender" id="gender" class="form-control custom-select" required>
                                                            <option value="-1" disabled ${param.gender == null || param.gender == '-1' ? 'selected' : ''}>Chọn giới tính</option>
                                                            <option value="1" ${param.gender == '1' ? 'selected' : ''}>Nam</option>
                                                            <option value="0" ${param.gender == '0' ? 'selected' : ''}>Nữ</option>
                                                        </select>
                                                        <div class="invalid-feedback">Vui lòng chọn giới tính.</div>
                                                    </div>
                                                    <div class="col-md-6 mb-3">
                                                        <label for="address" class="form-label">Địa chỉ <span class="text-danger">*</span></label>
                                                        <input type="text" class="form-control" id="address" name="address" value="${param.address}" required>
                                                        <div class="invalid-feedback">Vui lòng nhập địa chỉ.</div>
                                                    </div>
                                                    <div class="col-md-6 mb-3">
                                                        <label for="phoneNumber" class="form-label">Số điện thoại</label>
                                                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${param.phoneNumber}">
                                                    </div>
                                                    <div class="col-md-12 mb-3">
                                                        <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                                                        <input type="email" class="form-control" id="email" name="email" value="${param.email}" required>
                                                        <div class="invalid-feedback">Vui lòng nhập email hợp lệ.</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" class="btn btn-success">Lưu lại</button>
                                                <button type="button" class="btn btn-danger" id="cancel-button" data-dismiss="modal">Hủy bỏ</button>
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



