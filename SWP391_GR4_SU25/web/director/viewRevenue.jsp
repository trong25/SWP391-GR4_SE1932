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
        <title>Doanh Thu</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,300,400,600,700,800,900" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <style>
            .chart-container {
                position: relative;
                height: 400px;
                margin: 20px 0;
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0.15rem 1.75rem 0 rgba(58, 59, 69, 0.15);
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
                margin-top: 20px;
                flex-wrap: wrap;
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
        </style>
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">

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
                                            <option value="2023" ${selectedYear == '2023' ? 'selected' : ''}>2023</option>
                                            <option value="2024" ${selectedYear == '2024' ? 'selected' : ''}>2024</option>
                                            <option value="2025" ${selectedYear == '2025' ? 'selected' : ''}>2025</option>
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
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="../vendor/jquery/jquery.min.js"></script>
        <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="../js/sb-admin-2.min.js"></script>

        <!-- Chart.js -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>

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
                                                window.location.href = '${pageContext.request.contextPath}/director/revenue?year=' + selectedYear;
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