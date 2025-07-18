<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
    <head>

        <title>Quản lý Nhân sự</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script>
            $(document).ready(function () {
                var toastMessage = '<%= request.getAttribute("message") %>';
                var toastType = '<%= request.getAttribute("type") %>';
                if (toastMessage) {
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'fail') {
                        toastr.error(toastMessage);
                    }
                }
            });
        </script>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <style>
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
    <body>
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Nhân Viên </h1>

                        <div class="row">
                            <%--  Begin : Select item      --%>
                            <c:set var="sltedrole" value="${requestScope.selectedrole}"/>
                            <c:set var="sltedstatus" value="${requestScope.selectedstatus}"/>
                            <c:set var="vietnamesePattern"
                                   value="ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ"/>
                            <div class="col-lg-4">
                                <form action="listpersonnel" method="post">
                                    <div style="display: flex; justify-content: space-between">
                                        <div class="class-form">


                                            <label>Chức vụ
                                                <select name="role" onchange="this.form.submit()" class="custom-select">
                                                    <option value="" hidden>Chức vụ</option>

                                                    <c:forEach items="${requestScope.roles}" var="r">
                                                        <c:choose>
                                                            <c:when test="${fn:trim(sltedrole) == fn:trim(r.id)}">
                                                                <option value="${r.id}" selected>${r.VNeseDescription}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${r.id}">${r.VNeseDescription}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>

                                                    <c:choose>
                                                        <c:when test="${sltedrole == 'all'}">
                                                            <option value="all" selected>Hiện toàn bộ chức vụ</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="all">Hiện toàn bộ chức vụ</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </select>
                                            </label>




                                        </div>

                                        <div class="class-form">

                                            <label>Trạng thái
                                                <select name="status" onchange="this.form.submit()" class="custom-select">
                                                    <option value="" hidden>Trạng thái</option>
                                                    <c:forEach items="${requestScope.statuss}" var="r">
                                                        <option ${sltedstatus eq r ? "selected" : ""} value="${r}">${r}</option>
                                                    </c:forEach>
                                                    <c:if test="${sltedstatus eq 'all'}">
                                                        <option value="all" selected>Hiện toàn bộ trạng thái</option>
                                                    </c:if>
                                                    <c:if test="${sltedstatus ne'all'}">
                                                        <option value="all">Hiện toàn bộ trạng thái</option>
                                                    </c:if>
                                                </select>
                                            </label>
                                        </div>
                                    </div>
                                </form>

                                <style>
                                    .btn {
                                        margin: 0px 0px 0px 10px;
                                    }
                                </style>

                            </div>
                            <div class="col-lg-4">

                            </div>
                            <%--End : Select item    --%>
                            <div class="col-lg-4" style="display: flex; justify-content: end">
                                <div>
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newPersonnelModal">
                                        TẠO NHÂN VIÊN MỚI
                                    </button>

                                </div>


                            </div>

                        </div>


                        <style>
                            /* Hide the placeholder in the dropdown options */
                            option[hidden] {
                                display: none;
                            }
                        </style>


                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Nhân Viên</h6>
                            </div>

                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Ảnh</th>
                                                <th>Mã Nhân Viên</th>
                                                <th>Tên</th>
                                                <th>Giới Tính</th>
                                                <th>Ngày sinh</th>
                                                <th>Chức vụ</th>
                                                <th>Trạng thái</th>
                                                <th>Chi tiết</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <div style="color: red">${requestScope.error}</div>
                                        <c:forEach items="${persons}" var="p" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>
                                                <td><img class="profile_img" src="../images/${p.getAvatar()}" alt="ảnh nhân viên" width="191px"
                                                         height="263px" object-fit: cover></td>
                                                <td>${p.getId()}</td>
                                                <td>${p.getLastName()} ${p.getFirstName()}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${p.gender}">
                                                            Nam
                                                        </c:when>
                                                        <c:otherwise>
                                                            Nữ
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>

                                                <td><fmt:formatDate value="${p.getBirthday()}" pattern="yyyy/MM/dd"/> </td>
                                                <td>
                                                    <c:if test="${p.getRoleId()== 0}">
                                                        Nhân viên IT
                                                    </c:if>
                                                    <c:if test="${p.getRoleId()==1}">

                                                        Hiệu trưởng

                                                        Giám Đốc

                                                    </c:if>
                                                    <c:if test="${p.getRoleId()==2}">
                                                        Giáo vụ
                                                    </c:if>
                                                    <c:if test="${p.getRoleId()==3}">
                                                        Giáo viên
                                                    </c:if>
                                                    <c:if test="${p.getRoleId()==5}">
                                                        Nhân viên kế toán
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${p.status == 'đang làm việc'}">
                                                            <span class="badge badge-success">${p.status}</span>
                                                        </c:when>
                                                        <c:when test="${p.status == 'đang chờ xử lý'}">
                                                            <span class="badge badge-warning">${p.status}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-info">${p.status}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>


                                                    <a href="viewpersonnel?id=${p.getId()}&page=list"
                                                       class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Thông
                                                        tin chi tiết</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <!--modal for new personnel-->
                        <div class="modal fade" id="newPersonnelModal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                             data-keyboard="false">
                            <form action="createpersonnel?action=create" id="create-personnel" method="POST">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="form-group col-md-12">
                                                    <span class="thong-tin-thanh-toan">
                                                        <h5>Tạo Nhân Viên Mới</h5>
                                                        <p>Các mục có <a
                                                                style="color: red">(*)</a> là các mục bắt buộc</p>
                                                    </span>
                                                </div>
                                            </div>
                                            <div class="row">

                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Họ<a
                                                            style="color: red">(*)</a></label>
                                                    <input class="form-control" type="text" id="lastname" name="lastname" value="${requestScope.lastname}"
                                                           pattern="^[A-Za-z${vietnamesePattern}\s]{1,50}$" required>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Tên<a
                                                            style="color: red">(*)</a> </label>
                                                    <input class="form-control" id="firstname" type="text" name="firstname" value="${requestScope.firstname}"
                                                           pattern="^[a-zA-Z${vietnamesePattern}\s]{1,20}$" required>
                                                </div>

                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Giới tính<a
                                                            style="color: red">(*)</a> </label>
                                                    <select class="form-control" name="gender" id="gender" required>
                                                        <option value="" hidden selected>Chọn giới tính</option>
                                                        <c:choose>
                                                            <c:when test="${requestScope.gender =='0'}">
                                                                <option value="0" selected>Nữ</option>
                                                                <option value="1">Nam</option>
                                                            </c:when>
                                                            <c:when test="${requestScope.gender =='1'}">
                                                                <option value="0" >Nữ</option>
                                                                <option value="1" selected>Nam</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="0">Nữ</option>
                                                                <option value="1">Nam</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Ngày sinh<a
                                                            style="color: red">(*)</a></label>
                                                    <input class="form-control" id="birthday" type="date" name="birthday" value="${requestScope.birthday}" required>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Địa chỉ<a
                                                            style="color: red">(*)</a></label>
                                                    <input class="form-control" type="text" id="address" name="address" value="${requestScope.address}"
                                                           pattern="^[A-Za-z1-9,${vietnamesePattern}\s]{1,300}$" required>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Email<a
                                                            style="color: red">(*)</a></label>
                                                    <input class="form-control" type="email" id="email" name="email" value="${requestScope.email}" required>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Số điện thoại<a
                                                            style="color: red">(*)</a></label>
                                                    <input class="form-control" type="text" id="phone" pattern="^(0[23578]|09)\d{8}$" name="phone" value="${requestScope.phone}"
                                                           required>


                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label" >Chức vụ<a
                                                            style="color: red">(*)</a> </label>
                                                    <select class="form-control" name="role" id="role" required>
                                                        <option value="" hidden> Chọn chức vụ</option>
                                                        <c:forEach items="${requestScope.roles}" var="r">
                                                            <option ${requestScope.role eq r.getId() ? "selected" : ""}
                                                                value="${r.getId()}">${r.getVNeseDescription()}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Trình độ<a style="color: red">(*)</a></label>
                                                    <input class="form-control" type="text" id="qualification" name="qualification" value="${requestScope.qualification}"
                                                           pattern="^[A-Za-z${vietnamesePattern}\s]{1,100}$" required>
                                                </div>
                                                <!-- New fields for specialization, achievements, and teaching_years -->
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Chuyên môn<a style="color: red">(*)</a></label>
                                                    <input class="form-control" type="text" id="specialization" name="specialization" value="${requestScope.specialization}" pattern="^[A-Za-z${vietnamesePattern}\s]{1,100}$" required>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Thành tựu<a style="color: red">(*)</a></label>
                                                    <input class="form-control" type="text" id="achievements" name="achievements" value="${requestScope.achievements}" pattern="^[A-Za-z0-9,${vietnamesePattern}\s]{0,500}$">
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Số năm kinh nghiệm<a style="color: red">(*)</a></label>
                                                    <input class="form-control" type="number" id="teaching_years" name="teaching_years" value="${requestScope.teaching_years}" min="0" max="100" required>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Tệp CV<a style="color: red">(*)</a></label>
                                                    <input class="form-control" type="file" id="cv_file" name="cv_file" required accept=".pdf,.doc,.docx">
                                                    <div id="cvPreview" class="mt-3 text-center" style="display: none;">
                                                        <p id="cvFileName"></p>
                                                    </div>
                                                </div>

                                                <div class="col-md-12">
                                                    <label for="imageUpload" class="form-label"
                                                           style="cursor: pointer ;margin-left: 14px">Chọn hình ảnh<a
                                                            style="color: red">(*)</a></label>
                                                    <input type="file" class="form-control" id="imageUpload" required
                                                           accept="image/*" onchange="previewImage(event)" name="avatar"
                                                           value="${requestScope.avatar}">

                                                    <div id="imagePreview" class="mt-3 text-center" style="display: none;">
                                                        <img id="preview" src="../images${requestScope.avatar}"
                                                             class="img-fluid rounded-circle" alt="Preview Image">
                                                    </div>
                                                </div>
                                                <script>
                                                    function previewImage(event) {
                                                        var reader = new FileReader();
                                                        reader.onload = function () {
                                                            var preview = document.getElementById('preview');
                                                            preview.src = reader.result;
                                                            document.getElementById('imagePreview').style.display = 'block';
                                                        };
                                                        reader.readAsDataURL(event.target.files[0]);
                                                    }
                                                </script>
                                            </div>
                                            <br>
                                            <button class="btn btn-success" type="submit" id="save-button">Lưu lại</button>
                                            <a class="btn btn-danger" data-dismiss="modal"
                                               id="cancel-button">Hủy bỏ</a>
                                            <br>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>



        <!--===============================================================================================-->
        <script src="js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <!--===============================================================================================-->
        <script src="js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="js/main.js"></script>
        <!--===============================================================================================-->
        <script src="js/plugins/pace.min.js"></script>
        <!--===============================================================================================-->
        <!--===============================================================================================-->

        <script>
                                                    document.getElementById('role').addEventListener('change', function () {
                                                        this.querySelector('option[hidden]').disabled = true;
                                                    });
        </script>
        <script>


            function redirect() {
                window.location.href = "listpersonnel";
            }
        </script>
        <script>
            document.getElementById('role').addEventListener('change', function () {
                this.querySelector('option[hidden]').disabled = true;
            });
            document.getElementById('id').addEventListener('change', function () {
                this.querySelector('option[hidden]').disabled = true;
            });
        </script>
        <script>


            function readURL(input, thumbimage) {
                if (input.files && input.files[0]) { //Sử dụng  cho Firefox - chrome
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#thumbimage").attr('src', e.target.result);
                    }
                    reader.readAsDataURL(input.files[0]);
                } else { // Sử dụng cho IE
                    $("#thumbimage").attr('src', input.value);

                }
                $("#thumbimage").show();
                $('.filename').text($("#uploadfile").val());
                $('.Choicefile').css('background', '#14142B');
                $('.Choicefile').css('cursor', 'default');
                $(".removeimg").show();
                $(".Choicefile").unbind('click');

            }

            $(document).ready(function () {
                $(".Choicefile").bind('click', function () {
                    $("#uploadfile").click();

                });
                $(".removeimg").click(function () {
                    $("#thumbimage").attr('src', '').hide();
                    $("#myfileupload").html('<input type="file" id="uploadfile"  onchange="readURL(this);" />');
                    $(".removeimg").hide();
                    $(".Choicefile").bind('click', function () {
                        $("#uploadfile").click();
                    });
                    $('.Choicefile').css('background', '#14142B');
                    $('.Choicefile').css('cursor', 'pointer');
                    $(".filename").text("");
                });
            });
        </script>
        <script>
            const inpFile = document.getElementById("inpFile");
            const loadFile = document.getElementById("loadFile");
            const previewContainer = document.getElementById("imagePreview");
            const previewImage = previewContainer.querySelector(".image-preview__image");
            const previewDefaultText = previewContainer.querySelector(".image-preview__default-text");
            inpFile.addEventListener("change", function () {
                const file = this.files[0];
                if (file) {
                    const reader = new FileReader();
                    previewDefaultText.style.display = "none";
                    previewImage.style.display = "block";
                    reader.addEventListener("load", function () {
                        previewImage.setAttribute("src", this.result);
                    });
                    reader.readAsDataURL(file);
                }
            });


        </script>
        <script>


            document.addEventListener("DOMContentLoaded", function () {
                const birthInput = document.getElementById("birthday");

                // Calculate the minimum date (3 years ago from today)
                const today = new Date();
                const minDate = new Date(today.setFullYear(today.getFullYear() - 18));
                const minDateString = minDate.toISOString().split('T')[0]; // Format to YYYY-MM-DD

                // Set the minimum date on the input field
                birthInput.setAttribute("max", minDateString);

                // Add event listener to validate the date
                document.getElementById("create-personnel").addEventListener("submit", function (event) {
                    const selectedDate = new Date(birthInput.value);
                    if (selectedDate > minDate) {
                        alert("The birth date must be at least 18 years ago.");
                        event.preventDefault(); // Prevent the form from being submitted
                    }
                });
            });
        </script>
        <script>
            document.getElementById('save-button').addEventListener('click', function (event) {
                const role = document.getElementById('role').value;
                const address = document.getElementById('address').value;
                const lastname = document.getElementById('lastname').value;
                const firstname = document.getElementById('firstname').value;
                const gender = document.getElementById('gender').value;
                const birthday = document.getElementById('birthday').value;
                const email = document.getElementById('email').value;
                const phone = document.getElementById('phone').value;
                const imageUpload = document.getElementById('imageUpload').value;
                const imagePreview = document.getElementById('imagePreview').style.display;
                const previewSrc = document.getElementById('preview').src;
                const  pattern2 = /^[a-zA-ZĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ\s]{1,50}$/;
                const pattern1 = /^[a-zA-ZĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ\s]{1,20}$/;
                const  pattern3 = /^[A-Za-z1-9,ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ\s]{1,300}$/;
                const pattern5 = /^(0[23578]|09)\d{8}$/;
                const pattern4 = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;

                let hasError = false;
                if (address === '' ||
                        role === '' ||
                        lastname === '' ||
                        firstname === '' ||
                        gender === '' ||
                        birthday === '' ||
                        email === '' ||
                        phone === '' ||
                        imageUpload === '' ||
                        imagePreview === 'none' ||
                        previewSrc === '') {

                    toastr.error("Thao tác thất bại! không được bỏ trống các mục bắt buộc");
                    return;
                } else {
                    if (pattern1.test(firstname) === false) {
                        toastr.error("Thao tác Thất bại ! Tên không được có ký tự đặc biệt hay chữ số và chỉ được nhập tối đa 20 ký tự !");
                        hasError = true;

                    }
                    if (pattern2.test(lastname) === false) {
                        toastr.error("Thao tác thất bại! Họ không được có ký tự đặc biệt hay chữ số và chỉ được nhập tối đa 50 ký tự !");
                        hasError = true;

                    }
                    if (isOver18(birthday) === false) {
                        toastr.error("Thao tác thất bại! Không đủ 18 tuổi !");
                        hasError = true;

                    }
                    if (pattern3.test(address) === false) {
                        toastr.error("Thao tác thất bại! Địa chỉ chứa ký tự không hợp lệ!");
                        hasError = true;

                    }

                    if (pattern4.test(email) === false) {
                        toastr.error("Thao tác thất bại! Địa chỉ Email không hợp lệ !");
                        hasError = true;

                    }
                    if (pattern5.test(phone) === false) {
                        toastr.error("Thao tác thất bại! Số điện thoại phải có 10 chữ số và là số điện thoại Việt Nam");
                        hasError = true;

                    }
                    if (!allowedExtensions.exec(imageUpload)) {
                        toastr.error("Chỉ các tập tin .jpg, .jpeg, và .png được cho phép.");
                        hasError = true;


                    }
                    if (hasError) {
                        event.preventDefault(); // Prevent form submission if there are errors
                    }

                }
            });
        </script>
        <script>
            document.getElementById('cancel-button').addEventListener('click', function () {
                document.getElementById('role').value = '';
                document.getElementById('address').value = '';
                document.getElementById('lastname').value = '';
                document.getElementById('firstname').value = '';
                document.getElementById('gender').value = '';
                document.getElementById('birthday').value = '';
                document.getElementById('email').value = '';
                document.getElementById('phone').value = '';
                document.getElementById('imageUpload').value = ''; // Reset the image selection
                document.getElementById('imagePreview').style.display = 'none'; // Hide the image preview
                document.getElementById('preview').src = ''; // Clear the image source
            });
        </script>

        <script>
            function isOver18(birthDate) {
                const today = new Date();
                const birth = new Date(birthDate);

                // Calculate the age
                let age = today.getFullYear() - birth.getFullYear();
                const monthDiff = today.getMonth() - birth.getMonth();
                const dayDiff = today.getDate() - birth.getDate();

                // Adjust age if the birthday has not yet occurred this year
                if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
                    age--;
                }

                return age >= 18;
            }
        </script>

        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>
    </body>
</html>
