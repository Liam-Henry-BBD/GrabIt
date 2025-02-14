-- Retrieve all tasks that are grabbed by a specific user including their role.
CREATE FUNCTION [dbo].[udfTasksGrabbedByUserAndTheirRole]
(
@UserID INT
)
RETURNS TABLE
AS 
RETURN 
(
 SELECT Tasks.TaskID, 
        Tasks.TaskName, 
        TaskStatus.StatusName, 
        Roles.RoleTitle
 FROM Tasks
 JOIN TaskStatus ON Tasks.TaskStatusID = TaskStatus.TaskStatusID
 JOIN TaskCollaborators ON Tasks.TaskID = TaskCollaborators.TaskID
 JOIN Roles ON TaskCollaborators.RoleID = Roles.RoleID
 WHERE TaskCollaborators.UserID = @UserID
 AND TaskStatus.StatusName = 'Grabbed'
);

-- Retrieve all completed tasks for a given project, including the task completion date.
CREATE FUNCTION [dbo].[udfCompletedTasksAndDates]
(
@ProjectID INT
)
RETURNS TABLE
AS
RETURN
(
 SELECT Tasks.TaskID, 
        Tasks.TaskName, 
        TaskStatus.StatusName, 
        Tasks.TaskCompletedAt
 FROM Tasks
 JOIN TaskStatus ON Tasks.TaskStatusID = TaskStatus.TaskStatusID
 WHERE Tasks.ProjectID = @ProjectID
 AND TaskStatus.StatusName = 'Complete'
);

-- Get a list of active tasks (those that are either available or grabbed) within a specific project.
CREATE FUNCTION [dbo].[udfActiveTasksAvailableAndGrabbed]
(
@ProjectID INT
)
RETURNS TABLE 
AS
RETURN
(
 SELECT Tasks.TaskID, 
        Tasks.TaskName, 
        TaskStatus.StatusName
 FROM Tasks
 JOIN TaskStatus ON Tasks.TaskStatusID = TaskStatus.TaskStatusID
 WHERE Tasks.ProjectID = @ProjectID
 AND TaskStatus.StatusName IN ('Available', 'Grabbed')
);

--Retrieve all users who are collaborators on a specific task and include their roles in the result.
CREATE FUNCTION [dbo].[udfTaskCollaboratorsWithRoles]
(
@TaskID INT
)
RETURNS TABLE
AS
RETURN
(
 SELECT Users.UserID,
        Users.GitHubID,
        Roles.RoleTitle
 FROM TaskCollaborators
 JOIN Users ON TaskCollaborators.UserID = Users.UserID
 JOIN Roles ON TaskCollaborators.RoleID = Roles.RoleID
 WHERE TaskCollaborators.TaskID = @TaskID
);

--List all tasks within a specific project and show whether they are available, grabbed, in review, or completed, along with their deadlines.
CREATE FUNCTION [dbo].[udfProjectTasksWithStatusAndDeadlines]
(
@ProjectID INT
)
RETURNS TABLE
AS
RETURN
(
 SELECT Tasks.TaskID,
        Tasks.TaskName,
        TaskStatus.StatusName,
        Tasks.TaskDeadline
 FROM Tasks
 JOIN TaskStatus ON Tasks.TaskStatusID = TaskStatus.TaskStatusID
 WHERE Tasks.ProjectID = @ProjectID
);
