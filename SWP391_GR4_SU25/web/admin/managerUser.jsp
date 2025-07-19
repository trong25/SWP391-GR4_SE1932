<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Quản lý tài khoản</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
        <script>
            $(document).ready(function () {
                var toastMessage = '<%= request.getAttribute("toastMessage") %>';
                var toastType = '<%= request.getAttribute("toastType") %>';
                if (toastMessage) {
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'error') {
                        toastr.error(toastMessage);
                    }
                }
            });
        </script>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
            
            // Function to format ID - remove U and leading zeros
            function formatId(id) {
                if (id && id.startsWith('U')) {
                    var result = id.replace(/^U0+/, '');
                    return result === '' ? '0' : result;
                }
                return id;
            }
        </script>
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">

                        <h1 class="h3 mb-4 text-gray-800 text-center">QUẢN LÝ TÀI KHOẢN</h1>
                        <div class="row">
                            <div class="col-lg-2 mb-4">
                                <div>
                                    <label>VAI TRÒ</label>
                                    <select class="custom-select" name="role" id="roleSelect" onchange="redirectToServlet()">
                                        <option value="6">TẤT CẢ (VAI TRÒ)</option>
                                        <option value="0">NHÂN VIÊN IT</option>
                                        <option value="1">GIÁM ĐỐC</option>
                                        <option value="2">GIÁO VỤ</option>
                                        <option value="3">GIÁO VIÊN</option>
                                        <option value="4">HỌC SINH</option>
                                        <option value="5">KẾ TOÁN</option>
                                    </select>                                   
                                </div>
                            </div>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">DANH SÁCH TÀI KHOẢN</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">

                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                        <th>STT</th>
                                        <th>TÊN TÀI KHOẢN</th>
                                        <th>ID</th>
                                        <th>EMAIL</th>
                                        <th>VAI TRÒ</th>
                                        <th>TRẠNG THÁI</th>
                                        <th>HÀNH ĐỘNG</th>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${requestScope.list}" var="u" varStatus="status">
                                                <tr>
                                                    <td>${status.index + 1}</td>
                                                    <td>${u.getUsername()}</td>
                                                    <td>
                                                        <c:set var="displayId" value="${fn:replace(u.getId(), 'U', '')}" />
                                                        <c:set var="displayId" value="${fn:replace(displayId, '0', '')}" />
                                                        ${empty displayId ? '0' : displayId}
                                                    </td>
                                                    <td>${u.getEmail()}</td>
                                                    <td>${roleMap[u.getRoleId()]}</td>
                                                    <td>
                                                        <c:if test="${roleDis[u.getIsDisabled()] eq 'HOẠT ĐỘNG'}">
                                                        <label class="badge badge-success">${roleDis[u.getIsDisabled()]}</label>
                                                        </c:if>
                                                        <c:if test="${roleDis[u.getIsDisabled()] eq 'KHÔNG HOẠT ĐỘNG'}">
                                                        <label class="badge badge-danger">${roleDis[u.getIsDisabled()]}</label>
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <form action="edituser" method="post" style="display: inline;">
                                                            <input type="hidden" name="id" value="${u.getId()}">
                                                            <button type="submit" class="btn btn-primary btn-sm mb-1">CHỈNH SỬA</button>
                                                        </form>
                                                        <form action="resetpassword" method="post" style="display: inline;">
                                                            <input type="hidden" name="email" value="${u.getEmail()}">
                                                            <button type="submit" class="btn btn-danger btn-sm mb-1">ĐẶT LẠI MẬT KHẨU</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="d-flex justify-content-end">
                            <a href="createuser"> <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newClassModal">
                                    TẠO TÀI KHOẢN MỚI
                                </button></a>
                        </div>   
                    </div>

                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>
        <script>
            function redirectToServlet() {
                var selectedRole = document.getElementById("roleSelect").value;
                if (selectedRole !== "") {
                    window.location.href = "categoryRoleManager?role=" + selectedRole;
                }
            }
            // Function to get query parameter value
            function getQueryParam(param) {
                var urlParams = new URLSearchParams(window.location.search);
                return urlParams.get(param);
            }

            // Set the selected value on page load
            document.addEventListener('DOMContentLoaded', (event) => {
                var selectedRole = getQueryParam('role');
                if (selectedRole) {
                    document.getElementById('roleSelect').value = selectedRole;
                }
            });
        </script>
    </body>
</html>