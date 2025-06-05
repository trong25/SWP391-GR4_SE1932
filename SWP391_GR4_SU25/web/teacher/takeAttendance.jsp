<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="studentAttendanceBean" class="model.student.StudentAttendanceDAO"/>
<jsp:useBean id="dayBean" class="model.day.DayDAO"/>
<html>
<head>
    <title>Điểm Danh</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
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
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Điểm Danh Hôm Nay</h1>

                <!-- Debug information -->
                <div style="display: none;">
                    Debug Info:
                    <p>Current Date: ${requestScope.currentDate}</p>
                    <p>Class Name: ${requestScope.className}</p>
                    <p>Number of Students: ${requestScope.students.size()}</p>
                </div>

                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Danh sách học sinh</h6>
                        <h6 class="m-0 font-weight-bold text-primary">Lớp : <a style="margin-right: 60px" >${requestScope.className == null ?"Ngày hôm nay không có lớp":requestScope.className}</a></h6>
                    </div>
                    <div class="card-body">
                        <form action="takeattendance" method="post">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Mã học sinh</th>
                                        <th>Ảnh</th>
                                        <th>Họ và tên</th>
                                        <th>Có mặt</th>
                                        <th>Vắng</th>
                                        <th>Ghi chú</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${empty requestScope.students}">
                                        <tr>
                                            <td colspan="7" class="text-center">Không có học sinh nào trong danh sách</td>
                                        </tr>
                                    </c:if>
                                    <c:forEach var="student" items="${requestScope.students}" varStatus="status">
                                        <tr>
                                            <th scope="row">${status.index + 1}</th>
                                            <td>${student.id}</td>
                                            <td style="width: 20%;">
                                                <img src="../images/${student.avatar}"
                                                     class="mx-auto d-block"
                                                     style="width:100px; height:100px; object-fit: cover;">
                                            </td>
                                            <td>${student.lastName} ${student.firstName}</td>
                                            <c:set var="day" value="${dayBean.getDayByDate(requestScope.currentDate).id}"/>
                                            <c:set value="${studentAttendanceBean.getAttendanceByStudentAndDay(student.id, day)}" var="attendance"/>
                                            <td>
                                                <input type="radio" name="attendance${student.id}" value="present" ${attendance.status == 'present' ? 'checked' : ''}>
                                            </td>
                                            <td>
                                                <input type="radio" name="attendance${student.id}" value="absent" ${attendance.status != 'present' ? 'checked' : ''}>
                                            </td>
                                            <td><textarea name="note${student.id}" class="form-control" rows="1">${attendance.note}</textarea></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <c:if test="${not empty requestScope.students}">
                                <div class="form-group float-right">
                                    <button type="submit" class="btn btn-success" style="width: 100px">Lưu</button>
                                </div>
                            </c:if>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
</body>
</html>
