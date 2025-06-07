<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Danh Sách Năm Học</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <script>
        $(document).ready(function() {
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
    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Năm Học</h1>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Danh Sách Năm Học</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Năm học</th>
                                    <th>Ngày bắt đầu</th>
                                    <th>Ngày kết thúc</th>
                                    <th>Người tạo</th>
                                    <th>Hành động</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="schoolYear" items="${requestScope.schoolYears}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${schoolYear.name}</td>
                                        <td><fmt:formatDate value="${schoolYear.startDate}" pattern="yyyy/MM/dd"/> </td>
                                        <td><fmt:formatDate value="${schoolYear.endDate}" pattern="yyyy/MM/dd"/> </td>
                                        <td>${schoolYear.createdBy.lastName} ${schoolYear.createdBy.firstName}</td>
                                        <td class="text-center">
                                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                                    data-target="#editSchoolYearModal${schoolYear.id}">
                                                Chỉnh Sửa
                                            </button>
                                        </td>
                                    </tr>
                                    <!-- Edit School Year Modal -->
                                    <div class="modal fade" id="editSchoolYearModal${schoolYear.id}" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                                        <form action="schoolyear?action=edit" method="POST">
                                            <input name="schoolYearId" value="${schoolYear.id}" hidden="hidden">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-body">
                                                        <div class="row">
                                                            <div class="form-group col-md-12">
                                                                <span class="thong-tin-thanh-toan">
                                                                    <h5>Chỉnh Sửa Năm Học</h5>
                                                                </span>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="form-group col-md-6">
                                                                <label class="control-label">Ngày bắt đầu</label>
                                                                <input class="form-control" type="date" id="startDateEdit" value="${schoolYear.startDate}" name="startDate" required>
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label class="control-label">Ngày kết thúc</label>
                                                                <input class="form-control" type="date" id="endDateEdit" value="${schoolYear.endDate}" name="endDate" required>
                                                            </div>
                                                            <div class="form-group col-md-12">
                                                                <label class="control-label">Mô Tả</label>
                                                                <textarea class="form-control" type="text" placeholder="Không được vượt quá 255 kí tự"
                                                                          name="description" rows="5" required maxlength="255">${schoolYear.description}</textarea>
                                                                <p style="display: none" id="charCountEdit">255 characters remaining</p>
                                                                <p style="display: none" class="alert-warning" id="warningEdit">Không được vượt quá 255 kí tự.</p>
                                                            </div>
                                                            <script>
                                                                const textarea = document.querySelector('textarea[name="description"]');
                                                                const charCount = document.getElementById('charCountEdit');
                                                                const warning = document.getElementById('warningEdit');

                                                                textarea.addEventListener('input', () => {
                                                                    const remaining = 255 - textarea.value.length;
                                                                    charCount.textContent = `${remaining} characters remaining`;

                                                                    if (remaining <= 0) {
                                                                        warning.style.display = 'block';
                                                                    } else {
                                                                        warning.style.display = 'none';
                                                                    }
                                                                });
                                                            </script>
                                                        </div>
                                                        <br>
                                                        <button class="btn btn-success" type="submit">Lưu lại</button>
                                                        <a class="btn btn-danger" data-dismiss="modal" href="#">Hủy bỏ</a>
                                                        <br>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
<%--                                    End Edit School Year--%>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                    <div class="d-flex justify-content-end">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newSchoolYearModal">
                            TẠO NĂM HỌC MỚI
                        </button>
                    </div>
                <!-- New School Year Modal -->
                <div class="modal fade" id="newSchoolYearModal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                    <form action="schoolyear?action=create" method="POST">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="form-group col-md-12">
                            <span class="thong-tin-thanh-toan">
                                <h5>Tạo Năm Học Mới</h5>
                            </span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Ngày bắt đầu</label>
                                            <input class="form-control" type="date" id="startDate" name="startDate" required>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Ngày kết thúc</label>
                                            <input class="form-control" type="date" id="endDate" name="endDate" required>
                                        </div>
                                        <div class="form-group col-md-12">
                                            <label class="control-label">Mô Tả</label>
                                            <textarea class="form-control" type="text" placeholder="Không được vượt quá 255 kí tự"
                                                      name="description" rows="5" required maxlength="255"></textarea>
                                            <p style="display: none" id="charCount">255 characters remaining</p>
                                            <p style="display: none" class="alert-warning" id="warning">Không được vượt quá 255 kí tự.</p>
                                        </div>
                                        <script>
                                            const textarea = document.querySelector('textarea[name="description"]');
                                            const charCount = document.getElementById('charCount');
                                            const warning = document.getElementById('warning');

                                            textarea.addEventListener('input', () => {
                                                const remaining = 255 - textarea.value.length;
                                                charCount.textContent = `${remaining} characters remaining`;

                                                if (remaining <= 0) {
                                                    warning.style.display = 'block';
                                                } else {
                                                    warning.style.display = 'none';
                                                }
                                            });
                                        </script>
                                    </div>
                                    <br>
                                    <button class="btn btn-success" type="submit">Lưu lại</button>
                                    <a class="btn btn-danger" data-dismiss="modal" href="#">Hủy bỏ</a>
                                    <br>
                                </div>
                            </div>
                        </div>
                    </form>
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
</body>
</html>