<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Trung tâm dạy thêm TaBi</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Test CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">

        <style>
            .notification-section, .application-section {
                display: none;
                animation: fadeIn 0.5s ease-in-out;
            }
            
            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(-20px); }
                to { opacity: 1; transform: translateY(0); }
            }
            
            .notification-item, .application-item {
                border-left: 4px solid #4e73df;
                transition: all 0.3s ease;
                cursor: pointer;
            }
            
            .notification-item:hover, .application-item:hover {
                transform: translateX(5px);
                box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            }
            
            .notification-date, .application-date {
                font-size: 0.8rem;
                color: #6c757d;
            }
            
            .notification-heading, .application-heading {
                font-weight: 600;
                color: #2e3d49;
                margin-bottom: 5px;
            }
            
            .notification-details, .application-details {
                color: #666;
                font-size: 0.9rem;
                line-height: 1.4;
            }
            
            .empty-notifications, .empty-applications {
                text-align: center;
                padding: 3rem;
                color: #6c757d;
            }
            
            .empty-notifications i, .empty-applications i {
                font-size: 4rem;
                margin-bottom: 1rem;
                opacity: 0.5;
            }
            
            .notification-card:hover, .application-card:hover {
                cursor: pointer;
                transform: translateY(-2px);
                transition: all 0.3s ease;
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

                        <!-- Page Heading -->
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <h1 class="h3 mb-0 text-gray-800">Bảng điều khiển Teacher</h1>
                        </div>

                        <!-- Content Row -->
                        <div class="row">

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-primary shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                                    Tổng số học sinh
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.listStudentInClass}</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-group fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Attendance Status Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-info shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                                    Trạng thái chấm công
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <c:choose>
                                                        <c:when test="${requestScope.attendanceStatus eq 'Đã chấm công'}">
                                                            <span class="badge badge-success">Đã chấm công</span>
                                                        </c:when>
                                                        <c:when test="${requestScope.attendanceStatus eq 'Chưa chấm công'}">
                                                            <span class="badge badge-danger">Chưa chấm công</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-secondary">${requestScope.attendanceStatus}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-user-check fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>



                            <!-- Notifications Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-warning shadow h-100 py-2 notification-card" id="notificationCard">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                    Thông báo mới
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.sumNotification} thông báo</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-bell fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Today's Schedule Card -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-success shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                    Lịch dạy hôm nay
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.todayClasses} lớp</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <!-- Notifications Section -->
                        <div class="row notification-section" id="notificationSection">
                            <div class="col-12">
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                        <h6 class="m-0 font-weight-bold text-primary">
                                            <i class="fas fa-bell mr-2"></i>Danh sách thông báo
                                        </h6>
                                       
                                    </div>
                                    <div class="card-body">
                                        <c:choose>
                                            <c:when test="${empty requestScope.listNotifications}">
                                                <div class="empty-notifications">
                                                    <i class="fas fa-bell-slash"></i>
                                                    <h5>Không có thông báo nào</h5>
                                                    <p class="text-muted">Hiện tại bạn không có thông báo mới nào.</p>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="list-group list-group-flush">
                                                    <c:forEach var="notification" items="${requestScope.listNotifications}">
                                                        <div class="list-group-item notification-item" onclick="showNotificationDetail('${notification.id}', '${notification.heading}', '${notification.details}', '${notification.createdAt}', '${notification.createdBy.firstName} ${notification.createdBy.lastName}')">
                                                            <div class="d-flex w-100 justify-content-between">
                                                                <div class="notification-heading">${notification.heading}</div>
                                                                <div class="notification-date">
                                                                    <fmt:formatDate value="${notification.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                                                </div>
                                                            </div>
                                                            <div class="notification-details">
                                                                <c:choose>
                                                                    <c:when test="${fn:length(notification.details) > 100}">
                                                                        ${fn:substring(notification.details, 0, 100)}...
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        ${notification.details}
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                            <small class="text-muted">
                                                                <i class="fas fa-user mr-1"></i>Gửi bởi: ${notification.createdBy.firstName} ${notification.createdBy.lastName}
                                                            </small>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
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
