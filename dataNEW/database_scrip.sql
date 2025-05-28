USE [master]
GO

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'Cultural tutoring center')
BEGIN
	ALTER DATABASE [Cultural_Tutoring_Center_HN] SET OFFLINE WITH ROLLBACK IMMEDIATE;
	ALTER DATABASE [Cultural_Tutoring_Center_HN] SET ONLINE;
	DROP DATABASE [Cultural_Tutoring_Center_HN];
END

GO

CREATE DATABASE [Cultural_Tutoring_Center_TB]
GO

USE [Cultural_Tutoring_Center_TB]
GO

/*******************************************************************************
	Drop tables if exists
*******************************************************************************/
DECLARE @sql nvarchar(MAX) 
SET @sql = N'' 

SELECT @sql = @sql + N'ALTER TABLE ' + QUOTENAME(KCU1.TABLE_SCHEMA) 
    + N'.' + QUOTENAME(KCU1.TABLE_NAME) 
    + N' DROP CONSTRAINT ' -- + QUOTENAME(rc.CONSTRAINT_SCHEMA)  + N'.'  -- not in MS-SQL
    + QUOTENAME(rc.CONSTRAINT_NAME) + N'; ' + CHAR(13) + CHAR(10) 
FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS AS RC 

INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KCU1 
    ON KCU1.CONSTRAINT_CATALOG = RC.CONSTRAINT_CATALOG  
    AND KCU1.CONSTRAINT_SCHEMA = RC.CONSTRAINT_SCHEMA 
    AND KCU1.CONSTRAINT_NAME = RC.CONSTRAINT_NAME 

EXECUTE(@sql) 

GO
DECLARE @sql2 NVARCHAR(max)=''

SELECT @sql2 += ' Drop table ' + QUOTENAME(TABLE_SCHEMA) + '.'+ QUOTENAME(TABLE_NAME) + '; '
FROM   INFORMATION_SCHEMA.TABLES
WHERE  TABLE_TYPE = 'BASE TABLE'

Exec Sp_executesql @sql2 
GO 


CREATE TABLE [User] (
  [id] varchar(10) PRIMARY KEY,
  [user_name] nvarchar(255) UNIQUE,
  [salt] VARBINARY(16),
  [hashed_password] VARBINARY(64),
  [email] nvarchar(255) UNIQUE,
  [role_id] integer,
  [isDisabled] tinyint
)
GO

CREATE TABLE [Roles] (
  [id] integer PRIMARY KEY,
  [description] nvarchar(255)
)
GO

CREATE TABLE [SchoolYears] (
  [id] varchar(10) PRIMARY KEY,
  [name] nvarchar(255) UNIQUE,
  [start_date] date,
  [end_date] date,
  [description] nvarchar(255),
  [created_by] varchar(10)
)
GO

CREATE TABLE [Class] (
  [id] varchar(10) PRIMARY KEY,
  [name] nvarchar(255),
  [grade_id] varchar(10),
  [teacher_id] varchar(10),
  [school_year_id] varchar(10),
  [status] nvarchar(255),
  [created_by] varchar(10)
)
GO
-- bảng học sinh
CREATE TABLE [Students] (
  [id] varchar(10) PRIMARY KEY,
  [user_id] varchar(10),
  [first_name] nvarchar(255),
  [last_name] nvarchar(255),
  [address] nvarchar(255),
  [email] nvarchar(255) UNIQUE,
  [status] nvarchar(255),
  [birthday] date,
  [gender] integer,
  [first_guardian_name] nvarchar(255),
  [first_guardian_phone_number] nvarchar(255) UNIQUE,
  [avatar] nvarchar(255),
  [second_guardian_name] nvarchar(255),
  [second_guardian_phone_number] nvarchar(255),
  [created_by] varchar(10),
  [parent_special_note] nvarchar(max),
  [school_name] nvarchar(255)
)
GO



CREATE TABLE [Timeslots] (
  [id] varchar(10) PRIMARY KEY,
  [name] nvarchar(255),
  [start_time] nvarchar(255),
  [end_time] nvarchar(255),
  [slot_number] nvarchar(2)
)
GO
--bảng khối lớp 
CREATE TABLE [Grades] (
  [id] varchar(10) PRIMARY KEY,
  [name] nvarchar(255),
  [description] nvarchar(255)
)
GO
-- bảng môn học
CREATE TABLE [Subjects] (
  [id] varchar(10) PRIMARY KEY,
  [name] nvarchar(255),
  [grade_id] varchar(10),
  [description] nvarchar(255),
  [status] nvarchar(255)
)
GO

CREATE TABLE [Personnels] (
  [id] varchar(10) PRIMARY KEY,
  [first_name] nvarchar(255),
  [last_name] nvarchar(255),
  [gender] integer,
  [birthday] date,
  [address] nvarchar(255),
  [email] nvarchar(255) UNIQUE,
  [phone_number] nvarchar(255) UNIQUE,
  [role_id] integer,
  [status] nvarchar(255),
  [avatar] nvarchar(255),
  [user_id] varchar(10),
   [school_name] nvarchar(255)
)
GO

