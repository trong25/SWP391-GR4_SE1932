<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="studentEvaluationBean" class="model.evaluation.EvaluationDAO"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Xem Báo Cáo Đánh Giá</title>
    <script>
        function submitForm(formId) {
            document.getElementById(formId).submit();
        }

        function resetAndSubmitForm(formId){
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

                <div class="row">
                    <div class="col-lg-6 mb-4">
                        <form action="studentsevaluation"  id="selectForm">
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
                                            <c:set var="evaluation" value="${studentEvaluationBean.getEvaluationByStudentIdAndDay(student.id, day.id)}"/>
                                            <td>
                                                
                                                <c:choose>
                                                    <c:when test="${evaluation != null}">
                                                        <c:set value="${evaluation.evaluation}" var="s"/>
                                                        <c:choose>
                                                            <c:when test="${s eq 'Ngày Học Tốt'}">
                                                                <span class="badge badge-success">Ngày Học Tốt</span>
                                                            </c:when>
                                                            <c:when test="${s eq 'Ngày Học Khá'}">
                                                                <span class="badge badge-warning">Ngày Học Khá</span>
                                                            </c:when>
                                                            <c:when test="${s eq 'Nghỉ học'}">
                                                                <span class="badge badge-secondary">Nghỉ học</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge badge-info">${s}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-danger">Chưa cập nhật</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
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
