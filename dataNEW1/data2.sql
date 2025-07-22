INSERT INTO [Grades] VALUES
(N'G000001', N'Lớp 6', N'Chương trình lớp 6 giúp học sinh làm quen với kiến thức cơ bản bậc trung học cơ sở như toán học, ngữ văn, khoa học và kỹ năng học tập độc lập.'),
(N'G000002', N'Lớp 7', N'Lớp 7 phát triển kỹ năng tư duy logic, phân tích và tổng hợp thông tin thông qua các môn học như toán, văn, sử, địa và ngoại ngữ.'),
(N'G000003', N'Lớp 8', N'Lớp 8 mở rộng kiến thức khoa học, xã hội và kỹ năng giải quyết vấn đề, chuẩn bị cho các chương trình học nâng cao.'),
(N'G000004', N'Lớp 9', N'Lớp 9 là năm học cuối cấp THCS, tập trung ôn luyện và củng cố kiến thức để chuẩn bị cho kỳ thi tuyển sinh vào lớp 10.'),
(N'G000005', N'Lớp 10', N'Lớp 10 là năm đầu tiên của bậc THPT, học sinh làm quen với phân ban và tiếp cận kiến thức nâng cao ở các môn học.'),
(N'G000006', N'Lớp 11', N'Lớp 11 tăng cường chuyên sâu các môn học theo định hướng nghề nghiệp hoặc khối thi đại học, phát triển kỹ năng tư duy phản biện.'),
(N'G000007', N'Lớp 12', N'Lớp 12 là năm cuối cấp THPT, học sinh tập trung ôn luyện kiến thức để chuẩn bị cho kỳ thi tốt nghiệp và xét tuyển đại học.');

	

INSERT INTO [Class] VALUES 
('C000001', N'Lớp Toán học 6 - Cơ bản', 'G000001', 'GV000001', 'SY000001', N'đã được duyệt', 'NV000001'),
('C000002', N'Lớp Ngữ văn 6 - Cơ bản', 'G000001', 'GV000002', 'SY000001', N'đã được duyệt', 'NV000002'),
('C000003', N'Lớp Toán học 7 - Cơ bản', 'G000002', 'GV000003', 'SY000001', N'đã được duyệt', 'NV000003'),
('C000004', N'Lớp Tiếng Anh 7 - Cơ bản', 'G000002', 'GV000004', 'SY000001', N'đã được duyệt', 'NV000004'),
('C000005', N'Lớp Toán học 8 - Cơ bản', 'G000003', 'GV000005', 'SY000002', N'đã được duyệt', 'NV000005'),
('C000006', N'Lớp Ngữ văn 8 - Cơ bản', 'G000003', 'GV000006', 'SY000002', N'đã được duyệt', 'NV000006'),
('C000007', N'Lớp Toán học 9 - Nâng cao', 'G000004', 'GV000007', 'SY000002', N'đã được duyệt', 'NV000007'),
('C000008', N'Lớp Tiếng Anh 9 - Nâng cao', 'G000004', 'GV000008', 'SY000002', N'đã được duyệt', 'NV000008'),
('C000009', N'Lớp Toán học 10 - Cơ bản', 'G000005', 'GV000009', 'SY000001', N'đang chờ xử lý', 'NV000009'),
('C000010', N'Lớp Tiếng Anh 10 - Nâng cao', 'G000005', 'GV000010', 'SY000002', N'đang chờ xử lý', 'NV000010'),
('C000011', N'Lớp Toán học 11 - Cơ bản', 'G000006', 'GV000011', 'SY000002', N'đã được duyệt', 'NV000011'),
('C000012', N'Lớp Ngữ văn 11 - Cơ bản', 'G000006', 'GV000012', 'SY000002', N'đã được duyệt', 'NV000012'),
('C000013', N'Lớp Toán 12 - Ôn thi THPT', 'G000007', 'GV000013', 'SY000002', N'đã được duyệt', 'NV000013'),
('C000014', N'Lớp Ngữ văn 12 - Ôn thi THPT', 'G000007', 'GV000014', 'SY000002', N'đã được duyệt', 'NV000014');


