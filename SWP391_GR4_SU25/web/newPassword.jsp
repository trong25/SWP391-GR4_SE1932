<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Đổi Mật Khẩu</title>

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
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .form-container {
             background: white;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,0.15);
            width: 380px;
            padding: 30px 40px;
        }

        .form-container h4 {
            text-align: center;
            margin-bottom: 25px;
            color: #4A90E2;
        }

        .input-group-text {
            background-color: #f0f0f0;
            border-right: 0;
        }

        .form-control {
            border-left: 0;
        }

        .form-control:focus {
            box-shadow: none;
            border-color: #4A90E2;
        }

        .btn-primary {
            background-color: #4A90E2;
            border: none;
        }

        .btn-primary:hover {
            background-color: #3a76c1;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h4>Đổi mật khẩu</h4>
    <form action="newPassword" method="POST">
        <div class="input-group mb-3">
            <span class="input-group-text"><i class="fas fa-lock"></i></span>
            <input type="password" class="form-control" name="password"
                   placeholder="Nhập mật khẩu mới" pattern=".{8,12}" required
                   title="Mật khẩu phải từ 8 đến 12 ký tự">
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text"><i class="fas fa-lock"></i></span>
            <input type="password" class="form-control" name="confPassword"
                   placeholder="Nhập lại mật khẩu mới" pattern=".{8,12}" required
                   title="Mật khẩu phải từ 8 đến 12 ký tự">
        </div>
        <button type="submit" class="btn btn-primary w-100">Lưu mật khẩu</button>
    </form>
</div>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    <c:if test="${not empty err}">
        Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: '${err}',
            confirmButtonText: 'OK'
        });
    </c:if>
</script>
</body>
</html>
