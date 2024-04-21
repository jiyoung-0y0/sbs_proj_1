/*
SQLyog Community v13.2.1 (64 bit)
MySQL - 10.4.32-MariaDB : Database - sbs_proj_1
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sbs_proj_1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `sbs_proj_1`;

/*Table structure for table `course_registrations` */

DROP TABLE IF EXISTS `course_registrations`;

CREATE TABLE `course_registrations` (
  `registration_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `lecture_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`registration_id`),
  UNIQUE KEY `student_id` (`student_id`,`lecture_id`),
  KEY `lecture_id` (`lecture_id`),
  CONSTRAINT `course_registrations_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  CONSTRAINT `course_registrations_ibfk_2` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`lecture_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `course_registrations` */

insert  into `course_registrations`(`registration_id`,`student_id`,`lecture_id`) values 
(3,1,5);

/*Table structure for table `grades` */

DROP TABLE IF EXISTS `grades`;

CREATE TABLE `grades` (
  `grade_id` int(11) NOT NULL AUTO_INCREMENT,
  `lecture_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `grade` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`grade_id`),
  KEY `lecture_id` (`lecture_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `grades_ibfk_1` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`lecture_id`),
  CONSTRAINT `grades_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `grades` */

insert  into `grades`(`grade_id`,`lecture_id`,`student_id`,`grade`) values 
(5,5,1,'d');

/*Table structure for table `lectures` */

DROP TABLE IF EXISTS `lectures`;

CREATE TABLE `lectures` (
  `lecture_id` int(11) NOT NULL AUTO_INCREMENT,
  `lecture_name` varchar(100) NOT NULL,
  `professor_id` int(11) DEFAULT NULL,
  `capacity` int(11) DEFAULT 3,
  `remaining_capacity` int(11) DEFAULT 3,
  PRIMARY KEY (`lecture_id`),
  KEY `professor_id` (`professor_id`),
  CONSTRAINT `lectures_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `professors` (`professor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `lectures` */

insert  into `lectures`(`lecture_id`,`lecture_name`,`professor_id`,`capacity`,`remaining_capacity`) values 
(5,'apfhd',NULL,3,2),
(6,'dkssud',NULL,3,3);

/*Table structure for table `notices` */

DROP TABLE IF EXISTS `notices`;

CREATE TABLE `notices` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `notices` */

insert  into `notices`(`notice_id`,`title`,`content`) values 
(2,'dkssudgktpy','dlrjqsdl');

/*Table structure for table `professors` */

DROP TABLE IF EXISTS `professors`;

CREATE TABLE `professors` (
  `professor_id` int(11) NOT NULL AUTO_INCREMENT,
  `professor_name` varchar(100) NOT NULL,
  `professor_username` varchar(100) NOT NULL,
  `professor_password` varchar(100) NOT NULL,
  PRIMARY KEY (`professor_id`),
  UNIQUE KEY `professor_username` (`professor_username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `professors` */

/*Table structure for table `students` */

DROP TABLE IF EXISTS `students`;

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_name` varchar(100) NOT NULL,
  `student_username` varchar(100) NOT NULL,
  `student_password` varchar(100) NOT NULL,
  `total_courses_allowed` int(11) DEFAULT 5,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `student_username` (`student_username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `students` */

insert  into `students`(`student_id`,`student_name`,`student_username`,`student_password`,`total_courses_allowed`) values 
(1,'rlawldud','20200553','wldud9409',5),
(2,'rlawldb','2020','rlawldb',5);

/* Trigger structure for table `course_registrations` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `update_remaining_capacity` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'sbsst'@'%' */ /*!50003 TRIGGER `update_remaining_capacity` BEFORE INSERT ON `course_registrations` FOR EACH ROW 
BEGIN
    UPDATE lectures
    SET remaining_capacity = remaining_capacity - 1
    WHERE lecture_id = NEW.lecture_id AND remaining_capacity > 0;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No remaining capacity for this lecture.';
    END IF;
END */$$


DELIMITER ;

/* Trigger structure for table `course_registrations` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `prevent_duplicate_registration` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'sbsst'@'%' */ /*!50003 TRIGGER `prevent_duplicate_registration` BEFORE INSERT ON `course_registrations` FOR EACH ROW 
BEGIN
    DECLARE existing_registration_count INT;

    SELECT COUNT(*) INTO existing_registration_count 
    FROM course_registrations 
    WHERE student_id = NEW.student_id AND lecture_id = NEW.lecture_id;

    IF existing_registration_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Student has already registered for this lecture.';
    END IF;
END */$$


DELIMITER ;

/* Trigger structure for table `students` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `limit_student_registration` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'sbsst'@'%' */ /*!50003 TRIGGER `limit_student_registration` BEFORE INSERT ON `students` FOR EACH ROW 
BEGIN
    DECLARE student_count INT;
    SELECT COUNT(*) INTO student_count FROM students;
    IF student_count >= 5 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Only 5 students can be registered.';
    END IF;
END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;git
