<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">


    <title>Trung tâm dạy thêm TaBi</title>

        <title>DashBoard</title>


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
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-0 text-gray-800">Chào mừng đến với trung tâm dạy học TaBi</h1>
                </div>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <!-- Page Heading -->
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <h1 class="h3 mb-0 text-gray-800">Chào mừng đến với TABI EDU</h1>
                        </div>


                        <!-- Content Row -->
                        <div class="row">

                            <!-- Menu Card Example -->

                            <!-- Earnings (Monthly) Card Example -->

                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-primary shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                                    Số học sinh đang theo học
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.numberOfStudent}</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-user fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Tasks Card Example -->

                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-info shadow h-100 py-2">
                                    <a href="${pageContext.request.contextPath}/director/class" >
                                        <div class="card-body">
                                            <div class="row no-gutters align-items-center">
                                                <div class="col mr-2">
                                                    <c:choose>
                                                        <c:when test="${requestScope.listClass.size() > 0}">
                                                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                                                Số lớp trong năm học ${requestScope.listClass.get(0).schoolYear.name}
                                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.listClass.size()}</div>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                                                Chưa có lớp học trong năm
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="col-auto">
                                                    <i class="fas fa-fw fa-graduation-cap text-gray-300"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>

                            </div>

                        </div>
                        <!-- Event List -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Đang chờ xử lý</h6>
                                    </div>
                                    <c:choose>
                                        <c:when test ="${not empty pendingClasses}">
                                            <a href="${pageContext.request.contextPath}/director/reviewclass" class="btn btn-primary">
                                                Đang có ${fn:length(pendingClasses)} lớp chờ duyệt 
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <p  class="alert alert-warning">Hiện không có đơn nào chờ phê duyệt</p>
                                        </c:otherwise>
                                    </c:choose>


                                </div>
                            </div>
                        </div>
                        <!-- table revenue -->
                        <h3 class="h3 mb-0 text-gray-800">Bảng doanh thu</h3>

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

                        <h3 class="h3 mb-0 text-gray-800">Biểu đồ doanh thu</h3>

                        <div style="width: 600px; height: 400px;">
                            <canvas id="revenueChart"></canvas>
                        </div>

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

                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
    </body>

</html>