-- Get the total number of tasks in a specific project using a project ID.
CREATE FUNCTION [grabit].udfTotalTasksUsingProject (
	@ProjectID INT = NULL,
	@ProjectName VARCHAR(100) = NULL
	)
RETURNS INT
AS
BEGIN
	DECLARE @TotalTasksPerProj INT;

	SELECT @TotalTasksPerProj = COUNT([grabit].Tasks.TaskID)
	FROM Tasks
	JOIN [grabit].Projects ON [grabit].Tasks.ProjectID = [grabit].Projects.ProjectID
	WHERE (
			[grabit].Projects.ProjectID = @ProjectID
			OR @ProjectID IS NULL
			)
		AND (
			[grabit].Projects.ProjectName = @ProjectName
			OR @ProjectName IS NULL
			);

	RETURN @TotalTasksPerProj;
END;
GO

-- Get the total number of projects a specific user is involved in.
CREATE FUNCTION [grabit].udfTotalUserProjects (
	@UserID INT = NULL,
	@GitHubID VARCHAR(100) = NULL
	)
RETURNS INT
AS
BEGIN
	DECLARE @TotalProjects INT;

	SELECT @TotalProjects = COUNT([grabit].ProjectCollaborators.ProjectID)
	FROM [grabit].ProjectCollaborators
	JOIN [grabit].Users ON [grabit].ProjectCollaborators.UserID = [grabit].Users.UserID
	WHERE (
			[grabit].Users.UserID = @UserID
			OR @UserID IS NULL
			)
		AND (
			[grabit].Users.GitHubID = @GitHubID
			OR @GitHubID IS NULL
			);

	RETURN @TotalProjects;
END;
GO

-- Retrieve the role of a user for a specific task (e.g., task grabber or task collaborator).
CREATE FUNCTION [grabit].udfGetUserRoleForTask (
	@UserID INT,
	@TaskID INT
	)
RETURNS VARCHAR(50)
AS
BEGIN
	DECLARE @RoleTitle VARCHAR(50);

	SELECT @RoleTitle = [grabit].Roles.RoleTitle
	FROM [grabit].TaskCollaborators
	JOIN [grabit].Roles ON [grabit].TaskCollaborators.RoleID = [grabit].Roles.RoleID
	WHERE [grabit].TaskCollaborators.UserID = @UserID
		AND [grabit].TaskCollaborators.TaskID = @TaskID
		AND [grabit].TaskCollaborators.isActive = 1;

	RETURN @RoleTitle;
END;
GO

-- Get the total number of tasks a specific user is involved in.
CREATE FUNCTION [grabit].udfTotalUserTasks (
	@UserID INT = NULL,
	@GitHubID VARCHAR(100) = NULL
	)
RETURNS INT
AS
BEGIN
	DECLARE @TotalTasks INT;

	SELECT @TotalTasks = COUNT([grabit].TaskCollaborators.TaskID)
	FROM [grabit].TaskCollaborators
	JOIN [grabit].Users ON [grabit].TaskCollaborators.UserID = [grabit].Users.UserID
	WHERE (
			[grabit].Users.UserID = @UserID
			OR @UserID IS NULL
			)
		AND (
			[grabit].Users.GitHubID = @GitHubID
			OR @GitHubID IS NULL
			);

	RETURN @TotalTasks;
END;
GO

-- Get the average points per project.
CREATE FUNCTION [grabit].udfAverageTaskPointsPerProject (@ProjectID INT)
RETURNS FLOAT
AS
BEGIN
	DECLARE @AveragePoints FLOAT;

	SELECT @AveragePoints = AVG([grabit].TaskPoints.TaskPointID)
	FROM [grabit].Tasks
	JOIN [grabit].TaskPoints ON [grabit].Tasks.TaskPointID = [grabit].TaskPoints.TaskPointID
	WHERE [grabit].Tasks.ProjectID = @ProjectID;

	RETURN @AveragePoints;
END;
GO

-- Get the total number of tasks that were completed after a specific date.
CREATE FUNCTION [grabit].udfTotalCompletedTasksAfterDate (@SpecificDate DATETIME)
RETURNS INT
AS
BEGIN
	DECLARE @TotalCompletedTasks INT;

	SELECT @TotalCompletedTasks = COUNT([grabit].[Tasks].TaskID)
	FROM [grabit].Tasks
	WHERE [grabit].Tasks.TaskCompletedAt > @SpecificDate;

	RETURN @TotalCompletedTasks;
