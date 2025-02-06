DROP DATABASE IF EXISTS DrivingSchoolDB;
CREATE DATABASE DrivingSchoolDB;
USE DrivingSchoolDB;

CREATE TABLE User (
                      user_id VARCHAR(10) PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      email VARCHAR(100) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role VARCHAR(20) NOT NULL
);

CREATE TABLE Course (
                        course_id VARCHAR(10) PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        duration INT NOT NULL,
                        price DECIMAL(10,2) NOT NULL
);

CREATE TABLE Student (
                         student_id VARCHAR(10) PRIMARY KEY,
                         email VARCHAR(100),
                         nic VARCHAR(20),
                         contact VARCHAR(15)
);

CREATE TABLE StudentRegistration (
                                     registration_id VARCHAR(10) PRIMARY KEY,
                                     student_id VARCHAR(10),
                                     course_id VARCHAR(10),
                                     registration_date DATE NOT NULL,
                                     pay_balance DECIMAL(10,2),
                                     FOREIGN KEY (student_id) REFERENCES Student(student_id)
                                         ON DELETE CASCADE ON UPDATE CASCADE,
                                     FOREIGN KEY (course_id) REFERENCES Course(course_id)
                                         ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Payment (
                         payment_id VARCHAR(10) PRIMARY KEY,
                         registration_id VARCHAR(10),
                         student_id VARCHAR(10),
                         amount DECIMAL(10,2) NOT NULL,
                         payment_date DATE NOT NULL,
                         FOREIGN KEY (registration_id) REFERENCES StudentRegistration(registration_id)
                             ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (student_id) REFERENCES Student(student_id)
                             ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Exam (
                      exam_id VARCHAR(10) PRIMARY KEY,
                      exam_name VARCHAR(100),
                      student_id VARCHAR(10),
                      exam_date DATE NOT NULL,
                      result VARCHAR(20),
                      FOREIGN KEY (student_id) REFERENCES Student(student_id)
                          ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Instructor (
                            instructor_id VARCHAR(10) PRIMARY KEY,
                            name VARCHAR(50),
                            email VARCHAR(50),
                            contact VARCHAR(20),
                            vehicle_class VARCHAR(10)
);

CREATE TABLE Vehicle (
                         vehicle_id VARCHAR(10) PRIMARY KEY,
                         vehicle_number VARCHAR(20) UNIQUE NOT NULL,
                         engine_number VARCHAR(50) NOT NULL,
                         vehicle_class VARCHAR(20),
                         status VARCHAR(100)
);

CREATE TABLE Sessions (
                          session_id VARCHAR(10) PRIMARY KEY,
                          instructor_id VARCHAR(10),
                          vehicle_id VARCHAR(10),
                          day VARCHAR(20),
                          start_time VARCHAR(20),
                          end_time VARCHAR(20),
                          FOREIGN KEY (instructor_id) REFERENCES Instructor(instructor_id)
                              ON DELETE CASCADE ON UPDATE CASCADE,
                          FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
                              ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Booking (
                         booking_id VARCHAR(10) PRIMARY KEY,
                         student_id VARCHAR(10),
                         session_id VARCHAR(10),
                         booking_date DATE NOT NULL,
                         practise_session VARCHAR(100),
                         FOREIGN KEY (student_id) REFERENCES Student(student_id)
                             ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (session_id) REFERENCES Sessions(session_id)
                             ON DELETE CASCADE ON UPDATE CASCADE
);
