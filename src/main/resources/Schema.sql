DROP TABLE IF EXISTS Subtask;
DROP TABLE IF EXISTS EmployeeTask;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS EmployeeProject;
DROP TABLE IF EXISTS Subproject;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS project;

CREATE TABLE Project (
                         Id INT AUTO_INCREMENT PRIMARY KEY,
                         Name VARCHAR(50) NOT NULL,
                         Description TEXT,
                         Deadline DATE,
                         EstimatedHours DOUBLE DEFAULT 0
);


CREATE TABLE Employee (
                          Id INT AUTO_INCREMENT PRIMARY KEY,
                          Name VARCHAR(50) NOT NULL,
                          Email VARCHAR(100) NOT NULL UNIQUE,
                          Password VARCHAR(255) NOT NULL,
                          Role ENUM('PROJECT_LEADER', 'DEVELOPER') NOT NULL
);


CREATE TABLE EmployeeProject (
                                 Employee_Id INT NOT NULL,
                                 Project_Id INT NOT NULL,
                                 PRIMARY KEY (Employee_Id, Project_Id),
                                 FOREIGN KEY (Employee_Id) REFERENCES Employee(Id) ON DELETE CASCADE,
                                 FOREIGN KEY (Project_Id) REFERENCES Project(Id) ON DELETE CASCADE
);


CREATE TABLE Subproject (
                            Id INT AUTO_INCREMENT PRIMARY KEY,
                            Project_Id INT NOT NULL,
                            Name VARCHAR(50) NOT NULL,
                            Description TEXT,
                            EstimatedHour DOUBLE,
                            FOREIGN KEY (Project_Id) REFERENCES Project(Id) ON DELETE CASCADE
);


CREATE TABLE Task (
                      Id INT AUTO_INCREMENT PRIMARY KEY,
                      Subproject_Id INT NOT NULL,
                      Name VARCHAR(50) NOT NULL,
                      Description TEXT,
                      EstimatedHours DOUBLE DEFAULT 0,
                      Deadline DATE,
                      Status ENUM('ToDo', 'InProgress', 'Done') DEFAULT 'ToDo',
                      FOREIGN KEY (Subproject_Id) REFERENCES Subproject(Id) ON DELETE CASCADE
);


CREATE TABLE EmployeeTask (
                              Employee_Id INT NOT NULL,
                              Task_Id INT NOT NULL,
                              PRIMARY KEY (Employee_Id, Task_Id),
                              FOREIGN KEY (Employee_Id) REFERENCES Employee(Id) ON DELETE CASCADE,
                              FOREIGN KEY (Task_Id) REFERENCES Task(Id) ON DELETE CASCADE
);


CREATE TABLE Subtask (
                         Id INT AUTO_INCREMENT PRIMARY KEY,
                         Task_Id INT NOT NULL,
                         Name VARCHAR(50) NOT NULL,
                         Description TEXT,
                         EstimatedHours DOUBLE DEFAULT 0,
                         Status ENUM('ToDo', 'InProgress', 'Done') DEFAULT 'ToDo',
                         FOREIGN KEY (Task_Id) REFERENCES Task(Id) ON DELETE CASCADE
);