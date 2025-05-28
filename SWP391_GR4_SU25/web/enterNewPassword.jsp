<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Nhập mật khẩu mới</title>
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
        .password-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,0.15);
            width: 400px;
            padding: 30px 40px;
        }
        .password-container .form-control:focus {
            box-shadow: 0 0 10px #4A90E2;
            border-color: #4A90E2;
        }
        .password-container .btn-primary {
            background-color: #4A90E2;
            border: none;
        }
        .password-container .btn-primary:hover {
            background-color: #3a76c1;
        }
        .password-logo {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }
        .password-logo img {
            width: 120px;
            border-radius: 50%;
            box-shadow: 0 0 15px rgba(74,144,226,0.6);
        }
        .message-line {
            color: black;
            font-weight: 500;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="password-container">


    <h4 class="text-center mb-4 text-primary">Nhập mật khẩu mới</h4>

    <form action="ValidatePassword" method="post" novalidate>
        <div class="mb-3 input-group">
            <span class="input-group-text bg-white border-end-0"><i class="fa fa-key"></i></span>
            <input type="password" class="form-control border-start-0" name="currentPassword" placeholder="Nhập mật khẩu mới" required />
        </div>

        <button type="submit" class="btn btn-primary w-100 py-2 fs-5">Đổi mật khẩu</button>

        <div class="message-line">
            <c:if test="${not empty message}">
                ${message}
            </c:if>
            <c:if test="${not empty error}">
                ${error}
            </c:if>
        </div>
    </form>

    <div class="text-center mt-3">
        <a href="login.jsp" class="text-decoration-none text-primary">← Quay lại đăng nhập</a>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
