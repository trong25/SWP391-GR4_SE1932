<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Thanh toán</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css"/>
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">
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
                // Hiển thị số tiền động khi chọn gói
                $('#packageSelect').on('change', function() {
                    var price = $(this).find(':selected').data('price');
                    if(price) {
                        $('#amount').val(price);
                    } else {
                        $('#amount').val('');
                    }
                });
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
                        <h1 class="h3 mb-4 text-gray-800 text-center">Thanh toán</h1>
                        <div class="card shadow mb-4" style="max-width: 700px; margin: auto;">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Gia hạn khóa học</h6>
                            </div>
                            <div class="card-body">
                                <form action="transaction" method="post">
                                    <div class="mb-3">
                                        <label for="packageSelect">Chọn gói gia hạn</label>
                                        <select id="packageSelect" name="packageId" class="custom-select dropdown mb-3" required>
                                            <option value="">- Chọn gói -</option>
                                            <c:forEach items="${requestScope.packages}" var="pkg">
                                                <option value="${pkg.id}" data-price="${pkg.price}">${pkg.name} - ${pkg.duration} tháng (${pkg.price} VNĐ)</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label for="amount">Số tiền</label>
                                        <input type="text" class="form-control mb-3" id="amount" name="amount" placeholder="Số tiền" readonly required>
                                    </div>
                                    <button type="submit" class="btn btn-primary float-right">Tạo mã QR</button>
                                </form>
                                <c:if test="${not empty requestScope.qrUrl}">
                                    <div class="mt-4 text-center">
                                        <h5>Quét mã QR để thanh toán</h5>
                                        <img src="${requestScope.qrUrl}" alt="QR Thanh toán" style="max-width: 300px; width: 100%; height: auto; margin: 0 auto;"/>
                                        <p class="mt-2">Vui lòng quét mã QR bằng app ngân hàng/Momo để hoàn tất thanh toán.</p>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
    </body>
</html>
