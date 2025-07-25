    <%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en">

        <head>

            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <meta name="description" content="">
            <meta name="author" content="">

            <title></title>

            <!-- Custom fonts for this template-->
            <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
            <link
                href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
                rel="stylesheet">

            <!-- Custom styles for this template-->
            <link href="../css/sb-admin-2.min.css" rel="stylesheet">

            <script>
                $(document).ready(function() {
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
                <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/student/dashboard">
                    <div class="sidebar-brand-icon rotate-n-15">
                        <i class="fas fa-laugh-wink"></i>
                    </div>
                    <div class="sidebar-brand-text mx-3">Tabi Learning Center</div>
                </a>

                <!-- Divider -->
                <hr class="sidebar-divider my-0">

                <!-- Nav Item -->
                <li class="nav-item">
                    <a class="nav-link" href="NotificationStudent">
                        <i class="fas fa-fw fa-solid fa-bell"></i>
                        <span>Thông Báo</span></a>
                </li>


                <!-- Nav Item -->
                <li class="nav-item">
                    <a class="nav-link" href="attendance">
                        <i class="fas fa-fw fa-calendar-check"></i>
                        <span>Điểm Danh</span></a>
                </li>

                <!-- Nav Item -->
                <li class="nav-item">
                    <a class="nav-link" href="view-timetable">
                        <i class="fas fa-fw fa-calendar"></i>
                        <span>Thời khoá biểu</span></a>
                </li>

                <li class="nav-item">
                    <a class="nav-link collapsed" href="sentapplications" data-toggle="collapse" data-target="#collapseTwo"
                       aria-expanded="true" aria-controls="collapseTwo">
                        <i class="fas fa-fw fa-cog"></i>
                        <span>Đơn Từ</span>
                    </a>
                    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <a class="collapse-item" href="sentapplications">Xem Đơn</a>
                            <a class="collapse-item" href="sendapplication">Gửi Đơn</a>
                        </div>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="payments">
                        <i class="fas fa-fw fa-money-bill-wave"></i>                    
                        <span>Thanh toán</span></a>
                </li>


                <li class="nav-item">
                    <a class="nav-link" href="viewdailyevaluationreport">
                        <i class="fas fa-fw fa-bars"></i>
                        <span>Báo cáo đánh giá trên lớp</span></a>
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
