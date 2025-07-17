<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="personnelAttendanceBean" class="model.personnel.PersonnelAttendanceDAO"/>
<jsp:useBean id="dayBean" class="model.day.DayDAO"/>
<html>
    <head>
        <title>Chấm công</title>

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
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Chấm Công Hôm Nay</h1>

                        <c:choose>
                            <c:when test="${requestScope.personnel != null}">
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Danh sách Nhân Sự</h6>
                                    </div>
                                    <div class="card-body">
                                        <form action="taketimekeep" method="post">
                                            <div class="table-responsive">
                                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>STT</th>
                                                            <th>Mã Nhân Sự</th>
                                                            <th>Ảnh</th>
                                                            <th>Họ và tên</th>
                                                            <th>Có mặt</th>
                                                            <th>Vắng</th>
                                                            <th>Ghi chú</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="personnel" items="${requestScope.personnel}" varStatus="status">
                                                            <tr>
                                                                <th scope="row">${status.index + 1}</th>
                                                                <td>${personnel.id}</td>
                                                                <td style="width: 20%;">
                                                                    <img src="../images/${personnel.avatar}"
                                                                         class="mx-auto d-block"
                                                                         style="width:100px; height: 160px; object-fit: cover;">
                                                                </td>
                                                                <td>
                                                                    ${personnel.lastName} ${personnel.firstName}
                                                                    <input type="hidden" name="personnelId" value="${personnel.id}" />
                                                                </td>

                                                                <c:set var="day" value="${dayBean.getDayByDate(requestScope.date)}"/>
                                                                <c:set value="${personnelAttendanceBean.getAttendanceByPersonnelAndDay(personnel.id, day.id)}" var="attendanceStatus"/>
                                                                <td>
                                                                    <input type="radio" name="attendance${personnel.id}" value="present" ${attendanceStatus.status == 'present' ? 'checked' : ''}>
                                                                </td>
                                                                <td>
                                                                    <input type="radio" name="attendance${personnel.id}" value="absent" ${attendanceStatus.status != 'present' ? 'checked' : ''}>
                                                                </td>
                                                                <td>
                                                                    <textarea name="note${personnel.id}" class="form-control" rows="1">${attendanceStatus.note}</textarea>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <c:if test="${requestScope.personnel != null}">
                                                <div class="form-group float-right">
                                                    <button type="submit" class="btn btn-success" style="width: 100px">Lưu</button>
                                                </div>
                                            </c:if>
                                        </form>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="card shadow mb-4">
                                    <div class="card-body" style="display: flex; justify-content: center">
                                        <p style="color: red">  Hôm nay không có lịch !</p>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>
    </body>
</html>
