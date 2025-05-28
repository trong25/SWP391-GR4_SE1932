<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quên mật khẩu</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap 5 -->
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
        .forgot-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,0.15);
            width: 400px;
            padding: 30px 40px;
        }
        .forgot-container .form-control:focus {
            box-shadow: 0 0 10px #4A90E2;
            border-color: #4A90E2;
        }
        .forgot-container .btn-primary {
            background-color: #4A90E2;
            border: none;
        }
        .forgot-container .btn-primary:hover {
            background-color: #3a76c1;
        }
        .forgot-logo {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }
        .forgot-logo img {
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
    </style>
</head>
<body>

<div class="forgot-container">
 
    <h4 class="text-center mb-4 text-primary">Quên mật khẩu?</h4>

    <form action="forgotPassword" method="post" novalidate>
        <div class="mb-3 input-group">
            <span class="input-group-text bg-white border-end-0"><i class="fa fa-user"></i></span>
            <input type="text" class="form-control border-start-0" name="email" placeholder="Nhập tài khoản hoặc email" required />
        </div>

        <button type="submit" class="btn btn-primary w-100 py-2 fs-5">Xác nhận</button>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
    </form>

    <div class="text-center mt-3">
        <a href="login.jsp" class="text-decoration-none text-primary">← Quay lại đăng nhập</a>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
