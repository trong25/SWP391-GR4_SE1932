<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>TaBi Learning Center</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
            $(document).ready(function () {
                $('#selectSchoolyear').change(function () {
                    updateOptions('schoolYearId', $(this).val());
                });

                $('#selectWeek').change(function () {
                    updateOptions('weekId', $(this).val());
                });

                $('#selectGrade').change(function () {
                    updateOptions('gradeId', $(this).val());
                });

                $('#selectClass').change(function () {
                    updateOptions('classId', $(this).val());
                });

                function updateOptions(paramName, paramValue) {
                    $.ajax({
                        url: 'timetable',
                        method: 'GET',
                        data: {
                            [paramName]: paramValue,
                            schoolYearId: $('#selectSchoolyear').val(),
                            weekId: $('#selectWeek').val(),
                            gradeId: $('#selectGrade').val(),
                            classId: $('#selectClass').val()
                        },
                        success: function (response) {
                            $('#selectWeek').html($(response).find('#selectWeek').html());
                            $('#selectGrade').html($(response).find('#selectGrade').html());
                            $('#selectClass').html($(response).find('#selectClass').html());
                        }
                    });
                }
            });
        </script>

        <style>
            #selectWeek {
                width: 95%;
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

    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>

                    <!-- Begin Page Content -->
                    <div class="container-fluid" style="width: 90%">

                        <div class="app-title">
                            <div>
                                <h1><i class="fa fa-calendar"></i> Tạo thời khóa biểu</h1>
                            </div>
                        </div>

                        <!-- Timetable Form -->
                        <form id="combinedForm" action="timetable" method="get">
                            <div class="form-row">
                                <div class="form-group col-md-2">
                                    <label for="selectSchoolyear">Chọn năm:</label>
                                    <select class="form-control" id="selectSchoolyear" name="schoolYearId" style="width: 87%">
                                        <option>Chọn năm</option>
                                        <c:forEach var="schoolYear" items="${requestScope.listSchoolYears}">
                                            <option value="${schoolYear.id}" <c:if test="${param.schoolYearId == schoolYear.id}">selected</c:if>>${schoolYear.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group col-md-3" style="padding-left: 0px; width: 80%;">
                                    <label for="selectWeek">Chọn tuần:</label>
                                    <select class="form-control" id="selectWeek" name="weekId" onchange="submitForms()">
                                        <option>Chọn tuần</option>
                                        <c:forEach var="listWeek" items="${requestScope.listWeek}">
                                            <option value="${listWeek.id}" <c:if test="${param.weekId == listWeek.id}">selected</c:if>>
                                                <fmt:formatDate value="${listWeek.startDate}" pattern="yyyy/MM/dd" /> đến
                                                <fmt:formatDate value="${listWeek.endDate}" pattern="yyyy/MM/dd" />
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-2">
                                    <label for="selectGrade">Chọn khối:</label>
                                    <select class="form-control" id="selectGrade" name="gradeId" onchange="submitForms()" style="width: 87%">
                                        <option>Chọn khối</option>
                                        <c:forEach var="listGrade" items="${requestScope.listGrade}">
                                            <option value="${listGrade.id}" <c:if test="${param.gradeId == listGrade.id}">selected</c:if>>${listGrade.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-2">
                                    <label for="selectClass">Chọn lớp:</label>
                                    <select class="form-control" id="selectClass" name="classId" onchange="submitForms()" style="width: 80%;" value="$">
                                        <option>Chọn lớp</option>
                                        <c:if test="${ not empty requestScope.classList}">
                                            <c:forEach var="classList" items="${requestScope.classList}">
                                                <option value="${classList.id}" <c:if test="${param.classId == classList.id}">selected</c:if>>${classList.name}</option>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${empty requestScope.classList}">
                                            <option>Chưa có lớp</option>
                                        </c:if>

                                    </select>
                                </div>
                            </div>
                        </form>

                        <form id="createTimetableForm" action="timetable?action=create-timetable" method="POST" onsubmit="return validateTimetableForm()">
                            <div class="form-row">
                                <input type="hidden" name="weekId" value="${requestScope.dateWeek}">
                                <input type="hidden" name="classId" value="${requestScope.classSelected}">
                            </div>


                                <table class="timetable-table table table-bordered text-center">
                                    <thead>
                                        <tr class="bg-light-gray">
                                            <th class="text-uppercase">Thời gian</th>
                                            <th class="text-uppercase">Thứ hai</th>
                                            <th class="text-uppercase">Thứ ba</th>
                                            <th class="text-uppercase">Thứ tư</th>
                                            <th class="text-uppercase">Thứ năm</th>
                                            <th class="text-uppercase">Thứ sáu</th>
                                            <th class="text-uppercase">Thứ bảy</th>
                                            <th class="text-uppercase">Chủ Nhật</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${not empty requestScope.dayList}">

                                            <tr>
                                                <td></td>
                                                <c:forEach var="day" items="${requestScope.dayList}">
                                                    <td><fmt:formatDate value="${day.date}" pattern="dd/MM/yyyy" /></td>
                                                </c:forEach>
                                            </tr>
                                        </c:if>
                                        <c:if test="${not empty requestScope.dayList}">
                                            <c:forEach var="timeslot" items="${requestScope.listTimeslot}">
                                                <tr>
                                                    <td class="align-middle">${timeslot.startTime} - ${timeslot.endTime}</td>
                                                    <c:forEach var="day" items="${requestScope.dayList}">
                                                        <td>
                                                            <select class="form-control" name="timeslotId_${day.id}_${timeslot.id}">
                                                                <option value="">Chọn môn học</option>
                                                                <c:forEach var="subject" items="${requestScope.subList}">
                                                                    <option value="${subject.id}" name="subjectId_${day.id}_${timeslot.id}_${subject.id}">${subject.name}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${empty requestScope.dayList}">
                                            <c:forEach var="timeslot" items="${requestScope.listTimeslot}">
                                                <tr>
                                                    <td class="align-middle">${timeslot.startTime} - ${timeslot.endTime}</td>
                                                    <c:forEach var="day" items='${days}'>
                                                        <td>
                                                            <select class="form-control" name="${day}">
                                                                <option value="">-</option>
                                                            </select>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                    </tbody>
                                </table>

                            <div class="btn-container">
                                <div class="d-flex justify-content-end">
                                </div>
                                <div class="btn-group-right">
                                    <button type="submit" class="btn btn-success" style="width: 100px">Lưu</button>
                                    <button type="button" onclick="goBack()" class="btn btn-danger" style="width: 100px">Hủy</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- /.container-fluid -->
                </div>
                <!-- End of Main Content -->
                <jsp:include page="../footer.jsp"/>
            </div>
            <!-- End of Content Wrapper -->
        </div>
        <!-- End of Page Wrapper -->

        <script>
            function submitForms() {
                document.getElementById("combinedForm").submit();
            }

            function validateTimetableForm() {
                const gradeSelect = document.getElementById('selectGrade');
                const classSelect = document.getElementById('selectClass');
                if (gradeSelect.value === "Chọn khối" || classSelect.value === "Chọn lớp") {
                    toastr.error('Vui lòng chọn khối và lớp trước khi lưu.');
                    return false;
                }
                return true;
            }

            function goBack() {
                window.history.back();
            }
        </script>
    </body>

</html>
