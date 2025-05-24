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
            justify-content: center;
            align-items: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .login-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,0.15);
            width: 380px;
            padding: 30px 40px;
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
        .login-logo {
            display: flex;
            justify-content: center;
            margin-bottom: 25px;
        }
        .login-logo img {
            width: 120px;
            border-radius: 50%;
            box-shadow: 0 0 15px rgba(74,144,226,0.6);
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
    </style>
</head>
<body>

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

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
