<%-- 
    Document   : class
    Created on : Jun 12, 2025, 11:43:07 AM
    Author     : MSI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Danh Sách Lớp Học</title>
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

                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Lớp Học</h1>
                        <div class="row">
                            <div class="col-lg-6 mb-4">
                                <form action="class"  id="myForm">
                                    <div>
                                        <label >Chọn năm học</label>
                                        <select class="custom-select" style="width: 25%"  aria-label="Default select example" onchange="submitForm()" name="schoolYearId">
                                            <c:forEach items="${requestScope.schoolYears}" var="year">
                                                <option ${requestScope.selectedSchoolYear.id eq year.id ? "selected" : ""} value="${year.id}">${year.name}</option>
                                            </c:forEach>
                                        </select>
                                        <label for="selectStatus">Chọn trạng thái</label>
                                        <select style="width: 25%" class="custom-select" id="selectStatus" aria-label="Default select example" onchange="submitForm()" name="status">
                                            <option ${requestScope.status eq 'all'? "selected" :""} value="all">Tất cả</option>
                                            <option  ${requestScope.status eq 'đang chờ xử lý'? "selected" :""} value="đang chờ xử lý">Đang chờ xử lý</option>
                                            <option ${requestScope.status eq 'đã được duyệt'? "selected" :""}  value="đã được duyệt">Đã được duyệt</option>
                                            <option  ${requestScope.status eq 'không được duyệt'? "selected" :""} value="không được duyệt">Không được duyệt</option>
                                        </select>
                                    </div>
                                </form>
                            </div>
                        </div>


                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Lớp Học</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Tên Lớp</th>
                                                <th>Khối</th>
                                                <th>Giáo viên</th>
                                                <th>Trạng thái</th>
                                                <th>Loại Lớp</th>
                                                <th>Thông Tin Lớp</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <div style="color: red">${requestScope.error}</div>
                                        <c:forEach var="classes" items="${requestScope.classes}" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>
                                                <td>${classes.name}</td>
                                                <td>${classes.grade.name}</td>
                                                <td>
                                                    <c:if test="${classes.teacher.id == null}">
                                                        Chưa được sắp xếp
                                                    </c:if>
                                                    <c:if test="${classes.teacher != null}">
                                                        ${classes.teacher.lastName} ${classes.teacher.firstName}
                                                    </c:if>
                                                </td>
                                                <c:set value="${classes.status}" var="s"/>
                                                <c:if test="${s eq 'đã được duyệt'}">
                                                    <td><span class="badge badge-success">${s}</span></td>
                                                    </c:if>
                                                    <c:if test="${s eq 'đang chờ xử lý'}">
                                                    <td><span class="badge badge-warning">${s}</span>  </td>
                                                </c:if>
                                                <c:if test="${s eq 'không được duyệt'}">
                                                    <td><span class="badge badge-danger">${s}</span>  </td>
                                                </c:if>
                                                    <td>${classes.classType}</td>
                                                <td class="text-center"><a href="classdetail?classId=${classes.id}"
                                                                           class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Thông
                                                        tin chi tiết</a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="d-flex justify-content-end">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newClassModal">
                                TẠO LỚP MỚI
                            </button>
                        </div>

                        <!-- New School Year Modal -->
                        <div class="modal fade" id="newClassModal" tabindex="-1" role="dialog" aria-hidden="true"
                             data-backdrop="static" data-keyboard="false">
                            <form action="class?action=create" method="POST">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="form-group col-md-12">
                                                    <span class="thong-tin-thanh-toan">
                                                        <h5>Tạo Lớp Mới</h5>
                                                    </span>
                                                </div>
                                            </div>
                                            <c:set var="vietnamesePattern"
                                                   value="ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ"/>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Tên Lớp<a
                                                            style="color: red">(*)</a></label>
                                                    <input class="form-control" type="text" placeholder="Tối đa 50 kí tự"
                                                           name="name" pattern="^[a-zA-Z0-9${vietnamesePattern}\s]{1,50}$"
                                                           title="Tên không được chứa kí tự đặc biệt (Tối đa 50 kí tự)"
                                                           required>
                                                    <p style="display: none" id="nameWarning" class="alert-warning">Tên không
                                                        được chứa kí tự đặc biệt và tối đa 50 kí tự.</p>
                                                </div>
                                                <script>
                                                    const nameInput = document.querySelector('input[name="name"]');
                                                    const nameWarning = document.getElementById('nameWarning');

                                                    nameInput.addEventListener('input', () => {
                                                        if (nameInput.validity.patternMismatch) {
                                                            nameWarning.style.display = 'block';
                                                        } else {
                                                            nameWarning.style.display = 'none';
                                                        }
                                                    });
                                                </script>
                                                <div class="form-group col-md-6">
                                                    <label for="teacherSelect">Giáo viên</label>
                                                    <select class="form-control" id="teacherSelect" name="teacher">
                                                        <option value="">-- Chọn Giáo Viên --</option>
                                                        <c:forEach var="teacher" items="${requestScope.teachers}">
                                                            <option value="${teacher.id}">${teacher.lastName} ${teacher.firstName} - ${teacher.schoolName} - ${teacher.addressSchool} </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="gradeSelect">Khối<a
                                                            style="color: red">(*)</a></label>
                                                    <select class="form-control" id="gradeSelect" name="grade" required>
                                                        <option value="">-- Chọn Khối --</option>
                                                        <c:forEach var="grade" items="${requestScope.grades}">
                                                            <option value="${grade.id}">${grade.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <!-- Dropdown 2: Chọn loại lớp học -->
                                                    <label for="classTypeSelect">Loại Lớp<a style="color: red">(*)</a></label>
                                                    <select class="form-control" id="classTypeSelect" name="classType" required>
                                                        <option value="">-- Loại Lớp --</option>
                                                        <option value="Cơ bản">Cơ bản</option>
                                                        <option value="Nâng Cao">Nâng cao</option>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Năm học</label>
                                                    <input class="form-control" type="text"
                                                           value="${requestScope.selectedSchoolYear.name}" readonly>
                                                    <input name="schoolYear" value="${requestScope.selectedSchoolYear.id}"
                                                           hidden>
                                                </div>
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