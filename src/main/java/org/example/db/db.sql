-- 데이터베이스 생성
USE sbs_proj_1;
DROP DATABASE IF EXISTS sbs_proj_1;
CREATE DATABASE sbs_proj_1;

-- 학생 테이블 생성
CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    student_username VARCHAR(100) UNIQUE NOT NULL,
    student_password VARCHAR(100) NOT NULL,
    total_courses_allowed INT DEFAULT 5 -- 학생이 신청할 수 있는 총 강의 수 (기본값: 5)
);

-- 교수 테이블 생성
CREATE TABLE professors (
    professor_id INT AUTO_INCREMENT PRIMARY KEY,
    professor_name VARCHAR(100) NOT NULL,
    professor_username VARCHAR(100) UNIQUE NOT NULL,
    professor_password VARCHAR(100) NOT NULL
);

-- 강의 테이블 생성
CREATE TABLE lectures (
    lecture_id INT AUTO_INCREMENT PRIMARY KEY,
    lecture_name VARCHAR(100) NOT NULL,
    professor_id INT,
    capacity INT DEFAULT 3, -- 수용 가능한 학생 수 (기본값: 3)
    remaining_capacity INT DEFAULT 3, -- 남은 수용 가능한 학생 수 (기본값: 3)
    FOREIGN KEY (professor_id) REFERENCES professors(professor_id)
);

-- 성적 테이블 생성
CREATE TABLE grades (
    grade_id INT AUTO_INCREMENT PRIMARY KEY,
    lecture_id INT,
    student_id INT,
    grade DECIMAL(4,2),
    FOREIGN KEY (lecture_id) REFERENCES lectures(lecture_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);

-- 강의 신청 테이블 생성
CREATE TABLE course_registrations (
    registration_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    lecture_id INT,
    UNIQUE KEY (student_id, lecture_id), -- 학생이 중복해서 신청하는 것을 방지
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (lecture_id) REFERENCES lectures(lecture_id)
);

-- 학생 등록 수 제한을 위한 트리거 생성
DELIMITER //
CREATE TRIGGER limit_student_registration
BEFORE INSERT ON students
FOR EACH ROW
BEGIN
    DECLARE student_count INT;
    SELECT COUNT(*) INTO student_count FROM students;
    IF student_count >= 5 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Only 5 students can be registered.';
    END IF;
END //
DELIMITER ;

-- 강의에 학생이 신청할 때 남은 수용 가능한 학생 수 갱신
DELIMITER //
CREATE TRIGGER update_remaining_capacity
BEFORE INSERT ON course_registrations
FOR EACH ROW
BEGIN
    UPDATE lectures
    SET remaining_capacity = remaining_capacity - 1
    WHERE lecture_id = NEW.lecture_id AND remaining_capacity > 0;
    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No remaining capacity for this lecture.';
    END IF;
END //
DELIMITER ;

-- 학생이 이미 신청한 강의를 다시 신청하는 것을 방지하는 트리거 생성
DELIMITER //
CREATE TRIGGER prevent_duplicate_registration
BEFORE INSERT ON course_registrations
FOR EACH ROW
BEGIN
    DECLARE existing_registration_count INT;
    SELECT COUNT(*) INTO existing_registration_count
    FROM course_registrations
    WHERE student_id = NEW.student_id AND lecture_id = NEW.lecture_id;
    IF existing_registration_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Student has already registered for this lecture.';
    END IF;
END //
DELIMITER ;

-- 학생이 신청한 강의 목록 조회
SELECT l.lecture_name
FROM lectures l
JOIN course_registrations cr ON l.lecture_id = cr.lecture_id
WHERE cr.student_id = <학생ID>; -- 여기서 <학생ID>는 실제 학생의 ID로 대체해야 합니다.
