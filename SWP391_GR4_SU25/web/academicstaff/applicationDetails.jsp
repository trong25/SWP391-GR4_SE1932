<%@ page import="model.application.Application" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="bean" class="model.student.StudentDAO"/>
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
<%--confirm message of processing application--%>
<script>
    function confirmAccept(formId, msg, action) {
        document.getElementById('actionField').value = action;
        formIdToAccept = formId;
        document.getElementById('confirmMessageTitle').innerText = msg;
        $('#confirmMessage').modal('show');
    }
    $(document).ready(function() {
        $('#confirmMessageButton').click(function() {
            document.getElementById(formIdToAccept).submit();

        });
    });
</script>
<html>
<head>
    <title>Chi Tiết Đơn Từ</title>
</head>
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Chi Tiết Đơn Từ</h1>
                <div class="card mb-4">
                    <div class="card-body">
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Loại đơn:</div>
                            <div class="col-sm-9" id="type">${requestScope.application.type.name}</div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Người gửi:</div>
                            <div class="col-sm-9" id="createdBy">
                                ${bean.getStudentByUserId(requestScope.application.createdBy).id} -
                                ${bean.getStudentByUserId(requestScope.application.createdBy).lastName} ${bean.getStudentByUserId(requestScope.application.createdBy).firstName}
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Thời gian gửi:</div>
                            <div class="col-sm-9" id="createdAt">${requestScope.application.createdAt}</div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Trạng thái đơn:</div>
                            <c:set value="${requestScope.application.status}" var="s"/>
                            <c:if test="${s eq 'đã được duyệt'}">
                                <div class="col-sm-9 text-success" id="status">${s}</div>
                            </c:if>
                            <c:if test="${s eq 'đang chờ xử lý'}">
                                <div class="col-sm-9 text-warning" id="status">${s}</div>
                            </c:if>
                            <c:if test="${s eq 'không được duyệt'}">
                                <div class="col-sm-9 text-danger" id="status">${s}</div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="card mb-4">
                    <div class="card-header bg-primary text-white">
                        <h4>Lý do:</h4>
                    </div>
                    <div class="card-body">
                        <div class="row mb-3">
                            <div class="col-sm-9" id="details">
                                <c:out value="${detailsWithBr}" escapeXml="false"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <form action="applicationdetails" method="post" id="applicationForm">
                        <label for="note">Ghi chú</label>
                        <textarea class="form-control mb-5" type="text" placeholder="${requestScope.application.processNote}"
                                  name="note" id="note" rows="5" required></textarea>
                        <input name="id" value="${requestScope.applicationId}" hidden/>
                        <input name="action" id="actionField" hidden="hidden">
                        <button name="action" value="approve" type="button" class="btn btn-success" onclick="confirmAccept('applicationForm', 'Bạn có chắc chắn muốn duyệt đơn này?', 'approve')">
                            Duyệt
                        </button>
                        <button name="action" value="reject" type="button" class="btn btn-danger" onclick="confirmAccept('applicationForm', 'Bạn có chắc chắn muốn từ chối đơn này?', 'reject')">
                            Từ chối
                        </button>
                    </form>
                </div>

                <div class="btn-group-right float-right">
                    <button type="button" class="btn btn-primary" onclick="history.back()"  style="width: 100px">Quay lại</button>
                </div>
            </div>
            <%-- Begin confirmMessage modal--%>
            <div class="modal fade" id="confirmMessage" tabindex="-1" role="dialog" aria-labelledby="confirmationModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" >Xác nhận thao tác</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body" id="confirmMessageTitle">
                            <!-- Dynamic message will be inserted here -->
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                            <button type="button" class="btn btn-primary" id="confirmMessageButton">Xác Nhận</button>
                        </div>
                    </div>
                </div>
            </div>
            <%-- End confirmMessage modal--%>
            <jsp:include page="../footer.jsp"/>
        </div>
    </div>

    <%--        this checks for application status and let staff process or not--%>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            // Get the application status from the JSP
            var applicationStatus = '${requestScope.application.status}';

            // Get the form elements
            var noteTextarea = document.getElementById('note');
            var approveButton = document.querySelector('button[name="action"][value="approve"]');
            var rejectButton = document.querySelector('button[name="action"][value="reject"]');

            // Check the status and modify the form accordingly
            if (applicationStatus !== 'đang chờ xử lý') {
                // Make the textarea read-only
                noteTextarea.readOnly = true;
                // Hide the submit buttons
                approveButton.style.display = 'none';
                rejectButton.style.display = 'none';
            }
        });
    </script>
</body>
</html>
