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

        <title>Tabi Learning Center</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">

    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="header.jsp"/>
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
                                <div class="card border-left-${requestScope.takeAttendance eq "present"?"success":"danger"} shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-${requestScope.takeAttendance eq "present"?"success":"danger"} text-uppercase mb-1">
                                                    Điểm danh hôm nay
                                                </div>
                                                <div class="row no-gutters align-items-center">
                                                    <div class="col-auto">
                                                        <c:choose>
                                                            <c:when test="${requestScope.takeAttendance eq 'Đang không trong năm học'}">
                                                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">Đang không trong năm học</div>
                                                            </c:when>
                                                            <c:when test="${requestScope.takeAttendance eq 'present' || requestScope.takeAttendance eq 'absent'}">
                                                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">${requestScope.takeAttendance eq 'present' ? "Có mặt" : "Vắng"}</div>
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
                                            <c:when test="${not empty requestScope.timetableList}">
                                                <div class="table-responsive">
                                                    <table class="table table-bordered table-striped" width="100%" cellspacing="0">
                                                        <thead class="thead-light">
                                                            <tr>
                                                                <th class="text-center" style="width: 8%;">Ca học</th>
                                                                <th class="text-center" style="width: 12%;">Thời gian</th>
                                                                <th class="text-center" style="width: 11.5%;">Thứ 2</th>
                                                                <th class="text-center" style="width: 11.5%;">Thứ 3</th>
                                                                <th class="text-center" style="width: 11.5%;">Thứ 4</th>
                                                                <th class="text-center" style="width: 11.5%;">Thứ 5</th>
                                                                <th class="text-center" style="width: 11.5%;">Thứ 6</th>
                                                                <th class="text-center" style="width: 11.5%;">Thứ 7</th>
                                                                <th class="text-center" style="width: 11.5%;">Chủ nhật</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="timetable" items="${requestScope.timetableList}">
                                                                <tr>
                                                                    <td class="text-center font-weight-bold">${timetable.slotNumber}</td>
                                                                    <td class="text-center">
                                                                        <small class="text-muted">${timetable.timeSlot}</small>
                                                                    </td>
                                                                    <td class="text-center">
                                                                        <c:if test="${not empty timetable.monday}">
                                                                            <span class="badge badge-primary p-2">${timetable.monday}</span>
                                                                        </c:if>
                                                                    </td>
                                                                    <td class="text-center">
                                                                        <c:if test="${not empty timetable.tuesday}">
                                                                            <span class="badge badge-success p-2">${timetable.tuesday}</span>
                                                                        </c:if>
                                                                    </td>
                                                                    <td class="text-center">
                                                                        <c:if test="${not empty timetable.wednesday}">
                                                                            <span class="badge badge-info p-2">${timetable.wednesday}</span>
                                                                        </c:if>
                                                                    </td>
                                                                    <td class="text-center">
                                                                        <c:if test="${not empty timetable.thursday}">
                                                                            <span class="badge badge-warning p-2">${timetable.thursday}</span>
                                                                        </c:if>
                                                                    </td>
                                                                    <td class="text-center">
                                                                        <c:if test="${not empty timetable.friday}">
                                                                            <span class="badge badge-danger p-2">${timetable.friday}</span>
                                                                        </c:if>
                                                                    </td>
                                                                    <td class="text-center">
                                                                        <c:if test="${not empty timetable.saturday}">
                                                                            <span class="badge badge-secondary p-2">${timetable.saturday}</span>
                                                                        </c:if>
                                                                    </td>
                                                                    <td class="text-center">
                                                                        <c:if test="${not empty timetable.sunday}">
                                                                            <span class="badge badge-dark p-2">${timetable.sunday}</span>
                                                                        </c:if>
                                                                    </td>
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

    </body>

</html>