INSERT INTO [Timeslots] VALUES
    -- Các tiết cuối tuần (thứ 7, CN)
    ('TS001', N'Tiết sáng 1 (CN)', '08:00', '10:00', '1', 'weekend'),
    ('TS002', N'Tiết sáng 2 (CN)', '10:00', '12:00', '2', 'weekend'),
    ('TS003', N'Tiết chiều 1 (CN)', '13:00', '15:00', '3', 'weekend'),
    ('TS004', N'Tiết chiều 2 (CN)', '15:00', '17:00', '4', 'weekend'),
    
    -- Các tiết buổi tối trong tuần (thứ 2-6)
    ('TS005', N'Tiết tối 1 (19-21h)', '19:00', '21:00', '5', 'weekday'),
    ('TS006', N'Tiết tối 2 (21-23h)', '21:00', '23:00', '6', 'weekday'),
    ('TS007', N'Tiết tối 3 (18-20h)', '18:00', '20:00', '7', 'weekday');
	INSERT INTO [ClassDetails] VALUES (N'HS000001',N'C000001'),
	(N'HS000002',N'C000001'),
	(N'HS000003',N'C000001'),
	(N'HS000004',N'C000001'),
	(N'HS000005',N'C000001'),
	(N'HS000006',N'C000001'),
	(N'HS000007',N'C000001'),
	(N'HS000008',N'C000001'),
	(N'HS000009',N'C000002'),
	(N'HS000010',N'C000002'),
	(N'HS000011',N'C000002'),
	(N'HS000012',N'C000002'),
	(N'HS000013',N'C000002'),
	(N'HS000014',N'C000002'),
	(N'HS000015',N'C000002'),
	(N'HS000016',N'C000002'),
	(N'HS000017',N'C000002'),
	(N'HS000018',N'C000003'),
	(N'HS000019',N'C000003'),
	(N'HS000020',N'C000003'),
	(N'HS000021',N'C000003'),
	(N'HS000022',N'C000003'),
	(N'HS000023',N'C000003'),
	(N'HS000024',N'C000003'),
	(N'HS000025',N'C000003'),
	(N'HS000001',N'C000010'),
	(N'HS000002',N'C000010'),
	(N'HS000003',N'C000010');

