-- 데이터베이스 생성
DROP DATABASE IF EXISTS sbs_proj_1;
CREATE DATABASE sbs_proj_1;
USE sbs_proj_1;

-- 학생 테이블 생성
CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    student_username VARCHAR(100) UNIQUE NOT NULL, -- 학번 또는 사용자명
    student_password VARCHAR(100) NOT NULL,       -- 학생 비밀번호
    total_courses_allowed INT DEFAULT 5           -- 학생이 신청할 수 있는 강의 수 (기본값: 5)
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
    capacity INT DEFAULT 3,          -- 최대 수용 인원 (기본값: 3)
    remaining_capacity INT DEFAULT 3, -- 남은 수용 인원 (기본값: 3)
    FOREIGN KEY (professor_id) REFERENCES professors(professor_id)
);

-- 성적 테이블 생성
CREATE TABLE grades (
    grade_id INT AUTO_INCREMENT PRIMARY KEY,
    lecture_id INT,
    student_id INT,
    grade VARCHAR(5), -- 성적을 문자열로 저장
    FOREIGN KEY (lecture_id) REFERENCES lectures(lecture_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);

-- 강의 신청 테이블 생성
CREATE TABLE course_registrations (
    registration_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    lecture_id INT,
    UNIQUE KEY (student_id, lecture_id), -- 학생이 중복 신청하는 것을 방지
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (lecture_id) REFERENCES lectures(lecture_id)
);

-- 공지사항 테이블 생성
CREATE TABLE notices (
    notice_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL
);

-- 공지사항 추가
INSERT INTO notices (title, content) VALUES ('공지 제목', '공지 내용');

-- 학생 등록 수 제한 트리거
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

-- 강의 수용 가능한 인원 갱신 트리거
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

-- 중복 강의 신청을 방지하는 트리거
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
JOIN course_registrations cr
ON l.lecture_id = cr.lecture_id
WHERE cr.student_id = (SELECT student_id FROM students WHERE student_username = '학생의_유저네임');

-- 강의별 학생 목록 조회
SELECT s.student_username, s.student_name
FROM students s
JOIN course_registrations cr
ON s.student_id = cr.student_id
WHERE cr.lecture_id = '강의_ID';

-- 강의별 학생 성적 조회
SELECT s.student_username, g.grade
FROM grades g
JOIN students s
ON g.student_id = s.student_id
WHERE g.lecture_id = '강의_ID';

-- 전체 강의 목록 조회
SELECT lecture_id, lecture_name
FROM lectures;

SELECT * FROM students;
SELECT * FROM professors;
SELECT * FROM lectures;
SELECT * FROM grades;
SELECT * FROM course_registrations;
-- 공지사항 조회
SELECT * FROM notices;
-- 모든 학생 조회
SELECT student_id, student_name, student_username FROM students;