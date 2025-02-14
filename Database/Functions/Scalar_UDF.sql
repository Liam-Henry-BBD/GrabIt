-- Get the total number of tasks in a specific project using a project ID.
CREATE FUNCTION [dbo].[udfTotalTasksUsingProjectID] (@ProjectID INT)
RETURNS INT
AS
BEGIN
	DECLARE @TotalTasksPerProj INT

	SELECT @TotalTasksPerProj = COUNT(TaskID)
	FROM Tasks
	WHERE ProjectID = @ProjectID

	RETURN @TotalTasksPerProj
END
GO

CREATE FUNCTION [dbo].[udfTotalTasksUsingProjectName] (@ProjectName VARCHAR(100))
RETURNS INT
AS
BEGIN
	DECLARE @TotalTasksPerProj INT

	SELECT @TotalTasksPerProj = COUNT(TaskID)
	FROM Tasks
	JOIN Projects ON Projects.ProjectID = Tasks.ProjectID
	WHERE ProjectName = @ProjectName

	RETURN @TotalTasksPerProj
END
GO

-- Get the total number of projects a specific user is involved in.
CREATE FUNCTION [dbo].[udfTotalUserProjectsPerUserID] (@UserID INT)
RETURNS INT
AS
BEGIN
	DECLARE @ProjectsPerUserID INT

	SELECT @ProjectsPerUserID = COUNT(ProjectID)
	FROM ProjectCollaborators
	WHERE UserID = @UserID

	RETURN @ProjectsPerUserID
END
GO

CREATE FUNCTION [dbo].[udfTotalUserProjectsPerGitHubID] (@GitHubID VARCHAR(100))
RETURNS INT
AS
BEGIN
	DECLARE @ProjectsPerGitHubID INT

	SELECT @ProjectsPerGitHubID = COUNT(ProjectID)
	FROM ProjectCollaborators
	JOIN Users ON ProjectCollaborators.UserID = Users.UserID
	WHERE Users.GitHubID = @GitHubID

	RETURN @ProjectsPerGitHubID
END
GO

-- Retrieve the role of a user for a specific task (e.g., task grabber or task collaborator).
CREATE FUNCTION [dbo].[udfRoleOfUserInTask] (
	@UserID INT,
	@TaskID INT
	)
RETURNS VARCHAR(50)
AS
BEGIN
	DECLARE @UserRole VARCHAR(50)

	SELECT @UserRole = Roles.RoleTitle
	FROM TaskCollaborators
	JOIN Roles ON TaskCollaborators.RoleID = Roles.RoleID
	WHERE TaskCollaborators.TaskID = @TaskID
		AND TaskCollaborators.UserID = @UserID

	RETURN @UserRole
END
GO

-- Get the total number of tasks a specific user is involved in.
CREATE FUNCTION [dbo].[udfTotalUserTasksPerUserID] (@UserID INT)
RETURNS INT
AS
BEGIN
	DECLARE @TasksPerUserID INT

	SELECT @TasksPerUserID = COUNT(TaskID)
	FROM TaskCollaborators
	WHERE UserID = @UserID

	RETURN @TasksPerUserID
END
GO

CREATE FUNCTION [dbo].[udfTotalUserTasksPerGitHubID] (@GitHubID VARCHAR(100))
RETURNS INT
AS
BEGIN
	DECLARE @TasksPerGitHubID INT

	SELECT @TasksPerGitHubID = COUNT(TaskID)
	FROM TaskCollaborators
	JOIN Users ON TaskCollaborators.UserID = Users.UserID
	WHERE Users.GitHubID = @GitHubID

	RETURN @TasksPerGitHubID
END
GO

-- Get the average points per project.
CREATE FUNCTION [dbo].[udfAverageTaskPointsPerProject] (@ProjectID INT)
RETURNS FLOAT
AS
BEGIN
	DECLARE @AveragePoints FLOAT

	SELECT @AveragePoints = AVG(TaskPoints.PointValue)
	FROM Tasks
	JOIN TaskPoints ON Tasks.TaskPointID = TaskPoints.TaskPointID
	WHERE Tasks.ProjectID = @ProjectID

	RETURN @AveragePoints
END
GO

