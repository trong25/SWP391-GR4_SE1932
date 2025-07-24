    <%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Tabi Learning Center</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script>
            $(document).ready(function () {
                var toastMessage = '<%= request.getAttribute("toastMessage") %>';
                var toastType = '<%= request.getAttribute("toastType") %>';
                if (toastMessage) {
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'fail') {
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
                    <jsp:include page="header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center"> Báo cáo đánh giá trên lớp </h1>
                        <div class="row">
                            <style>
                                option[hidden] {
                                    display: none;
                                }
                                .class-form {
                                    margin: 0 10px; /* Adjust the margin as needed */
                                }
                                .custom-select {
                                    padding: 5px; /* Add padding for better appearance */
                                    border: 1px solid #ccc; /* Optional: Add border to match design */
                                    border-radius: 4px; /* Optional: Add border radius for rounded corners */
                                    min-width: 200px; /* Increase minimum width for readability */
                                    max-width: 100%; /* Allow the width to adjust based on content */
                                    overflow: hidden; /* Ensure no overflow */
                                    text-overflow: ellipsis; /* Handle overflowed text with ellipsis */
                                }
                            </style>

                            <c:set var="sltedw" value="${requestScope.sltedw}"/>
                            <c:set var="sltedy" value="${requestScope.sltedy}"/>
                            <form action="viewdailyevaluationreport" method="post">

                                <div style="display: flex; justify-content: space-evenly;">

                                    <c:if test="${requestScope.display eq 'week'}">
                                        <div class="class-form">
                                            <label>Năm học
                                                <select name="schoolyear" onchange=" this.form.submit();" class="custom-select" >
                                                    <option value="" hidden>Năm học</option>
                                                    <c:forEach items="${requestScope.schoolYearList}" var="sy">
                                                        <option ${sltedy eq sy.getId() ? "selected" : ""}
                                                            value="${sy.getId()}">${sy.getName()}</option>
                                                    </c:forEach>
                                                </select>
                                            </label>
                                        </div>

                                        <div class="class-form">
                                            <label>Tuần học
                                                <select name="week" onchange="this.form.submit()" class="custom-select" >
                                                    <option value="" hidden>Tuần học</option>
                                                    <c:forEach items="${requestScope.weekList}" var="w">
                                                        <option ${sltedw eq w.getId() ? "selected" : ""}
                                                            value="${w.getId()}">${w.getStartDatetoEndDate()} </option>
                                                        <c:set var="week" value="${w.getStartDatetoEndDate()}"/>
                                                    </c:forEach>
                                                </select>
                                            </label>
                                        </div>
                                    </c:if>


                                    <div class="class-form" hidden="">
                                        <label> Hiển thị
                                            <input name="display" hidden="" value="week">Theo tuần học
                                        </label>
                                        <div>
                                        </div>
                                    </div>
                                </div>
                            </form>

                        </div>


                        <c:if test='${requestScope.display == "week"}'>
                            <div class="card shadow mb-4">

                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary"> Đánh giá Theo Tuần: ${requestScope.cweek.getStartDatetoEndDate()} </h6>
                                    <h6 class="m-0 font-weight-bold text-primary"> Học sinh : ${sessionScope.student.lastName} ${sessionScope.student.firstName}</h6>
                                </div>


                                <%
            String[] daysOfWeek = {"Thứ Hai", "Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy","Chủ Nhật"};
            request.setAttribute("daysOfWeek", daysOfWeek);
                                %>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered" id="" width="100%" cellspacing="0">
                                            <thead>
                                                <tr>
                                                    <th>Thời gian</th>
                                                    <th>Thứ Hai</th>
                                                    <th>Thứ Ba</th>
                                                    <th>Thứ Tư</th>
                                                    <th>Thứ Năm</th>
                                                    <th>Thứ Sáu</th>
                                                    <th>Thứ Bảy</th>
                                                    <th>Chủ Nhật</th>


                                                </tr>
                                            </thead>

                                            <tbody>


                                                <tr>
                                                    <td>Đánh giá</td>
                                                    <c:forEach var="dayOfWeek" items="${daysOfWeek}">
                                                        <td>
                                                            <c:forEach var="evaluation" items="${requestScope.evaluationList}">
                                                                <c:if test="${evaluation.date.convertToWeekDay() == dayOfWeek}">
                                                                    ${evaluation.getEvaluation()}
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr>
                                                    <td>Ghi chú</td>
                                                    <c:forEach var="dayOfWeek" items="${daysOfWeek}">
                                                        <td>
                                                            <c:forEach var="evaluation" items="${requestScope.evaluationList}">
                                                                <c:if test="${evaluation.date.convertToWeekDay() == dayOfWeek}">
                                                                    ${evaluation.getNotes()}
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>

                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>
    </body>

</html>