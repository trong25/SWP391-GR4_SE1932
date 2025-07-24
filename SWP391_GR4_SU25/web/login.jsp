<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Đăng nhập</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FontAwesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet" />

    <style>
        body {
            background: linear-gradient(135deg, #4A90E2, #50E3C2);
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
        }

        .header {
            text-align: center;
            padding: 50px 0 10px;
            font-size: 2.2rem;
            font-weight: bold;
            color: white;
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3);
        }

        .login-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,0.15);
            width: 380px;
            padding: 30px 40px;
            margin: 0 auto;
        }

        .login-container .form-control:focus {
            box-shadow: 0 0 10px #4A90E2;
            border-color: #4A90E2;
        }

        .login-container .btn-primary {
            background-color: #4A90E2;
            border: none;
        }

        .login-container .btn-primary:hover {
            background-color: #3a76c1;
        }

        .error-message {
            color: #d9534f;
            font-weight: 600;
            margin-top: 12px;
            text-align: center;
        }

        .forgot-link {
            font-size: 0.9rem;
        }

        .footer {
            background: rgba(255, 255, 255, 0.05);
            color: white;
            font-size: 0.9rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px 30px;
        }

        .footer .left {
            display: flex;
            align-items: center;
        }

        .footer .left i {
            background-color: white;
            color: #4a90e2;
            font-weight: bold;
            border-radius: 50%;
            width: 30px;
            height: 30px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 10px;
        }

        .footer .right {
            text-align: right;
        }

        @media (max-width: 400px) {
            .login-container {
                width: 90%;
            }

            .footer {
                flex-direction: column;
                text-align: center;
                gap: 5px;
            }

            .footer .right {
                text-align: center;
            }
        }
    </style>
</head>
<body>

<div class="header">
    <i class="fas fa-graduation-cap text-warning me-2"></i>
    Welcome TaBi Learning
</div>


<div class="login-container">
    <form action="login" method="POST" novalidate>
        <div class="mb-3 input-group">
            <span class="input-group-text bg-white border-end-0"><i class="fa fa-user"></i></span>
            <input
                type="text"
                class="form-control border-start-0"
                name="username"
                placeholder="Tài khoản"
                required
                autofocus
            />
        </div>

        <div class="mb-3 input-group">
            <span class="input-group-text bg-white border-end-0"><i class="fa fa-lock"></i></span>
            <input
                type="password"
                class="form-control border-start-0"
                name="password"
                placeholder="Mật khẩu"
                required
            />
        </div>

        <div class="d-flex justify-content-between align-items-center mb-3">
            <a href="forgotPassword" class="forgot-link">Quên mật khẩu?</a>
        </div>

        <button type="submit" class="btn btn-primary w-100 py-2 fs-5">Đăng nhập</button>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
    </form>
</div>

<div class="footer">
    <div class="left">
        <i>TB</i>
        <span><strong>TaBi Learning</strong></span>
    </div>
    <div class="right">
        <div><i class="fa fa-envelope"></i> Email: he180086daovanhuy@gmail.com</div>
        <div><i class="fa fa-phone"></i> Hotline: 0899160904</div>
    </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
