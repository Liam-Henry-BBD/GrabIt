-- Retrieve all tasks that are grabbed by a specific user including their role.
CREATE FUNCTION [grabit].udfTasksGrabbedByUserAndTheirRole (@UserID INT)
RETURNS TABLE
AS
RETURN (
		SELECT [grabit].Tasks.TaskID,
			[grabit].Tasks.TaskName,
			[grabit].TaskStatus.StatusName
		FROM [grabit].Tasks
		JOIN [grabit].TaskStatus ON [grabit].Tasks.TaskStatusID = [grabit].TaskStatus.TaskStatusID
		JOIN [grabit].TaskCollaborators ON [grabit].Tasks.TaskID = [grabit].TaskCollaborators.TaskID
		JOIN [grabit].Roles ON [grabit].TaskCollaborators.RoleID = [grabit].Roles.RoleID
		WHERE [grabit].TaskCollaborators.UserID = @UserID
			AND [grabit].TaskStatus.StatusName = 'Grabbed'
		);
GO

-- Retrieve all completed tasks for a given project, including the task completion date.
CREATE FUNCTION [grabit].udfCompletedTasksAndDates (@ProjectID INT)
RETURNS TABLE
AS
RETURN (
		SELECT [grabit].Tasks.TaskID,
			[grabit].Tasks.TaskName,
			[grabit].TaskStatus.StatusName,
			[grabit].Tasks.TaskCompletedAt
		FROM [grabit].Tasks
		JOIN [grabit].TaskStatus ON [grabit].Tasks.TaskStatusID = [grabit].TaskStatus.TaskStatusID
		WHERE [grabit].Tasks.ProjectID = @ProjectID
			AND [grabit].TaskStatus.StatusName = 'Complete'
		);
GO

-- Get a list of active tasks (those that are either available or grabbed) within a specific project.
CREATE FUNCTION [grabit].udfActiveTasksAvailableAndGrabbed (@ProjectID INT)
RETURNS TABLE
AS
RETURN (
		SELECT [grabit].Tasks.TaskID,
			[grabit].Tasks.TaskName,
			[grabit].TaskStatus.StatusName
		FROM [grabit].Tasks
		JOIN [grabit].TaskStatus ON [grabit].Tasks.TaskStatusID = [grabit].TaskStatus.TaskStatusID
		WHERE [grabit].Tasks.ProjectID = @ProjectID
			AND [grabit].TaskStatus.StatusName IN ('Available', 'Grabbed')
		);
GO

-- Retrieve all users who are collaborators on a specific task and include their roles in the result.
CREATE FUNCTION [grabit].udfTaskCollaboratorsWithRoles (@TaskID INT)
RETURNS TABLE
AS
RETURN (
		SELECT [grabit].Users.UserID,
			[grabit].Users.GitHubID,
			[grabit].Roles.RoleTitle
		FROM [grabit].TaskCollaborators
		JOIN [grabit].Users ON [grabit].TaskCollaborators.UserID = [grabit].Users.UserID
		JOIN [grabit].Roles ON [grabit].TaskCollaborators.RoleID = [grabit].Roles.RoleID
		WHERE [grabit].TaskCollaborators.TaskID = @TaskID
		);
GO

-- List all tasks within a specific project and show whether they are available, grabbed, in review, or completed, along with their deadlines.
CREATE FUNCTION [grabit].udfProjectTasksWithStatusAndDeadlines (@ProjectID INT)
RETURNS TABLE
AS
RETURN (
		SELECT [grabit].Tasks.TaskID,
			[grabit].Tasks.TaskName,
			[grabit].TaskStatus.StatusName,
			[grabit].Tasks.TaskDeadline
		FROM [grabit].Tasks
		JOIN [grabit].TaskStatus ON [grabit].Tasks.TaskStatusID = [grabit].TaskStatus.TaskStatusID
		WHERE [grabit].Tasks.ProjectID = @ProjectID
		);
GO

-- Retrieve all users associated with a project, their roles, and tasks assigned to them in the given project.
CREATE FUNCTION [grabit].udfProjectUsersRolesTasks (@ProjectID INT)
RETURNS TABLE
AS
RETURN (
		SELECT DISTINCT [grabit].Users.UserID,
			[grabit].Users.GitHubID,
			[grabit].Roles.RoleTitle,
			[grabit].Tasks.TaskID,
			[grabit].Tasks.TaskName,
			[grabit].Tasks.TaskDeadline
		FROM [grabit].ProjectCollaborators
		JOIN [grabit].Users ON [grabit].ProjectCollaborators.UserID = [grabit].Users.UserID
		JOIN [grabit].Roles ON [grabit].ProjectCollaborators.RoleID = [grabit].Roles.RoleID
		JOIN [grabit].Tasks ON [grabit].ProjectCollaborators.ProjectID = [grabit].Tasks.ProjectID
		LEFT JOIN [grabit].TaskCollaborators ON [grabit].Tasks.TaskID = [grabit].TaskCollaborators.TaskID
		WHERE [grabit].ProjectCollaborators.ProjectID = @ProjectID
		);
GO

-- Get the total time spent on tasks by a user in a project (difference between created and completed times).
CREATE FUNCTION [grabit].udfTotalTimeSpentOnTasks (
	@UserID INT,
	@ProjectID INT
	)
RETURNS TABLE
AS
RETURN (
		SELECT [grabit].Users.GitHubID AS Username,
			[grabit].Tasks.TaskName AS Task,
			SUM(DATEDIFF(hour, [grabit].Tasks.TaskCreatedAt, [grabit].Tasks.TaskCompletedAt)) AS TotalHours
		FROM [grabit].Users
		JOIN [grabit].TaskCollaborators ON [grabit].TaskCollaborators.UserID = @UserID
		JOIN [grabit].Tasks ON [grabit].TaskCollaborators.TaskID = [grabit].Tasks.TaskID
		JOIN [grabit].Projects ON [grabit].Projects.ProjectID = [grabit].Tasks.ProjectID
		WHERE [grabit].Users.UserID = @UserID
			AND [grabit].Projects.ProjectID = @ProjectID
		GROUP BY [grabit].Users.GitHubID,
			[grabit].Tasks.TaskName
		);
GO


