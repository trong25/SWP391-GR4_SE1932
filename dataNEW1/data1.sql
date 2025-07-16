
INSERT INTO [Roles] VALUES (0,N'admin'),
	(1,N'director'),
	(2,N'academic staff'),
	(3,N'teacher'),
	(4,N'student'),
	(5,N'accountant');
INSERT INTO [User]
VALUES
('U000001', 'ad000001', 0x1009DC156FE762654F4D9CE9AE7BCB09, 0xF260D2220E1E9EC4967BC30E7AD4F34284487599954190791DF1CE327D9AA3CC, 'daohuy0202@gmail.com', 0, 0),
('U000002', 'gd000001', 0x43DBDF8D502A577C0E9F248B0E88EE56, 0xDE1E5D384682E20EB1704F35D726026284FD31AB89880C03DC70725EA8CE09CE, 'tuanthanh592004@gmail.com', 1, 0),
('U000003', 'nv000006', 0x529475B5FE5498EC136F5C78498914F4, 0xEB58E342C22A091DD84A285456E4C5800F42EEE7D271E5C35D8702A2BD9FDAB9, 'he181249nguyenvantrong@gmail.com', 2, 0),
('U000004', 'gv000008', 0x05239B93674F621613653C7F4FE05C66, 0x052DCC65FCD3745318E7E3DDE4371A3662109FDFAD5B5B39D14F49C2ED530EC3, 'thanhbg1310@gmai.com', 3, 0),
('U000005', 'hs000001', 0x2F6D1A0C44C9D15A3BECE80B47AEC9A6, 0xA9C207CAF5965FA1E9F219A107FC77F4665D7749337BEF99FBAFAB9B79AE9FB7, 'phamnguyenkien1609@gmail.com', 4, 0),
('U000006', 'kt000001', 0xD8B4635435FC830A5F23D0318F748705, 0xC00A5B6FA775EA21E18FD52B6023BAED80E83C439CAA558681A0F6CC4D887A75, N'khunglong0202@gmail.com', 5, 0);

INSERT INTO [Schools] VALUES
('S001', N'Trường THCS Kim Đồng', N'123 Trần Phú, Hà Nội', 'kimdong@school.edu.vn'),
('S002', N'Trường THCS Nguyễn Trãi', N'45 Lê Lợi, Hà Nội', 'nguyentrai@school.edu.vn'),
('S003', N'Trường THPT Chu Văn An', N'88 Nguyễn Du, Hà Nội', 'chuvanan@school.edu.vn'),
('S004', N'Trường THCS Lê Văn Tám', N'77 Hai Bà Trưng, Hà Nội', 'levantam@school.edu.vn'),
('S005', N'Trường THCS Đống Đa', N'52 Đặng Tiến Đông, Hà Nội', 'dongda@school.edu.vn'),
('S006', N'Trường THPT Phan Đình Phùng', N'60 Nguyễn Trãi, Hà Nội', 'phanphung@school.edu.vn'),
('S007', N'Trường THCS Nghĩa Tân', N'15 Hoàng Quốc Việt, Hà Nội', 'nghiatan@school.edu.vn'),
('S008', N'Trường THCS Cầu Giấy', N'91 Trần Duy Hưng, Hà Nội', 'caugiay@school.edu.vn'),
('S009', N'Trường THPT Việt Đức', N'20 Lý Thường Kiệt, Hà Nội', 'vietduc@school.edu.vn'),
('S010', N'Trường THCS Lý Thường Kiệt', N'13 Quang Trung, Hà Nội', 'lykiet@school.edu.vn');

INSERT INTO [SchoolClasses] (id, school_id, class_name, grade_level) VALUES
('SC001', 'S001', N'Lớp 6B', N'Lớp 6'),
('SC002', 'S001', N'Lớp 7B', N'Lớp 7'),
('SC003', 'S002', N'Lớp 6A', N'Lớp 6'),
('SC004', 'S002', N'Lớp 7B', N'Lớp 7'),
('SC005', 'S003', N'Lớp 10A1', N'Lớp 10'),
('SC006', 'S003', N'Lớp 11A2', N'Lớp 11'),
('SC007', 'S004', N'Lớp 8B', N'Lớp 8'),
('SC008', 'S005', N'Lớp 8A', N'Lớp 8'),
('SC009', 'S006', N'Lớp 12A1', N'Lớp 12'),
('SC010', 'S010', N'Lớp 9B', N'Lớp 9');
INSERT INTO [Personnels] (
  id, first_name, last_name, gender, birthday, address, email, phone_number,
  role_id, status, avatar, user_id, school_id, school_class_id,
  specialization, qualification, teaching_years, achievements, cv_file
)
VALUES
-- ===== GIÁO VIÊN =====
(N'GV000001',N'Anh Dũng',N'Nguyễn',1,'1999-07-09',N'123 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh',N'dungnguyen@gmail.com',N'0901 234 567',3,N'đang chờ xử lý',N'gv1.png',NULL, N'S001', N'SC001', N'Toán', N'Thạc sĩ', 5, N'GV giỏi TP 2022', NULL),
(N'GV000002',N'Bảo Ngọc',N'Trần',0,'1999-07-10',N'45 Trần Hưng Đạo, Quận Hoàn Kiếm, Hà Nội',N'ngoctran@gmail.com',N'0912 345 678',3,N'đang chờ xử lý',N'gv2.png',NULL,N'S001', N'SC001', N'Văn', N'Tiến sĩ', 6, N'Tác giả SGK Ngữ Văn 9', NULL),
(N'GV000003',N'Chí Công',N'Lê',1,'1999-07-11',N'10 Lê Lợi, Quận Hải Châu, Đà Nẵng',N'congle@gmail.com',N'0923 456 789',3,N'đang chờ xử lý',N'gv3.png',NULL,N'S001', N'SC001', N'Toán', N'Thạc sĩ', 4, N'Sáng tạo thiết bị dạy học', NULL),
(N'GV000004',N'Đình Phong',N'Phạm',1,'1999-07-12',N'77 Nguyễn Văn Linh, Quận Ninh Kiều, Cần Thơ',N'phongpham@gmail.com',N'0934 567 890',3,N'đang chờ xử lý',N'gv4.png',NULL, N'S002', N'SC002', N'Tin', N'Thạc sĩ', 3, N'Hướng dẫn HSG tỉnh', NULL),
(N'GV000005',N'Gia Hân',N'Hoàng',0,'1997-10-13',N'6 Hùng Vương, TP. Huế, Thừa Thiên Huế',N'hanhoang@gmail.com',N'0945 678 901',3,N'đang chờ xử lý',N'gv5.png',NULL, N'S002', N'SC002', N'Toán', N'Tiến sĩ', 8, N'Nghiên cứu khoa học đạt giải quốc gia', NULL),
(N'GV000006',N'Hải Đăng',N'Huỳnh',1,'1998-04-08',N'99 Điện Biên Phủ, Quận Bình Thạnh, TP. Hồ Chí Minh',N'danghuynh@gmail.com',N'0956 789 012',3,N'đang chờ xử lý',N'gv6.png',NULL, N'S002', N'SC002', N'Tin', N'Thạc sĩ', 5, N'Đạt giải Sáng tạo trẻ VN', NULL),
(N'GV000007',N'Hoài An',N'Phan',0,'1998-10-02',N'30 Hàng Bông, Quận Hoàn Kiếm, Hà Nội',N'anphan@gmail.com',N'0967 890 123',3,N'đang chờ xử lý',N'gv7.png',NULL,  N'S002', N'SC002', N'Anh', N'Thạc sĩ', 4, N'Biên soạn giáo trình song ngữ', NULL),
(N'GV000008',N'Hồng Ngọc',N'Vũ',0,'1996-07-30',N'20 Võ Văn Kiệt, Quận Sơn Trà, Đà Nẵng',N'thanhbg1310@gmai.com',N'0978 901 234',3,N'đang làm việc',N'gv8.png',N'U000004', N'S002', N'SC002', N'Văn', N'Tiến sĩ', 7, N'GV xuất sắc toàn quốc', NULL),
(N'GV000009',N'Hữu Tín',N'Võ',1,'1996-07-31',N'55 Trần Phú, TP. Nha Trang, Khánh Hòa',N'tinvo@gmail.com',N'0989 012 345',3,N'đang làm việc',N'gv9.png',NULL,  N'S003', N'SC003', N'Toán', N'Thạc sĩ', 6, N'Giảng viên tiêu biểu ĐHKT', NULL),
(N'GV000010',N'Khánh Linh',N'Đặng',0,'1996-08-01',N'22 Lý Tự Trọng, Quận Ninh Kiều, Cần Thơ',N'linhdang@gmail.com',N'0990 123 456',3,N'đang làm việc',N'gv10.png',NULL,  N'S003', N'SC003', N'Toán', N'Thạc sĩ', 6, N'NCKH Sinh đạt giải tỉnh', NULL),
(N'GV000011',N'Kim Ngân',N'Bùi',0,'1995-06-07',N'78 Đinh Tiên Hoàng, TP. Vũng Tàu, Bà Rịa - Vũng Tàu',N'nganbui@gmail.com',N'0891 234 567',3,N'đang làm việc',N'gv11.png',NULL, N'S003', N'SC003', N'Văn', N'Thạc sĩ', 9, N'GV giỏi cấp tỉnh', NULL),
(N'GV000012',N'Lan Anh',N'Đỗ',0,'1995-06-08',N'16 Nguyễn Thị Minh Khai, TP. Đà Lạt, Lâm Đồng',N'anhdo@gmail.com',N'0882 345 678',3,N'đang làm việc',N'gv12.png',NULL,  N'S003', N'SC003', N'Anh', N'Tiến sĩ', 10, N'Biên dịch viên TOEFL quốc tế', NULL),
(N'GV000013',N'Lê Phong',N'Hồ',1,'1993-03-09',N'80 Nguyễn Du, Quận Hai Bà Trưng, Hà Nội',N'phongho@gmail.com',N'0873 456 789',3,N'đang làm việc',N'gv13.png',NULL,  N'S003', N'SC003', N'Toán', N'Thạc sĩ', 11, N'GV chủ nhiệm đội tuyển quốc gia', NULL),
(N'GV000014',N'Mai Phương',N'Ngô',0,'1993-03-10',N'50 Nguyễn Tất Thành, TP. Pleiku, Gia Lai',N'phuongngo@gmail.com',N'0864 567 890',3,N'đang làm việc',N'gv14.png',NULL,  N'S003', N'SC003', N'Anh', N'Tiến sĩ', 9, N'Thành viên đề án song ngữ quốc gia', NULL),
(N'GV000015',N'Minh Khang',N'Dương',1,'1992-12-08',N'25 Phạm Ngũ Lão, Quận 1, TP. Hồ Chí Minh',N'khangduong@gmail.com',N'0855 678 901',3,N'đang làm việc',N'gv15.png',NULL, N'S003', N'SC003', N'Toán', N'Thạc sĩ', 10, N'GV giỏi cấp Quốc gia 2023', NULL),

