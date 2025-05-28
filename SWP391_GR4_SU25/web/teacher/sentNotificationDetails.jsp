<%@ page import="models.application.Application" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="bean" class="model.student.S tudentDAO"/>
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

    function confirmAccept(formId, msg, action) {
        document.getElementById('actionField').value = action;
        formIdToAccept = formId;
        document.getElementById('confirmMessageTitle').innerText = msg;
        $('#confirmMessage').modal('show');
    }
    $(document).ready(function () {
        $('#confirmMessageButton').click(function () {
            document.getElementById(formIdToAccept).submit();

        });
    });
</script>
<html>
    <head>
        <title>Bono Kindergarten</title>
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Chi Tiết Thông Báo</h1>
                        <div class="card mb-4">
                            <div class="card-body">
                                <div class="row mb-3">
                                    <div class="col-sm-3 font-weight-bold">Thông Báo:</div>
                                    <div class="col-sm-9" id="type">
                                        ${notifi.heading}
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-3 font-weight-bold">Người nhận:</div>
                                    <div class="col-sm-9" id="createdBy">
                                        <c:forEach items="${listrole_id}" var="role_id">
                                            ${roleMap[role_id]}</br>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-sm-3 font-weight-bold">Thời gian gửi:</div>
                                    <div class="col-sm-9" id="createdAt">
                                        <fmt:formatDate value="${notifi.createdAt}" pattern="yyyy/MM/dd"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header bg-primary text-white">
                                <h4>Nội Dung:</h4>
                            </div>
                            <div class="card-body">
                                <div class="row mb-3">
                                    <div class="col-sm-9" id="details">
                                        ${notifi.details}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button type="button" onclick="goBack()" class="btn btn-danger">Quay Lại</button>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
    </body>
    <script>
        function goBack() {
            window.history.back();
        }
    </script>
</html>