INSERT INTO [Subjects] VALUES 
('S000001', N'Toán học lớp 6', 'G000001', N'Toán học chương trình cơ bản lớp 6', N'đã được duyệt', N'Cơ bản'),
('S000002', N'Ngữ văn lớp 6', 'G000001', N'Ngữ văn chương trình cơ bản lớp 6', N'đã được duyệt', N'Cơ bản'),
('S000003', N'Tiếng Anh lớp 6', 'G000001', N'Tiếng Anh chương trình cơ bản lớp 6', N'đã được duyệt', N'Cơ bản'),
('S000004', N'Lịch sử lớp 6', 'G000001', N'Lịch sử Việt Nam lớp 6', N'đang chờ xử lý', N'Cơ bản'),
('S000005', N'Địa lý lớp 6', 'G000001', N'Địa lý tự nhiên và xã hội lớp 6', N'đã được duyệt', N'Cơ bản'),
('S000006', N'Giáo dục công dân lớp 6', 'G000001', N'GDCD lớp 6', N'đã được duyệt', N'Cơ bản'),
('S000007', N'Toán học lớp 7', 'G000002', N'Toán học chương trình cơ bản lớp 7', N'đã được duyệt', N'Cơ bản'),
('S000008', N'Ngữ văn lớp 7', 'G000002', N'Ngữ văn chương trình cơ bản lớp 7', N'đã được duyệt', N'Cơ bản'),
('S000009', N'Tiếng Anh lớp 7', 'G000002', N'Tiếng Anh chương trình cơ bản lớp 7', N'đang chờ xử lý', N'Cơ bản'),
('S000010', N'Lịch sử lớp 7', 'G000002', N'Lịch sử chương trình cơ bản lớp 7', N'đã được duyệt', N'Cơ bản'),
('S000011', N'Địa lý lớp 7', 'G000002', N'Địa lý chương trình cơ bản lớp 7', N'đã được duyệt', N'Cơ bản'),
('S000012', N'Giáo dục công dân lớp 7', 'G000002', N'GDCD lớp 7', N'đã được duyệt', N'Cơ bản'),
('S000013', N'Toán học lớp 10', 'G000005', N'Toán học chương trình cơ bản lớp 10', N'đã được duyệt', N'Cơ bản'),
('S000014', N'Ngữ văn lớp 10', 'G000005', N'Ngữ văn chương trình cơ bản lớp 10', N'đã được duyệt', N'Cơ bản'),
('S000015', N'Tiếng Anh nâng cao lớp 10', 'G000005', N'Tiếng Anh chương trình nâng cao lớp 10', N'đã được duyệt', N'Nâng cao'),
('S000016', N'Lịch sử lớp 10', 'G000005', N'Lịch sử chương trình cơ bản lớp 10', N'đang chờ xử lý', N'Cơ bản'),
('S000017', N'Địa lý lớp 10', 'G000005', N'Địa lý chương trình cơ bản lớp 10', N'đã được duyệt', N'Cơ bản'),
('S000018', N'Giáo dục công dân lớp 10', 'G000005', N'GDCD lớp 10', N'đã được duyệt', N'Cơ bản'),
('S000019', N'Tiếng Anh lớp 8', 'G000003', N'Tiếng Anh chương trình cơ bản lớp 8', N'đang chờ xử lý', N'Cơ bản'),
('S000020', N'Lịch sử lớp 8', 'G000003', N'Lịch sử chương trình cơ bản lớp 8', N'đã được duyệt', N'Cơ bản'),
('S000021', N'Địa lý lớp 8', 'G000003', N'Địa lý chương trình cơ bản lớp 8', N'đã được duyệt', N'Cơ bản'),
('S000022', N'Giáo dục công dân lớp 8', 'G000003', N'GDCD lớp 8', N'đã được duyệt', N'Cơ bản'),
('S000023', N'Toán học nâng cao lớp 8', 'G000003', N'Toán học chương trình nâng cao lớp 8', N'đã được duyệt', N'Nâng cao'),
('S000024', N'Ngữ văn lớp 9', 'G000004', N'Ngữ văn chương trình cơ bản lớp 9', N'đang chờ xử lý', N'Cơ bản'),
('S000025', N'Tiếng Anh nâng cao lớp 9', 'G000004', N'Tiếng Anh chương trình nâng cao lớp 9', N'đang chờ xử lý', N'Nâng cao'),
('S000026', N'Lịch sử lớp 11', 'G000006', N'Lịch sử chương trình cơ bản lớp 11', N'đã được duyệt', N'Cơ bản'),
('S000027', N'Địa lý lớp 11', 'G000006', N'Địa lý chương trình cơ bản lớp 11', N'đã được duyệt', N'Cơ bản'),
('S000028', N'Giáo dục công dân lớp 11', 'G000006', N'GDCD lớp 11', N'đã được duyệt', N'Cơ bản'),
('S000029', N'Toán ôn thi THPT', 'G000004', N'Toán học ôn thi THPT', N'đã được duyệt', N'Ôn thi'),
('S000030', N'Toán ôn thi THPTQG', 'G000007', N'Toán học ôn thi THPT Quốc gia', N'đã được duyệt', N'Ôn thi');

