USE GrabIt
GO

-- Get the total number of tasks in a specific project using a project ID.
CREATE FUNCTION [dbo].[udfTotalTasksUsingProject] (
	@ProjectID INT = NULL,
	@ProjectName VARCHAR(100) = NULL
	)
RETURNS INT
AS
BEGIN
	DECLARE @TotalTasksPerProj INT;

	SELECT @TotalTasksPerProj = COUNT(Tasks.TaskID)
	FROM Tasks
	JOIN Projects ON Tasks.ProjectID = Projects.ProjectID
	WHERE (
			Projects.ProjectID = @ProjectID
			OR @ProjectID IS NULL
			)
		AND (
			Projects.ProjectName = @ProjectName
			OR @ProjectName IS NULL
			);

	RETURN @TotalTasksPerProj;
END;
GO

-- Get the total number of projects a specific user is involved in.
CREATE FUNCTION [dbo].[udfTotalUserProjects] (
	@UserID INT = NULL,
	@GitHubID VARCHAR(100) = NULL
	)
RETURNS INT
AS
BEGIN
	DECLARE @TotalProjects INT;

	SELECT @TotalProjects = COUNT(ProjectCollaborators.ProjectID)
	FROM ProjectCollaborators
	JOIN Users ON ProjectCollaborators.UserID = Users.UserID
	WHERE (
			Users.UserID = @UserID
			OR @UserID IS NULL
			)
		AND (
			Users.GitHubID = @GitHubID
			OR @GitHubID IS NULL
			);

	RETURN @TotalProjects;
END;
GO

-- Retrieve the role of a user for a specific task (e.g., task grabber or task collaborator).
CREATE FUNCTION [dbo].[udfGetUserRoleForTask] (
	@UserID INT,
	@TaskID INT
	)
RETURNS VARCHAR(50)
AS
BEGIN
	DECLARE @RoleTitle VARCHAR(50);

	SELECT @RoleTitle = Roles.RoleTitle
	FROM TaskCollaborators
	JOIN Roles ON TaskCollaborators.RoleID = Roles.RoleID
	WHERE TaskCollaborators.UserID = @UserID
		AND TaskCollaborators.TaskID = @TaskID
		AND TaskCollaborators.isActive = 1;

	RETURN @RoleTitle;
END;
GO

-- Get the total number of tasks a specific user is involved in.
CREATE FUNCTION [dbo].[udfTotalUserTasks] (
	@UserID INT = NULL,
	@GitHubID VARCHAR(100) = NULL
	)
RETURNS INT
AS
BEGIN
	DECLARE @TotalTasks INT;

	SELECT @TotalTasks = COUNT(TaskCollaborators.TaskID)
	FROM TaskCollaborators
	JOIN Users ON TaskCollaborators.UserID = Users.UserID
	WHERE (
			Users.UserID = @UserID
			OR @UserID IS NULL
			)
		AND (
			Users.GitHubID = @GitHubID
			OR @GitHubID IS NULL
			);

	RETURN @TotalTasks;
END;
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
CREATE FUNCTION [dbo].[udfTotalUsersInRole] (
	@RoleTitle VARCHAR(50) = NULL,
	@RoleID INT = NULL
	)
RETURNS INT
AS
BEGIN
	DECLARE @TotalUsersInRole INT;

	SELECT @TotalUsersInRole = COUNT(ProjectCollaborators.UserID)
	FROM ProjectCollaborators
	JOIN Roles ON ProjectCollaborators.RoleID = Roles.RoleID
	JOIN TaskCollaborators ON ProjectCollaborators.UserID = TaskCollaborators.UserID
	WHERE (
			Roles.RoleTitle = @RoleTitle
			OR @RoleTitle IS NULL
			)
		AND (
			Roles.RoleID = @RoleID
			OR @RoleID IS NULL
			)
		AND TaskCollaborators.TaskID IN (1, 2, 3, 4);

	RETURN @TotalUsersInRole;
END;
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


