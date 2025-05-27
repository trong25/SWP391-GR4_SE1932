INSERT INTO [Grades] VALUES
(N'G000001', N'Lớp 6', N'Chương trình lớp 6 giúp học sinh làm quen với kiến thức cơ bản bậc trung học cơ sở như toán học, ngữ văn, khoa học và kỹ năng học tập độc lập.'),
(N'G000002', N'Lớp 7', N'Lớp 7 phát triển kỹ năng tư duy logic, phân tích và tổng hợp thông tin thông qua các môn học như toán, văn, sử, địa và ngoại ngữ.'),
(N'G000003', N'Lớp 8', N'Lớp 8 mở rộng kiến thức khoa học, xã hội và kỹ năng giải quyết vấn đề, chuẩn bị cho các chương trình học nâng cao.'),
(N'G000004', N'Lớp 9', N'Lớp 9 là năm học cuối cấp THCS, tập trung ôn luyện và củng cố kiến thức để chuẩn bị cho kỳ thi tuyển sinh vào lớp 10.'),
(N'G000005', N'Lớp 10', N'Lớp 10 là năm đầu tiên của bậc THPT, học sinh làm quen với phân ban và tiếp cận kiến thức nâng cao ở các môn học.'),
(N'G000006', N'Lớp 11', N'Lớp 11 tăng cường chuyên sâu các môn học theo định hướng nghề nghiệp hoặc khối thi đại học, phát triển kỹ năng tư duy phản biện.'),
(N'G000007', N'Lớp 12', N'Lớp 12 là năm cuối cấp THPT, học sinh tập trung ôn luyện kiến thức để chuẩn bị cho kỳ thi tốt nghiệp và xét tuyển đại học.');

	

INSERT INTO [Class] VALUES (N'C000001',N'Lớp 6A',N'G000001',N'TEA000010',N'SY000001',N'đã được duyệt',N'AS000010'),
	(N'C000002',N'Lớp 6B',N'G000001',N'TEA000011',N'SY000001',N'đã được duyệt',N'AS000010'),
	(N'C000003',N'Lớp 7A',N'G000002',N'TEA000012',N'SY000001',N'đã được duyệt',N'AS000011'),
	(N'C000004',N'Lớp 7B ',N'G000002',N'TEA000013',N'SY000001',N'đã được duyệt',N'AS000011'),
	(N'C000005',N'Lớp 8A',N'G000003',N'TEA000014',N'SY000002',N'đã được duyệt',N'AS000012'),
	(N'C000006',N'Lớp 8B',N'G000003',N'TEA000015',N'SY000002',N'đã được duyệt',N'AS000012'),
	(N'C000007',N'Lớp 9A',N'G000004',N'TEA000016',N'SY000002',N'đã được duyệt',N'AS000013'),
	(N'C000008',N'Lớp 9B',N'G000004',N'TEA000017',N'SY000002',N'đang chờ xử lý',N'AS000013'),
	(N'C000009',N'Lớp 10A',N'G000005',N'TEA000018',N'SY000001',N'đã được duyệt',N'AS000010'),
	(N'C000010',N'Lớp 10B',N'G000005',N'TEA000019',N'SY000002',N'đã được duyệt',N'AS000013'),
	(N'C000011',N'Lớp 11A',N'G000006',N'TEA000020',N'SY000002',N'đã được duyệt',N'AS000013'),
	(N'C000012',N'Lớp 11B',N'G000006',N'TEA000021',N'SY000002',N'đã được duyệt',N'AS000013'),
	(N'C000013',N'Lớp 12A',N'G000007',N'TEA000019',N'SY000002',N'đã được duyệt',N'AS000013'),
	(N'C000014',N'Lớp 12B',N'G000007',N'TEA000019',N'SY000002',N'đã được duyệt',N'AS000013');

	INSERT INTO [dbo].[Timeslots] VALUES
    ('TS001', N'Tiết sáng 1', '07:00', '07:45', '1'),
    ('TS002', N'Tiết sáng 2', '07:45', '08:30', '2'),
    ('TS003', N'Tiết sáng 3', '08:30', '09:15', '3'),
    ('TS004', N'Tiết sáng 4', '09:15', '10:00', '4'),
	('TS005', N'Tiết chiều 1', '14:30', '15:15', '5'),
	('TS006', N'Tiết chiều 2', '15:15', '16:00', '6'),
	('TS07',N'Tiết chiều 3', '16:00', '16:45', '7')
	

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

	INSERT INTO [Subjects] VALUES ('S000001', N'Toán học', 'G000001', N'Toán học lớp 6', N'đã được duyệt'),
