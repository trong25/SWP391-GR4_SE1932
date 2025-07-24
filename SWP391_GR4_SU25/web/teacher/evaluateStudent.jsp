<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <title>Danh Sách Học Sinh</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
        const checkAttendance = '<%= request.getAttribute("checkAttendance") %>';
            $(document).ready(function () {
                if (checkAttendance === 'notAttendance') {
                    $('#errorModal').modal('show');
                }
            });
    </script>
    <script>
        $(document).ready(function () {
            var toastMessage = '<%= session.getAttribute("toastMessage") %>';
            var toastType = '<%= session.getAttribute("toastType") %>';
            <%
            session.removeAttribute("toastMessage");
            session.removeAttribute("toastType");
            %>
            if (toastMessage) {
                if (toastType === 'success') {
                    toastr.success(toastMessage);
                } else if (toastType === 'error') {
                    toastr.error(toastMessage);
                }
            }
        });
    </script>
    <style>
        .evaluation-input {
            width: 100%;
            height: 40px;
            text-align: center;
            box-sizing: border-box;
        }

        .evaluation-input input, .evaluation-input select {
            display: inline-block;
            vertical-align: middle;
            align-content: center;
        }
    </style>

</head>

<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Đánh giá học sinh hàng ngày</h1>
                <div class="row">


                </div>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <c:choose>
                            <c:when test="${requestScope.teacherClass == null}">
                                <h6 class="m-0 font-weight-bold text-primary">Lớp: <a
                                        style="color: red">Ngày hôm nay không có lớp</a>
                                </h6>
                            </c:when>
                            <c:otherwise>
                                <h6 class="m-0 font-weight-bold text-primary">Lớp: <a
                                >${requestScope.teacherClass}</a>
                                </h6>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <jsp:useBean id="evaluation" class="model.evaluation.EvaluationDAO"/>
                    <jsp:useBean id="studentAttendance" class="model.student.StudentAttendanceDAO"/>
                    <c:set var="dateId" value="${requestScope.dateId}"/>
                    <div class="card-body">
                    <form method="post" id="evaluateForm" action="evaluate?action=evaluateStudent">
                        <div class="table-responsive">
                            <table class="table table-bordered"      width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Mã học sinh</th>
                                    <th>Ảnh</th>
                                    <th>Họ và tên</th>
                                    <th>Đánh giá</th>
                                    <th>Ghi chú</th>
                                </tr>
                                </thead>
                                <tbody>
                                <div style="color: red">${requestScope.error}</div>
                                <c:forEach var="student" items="${requestScope.listStudent}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${student.id}</td>
                                        <td style="width: 20%;">
                                            <img src="../images/${student.avatar}"
                                                 class="mx-auto d-block"
                                                 style="width:100px; height:100px; object-fit: cover;">
                                        </td>
                                        <td>${student.lastName} ${student.firstName}</td>
                                        <td class="text-center" style="align-content: center">
                                            <c:choose>
                                                <c:when test="${studentAttendance.getAttendanceByStudentAndDay(student.id,dateId).status eq 'absent'}">
                                                    <input value="Nghỉ học" name="evaluation-${student.id}" readonly type="text" class="evaluation-input">
                                                </c:when>
                                                <c:otherwise>
                                                    <select class="form-control evaluation-input" aria-label="Default select example" name="evaluation-${student.id}" required>
                                                        <option value="" hidden="">-</option>
                                                        <option value="Ngày Học Tốt" ${evaluation.getEvaluationByStudentIdAndDay(student.id,dateId).evaluation eq 'Tốt'?"selected":""} name="evaluationId-good">Ngày Học Tốt</option>
                                                        <option value="Ngày Học Khá" ${evaluation.getEvaluationByStudentIdAndDay(student.id,dateId).evaluation eq 'Khá'?"selected":""} name="evaluationId-bad">Ngày Học Khá</option>
                                                    </select>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>


                                        <td class="text-center">
                                            <textarea class="mt-2 form-control" rows="3" name="notes-${student.id}">${evaluation.getEvaluationByStudentIdAndDay(student.id,dateId).notes}</textarea>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            <script>

                            </script>
                            </table>

                            <c:if test="${requestScope.teacherClass != null}">
                                <div class="btn-group-right float-right">
                                    <button type="submit" class="btn btn-success"   style="width: 100px">Lưu</button>
                                </div>
                            </c:if>
                        </div>
                    </form>
                    </div>
                </div>
            <%--            Modal for message error    --%>
                <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" data-backdrop="static">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Thông báo</h5>
                            </div>
                            <div class="modal-body">
                                Vui lòng điểm danh trước khi đánh giá học sinh !!!
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" onclick="goToAttendance()"  >Điểm danh học sinh</button>
                            </div>
                        </div>
                    </div>
                </div>
                <%--            Modal for message error    --%>

            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
<script>
    function goToAttendance() {
        window.location.href= 'takeattendance';
    }
</script>
<!-- Page level plugins -->
<script src="../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="../js/demo/datatables-demo.js"></script>
</body>
</html>