-- ===== NHÂN SỰ KHÁC =====
(N'NV000001',N'Thảo Nguyên',N'Tô',0,'1983-01-10',N'400 Nguyễn Văn Cừ, Quận 5, TP. Hồ Chí Minh',N'nguyento@gmail.com',N'0756 789 011',3,N'đang chờ xử lý',N'as1.png',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000002',N'Thiên Bảo',N'Tạ',1,'1981-02-07',N'500 Trần Hưng Đạo, Quận 1, TP. Hồ Chí Minh',N'baota@gmail.com',N'0767 890 126',3,N'đang chờ xử lý',N'as2.png',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000003',N'Thiên Kim',N'Trương',0,'1981-02-08',N'11 Trần Hưng Đạo, Quận 1, TP. Hồ Chí Minh',N'kimtruong@gmail.com',N'0778 901 235',3,N'đang chờ xử lý',N'as3.png',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000004',N'Thùy Dung',N'Bạch',0,'1981-02-09',N'22 Phạm Ngũ Lão, Quận 1, TP. Hồ Chí Minh',N'dungbach@gmail.com',N'0789 012 340',3,N'đang chờ xử lý',N'as4.png',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000005',N'Thùy Linh',N'Hứa',0,'1980-09-02',N'33 Lý Tự Trọng, Quận 1, TP. Hồ Chí Minh',N'linhhua@gmail.com',N'0790 123 450',3,N'đang chờ xử lý',N'as5.png',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000006',N'Thùy Trang',N'Thái',0,'1980-09-03',N'44 Nguyễn Thái Học, Quận 1, TP. Hồ Chí Minh',N'he181249nguyenvantrong@gmail.com',N'0601 234 569',2,N'đang làm việc',N'as6.png',N'U000003', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000007',N'Thuận Phong',N'Khổng',1,'1980-09-04',N'55 Nguyễn Trãi, Quận 1, TP. Hồ Chí Minh',N'phongkhong@gmail.com',N'0612 345 670',2,N'đang làm việc',N'as7.png',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000008',N'Tiến Đạt',N'Tôn',1,'1977-07-06',N'66 Trần Phú, Quận 1, TP. Hồ Chí Minh',N'dattton@gmail.com',N'0623 456 789',2,N'đang làm việc',N'as8.png',NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000009',N'Trang Nhung',N'Đoàn',0,'1977-07-07',N'77 Võ Văn Tần, Quận 3, TP. Hồ Chí Minh',N'nhungdoan@gmail.com',N'0634 567 890',2,N'đang làm việc',N'as9.png',NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000010',N'Trọng Nghĩa',N'Kiều',1,'1977-07-08',N'88 Nguyễn Đình Chiểu, Quận 3, TP. Hồ Chí Minh',N'nghiakieu@gmail.com',N'0645 678 901',2,N'đang làm việc',N'as10.png',NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000011',N'Trung Hiếu',N'Châu',1,'1977-07-09',N'99 Lê Văn Sỹ, Quận Phú Nhuận, TP. Hồ Chí Minh',N'hieuchau@gmail.com',N'0656 789 012',2,N'đang làm việc',N'as11.png',NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000012',N'Tường Vy',N'Quách',0,'1977-12-06',N'21 Đinh Bộ Lĩnh, Quận Bình Thạnh, TP. Hồ Chí Minh',N'vyquach@gmail.com',N'0667 890 123',2,N'đang làm việc',N'as12.png',NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000013',N'Tuyết Mai',N'Tăng',0,'1975-03-09',N'32 Phan Xích Long, Quận Phú Nhuận, TP. Hồ Chí Minh',N'maitang@gmail.com',N'0678 901 234',2,N'đang làm việc',N'as13.png',NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000014',N'Vân Khánh',N'Phùng',1,'1975-03-10',N'43 Nguyễn Hữu Cảnh, Quận Bình Thạnh, TP. Hồ Chí Minh',N'khanhphung@gmail.com',N'0689 012 345',2,N'đang làm việc',N'as14.png',NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'NV000015',N'Văn Sơn',N'Thạch',1,'1996-09-16',N'54 Bùi Viện, Quận 1, TP. Hồ Chí Minh',N'sonthach@gmail.com',N'0690 123 456',2,N'đang làm việc',N'as15.png',NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'GD000001',N'Xuân Hòa',N'Chu',1,'1996-09-17',N'65 Nguyễn Thị Nghĩa, Quận 1, TP. Hồ Chí Minh',N'tuanthanh592004@gmail.com',N'0991 234 567',1,N'đang làm việc',N'ht1.png',N'U000002',NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'AD000001',N'Xuân Khương',N'Phan',1,'1996-09-18',N'Hoà Lạc, Hà Nội',N'daohuy0202@gmail.com',N'0987 789 129',0,N'đang làm việc',N'ad1.png',N'U000001',NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(N'KT000001',N'Phương Linh',N'Lâm',0,'1987-09-02',N'14 Trần Quang Khải, Quận 1, TP. Hồ Chí Minh',N'khunglong0202@gmail.com',N'0923 234 567',5,N'đang làm việc',N'ac1.png',N'U000006',NULL, NULL, NULL, NULL, NULL, NULL, NULL);



INSERT INTO [Students] VALUES
-- Nhóm 1 (HS000001 - HS000010)
(N'HS000001', N'U000005', N'Minh Anh', N'Nguyễn', N'123 Đường Kim Mã, Quận Ba Đình, Hà Nội', N'phamnguyenkien1609@gmail.com', N'đang theo học', '2019-07-07', 1, N'Nguyễn Hằng', '+84912345678', N'pupil1.png', N'Nguyễn Đức', '+84912345778', N'NV000010', NULL, N'S001', N'SC001'),
(N'HS000002', NULL, N'Thị Thu Hằng', N'Trần', N'456 Đường Láng Hạ, Quận Đống Đa, Hà Nội', N'tran.quang@gmail.com', N'đang theo học', '2019-07-08', 0, N'Trần Lan Anh', '+84912345679', N'pupil2.png', N'Trần Quang', '+84912345779', N'NV000010', N'NONE', N'S001', N'SC001'),
(N'HS000003', NULL, N'Huy Hoàng', N'Lê', N'789 Đường Nguyễn Chí Thanh, Quận Đống Đa, Hà Nội', N'le.thanh@gmail.com', N'đang theo học', '2019-07-09', 0, N'Lê Ngọc Trâm', '+84912345680', N'pupil3.png', N'Lê Thành', '+84912345780', N'NV000010', N'NONE', N'S001', N'SC001'),
(N'HS000004', NULL, N'Ngọc Lan', N'Phạm', N'101 Đường Điện Biên Phủ, Quận Ba Đình, Hà Nội', N'pham.tuan@gmail.com', N'đang theo học', '2020-03-03', 1, N'Phạm Minh Châu', '+84912345681', N'pupil4.png', N'Phạm Tuấn', '+84912345781', N'NV000010', N'NONE', N'S001', N'SC001'),
(N'HS000005', NULL, N'Văn Đức', N'Đỗ', N'202 Đường Tôn Đức Thắng, Quận Đống Đa, Hà Nội', N'do.minh@gmail.com', N'đang theo học', '2020-03-04', 1, N'Hoàng Thuỳ Dương', '+84912345682', N'pupil5.png', N'Đỗ Minh', '+84912345782', N'NV000010', N'NONE', N'S001', N'SC001'),
(N'HS000006', NULL, N'Thị Minh Châu', N'Hoàng', N'303 Đường Giảng Võ, Quận Ba Đình, Hà Nội', N'hoang.nam@gmail.com', N'đang theo học', '2020-03-05', 1, N'Vũ Kim Ngân', '+84912345683', N'pupil6.png', N'Hoàng Nam', '+84912345783', N'NV000010', N'NONE', N'S001', N'SC001'),
(N'HS000007', NULL, N'Thanh Tùng', N'Vũ', N'404 Đường Nguyễn Thái Học, Quận Ba Đình, Hà Nội', N'vu.anh@gmail.com', N'đang theo học', '2020-11-11', 0, N'Đặng Thu Thảo', '+84912345684', N'pupil7.png', N'Vũ Anh', '+84912345784', N'NV000010', N'NONE', N'S001', N'SC001'),
(N'HS000008', NULL, N'Phương Linh', N'Bùi', N'505 Đường Hai Bà Trưng, Quận Hoàn Kiếm, Hà Nội', N'bui.huy@gmail.com', N'đang theo học', '2021-02-07', 0, N'Bùi Quỳnh Chi', '+84912345685', N'pupil8.png', N'Bùi Huy', '+84912345785', N'NV000010', N'NONE', N'S001', N'SC001'),
(N'HS000009', NULL, N'Quốc Bảo', N'Đinh', N'606 Đường Phố Huế, Quận Hai Bà Trưng, Hà Nội', N'dinh.hung@gmail.com', N'đang theo học', '2021-02-08', 1, N'Đỗ Thanh Hằng', '+84912345686', N'pupil9.png', N'Đinh Hùng', '+84912345786', N'NV000010', N'NONE', N'S001', N'SC001'),
(N'HS000010', NULL, N'Thùy Dương', N'Đặng', N'707 Đường Lê Duẩn, Quận Hoàn Kiếm, Hà Nội', N'dang.trung@gmail.com', N'đang theo học', '2021-02-09', 1, N'Ngô Thanh Tâm', '+84912345687', N'pupil10.png', N'Đặng Trung', '+84912345787', N'NV000010', N'NONE', N'S001', N'SC001'),

(N'HS000011', NULL, N'Tuấn Anh', N'Ngô', N'808 Đường Trần Hưng Đạo, Quận Hoàn Kiếm, Hà Nội', N'ngo.duy@gmail.com', N'đang theo học', '2020-09-04', 0, N'Võ Thị Thảo', '+84912345688', N'pupil11.png', N'Ngô Duy', '+84912345788', N'NV000010', N'NONE', N'S002', N'SC002'),
(N'HS000012', NULL, N'Thị Kim Ngân', N'Dương', N'909 Đường Bà Triệu, Quận Hai Bà Trưng, Hà Nội', N'duong.thanh@gmail.com', N'đang theo học', '2020-09-05', 0, N'Nguyễn Thu Trang', '+84912345689', N'pupil12.png', N'Dương Thanh', '+84912345789', N'NV000010', N'NONE', N'S002', N'SC002'),
(N'HS000013', NULL, N'Tiến Đạt', N'Lý', N'110 Đường Phan Chu Trinh, Quận Hoàn Kiếm, Hà Nội', N'ly.hoang@gmail.com', N'đang theo học', '2020-09-06', 1, N'Trần Quỳnh Chi', '+84912345690', N'pupil13.png', N'Lý Hoàng', '+84912345790', N'NV000011', N'NONE', N'S002', N'SC002'),
(N'HS000014', NULL, N'Thuỳ Linh', N'Võ', N'211 Đường Lý Thường Kiệt, Quận Hoàn Kiếm, Hà Nội', N'vo.long@gmail.com', N'đang theo học', '2021-03-31', 1, N'Lê Huyền Trang', '+84912345691', N'pupil14.png', N'Võ Long', '+84912345791', N'NV000011', N'NONE', N'S002', N'SC002'),
(N'HS000015', NULL, N'Hữu Hùng', N'Hồ', N'312 Đường Trần Khát Chân, Quận Hai Bà Trưng, Hà Nội', N'ho.quoc@gmail.com', N'đang theo học', '2021-04-01', 0, N'Phạm Thanh Hồng', '+84912345692', N'pupil15.png', N'Hồ Quốc', '+84912345792', N'NV000011', N'NONE', N'S002', N'SC002'),
(N'HS000016', NULL, N'Quỳnh Chi', N'Châu', N'413 Đường Đại Cồ Việt, Quận Hai Bà Trưng, Hà Nội', N'chau.bao@gmail.com', N'đang theo học', '2021-11-09', 0, N'Trần Thu Thủy', '+84912345693', N'pupil16.png', N'Châu Bảo', '+84912345793', N'NV000011', N'NONE', N'S002', N'SC002'),
(N'HS000017', NULL, N'Minh Tuấn', N'Trịnh', N'514 Đường Trần Bình Trọng, Quận Hoàn Kiếm, Hà Nội', N'trinh.duc@gmail.com', N'đang theo học', '2021-11-10', 1, N'Nguyễn Quỳnh Trang', '+84912345694', N'pupil17.png', N'Trịnh Đức', '+84912345794', N'NV000011', N'NONE', N'S002', N'SC002'),
(N'HS000018', NULL, N'Phương Thảo', N'Cao', N'615 Đường Nguyễn Du, Quận Hai Bà Trưng, Hà Nội', N'cao.khanh@gmail.com', N'đang theo học', '2021-11-11', 0, N'Hoàng Thị Thùy Linh', '+84912345695', N'pupil18.png', N'Cao Khánh', '+84912345795', N'NV000011', N'NONE', N'S002', N'SC002'),
(N'HS000019', NULL, N'Thị Lan Hương', N'Phan', N'716 Đường Lê Đại Hành, Quận Hai Bà Trưng, Hà Nội', N'phan.viet@gmail.com', N'đang theo học', '2019-06-05', 0, N'Lê Thanh Bình', '+84912345696', N'pupil19.png', N'Phan Việt', '+84912345796', N'NV000012', N'NONE', N'S002', N'SC002'),
(N'HS000020', NULL, N'Thế Anh', N'Mai', N'817 Đường Phạm Ngọc Thạch, Quận Đống Đa, Hà Nội', N'mai.tri@gmail.com', N'đang theo học', '2019-06-06', 0, N'Nguyễn Thanh Bình', '+84912345697', N'pupil20.png', N'Mai Trí', '+84912345797', N'NV000012', N'NONE', N'S002', N'SC002'),
--Nhóm 2 
(N'HS000021', NULL, N'Ngọc Mai', N'Tô', N'918 Đường Tây Sơn, Quận Đống Đa, Hà Nội', N'to.phong@gmail.com', N'đang theo học', '2020-08-07', 1, N'Trần Thanh Thuỷ', '+84912345698', N'pupil21.png', N'Tô Phong', '+84912345798', N'NV000012', N'NONE', N'S003', N'SC003'),
(N'HS000022', NULL, N'Khánh Huyền', N'Đào', N'102 Đường Thái Hà, Quận Đống Đa, Hà Nội', N'dao.hai@gmail.com', N'đang theo học', '2020-08-08', 1, N'Phan Thị Hồng', '+84912345699', N'pupil22.png', N'Đào Hải', '+84912345799', N'NV000012', N'NONE', N'S003', N'SC003'),

-- Nhóm 3 (HS000023 - HS000030)
(N'HS000023', NULL, N'Anh Tú', N'Tạ', N'203 Đường Chùa Bộc, Quận Đống Đa, Hà Nội', N'ta.son@gmail.com', N'đang theo học', '2020-08-09', 1, N'Hoàng Thị Hồng Minh', '+84912345700', N'pupil23.png', N'Tạ Sơn', '+84912345800', N'NV000012', N'NONE', N'S003', N'SC003'),
(N'HS000024', NULL, N'Thị Thu Trang', N'Lưu', N'304 Đường Đê La Thành, Quận Đống Đa, Hà Nội', N'luu.tuan@gmail.com', N'đang theo học', '2020-08-10', 0, N'Nguyễn Hồng Nhung', '+84912345701', N'pupil24.png', N'Lưu Tuấn', '+84912345801', N'NV000012', N'NONE', N'S003', N'SC003'),
(N'HS000025', NULL, N'Nhật Minh', N'Tăng', N'405 Đường Hoàng Cầu, Quận Đống Đa, Hà Nội', N'tang.binh@gmail.com', N'đang theo học', '2020-08-11', 0, N'Trần Thị Thùy Linh', '+84912345702', N'pupil25.png', N'Tăng Bình', '+84912345802', N'NV000012', N'NONE', N'S003', N'SC003'),
(N'HS000026', NULL, N'Hải Đăng', N'Hà', N'506 Đường Khâm Thiên, Quận Đống Đa, Hà Nội', N'ha.minh@gmail.com', N'đang theo học', '2021-04-04', 1, N'Lê Bảo Ngọc', '+84912345703', N'pupil26.png', N'Hà Minh', '+84912345803', N'NV000012', N'NONE', N'S003', N'SC003'),
(N'HS000027', NULL, N'Hoài Nam', N'Từ', N'607 Đường Hào Nam, Quận Đống Đa, Hà Nội', N'tu.thang@gmail.com', N'đang theo học', '2021-04-05', 1, N'Phạm Thanh Bình', '+84912345704', N'pupil27.png', N'Từ Thắng', '+84912345804', N'NV000012', N'NONE', N'S003', N'SC003'),
(N'HS000028', NULL, N'Mỹ Linh', N'Bạch', N'708 Đường Nguyễn Lương Bằng, Quận Đống Đa, Hà Nội', N'bach.hai@gmail.com', N'đang theo học', '2019-10-01', 0, N'Lê Ngọc Quỳnh', '+84912345705', N'pupil28.png', N'Bạch Hải', '+84912345805', N'NV000014', N'NONE', N'S003', N'SC003'),
(N'HS000029', NULL, N'Khánh Linh', N'Triệu', N'809 Đường Tôn Thất Tùng, Quận Đống Đa, Hà Nội', N'trieu.dat@gmail.com', N'đang theo học', '2019-10-02', 0, N'Hoàng Hải Yến', '+84912345706', N'pupil29.png', N'Triệu Đạt', '+84912345806', N'NV000014', N'NONE', N'S003', N'SC003'),
(N'HS000030', NULL, N'Minh Tuấn', N'Hứa', N'910 Đường Trung Kính, Quận Cầu Giấy, Hà Nội', N'hua.tam@gmail.com', N'đang theo học', '2019-10-03', 0, N'Trần Mai Phương', '+84912345707', N'pupil30.png', N'Hứa Tâm', '+84912345807', N'NV000014', N'NONE', N'S003', N'SC003'),

-- Nhóm 4 (HS000031 - HS000040)
(N'HS000031', NULL, N'Gia Hân', N'Tôn', N'111 Đường Nguyễn Khánh Toàn, Quận Cầu Giấy, Hà Nội', N'ton.nhat@gmail.com', N'đang theo học', '2019-05-09', 0, N'Lê Minh Tâm', '+84912345708', N'pupil31.png', N'Tôn Nhật', '+84912345808', N'NV000014', N'NONE', N'S003', N'SC003'),
(N'HS000032', NULL, N'Thị Quỳnh', N'Văn', N'212 Đường Xuân Thủy, Quận Cầu Giấy, Hà Nội', N'van.an@gmail.com', N'đang theo học', '2019-05-10', 1, N'Nguyễn Thùy Dung', '+84912345709', N'pupil32.png', N'Văn An', '+84912345809', N'NV000014', N'NONE', N'S004', N'SC004'),
(N'HS000033', NULL, N'Phương Nga', N'Thái', N'313 Đường Cầu Giấy, Quận Cầu Giấy, Hà Nội', N'thai.hoa@gmail.com', N'đang theo học', '2021-06-29', 1, N'Phạm Ngọc Trâm', '+84912345710', N'pupil33.png', N'Thái Hoa', '+84912345810', N'NV000014', N'NONE', N'S004', N'SC004'),
(N'HS000034', NULL, N'Anh Khoa', N'Hàn', N'414 Đường Trần Duy Hưng, Quận Cầu Giấy, Hà Nội', N'han.viet@gmail.com', N'đang theo học', '2021-06-30', 0, N'Lê Thu Thủy', '+84912345711', N'pupil34.png', N'Hàn Việt', '+84912345811', N'NV000014', N'NONE', N'S004', N'SC004'),
(N'HS000035', NULL, N'Thị Thuý Vy', N'Quách', N'515 Đường Nguyễn Chánh, Quận Cầu Giấy, Hà Nội', N'quach.tung@gmail.com', N'đang theo học', '2021-07-01', 1, N'Vũ Thanh Hà', '+84912345712', N'pupil35.png', N'Quách Tùng', '+84912345812', N'NV000014', N'NONE', N'S004', N'SC004'),
(N'HS000036', NULL, N'Bảo Ngọc', N'Phùng', N'616 Đường Hoàng Quốc Việt, Quận Cầu Giấy, Hà Nội', N'phung.khai@gmail.com', N'đang theo học', '2021-07-02', 0, N'Trần Hồng Minh', '+84912345713', N'pupil36.png', N'Phùng Khải', '+84912345813', N'NV000014', N'NONE', N'S004', N'SC004'),
(N'HS000037', NULL, N'Thanh Tùng', N'Kiều', N'717 Đường Lê Văn Lương, Quận Thanh Xuân, Hà Nội', N'kieu.duc@gmail.com', N'đang theo học', '2021-07-03', 0, N'Nguyễn Hà', '+84912345714', N'pupil37.png', N'Kiều Đức', '+84912345814', N'NV000014', N'NONE', N'S004', N'SC004'),
(N'HS000038', NULL, N'Minh Phương', N'Ngọc', N'818 Đường Nguyễn Xiển, Quận Thanh Xuân, Hà Nội', N'ngoc.trong@gmail.com', N'đang theo học', '2021-02-09', 0, N'Trần Minh', '+84912345715', N'pupil38.png', N'Ngọc Trọng', '+84912345815', N'NV000014', N'NONE', N'S004', N'SC004'),
(N'HS000039', NULL, N'Đức Anh', N'Giang', N'919 Đường Khuất Duy Tiến, Quận Thanh Xuân, Hà Nội', N'giang.hai@gmail.com', N'đang theo học', '2021-02-10', 0, N'Lê Thanh', '+84912345716', N'pupil39.png', N'Giang Hải', '+84912345816', N'NV000014', N'NONE', N'S004', N'SC004'),
(N'HS000040', NULL, N'Ngọc Bích', N'Nguyễn', N'120 Đường Nguyễn Trãi, Quận Thanh Xuân, Hà Nội', N'nguyen.gia@gmail.com', N'đang theo học', '2021-02-11', 0, N'Hoàng Thu', '+84912345717', N'pupil40.png', N'Nguyễn Gia', '+84912345817', N'NV000014', N'NONE', N'S004', N'SC004'),

-- Nhóm 5 (HS000041 - HS000049)
(N'HS000041', NULL, N'Thị Hải Yến', N'Chu', N'221 Đường Vũ Trọng Phụng, Quận Thanh Xuân, Hà Nội', N'chu.thang@gmail.com', N'đang theo học', '2021-02-12', 1, N'Phan Huyền', '+84912345718', N'pupil41.png', N'Chu Thắng', '+84912345818', N'NV000014', N'NONE', N'S005', N'SC005'),
(N'HS000042', NULL, N'Hồng Phúc', N'Khương', N'322 Đường Lê Trọng Tấn, Quận Thanh Xuân, Hà Nội', N'khuong.an@gmail.com', N'đang theo học', '2021-02-13', 0, N'Vũ Kim', '+84912345719', N'pupil42.png', N'Khương An', '+84912345819', N'NV000014', N'NONE', N'S005', N'SC005'),
(N'HS000043', NULL, N'Hoàng Nam', N'Hùng', N'423 Đường Nguyễn Tuân, Quận Thanh Xuân, Hà Nội', N'hung.dai@gmail.com', N'đang theo học', '2021-02-14', 0, N'Phan Thúy', '+84912345720', N'pupil43.png', N'Hùng Đại', '+84912345820', N'NV000014', N'NONE', N'S005', N'SC005'),
(N'HS000044', NULL, N'Thị Bích Ngọc', N'Thân', N'524 Đường Trường Chinh, Quận Thanh Xuân, Hà Nội', N'than.tuan@gmail.com', N'đang theo học', '2021-02-15', 0, N'Hoàng Hương', '+84912345721', N'pupil44.png', N'Thân Tuấn', '+84912345821', N'NV000014', N'NONE', N'S005', N'SC005'),
(N'HS000045', NULL, N'Đình Dũng', N'Mạc', N'625 Đường Hoàng Đạo Thúy, Quận Thanh Xuân, Hà Nội', N'mac.van@gmail.com', N'đang theo học', '2021-02-16', 1, N'Đặng Thuý', '+84912345722', N'pupil45.png', N'Mạc Văn', '+84912345822', N'NV000014', N'NONE', N'S005', N'SC005'),
(N'HS000046', NULL, N'Mai Phương', N'Tiêu', N'726 Đường Nguyễn Quý Đức, Quận Thanh Xuân, Hà Nội', N'tieu.hung@gmail.com', N'đang theo học', '2021-10-31', 0, N'Nguyễn Ngân', '+84912345723', N'pupil46.png', N'Tiêu Hưng', '+84912345823', N'NV000014', N'NONE', N'S005', N'SC005'),
(N'HS000047', NULL, N'Anh Dũng', N'Trương', N'827 Đường Phùng Khoang, Quận Nam Từ Liêm, Hà Nội', N'truong.duy@gmail.com', N'đang theo học', '2021-11-01', 0, N'Trần Phương', '+84912345724', N'pupil47.png', N'Trương Duy', '+84912345824', N'NV000014', N'NONE', N'S005', N'SC005'),
(N'HS000048', NULL, N'Thị Minh Tâm', N'Lại', N'928 Đường Trần Hữu Dực, Quận Nam Từ Liêm, Hà Nội', N'lai.dang@gmail.com', N'đang theo học', '2021-11-02', 1, N'Lê Hoàng', '+84912345725', N'pupil48.png', N'Lại Đăng', '+84912345825', N'NV000014', N'NONE', N'S005', N'SC005'),
(N'HS000049', NULL, N'Thùy Dung', N'Kim', N'103 Đường Nguyễn Cơ Thạch, Quận Nam Từ Liêm, Hà Nội', N'kim.son@gmail.com', N'đang theo học', '2021-07-08', 1, N'Nguyễn Thảo', '+84912345726', N'pupil49.png', N'Kim Sơn', '+84912345826', N'NV000014', N'NONE', N'S006', N'SC006')

	INSERT INTO [SchoolYears] VALUES (N'SY000001',N'2025-2026','2025-09-05','2026-05-30',N'năm học 2025-2026',N'ad000001'),
	(N'SY000002',N'2026-2027','2026-09-04','2027-05-30',N'năm học 2026-2027',N'ad000001');


-- Tạo lại bảng Weeks với tuần học chính xác (7 ngày/tuần)

INSERT INTO [Weeks] (id, start_date, end_date, school_year_id) VALUES
(N'W000079', CAST('2025-06-02' AS DATE),CAST( '2025-06-08' AS DATE), 'SY000001'),
(N'W000080', CAST('2025-06-09' AS DATE),CAST( '2025-06-15' AS DATE), 'SY000001'),
(N'W000081', CAST('2025-06-16' AS DATE),CAST( '2025-06-22' AS DATE), 'SY000001'),
(N'W000082', CAST('2025-06-23' AS DATE),CAST( '2025-06-29' AS DATE), 'SY000001');

INSERT INTO [Weeks] VALUES
-- Năm học 2025-2026 (SY000001)

('W000001', CAST('2025-09-01' AS DATE), CAST('2025-09-07' AS DATE), 'SY000001'),
('W000002', CAST('2025-09-08' AS DATE), CAST('2025-09-14' AS DATE), 'SY000001'),
('W000003', CAST('2025-09-15' AS DATE), CAST('2025-09-21' AS DATE), 'SY000001'),
('W000004', CAST('2025-09-22' AS DATE), CAST('2025-09-28' AS DATE), 'SY000001'),
('W000005', CAST('2025-09-29' AS DATE), CAST('2025-10-05' AS DATE), 'SY000001'),
('W000006', CAST('2025-10-06' AS DATE), CAST('2025-10-12' AS DATE), 'SY000001'),
('W000007', CAST('2025-10-13' AS DATE), CAST('2025-10-19' AS DATE), 'SY000001'),
('W000008', CAST('2025-10-20' AS DATE), CAST('2025-10-26' AS DATE), 'SY000001'),
('W000009', CAST('2025-10-27' AS DATE), CAST('2025-11-02' AS DATE), 'SY000001'),
('W000010', CAST('2025-11-03' AS DATE), CAST('2025-11-09' AS DATE), 'SY000001'),
('W000011', CAST('2025-11-10' AS DATE), CAST('2025-11-16' AS DATE), 'SY000001'),
('W000012', CAST('2025-11-17' AS DATE), CAST('2025-11-23' AS DATE), 'SY000001'),
('W000013', CAST('2025-11-24' AS DATE), CAST('2025-11-30' AS DATE), 'SY000001'),
('W000014', CAST('2025-12-01' AS DATE), CAST('2025-12-07' AS DATE), 'SY000001'),
('W000015', CAST('2025-12-08' AS DATE), CAST('2025-12-14' AS DATE), 'SY000001'),
('W000016', CAST('2025-12-15' AS DATE), CAST('2025-12-21' AS DATE), 'SY000001'),
('W000017', CAST('2025-12-22' AS DATE), CAST('2025-12-28' AS DATE), 'SY000001'),
('W000018', CAST('2025-12-29' AS DATE), CAST('2026-01-04' AS DATE), 'SY000001'),
('W000019', CAST('2026-01-05' AS DATE), CAST('2026-01-11' AS DATE), 'SY000001'),
('W000020', CAST('2026-01-12' AS DATE), CAST('2026-01-18' AS DATE), 'SY000001'),
('W000021', CAST('2026-01-19' AS DATE), CAST('2026-01-25' AS DATE), 'SY000001'),
('W000022', CAST('2026-01-26' AS DATE), CAST('2026-02-01' AS DATE), 'SY000001'),
('W000023', CAST('2026-02-02' AS DATE), CAST('2026-02-08' AS DATE), 'SY000001'),
('W000024', CAST('2026-02-09' AS DATE), CAST('2026-02-15' AS DATE), 'SY000001'),
('W000025', CAST('2026-02-16' AS DATE), CAST('2026-02-22' AS DATE), 'SY000001'),
('W000026', CAST('2026-02-23' AS DATE), CAST('2026-02-28' AS DATE), 'SY000001'),
('W000027', CAST('2026-03-01' AS DATE), CAST('2026-03-07' AS DATE), 'SY000001'),
('W000028', CAST('2026-03-08' AS DATE), CAST('2026-03-14' AS DATE), 'SY000001'),
('W000029', CAST('2026-03-15' AS DATE), CAST('2026-03-21' AS DATE), 'SY000001'),
('W000030', CAST('2026-03-22' AS DATE), CAST('2026-03-28' AS DATE), 'SY000001'),
('W000031', CAST('2026-03-29' AS DATE), CAST('2026-04-04' AS DATE), 'SY000001'),
('W000032', CAST('2026-04-05' AS DATE), CAST('2026-04-11' AS DATE), 'SY000001'),
('W000033', CAST('2026-04-12' AS DATE), CAST('2026-04-18' AS DATE), 'SY000001'),
('W000034', CAST('2026-04-19' AS DATE), CAST('2026-04-25' AS DATE), 'SY000001'),
('W000035', CAST('2026-04-26' AS DATE), CAST('2026-05-02' AS DATE), 'SY000001'),
('W000036', CAST('2026-05-03' AS DATE), CAST('2026-05-09' AS DATE), 'SY000001'),
('W000037', CAST('2026-05-10' AS DATE), CAST('2026-05-16' AS DATE), 'SY000001'),
('W000038', CAST('2026-05-17' AS DATE), CAST('2026-05-23' AS DATE), 'SY000001'),
('W000039', CAST('2026-05-24' AS DATE), CAST('2026-05-30' AS DATE), 'SY000001'),

-- Năm học 2026-2027 (SY000002)
('W000040', CAST('2026-08-30' AS DATE), CAST('2026-09-05' AS DATE), 'SY000002'),
('W000041', CAST('2026-09-06' AS DATE), CAST('2026-09-12' AS DATE), 'SY000002'),
('W000042', CAST('2026-09-13' AS DATE), CAST('2026-09-19' AS DATE), 'SY000002'),
('W000043', CAST('2026-09-20' AS DATE), CAST('2026-09-26' AS DATE), 'SY000002'),
('W000044', CAST('2026-09-27' AS DATE), CAST('2026-10-03' AS DATE), 'SY000002'),
('W000045', CAST('2026-10-04' AS DATE), CAST('2026-10-10' AS DATE), 'SY000002'),
('W000046', CAST('2026-10-11' AS DATE), CAST('2026-10-17' AS DATE), 'SY000002'),
('W000047', CAST('2026-10-18' AS DATE), CAST('2026-10-24' AS DATE), 'SY000002'),
('W000048', CAST('2026-10-25' AS DATE), CAST('2026-10-31' AS DATE), 'SY000002'),
('W000049', CAST('2026-11-01' AS DATE), CAST('2026-11-07' AS DATE), 'SY000002'),
('W000050', CAST('2026-11-08' AS DATE), CAST('2026-11-14' AS DATE), 'SY000002'),
('W000051', CAST('2026-11-15' AS DATE), CAST('2026-11-21' AS DATE), 'SY000002'),
('W000052', CAST('2026-11-22' AS DATE), CAST('2026-11-28' AS DATE), 'SY000002'),
('W000053', CAST('2026-11-29' AS DATE), CAST('2026-12-05' AS DATE), 'SY000002'),
('W000054', CAST('2026-12-06' AS DATE), CAST('2026-12-12' AS DATE), 'SY000002'),
('W000055', CAST('2026-12-13' AS DATE), CAST('2026-12-19' AS DATE), 'SY000002'),
('W000056', CAST('2026-12-20' AS DATE), CAST('2026-12-26' AS DATE), 'SY000002'),
('W000057', CAST('2026-12-27' AS DATE), CAST('2027-01-02' AS DATE), 'SY000002'),
('W000058', CAST('2027-01-03' AS DATE), CAST('2027-01-09' AS DATE), 'SY000002'),
('W000059', CAST('2027-01-10' AS DATE), CAST('2027-01-16' AS DATE), 'SY000002'),
('W000060', CAST('2027-01-17' AS DATE), CAST('2027-01-23' AS DATE), 'SY000002'),
('W000061', CAST('2027-01-24' AS DATE), CAST('2027-01-30' AS DATE), 'SY000002'),
('W000062', CAST('2027-01-31' AS DATE), CAST('2027-02-06' AS DATE), 'SY000002'),
('W000063', CAST('2027-02-07' AS DATE), CAST('2027-02-13' AS DATE), 'SY000002'),
('W000064', CAST('2027-02-14' AS DATE), CAST('2027-02-20' AS DATE), 'SY000002'),
('W000065', CAST('2027-02-21' AS DATE), CAST('2027-02-27' AS DATE), 'SY000002'),
('W000066', CAST('2027-02-28' AS DATE), CAST('2027-03-06' AS DATE), 'SY000002'),
('W000067', CAST('2027-03-07' AS DATE), CAST('2027-03-13' AS DATE), 'SY000002'),
('W000068', CAST('2027-03-14' AS DATE), CAST('2027-03-20' AS DATE), 'SY000002'),
('W000069', CAST('2027-03-21' AS DATE), CAST('2027-03-27' AS DATE), 'SY000002'),
('W000070', CAST('2027-03-28' AS DATE), CAST('2027-04-03' AS DATE), 'SY000002'),
('W000071', CAST('2027-04-04' AS DATE), CAST('2027-04-10' AS DATE), 'SY000002'),
('W000072', CAST('2027-04-11' AS DATE), CAST('2027-04-17' AS DATE), 'SY000002'),
('W000073', CAST('2027-04-18' AS DATE), CAST('2027-04-24' AS DATE), 'SY000002'),
('W000074', CAST('2027-04-25' AS DATE), CAST('2027-05-01' AS DATE), 'SY000002'),
('W000075', CAST('2027-05-02' AS DATE), CAST('2027-05-08' AS DATE), 'SY000002'),
('W000076', CAST('2027-05-09' AS DATE), CAST('2027-05-15' AS DATE), 'SY000002'),
('W000077', CAST('2027-05-16' AS DATE), CAST('2027-05-22' AS DATE), 'SY000002'),
('W000078', CAST('2027-05-23' AS DATE), CAST('2027-05-29' AS DATE), 'SY000002');


---Thêm mới
INSERT INTO [Days] (id, week_id, date, day_of_week) VALUES
(N'D000543', N'W000079', '2025-06-02', 1),
(N'D000544', N'W000079', '2025-06-03', 2),
(N'D000545', N'W000079', '2025-06-04', 3),
(N'D000546', N'W000079', '2025-06-05', 4),
(N'D000547', N'W000079', '2025-06-06', 5),
(N'D000548', N'W000079', '2025-06-07', 6),
(N'D000549', N'W000079', '2025-06-08', 7);

INSERT INTO [Days] (id, week_id, date, day_of_week) VALUES
(N'D000550', N'W000080', '2025-06-09', 1),
(N'D000551', N'W000080', '2025-06-10', 2),
(N'D000552', N'W000080', '2025-06-11', 3),
(N'D000553', N'W000080', '2025-06-12', 4),
(N'D000554', N'W000080', '2025-06-13', 5),
(N'D000555', N'W000080', '2025-06-14', 6),
(N'D000556', N'W000080', '2025-06-15', 7),
(N'D000557', N'W000081', '2025-06-16', 1),
(N'D000558', N'W000081', '2025-06-17', 2),
(N'D000559', N'W000081', '2025-06-18', 3),
(N'D000560', N'W000081', '2025-06-19', 4),
(N'D000561', N'W000081', '2025-06-20', 5),
(N'D000562', N'W000081', '2025-06-21', 6),
(N'D000563', N'W000081', '2025-06-22', 7),
(N'D000564', N'W000082', '2025-06-23', 1),
(N'D000565', N'W000082', '2025-06-24', 2),
(N'D000566', N'W000082', '2025-06-25', 3),
(N'D000567', N'W000082', '2025-06-26', 4),
(N'D000568', N'W000082', '2025-06-27', 5),
(N'D000569', N'W000082', '2025-06-28', 6),
(N'D000570', N'W000082', '2025-06-29', 7);



-- Chèn dữ liệu mới với week_id chính xác
INSERT INTO [Days] VALUES 

-- Tuần 1 (W000001: 2025-09-01 đến 2025-09-07)
(N'D000001',N'W000001','2025-09-05',5),
(N'D000002',N'W000001','2025-09-06',6),
(N'D000003',N'W000001','2025-09-07',7),

-- Tuần 2 (W000002: 2025-09-08 đến 2025-09-14)
(N'D000004',N'W000002','2025-09-08',1),
(N'D000005',N'W000002','2025-09-09',2),
(N'D000006',N'W000002','2025-09-10',3),
(N'D000007',N'W000002','2025-09-11',4),
(N'D000008',N'W000002','2025-09-12',5),
(N'D000009',N'W000002','2025-09-13',6),
(N'D000010',N'W000002','2025-09-14',7),

-- Tuần 3 (W000003: 2025-09-15 đến 2025-09-21)
(N'D000011',N'W000003','2025-09-15',1),
(N'D000012',N'W000003','2025-09-16',2),
(N'D000013',N'W000003','2025-09-17',3),
(N'D000014',N'W000003','2025-09-18',4),
(N'D000015',N'W000003','2025-09-19',5),
(N'D000016',N'W000003','2025-09-20',6),
(N'D000017',N'W000003','2025-09-21',7),

-- Tuần 4 (W000004: 2025-09-22 đến 2025-09-28)
(N'D000018',N'W000004','2025-09-22',1),
(N'D000019',N'W000004','2025-09-23',2),
(N'D000020',N'W000004','2025-09-24',3),
(N'D000021',N'W000004','2025-09-25',4),
(N'D000022',N'W000004','2025-09-26',5),
(N'D000023',N'W000004','2025-09-27',6),
(N'D000024',N'W000004','2025-09-28',7),

-- Tuần 5 (W000005: 2025-09-29 đến 2025-10-05)
(N'D000025',N'W000005','2025-09-29',1),
(N'D000026',N'W000005','2025-09-30',2),
(N'D000027',N'W000005','2025-10-01',3),
(N'D000028',N'W000005','2025-10-02',4),
(N'D000029',N'W000005','2025-10-03',5),
(N'D000030',N'W000005','2025-10-04',6),
(N'D000031',N'W000005','2025-10-05',7),

-- Tuần 6 (W000006: 2025-10-06 đến 2025-10-12)
(N'D000032',N'W000006','2025-10-06',1),
(N'D000033',N'W000006','2025-10-07',2),
(N'D000034',N'W000006','2025-10-08',3),
(N'D000035',N'W000006','2025-10-09',4),
(N'D000036',N'W000006','2025-10-10',5),
(N'D000037',N'W000006','2025-10-11',6),
(N'D000038',N'W000006','2025-10-12',7),

-- Tuần 7 (W000007: 2025-10-13 đến 2025-10-19)
(N'D000039',N'W000007','2025-10-13',1),
(N'D000040',N'W000007','2025-10-14',2),
(N'D000041',N'W000007','2025-10-15',3),
(N'D000042',N'W000007','2025-10-16',4),
(N'D000043',N'W000007','2025-10-17',5),
(N'D000044',N'W000007','2025-10-18',6),
(N'D000045',N'W000007','2025-10-19',7),

-- Tuần 8 (W000008: 2025-10-20 đến 2025-10-26)
(N'D000046',N'W000008','2025-10-20',1),
(N'D000047',N'W000008','2025-10-21',2),
(N'D000048',N'W000008','2025-10-22',3),
(N'D000049',N'W000008','2025-10-23',4),
(N'D000050',N'W000008','2025-10-24',5),
(N'D000051',N'W000008','2025-10-25',6),
(N'D000052',N'W000008','2025-10-26',7),

-- Tuần 9 (W000009: 2025-10-27 đến 2025-11-02)
(N'D000053',N'W000009','2025-10-27',1),
(N'D000054',N'W000009','2025-10-28',2),
(N'D000055',N'W000009','2025-10-29',3),
(N'D000056',N'W000009','2025-10-30',4),
(N'D000057',N'W000009','2025-10-31',5),
(N'D000058',N'W000009','2025-11-01',6),
(N'D000059',N'W000009','2025-11-02',7),

-- Tuần 10 (W000010: 2025-11-03 đến 2025-11-09)
(N'D000060',N'W000010','2025-11-03',1),
(N'D000061',N'W000010','2025-11-04',2),
(N'D000062',N'W000010','2025-11-05',3),
(N'D000063',N'W000010','2025-11-06',4),
(N'D000064',N'W000010','2025-11-07',5),
(N'D000065',N'W000010','2025-11-08',6),
(N'D000066',N'W000010','2025-11-09',7),

-- Tuần 11 (W000011: 2025-11-10 đến 2025-11-16)
(N'D000067',N'W000011','2025-11-10',1),
(N'D000068',N'W000011','2025-11-11',2),
(N'D000069',N'W000011','2025-11-12',3),
(N'D000070',N'W000011','2025-11-13',4),
(N'D000071',N'W000011','2025-11-14',5),
(N'D000072',N'W000011','2025-11-15',6),
(N'D000073',N'W000011','2025-11-16',7),

-- Tuần 12 (W000012: 2025-11-17 đến 2025-11-23)
(N'D000074',N'W000012','2025-11-17',1),
(N'D000075',N'W000012','2025-11-18',2),
(N'D000076',N'W000012','2025-11-19',3),
(N'D000077',N'W000012','2025-11-20',4),
(N'D000078',N'W000012','2025-11-21',5),
(N'D000079',N'W000012','2025-11-22',6),
(N'D000080',N'W000012','2025-11-23',7),

-- Tuần 13 (W000013: 2025-11-24 đến 2025-11-30)
(N'D000081',N'W000013','2025-11-24',1),
(N'D000082',N'W000013','2025-11-25',2),
(N'D000083',N'W000013','2025-11-26',3),
(N'D000084',N'W000013','2025-11-27',4),
(N'D000085',N'W000013','2025-11-28',5),
(N'D000086',N'W000013','2025-11-29',6),
(N'D000087',N'W000013','2025-11-30',7),

-- Tuần 14 (W000014: 2025-12-01 đến 2025-12-07)
(N'D000088',N'W000014','2025-12-01',1),
(N'D000089',N'W000014','2025-12-02',2),
(N'D000090',N'W000014','2025-12-03',3),
(N'D000091',N'W000014','2025-12-04',4),
(N'D000092',N'W000014','2025-12-05',5),
(N'D000093',N'W000014','2025-12-06',6),
(N'D000094',N'W000014','2025-12-07',7),

-- Tuần 15 (W000015: 2025-12-08 đến 2025-12-14)
(N'D000095',N'W000015','2025-12-08',1),
(N'D000096',N'W000015','2025-12-09',2),
(N'D000097',N'W000015','2025-12-10',3),
(N'D000098',N'W000015','2025-12-11',4),
(N'D000099',N'W000015','2025-12-12',5),
(N'D000100',N'W000015','2025-12-13',6),
(N'D000101',N'W000015','2025-12-14',7),

-- Tuần 16 (W000016: 2025-12-15 đến 2025-12-21)
(N'D000102',N'W000016','2025-12-15',1),
(N'D000103',N'W000016','2025-12-16',2),
(N'D000104',N'W000016','2025-12-17',3),
(N'D000105',N'W000016','2025-12-18',4),
(N'D000106',N'W000016','2025-12-19',5),
(N'D000107',N'W000016','2025-12-20',6),
(N'D000108',N'W000016','2025-12-21',7),

-- Tuần 17 (W000017: 2025-12-22 đến 2025-12-28)
(N'D000109',N'W000017','2025-12-22',1),
(N'D000110',N'W000017','2025-12-23',2),
(N'D000111',N'W000017','2025-12-24',3),
(N'D000112',N'W000017','2025-12-25',4),
(N'D000113',N'W000017','2025-12-26',5),
(N'D000114',N'W000017','2025-12-27',6),
(N'D000115',N'W000017','2025-12-28',7),

-- Tuần 18 (W000018: 2025-12-29 đến 2026-01-04)
(N'D000116',N'W000018','2025-12-29',1),
(N'D000117',N'W000018','2025-12-30',2),
(N'D000118',N'W000018','2025-12-31',3),
(N'D000119',N'W000018','2026-01-01',4),
(N'D000120',N'W000018','2026-01-02',5),
(N'D000121',N'W000018','2026-01-03',6),
(N'D000122',N'W000018','2026-01-04',7),

-- Tuần 19 (W000019: 2026-01-05 đến 2026-01-11)
(N'D000123',N'W000019','2026-01-05',1),
(N'D000124',N'W000019','2026-01-06',2),
(N'D000125',N'W000019','2026-01-07',3),
(N'D000126',N'W000019','2026-01-08',4),
(N'D000127',N'W000019','2026-01-09',5),
(N'D000128',N'W000019','2026-01-10',6),
(N'D000129',N'W000019','2026-01-11',7);

-- Tuần 20 (W000020: 2026-01-12 đến 2026-01-18)
INSERT INTO [Days] VALUES 
(N'D000130',N'W000020','2026-01-12',1),
(N'D000131',N'W000020','2026-01-13',2),
(N'D000132',N'W000020','2026-01-14',3),
(N'D000133',N'W000020','2026-01-15',4),
(N'D000134',N'W000020','2026-01-16',5),
(N'D000135',N'W000020','2026-01-17',6),
(N'D000136',N'W000020','2026-01-18',7),

-- Tuần 21 (W000021: 2026-01-19 đến 2026-01-25)
(N'D000137',N'W000021','2026-01-19',1),
(N'D000138',N'W000021','2026-01-20',2),
(N'D000139',N'W000021','2026-01-21',3),
(N'D000140',N'W000021','2026-01-22',4),
(N'D000141',N'W000021','2026-01-23',5),
(N'D000142',N'W000021','2026-01-24',6),
(N'D000143',N'W000021','2026-01-25',7),

-- Tuần 22 (W000022: 2026-01-26 đến 2026-02-01)
(N'D000144',N'W000022','2026-01-26',1),
(N'D000145',N'W000022','2026-01-27',2),
(N'D000146',N'W000022','2026-01-28',3),
(N'D000147',N'W000022','2026-01-29',4),
(N'D000148',N'W000022','2026-01-30',5),
(N'D000149',N'W000022','2026-01-31',6),
(N'D000150',N'W000022','2026-02-01',7),

-- Tuần 23 (W000023: 2026-02-02 đến 2026-02-08)
(N'D000151',N'W000023','2026-02-02',1),
(N'D000152',N'W000023','2026-02-03',2),
(N'D000153',N'W000023','2026-02-04',3),
(N'D000154',N'W000023','2026-02-05',4),
(N'D000155',N'W000023','2026-02-06',5),
(N'D000156',N'W000023','2026-02-07',6),
(N'D000157',N'W000023','2026-02-08',7),

-- Tuần 24 (W000024: 2026-02-09 đến 2026-02-15)
(N'D000158',N'W000024','2026-02-09',1),
(N'D000159',N'W000024','2026-02-10',2),
(N'D000160',N'W000024','2026-02-11',3),
(N'D000161',N'W000024','2026-02-12',4),
(N'D000162',N'W000024','2026-02-13',5),
(N'D000163',N'W000024','2026-02-14',6),
(N'D000164',N'W000024','2026-02-15',7),

-- Tuần 25 (W000025: 2026-02-16 đến 2026-02-22)
(N'D000165',N'W000025','2026-02-16',1),
(N'D000166',N'W000025','2026-02-17',2),
(N'D000167',N'W000025','2026-02-18',3),
(N'D000168',N'W000025','2026-02-19',4),
(N'D000169',N'W000025','2026-02-20',5),
(N'D000170',N'W000025','2026-02-21',6),
(N'D000171',N'W000025','2026-02-22',7),

-- Tuần 26 (W000026: 2026-02-23 đến 2026-02-29)
(N'D000172',N'W000026','2026-02-23',1),
(N'D000173',N'W000026','2026-02-24',2),
(N'D000174',N'W000026','2026-02-25',3),
(N'D000175',N'W000026','2026-02-26',4),
(N'D000176',N'W000026','2026-02-27',5),
(N'D000177',N'W000026','2026-02-28',6),


-- Tuần 27 (W000027: 2026-03-01 đến 2026-03-07)
(N'D000179',N'W000027','2026-03-01',1),
(N'D000180',N'W000027','2026-03-02',2),
(N'D000181',N'W000027','2026-03-03',3),
(N'D000182',N'W000027','2026-03-04',4),
(N'D000183',N'W000027','2026-03-05',5),
(N'D000184',N'W000027','2026-03-06',6),
(N'D000185',N'W000027','2026-03-07',7),

-- Tuần 28 (W000028: 2026-03-08 đến 2026-03-14)
(N'D000186',N'W000028','2026-03-08',1),
(N'D000187',N'W000028','2026-03-09',2),
(N'D000188',N'W000028','2026-03-10',3),
(N'D000189',N'W000028','2026-03-11',4),
(N'D000190',N'W000028','2026-03-12',5),
(N'D000191',N'W000028','2026-03-13',6),
(N'D000192',N'W000028','2026-03-14',7),

-- Tuần 29 (W000029: 2026-03-15 đến 2026-03-21)
(N'D000193',N'W000029','2026-03-15',1),
(N'D000194',N'W000029','2026-03-16',2),
(N'D000195',N'W000029','2026-03-17',3),
(N'D000196',N'W000029','2026-03-18',4),
(N'D000197',N'W000029','2026-03-19',5),
(N'D000198',N'W000029','2026-03-20',6),
(N'D000199',N'W000029','2026-03-21',7),

-- Tuần 30 (W000030: 2026-03-22 đến 2026-03-28)
(N'D000200',N'W000030','2026-03-22',1),
(N'D000201',N'W000030','2026-03-23',2),
(N'D000202',N'W000030','2026-03-24',3),
(N'D000203',N'W000030','2026-03-25',4),
(N'D000204',N'W000030','2026-03-26',5),
(N'D000205',N'W000030','2026-03-27',6),
(N'D000206',N'W000030','2026-03-28',7),

-- Tuần 31 (W000031: 2026-03-29 đến 2026-04-04)
(N'D000207',N'W000031','2026-03-29',1),
(N'D000208',N'W000031','2026-03-30',2),
(N'D000209',N'W000031','2026-03-31',3),
(N'D000210',N'W000031','2026-04-01',4),
(N'D000211',N'W000031','2026-04-02',5),
(N'D000212',N'W000031','2026-04-03',6),
(N'D000213',N'W000031','2026-04-04',7),

-- Tuần 32 (W000032: 2026-04-05 đến 2026-04-11)
(N'D000214',N'W000032','2026-04-05',1),
(N'D000215',N'W000032','2026-04-06',2),
(N'D000216',N'W000032','2026-04-07',3),
(N'D000217',N'W000032','2026-04-08',4),
(N'D000218',N'W000032','2026-04-09',5),
(N'D000219',N'W000032','2026-04-10',6),
(N'D000220',N'W000032','2026-04-11',7),

-- Tuần 33 (W000033: 2026-04-12 đến 2026-04-18)
(N'D000221',N'W000033','2026-04-12',1),
(N'D000222',N'W000033','2026-04-13',2),
(N'D000223',N'W000033','2026-04-14',3),
(N'D000224',N'W000033','2026-04-15',4),
(N'D000225',N'W000033','2026-04-16',5),
(N'D000226',N'W000033','2026-04-17',6),
(N'D000227',N'W000033','2026-04-18',7),

-- Tuần 34 (W000034: 2026-04-19 đến 2026-04-25)
(N'D000228',N'W000034','2026-04-19',1),
(N'D000229',N'W000034','2026-04-20',2),
(N'D000230',N'W000034','2026-04-21',3),
(N'D000231',N'W000034','2026-04-22',4),
(N'D000232',N'W000034','2026-04-23',5),
(N'D000233',N'W000034','2026-04-24',6),
(N'D000234',N'W000034','2026-04-25',7),

-- Tuần 35 (W000035: 2026-04-26 đến 2026-05-02)
(N'D000235',N'W000035','2026-04-26',1),
(N'D000236',N'W000035','2026-04-27',2),
(N'D000237',N'W000035','2026-04-28',3),
(N'D000238',N'W000035','2026-04-29',4),
(N'D000239',N'W000035','2026-04-30',5),
(N'D000240',N'W000035','2026-05-01',6),
(N'D000241',N'W000035','2026-05-02',7),

-- Tuần 36 (W000036: 2026-05-03 đến 2026-05-09)
(N'D000242',N'W000036','2026-05-03',1),
(N'D000243',N'W000036','2026-05-04',2),
(N'D000244',N'W000036','2026-05-05',3),
(N'D000245',N'W000036','2026-05-06',4),
(N'D000246',N'W000036','2026-05-07',5),
(N'D000247',N'W000036','2026-05-08',6),
(N'D000248',N'W000036','2026-05-09',7),

-- Tuần 37 (W000037: 2026-05-10 đến 2026-05-16)
(N'D000249',N'W000037','2026-05-10',1),
(N'D000250',N'W000037','2026-05-11',2),
(N'D000251',N'W000037','2026-05-12',3),
(N'D000252',N'W000037','2026-05-13',4),
(N'D000253',N'W000037','2026-05-14',5),
(N'D000254',N'W000037','2026-05-15',6),
(N'D000255',N'W000037','2026-05-16',7);
-- Tiếp tục chèn dữ liệu từ tuần 38 (2025-2026) đến hết năm học 2026-2027
INSERT INTO [Days] VALUES 
-- Tuần 38 (W000038: 2026-05-17 đến 2026-05-23)
(N'D000256',N'W000038','2026-05-17',1),
(N'D000257',N'W000038','2026-05-18',2),
(N'D000258',N'W000038','2026-05-19',3),
(N'D000259',N'W000038','2026-05-20',4),
(N'D000260',N'W000038','2026-05-21',5),
(N'D000261',N'W000038','2026-05-22',6),
(N'D000262',N'W000038','2026-05-23',7),

-- Tuần 39 (W000039: 2026-05-24 đến 2026-05-30) - Kết thúc năm học SY000001
(N'D000263',N'W000039','2026-05-24',1),
(N'D000264',N'W000039','2026-05-25',2),
(N'D000265',N'W000039','2026-05-26',3),
(N'D000266',N'W000039','2026-05-27',4),
(N'D000267',N'W000039','2026-05-28',5),
(N'D000268',N'W000039','2026-05-29',6),
(N'D000269',N'W000039','2026-05-30',7),

-- NĂM HỌC 2026-2027 (SY000002) -------------------------------------------------

-- Tuần 40 (W000040: 2026-08-30 đến 2026-09-05)
(N'D000270',N'W000040','2026-08-30',1),
(N'D000271',N'W000040','2026-08-31',2),
(N'D000272',N'W000040','2026-09-01',3),
(N'D000273',N'W000040','2026-09-02',4),
(N'D000274',N'W000040','2026-09-03',5),
(N'D000275',N'W000040','2026-09-04',6),
(N'D000276',N'W000040','2026-09-05',7),

-- Tuần 41 (W000041: 2026-09-06 đến 2026-09-12)
(N'D000277',N'W000041','2026-09-06',1),
(N'D000278',N'W000041','2026-09-07',2),
(N'D000279',N'W000041','2026-09-08',3),
(N'D000280',N'W000041','2026-09-09',4),
(N'D000281',N'W000041','2026-09-10',5),
(N'D000282',N'W000041','2026-09-11',6),
(N'D000283',N'W000041','2026-09-12',7),

-- Tuần 42 (W000042: 2026-09-13 đến 2026-09-19)
(N'D000284',N'W000042','2026-09-13',1),
(N'D000285',N'W000042','2026-09-14',2),
(N'D000286',N'W000042','2026-09-15',3),
(N'D000287',N'W000042','2026-09-16',4),
(N'D000288',N'W000042','2026-09-17',5),
(N'D000289',N'W000042','2026-09-18',6),
(N'D000290',N'W000042','2026-09-19',7),

-- Tuần 43 (W000043: 2026-09-20 đến 2026-09-26)
(N'D000291',N'W000043','2026-09-20',1),
(N'D000292',N'W000043','2026-09-21',2),
(N'D000293',N'W000043','2026-09-22',3),
(N'D000294',N'W000043','2026-09-23',4),
(N'D000295',N'W000043','2026-09-24',5),
(N'D000296',N'W000043','2026-09-25',6),
(N'D000297',N'W000043','2026-09-26',7),

-- Tuần 44 (W000044: 2026-09-27 đến 2026-10-03)
(N'D000298',N'W000044','2026-09-27',1),
(N'D000299',N'W000044','2026-09-28',2),
(N'D000300',N'W000044','2026-09-29',3),
(N'D000301',N'W000044','2026-09-30',4),
(N'D000302',N'W000044','2026-10-01',5),
(N'D000303',N'W000044','2026-10-02',6),
(N'D000304',N'W000044','2026-10-03',7),

-- Tuần 45 (W000045: 2026-10-04 đến 2026-10-10)
(N'D000305',N'W000045','2026-10-04',1),
(N'D000306',N'W000045','2026-10-05',2),
(N'D000307',N'W000045','2026-10-06',3),
(N'D000308',N'W000045','2026-10-07',4),
(N'D000309',N'W000045','2026-10-08',5),
(N'D000310',N'W000045','2026-10-09',6),
(N'D000311',N'W000045','2026-10-10',7),

-- Tuần 46 (W000046: 2026-10-11 đến 2026-10-17)
(N'D000312',N'W000046','2026-10-11',1),
(N'D000313',N'W000046','2026-10-12',2),
(N'D000314',N'W000046','2026-10-13',3),
(N'D000315',N'W000046','2026-10-14',4),
(N'D000316',N'W000046','2026-10-15',5),
(N'D000317',N'W000046','2026-10-16',6),
(N'D000318',N'W000046','2026-10-17',7),

-- Tuần 47 (W000047: 2026-10-18 đến 2026-10-24)
(N'D000319',N'W000047','2026-10-18',1),
(N'D000320',N'W000047','2026-10-19',2),
(N'D000321',N'W000047','2026-10-20',3),
(N'D000322',N'W000047','2026-10-21',4),
(N'D000323',N'W000047','2026-10-22',5),
(N'D000324',N'W000047','2026-10-23',6),
(N'D000325',N'W000047','2026-10-24',7),

-- Tuần 48 (W000048: 2026-10-25 đến 2026-10-31)
(N'D000326',N'W000048','2026-10-25',1),
(N'D000327',N'W000048','2026-10-26',2),
(N'D000328',N'W000048','2026-10-27',3),
(N'D000329',N'W000048','2026-10-28',4),
(N'D000330',N'W000048','2026-10-29',5),
(N'D000331',N'W000048','2026-10-30',6),
(N'D000332',N'W000048','2026-10-31',7),

-- Tuần 49 (W000049: 2026-11-01 đến 2026-11-07)
(N'D000333',N'W000049','2026-11-01',1),
(N'D000334',N'W000049','2026-11-02',2),
(N'D000335',N'W000049','2026-11-03',3),
(N'D000336',N'W000049','2026-11-04',4),
(N'D000337',N'W000049','2026-11-05',5),
(N'D000338',N'W000049','2026-11-06',6),
(N'D000339',N'W000049','2026-11-07',7),

-- Tuần 50 (W000050: 2026-11-08 đến 2026-11-14)
(N'D000340',N'W000050','2026-11-08',1),
(N'D000341',N'W000050','2026-11-09',2),
(N'D000342',N'W000050','2026-11-10',3),
(N'D000343',N'W000050','2026-11-11',4),
(N'D000344',N'W000050','2026-11-12',5),
(N'D000345',N'W000050','2026-11-13',6),
(N'D000346',N'W000050','2026-11-14',7),

-- Tuần 51 (W000051: 2026-11-15 đến 2026-11-21)
(N'D000347',N'W000051','2026-11-15',1),
(N'D000348',N'W000051','2026-11-16',2),
(N'D000349',N'W000051','2026-11-17',3),
(N'D000350',N'W000051','2026-11-18',4),
(N'D000351',N'W000051','2026-11-19',5),
(N'D000352',N'W000051','2026-11-20',6),
(N'D000353',N'W000051','2026-11-21',7),

-- Tuần 52 (W000052: 2026-11-22 đến 2026-11-28)
(N'D000354',N'W000052','2026-11-22',1),
(N'D000355',N'W000052','2026-11-23',2),
(N'D000356',N'W000052','2026-11-24',3),
(N'D000357',N'W000052','2026-11-25',4),
(N'D000358',N'W000052','2026-11-26',5),
(N'D000359',N'W000052','2026-11-27',6),
(N'D000360',N'W000052','2026-11-28',7),

-- Tuần 53 (W000053: 2026-11-29 đến 2026-12-05)
(N'D000361',N'W000053','2026-11-29',1),
(N'D000362',N'W000053','2026-11-30',2),
(N'D000363',N'W000053','2026-12-01',3),
(N'D000364',N'W000053','2026-12-02',4),
(N'D000365',N'W000053','2026-12-03',5),
(N'D000366',N'W000053','2026-12-04',6),
(N'D000367',N'W000053','2026-12-05',7),

-- Tuần 54 (W000054: 2026-12-06 đến 2026-12-12)
(N'D000368',N'W000054','2026-12-06',1),
(N'D000369',N'W000054','2026-12-07',2),
(N'D000370',N'W000054','2026-12-08',3),
(N'D000371',N'W000054','2026-12-09',4),
(N'D000372',N'W000054','2026-12-10',5),
(N'D000373',N'W000054','2026-12-11',6),
(N'D000374',N'W000054','2026-12-12',7),

-- Tuần 55 (W000055: 2026-12-13 đến 2026-12-19)
(N'D000375',N'W000055','2026-12-13',1),
(N'D000376',N'W000055','2026-12-14',2),
(N'D000377',N'W000055','2026-12-15',3),
(N'D000378',N'W000055','2026-12-16',4),
(N'D000379',N'W000055','2026-12-17',5),
(N'D000380',N'W000055','2026-12-18',6),
(N'D000381',N'W000055','2026-12-19',7),

-- Tuần 56 (W000056: 2026-12-20 đến 2026-12-26)
(N'D000382',N'W000056','2026-12-20',1),
(N'D000383',N'W000056','2026-12-21',2),
(N'D000384',N'W000056','2026-12-22',3),
(N'D000385',N'W000056','2026-12-23',4),
(N'D000386',N'W000056','2026-12-24',5),
(N'D000387',N'W000056','2026-12-25',6),
(N'D000388',N'W000056','2026-12-26',7),

-- Tuần 57 (W000057: 2026-12-27 đến 2027-01-02)
(N'D000389',N'W000057','2026-12-27',1),
(N'D000390',N'W000057','2026-12-28',2),
(N'D000391',N'W000057','2026-12-29',3),
(N'D000392',N'W000057','2026-12-30',4),
(N'D000393',N'W000057','2026-12-31',5),
(N'D000394',N'W000057','2027-01-01',6),
(N'D000395',N'W000057','2027-01-02',7),

-- Tuần 58 (W000058: 2027-01-03 đến 2027-01-09)
(N'D000396',N'W000058','2027-01-03',1),
(N'D000397',N'W000058','2027-01-04',2),
(N'D000398',N'W000058','2027-01-05',3),
(N'D000399',N'W000058','2027-01-06',4),
(N'D000400',N'W000058','2027-01-07',5),
(N'D000401',N'W000058','2027-01-08',6),
(N'D000402',N'W000058','2027-01-09',7),

-- Tuần 59 (W000059: 2027-01-10 đến 2027-01-16)
(N'D000403',N'W000059','2027-01-10',1),
(N'D000404',N'W000059','2027-01-11',2),
(N'D000405',N'W000059','2027-01-12',3),
(N'D000406',N'W000059','2027-01-13',4),
(N'D000407',N'W000059','2027-01-14',5),
(N'D000408',N'W000059','2027-01-15',6),
(N'D000409',N'W000059','2027-01-16',7),

-- Tuần 60 (W000060: 2027-01-17 đến 2027-01-23)
(N'D000410',N'W000060','2027-01-17',1),
(N'D000411',N'W000060','2027-01-18',2),
(N'D000412',N'W000060','2027-01-19',3),
(N'D000413',N'W000060','2027-01-20',4),
(N'D000414',N'W000060','2027-01-21',5),
(N'D000415',N'W000060','2027-01-22',6),
(N'D000416',N'W000060','2027-01-23',7);

INSERT INTO [Days] VALUES 
(N'D000417',N'W000061','2027-01-24',1),
(N'D000418',N'W000061','2027-01-25',2),
(N'D000419',N'W000061','2027-01-26',3),
(N'D000420',N'W000061','2027-01-27',4),
(N'D000421',N'W000061','2027-01-28',5),
(N'D000422',N'W000061','2027-01-29',6),
(N'D000423',N'W000061','2027-01-30',7),

-- Tuần 62 (W000062: 2027-01-31 đến 2027-02-06)
(N'D000424',N'W000062','2027-01-31',1),
(N'D000425',N'W000062','2027-02-01',2),
(N'D000426',N'W000062','2027-02-02',3),
(N'D000427',N'W000062','2027-02-03',4),
(N'D000428',N'W000062','2027-02-04',5),
(N'D000429',N'W000062','2027-02-05',6),
(N'D000430',N'W000062','2027-02-06',7),

-- Tuần 63 (W000063: 2027-02-07 đến 2027-02-13)
(N'D000431',N'W000063','2027-02-07',1),
(N'D000432',N'W000063','2027-02-08',2),
(N'D000433',N'W000063','2027-02-09',3),
(N'D000434',N'W000063','2027-02-10',4),
(N'D000435',N'W000063','2027-02-11',5),
(N'D000436',N'W000063','2027-02-12',6),
(N'D000437',N'W000063','2027-02-13',7),

-- Tuần 64 (W000064: 2027-02-14 đến 2027-02-20)
(N'D000438',N'W000064','2027-02-14',1),
(N'D000439',N'W000064','2027-02-15',2),
(N'D000440',N'W000064','2027-02-16',3),
(N'D000441',N'W000064','2027-02-17',4),
(N'D000442',N'W000064','2027-02-18',5),
(N'D000443',N'W000064','2027-02-19',6),
(N'D000444',N'W000064','2027-02-20',7),

-- Tuần 65 (W000065: 2027-02-21 đến 2027-02-27)
(N'D000445',N'W000065','2027-02-21',1),
(N'D000446',N'W000065','2027-02-22',2),
(N'D000447',N'W000065','2027-02-23',3),
(N'D000448',N'W000065','2027-02-24',4),
(N'D000449',N'W000065','2027-02-25',5),
(N'D000450',N'W000065','2027-02-26',6),
(N'D000451',N'W000065','2027-02-27',7),

-- Tuần 66 (W000066: 2027-02-28 đến 2027-03-06)
(N'D000452',N'W000066','2027-02-28',1),
(N'D000453',N'W000066','2027-03-01',2),
(N'D000454',N'W000066','2027-03-02',3),
(N'D000455',N'W000066','2027-03-03',4),
(N'D000456',N'W000066','2027-03-04',5),
(N'D000457',N'W000066','2027-03-05',6),
(N'D000458',N'W000066','2027-03-06',7),

-- Tuần 67 (W000067: 2027-03-07 đến 2027-03-13)
(N'D000459',N'W000067','2027-03-07',1),
(N'D000460',N'W000067','2027-03-08',2),
(N'D000461',N'W000067','2027-03-09',3),
(N'D000462',N'W000067','2027-03-10',4),
(N'D000463',N'W000067','2027-03-11',5),
(N'D000464',N'W000067','2027-03-12',6),
(N'D000465',N'W000067','2027-03-13',7),

-- Tuần 68 (W000068: 2027-03-14 đến 2027-03-20)
(N'D000466',N'W000068','2027-03-14',1),
(N'D000467',N'W000068','2027-03-15',2),
(N'D000468',N'W000068','2027-03-16',3),
(N'D000469',N'W000068','2027-03-17',4),
(N'D000470',N'W000068','2027-03-18',5),
(N'D000471',N'W000068','2027-03-19',6),
(N'D000472',N'W000068','2027-03-20',7),

-- Tuần 69 (W000069: 2027-03-21 đến 2027-03-27)
(N'D000473',N'W000069','2027-03-21',1),
(N'D000474',N'W000069','2027-03-22',2),
(N'D000475',N'W000069','2027-03-23',3),
(N'D000476',N'W000069','2027-03-24',4),
(N'D000477',N'W000069','2027-03-25',5),
(N'D000478',N'W000069','2027-03-26',6),
(N'D000479',N'W000069','2027-03-27',7),

-- Tuần 70 (W000070: 2027-03-28 đến 2027-04-03)
(N'D000480',N'W000070','2027-03-28',1),
(N'D000481',N'W000070','2027-03-29',2),
(N'D000482',N'W000070','2027-03-30',3),
(N'D000483',N'W000070','2027-03-31',4),
(N'D000484',N'W000070','2027-04-01',5),
(N'D000485',N'W000070','2027-04-02',6),
(N'D000486',N'W000070','2027-04-03',7),

-- Tuần 71 (W000071: 2027-04-04 đến 2027-04-10)
(N'D000487',N'W000071','2027-04-04',1),
(N'D000488',N'W000071','2027-04-05',2),
(N'D000489',N'W000071','2027-04-06',3),
(N'D000490',N'W000071','2027-04-07',4),
(N'D000491',N'W000071','2027-04-08',5),
(N'D000492',N'W000071','2027-04-09',6),
(N'D000493',N'W000071','2027-04-10',7),

-- Tuần 72 (W000072: 2027-04-11 đến 2027-04-17)
(N'D000494',N'W000072','2027-04-11',1),
(N'D000495',N'W000072','2027-04-12',2),
(N'D000496',N'W000072','2027-04-13',3),
(N'D000497',N'W000072','2027-04-14',4),
(N'D000498',N'W000072','2027-04-15',5),
(N'D000499',N'W000072','2027-04-16',6),
(N'D000500',N'W000072','2027-04-17',7),

-- Tuần 73 (W000073: 2027-04-18 đến 2027-04-24)
(N'D000501',N'W000073','2027-04-18',1),
(N'D000502',N'W000073','2027-04-19',2),
(N'D000503',N'W000073','2027-04-20',3),
(N'D000504',N'W000073','2027-04-21',4),
(N'D000505',N'W000073','2027-04-22',5),
(N'D000506',N'W000073','2027-04-23',6),
(N'D000507',N'W000073','2027-04-24',7),

-- Tuần 74 (W000074: 2027-04-25 đến 2027-05-01)
(N'D000508',N'W000074','2027-04-25',1),
(N'D000509',N'W000074','2027-04-26',2),
(N'D000510',N'W000074','2027-04-27',3),
(N'D000511',N'W000074','2027-04-28',4),
(N'D000512',N'W000074','2027-04-29',5),
(N'D000513',N'W000074','2027-04-30',6),
(N'D000514',N'W000074','2027-05-01',7),

-- Tuần 75 (W000075: 2027-05-02 đến 2027-05-08)
(N'D000515',N'W000075','2027-05-02',1),
(N'D000516',N'W000075','2027-05-03',2),
(N'D000517',N'W000075','2027-05-04',3),
(N'D000518',N'W000075','2027-05-05',4),
(N'D000519',N'W000075','2027-05-06',5),
(N'D000520',N'W000075','2027-05-07',6),
(N'D000521',N'W000075','2027-05-08',7),

-- Tuần 76 (W000076: 2027-05-09 đến 2027-05-15)
(N'D000522',N'W000076','2027-05-09',1),
(N'D000523',N'W000076','2027-05-10',2),
(N'D000524',N'W000076','2027-05-11',3),
(N'D000525',N'W000076','2027-05-12',4),
(N'D000526',N'W000076','2027-05-13',5),
(N'D000527',N'W000076','2027-05-14',6),
(N'D000528',N'W000076','2027-05-15',7),

-- Tuần 77 (W000077: 2027-05-16 đến 2027-05-22)
(N'D000529',N'W000077','2027-05-16',1),
(N'D000530',N'W000077','2027-05-17',2),
(N'D000531',N'W000077','2027-05-18',3),
(N'D000532',N'W000077','2027-05-19',4),
(N'D000533',N'W000077','2027-05-20',5),
(N'D000534',N'W000077','2027-05-21',6),
(N'D000535',N'W000077','2027-05-22',7),

-- Tuần 78 (W000078: 2027-05-23 đến 2027-05-29) - Kết thúc năm học SY000002
(N'D000536',N'W000078','2027-05-23',1),
(N'D000537',N'W000078','2027-05-24',2),
(N'D000538',N'W000078','2027-05-25',3),
(N'D000539',N'W000078','2027-05-26',4),
(N'D000540',N'W000078','2027-05-27',5),
(N'D000541',N'W000078','2027-05-28',6),
(N'D000542',N'W000078','2027-05-29',7);