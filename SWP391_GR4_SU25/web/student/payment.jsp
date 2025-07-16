<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TaBi Learning Center</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .payment-container {
                max-width: 500px;
                margin: 0 auto;
                padding: 2rem 1rem;
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .payment-card {
                background: rgba(255, 255, 255, 0.95);
                border-radius: 20px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                padding: 2.5rem;
                text-align: center;
                backdrop-filter: blur(10px);
                border: 1px solid rgba(255, 255, 255, 0.2);
            }

            .payment-header {
                margin-bottom: 2rem;
            }

            .payment-title {
                color: #2c3e50;
                font-size: 1.8rem;
                font-weight: 600;
                margin-bottom: 0.5rem;
            }

            .payment-subtitle {
                color: #7f8c8d;
                font-size: 1rem;
                margin-bottom: 1.5rem;
            }

            .amount-display {
                background: linear-gradient(45deg, #3498db, #2ecc71);
                color: white;
                padding: 1rem 2rem;
                border-radius: 15px;
                font-size: 1.5rem;
                font-weight: bold;
                margin-bottom: 2rem;
                display: inline-block;
            }

            .qr-container {
                background: white;
                padding: 1.5rem;
                border-radius: 15px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                margin-bottom: 2rem;
                display: inline-block;
            }

            .qr-code {
                max-width: 280px;
                width: 100%;
                height: auto;
                border-radius: 10px;
            }

            .payment-info {
                background: #f8f9fa;
                padding: 1.5rem;
                border-radius: 15px;
                margin-bottom: 2rem;
            }

            .info-item {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 0.8rem;
                font-size: 0.9rem;
            }

            .info-item:last-child {
                margin-bottom: 0;
            }

            .info-label {
                color: #7f8c8d;
                font-weight: 500;
            }

            .info-value {
                color: #2c3e50;
                font-weight: 600;
            }

            .status-indicator {
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 0.5rem;
                padding: 1rem;
                background: #fff3cd;
                border: 1px solid #ffeaa7;
                border-radius: 10px;
                color: #856404;
                font-weight: 500;
                margin-bottom: 1.5rem;
            }

            .status-indicator.success {
                background: #d4edda;
                border-color: #c3e6cb;
                color: #155724;
            }

            .spinner {
                width: 20px;
                height: 20px;
                border: 2px solid #f3f3f3;
                border-top: 2px solid #3498db;
                border-radius: 50%;
                animation: spin 1s linear infinite;
            }

            @keyframes spin {
                0% {
                    transform: rotate(0deg);
                }
                100% {
                    transform: rotate(360deg);
                }
            }

            .instructions {
                background: #e8f4f8;
                padding: 1.5rem;
                border-radius: 15px;
                text-align: left;
                margin-bottom: 2rem;
            }

            .instructions h6 {
                color: #2c3e50;
                margin-bottom: 1rem;
                font-weight: 600;
            }

            .instructions ol {
                color: #5a6c7d;
                margin-bottom: 0;
                padding-left: 1.2rem;
            }

            .instructions li {
                margin-bottom: 0.5rem;
                font-size: 0.9rem;
            }

            .bank-info {
                background: linear-gradient(45deg, #667eea, #764ba2);
                color: white;
                padding: 1rem;
                border-radius: 10px;
                margin-top: 1rem;
            }

            .bank-info strong {
                display: block;
                margin-bottom: 0.3rem;
            }

            @media (max-width: 576px) {
                .payment-card {
                    padding: 1.5rem;
                    margin: 1rem;
                }

                .payment-title {
                    font-size: 1.5rem;
                }

                .amount-display {
                    font-size: 1.2rem;
                    padding: 0.8rem 1.5rem;
                }
            }
        </style>
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="header-student.jsp"/>
                    <div class="payment-container">
                        <div class="payment-card">
                            <div class="payment-header">
                                <h1 class="payment-title">
                                    <i class="fas fa-qrcode text-primary"></i>
                                    Thanh Toán QR Code
                                </h1>
                                <p class="payment-subtitle">Quét mã QR bên dưới để thực hiện thanh toán</p>
                            </div>

                            <div class="amount-display">
                                <i class="fas fa-money-bill-wave"></i>
                                <span id="amount-text"></span>
                            </div>

                            <div class="qr-container">
                                <img src='https://img.vietqr.io/image/MB-00160920049999-qr_only.jpg?amount=${payment.amount}&addInfo=${payment.code}&accountName=Pham%20Nguyen%20Kien' class="qr-code" alt="QR Code thanh toán">
                            </div>

                            <div class="payment-info">
                                <div class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-receipt"></i>
                                        Mã giao dịch:
                                    </span>
                                    <span class="info-value">${payment.code}</span>
                                </div>
                                <div class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-university"></i>
                                        Ngân hàng:
                                    </span>
                                    <span class="info-value">MB Bank</span>
                                </div>
                                <div class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-user"></i>
                                        Người nhận:
                                    </span>
                                    <span class="info-value">Pham Nguyen Kien</span>
                                </div>
                                <div class="info-item">
                                    <span class="info-label">
                                        <i class="fas fa-credit-card"></i>
                                        Số tài khoản:
                                    </span>
                                    <span class="info-value">00160920049999</span>
                                </div>
                            </div>

                            <div class="status-indicator" id="status-indicator">
                                <div class="spinner"></div>
                                <span>Đang chờ thanh toán...</span>
                            </div>

                            <div class="instructions">
                                <h6><i class="fas fa-info-circle"></i> Hướng dẫn thanh toán:</h6>
                                <ol>
                                    <li>Mở ứng dụng ngân hàng trên điện thoại</li>
                                    <li>Chọn chức năng quét QR Code</li>
                                    <li>Quét mã QR code ở trên</li>
                                    <li>Kiểm tra thông tin và xác nhận thanh toán</li>
                                    <li>Chờ hệ thống xác nhận giao dịch</li>
                                </ol>

                                <div class="bank-info">
                                    <strong><i class="fas fa-shield-alt"></i> Bảo mật:</strong>
                                    Giao dịch được bảo vệ bởi hệ thống bảo mật ngân hàng
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>


        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
        <script>
            // Cập nhật giao diện với dữ liệu từ JSP
            function updatePaymentInfo() {
                document.getElementById('amount-text').textContent =
                        new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(${payment.amount});
            }

            // Cập nhật trạng thái thanh toán
            function updatePaymentStatus(isSuccess) {
                const statusIndicator = document.getElementById('status-indicator');

                if (isSuccess) {
                    statusIndicator.className = 'status-indicator success';
                    statusIndicator.innerHTML = `
                        <i class="fas fa-check-circle"></i>
                        <span>Thanh toán thành công!</span>
                    `;

                    // Chuyển hướng sau 2 giây
                    setTimeout(() => {
                        window.location.href = '/SWP391_GR4_SU25/student/dashboard';
                    }, 2000);
                }
            }

            // API functions từ code gốc
            const HEAD_URL = "https://docs.google.com/spreadsheets/d/";
            const SHEET_ID = "1XCFFXNArzXJB5IzCSJac8NNRXERKrJPHIggg9_ZrhBM";
            const GID_STRING = "gviz/tq?sheet=trans";

            function callTransactionData(sizeOfCell) {
                let sheet_range = "A2:F" + (sizeOfCell + 1);
                let full_link = HEAD_URL + SHEET_ID + "/" + GID_STRING + "&range=" + sheet_range;

                fetch(full_link)
                        .then(res => res.text())
                        .then(rep => {
                            var dataOfSheet = JSON.parse(rep.substr(47).slice(0, -2)).table.rows;
                            dataOfSheet.forEach(r_data => {
                                console.log(r_data);
                                if (parseFloat(r_data.c[2].v + "") >= (${payment.amount} * 0.99)) {
                                    let tran = r_data.c[1].v;
                                    if (tran.includes('${payment.code}')) {
                                        sendBuySignal('${payment.code}');
                                    }
                                }
                            });
                        })
                        .catch(err => {
                            console.log('Lỗi khi kiểm tra giao dịch:', err);
                        });
            }

            function sendBuySignal(transactionCode) {
                fetch('/SWP391_GR4_SU25/student/PaymentAccept?code=' + transactionCode, {
                    method: 'GET'
                })
                        .then(function (response) {
                            if (response.status !== 200) {
                                console.log('Lỗi, mã lỗi ' + response.status);
                                return;
                            }

                            // Cập nhật trạng thái thành công
                            updatePaymentStatus(true);
                        })
                        .catch(err => {
                            console.log('Error :-S', err);
                        });
            }

            // Khởi tạo trang
            document.addEventListener('DOMContentLoaded', function () {
                updatePaymentInfo();

                // Kiểm tra giao dịch mỗi 2 giây
                setInterval(function () {
                    callTransactionData(4);
                }, 2000);
            });
        </script>
    </body>
</html>