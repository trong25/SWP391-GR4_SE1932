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

        <style>
            .chart-container {
                position: relative;
                height: auto;
                min-height: 450px;
                margin: 20px 0 40px 0;
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0.15rem 1.75rem 0 rgba(58, 59, 69, 0.15);
            }

            #revenueChart {
                height: 350px !important;
                margin-bottom: 20px;
            }

            .year-selector {
                text-align: center;
                margin-bottom: 20px;
            }

            .year-selector select {
                padding: 8px 15px;
                font-size: 14px;
                border: 1px solid #d1d3e2;
                border-radius: 5px;
                background: white;
                color: #5a5c69;
            }

            .revenue-stats {
                display: flex;
                justify-content: space-around;
                margin: 30px 0;
                flex-wrap: wrap;
                clear: both;
            }

            .revenue-stat-item {
                text-align: center;
                padding: 15px;
                background: #f8f9fc;
                border-radius: 8px;
                margin: 5px;
                min-width: 150px;
                border-left: 4px solid #4e73df;
            }

            .revenue-stat-value {
                font-size: 20px;
                font-weight: bold;
                color: #4e73df;
            }

            .revenue-stat-label {
                color: #858796;
                margin-top: 5px;
                font-size: 12px;
                text-transform: uppercase;
                font-weight: bold;
            }

            .chart-header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 15px 20px;
                border-radius: 10px 10px 0 0;
                margin: -20px -20px 20px -20px;
            }

            .chart-header h4 {
                margin: 0;
                font-weight: 600;
            }

            /* Đảm bảo layout đúng cho SB Admin 2 */
            #wrapper {
                display: flex;
            }

            #content-wrapper {
                background-color: #f8f9fc;
                width: 100%;
                overflow-x: hidden;
            }

            #content {
                flex: 1 0 auto;
                padding-bottom: 50px;
            }

            .container-fluid {
                padding: 1.5rem;
            }

            /* Card styles */
            .card {
                margin-bottom: 25px;
            }

            /* Responsive cho mobile */
            @media (max-width: 768px) {
                .chart-container {
                    min-height: 400px;
                    margin: 20px 0 30px 0;
                }

                .revenue-stats {
                    flex-direction: column;
                    align-items: center;
                    margin: 20px 0;
                }

                .revenue-stat-item {
                    margin: 10px 0;
                    width: 90%;
                }
            }

            /* Scroll to top button */
            .scroll-to-top {
                position: fixed;
                right: 1rem;
                bottom: 1rem;
                display: none;
                width: 2.75rem;
                height: 2.75rem;
                text-align: center;
                color: #fff;
                background: rgba(90, 92, 105, 0.5);
                line-height: 46px;
                z-index: 1000;
            }

            .scroll-to-top:focus,
            .scroll-to-top:hover {
                color: white;
            }

            .scroll-to-top:hover {
                background: #5a5c69;
            }

            .scroll-to-top.rounded {
                border-radius: 100%;
            }
        </style>
    </head>

    <body id="page-top">
        <!-- Page Wrapper -->
        <div id="wrapper">

            <!-- Sidebar/Navbar -->
            <jsp:include page="navbar.jsp"/>

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar/Header -->
                    <jsp:include page="../header.jsp"/>

                    <!-- Begin Page Content -->
                    <div class="container-fluid">

                        <!-- Page Heading -->
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <h1 class="h3 mb-0 text-gray-800">Bảng điều khiển Director</h1>
                        </div>

                        <!-- Content Row - Các thống kê -->
                        <div class="row">
                            <!-- Card Tổng doanh thu -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-success shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                    Tổng doanh thu 
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <c:choose>
                                                        <c:when test="${not empty allRevenue && allRevenue > 0}">
                                                            <fmt:formatNumber value="${allRevenue}" type="currency" currencySymbol="₫" />
                                                        </c:when>
                                                        <c:otherwise>0 ₫</c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Card Doanh thu tháng hiện tại -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-success shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                    Doanh thu tháng <fmt:formatDate value="${currentDate}" pattern="MM/yyyy" />
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <c:choose>
                                                        <c:when test="${not empty currentMonthRevenue && currentMonthRevenue > 0}">
                                                            <fmt:formatNumber value="${currentMonthRevenue}" type="currency" currencySymbol="₫" />
                                                        </c:when>
                                                        <c:otherwise>0 ₫</c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
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

                        </div>



                        <!-- Biểu đồ doanh thu -->

                        <div class="row">
                            <div class="col-xl-12">
                                <div class="chart-container">
                                    <div class="chart-header">
                                        <h4><i class="fas fa-chart-bar mr-2"></i>Biểu Đồ Doanh Thu Theo Tháng</h4>
                                    </div>
                                    <div class = "year-selector">
                                        <c:set var="selectedYear" value="${param.year != null ? param.year : '2024'}" />
                                        <select id="yearSelect" onchange="updateChart()">
                                            <option value="2023" ${selectedYear == '2024' ? 'selected' : ''}>2024</option>
                                            <option value="2024" ${selectedYear == '2025' ? 'selected' : ''}>2025</option>
                                            <option value="2025" ${selectedYear == '2026' ? 'selected' : ''}>2026</option>
                                        </select>
                                    </div>

                                    <canvas id="revenueChart"></canvas>

                                    <div class="revenue-stats">
                                        <div class="revenue-stat-item">
                                            <div class="revenue-stat-value" id="totalRevenue">0 ₫</div>
                                            <div class="revenue-stat-label">Tổng Doanh Thu</div>
                                        </div>
                                        <div class="revenue-stat-item">
                                            <div class="revenue-stat-value" id="avgRevenue">0 ₫</div>
                                            <div class="revenue-stat-label">Trung Bình/Tháng</div>
                                        </div>
                                        <div class="revenue-stat-item">
                                            <div class="revenue-stat-value" id="maxMonth">-</div>
                                            <div class="revenue-stat-label">Tháng Cao Nhất</div>
                                        </div>
                                        <div class="revenue-stat-item">
                                            <div class="revenue-stat-value" id="growthRate">0%</div>
                                            <div class="revenue-stat-label">Tăng Trưởng</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!-- /.container-fluid -->

                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <jsp:include page="../footer.jsp"/>

            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->

        <!-- Scroll to Top Button-->
        <a class="scroll-to-top rounded" href="#page-top">
            <i class="fas fa-angle-up"></i>
        </a>



        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
                                            // Dữ liệu thực từ PaymentDAO - khởi tạo 12 tháng với giá trị 0
                                            let currentYearData = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];

                                            // Lấy dữ liệu thực từ database
            <c:if test="${not empty monthlyRevenue}">
                <c:forEach var="entry" items="${monthlyRevenue}">
                                            currentYearData[${entry.key - 1}] = ${entry.value}; // Chuyển từ tháng 1-12 sang index 0-11
                </c:forEach>
            </c:if>

                                            const monthLabels = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
                                                'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'];

                                            let chart;

                                            function initChart() {
                                                const ctx = document.getElementById('revenueChart').getContext('2d');

                                                chart = new Chart(ctx, {
                                                    type: 'bar',
                                                    data: {
                                                        labels: monthLabels,
                                                        datasets: [{
                                                                label: 'Doanh Thu (VNĐ)',
                                                                data: currentYearData,
                                                                backgroundColor: [
                                                                    'rgba(78, 115, 223, 0.8)', 'rgba(28, 200, 138, 0.8)', 'rgba(54, 185, 204, 0.8)',
                                                                    'rgba(246, 194, 62, 0.8)', 'rgba(231, 74, 59, 0.8)', 'rgba(133, 135, 150, 0.8)',
                                                                    'rgba(78, 115, 223, 0.6)', 'rgba(28, 200, 138, 0.6)', 'rgba(54, 185, 204, 0.6)',
                                                                    'rgba(246, 194, 62, 0.6)', 'rgba(231, 74, 59, 0.6)', 'rgba(133, 135, 150, 0.6)'
                                                                ],
                                                                borderColor: [
                                                                    'rgba(78, 115, 223, 1)', 'rgba(28, 200, 138, 1)', 'rgba(54, 185, 204, 1)',
                                                                    'rgba(246, 194, 62, 1)', 'rgba(231, 74, 59, 1)', 'rgba(133, 135, 150, 1)',
                                                                    'rgba(78, 115, 223, 1)', 'rgba(28, 200, 138, 1)', 'rgba(54, 185, 204, 1)',
                                                                    'rgba(246, 194, 62, 1)', 'rgba(231, 74, 59, 1)', 'rgba(133, 135, 150, 1)'
                                                                ],
                                                                borderWidth: 2,
                                                                borderRadius: 5,
                                                                borderSkipped: false,
                                                            }]
                                                    },
                                                    options: {
                                                        responsive: true,
                                                        maintainAspectRatio: false,
                                                        plugins: {
                                                            legend: {
                                                                display: false
                                                            },
                                                            tooltip: {
                                                                callbacks: {
                                                                    label: function (context) {
                                                                        return 'Doanh thu: ' + formatCurrency(context.parsed.y);
                                                                    }
                                                                }
                                                            }
                                                        },
                                                        scales: {
                                                            y: {
                                                                beginAtZero: true,
                                                                ticks: {
                                                                    callback: function (value) {
                                                                        return formatCurrency(value);
                                                                    }
                                                                }
                                                            },
                                                            x: {
                                                                grid: {
                                                                    display: false
                                                                }
                                                            }
                                                        },
                                                        interaction: {
                                                            intersect: false,
                                                            mode: 'index'
                                                        }
                                                    }
                                                });

                                                updateStats(currentYearData);
                                            }

                                            function updateChart() {
                                                const selectedYear = document.getElementById('yearSelect').value;

                                                // Chuyển hướng đến trang với tham số năm để lấy dữ liệu mới từ database
                                                window.location.href = '${pageContext.request.contextPath}/director/dashboard?year=' + selectedYear;
                                            }

                                            function updateStats(data) {
                                                const total = data.reduce((sum, value) => sum + value, 0);
                                                const avg = total / data.length;
                                                const maxValue = Math.max(...data);
                                                const maxMonthIndex = data.indexOf(maxValue) + 1;

                                                // Tính tăng trưởng so với tháng trước
                                                let growthRate = 0;
                                                if (data.length >= 2) {
                                                    const lastMonth = data[data.length - 1];
                                                    const prevMonth = data[data.length - 2];
                                                    if (prevMonth > 0) {
                                                        growthRate = ((lastMonth - prevMonth) / prevMonth * 100);
                                                    }
                                                }

                                                document.getElementById('totalRevenue').textContent = formatCurrency(total);
                                                document.getElementById('avgRevenue').textContent = formatCurrency(avg);
                                                document.getElementById('maxMonth').textContent = 'Tháng ' + maxMonthIndex;
                                                document.getElementById('growthRate').textContent = growthRate.toFixed(1) + '%';
                                            }

                                            function formatCurrency(amount) {
                                                return new Intl.NumberFormat('vi-VN', {
                                                    style: 'currency',
                                                    currency: 'VND'
                                                }).format(amount);
                                            }

                                            // Khởi tạo biểu đồ khi trang load
                                            document.addEventListener('DOMContentLoaded', function () {
                                                initChart();
                                            });
        </script>
    </body>
</html>
