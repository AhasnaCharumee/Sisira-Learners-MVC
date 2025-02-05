DROP Database IF EXISTS DrivingSchoolDB;
CREATE DATABASE DrivingSchoolDB;
USE DrivingSchoolDB;

-- User Table
CREATE TABLE User (
                      user_id varchar(10) PRIMARY KEY ,
                      name VARCHAR(100) NOT NULL,
                      email VARCHAR(100) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role varchar(20) NOT NULL
);

-- Course Table
CREATE TABLE Course (
                        course_id varchar(10) PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        duration INT NOT NULL,
                        price DECIMAL(10,2) NOT NULL
);

-- Student Table
CREATE TABLE Student (
                         student_id varchar(10) PRIMARY KEY AUTO_INCREMENT,
                         email varchar(100),
                         nic varchar (20),
                         contact VARCHAR(15)
);

-- Student Registration Table (Many-to-Many: Student & Course)
CREATE TABLE StudentRegistration (
                                     registration_id varchar(10) PRIMARY KEY ,
                                     student_id varchar(10),
                                     course_id varchar(10),
                                     registration_date DATE NOT NULL,
                                     pay_balance decimal(10,2),
                                     FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE,
                                     FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE
);

-- Payment Table (One-to-One with Registration)
CREATE TABLE Payment (
                         payment_id varchar PRIMARY KEY ,
                         registration_id varchar(10),
                         student_id varchar(10),
                         amount DECIMAL(10,2) NOT NULL,
                         payment_date DATE NOT NULL,
                         FOREIGN KEY (registration_id) REFERENCES StudentRegistration(registration_id) ON DELETE CASCADE
                            FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE

);

-- Exam Table (One-to-Many with Student)
CREATE TABLE Exam (
                      exam_id varchar(10) PRIMARY KEY ,
                      exam_name varchar(100),
                      student_id varchar(10),
                      exam_date DATE NOT NULL,
                      result varchar(20),
                      FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE
);

-- Booking Table (For Scheduling Driving Sessions)
CREATE TABLE Booking (
                         booking_id varchar(10) PRIMARY KEY ,
                         student_id varchar(10),
                         session_id varchar(10),
                         booking_date DATE NOT NULL,
                         practise_session varchar(100)
                             FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE
    FOREIGN KEY (session_id) REFERENCES Sessions(session_id) ON DELETE CASCADE

);

-- Section Table (Driving Sessions)
CREATE TABLE Sessions (
                          section_id varchar(10) PRIMARY KEY ,
                          instructor_id varchar(10),
                          vehicle_id varchar(10),
                          day varchar(20),
                          start_time varchar(20),
                          end_time varchar(20),
                          FOREIGN KEY (instructor_id) REFERENCES Instructor(instructor_id) ON DELETE CASCADE,
                          FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id) ON DELETE CASCADE
);

-- Instructor Table
CREATE TABLE Instructor (
                            instructor_id varchar(10) PRIMARY KEY ,
                            name varchar(20),
                            email varchar(50),
                            contact varchar(20),
                            vehicle_class varchar(10)
);

-- Vehicle Table
CREATE TABLE Vehicle (
                         vehicle_id varchar(10) PRIMARY KEY ,
                         vehicle_number VARCHAR(20) UNIQUE NOT NULL,
                         engine_number VARCHAR(50) NOT NULL,
                         vehicle_class varchar(20),
                         status varchar(100)
);
