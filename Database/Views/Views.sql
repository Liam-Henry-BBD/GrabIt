
-- View of available tasks
CREATE VIEW vwAvailableTasks
AS
SELECT t.TaskID,
	t.TaskName,
	t.TaskDescription,
	t.TaskPointID
FROM [grabit].[Tasks] t
WHERE t.TaskStatusID = 1;
GO

-- View of grabbed tasks
CREATE VIEW vwGrabbedTasks
AS
SELECT t.TaskID,
	t.TaskName,
	t.TaskDescription,
	t.TaskPointID
FROM [grabit].[Tasks] t
WHERE t.TaskStatusID = 2;
GO

-- View of review tasks
CREATE VIEW vwReviewTasks
AS
SELECT t.TaskID,
	t.TaskName,
	t.TaskDescription,
	t.TaskPointID
FROM [grabit].[Tasks] t
WHERE t.TaskStatusID = 3;
GO

-- View of completed tasks
CREATE VIEW vwCompletedTasks
AS
SELECT t.TaskID,
	t.TaskName,
	t.TaskDescription,
	t.TaskCompletedAt
FROM [grabit].[Tasks] t
WHERE t.TaskStatusID = 4;
GO

-- View of user project roles
CREATE VIEW vwUserProjectRoles
AS
SELECT pc.UserID,
	u.GitHubID,
	pc.ProjectID,
	p.ProjectName,
	r.RoleTitle,
	pc.JoinedAt
FROM [grabit].[ProjectCollaborators] pc
JOIN [grabit].[Roles] r ON pc.RoleID = r.RoleID
JOIN [grabit].[Users] u ON pc.UserID = u.UserID
JOIN [grabit].[Projects] p ON pc.ProjectID = p.ProjectID;
GO

-- View of project completion rate
CREATE VIEW vwProjectCompletionRate
AS
SELECT t.ProjectID,
	COUNT(t.TaskID) AS TotalTasks,
	COUNT(CASE 
			WHEN t.TaskCompletedAt IS NOT NULL
				THEN t.TaskID
			END) AS TasksCompleted,
	CONVERT(DECIMAL(5, 2), COUNT(CASE 
				WHEN t.TaskCompletedAt IS NOT NULL
					THEN t.TaskID
				END) * 100.0 / COUNT(t.TaskID)) AS CompletionRate
FROM [grabit].[Tasks] t
GROUP BY t.ProjectID;
GO

-- View of Total Points for all Projects
CREATE VIEW vwProjectLeaderBoards
AS
SELECT u.UserID,
	u.GitHubID,
	SUM(COALESCE(tp.TaskPointID, 0)) AS TotalScore
FROM (
	SELECT DISTINCT tc.UserID,
		tc.TaskID
	FROM [grabit].[TaskCollaborators] tc
	JOIN [grabit].[Tasks] t ON t.TaskID = tc.TaskID
	WHERE tc.isActive = 1 -- Filter active users 
	) AS distinct_user_tasks
JOIN [grabit].[Users] u ON u.UserID = distinct_user_tasks.UserID
JOIN [grabit].[Tasks] t ON t.TaskID = distinct_user_tasks.TaskID
JOIN [grabit].[TaskPoints] tp ON tp.TaskPointID = t.TaskPointID
WHERE t.TaskStatusID = 1
GROUP BY u.UserID,
	u.GitHubID
GO

-- view of users and associated tasks
CREATE VIEW vwUserTasks
AS
SELECT tc.UserID,
	u.GitHubID,
	t.TaskID,
	t.TaskName,
	t.ProjectID,
	p.ProjectName
FROM [grabit].[TaskCollaborators] tc
JOIN [grabit].[Tasks] t ON tc.TaskID = t.TaskID
JOIN [grabit].[Projects] p ON t.ProjectID = p.ProjectID
JOIN [grabit].[Users] u ON tc.UserID = u.UserID
GO

-- view of users that are inactive on a project
CREATE VIEW vwInactiveProjectUsers
AS
SELECT DISTINCT u.UserID,
	u.GitHubID,
	t.ProjectID,
	p.ProjectName
FROM [grabit].[Users] u
JOIN [grabit].[ProjectCollaborators] pc ON u.UserID = pc.UserID
JOIN [grabit].[Tasks] t ON pc.ProjectID = t.ProjectID
JOIN [grabit].[Projects] p ON pc.ProjectID = p.ProjectID
WHERE pc.isActive = 0;
GO

-- view of which users are inactive on a task
CREATE VIEW vwInactiveTaskUsers
AS
SELECT DISTINCT u.UserID,
	u.GitHubID,
	tc.TaskID,
	t.ProjectID
FROM [grabit].[Users] u
JOIN [grabit].[TaskCollaborators] tc ON u.UserID = tc.UserID
JOIN [grabit].[Tasks] t ON tc.TaskID = t.TaskID
WHERE tc.isActive = 0;
GO

---view of all easy tasks
CREATE VIEW vwEasyTasks
AS
SELECT *
FROM [grabit].[Tasks]
WHERE [grabit].[Tasks].TaskPointID = 'Easy'
GO

-- view of all medium tasks
CREATE VIEW vwMediumTasks
AS
SELECT *
FROM [grabit].[Tasks]
WHERE [grabit].[Tasks].TaskPointID = 'Medium'
GO

-- view of all hard tasks
CREATE VIEW vwHardTasks
AS
SELECT *
FROM [grabit].[Tasks]
WHERE [grabit].[Tasks].TaskPointID = 'Hard'
GO

-- view of the number of collaborators on each project
CREATE VIEW vwProjectCollaboratorCount
AS
SELECT pc.ProjectID,
	COUNT(pc.UserID) AS CollaboratorCount
FROM [grabit].[ProjectCollaborators] pc
GROUP BY pc.ProjectID;
GO

-- view of the number of collaborators on each task
CREATE VIEW vwTaskCollaboratorCount
AS
SELECT tc.TaskID,
	COUNT(tc.UserID) AS CollaboratorCount
FROM [grabit].[TaskCollaborators] tc
GROUP BY tc.TaskID;
GO

-- view of users collaboration counts (total number of projects and tasks)
CREATE VIEW vwUserCollaborationCount
AS
SELECT u.UserID,
	u.GitHubID,
	COUNT(DISTINCT tc.TaskID) AS TaskCount,
	COUNT(DISTINCT pc.ProjectID) AS ProjectCount
FROM [grabit].[Users] u
LEFT JOIN [grabit].[TaskCollaborators] tc ON u.UserID = tc.UserID
LEFT JOIN [grabit].[ProjectCollaborators] pc ON u.UserID = pc.UserID
GROUP BY u.UserID,
	u.GitHubID;
GO

-- view of Projects statuses
CREATE VIEW vwProjectStatus
AS
SELECT p.ProjectName,
	u.GitHubID AS ProjectOwner,
	p.CreatedAt,
	p.UpdatedAt,
	iif(pc.isActive = 1, 'Open', 'Closed') AS ProjectStatus
FROM [grabit].[Projects] p
JOIN [grabit].[ProjectCollaborators] pc ON p.ProjectID = pc.ProjectID
JOIN [grabit].[Users] u ON pc.UserID = u.UserID
WHERE pc.RoleID = 1
GO
