<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Trung tâm dạy thêm TaBi</title>

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
                    <h1 class="h3 mb-0 text-gray-800">Chào mừng đến với trung tâm dạy học TaBi</h1>
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
                        <div class="card border-left-warning shadow h-100 py-2">
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

                <!-- Event List -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Danh sách sự kiện</h6>
                            </div>
                            <c:choose>
                                <c:when test="${requestScope.listEvents.size() > 0}">
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                                <thead>
                                                <tr>
                                                    <th>STT</th>
                                                    <th>Tên sự kiện</th>
                                                    <th>Ngày</th>
                                                    <th>Người gửi</th>
                                                    <th>Chi tiết</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="event" items="${requestScope.listEvents}" varStatus="status">
                                                    <tr>
                                                        <th scope="row">${status.index + 1}</th>
                                                        <td>${event.heading}</td>
                                                        <td><fmt:formatDate value="${event.date}" pattern="yyyy/MM/dd"/></td>
                                                        <td>
                                                                ${event.createdBy.lastName} ${event.createdBy.firstName}
                                                        </td>
                                                        <td class="text-center"><a href="eventDetail?id=${event.id}"
                                                                                   class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Chi tiết</a></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="card-body">
                                        <div class="alert alert-info text-center" role="alert">
                                            <strong>Không có sự kiện nào</strong>
                                            <p>Hiện tại không có sự kiện nào được lên lịch.</p>
                                            <i class="fas fa-calendar-alt fa-2x"></i>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
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