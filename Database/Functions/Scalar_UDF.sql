-- Get the total number of tasks in a specific project using a project ID.
CREATE FUNCTION [dbo].[udfTotalTasksUsingProjectID]
(
@ProjectID INT
)
RETURNS INT
AS 
BEGIN
 DECLARE @TotalTasksPerProj INT
 SELECT @TotalTasksPerProj = COUNT(TaskID)
 FROM Tasks
 WHERE ProjectID = @ProjectID

RETURN @TotalTasksPerProj
END


CREATE FUNCTION [dbo].[udfTotalTasksUsingProjectName]
(
@ProjectName VARCHAR(100)
)
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

-- Get the total number of projects a specific user is involved in.
CREATE FUNCTION [dbo].[udfTotalUserProjectsPerUserID]
(
@UserID INT
)
RETURNS INT
AS
BEGIN
 DECLARE @ProjectsPerUserID INT
 SELECT @ProjectsPerUserID = COUNT(ProjectID)
 FROM ProjectCollaborators
 WHERE UserID = @UserID

RETURN @ProjectsPerUserID
END

CREATE FUNCTION [dbo].[udfTotalUserProjectsPerGitHubID]
(
@GitHubID VARCHAR(100)
)
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

-- Retrieve the role of a user for a specific task (e.g., task grabber or task collaborator).
CREATE FUNCTION [dbo].[udfRoleOfUserInTask]
(
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


-- Get the total number of tasks a specific user is involved in.
CREATE FUNCTION [dbo].[udfTotalUserTasksPerUserID]
(
@UserID INT
)
RETURNS INT
AS
BEGIN
 DECLARE @TasksPerUserID INT
 SELECT @TasksPerUserID = COUNT(TaskID)
 FROM TaskCollaborators
 WHERE UserID = @UserID

RETURN @TasksPerUserID
END

CREATE FUNCTION [dbo].[udfTotalUserTasksPerGitHubID]
(
@GitHubID VARCHAR(100)
)
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

-- Get the average points per project.
CREATE FUNCTION [dbo].[udfAverageTaskPointsPerProject]
(
@ProjectID INT
)
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

-- Get the total number of tasks that were completed after a specific date.
CREATE FUNCTION [dbo].[udfTotalCompletedTasksAfterDate]
(
@SpecificDate DATETIME
)
RETURNS INT
AS
BEGIN
 DECLARE @TotalCompletedTasks INT
 SELECT @TotalCompletedTasks = COUNT(TaskID)
 FROM Tasks
 WHERE TaskCompletedAt > @SpecificDate
 RETURN @TotalCompletedTasks
END

-- Get the total number of tasks in a specific status (e.g., "Review") across all projects.
