<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
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

        <title>Tabi Learning Center</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <!--<link href="../css/sb-admin-2.min.css" rel="stylesheet">-->

    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="header-student.jsp"/>
                    <div class="container-fluid">
                        <!-- Page Heading -->
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <h1 class="h3 mb-0 text-gray-800">Chào mừng tới Tabi Learning Center</h1>
                        </div>

                        <!-- Content Row -->
                        <div class="row">

                            <!-- Earnings (Monthly) Card Example -->
                            <!-- Menu Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-${requestScope.takeAttendance eq 'Có mặt' ? 'success' : (requestScope.takeAttendance eq 'Vắng' ? 'danger' : 'secondary')} shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-${requestScope.takeAttendance eq 'Có mặt' ? 'success' : (requestScope.takeAttendance eq 'Vắng' ? 'danger' : 'secondary')} text-uppercase mb-1">
                                                    Điểm danh hôm nay
                                                </div>
                                                <div class="row no-gutters align-items-center">
                                                    <div class="col-auto">
                                                        <c:choose>
                                                            <c:when test="${requestScope.takeAttendance eq 'Đang không trong năm học'}">
                                                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">Đang không trong năm học</div>
                                                            </c:when>
                                                            <c:when test="${requestScope.takeAttendance eq 'Có mặt' || requestScope.takeAttendance eq 'Vắng'}">
                                                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">${requestScope.takeAttendance}</div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">Chưa cập nhật</div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-star text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Tasks Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-info shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                                    Đánh giá hôm nay
                                                </div>
                                                <div class="row no-gutters align-items-center">
                                                    <div class="col-auto">
                                                        <c:if test="${requestScope.evaluation ne ''}">
                                                            <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">${requestScope.evaluation}</div>
                                                        </c:if>
                                                        <c:if test="${requestScope.evaluation eq ''}">
                                                            <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">Chưa cập nhật</div>
                                                        </c:if>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-star text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-warning shadow h-100 py-2" style="cursor: pointer;" onclick="showNotifications()">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                    Thông báo
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.notifications} Thông báo mới</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-bell fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Timetable Section -->
                        <div class="row mb-4">
                            <div class="col-lg-12">
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Thời khóa biểu</h6>
                                    </div>
                                    <div class="card-body">
                                        <c:choose>
                                            <c:when test="${not empty timeslotList && not empty dayList}">
                                                <div class="table-responsive">
                                                    <table class="table table-bordered table-striped" width="100%" cellspacing="0">
                                                        <thead class="thead-light">
                                                            <tr>
                                                                <th class="text-center">Ca học</th>
                                                                <th class="text-center">Thời gian</th>
                                                                <c:forEach var="day" items="${dayList}">
                                                                    <th class="text-center">
                                                                        <fmt:formatDate value="${day.date}" pattern="EEEE"/><br/>
                                                                        <fmt:formatDate value="${day.date}" pattern="dd-MM-yyyy"/>
                                                                    </th>
                                                                </c:forEach>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="timeslot" items="${timeslotList}">
                                                                <tr>
                                                                    <td class="text-center font-weight-bold">${timeslot.slotNumber}</td>
                                                                    <td class="text-center">
                                                                        <small class="text-muted">${timeslot.startTime} - ${timeslot.endTime}</small>
                                                                    </td>
                                                                    <c:forEach var="day" items="${dayList}">
                                                                        <td class="text-center">
                                                                            <c:set var="found" value="false"/>
                                                                            <c:forEach var="timetable" items="${timetables}">
                                                                                <c:if test="${timetable.timeslot.id eq timeslot.id && timetable.day.id eq day.id}">
                                                                                    <c:set var="found" value="true"/>
                                                                                    <c:choose>
                                                                                        <c:when test="${timetable.attendanceStatus eq 'present'}">
                                                                                            <span class="badge badge-success p-2">
                                                                                                ${timetable.subject.name} - ${timetable.teacher.lastName} ${timetable.teacher.firstName} (Có mặt)
                                                                                            </span>
                                                                                        </c:when>
                                                                                        <c:when test="${timetable.attendanceStatus eq 'absent'}">
                                                                                            <span class="badge badge-danger p-2">
                                                                                                ${timetable.subject.name} - ${timetable.teacher.lastName} ${timetable.teacher.firstName} (Vắng)
                                                                                            </span>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <span class="badge badge-secondary p-2">
                                                                                                ${timetable.subject.name} - ${timetable.teacher.lastName} ${timetable.teacher.firstName} (Chưa học)
                                                                                            </span>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                            
                                                                        </td>
                                                                    </c:forEach>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="alert alert-info text-center" role="alert">
                                                    <strong>Không có thời khóa biểu</strong>
                                                    <p>Hiện tại chưa có thời khóa biểu nào được cập nhật.</p>
                                                    <i class="fas fa-calendar-times fa-2x"></i>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <!-- Notification Modal -->
                        <div class="modal fade" id="notificationModal" tabindex="-1" role="dialog" aria-labelledby="notificationModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="notificationModalLabel">
                                            <i class="fas fa-bell mr-2"></i>Danh sách thông báo
                                        </h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <c:choose>
                                            <c:when test="${not empty requestScope.listNotifications}">
                                                <div class="list-group">
                                                    <c:forEach var="notification" items="${requestScope.listNotifications}" varStatus="status">
                                                        <div class="list-group-item list-group-item-action">
                                                            <div class="d-flex w-100 justify-content-between">
                                                                <h6 class="mb-1 text-primary">${notification.heading}</h6>
                                                                <small class="text-muted">
                                                                    <fmt:formatDate value="${notification.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                                                </small>
                                                            </div>
                                                            <p class="mb-1">${notification.details}</p>
                                                            <small class="text-muted">
                                                                <i class="fas fa-user mr-1"></i>
                                                                Từ: ${notification.createdBy.lastName} ${notification.createdBy.firstName}
                                                            </small>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="text-center py-4">
                                                    <i class="fas fa-bell-slash fa-3x text-muted mb-3"></i>
                                                    <h5 class="text-muted">Không có thông báo nào</h5>
                                                    <p class="text-muted">Hiện tại bạn không có thông báo mới nào.</p>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <script>
                            function showNotifications() {
                                $('#notificationModal').modal('show');
                            }
                        </script>

                        <!-- Bootstrap core JavaScript-->
                        <script src="../vendor/jquery/jquery.min.js"></script>
                        <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

                        <!-- Core plugin JavaScript-->
                        <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

                        <!-- Custom scripts for all pages-->
                        <script src="../js/sb-admin-2.min.js"></script>

                        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
                        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

                        <!-- Page level custom scripts -->
                        <script src="../js/demo/datatables-demo.js"></script>
                        </body>

                        </html>