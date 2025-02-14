-- Retrieve all tasks that are grabbed by a specific user including their role.
CREATE FUNCTION [dbo].[udfTasksGrabbedByUserAndTheirRole] (@UserID INT)
RETURNS TABLE
AS
RETURN (
		SELECT Tasks.TaskID,
			Tasks.TaskName,
			TaskStatus.StatusName
		FROM Tasks
		JOIN TaskStatus ON Tasks.TaskStatusID = TaskStatus.TaskStatusID
		JOIN TaskCollaborators ON Tasks.TaskID = TaskCollaborators.TaskID
		JOIN Roles ON TaskCollaborators.RoleID = Roles.RoleID
		WHERE TaskCollaborators.UserID = @UserID
			AND TaskStatus.StatusName = 'Grabbed'
		);
GO

-- Retrieve all completed tasks for a given project, including the task completion date.
CREATE FUNCTION [dbo].[udfCompletedTasksAndDates] (@ProjectID INT)
RETURNS TABLE
AS
RETURN (
		SELECT Tasks.TaskID,
			Tasks.TaskName,
			TaskStatus.StatusName,
			Tasks.TaskCompletedAt
		FROM Tasks
		JOIN TaskStatus ON Tasks.TaskStatusID = TaskStatus.TaskStatusID
		WHERE Tasks.ProjectID = @ProjectID
			AND TaskStatus.StatusName = 'Complete'
		);
GO

-- Get a list of active tasks (those that are either available or grabbed) within a specific project.
CREATE FUNCTION [dbo].[udfActiveTasksAvailableAndGrabbed] (@ProjectID INT)
RETURNS TABLE
AS
RETURN (
		SELECT Tasks.TaskID,
			Tasks.TaskName,
			TaskStatus.StatusName
		FROM Tasks
		JOIN TaskStatus ON Tasks.TaskStatusID = TaskStatus.TaskStatusID
		WHERE Tasks.ProjectID = @ProjectID
			AND TaskStatus.StatusName IN ('Available', 'Grabbed')
		);
GO

--Retrieve all users who are collaborators on a specific task and include their roles in the result.
CREATE FUNCTION [dbo].[udfTaskCollaboratorsWithRoles] (@TaskID INT)
RETURNS TABLE
AS
RETURN (
		SELECT Users.UserID,
			Users.GitHubID,
			Roles.RoleTitle
		FROM TaskCollaborators
		JOIN Users ON TaskCollaborators.UserID = Users.UserID
		JOIN Roles ON TaskCollaborators.RoleID = Roles.RoleID
		WHERE TaskCollaborators.TaskID = @TaskID
		);
GO

--List all tasks within a specific project and show whether they are available, grabbed, in review, or completed, along with their deadlines.
CREATE FUNCTION [dbo].[udfProjectTasksWithStatusAndDeadlines] (@ProjectID INT)
RETURNS TABLE
AS
RETURN (
		SELECT Tasks.TaskID,
			Tasks.TaskName,
			TaskStatus.StatusName,
			Tasks.TaskDeadline
		FROM Tasks
		JOIN TaskStatus ON Tasks.TaskStatusID = TaskStatus.TaskStatusID
		WHERE Tasks.ProjectID = @ProjectID
		);
GO

-- Retrieve all users associated with a project, their roles, and tasks assigned to them in the given project.
CREATE FUNCTION [dbo].[udfProjectUsersRolesTasks] (@ProjectID INT)
RETURNS TABLE
AS
RETURN (
		SELECT DISTINCT Users.UserID,
			Users.GitHubID,
			Roles.RoleTitle,
			Tasks.TaskID,
			Tasks.TaskName,
			Tasks.TaskDeadline
		FROM ProjectCollaborators
		JOIN Users ON ProjectCollaborators.UserID = Users.UserID
		JOIN Roles ON ProjectCollaborators.RoleID = Roles.RoleID
		JOIN Tasks ON ProjectCollaborators.ProjectID = Tasks.ProjectID
		LEFT JOIN TaskCollaborators ON Tasks.TaskID = TaskCollaborators.TaskID
		WHERE ProjectCollaborators.ProjectID = @ProjectID
		);
GO

-- Get the total time spent on tasks by a user in a project (difference between created and completed times).
CREATE FUNCTION [dbo].[udfTotalTimeSpentOnTasks] (
	@UserID INT,
	@ProjectID INT
	)
RETURNS TABLE
AS
RETURN (
		SELECT Users.GitHubID AS Username,
			Tasks.TaskName AS Task,
			SUM(DATEDIFF(hour, Tasks.TaskCreatedAt, Tasks.TaskCompletedAt)) AS TotalHours
		FROM Users
		JOIN TaskCollaborators ON TaskCollaborators.UserID = @UserID
		JOIN Tasks ON TaskCollaborators.TaskID = Tasks.TaskID
		JOIN Projects ON Projects.ProjectID = Tasks.ProjectID
		WHERE Users.UserID = @UserID
			AND Projects.ProjectID = @ProjectID
		GROUP BY Users.GitHubID,
			Tasks.TaskName
		)
GO


