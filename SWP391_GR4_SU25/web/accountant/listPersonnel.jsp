<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Quản lý Bảng Lương</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script>
            $(document).ready(function () {
                var toastMessage = '<%= request.getAttribute("message") %>';
                var toastType = '<%= request.getAttribute("type") %>';
                if (toastMessage) {
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'fail') {
                        toastr.error(toastMessage);
                        $('#newPersonnelModal').modal('show'); // Show the modal if the toast type is fail
                    }
                }
            });
        </script>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Bảng Lương Của Nhân Viên</h1>
                        <div class="row">
                            <c:set var="sltedrole" value="${requestScope.selectedrole}"/>
                            <c:set var="sltedstatus" value="${requestScope.selectedstatus}"/>
                            <c:set var="vietnamesePattern"
                                   value="ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ"/>
                            <div class="col-lg-4">
                                <form action="listpersonnell" method="post">
                                    <div style="display: flex; justify-content: space-between">
                                        <div class="class-form">

                                            <label> Chức vụ
                                                <select name="role" onchange="this.form.submit()" class="custom-select">
                                                    <option value="" hidden>Chức vụ</option>
                                                    <c:forEach items="${requestScope.roles}" var="r">
                                                        <option ${sltedrole eq r.getId() ? "selected" : ""}
                                                            value="${r.getId()}">${r.getVNeseDescription()}</option>
                                                    </c:forEach>
                                                    <c:if test="${sltedrole eq 'all'}">
                                                        <option value="all" selected>Hiện toàn bộ chức vụ</option>
                                                    </c:if>
                                                    <c:if test="${sltedrole ne'all'}">
                                                        <option value="all">Hiện toàn bộ chức vụ</option>
                                                    </c:if>
                                                </select>
                                            </label>
                                        </div>
                                        <div class="class-form">

                                            <label>Trạng thái
                                                <select name="status" onchange="this.form.submit()" class="custom-select">
                                                    <option value="" hidden>Trạng thái</option>
                                                    <c:forEach items="${requestScope.statuss}" var="r">
                                                        <option ${sltedstatus eq r ? "selected" : ""} value="${r}">${r}</option>
                                                    </c:forEach>
                                                    <c:if test="${sltedstatus eq 'all'}">
                                                        <option value="all" selected>Hiện toàn bộ trạng thái</option>
                                                    </c:if>
                                                    <c:if test="${sltedstatus ne'all'}">
                                                        <option value="all">Hiện toàn bộ trạng thái</option>
                                                    </c:if>
                                                </select>
                                            </label>
                                        </div>

                                    </div>


                                </form>
                                <style>
                                    .btn {
                                        margin: 0px 0px 0px 10px;
                                    }
                                </style>


                            </div>


                        </div>
                        <style>
                            /* Hide the placeholder in the dropdown options */
                            option[hidden] {
                                display: none;
                            }
                        </style>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Bảng Lương Của Nhân Viên</h6>
                            </div>                      
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Ảnh</th>
                                                <th>Mã Nhân Viên</th>
                                                <th>Tên</th>
                                                <th>Giới Tính</th>
                                                <th>Ngày sinh</th>
                                                <th>Chức vụ</th>
                                                <th>Năm Kinh Nghiệm</th>
                                                <th>Trình Độ</th>
                                                <th>Lương cơ bản</th>
                                                <th>Tổng lương</th>
                                                <th>Trạng thái</th>
                                                <th>Chi tiết</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <div style="color: red">${requestScope.error}</div>
                                        <c:forEach items="${persons}" var="p" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>
                                                <td><img class="profile_img" src="../images/${p.getAvatar()}" alt="ảnh nhân viên" width="191px"
                                                         height="263px" object-fit: cover></td>
                                                <td>${p.getId()}</td>
                                                <td>${p.getLastName()} ${p.getFirstName()}</td>
                                                <td>
                                                    <c:if test="${p.getGender()==true}">
                                                        Nam
                                                    </c:if>
                                                    <c:if test="${p.getGender()==false}">
                                                        Nữ
                                                    </c:if>
                                                </td>
                                                <td><fmt:formatDate value="${p.getBirthday()}" pattern="yyyy/MM/dd"/> </td>
                                                <td>
                                                    <c:if test="${p.getRoleId()== 0}">
                                                        Nhân viên IT
                                                    </c:if>

                                                    <c:if test="${p.getRoleId()==2}">
                                                        Giáo vụ
                                                    </c:if>
                                                    <c:if test="${p.getRoleId()==3}">
                                                        Giáo viên
                                                    </c:if>
                                                    <c:if test="${p.getRoleId()==5}">
                                                        Nhân viên kế toán
                                                    </c:if>

                                                </td>
                                                <td>${p.getTeaching_years()}</td>
                                                <td>${p.getQualification()}</td>
                                                <td><fmt:formatNumber value="${p.getBaseSalary()}" type="number" groupingUsed="true"/> VND</td>
                                                <td><fmt:formatNumber value="${p.getTotalSalary()}" type="number" groupingUsed="true"/> VND</td>

                                                <c:if test="${p.getStatus() != null}">
                                                    <c:choose>
                                                        <c:when test="${p.getStatus() == 'đang làm việc'}">
                                                            <td >
                                                                <span class="badge badge-success">${p.getStatus()}</span>
                                                            </td>
                                                        </c:when>
                                                        <c:when test="${p.getStatus() == 'đang chờ xử lý'}">
                                                            <td>
                                                                <span class="badge badge-warning">${p.getStatus()}</span>
                                                            </td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td >
                                                                <span class="badge badge-info">${p.getStatus()}</span>
                                                            </td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                                <td>
                                                    <a href="viewpersonnel?id=${p.getId()}"
                                                       class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Thanh Toán
                                                        Lương</a>


                                                </td>
                                            </tr>
                                        </c:forEach>

                                        </tbody>
                                    </table>

                                    <!--                                        <p>Đây là nội dung JSP thuần, không cần JSTL.</p>-->

                                </div>
                            </div>

                        </div>
                    </div>

                </div>
                <jsp:include page="../footer.jsp"/>
            </div>

        </div>
        <script src="js/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        <script src="js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <!--===============================================================================================-->
        <script src="js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="js/main.js"></script>
        <!--===============================================================================================-->
        <script src="js/plugins/pace.min.js"></script>
        <!--===============================================================================================-->
        <!--===============================================================================================-->

        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>

    </body>
</html>
