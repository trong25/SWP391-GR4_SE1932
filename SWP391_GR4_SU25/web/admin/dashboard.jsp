<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Admin Dashboard - Trung tâm dạy thêm TaBi</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,80...900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <style>
            html, body {
                height: 100%;
            }
            #content-wrapper {
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            #content {
                flex: 1;
            }

            footer {
                margin-top: auto;
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
                            <h1 class="h3 mb-0 text-gray-800">Bảng điều khiển Admin</h1>
                        </div>

                        <!-- Content Row -->
                        <div class="row">

                            <!-- Chấm công hôm nay Card -->
                            <div class="col-xl-4 col-md-6 mb-4">
                                <div class="card border-left-${requestScope.attendance.status eq "present"?"success":"danger"} shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-${requestScope.attendance.status eq "present"?"success":"danger"} text-uppercase mb-1">
                                                    Chấm công hôm nay
                                                </div>
<div class="row no-gutters align-items-center">
                                                    <div class="col-auto">
                                                        <c:if test="${requestScope.attendance!=null}">
                                                            <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">${requestScope.attendance.status eq 'present'?"Có mặt":"Vắng" }</div>
                                                        </c:if>
                                                        <c:if test="${requestScope.attendance==null}">
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

                            <!-- Tổng số tài khoản Card -->
                            <div class="col-xl-4 col-md-6 mb-4">
                                <div class="card border-left-primary shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                                    Tổng số tài khoản
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.totalAccounts}</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-users fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Tài khoản bị khóa Card -->
                            <div class="col-xl-4 col-md-6 mb-4">
                                <div class="card border-left-warning shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
<div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                    Tài khoản bị khóa/vô hiệu hóa
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.blockedAccounts}</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-user-lock fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Content Row - Charts -->
                            <div class="row">

                                <!-- Biểu đồ cột: Nhân sự theo vai trò -->
                                <div class="col-xl-6 col-lg-6 mb-4">
                                    <div class="card shadow h-100">
                                        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                            <h6 class="m-0 font-weight-bold text-primary">Nhân sự theo vai trò</h6>
                                            <div class="dropdown no-arrow">
                                                <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                    <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="card-body" style="height: 400px;">
                                            <div class="chart-bar h-100">
                                                <canvas id="barChart" width="100%" height="100%"></canvas>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Biểu đồ tròn: Nhân sự theo trạng thái -->
                                <div class="col-xl-6 col-lg-6 mb-4">
                                    <div class="card shadow h-100">
                                        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                            <h6 class="m-0 font-weight-bold text-success">Nhân sự theo trạng thái</h6>
<div class="dropdown no-arrow">
                                                <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                    <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="card-body d-flex flex-column justify-content-center" style="height: 400px;">
                                            <div class="chart-pie h-75 d-flex justify-content-center align-items-center">
                                                <canvas id="pieChart" width="100%" height="100%"></canvas>
                                            </div>
                                            <div class="mt-3 text-center small">
                                                <span class="mr-3">
                                                    <i class="fas fa-circle text-success"></i> Đang làm việc
                                                </span>
                                                <span class="mr-3">
                                                    <i class="fas fa-circle text-warning"></i> Đang chờ xử lý
                                                </span>
                                                <span class="mr-3">
                                                    <i class="fas fa-circle text-danger"></i> Không Được Duyệt
                                                </span>
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
                


            <!-- Page level plugins -->
            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

            <script>
                // Dữ liệu từ JSP
                const roleLabels = [<c:forEach var="entry" items="${countByRole}">"${entry.key}",</c:forEach>];
                const roleData = [<c:forEach var="entry" items="${countByRole}">${entry.value},</c:forEach>];

                const statusLabels = [<c:forEach var="entry" items="${countByStatus}">"${entry.key}",</c:forEach>];
                const statusData = [<c:forEach var="entry" items="${countByStatus}">${entry.value},</c:forEach>];

                // Map màu theo trạng thái (dạng thường, không dấu)
                const statusColorMap = {
                    "đang làm việc": "#1cc88a",
"đang chờ xử lý": "#f6c23e",
                    "không được duyệt": "#e74a3b"
                };

                // Chuẩn hóa nhãn trạng thái (để khớp với key trong map)
                const normalizeLabel = label => label.trim().toLowerCase();

                // Sinh mảng màu đúng thứ tự label từ dữ liệu backend
                const backgroundColors = statusLabels.map(label => {
                    const normalized = normalizeLabel(label);
                    return statusColorMap[normalized] || "#cccccc"; // màu xám nếu không khớp
                });

                // ==== BIỂU ĐỒ CỘT - Nhân sự theo vai trò ====
                new Chart(document.getElementById("barChart"), {
                    type: 'bar',
                    data: {
                        labels: roleLabels,
                        datasets: [{
                                label: 'Số lượng nhân sự',
                                data: roleData,
                                backgroundColor: [
                                    '#4e73df',
                                    '#1cc88a',
                                    '#36b9cc',
                                    '#f6c23e',
                                    '#e74a3b'
                                ],
                                borderColor: [
                                    '#4e73df',
                                    '#1cc88a',
                                    '#36b9cc',
                                    '#f6c23e',
                                    '#e74a3b'
                                ],
                                borderWidth: 1
                            }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                display: false
                            }
                        },
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    stepSize: 1
                                }
                            }
                        }
                    }
                });

                // ==== BIỂU ĐỒ TRÒN - Nhân sự theo trạng thái ====
                new Chart(document.getElementById("pieChart"), {
                    type: 'doughnut',
                    data: {
                        labels: statusLabels,
                        datasets: [{
                                data: statusData,
                                backgroundColor: backgroundColors,
                                borderColor: backgroundColors,
                                borderWidth: 2
                            }]
                    },
                    options: {
responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                display: false
                            }
                        },
                        cutout: '60%'
                    }
                });
            </script>


    </body>

</html>