('S000002', N'Ngữ văn','G000001', N'Ngữ văn lớp 6', N'đã được duyệt'),
('S000003', N'Tiếng Anh','G000001', N'Tiếng Anh cơ bản lớp 6', N'đã được duyệt'),
('S000004', N'Lịch sử', 'G000001', N'Lịch sử Việt Nam cơ bản lớp 6', N'đang chờ xử lý'),
('S000005', N'Địa lý', 'G000001', N'Địa lý tự nhiên và xã hội lớp 6', N'đã được duyệt'),
('S000006', N'Giáo dục công dân', 'G000001', N'Nhận thức xã hội và đạo đức lớp 6', N'đã được duyệt'),
('S00007', N'Toán học', 'G000002', N'Toán học lớp 7', N'đã được duyệt'),
('S00008', N'Ngữ văn', 'G000002', N'Ngữ văn lớp 7', N'đã được duyệt'),
('S00009', N'Tiếng Anh', 'G000002', N'Tiếng Anh lớp 7', N'đang chờ xử lý'),
('S00010', N'Lịch sử', 'G000002', N'Lịch sử lớp 7', N'đã được duyệt'),
('S00011', N'Địa lý', 'G000002', N'Địa lý lớp 7', N'đã được duyệt'),
('S00012', N'Giáo dục công dân', 'G000002', N'GDCD lớp 7', N'đã được duyệt'),
('S00013', N'Toán học', 'G000005', N'Toán học lớp 10', N'đã được duyệt'),
('S00014', N'Ngữ văn', 'G000005', N'Ngữ văn lớp 10', N'đã được duyệt'),
('S00015', N'Tiếng Anh', 'G000005', N'Tiếng Anh nâng cao lớp 10', N'đã được duyệt'),
('S00016', N'Lịch sử', 'G000005', N'Lịch sử lớp 10', N'đang chờ xử lý'),
('S00017', N'Địa lý', 'G000005', N'Địa lý lớp 10', N'đã được duyệt'),
('S00018', N'Giáo dục công dân', 'G000005', N'GDCD lớp 10', N'đã được duyệt'),
('S00019', N'Tiếng Anh', 'G000003', N'Tiếng Anh lớp 8 ', N'đang chờ xử lý'),
('S00020', N'Lịch sử', 'G000003', N'Lịch sử lớp 8', N'đã được duyệt'),
('S00021', N'Địa lý', 'G000003', N'Địa lý lớp 8', N'đã được duyệt'),
('S00022', N'Giáo dục công dân', 'G000003', N'GDCD lớp 8', N'đã được duyệt'),
('S00023', N'Toán học', 'G000003', N'Toán học lớp 8', N'đã được duyệt'),
('S00024', N'Ngữ văn', 'G000004', N'Ngữ văn lớp 9', N'đang chờ xử lý'),
('S00025', N'Tiếng Anh', 'G000004', N'Tiếng Anh nâng cao lớp 9', N'đang chờ xử lý'),
('S00026', N'Lịch sử', 'G000006', N'Lịch sử lớp 11', N'đã được duyệt'),
('S00027', N'Địa lý', 'G000006', N'Địa lý lớp 11', N'đã được duyệt'),
('S00028', N'Giáo dục công dân', 'G000006', N'GDCD lớp 11', N'đã được duyệt'),
('S00029', N'Toán Học', 'G000004', N'Ôn Thi THPT', N'đã được duyệt'),
('S00030', N'Ôn Thi THPTQG Toán Học', 'G000007', N'Ôn Thi THPTQG ', N'đã được duyệt')

INSERT INTO [Application_Types] VALUES ('AT000001',N'Đơn xin nghỉ học', N'pupil', N'teacher',N'Học sinh xin nghỉ học sẽ gửi đơn này cho giáo viên chủ nhiệm');


INSERT INTO [Applications] VALUES ('APP000001', '2024-01-15', 'AT000001', N'Đơn xin nghỉ học vì lý do sức khỏe', NULL,N'đã được duyệt', 'U000003', '2024-01-10', '2024-01-11', '2024-01-11', 'AS000006');
GO

INSERT INTO Events
	VALUES	('E000001', 'HT000001', N'Hội Thảo Phương Pháp Học Tập Hiệu Quả', N'Sự kiện dành cho học sinh từ lớp 6–12 nhằm hướng dẫn các phương pháp học tập chủ động, ghi nhớ hiệu quả và luyện đề thi. Tổ chức vào ngày 10/06/2024 tại hội trường Trung tâm.', '2024-06-10'),

('E000002', 'HT000001', N'Cuộc Thi Học Sinh Giỏi Cấp Trung Tâm', N'Cuộc thi tuyển chọn học sinh giỏi các môn Toán, Văn, Anh, Lý, Hóa. Diễn ra vào ngày 25/06/2024, từ 8:00 AM đến 11:30 AM tại các phòng thi trung tâm.', '2024-06-25'),

('E000003', 'HT000001', N'Buổi Định Hướng Lộ Trình Ôn Thi Vào 10',  N'Chương trình tư vấn và định hướng cho học sinh lớp 9 về lộ trình ôn thi vào lớp 10, bao gồm phân tích cấu trúc đề, phương pháp học và phân bổ thời gian học tập. Tổ chức vào ngày 30/06/2024.', '2024-06-30'),

('E000004', 'HT000001', N'Tư Vấn Tuyển Sinh và Giới Thiệu Khóa Học Mới',  N'Sự kiện giới thiệu các lớp học thêm chuẩn bị cho năm học mới, bao gồm các môn học chính và lớp luyện thi. Tổ chức vào ngày 15/07/2024 tại Trung tâm.', '2024-07-15'),

('E000005', 'HT000001', N'Báo Cáo Kết Quả Học Tập Học Kỳ 2', N'Sự kiện tổng kết học kỳ, trao phần thưởng cho học sinh xuất sắc, học sinh tiến bộ và phản hồi phụ huynh. Tổ chức ngày 05/07/2024 tại Hội trường Trung tâm.', '2024-07-05'),

('E000006', 'HT000001', N'Lớp Bồi Dưỡng Giáo Viên Môn Toán và Văn', N'Khóa tập huấn ngắn hạn cho giáo viên trung tâm để nâng cao kỹ năng giảng dạy, cập nhật phương pháp luyện thi và xử lý học sinh yếu kém.', '2024-08-01'),

('E000007', 'HT000001', N'Hội Thảo Tâm Lý Học Đường Cho Phụ Huynh', N'Sự kiện tư vấn cho phụ huynh về các vấn đề tâm lý học đường như áp lực học tập, định hướng nghề nghiệp và giao tiếp với con. Tổ chức vào ngày 12/08/2024.', '2024-08-12');
Insert into eventDetails values ('E000001',4),('E000002',4),('E000003',4),('E000004',3),('E000005',4),('E000006',3);
								