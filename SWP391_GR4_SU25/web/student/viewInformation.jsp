<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="studentBean" class="model.student.StudentDAO"/>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Profile</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Custom CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">

        <style>
            .profile-card {
                display: flex;
                flex-direction: column;
                align-items: center;
                background: #f8f9fa;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            .profile-card img {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                object-fit: cover;
                margin-bottom: 15px;
            }
            .profile-info {
                text-align: left;
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
                width: 100%;
            }
            .profile-info div {
                flex: 1 1 45%;
                margin-bottom: 10px;
            }
            .profile-info h3 {
                text-align: center;
                margin-bottom: 10px;
                color: #333;
                width: 100%;
            }
            .profile-info p {
                margin: 5px 0;
                color: #666;
            }
            .profile-actions {
                margin-top: 20px;
            }
            .profile-actions a {
                text-decoration: none;
                padding: 10px 20px;
                background: #007bff;
                color: white;
                border-radius: 5px;
                transition: background 0.3s ease;
            }
            .profile-actions a:hover {
                background: #0056b3;
            }
        </style>
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="header.jsp"/>
                    <div class="container-fluid">

                        <main>

                            <div class="row justify-content-center">

                                <div class="col-md-6">
                                    <div class="card shadow mb-4">
                                        <div class="card-header py-3">
                                            <h3 class="m-0 font-weight-bold row justify-content-center">Thông tin tài khoản</h3>
                                        </div>
                                    </div>
                                    <div class="profile-card">
                                        <c:set var="tempStudent" value="${studentBean.getStudentByUserId(sessionScope.student.userId)}"/>
                                        <c:if test="${tempStudent != null}">
                                            <c:set var="student" value="${studentBean.getStudentByIdWithNames(tempStudent.id)}"/>
                                        </c:if>
                                        <img src="${pageContext.request.contextPath}/images/${student.avatar}" alt="User Avatar">
                                        <div class="profile-info">
                                            <h3>${student.lastName} ${student.firstName}</h3>
                                            <div class="col-md-6">
                                                <div><p><strong>ID người dùng:</strong> ${student.userId}</p></div>
                                                <div><p><strong>ID học sinh:</strong> ${student.id}</p></div>
                                                <div><p><strong>Họ tên bố:</strong> ${student.firstGuardianName}</p></div>
                                                <div><p><strong>Số điện thoại bố:</strong> ${student.firstGuardianPhoneNumber}</p></div>
                                                <div><p><strong>Họ tên mẹ:</strong> ${student.secondGuardianName}</p></div>
                                                <div><p><strong>Số điện thoại mẹ:</strong> ${student.secondGuardianPhoneNumber}</p></div>
                                                <div><p><strong>Lớp:</strong> ${student.school_class_id.className}</p></div>
                                            </div>
                                            <div class="col-md-6">
                                                <div><p><strong>Họ tên học sinh:</strong> ${student.lastName} ${student.firstName}</p></div>
                                                <div><p><strong>Ngày sinh:</strong> <fmt:formatDate value="${student.birthday}" pattern="yyyy/MM/dd"/></p></div>
                                                <div><p><strong>Email:</strong> ${student.email}</p></div>
                                                <div><p><strong>Địa chỉ:</strong> ${student.address}</p></div>
                                                <div><p><strong>Tình trạng:</strong> ${student.status}</p></div>
                                                <div><p><strong>Trường:</strong> ${student.school_id.schoolName}</p></div>
                                                <div><p><strong>Ghi chú:</strong> ${student.parentSpecialNote}</p></div>
                                                
                                                
                                            </div>
                                        </div>
                                        <div class="profile-actions">
                                            <a href="information">Cập nhật thông tin</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
    </body>

</html>
