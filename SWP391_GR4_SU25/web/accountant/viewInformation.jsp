<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="personnelBean" class="model.personnel.PersonnelDAO"/>
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
            .profile-info {
                text-align: left;
                max-width: 500px;
                margin: 0 auto;
                font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
                color: #333;
            }
            .profile-info h3 {
                text-align: center;
                width: 100%;
                margin-bottom: 20px;
                color: #333;
            }


            .profile-info p {
                margin: 8px 0;
                line-height: 1.5;
                font-size: 16px;
                border-bottom: 1px solid #eee;
                padding-bottom: 6px;
            }

            .profile-info strong {
                color: #007bff;
                min-width: 150px;
                display: inline-block;
            }

            .profile-info a {
                color: #007bff;
                text-decoration: none;
                font-weight: 600;
            }

            .profile-info a:hover {
                text-decoration: underline;
            }

            .profile-card {
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 6px 12px rgba(0,0,0,0.1);
                padding: 25px 30px;
                max-width: 600px;
                margin: 30px auto;
            }

            .profile-card img {
                width: 160px;
                height: 160px;
                border-radius: 50%;
                object-fit: cover;
                margin: 0 auto 20px auto;
                display: block;
                box-shadow: 0 4px 8px rgba(0,0,0,0.15);
            }
            .profile-actions {
                margin-top: 20px;
                text-align: center; /* căn giữa nội dung bên trong */
            }

            .profile-actions a {
                display: inline-block;
                margin-top: 25px;
                padding: 12px 28px;
                background-color: #007bff;
                color: white;
                border-radius: 8px;
                font-weight: 600;
                transition: background-color 0.3s ease;
            }

            .profile-actions a:hover {
                background-color: #0056b3;
            }

        </style>
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <!-- Head Teacher Information Section -->
                        <main>

                            <div class="row justify-content-center">
                                <div class="col-md-6">
                                    <div class="card shadow mb-4">
                                        <div class="card-header py-3">
                                            <h3 class="m-0 font-weight-bold row justify-content-center">Thông tin tài khoản</h3>
                                        </div>
                                    </div>
                                    <div class="profile-card">
                                        <c:set var="personnel" value="${personnelBean.getPersonnels(sessionScope.personnel.id)}"/>
                                        <img src="${pageContext.request.contextPath}/images/${personnel.avatar}" alt="User Avatar">
                                        <div class="profile-info">
                                            <h3>${personnel.lastName} ${personnel.firstName}</h3>
                                            <br/>
                                            <p><strong>ID:</strong> ${personnel.userId}</p>
                                            <br/>
                                            <p><strong>Giới tính:</strong> ${personnel.gender ? 'Nam' : 'Nữ'}</p>
                                            <br/>
                                            <p><strong>Ngày sinh:</strong> <fmt:formatDate value="${personnel.birthday}" pattern="yyyy/MM/dd"/> </p>
                                            <br/>
                                            <p><strong>Địa chỉ:</strong> ${personnel.address}</p>
                                            <br/>
                                            <p><strong>Email:</strong> ${personnel.email}</p>
                                            <br/>
                                            <p><strong>Số điện thoại:</strong> ${personnel.phoneNumber}</p>
                                            <br/>
                                            <p><strong>Chuyên môn:</strong> ${personnel.specialization}</p>
                                            <br/>
                                            <p><strong>Trình độ:</strong> ${personnel.qualification}</p>
                                            <br/>
                                            <p><strong>Số năm kinh nghiệm:</strong> ${personnel.teaching_years}</p>
                                            <br/>
                                            <p><strong>Thành tích nổi bật:</strong> ${personnel.achievements}</p>
                                            <br/>


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