END;
GO

-- Get the total number of tasks in a specific status (e.g., "Review") across all projects.
CREATE FUNCTION [grabit].udfTotalTasksInStatus (@StatusName VARCHAR(50))
RETURNS INT
AS
BEGIN
	DECLARE @TotalTasks INT;

	SELECT @TotalTasks = COUNT([grabit].Tasks.TaskID)
	FROM [grabit].Tasks
	JOIN [grabit].TaskStatus ON [grabit].Tasks.TaskStatusID = [grabit].TaskStatus.TaskStatusID
	WHERE [grabit].TaskStatus.StatusName = @StatusName;

	RETURN @TotalTasks;
END;
GO

-- Get the total number of users in a specific role across all projects.
CREATE FUNCTION [grabit].udfTotalUsersInRole (
	@RoleTitle VARCHAR(50) = NULL,
	@RoleID INT = NULL
	)
RETURNS INT
AS
BEGIN
	DECLARE @TotalUsersInRole INT;

	SELECT @TotalUsersInRole = COUNT([grabit].ProjectCollaborators.UserID)
	FROM [grabit].ProjectCollaborators
	JOIN [grabit].Roles ON [grabit].ProjectCollaborators.RoleID = [grabit].Roles.RoleID
	JOIN [grabit].TaskCollaborators ON [grabit].ProjectCollaborators.UserID = [grabit].TaskCollaborators.UserID
	WHERE (
		[grabit].Roles.RoleTitle = @RoleTitle
			OR @RoleTitle IS NULL
			)
		AND (
			[grabit].Roles.RoleID = @RoleID
			OR @RoleID IS NULL
			)
		AND [grabit].TaskCollaborators.TaskID IN (1, 2, 3, 4);

	RETURN @TotalUsersInRole;
END;
GO

-- Get the total number of tasks that are overdue (past deadline) for a specific project.
CREATE FUNCTION [grabit].udfOverdueTasksPerProject (@ProjectID INT)
RETURNS INT
AS
BEGIN
	DECLARE @OverdueTasks INT;

	SELECT @OverdueTasks = COUNT([grabit].Tasks.TaskID)
	FROM [grabit].Tasks
	WHERE [grabit].Tasks.ProjectID = @ProjectID
		AND [grabit].Tasks.TaskDeadline < GETDATE()
		AND [grabit].Tasks.TaskStatusID <> (
			SELECT TaskStatusID
			FROM [grabit].TaskStatus
			WHERE StatusName = 'Completed'
			);

	RETURN @OverdueTasks;
END;
GO

-- Get the total number of tasks that are overdue (past deadline) for a specific user.
CREATE FUNCTION [grabit].udfTotalOverdueTasksPerUser (@UserID INT)
RETURNS INT
AS
BEGIN
	DECLARE @TotalOverdueTasks INT;

	SELECT @TotalOverdueTasks = COUNT([grabit].Tasks.TaskID)
	FROM [grabit].Tasks
	JOIN [grabit].TaskCollaborators ON [grabit].Tasks.TaskID = [grabit].TaskCollaborators.TaskID
	WHERE [grabit].TaskCollaborators.UserID = @UserID
		AND [grabit].Tasks.TaskDeadline < GETDATE()
		AND [grabit].Tasks.TaskStatusID <> (
			SELECT TaskStatusID
			FROM [grabit].TaskStatus
			WHERE StatusName = 'Completed'
			);

	RETURN @TotalOverdueTasks;
END;
GO

-- Get the average time spent on tasks in a specific project.
CREATE FUNCTION [grabit].udfAverageTimeOnTasks (@ProjectID INT)
RETURNS INT
AS
BEGIN
	DECLARE @AveTimeOnTask INT;

	SELECT @AveTimeOnTask = (AVG(DATEDIFF(hour, [grabit].Tasks.TaskCreatedAt, [grabit].Tasks.TaskCompletedAt)))
	FROM [grabit].Tasks
	JOIN [grabit].Projects ON [grabit].Tasks.ProjectID = [grabit].Projects.ProjectID
	WHERE [grabit].Projects.ProjectID = @ProjectID;

	RETURN @AveTimeOnTask;
END;
GO


