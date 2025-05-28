<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <link rel="shortcut icon" type="image/x-icon" href="../image/logo.png" />
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Profile</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">

        <script>
            $(document).ready(function () {
                $('#dataTable').DataTable({
                    language: {
                        sProcessing: "Đang xử lý...",
                        sLengthMenu: "Xem _MENU_ mục",
                        sZeroRecords: "Không tìm thấy dòng nào phù hợp",
                        sInfo: "Đang xem _START_ đến _END_ trong tổng số _TOTAL_ mục",
                        sInfoEmpty: "Đang xem 0 đến 0 trong tổng số 0 mục",
                        sInfoFiltered: "(được lọc từ _MAX_ mục)",
                        sInfoPostFix: "",
                        sSearch: "Tìm:",
                        sUrl: "",
                        sEmptyTable: "Không có dữ liệu trong bảng",
                        sLoadingRecords: "Đang tải...",
                        sInfoThousands: ",",
                        oPaginate: {
                            sFirst: "Đầu",
                            sLast: "Cuối",
                            sNext: "Tiếp",
                            sPrevious: "Trước"
                        },
                        oAria: {
                            sSortAscending: ": Sắp xếp thứ tự tăng dần",
                            sSortDescending: ": Sắp xếp thứ tự giảm dần"
                        }
                    }
                });
            });
        </script>

    </head>

    <body id="page-top">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/teacher/dashboard">
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-laugh-wink"></i>
                </div>
                <div class="sidebar-brand-text mx-3">TaBi Learning Center</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">
            <!-- Nav Item - Timekeeping -->
            <li class="nav-item">
                <a class="nav-link" href="mytimekeeping">
                    <i class="fas fa-fw fa-clock"></i>
                    <span>Chấm Công Của Tôi</span>
                </a>
            </li>
            <!-- Nav Item - Thời khóa biểu -->
            <li class="nav-item">
                <a class="nav-link" href="view-timetable"> 
                    <i class='fas fa-fw fa-calendar-check'></i>
                    <span>Thời khóa biểu</span>

                </a>
            </li>

<!--             Nav Item - Thực đơn 
            <li class="nav-item">
                <a class="nav-link" href="viewmealtimetable">
                    <i class='fas fa-fw fa-cheese'></i>
                    <span>Thực đơn</span>
                </a>
            </li>-->

            <!-- Nav Item - Điểm danh -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseAttendance"
                   aria-expanded="true" aria-controls="collapseTwo">
                    <i class="fas fa-fw fa-solid fa-bell"></i>
                    <span>Điểm Danh</span>
                </a>
                <div id="collapseAttendance" class="collapse" aria-labelledby="headingNoti" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="takeattendance">Điểm Danh Học Sinh</a>
                        <a class="collapse-item" href="studentsattendance">Xem Điểm Danh Học Sinh</a>
                    </div>
                </div>
            </li>

            <!-- Nav Item - Quản lý học sinh -->
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/teacher/liststudent">
                    <i class='fas fa-fw fa-solid fa-graduation-cap'></i>
                    <span>Quản lý học sinh</span>
                </a>
            </li>

            <!-- Nav Item - Thông báo -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseNoti"
                   aria-expanded="true" aria-controls="collapseTwo">
                    <i class="fas fa-fw fa-solid fa-bell"></i>
                    <span>Thông Báo</span>
                </a>
                <div id="collapseNoti" class="collapse" aria-labelledby="headingNoti" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="listnotification">Danh Sách Thông Báo</a>
                        <a class="collapse-item" href="createnotifi">Tạo Thông Báo</a>
                        <a class="collapse-item" href="listsentnotifi">Thông Báo Đã Gửi</a>
                    </div>
                </div>
            </li>

<!--             Nav Item - Báo cáo sức khỏe -->
<!--            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/teacher/list-student">
                    <i class='fas fa-fw fa-hand-holding-heart'></i>
                    <span>Báo cáo sức khỏe</span>
                </a>
            </li>-->

<!--             Nav Item - Phiếu bé ngoan 
            <li class="nav-item">
                <a class="nav-link" href="evaluationreport">
                    <i class='fas fa-fw fa-id-badge'></i>
                    <span>Phiếu bé ngoan</span>
                </a>
            </li>-->

            <!-- Nav Item - Đánh giá học sinh hằng ngày -->
<!--            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseEvaluate"
                   aria-expanded="true" aria-controls="collapseTwo">
                    <i class="fas fa-fw fa-solid fa-bell"></i>
                    <span>Đánh giá học sinh</span>
                </a>
                <div id="collapseEvaluate" class="collapse" aria-labelledby="headingEvaluate" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="evaluate"><span>Đánh giá học sinh <br> hằng ngày</span></a>
                        <a class="collapse-item" href="schoolyearsummarize">Tổng kết năm học</a>
                    </div>
                </div>
            </li>-->

            <li class="nav-item">
                <a class="nav-link" href="application">
                    <i class="fas fa-fw fa-envelope" aria-hidden="true"></i>
                    <span>Xử Lý Đơn Từ</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="listevent">
                    <i class="fas fa-fw fa-calendar-check" aria-hidden="true"></i>
                    <span>Danh Sách Sự Kiện</span></a>
            </li>


            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        <!-- End of Sidebar -->


        <!-- Bootstrap core JavaScript-->
        <script src="../vendor/jquery/jquery.min.js"></script>
        <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="../js/sb-admin-2.min.js"></script>

        <!-- Page level plugins -->
        <script src="../vendor/chart.js/Chart.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/chart-area-demo.js"></script>
        <script src="../js/demo/chart-pie-demo.js"></script>

    </body>

</html>
