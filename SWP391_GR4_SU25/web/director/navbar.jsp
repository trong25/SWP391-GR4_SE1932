<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Navbar</title>
    <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">
</head>

<body id="page-top">

<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <!-- Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="dashboard">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">TABI EDU</div>
    </a>

    <hr class="sidebar-divider my-0">

    <!-- Quản lý lớp học -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseClass">
            <i class="fas fa-fw fa-cog"></i>
            <span>Quản lý lớp học</span>
        </a>
        <div id="collapseClass" class="collapse">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="class">Danh Sách Lớp</a>
                <a class="collapse-item" href="reviewclass">Lớp Chờ Phê Duyệt</a>
            </div>
        </div>
    </li>

    <!-- Quản lý thời khóa biểu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTimetable">
            <i class="fas fa-fw fa-calendar-week"></i>
            <span>Quản lý thời khóa biểu</span>
        </a>
        <div id="collapseTimetable" class="collapse">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="reviewtimetable">Đang Chờ Phê Duyệt</a>
                <a class="collapse-item" href="viewtimetableclass">Xem Thời Khóa Biểu</a>
            </div>
        </div>
    </li>

    <!-- Quản lý môn học -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSubject">
            <i class="fas fa-fw fa-book"></i>
            <span>Quản lý môn học</span>
        </a>
        <div id="collapseSubject" class="collapse">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="listsubject">Danh Sách Môn Học</a>
                <a class="collapse-item" href="reviewsubject">Đang Chờ Phê Duyệt</a>
            </div>
        </div>
    </li>

<!--     Quản lý học sinh 
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePupil">
            <i class="fas fa-fw fa-user"></i>
            <span>Quản lý học sinh</span>
        </a>
        <div id="collapsePupil" class="collapse">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="listpupil">Danh Sách Học Sinh</a>
                <a class="collapse-item" href="reviewstudent">Học Sinh Cần Phê Duyệt</a>
            </div>
        </div>
    </li>-->

    <!-- Quản lý nhân sự -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePersonnel">
            <i class="fas fa-fw fa-cheese"></i>
            <span>Quản lý nhân sự</span>
        </a>
        <div id="collapsePersonnel" class="collapse">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="listpersonnel">Danh sách nhân viên</a>
                <a class="collapse-item" href="waitlistpersonnel">Phê duyệt nhân viên</a>
            </div>
        </div>
    </li>

    <!-- Tổng kết -->
    <li class="nav-item">
        <a class="nav-link" href="schoolyearsummarize">
            <i class="fas fa-fw fa-bars"></i>
            <span>Tổng kết khen thưởng học sinh</span>
        </a>
    </li>

</ul>
<!-- End of Sidebar -->

<!-- Scripts -->
<script src="../vendor/jquery/jquery.min.js"></script>
<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="../vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="../js/sb-admin-2.min.js"></script>

<!-- DataTable setup -->
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

</body>
</html>