CREATE TABLE [Weeks] (
  [id] varchar(10) PRIMARY KEY,
  [start_date] date,
  [end_date] date,
  [school_year_id] varchar(10)
)
GO

CREATE TABLE [Days] (
  [id] varchar(10) PRIMARY KEY,
  [week_id] varchar(10),
  [date] date
)
GO

CREATE TABLE [Timetables] (
  [id] varchar(10) PRIMARY KEY,
  [class_id] varchar(10),
  [timeslot_id] varchar(10),
  [date_id] varchar(10),
  [subject_id] varchar(10),
  [created_by] varchar(10),
  [status] nvarchar(255),
  [note] nvarchar(max),
  [teacher_id] varchar(10)
)
GO


CREATE TABLE [Evaluations] (
  [id] varchar(10) PRIMARY KEY,
  [student_id] varchar(10),
  [date_id] varchar(10),
  [evaluation] nvarchar(255),
  [notes] nvarchar(max)
)
GO

CREATE TABLE [Notifications] (
  [id] varchar(10) PRIMARY KEY,
  [heading] nvarchar(255),
  [details] nvarchar(max),
  [created_by] varchar(10),
  [created_at] date
)
GO

CREATE TABLE [Applications] (
  [id] varchar(10) PRIMARY KEY,
  [processed_at] date,
  [application_type] varchar(10),
  [details] nvarchar(max),
  [process_note] nvarchar(max),
  [status] nvarchar(255),
  [created_by] varchar(10),
  [created_at] date,
  [start_date] date,
  [end_date] date,
  [processed_by] varchar(10)
)
GO

CREATE TABLE [Application_Types] (
  [id] varchar(10) PRIMARY KEY,
  [name] nvarchar(255),
  [sender_role] nvarchar(255),
  [receiver_role] nvarchar(255),
  [description] nvarchar(255)
)
GO

CREATE TABLE [Events] (
  [id] varchar(10) PRIMARY KEY,
  [created_by] varchar(10),
  [heading] nvarchar(255),
  [details] nvarchar(max),
  [date] date
)
GO

CREATE TABLE [eventDetails] (
  [event_id] varchar(10),
  [participant] integer,
  PRIMARY KEY ([event_id], [participant])
)
GO

CREATE TABLE [StudentsAttendance] (
  [id] varchar(10) PRIMARY KEY,
  [day_id] varchar(10),
  [student_id] varchar(10),
  [status] nvarchar(255),
  [note] nvarchar(255)
)
GO

CREATE TABLE [PersonnelsAttendance] (
  [id] varchar(10) PRIMARY KEY,
  [day_id] varchar(10),
  [personnel_id] varchar(10),
  [status] nvarchar(255),
  [note] nvarchar(255)
)
GO

CREATE TABLE [NotificationDetails] (
  [notification_id] varchar(10),
  [receiver_id] varchar(10),
  PRIMARY KEY ([notification_id], [receiver_id])
)
GO

CREATE TABLE [classDetails] (
  [student_id] varchar(10),
  [class_id] varchar(10),
  PRIMARY KEY ([student_id], [class_id])
)
Go
CREATE TABLE [ScoreReports] (
  [id] varchar(10) PRIMARY KEY,
  [student_id] varchar(10),
  [subject_id] varchar(10),
  [teacher_id] varchar(10),
  [school_year_id] varchar(10),
  [class_id] varchar(10),
  [grading_date] date,
  [oral_score] float,
  [midterm_score] float,
  [final_score] float,
  [average_score] float,
  [notes] nvarchar(max)
);
GO

Create table [SchoolYearSummarizeReport](
	[student_id]varchar(10),
	[schoolyear_id]varchar(10),
	[teacher_id]varchar(10),
	[good_ticket] varchar(10),
	[title] nvarchar(10),
	[teacher_note] nvarchar(MAX)
	PRIMARY KEY([student_id],[schoolyear_id])
)
GO
ALTER TABLE [SchoolYearSummarizeReport] ADD FOREIGN KEY ([student_id]) REFERENCES [Students] ([id])
GO
ALTER TABLE [SchoolYearSummarizeReport] ADD FOREIGN KEY ([schoolyear_id]) REFERENCES [SchoolYears] ([id])
GO
CREATE UNIQUE INDEX [Class_index_0] ON [Class] ("name", "school_year_id")
GO

ALTER TABLE [Students] ADD FOREIGN KEY ([user_id]) REFERENCES [User] ([id])
GO

ALTER TABLE [Class] ADD FOREIGN KEY ([grade_id]) REFERENCES [Grades] ([id])
GO

ALTER TABLE [Class] ADD FOREIGN KEY ([school_year_id]) REFERENCES [SchoolYears] ([id])
GO

