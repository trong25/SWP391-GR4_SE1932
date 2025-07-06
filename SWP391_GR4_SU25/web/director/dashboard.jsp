<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Trung tâm dạy thêm TaBi - DashBoard</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,300,400,600,700,800,900" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
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
                            <h1 class="h3 mb-0 text-gray-800">Chào mừng đến với trung tâm dạy học TaBi</h1>
                        </div>

                        <!-- Content Row - Các thống kê -->
                        <div class="row">
                            <!-- Số học sinh -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-primary shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                                    Số học sinh đang theo học
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    ${requestScope.numberOfStudent}
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-user fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Số lớp học -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-success shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <c:choose>
                                                    <c:when test="${requestScope.listClass.size() > 0}">
                                                        <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                            Số lớp trong năm học ${requestScope.listClass.get(0).schoolYear.name}
                                                        </div>
                                                        <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                            ${requestScope.listClass.size()}
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                            Chưa có lớp học trong năm
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-graduation-cap fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-footer text-center">
                                        <a href="${pageContext.request.contextPath}/director/class" class="btn btn-sm btn-outline-success">
                                            Xem chi tiết
                                        </a>
                                    </div>
                                </div>
                            </div>

                                            
                            <!-- Số môn học đang chờ duyệt -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-warning shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <c:choose>
                                                    <c:when test="${not empty requestScope.listSubjectPending}">
                                                        <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                            Môn học đang chờ duyệt
                                                        </div>
                                                        <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                            ${requestScope.listSubjectPending.size()}
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                            Không có môn học chờ duyệt
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-book fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="card-footer text-center">
                                        <a href="${pageContext.request.contextPath}/director/reviewsubject" class="btn btn-sm btn-outline-warning">
                                            Xem chi tiết
                                        </a>
                                    </div>
                                </div>
                            </div>

                            <!-- Lớp học chờ phê duyệt -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-warning shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                    Lớp học chờ phê duyệt
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <c:choose>
                                                        <c:when test="${not empty pendingClasses}">
                                                            ${fn:length(pendingClasses)}
                                                        </c:when>
                                                        <c:otherwise>0</c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${not empty pendingClasses}">
                                        <div class="card-footer text-center">
                                            <a href="${pageContext.request.contextPath}/director/reviewclass" class="btn btn-sm btn-outline-warning">
                                                Xem chi tiết
                                            </a>
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <!-- Nhân sự chờ phê duyệt -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-info shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                                    Nhân sự chờ phê duyệt
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <c:choose>
                                                        <c:when test="${not empty waitlistpersonnel}">
                                                            ${fn:length(waitlistpersonnel)}
                                                        </c:when>
                                                        <c:otherwise>0</c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-user-clock fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${not empty waitlistpersonnel}">
                                        <div class="card-footer text-center">
                                            <a href="${pageContext.request.contextPath}/director/waitlistpersonnel" class="btn btn-sm btn-outline-info">
                                                Xem chi tiết
                                            </a>
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <!-- Học sinh chờ phê duyệt -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-warning shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                    Học sinh chờ phê duyệt
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    ${listStudent}
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-user-graduate fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${listStudent > 0}">
                                        <div class="card-footer text-center">
                                            <a href="${pageContext.request.contextPath}/director/reviewstudent" class="btn btn-sm btn-outline-warning">
                                                Xem chi tiết
                                            </a>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>

                        <!-- Bảng doanh thu -->
                        <div class="row mb-4">
                            <div class="col-12">
                                <h3 class="h3 mb-3 text-gray-800">Bảng doanh thu</h3>
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Thời gian</th>
                                            <th>Doanh thu</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${not empty revenueData}">
                                                <c:forEach var="entry" items="${revenueData}">
                                                    <tr>
                                                        <td>${entry.key}</td>
                                                        <td>${entry.value}</td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td colspan="2" class="text-center text-muted">Chưa có dữ liệu doanh thu</td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <!-- Biểu đồ doanh thu -->
                        <div class="row">
                            <div class="col-12">
                                <h3 class="h3 mb-3 text-gray-800">Biểu đồ doanh thu</h3>
                                <div style="width: 100%; max-width: 600px; height: 400px;">
                                    <canvas id="revenueChart"></canvas>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="../footer.jsp"/>
        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
            var labels = [];
            var data = [];
            <c:if test="${not empty revenueData}">
                <c:forEach var="entry" items="${revenueData}">
            labels.push("${entry.key}");
            data.push(${entry.value});
                </c:forEach>
            </c:if>

            const ctx = document.getElementById('revenueChart').getContext('2d');
            const revenueChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'Doanh thu',
                            data: data,
                            backgroundColor: 'rgba(54, 162, 235, 0.7)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                },
                options: {
                    scales: {
                        y: {beginAtZero: true}
                    }
                }
            });
        </script>
    </body>
</html>
