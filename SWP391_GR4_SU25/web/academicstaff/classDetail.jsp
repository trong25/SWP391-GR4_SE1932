<%-- 
    Document   : classDetail
    Created on : Jun 12, 2025, 3:36:35 PM
    Author     : MSI
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<jsp:useBean id="dayBean" class="model.day.DayDAO"/>--%>
<jsp:useBean id="personnelBean" class="model.personnel.PersonnelDAO"/>
<html>

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Chi tiết lớp học</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>




        <%
            String toastMessage = (String) session.getAttribute("toastMessage");
            String toastType = (String) session.getAttribute("toastType");
            session.removeAttribute("toastMessage");
            session.removeAttribute("toastType");
            String popUpModal = (String) request.getAttribute("popUpModal");
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
                var popUpModal = '<%= popUpModal %>';
                if (popUpModal === 'true') {
                    $('#assignTeacher').modal('show');
                    document.getElementById('substituteCheckbox').checked = true;
                    document.getElementById('substituteTeacherDiv').style.display = 'block';
                    document.getElementById('formAction').value = 'assignSubTeacher';
                    document.getElementById('teacher').style.display = 'none';
                    document.getElementById('teacherLabel').style.display = 'none';
                    document.getElementById('assignSubTeacherSubmitButton').style.display = 'block';
                    document.getElementById('assignTeacherSubmitButton').style.display = 'none';
                }

            });
        </script>
        <script>
            //// Begin confirm message javascript for add pupil to class, move out class for pupil function
            function confirmAccept(formId, msg) {
                formIdToAccept = formId;
                document.getElementById('confirmMessageTitle').innerText = msg;
                $('#confirmMessage').modal('show');
            }
            $(document).ready(function () {
                $('#confirmMessageButton').click(function () {
                    document.getElementById(formIdToAccept).submit();

                });
            });
            //// End confirm message javascript for add pupil to class, move out class for pupil function
            function alertMessage() {
                document.getElementById('alertMessage').innerText = 'Thao tác không khả dụng với năm học trong quá khứ !!';
                $('#alertModal').modal('show');
            }
            function notReadyMessage() {
                document.getElementById('alertMessage').innerText = 'Lớp chưa được duyệt !!';
                $('#alertModal').modal('show');
            }

            function confirmAssign(formId, msg) {
                formIdToSubmit = formId;
                additionalConfirmationNeeded = '<%= request.getAttribute("teacherName") %>' !== 'null null';

                document.getElementById('confirmationMessage').innerText = msg;
                $('#confirmationModal').modal('show');
            }

            $(document).ready(function () {
                $('#confirmButton').click(function () {
                    if (additionalConfirmationNeeded) {
                        additionalConfirmationNeeded = false;
                        document.getElementById('confirmationMessage').innerText = 'Lớp này đã được phân công giáo viên. Bạn có chắc muốn phân công giáo viên đã chọn không?';
                        $('#confirmationModal').modal('show');
                    } else {
                        document.getElementById(formIdToSubmit).submit();
                    }
                });
            });

            function submit(formId) {
                document.getElementById(formId).submit();
            }
        </script>
        <script>
            function back() {
                window.location.href = "class";
            }
        </script>
        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <c:set var="classes" value="${requestScope.classes}"/>
                        <c:set value="${requestScope.checkedDate}" var="checkedDateInThePast"/>
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Học Sinh</h1>
                        <div class="row align-items-center">
                            <!-- Form section with select elements -->
                            <div class="me-3 mb-4 mr-3 ml-3">
                                <c:choose>
                                    <c:when test="${classes.status eq 'đã được duyệt'}">
                                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                                data-target="${checkedDateInThePast ? '' : '#addPupilToClass'}"
                                                onclick="${checkedDateInThePast ? 'alertMessage()' : ''}">
                                            Thêm Học Sinh Vào Lớp
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Code for the else condition goes here -->
                                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                                data-target=""
                                                onclick="notReadyMessage()">
                                            Thêm Học Sinh Vào Lớp
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="mb-4 mr-3">
                                <c:choose>
                                    <c:when test="${classes.status eq 'đã được duyệt'}">
                                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                                data-target="${checkedDateInThePast ? '' : '#assignTeacher'}"
                                                onclick="${checkedDateInThePast ? 'alertMessage()' : ''}">
                                            Phân Công Giáo Viên
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Code for the else condition goes here -->
                                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                                data-target=""
                                                onclick="notReadyMessage()">
                                            Phân Công Giáo Viên
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="mb-4 mr-3">
                                <c:choose>
                                    <c:when test="${classes.status eq 'đã được duyệt'}">
                                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                                data-target="${checkedDateInThePast ? '' : '#moveOutPupil'}"
                                                onclick="${checkedDateInThePast ? 'alertMessage()' : ''}">
                                            Đổi lớp cho học sinh
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Code for the else condition goes here -->
                                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                                data-target=""
                                                onclick="notReadyMessage()">
                                            Đổi lớp cho học sinh
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="card shadow mb-4">

                            <div class="card-header py-3">
                                <c:choose>
                                    <c:when test="${requestScope.teacherName eq 'null null'}">
                                        <h6 class="m-0 font-weight-bold text-primary">Giáo viên: <a
                                                style="color: red">Chưa được phân công</a>
                                        </h6>
                                    </c:when>
                                    <c:otherwise>
                                        <h6 class="m-0 font-weight-bold text-primary">Giáo viên: <a
                                                >${requestScope.teacherName}</a>
                                        </h6>
                                    </c:otherwise>
                                </c:choose>
                                <h6 class="m-0 font-weight-bold text-primary">Lớp: <a
                                        style="margin-right: 60px;">${classes.name}</a></h6>
                                <h6 class="m-0 font-weight-bold text-primary">Khối: <a
                                        >${classes.grade.name}</a></h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Mã học sinh</th>
                                                <th>Ảnh</th>
                                                <th>Họ và tên</th>
                                                <th>Ngày sinh</th>
                                                <th>Địa chỉ</th>
                                                <th>Mã Trường Học</th>
                                                <th>Tên Trường Học</th>
                                                <th>Tên Lớp Học</th>
                                                <th>Địa Chỉ Trường Học</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="student" items="${requestScope.listStudent}" varStatus="status">
                                                <tr>
                                                    <th scope="row">${status.index + 1}</th>
                                                    <td>${student.id}</td>
                                                    <td style="width: 20%;"><img src="../images/${student.avatar}"
                                                                                 class="mx-auto d-block"
                                                                                 style="width:100px; height:100px; object-fit: cover;">
                                                    </td>
                                                    <td>${student.lastName} ${student.firstName}</td>
                                                    <td><fmt:formatDate value="${student.birthday}" pattern="yyyy/MM/dd"/></td>
                                                    <td>${student.address}</td>
                                                    <td>"${student.school_id.id}" </td>
                                                    <td>${student.school_id.schoolName}</td>
                                                    <td>${student.school_class_id.className}</td>
                                                    <td>${student.school_id.addressSchool}</td>
                                                    <td class="d-flex justify-content-center align-items-center"
                                                        style="height: 150px;">
                                                        <form method="post" action="studentprofileclass">
                                                            <input hidden="" value="${student.id}" name="id"/>
                                                            <input hidden="" value="${sessionScope.classId}" name="classId"/>
                                                            <input hidden="" value="view" name="action"/>
                                                            <button type="submit" class="btn btn-primary">   Thông tin chi tiết</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="btn-group-right float-right">
                                        <button type="button" class="btn btn-primary" onclick="back()"  style="width: 100px">Quay lại</button>
                                    </div>

                                </div>
                            </div>
                        </div>


                        <%--    Add Pupil To Class Modal            --%>
                        <div class="modal fade addPupilToClass" id="addPupilToClass" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="row">
                                        <div class="form-group col-md-12">
                                            <span class="thong-tin-thanh-toan">
                                                <h5 style="margin: 14px">Thêm học sinh mới vào lớp</h5>

                                            </span>
                                            <div class="d-flex justify-content-end mt-3 mr-2">
                                                <button class="btn btn-primary" onclick="toggleCheckboxes()">Chọn / Bỏ 10 học
                                                    sinh
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card shadow mb-4">
                                        <form method="post" action="classdetail?action=addStudent" id="accept-form">
                                            <input hidden="" value="${classes.id}" name="classId">
                                            <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                                <button id="add-button" type="button" class="btn btn-outline-success"
                                                        onclick="confirmAccept('accept-form', 'Bạn có chắc chắn muốn thêm những học sinh này vào lớp?')">
                                                    <i class="fas fa-plus"></i> Thêm học sinh
                                                </button>

                                            </div>
                                            <div class="card-body">
                                                <div class="table-responsive">
                                                    <table class="table table-bordered" id="dataTable" width="100%"
                                                           cellspacing="0">
                                                        <thead>
                                                            <tr>

                                                                <th>STT</th>
                                                                <th>Mã học sinh</th>
                                                                <th>Ảnh</th>
                                                                <th>Họ và tên</th>
                                                                <th>Ngày sinh</th>
                                                                <th>Tên Trường Học</th>
                                                                <th>Địa Chỉ Trường Học</th>
                                                                <th>Hành động</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="student" items="${requestScope.listAllStudent}"
                                                                       varStatus="status">
                                                                <tr>
                                                                    <th scope="row">${status.index + 1}</th>
                                                                    <td>${student.id}</td>
                                                                    <td style="width: 20%;">
                                                                        <img src="../images/${student.avatar}"
                                                                             class="mx-auto d-block"
                                                                             style="width:100px; height:100px; object-fit: cover;">
                                                                    </td>
                                                                    <td>${student.lastName} ${student.firstName}</td>
                                                                    <td><fmt:formatDate value="${student.birthday}"
                                                                                    pattern="yyyy/MM/dd"/></td>
                                                                    <td>${student.school_id.schoolName}</td>
                                                                    <td>${student.school_id.addressSchool}</td>
                                                                    <td class="align-middle text-center">
                                                                        <div class="form-check custom-checkbox d-flex justify-content-center align-items-center">
                                                                            <input style="cursor: pointer;" class="form-check-input"
                                                                                   type="checkbox" value="${student.id}"
                                                                                   id="myCheckbox" name="studentSelected">
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%-- End modal Add pupil to class --%>



                        <%-- Begin modal for move out class for pupil--%>
                        <div class="modal fade" id="moveOutPupil" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                            <form action="classdetail?action=moveOutClassForStudent" method="POST" id="moveOutForm">
                                <input hidden="" name="classId" value="${requestScope.classes.id}">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="form-group col-md-12">
                                                    <span class="thong-tin-thanh-toan">
                                                        <h5>Đổi lớp cho học sinh</h5>
                                                    </span>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <p style="margin-left: 11px;font-weight: bold">Ghi chú: <a style="font-weight: normal">Các thông tin có dấu</a><a style="color: red"> (*) </a><a style="font-weight: normal">là thông tin bắt buộc phải nhập</a></p>
                                                <div class="col-md-7">
                                                    <div class="form-group">
                                                        <label class="control-label" for="pupil">Mã - Tên học sinh<a style="color: red">(*)</a></label>
                                                        <select class="form-control" id="pupil" name="student" required>
                                                            <option value="">-- Chọn Học Sinh --</option>
                                                            <c:forEach var="student" items="${requestScope.listStudent}">
                                                                <option value="${student.id}" ${param.student eq student.id ? "selected":""}>${student.id} - ${student.lastName} ${student.firstName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-5">
                                                    <div class="form-group">
                                                        <label for="classes">Lớp<a style="color: red">(*)</a></label>
                                                        <select class="form-control" id="classes" name="classes" required>
                                                            <option value="">-- Chọn Lớp --</option>
                                                            <c:forEach var="classes" items="${requestScope.moveOutClass}">
                                                                <option value="${classes.id}" ${param.classes eq classes.id ? "selected":""}>${classes.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <br>
                                            <button class="btn btn-success" type="button" onclick="confirmAccept('moveOutForm', 'Bạn có chắc chắn muốn đổi lớp học sinh này?')">Lưu lại</button>
                                            <a class="btn btn-danger" data-dismiss="modal" id="cancel-button">Hủy bỏ</a>
                                            <br>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <%-- End modal for move out class for pupil--%>


                        <%-- Begin modal for assign teacher to class --%>
                        <div class="modal fade" id="assignTeacher" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                            <form action="classdetail" method="POST" id="assignTeacherForm">
                                <input type="text" value="assignTeacher" name="action" id="formAction" hidden/>
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="form-group col-md-12">
                                                    <span class="thong-tin-thanh-toan">
                                                        <h5>Phân công giáo viên vào lớp</h5>
                                                    </span>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <p style="margin-left: 11px;font-weight: bold">Ghi chú: <a style="font-weight: normal">Các thông tin có dấu</a><a style="color: red"> (*) </a><a style="font-weight: normal">là thông tin bắt buộc phải nhập</a></p>
                                                <div class="col-md-7">
                                                    <%--  current teacher--%>
                                                    <div class="form-group">
                                                        <label class="control-label" for="pupil">Mã - Tên giáo viên hiện tại<a style="color: red">*</a></label>
                                                        <input class="form-control" type="text" value="${requestScope.teacher.id} - ${requestScope.teacher.lastName} ${requestScope.teacher.firstName}">
                                                    </div>
                                                    <%-- new teacher --%>
                                                    <div class="form-group">
                                                        <label id="teacherLabel" class="control-label" for="pupil">Mã - Tên giáo viên mới<a style="color: red">*</a></label>
                                                        <select class="form-control" id="teacher" name="teacher" required>
                                                            <option value="">-- Chọn Giáo Viên --</option>
                                                            <c:forEach var="teacher" items="${requestScope.teachers}">
                                                                <option value="${teacher.id}" ${param.teacher eq teacher.id ? "selected":""}>${teacher.id} - ${teacher.lastName} ${teacher.firstName}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <%--                                                        class id--%>
                                                        <input hidden value="${requestScope.classes.id}" name="classId">
                                                    </div>

                                                    <%--                                                        sub teacher--%>
<!--                                                    <div class="form-group">
                                                        <input type="checkbox" id="substituteCheckbox" name="substituteCheckbox">
                                                        <label for="substituteCheckbox">Phân công giáo viên dạy thay</label>
                                                    </div>
                                                    <div class="form-group" id="substituteTeacherDiv" style="display:none;">
                                                        <label class="control-label mt-2" for="days">Chọn ngày<a style="color: red">*</a></label>
                                                        <select class="form-control" id="days" name="day" onchange="submit('assignTeacher')">
                                                            <option value="">-- Chọn Ngày --</option>
                                                            <c:forEach var="day" items="${dayBean.getDaysInFutureWithTimetableForClass(requestScope.classes.id)}">
                                                                <option value="${day.id}" ${requestScope.dayId eq day.id ? "selected":""}>${day.date}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <label class="control-label" for="substituteTeacher">Mã - Tên giáo viên Dạy Thay<a style="color: red">*</a></label>
                                                        <select class="form-control" id="substituteTeacher" name="substituteTeacher">
                                                            <option value="null">-- Chọn Giáo Viên --</option>
                                                            <c:forEach var="teacher" items="${requestScope.freeTeachers}">
                                                                <option value="${teacher.id}" ${param.substituteTeacher eq teacher.id ? "selected":""}>${teacher.id} - ${teacher.lastName} ${teacher.firstName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>-->
                                                </div>
                                            </div>
                                            <br>
                                            <div class="form-group row" style="display: flex;">
                                                <button id="assignTeacherSubmitButton" class="btn btn-success ml-1" type="button" onclick="confirmAssign('assignTeacherForm', 'Bạn có chắc chắn muốn phân công giáo viên này vào lớp?')">Lưu lại</button>
                                                <button id="assignSubTeacherSubmitButton" style="display: none" class="btn btn-success ml-1" type="button" onclick="confirmAccept('assignTeacherForm', 'Bạn có chắc chắn muốn phân công giáo viên dạy thay này?')">Lưu lại</button>
                                                <a class="btn btn-danger ml-1" data-dismiss="modal" id="cancel-button">Hủy bỏ</a>
                                            </div>
                                            <br>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <%-- End modal for assign teacher to class--%>
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

                    <%-- Begin confirmMessage modal--%>
                    <div class="modal fade" id="confirmMessage" tabindex="-1" role="dialog" aria-labelledby="confirmationModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" >Xác nhận thao tác</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body" id="confirmMessageTitle">
                                    <!-- Dynamic message will be inserted here -->
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                    <button type="button" class="btn btn-primary" id="confirmMessageButton">Xác Nhận</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%-- End confirmMessage modal--%>

                    <%-- Begin alert modal--%>
                    <div class="modal fade" id="alertModal" tabindex="-1" role="dialog" aria-labelledby="confirmationModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="alertModals">Thông báo</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body" id="alertMessage">
                                    <!-- Dynamic message will be inserted here -->
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Xác nhận</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%-- End alert modal--%>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
        <script>
            // Variable to track the current state (true: checked, false: unchecked)
            var areChecked = false;

            // Function to toggle the first 30 checkboxes
            function toggleCheckboxes() {
                // Get all checkboxes with the class 'myCheckbox'
                var checkboxes = document.querySelectorAll('#myCheckbox');
                // Loop through the first 30 checkboxes and toggle their checked state
                for (var i = 0; i < 10 && i < checkboxes.length; i++) {
                    checkboxes[i].checked = !areChecked;
                }
                // Toggle the state variable
                areChecked = !areChecked;
            }

            document.getElementById('substituteCheckbox').addEventListener('change', function () {
                var substituteTeacherDiv = document.getElementById('substituteTeacherDiv');
                if (this.checked) {
                    substituteTeacherDiv.style.display = 'block';
                    document.getElementById('formAction').value = 'assignSubTeacher';
                    document.getElementById('teacher').style.display = 'none';
                    document.getElementById('teacherLabel').style.display = 'none';
                    document.getElementById('assignSubTeacherSubmitButton').style.display = 'block';
                    document.getElementById('assignTeacherSubmitButton').style.display = 'none';
                } else {
                    substituteTeacherDiv.style.display = 'none';
                    document.getElementById('formAction').value = 'assignTeacher';
                    document.getElementById('teacher').style.display = 'block';
                    document.getElementById('teacherLabel').style.display = 'block';
                    document.getElementById('assignSubTeacherSubmitButton').style.display = 'none';
                    document.getElementById('assignTeacherSubmitButton').style.display = 'block';
                }
            });
        </script>
        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>
    </body>

</html>