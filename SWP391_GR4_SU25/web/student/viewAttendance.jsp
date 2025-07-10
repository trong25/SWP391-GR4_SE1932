<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="studentAttendanceBean" class="model.student.StudentAttendanceDAO"/>
<jsp:useBean id="timetableBean" class="model.timetable.TimetableDAO"/>
<jsp:useBean id="personneBean" class="model.personnel.PersonnelDAO"/>
<html>
    <head>
        <title>Tình Hình Điểm Danh</title>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
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

            function resetWeekAndSubmitForm() {
                document.getElementById("week").selectedIndex = 0;
                document.getElementById("schoolYearForm").submit();
            }
        </script>
        <style>
            #selectWeek {
                width: 80%;
            }

            #selectYear {
                width: 70%;
            }

            .btn-container {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-top: 20px;
            }

            .btn-group-right {
                display: flex;
                gap: 10px;
            }

            .timetable-table {
                width: 100%;
                border-collapse: collapse;
            }

            .timetable-table thead th {
                background-color: #f8f9fc;
                color: #5a5c69;
                font-weight: 700;
                padding: 12px;
                text-align: center;
                border: 1px solid #e3e6f0;
            }

            .timetable-table tbody td {
                padding: 8px;
                text-align: center;
                border: 1px solid #e3e6f0;
            }

            .timetable-table tbody tr:nth-child(even) {
                background-color: #f8f9fc;
            }

            .timetable-table select {
                width: 100%;
                padding: 6px;
                border-radius: 4px;
                border: 1px solid #d1d3e2;
                font-size: 0.875rem;
                color: #858796;
            }
        </style>
        <script>
            function submitForm(formId) {
                document.getElementById(formId).submit();
            }
        </script>
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="header-student.jsp"/>
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-12 mb-4">
                                <form action="attendance" id="mergedForm">
                                    <div class="mb-4">
                                        <label>Chọn năm học</label>
                                        <select class="custom-select" style="width: 10%" aria-label="Default select example" onchange="submitForm('mergedForm')" name="schoolYearId">
                                            <c:forEach items="${requestScope.schoolYears}" var="year">
                                                <option ${requestScope.schoolYearId eq year.id ? "selected" : ""} value="${year.id}">${year.name}</option>
                                            </c:forEach>
                                        </select>
                                        <label class="ml-1">Chọn tuần</label>
                                        <select class="custom-select" style="width: 25%" aria-label="Default select example" onchange="submitForm('mergedForm')" name="weekId">
                                            <c:forEach items="${requestScope.weeks}" var="week">
                                                <option ${requestScope.weekId eq week.id ? "selected" : ""} value="${week.id}">${week.getStartDatetoEndDate()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Tình hình điểm danh của học sinh ${requestScope.studentFullName}</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>Ngày</th>
                                                <th>Môn học</th>
                                                <th>Ca học</th>
                                                <th>Giáo viên</th>
                                                <th>Trạng thái điểm danh</th>
                                                <th>Ghi chú</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="day" items="${requestScope.days}" varStatus="status">
                                                <c:set var="attendance" value="${studentAttendanceBean.getAttendanceByStudentAndDay(requestScope.studentId, day.id)}"/>
                                                <c:forEach items="${timetableBean.getTeacherByDayId(day.id,sessionScope.student.id)}" var="teacherSlot" varStatus="countStatus">
                                                    <tr>
                                                        <fmt:setLocale value="vi_VN" />
                                                        <c:if test="${countStatus.index eq 0}">
                                                            <td rowspan="${timetableBean.getTeacherByDayId(day.id,sessionScope.student.id).size()}">
                                                                <fmt:formatDate value="${day.date}" pattern="EEEE" /> - <fmt:formatDate value="${day.date}" pattern="yyyy/MM/dd" />
                                                            </td>
                                                        </c:if>
                                                        <td>${teacherSlot.subjectName}</td>
                                                        <td>${teacherSlot.timeslotName}</td>
                                                        <c:if test="${timetableBean.getTeacherByDayId(day.id,sessionScope.student.id) == null}">
                                                            <td>-</td>
                                                        </c:if>
                                                        <td>
                                                            ${personneBean.getPersonnel(teacherSlot.teacherId).lastName} 
                                                            ${personneBean.getPersonnel(teacherSlot.teacherId).firstName} 
                                                        </td>

                                                        <c:set value="${attendance.status}" var="s"/>
                                                        <c:if test="${s eq 'present'}">
                                                            <td><span class="badge badge-success">có mặt</span></td>
                                                        </c:if>
                                                        <c:if test="${s eq 'absent'}">
                                                            <td><span class="badge badge-danger">vắng</span>  </td>
                                                        </c:if>
                                                        <c:if test="${s == null}">
                                                            <td><span class="badge badge-warning">chưa cập nhật</span>  </td>
                                                        </c:if>

                                                        <td>${attendance.note}</td>



                                                    </tr>
                                                </c:forEach>
                                            </c:forEach>
                                        </tbody>
                                    </table>
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
