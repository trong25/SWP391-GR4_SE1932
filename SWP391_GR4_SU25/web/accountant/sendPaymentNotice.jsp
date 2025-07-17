<%-- 
    Document   : sendPaymentNotice
    Created on : Jul 17, 2025, 5:47:01 PM
    Author     : ThanhNT
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
                                            <c:forEach items="${requestScope.listStudent}" var="student">
                                                <tr>
                                                    <th scope="row">${status.index + 1}</th>
                                                    <td>${student.id}</td>
                                                    <td style="width: 20%;">
                                                    <img src="../images/${student.avatar}"
                                                         class="mx-auto d-block"
                                                         style=" width: 220px;
                                                         height: 280px;
                                                         object-fit: cover;
                                                         border-radius: 10px;
                                                         box-shadow: 0 4px 8px rgba(0,0,0,0.2);
                                                         border: 1px solid #ccc; ">
                                                </td>
                                                    <td>${student.lastName} ${student.firstName}</td>
                                                    <td>${student.classId}</td>
                                                    <td>${student.className}</td>
                                                    <td class="text-center">
                                                        <form method="post" action="studentprofile">
                                                            <input hidden="" value="${student.id}" name="id"/>
                                                            <input hidden="" value="view" name="action"/>
                                                            <button type="submit" class="btn btn-primary"> Thông tin chi tiết</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        <tbody>
                                    </table>
                                </div>
                            </div>
                        </div>




                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>


        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const form = document.querySelector("form"); // điều chỉnh selector nếu cần
                form.addEventListener("submit", function (event) {
                    const schoolSelect = document.getElementById("schoolID");
                    const classSelect = document.getElementById("schoolClassID");

                    let isValid = true;
                    let message = "";

                    if (schoolSelect.value.trim() === "") {
                        isValid = false;
                        message += "Vui lòng chọn trường học.\n";
                    }

                    if (classSelect.value.trim() === "") {
                        isValid = false;
                        message += "Vui lòng chọn lớp học.\n";
                    }

                    if (!isValid) {
                        event.preventDefault(); // Ngăn không cho submit form
                        alert(message); // hoặc thay bằng toast nếu bạn đang dùng thư viện thông báo
                    }
                });
            });

            function loadSchoolClasses(schoolId) {
                const classSelect = document.getElementById("schoolClassID");
                classSelect.innerHTML = '<option value="">-- Đang tải lớp học --</option>';

                fetch('student?schoolId=' + schoolId, {
                    headers: {
                        'X-Requested-With': 'XMLHttpRequest'
                    }
                })
                        .then(response => response.json())
                        .then(data => {
                            classSelect.innerHTML = '<option value="">-- Chọn lớp học --</option>';
                            data.forEach(function (schoolClass) {
                                const option = document.createElement("option");
                                option.value = schoolClass.id;
                                option.text = schoolClass.className;
                                classSelect.appendChild(option);
                            });
                        })
                        .catch(error => {
                            classSelect.innerHTML = '<option value="">-- Không thể tải lớp học --</option>';
                        });
            }
        </script>

        <script>
            function validateForm() {
                var vietnamesePattern = "ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ";
                var phoneNumber1 = document.getElementById('firstGuardianPhoneNumber').value;
                var phoneNumber2 = document.getElementById('secondGuardianPhoneNumber').value;
                var address = document.getElementById('address').value;
                var guardianName1 = document.getElementById("firstGuardianName").value;
                var guardianName2 = document.getElementById("secondGuardianName").value;
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
                document.getElementById('secondGuardianName').value = '';
                document.getElementById('firstGuardianName').value = '';
                document.getElementById('secondGuardianPhoneNumber').value = '';
                document.getElementById('firstGuardianPhoneNumber').value = '';
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



