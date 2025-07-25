<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Gửi Đơn</title>
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
    </head>
    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="header-student.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Gửi Đơn</h1>

                        <div class="card" style="max-width: 700px; margin: auto;">
                            <div class="card-body">
                                <h4 class="h4 mb-2 text-gray-800">Loại Đơn</h4>
                                <form action="sendapplication" method="post">
                                    <div class="mb-3">
                                        <select id="applicationType" style="width: 30%" class="custom-select dropdown mb-3" aria-label="Default select example" name="type">
                                            <option value="">- Chọn Loại Đơn -</option>
                                            <c:forEach items="${requestScope.applicationTypes}" var="type">
                                                <option value="${type.id}">${type.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="row" id="datePickerContainer" style="display: none;">
                                        <div class="form-group col-md-6" id="startDatePickerContainer">
                                            <label for="startDate">Chọn Ngày Bắt Đầu</label>
                                            <input type="date" class="form-control mb-3" id="startDate" name="startDate">
                                        </div>
                                        <div class="form-group col-md-6" id="endDatePickerContainer">
                                            <label for="endDate">Chọn Ngày Kết Thúc</label>
                                            <input type="date" class="form-control mb-3" id="endDate" name="endDate">
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        <h4 class="h4 mb-2 text-gray-800">Lý do</h4>
                                        <textarea class="form-control mb-5" type="text" placeholder="Lý do"
                                                  name="details" rows="5" required></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary float-right">Gửi</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
        <script>
            document.getElementById('applicationType').addEventListener('change', function () {
                var selectedType = this.value;
                var datePickerContainer = document.getElementById('datePickerContainer');
                if (selectedType === 'AT000001') {
                    datePickerContainer.style.display = 'flex';

                } else {
                    datePickerContainer.style.display = 'none';
                }
            });
        </script>
    </body>
</html>
