USE MASTER
GO

CREATE DATABASE GrabIt
GO

USE GrabIt
GO

-- Create Users table
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    GitHubID VARCHAR(100) NOT NULL UNIQUE,
    JoinedAt DATETIME NOT NULL
);
GO

-- Create Roles table
CREATE TABLE Roles (
    RoleID TINYINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    RoleTitle VARCHAR(50) NOT NULL
);
GO

-- Create Projects table
CREATE TABLE Projects (
    ProjectID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ProjectName VARCHAR(50) NOT NULL,
    ProjectDescription VARCHAR(255) NOT NULL,
    CreatedAt DATETIME NOT NULL,
    UpdatedAt DATETIME
);
GO

-- Create TaskPoints table
CREATE TABLE TaskPoints (
    TaskPointID TINYINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    PointValue TINYINT NOT NULL
);
GO

-- Create TaskStatus table
CREATE TABLE TaskStatus (
    TaskStatusID VARCHAR(20) PRIMARY KEY NOT NULL,
    StatusName VARCHAR(50) NOT NULL
);
GO

-- Create Tasks table
CREATE TABLE Tasks (
    TaskID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    ProjectID INT NOT NULL,
    TaskPointID TINYINT NOT NULL,
    TaskStatusID TINYINT NOT NULL,
    TaskName VARCHAR(50) NOT NULL,
    TaskDescription VARCHAR(255) NOT NULL,
    TaskDeadline DATETIME,
    TaskCreatedAt DATETIME NOT NULL,
    TaskUpdatedAt DATETIME,
    TaskReviewRequestedAt DATETIME,
    TaskCompletedAt DATETIME,
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID),
    FOREIGN KEY (TaskPointID) REFERENCES TaskPoints(TaskPointID),
    FOREIGN KEY (TaskStatusID) REFERENCES TaskStatus(TaskStatusID)
);
GO

-- Create ProjectCollaborators table
CREATE TABLE ProjectCollaborators (
    ProjectCollaboratorID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    UserID INT NOT NULL,
    ProjectID INT NOT NULL,
    RoleID TINYINT NOT NULL,
    JoinedAt DATETIME NOT NULL,
    isActive BIT NOT NULL DEFAULT 1
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID),
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);
GO

-- Create TaskCollaborators table
CREATE TABLE TaskCollaborators (
    TaskCollaboratorID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    UserID INT NOT NULL,
    RoleID TINYINT NOT NULL,
    TaskID INT NOT NULL,
    JoinedAt DATETIME NOT NULL,
    isActive BIT NOT NULL DEFAULT 1
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID),
    FOREIGN KEY (TaskID) REFERENCES Tasks(TaskID)
);
GO