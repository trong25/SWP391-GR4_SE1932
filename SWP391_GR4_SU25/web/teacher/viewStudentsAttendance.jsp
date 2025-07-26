<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="studentAttendanceBean" class="model.student.StudentAttendanceDAO"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Xem Điểm Danh Học Sinh</title>
        <script>
            function submitForm(formId) {
                document.getElementById(formId).submit();
            }

            function resetAndSubmitForm(formId) {
                document.getElementById('weekSelect').selectedIndex = 0;
                document.getElementById(formId).submit();
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
                        <h1 class="h3 mb-4 text-gray-800 text-center">
                            <c:if test="${not empty requestScope.classes}">
                                Tình trạng điểm danh của học sinh lớp ${requestScope.classes.name}
                            </c:if>
                            <c:if test="${empty requestScope.classes}">
                                Bạn không có lớp trong thời gian này
                            </c:if>
                        </h1>

                        <div class="row">
                            <div class="col-lg-6 mb-4">
                                <form action="studentsattendance"  id="selectForm">
                                    <div>
                                        <div class="mb-4">
                                            <label>Chọn năm học</label>
                                            <select id="schoolYearSelect" class="custom-select" style="width: 25%" aria-label="Default select example" onchange="resetAndSubmitForm('selectForm')" name="schoolYearId">
                                                <c:forEach items="${requestScope.schoolYears}" var="year">
                                                    <option ${requestScope.schoolYearId eq year.id ? "selected" : ""} value="${year.id}">${year.name}</option>
                                                </c:forEach>
                                            </select>
                                            <label class="ml-1">Chọn tuần</label>
                                            <select id="weekSelect" class="custom-select" style="width: 40%" aria-label="Default select example" onchange="submitForm('selectForm')" name="weekId">
                                                <option value="">- Chọn Tuần -</option>
                                                <c:forEach items="${requestScope.weeks}" var="week">
                                                    <option ${requestScope.weekId eq week.id ? "selected" : ""} value="${week.id}">${week.getStartDatetoEndDate()}</option>
                                                </c:forEach>
                                            </select>
                                            <label class="ml-1">Chọn lớp</label>
                                            <select id="classSelect" class="custom-select" style="width: 25%" aria-label="Default select example" onchange="submitForm('selectForm')" name="classId">
                                                <c:forEach items="${requestScope.classList}" var="c">
                                                    <option value="${c.id}" <c:if test="${c.id eq selectedClassId}">selected</c:if>>${c.name}</option>
                                                </c:forEach>
                                            </select>

                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <fmt:setLocale value="vi_VN" />
                        <div class="card shadow mb-4">
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Họ và tên</th>
                                                    <c:forEach var="day" items="${requestScope.days}">
                                                    <th>
                                                        <fmt:formatDate value="${day.date}" pattern="EEEE" /> - <fmt:formatDate value="${day.date}" pattern="yyyy/MM/dd" />
                                                    </th>
                                                </c:forEach>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="student" items="${requestScope.students}" varStatus="status">
                                                <tr>
                                                    <td>${status.index + 1}</td>
                                                    <td>${student.lastName} ${student.firstName}</td>
                                                    <c:forEach var="day" items="${requestScope.days}">
                                                        <c:set var="attendance" value="${studentAttendanceBean.getAttendanceByStudentAndDay(student.id, day.id)}"/>
                                                        <c:set value="${attendance.status}" var="s"/>
                                                        <c:if test="${s eq 'present'}">
                                                            <td><span class="badge badge-success">có mặt</span></td>
                                                        </c:if>
                                                        <c:if test="${s eq 'absent'}">
                                                            <td><span class="badge badge-danger">vắng</span> </td>
                                                        </c:if>
                                                        <c:if test="${s == null}">
                                                            <td><span class="badge badge-warning">chưa cập nhật</span>  </td>
                                                        </c:if>
                                                    </c:forEach>
                                                </tr>
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
