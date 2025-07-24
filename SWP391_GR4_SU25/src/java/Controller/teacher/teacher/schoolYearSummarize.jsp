<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="evaluationBean" class="model.evaluation.EvaluationDAO"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <link rel="shortcut icon" type="image/x-icon" href="../image/logo.png" />
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Tổng kết năm học</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
              rel="stylesheet">
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
        <script>
            $(document).ready(function () {
                var toastMessage = '<%= request.getAttribute("toastMessage")%>';
                var toastType = '<%= request.getAttribute("toastType")%>';
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
    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Tổng Kết Khen Thưởng Học Sinh</h1>
                        <div class="row">
                            <div class="col-lg-6">
                                <form action="schoolyearsummarize" id="selectForm">
                                    <div class="year-form">
                                        <label>Chọn năm học</label>
                                        <select class="custom-select" style="width: 40%" aria-label="Default select example" onchange="this.form.submit()"
                                                name="schoolYearId">
                                            <c:forEach items="${requestScope.schoolYears}" var="year">
                                                <option ${requestScope.schoolYearId eq year.id ? "selected" : ""}
                                                    value="${year.id}">${year.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <c:choose>
                            <c:when test="${requestScope.display eq true}">
                                <div class="card shadow mb-4">
                                    <div class="card-body">
                                        <form action="schoolyearsummarize" method="post">
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>STT</th>
                                                            <th>Mã Học Sinh</th>
                                                            <th>Tên</th>
                                                            <th>Giới Tính</th>
                                                            <th>Ngày sinh</th>
                                                            <th>Số ngày tốt</th>
                                                            <th>Số ngày khá</th>
                                                            <th>Nhận xét của giáo viên</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <div style="color: red">${requestScope.error}</div>
                                                    <c:forEach items="${requestScope.students}" var="student" varStatus="status">
                                                        <tr>
                                                            <th scope="row">${status.index + 1}</th>

                                                            <td>${student.getId()}</td>
                                                            <td>${student.getLastName()} ${student.getFirstName()}</td>
                                                            <td>
                                                                <c:if test="${student.getGender()==true}">
                                                                    Nam
                                                                </c:if>
                                                                <c:if test="${student.getGender()==false}">
                                                                    Nữ
                                                                </c:if>
                                                            </td>
                                                            <td><fmt:formatDate value="${student.getBirthday()}" pattern="yyyy/MM/dd"/>  </td>

                                                            <c:if test="${student.isSummarized(requestScope.schoolYearId) eq true}">
                                                                <c:set value="${evaluationBean.getSchoolYearSummarize(student.id, requestScope.schoolYearId)}" var="evaluation"/>

                                                                <td>
                                                                    <input class="form-control" value="${evaluation.goodTicket}" type="text" name="goodTicket${student.id}" readonly>
                                                                    </td>
                                                                    <td>
                                                                        <select class="form-control" name="evaluate${student.id}" <c:if test="${requestScope.expired eq 'true'}">disabled</c:if>>
                                                                        <option value="Đạt" ${evaluation.title == "Đạt" ? "selected" : ""}>Đạt</option>
                                                                        <option value="Không Đạt" ${evaluation.title == "Không Đạt" ? "selected" : ""}>Không Đạt</option>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <textarea name="note${student.id}" class="form-control" rows="1" <c:if test="${requestScope.expired eq 'true'}">readonly</c:if>>${evaluation.teacherNote}</textarea>
                                                                    </td>
                                                            </c:if>

                                                            <c:if test="${student.isSummarized(requestScope.schoolYearId) eq false}">
                                                                <td>
                                                                    <input class="form-control" value="${student.getYearEvaluation(requestScope.schoolYearId)}" type="text" name="goodTicket${student.id}" readonly>
                                                                    </td>
                                                                    <td>
                                                                        <select class="form-control" name="evaluate${student.id}" <c:if test="${requestScope.expired eq 'true'}">disabled</c:if>>
                                                                        <option value="Đạt" ${student.evaluate(requestScope.schoolYearId) == "Đạt" ? "selected" : ""}>Đạt</option>
                                                                        <option value="Không Đạt" ${student.evaluate(requestScope.schoolYearId) == "Không Đạt" ? "selected" : ""}>Không Đạt</option>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <textarea name="note${student.id}" class="form-control" rows="1" <c:if test="${requestScope.expired eq 'true'}">readonly</c:if>></textarea>
                                                                    </td>
                                                            </c:if>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <input type="text" name="schoolYearId" value="${requestScope.schoolYearId}" hidden/>
                                            <c:if test="${requestScope.display eq true}">
                                                <div class="form-group float-right">
                                                    <button type="submit" class="btn btn-success" style="width: 100px" <c:if test="${requestScope.expired eq 'true'}">disabled</c:if>>Lưu</button>
                                                    </div>
                                            </c:if>
                                        </form>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="card shadow mb-4">
                                    <div class="card-body" style="display: flex; justify-content: center">
                                        <p style="color: red">Dữ liệu tổng kết sẽ được cập nhật vào tuần cuối cùng của năm học !</p>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>



                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>

    </body>
</html>