INSERT INTO [Application_Types] VALUES ('AT000001',N'Đơn xin nghỉ học', N'pupil', N'teacher',N'Học sinh xin nghỉ học sẽ gửi đơn này cho giáo viên chủ nhiệm');


INSERT INTO [Applications] VALUES ('APP000001', '2024-01-15', 'AT000001', N'Đơn xin nghỉ học vì lý do sức khỏe', NULL,N'đã được duyệt', 'U000003', '2024-01-10', '2024-01-11', '2024-01-11', 'NV000006');
GO


-- Các lớp cuối tuần (TS001-TS004)
INSERT INTO Timetables VALUES
('TT000001', 'C000001', 'TS001', 'D000009', 'S000001', 'GV000001', N'đã được duyệt', N'Toán lớp 6 cơ bản - CN sáng', 'GV000001', N'Lớp 6', N'Cơ bản'),
('TT000002', 'C000001', 'TS002', 'D000009', 'S000002', 'GV000002', N'đã được duyệt', N'Ngữ văn lớp 6 cơ bản - CN sáng', 'GV000002', N'Lớp 6', N'Cơ bản'),
('TT000003', 'C000001', 'TS003', 'D000010', 'S000003', 'GV000003', N'đã được duyệt', N'Tiếng Anh lớp 6 cơ bản - CN chiều', 'GV000003', N'Lớp 6', N'Cơ bản'),

-- Toán ôn thi THPT (dùng GV000004, GV000005)
('TT000008', 'C000008', 'TS005', 'D000242', 'S000029', 'GV000004', N'đã được duyệt', N'Toán ôn thi THPT', 'GV000004', N'Lớp 12', N'Ôn thi'),
('TT000009', 'C000008', 'TS006', 'D000243', 'S000029', 'GV000004', N'đã được duyệt', N'Toán ôn thi THPT', 'GV000004', N'Lớp 12', N'Ôn thi'),
('TT000010', 'C000008', 'TS007', 'D000246', 'S000029', 'GV000005', N'đã được duyệt', N'Toán ôn thi THPT', 'GV000005', N'Lớp 12', N'Ôn thi'),

-- Môn khác lớp 12
('TT000011', 'C000008', 'TS005', 'D000255', 'S000030', 'GV000006', N'đã được duyệt', N'Toán ôn thi THPTQG', 'GV000006', N'Lớp 12', N'Ôn thi'),

-- Lớp 7 - buổi tối thứ 2/4/6
('TT000018', 'C000003', 'TS005', 'D000011', 'S000007', 'GV000007', N'đã được duyệt', N'Toán lớp 7 buổi tối thứ 2', 'GV000007', N'Lớp 7', N'Cơ bản'),
('TT000019', 'C000003', 'TS007', 'D000012', 'S000008', 'GV000008', N'đã được duyệt', N'Ngữ văn lớp 7 buổi tối thứ 4', 'GV000008', N'Lớp 7', N'Cơ bản'),
('TT000020', 'C000003', 'TS006', 'D000013', 'S000009', 'GV000009', N'đã được duyệt', N'Tiếng Anh lớp 7 buổi tối thứ 6', 'GV000009', N'Lớp 7', N'Cơ bản'),

-- Lớp 8 - CN
('TT000021', 'C000005', 'TS006', 'D000014', 'S000019', 'GV000010', N'đã được duyệt', N'Tiếng Anh lớp 8 CN sáng', 'GV000010', N'Lớp 8', N'Cơ bản'),
('TT000022', 'C000005', 'TS007', 'D000014', 'S000020', 'GV000011', N'đã được duyệt', N'Lịch sử lớp 8 CN chiều', 'GV000011', N'Lớp 8', N'Cơ bản'),
('TT000023', 'C000006', 'TS005', 'D000015', 'S000023', 'GV000012', N'đã được duyệt', N'Toán nâng cao lớp 8 CN tối', 'GV000012', N'Lớp 8', N'Nâng cao');
