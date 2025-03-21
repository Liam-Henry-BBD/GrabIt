
CREATE TABLE [grabit].Users (
	UserID INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
	GitHubID VARCHAR(100) NOT NULL UNIQUE,
	JoinedAt DATETIME NOT NULL
	);
GO

CREATE TABLE [grabit].Roles (
	RoleID TINYINT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
	RoleTitle VARCHAR(50) NOT NULL
	);
GO

CREATE TABLE [grabit].Projects (
	ProjectID INT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
	ProjectName VARCHAR(50) NOT NULL,
	ProjectDescription VARCHAR(255) NOT NULL,
	CreatedAt DATETIME NOT NULL,
	UpdatedAt DATETIME
	);
GO

CREATE TABLE [grabit].TaskPoints (
	TaskPointID TINYINT  PRIMARY KEY NOT NULL,
	TaskDifficulty VARCHAR(20) UNIQUE NOT NULL
	);
GO

CREATE TABLE [grabit].TaskStatus (
	TaskStatusID TINYINT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
	StatusName VARCHAR(50) NOT NULL
	);
GO

CREATE TABLE [grabit].Tasks (
	TaskID INT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
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
	FOREIGN KEY (ProjectID) REFERENCES [grabit].Projects(ProjectID),
	FOREIGN KEY (TaskPointID) REFERENCES [grabit].TaskPoints(TaskPointID),
	FOREIGN KEY (TaskStatusID) REFERENCES [grabit].TaskStatus(TaskStatusID)
	);
GO

CREATE TABLE [grabit].ProjectCollaborators (
	ProjectCollaboratorID INT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
	UserID INT NOT NULL,
	ProjectID INT NOT NULL,
	RoleID TINYINT NOT NULL,
	JoinedAt DATETIME NOT NULL,
	isActive BIT NOT NULL DEFAULT 1,
	UNIQUE(ProjectID, UserID),
	FOREIGN KEY (UserID) REFERENCES [grabit].Users(UserID),
	FOREIGN KEY (ProjectID) REFERENCES [grabit].Projects(ProjectID),
	FOREIGN KEY (RoleID) REFERENCES [grabit].Roles(RoleID)
	);
GO

CREATE TABLE [grabit].TaskCollaborators (
	TaskCollaboratorID INT PRIMARY KEY IDENTITY(1, 1) NOT NULL,
	UserID INT NOT NULL,
	RoleID TINYINT NOT NULL,
	TaskID INT NOT NULL,
	JoinedAt DATETIME NOT NULL,
	isActive BIT NOT NULL DEFAULT 1,
	UNIQUE(TaskID, UserID),
	FOREIGN KEY (UserID) REFERENCES [grabit].Users(UserID),
	FOREIGN KEY (RoleID) REFERENCES [grabit].Roles(RoleID),
	FOREIGN KEY (TaskID) REFERENCES [grabit].Tasks(TaskID)
	);
GO