-- Get the total number of tasks that were completed after a specific date.
CREATE FUNCTION [dbo].[udfTotalCompletedTasksAfterDate] (@SpecificDate DATETIME)
RETURNS INT
AS
BEGIN
	DECLARE @TotalCompletedTasks INT

	SELECT @TotalCompletedTasks = COUNT(TaskID)
	FROM Tasks
	WHERE TaskCompletedAt > @SpecificDate

	RETURN @TotalCompletedTasks
END
GO

-- Get the total number of tasks in a specific status (e.g., "Review") across all projects.
CREATE FUNCTION [dbo].[udfTotalTasksInStatus] (@StatusName VARCHAR(50))
RETURNS INT
AS
BEGIN
	DECLARE @TotalTasks INT

	SELECT @TotalTasks = COUNT(Tasks.TaskID)
	FROM Tasks
	JOIN TaskStatus ON Tasks.TaskStatusID = TaskStatus.TaskStatusID
	WHERE TaskStatus.StatusName = @StatusName

	RETURN @TotalTasks
END
GO

-- Get the total number of users in a specific role across all projects.
CREATE FUNCTION [dbo].[udfTotalUsersInRole] (@RoleTitle VARCHAR(50))
RETURNS INT
AS
BEGIN
	DECLARE @TotalUsersInRole INT

	SELECT @TotalUsersInRole = COUNT(ProjectCollaborators.UserID)
	FROM ProjectCollaborators
	JOIN Roles ON ProjectCollaborators.RoleID = Roles.RoleID
	WHERE Roles.RoleTitle = @RoleTitle

	RETURN @TotalUsersInRole
END
GO

CREATE FUNCTION [dbo].[udfTotalUsersInRoleUsingID] (@RoleID INT)
RETURNS INT
AS
BEGIN
	DECLARE @TotalUsersInRoleID INT

	SELECT @TotalUsersInRoleID = COUNT(ProjectCollaborators.UserID)
	FROM ProjectCollaborators
	JOIN Roles ON ProjectCollaborators.RoleID = Roles.RoleID
	WHERE Roles.RoleID = @RoleID

	RETURN @TotalUsersInRoleID
END
GO

-- Get the total number of tasks that are overdue (past deadline) for a specific project.
CREATE FUNCTION [dbo].[udfOverdueTasksPerProject] (@ProjectID INT)
RETURNS INT
AS
BEGIN
	DECLARE @OverdueTasks INT

	SELECT @OverdueTasks = COUNT(TaskID)
	FROM Tasks
	WHERE ProjectID = @ProjectID
		AND TaskDeadline < GETDATE()
		AND TaskStatusID <> (
			SELECT TaskStatusID
			FROM TaskStatus
			WHERE StatusName = 'Completed'
			)

	RETURN @OverdueTasks
END
GO

-- Get the total number of tasks that are overdue (past deadline) for a specific user.
CREATE FUNCTION [dbo].[udfTotalOverdueTasksPerUser] (@UserID INT)
RETURNS INT
AS
BEGIN
	DECLARE @TotalOverdueTasks INT

	SELECT @TotalOverdueTasks = COUNT(Tasks.TaskID)
	FROM Tasks
	JOIN TaskCollaborators ON Tasks.TaskID = TaskCollaborators.TaskID
	WHERE TaskCollaborators.UserID = @UserID
		AND Tasks.TaskDeadline < GETDATE()
		AND Tasks.TaskStatusID <> (
			SELECT TaskStatusID
			FROM TaskStatus
			WHERE StatusName = 'Completed'
			)

	RETURN @TotalOverdueTasks
END
GO

-- Get the average time spent on tasks in a specific project.
CREATE FUNCTION [dbo].[udfAverageTimeOnTasks] (@ProjectID INT)
RETURNS INT
AS
BEGIN
	DECLARE @AveTimeOnTask INT

	SELECT @AveTimeOnTask = (AVG(DATEDIFF(hour, Tasks.TaskCreatedAt, Tasks.TaskCompletedAt)))
	FROM Tasks
	JOIN Projects ON Tasks.ProjectID = Projects.ProjectID
	WHERE Projects.ProjectID = @ProjectID

	RETURN @AveTimeOnTask
END
GO


