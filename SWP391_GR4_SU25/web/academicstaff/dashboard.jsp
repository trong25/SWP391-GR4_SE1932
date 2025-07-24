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
                    <jsp:include page="../header.jsp"/>

                    <div class="container-fluid">
                        <!-- Page Heading -->
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <h1 class="h3 mb-0 text-gray-800">Bảng điều khiển Academic Staff</h1>
                        </div>

                        <!-- Content Row -->
                        <div class="row">

                            <!-- Menu Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
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
                                </div>
                            </div>
                        </div>



                        <div class="row">
                            <!-- Biểu đồ Học sinh -->
                            <div class="col-lg-6 mb-4">
                                <div class="card shadow">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">Học sinh đang chờ xử lý</h6>
                                    </div>
                                    <div class="card-body">
                                        <canvas id="pendingStudentChart"></canvas>
                                    </div>
                                </div>
                            </div>

                            <!-- Biểu đồ Giáo viên -->
                            <div class="col-lg-6 mb-4">
                                <div class="card shadow">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-warning">Giáo viên đang chờ xử lý</h6>
                                    </div>
                                    <div class="card-body">
                                        <canvas id="pendingTeacherChart"></canvas>
                                    </div>
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
</body>
<script>
    const labels = ["Đang chờ xử lý"];

    const studentCount = ${studentPendingCount};
    const teacherCount = ${teacherPendingCount};

    new Chart(document.getElementById('pendingStudentChart'), {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Học sinh',
                    data: [studentCount],
                    backgroundColor: '#4e73df'
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: true }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    precision: 0
                }
            }
        }
    });

    new Chart(document.getElementById('pendingTeacherChart'), {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Giáo viên',
                    data: [teacherCount],
                    backgroundColor: '#f6c23e'
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: true }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    precision: 0
                }
            }
        }
    });
</script>


</html>