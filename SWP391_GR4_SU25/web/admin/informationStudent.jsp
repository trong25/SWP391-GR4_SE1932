<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Thông tin học sinh - ${student.lastName} ${student.firstName}</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="../css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

        <!-- Scripts -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>

        <style>
            .student-info-container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
            }

            .student-avatar {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                object-fit: cover;
                border: 4px solid #007bff;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }

            .info-grid {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 20px;
                margin-top: 20px;
            }

            .info-item {
                background: #f8f9fa;
                padding: 15px;
                border-radius: 8px;
                border-left: 4px solid #007bff;
            }

            .info-item.full-width {
                grid-column: span 2;
            }

            .info-label {
                font-weight: 600;
                color: #495057;
                margin-bottom: 5px;
                font-size: 14px;
            }

            .info-value {
                color: #212529;
                font-size: 16px;
                word-wrap: break-word;
            }

            .info-value.empty {
                color: #6c757d;
                font-style: italic;
            }

            .back-button {
                background: linear-gradient(45deg, #dc3545, #c82333);
                color: white;
                border: none;
                padding: 12px 24px;
                border-radius: 25px;
                font-size: 16px;
                cursor: pointer;
                transition: all 0.3s ease;
                box-shadow: 0 4px 15px rgba(220, 53, 69, 0.3);
            }

            .back-button:hover {
                transform: translateY(-2px);
                box-shadow: 0 6px 20px rgba(220, 53, 69, 0.4);
            }

            .profile-header {
                text-align: center;
                margin-bottom: 30px;
            }

            .student-name {
                font-size: 24px;
                font-weight: 700;
                color: #212529;
                margin-top: 15px;
            }

            .student-id {
                color: #6c757d;
                font-size: 14px;
            }

            @media (max-width: 768px) {
                .info-grid {
                    grid-template-columns: 1fr;
                }

                .info-item.full-width {
                    grid-column: span 1;
                }
            }
        </style>

        <script>
            $(document).ready(function () {
                var toastMessage = '<%= request.getAttribute("toastMessage") %>';
                var toastType = '<%= request.getAttribute("toastType") %>';

                if (toastMessage && toastMessage !== 'null') {
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'error') {
                        toastr.error(toastMessage);
                    }
                }
            });

            function goBack() {
                window.history.back();
            }
        </script>
        <script>
            function confirmAccept(formId, msg) {
                formIdToSubmit = formId;
                document.getElementById('confirmationMessage').innerText = msg;
                $('#confirmationModal').modal('show');
            }
            $(document).ready(function () {
                $('#confirmButton').click(function () {
                    document.getElementById(formIdToSubmit).submit();

                });
            });
        </script>
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>

            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>

                    <div class="student-info-container">
                        <!-- Header Card -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h3 class="m-0 font-weight-bold text-primary">
                                    <i class="fa fa-user"></i> THÔNG TIN HỌC SINH
                                </h3>
                            </div>
                        </div>

                        <!-- Main Content -->
                        <div class="row">
                            <!-- Left Column - Avatar -->
                            <div class="col-lg-4 col-md-12">
                                <div class="card shadow h-100">
                                    <div class="card-body">
                                        <div class="profile-header">
                                            <img class="student-avatar" 
                                                 src="../images/${student.avatar}" 
                                                 alt="Avatar của ${student.lastName} ${student.firstName}"
                                                 onerror="this.src='../images/default-avatar.png'">
                                            <h5 class="student-name">${student.lastName} ${student.firstName}</h5>
                                            <p class="student-id">ID: ${student.id}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Right Column - Information -->
                            <div class="col-lg-8 col-md-12">
                                <div class="card shadow h-100">
                                    <div class="card-body">
                                        <div class="info-grid">
                                            <!-- Basic Information -->
                                            <div class="info-item">
                                                <div class="info-label">Mã Người Dùng</div>
                                                <div class="info-value ${student.userId == null ? 'empty' : ''}">
                                                    ${student.userId != null ? student.userId : 'Chưa có tài khoản'}
                                                </div>
                                            </div>
                                            <div class="info-item">
                                                <div class="info-label">Ngày Sinh</div>
                                                <div class="info-value">
                                                    <c:choose>
                                                        <c:when test="${student.birthday != null}">
                                                            ${student.birthday}
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="empty">Chưa cập nhật</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>



                                            <!-- Guardian 1 Information -->
                                            <div class="info-item">
                                                <div class="info-label">Người Giám Hộ Thứ Nhất</div>
                                                <div class="info-value ${empty student.firstGuardianName ? 'empty' : ''}">
                                                    ${empty student.firstGuardianName ? 'Chưa cập nhật' : student.firstGuardianName}
                                                </div>
                                            </div>

                                            <div class="info-item">
                                                <div class="info-label">SĐT Người Giám Hộ Thứ Nhất</div>
                                                <div class="info-value ${empty student.firstGuardianPhoneNumber ? 'empty' : ''}">
                                                    ${empty student.firstGuardianPhoneNumber ? 'Chưa cập nhật' : student.firstGuardianPhoneNumber}
                                                </div>
                                            </div>

                                            <!-- Guardian 2 Information -->
                                            <div class="info-item">
                                                <div class="info-label">Người Giám Hộ Thứ Hai</div>
                                                <div class="info-value ${empty student.secondGuardianName ? 'empty' : ''}">
                                                    ${empty student.secondGuardianName ? 'Chưa cập nhật' : student.secondGuardianName}
                                                </div>
                                            </div>

                                            <div class="info-item">
                                                <div class="info-label">SĐT Người Giám Hộ Thứ Hai</div>
                                                <div class="info-value ${empty student.secondGuardianPhoneNumber ? 'empty' : ''}">
                                                    ${empty student.secondGuardianPhoneNumber ? 'Chưa cập nhật' : student.secondGuardianPhoneNumber}
                                                </div>
                                            </div>

                                            <!-- Contact Information -->
                                            <div class="info-item">
                                                <div class="info-label">Email</div>
                                                <div class="info-value ${empty student.email ? 'empty' : ''}">
                                                    ${empty student.email ? 'Chưa cập nhật' : student.email}
                                                </div>
                                            </div>


                                            <!-- Address - Full Width -->
                                            <div class="info-item full-width">
                                                <div class="info-label">Địa Chỉ</div>
                                                <div class="info-value ${empty student.address ? 'empty' : ''}">
                                                    ${empty student.address ? 'Chưa cập nhật' : student.address}
                                                </div>
                                            </div>

                                            <!-- Special Notes - Full Width -->
                                            <div class="info-item full-width">
                                                <div class="info-label">Ghi Chú Đặc Biệt</div>
                                                <div class="info-value ${empty student.parentSpecialNote ? 'empty' : ''}">
                                                    ${empty student.parentSpecialNote ? 'Không có ghi chú' : student.parentSpecialNote}
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row text-center my-3">
                                            <c:if test="${student.status == 'đang chờ xử lý'}">
                                                <div class="col-lg-4">
                                                    <form method="post" action="studentsprofile">
                                                        <input type="hidden" name="action" value="accept"/>
                                                        <input type="hidden" name="id" value="${student.id}"/>
                                                        <button type="submit" class="btn btn-success w-100">Chấp nhận</button>
                                                    </form>
                                                </div>
                                                <div class="col-lg-4">
                                                    <form method="post" action="studentsprofile">
                                                        <input type="hidden" name="action" value="decline"/>
                                                        <input type="hidden" name="id" value="${student.id}"/>
                                                        <button type="submit" class="btn btn-danger w-100">Từ chối</button>
                                                    </form>
                                                </div>
                                            </c:if>

                                            <c:if test="${student.status == 'đang theo học'}">
                                                <div class="col-lg-4">
                                                    <form method="post" action="studentsprofile">
                                                        <input type="hidden" name="action" value="dropout"/>
                                                        <input type="hidden" name="id" value="${student.id}"/>
                                                        <button type="submit" class="btn btn-warning w-100">Nghỉ học</button>
                                                    </form>
                                                </div>
                                            </c:if>

                                            <c:if test="${student.status == 'đã nghỉ học'}">
                                                <div class="col-lg-4">
                                                    <form method="post" action="studentsprofile">
                                                        <input type="hidden" name="action" value="accept"/>
                                                        <input type="hidden" name="id" value="${student.id}"/>
                                                        <button type="submit" class="btn btn-success w-100">Đi học lại</button>
                                                    </form>
                                                </div>
                                            </c:if>

                                            <c:if test="${student.status == 'không được duyệt'}">
                                                <div class="col-lg-4">
                                                    <form method="post" action="studentsprofile">
                                                        <input type="hidden" name="action" value="accept"/>
                                                        <input type="hidden" name="id" value="${student.id}"/>
                                                        <button type="submit" class="btn btn-success w-100">Chấp nhận lại</button>
                                                    </form>
                                                </div>
                                            </c:if>

                                            <!-- Nút quay lại -->
                                            <div class="col-lg-4">
                                                <a href="listpupil" class="btn btn-info w-100">Quay lại</a>
                                            </div>
                                        </div>


                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%-- Begin confirmation modal--%>
                    <div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="confirmationModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="confirmationModalLabel">Xác nhận thao tác</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body" id="confirmationMessage">
                                    <!-- Dynamic message will be inserted here -->
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                    <button type="button" class="btn btn-primary" id="confirmButton">Xác Nhận</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
    </body>
</html>