ALTER TABLE [Personnels] ADD FOREIGN KEY ([user_id]) REFERENCES [User] ([id])
GO

ALTER TABLE [User] ADD FOREIGN KEY ([role_id]) REFERENCES [Roles] ([id])
GO

ALTER TABLE [Days] ADD FOREIGN KEY ([week_id]) REFERENCES [Weeks] ([id])
GO

ALTER TABLE [Timetables] ADD FOREIGN KEY ([class_id]) REFERENCES [Class] ([id])
GO

ALTER TABLE [Timetables] ADD FOREIGN KEY ([timeslot_id]) REFERENCES [Timeslots] ([id])
GO

ALTER TABLE [Timetables] ADD FOREIGN KEY ([date_id]) REFERENCES [Days] ([id])
GO

ALTER TABLE [Timetables] ADD FOREIGN KEY ([subject_id]) REFERENCES [Subjects] ([id])
GO

ALTER TABLE [Timetables] ADD FOREIGN KEY ([created_by]) REFERENCES [Personnels] ([id])
GO

ALTER TABLE [Class] ADD FOREIGN KEY ([created_by]) REFERENCES [Personnels] ([id])
GO

ALTER TABLE [SchoolYears] ADD FOREIGN KEY ([created_by]) REFERENCES [Personnels] ([id])
GO

ALTER TABLE [Evaluations] ADD FOREIGN KEY ([student_id]) REFERENCES [Students] ([id])
GO

ALTER TABLE [Evaluations] ADD FOREIGN KEY ([date_id]) REFERENCES [Days] ([id])
GO

ALTER TABLE [Weeks] ADD FOREIGN KEY ([school_year_id]) REFERENCES [SchoolYears] ([id])
GO

ALTER TABLE [StudentsAttendance] ADD FOREIGN KEY ([day_id]) REFERENCES [Days] ([id])
GO

ALTER TABLE [StudentsAttendance] ADD FOREIGN KEY ([student_id]) REFERENCES [Students] ([id])
GO

ALTER TABLE [Notifications] ADD FOREIGN KEY ([created_by]) REFERENCES [Personnels] ([id])
GO

ALTER TABLE [Applications] ADD FOREIGN KEY ([processed_by]) REFERENCES [Personnels] ([id])
GO

ALTER TABLE [Applications] ADD FOREIGN KEY ([created_by]) REFERENCES [User] ([id])
GO

ALTER TABLE [PersonnelsAttendance] ADD FOREIGN KEY ([day_id]) REFERENCES [Days] ([id])
GO

ALTER TABLE [PersonnelsAttendance] ADD FOREIGN KEY ([personnel_id]) REFERENCES [Personnels] ([id])
GO

ALTER TABLE [Subjects] ADD FOREIGN KEY ([grade_id]) REFERENCES [Grades] ([id])
GO

ALTER TABLE [classDetails] ADD FOREIGN KEY ([student_id]) REFERENCES [Students] ([id])
GO

ALTER TABLE [classDetails] ADD FOREIGN KEY ([class_id]) REFERENCES [Class] ([id])
GO

ALTER TABLE [Class] ADD FOREIGN KEY ([teacher_id]) REFERENCES [Personnels] ([id])
GO

ALTER TABLE [Events] ADD FOREIGN KEY ([created_by]) REFERENCES [Personnels] ([id])
GO

ALTER TABLE [eventDetails] ADD FOREIGN KEY ([event_id]) REFERENCES [Events] ([id])
GO

ALTER TABLE [eventDetails] ADD FOREIGN KEY ([participant]) REFERENCES [Roles] ([id])
GO

ALTER TABLE [NotificationDetails] ADD FOREIGN KEY ([notification_id]) REFERENCES [Notifications] ([id])
GO

ALTER TABLE [NotificationDetails] ADD FOREIGN KEY ([receiver_id]) REFERENCES [User] ([id])
GO

ALTER TABLE [Personnels] ADD FOREIGN KEY ([role_id]) REFERENCES [Roles] ([id])
GO

ALTER TABLE [Students] ADD FOREIGN KEY ([created_by]) REFERENCES [Personnels] ([id])
GO

ALTER TABLE [Applications] ADD FOREIGN KEY ([application_type]) REFERENCES [Application_Types] ([id])
GO
ALTER TABLE [ScoreReports] ADD FOREIGN KEY ([student_id]) REFERENCES [Students]([id]);
GO
ALTER TABLE [ScoreReports] ADD FOREIGN KEY ([subject_id]) REFERENCES [Subjects]([id]);
GO
ALTER TABLE [ScoreReports] ADD FOREIGN KEY ([teacher_id]) REFERENCES [Personnels]([id]);
GO
ALTER TABLE [ScoreReports] ADD FOREIGN KEY ([school_year_id]) REFERENCES [SchoolYears]([id]);
GO
ALTER TABLE [ScoreReports] ADD FOREIGN KEY ([class_id]) REFERENCES [Class]([id]);
GO
