---------- stored Procudure queries ----------

-- tored procedure to allow a user 
-- to grab a task, 
-- update its status to "grabbed," and add the user as a collaborator.--

CREATE PROCEDURE GrabTask
    @UserID INT,
    @TaskID INT
AS
BEGIN
    UPDATE Tasks
    SET TaskStatusID = 2
    WHERE TaskID = @TaskID;

    INSERT INTO TaskCollaborators (UserID, TaskID, RoleID, JoinedAt)
    VALUES (@UserID, @TaskID, 2, GETDATE());
END;

-- Stored procedure to change the status of a task to "review" after it has been grabbed and is ready for review.--

CREATE PROCEDURE ReviewTask
    @TaskID INT
    @
AS
BEGIN
    UPDATE Tasks
    SET TaskStatusID = 3
    WHERE TaskID = @TaskID;
END;

-- Stored procedure to mark a task as "complete," update its status, and record the completion date. --

CREATE PROCEDURE CompleteTask
    @TaskID INT
AS

BEGIN
    UPDATE Tasks
    SET TaskStatusID = 4, TaskCompletedAt = GETDATE()
    WHERE TaskID = @TaskID;
END;

-- Stored procedure to assign a user to a task and update the task status to "grabbed" if the task is available. --

CREATE PROCEDURE AssignUserToTask
    @UserID INT,
    @TaskID INT
AS
BEGIN
    DECLARE @TaskStatusID INT;

    SELECT @TaskStatusID = TaskStatusID
    FROM Tasks
    WHERE TaskID = @TaskID;

    IF @TaskStatusID = 1
    BEGIN
        UPDATE Tasks
        SET TaskStatusID = 2
        WHERE TaskID = @TaskID;

        INSERT INTO TaskCollaborators (UserID, TaskID, RoleID, JoinedAt)
        VALUES (@UserID, @TaskID, 2, GETDATE());
    END;
END;

-- Stored procedure to remove a user as a collaborator from a task and revert the task's status if necessary.switching IsActive column from 1-0 --

CREATE PROCEDURE RemoveUserFromTask
    @UserID INT,
    @TaskID INT
AS
BEGIN
    DECLARE @TaskStatusID INT;

    SELECT @TaskStatusID = TaskStatusID
    FROM Tasks
    WHERE TaskID = @TaskID;

    WHERE UserID = @UserID AND TaskID = @TaskID;

    IF @TaskStatusID = 2
    BEGIN
        UPDATE Tasks
        SET TaskStatusID = 1
        WHERE TaskID = @TaskID;
    END;
END;


-- Stored procedure to retrieve all tasks assigned to a specific user, along with their statuses and deadlines. --

CREATE PROCEDURE GetTasksByUser
    @UserID INT
AS
BEGIN
    SELECT t.TaskID, t.TaskName, ts.TaskStatusName, t.TaskDeadline
    FROM Tasks t
    INNER JOIN TaskStatuses ts ON t.TaskStatusID = ts.TaskStatusID
    INNER JOIN TaskCollaborators tc ON t.TaskID = tc.TaskID
    WHERE tc.UserID = @UserID;
END;


-- Stored procedure to calculate the number of completed tasks in a project and return the total. --

CREATE PROCEDURE GetCompletedTasksByProject
    @ProjectID INT
AS
BEGIN
    SELECT COUNT(*)
    FROM Tasks
    WHERE ProjectID = @ProjectID AND TaskStatusID = 4;
END;

-- Stored procedure to check if a user is eligible to grab a task based on their role and the task's status. --

CREATE PROCEDURE CheckEligibilityToGrabTask
    @UserID INT,
    @TaskID INT
AS
BEGIN
    DECLARE @UserRoleID INT;
    DECLARE @TaskStatusID INT;

    SELECT @UserRoleID = RoleID
    FROM UserRoles
    WHERE UserID = @UserID;

    SELECT @TaskStatusID = TaskStatusID
    FROM Tasks
    WHERE TaskID = @TaskID;

    IF @UserRoleID = 1 AND @TaskStatusID = 1
    BEGIN
        SELECT 'Eligible to grab task';
    END
    ELSE
    BEGIN
        SELECT 'Not eligible to grab task';
    END;
END;

-- Stored procedure to update a task's deadline after it has been grabbed but before it is completed. --

CREATE PROCEDURE UpdateTaskDeadline
    @TaskID INT,
    @NewDeadline DATE
AS
BEGIN
    UPDATE Tasks
    SET TaskDeadline = @NewDeadline
    WHERE TaskID = @TaskID;
END;

-- Create a stored procedure to list all tasks in a project, 
--showing their statuses, 
--deadlines, 
--and assigned collaborators. --

CREATE PROCEDURE GetTasksByProject
    @ProjectID INT
AS
BEGIN
    SELECT t.TaskID, t.TaskName, ts.TaskStatusName, t.TaskDeadline, u.UserName
    FROM Tasks t
    JOIN TaskStatuses ts ON t.TaskStatusID = ts.TaskStatusID
    JOIN TaskCollaborators tc ON t.TaskID = tc.TaskID
    JOIN Users u ON tc.UserID = u.UserID
    WHERE t.ProjectID = @ProjectID;
END;

-- Stored procedure to return all tasks in review for a specific project and include their collaborators. --

CREATE PROCEDURE GetTasksInReviewByProject
    @ProjectID INT
AS
BEGIN
    SELECT t.TaskID, t.TaskName, u.UserName
    FROM Tasks t
    JOIN TaskCollaborators tc ON t.TaskID = tc.TaskID
    JOIN Users u ON tc.UserID = u.UserID
    WHERE t.ProjectID = @ProjectID AND t.TaskStatusID = 3;
END;

-- Create a stored procedure to get all tasks in a project that are not yet completed, and return their statuses and collaborators. --

CREATE PROCEDURE GetTasksInProgressByProject
    @ProjectID INT
AS
BEGIN
    SELECT t.TaskID, t.TaskName, ts.TaskStatusName, u.UserName
    FROM Tasks t
    JOIN TaskStatuses ts ON t.TaskStatusID = ts.TaskStatusID
    JOIN TaskCollaborators tc ON t.TaskID = tc.TaskID
    JOIN Users u ON tc.UserID = u.UserID
    WHERE t.ProjectID = @ProjectID AND t.TaskStatusID != 4;
END;


-- Create a stored procedure to prevent further edits to a task once it is marked as completed. --

CREATE PROCEDURE PreventEditsToCompletedTask
    @TaskID INT
AS
BEGIN
    UPDATE Tasks
    SET TaskStatusID = 5
    WHERE TaskID = @TaskID;
END;