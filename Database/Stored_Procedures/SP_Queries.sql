USE GrabIt
GO

-- Stored procedure to allow a user 
-- to grab a task, 
-- update its status to "grabbed," and add the user as a collaborator.--

CREATE PROCEDURE GrabbedTask
    @UserID INT,
    @TaskID INT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        UPDATE Tasks
        SET TaskStatusID = 2
        WHERE TaskID = @TaskID;

        INSERT INTO TaskCollaborators (UserID, TaskID, RoleID, JoinedAt)
        VALUES (@UserID, @TaskID, 2, GETDATE());

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO

-- Stored procedure to change the status of a task to "review" after it has been grabbed and is ready for review.--

CREATE PROCEDURE ReviewTask
    @TaskID INT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        UPDATE Tasks
        SET TaskStatusID = 3
        WHERE TaskID = @TaskID;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO
-- Stored procedure to mark a task as "complete," update its status, and record the completion date. --

CREATE PROCEDURE CompletedTask
    @TaskID INT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        UPDATE Tasks
        SET TaskStatusID = 4, TaskCompletedAt = GETDATE()
        WHERE TaskID = @TaskID;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO
-- Stored procedure to assign a user to a task and update the task status to "grabbed" if the task is available. --

CREATE PROCEDURE AssignedUserToTask
    @UserID INT,
    @TaskID INT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

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

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO
-- Stored procedure to remove a user as a collaborator from a task and revert the task's status if necessary. --

CREATE PROCEDURE RemoveUserFromTask
    @UserID INT,
    @TaskID INT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        DECLARE @TaskStatusID INT;

        SELECT @TaskStatusID = TaskStatusID
        FROM Tasks
        WHERE TaskID = @TaskID;

        DELETE FROM TaskCollaborators
        WHERE UserID = @UserID AND TaskID = @TaskID;

        IF @TaskStatusID = 2
        BEGIN
            UPDATE Tasks
            SET TaskStatusID = 1
            WHERE TaskID = @TaskID;
        END;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO
-- Stored procedure to retrieve all tasks assigned to a specific user, along with their statuses and deadlines. --GO
CREATE PROCEDURE GetALLTasksByUser
    @UserID INT
AS
BEGIN
    BEGIN TRY
        SELECT t.TaskID, t.TaskName, ts.StatusName, t.TaskDeadline
        FROM Tasks t
        INNER JOIN TaskStatus ts ON t.TaskStatusID = ts.TaskStatusID
        INNER JOIN TaskCollaborators tc ON t.TaskID = tc.TaskID
        WHERE tc.UserID = @UserID;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO
-- Stored procedure to calculate the number of completed tasks in a project and return the total. --

CREATE PROCEDURE GetCompletedTasksByProject
    @ProjectID INT
AS
BEGIN
    BEGIN TRY
        SELECT COUNT(*)
        FROM Tasks
        WHERE ProjectID = @ProjectID AND TaskStatusID = 4;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- Stored procedure to check if a user is eligible to grab a task based on their role and the task's status. --
CREATE PROCEDURE CheckEligibilityToGrabbedTask
    @UserID INT,
    @TaskID INT
AS
BEGIN
    BEGIN TRY
        DECLARE @UserRoleID INT;
        DECLARE @TaskStatusID INT;

        SELECT @UserRoleID = RoleID
        FROM TaskCollaborators
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
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- Stored procedure to update a task's deadline after it has been grabbed but before it is completed. --
CREATE PROCEDURE UpdateTaskDeadline
    @TaskID INT,
    @NewDeadline DATE
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        UPDATE Tasks
        SET TaskDeadline = @NewDeadline
        WHERE TaskID = @TaskID;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO
-- Create a stored procedure to list all tasks in a project, 
--showing their statuses, 
--deadlines, 
--and assigned collaborators. --

CREATE PROCEDURE GetTasksPerProject
    @ProjectID INT
AS
BEGIN
    BEGIN TRY
        SELECT t.TaskID, t.TaskName, ts.StatusName, t.TaskDeadline, u.GitHubID
        FROM Tasks t
        JOIN TaskStatus ts ON t.TaskStatusID = ts.TaskStatusID
        JOIN TaskCollaborators tc ON t.TaskID = tc.TaskID
        JOIN Users u ON tc.UserID = u.UserID
        WHERE t.ProjectID = @ProjectID;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- Stored procedure to return all tasks in review for a specific project and include their collaborators. --
CREATE PROCEDURE GetTasksInReviewByProject
    @ProjectID INT
AS
BEGIN
    BEGIN TRY
        SELECT t.TaskID, t.TaskName, u.GitHubID
        FROM Tasks t
        JOIN TaskCollaborators tc ON t.TaskID = tc.TaskID
        JOIN Users u ON tc.UserID = u.UserID
        WHERE t.ProjectID = @ProjectID AND t.TaskStatusID = 3;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- Create a stored procedure to get all tasks in a project that are not yet completed, and return their statuses and collaborators. --
CREATE PROCEDURE GetTasksInProgressPerProject
    @ProjectID INT
AS
BEGIN
    BEGIN TRY
        SELECT t.TaskID, t.TaskName, ts.StatusName, u.GitHubID
        FROM Tasks t
        JOIN TaskStatus ts ON t.TaskStatusID = ts.TaskStatusID
        JOIN TaskCollaborators tc ON t.TaskID = tc.TaskID
        JOIN Users u ON tc.UserID = u.UserID
        WHERE t.ProjectID = @ProjectID AND t.TaskStatusID != 4;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- Create a stored procedure to prevent further edits to a task once it is marked as completed. --
CREATE PROCEDURE PreventEditsToCompletedTask
    @TaskID INT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        UPDATE Tasks
        SET TaskStatusID = 5
        WHERE TaskID = @TaskID;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO
-- Stored procedure to get all tasks that are overdue in a project, --

CREATE PROCEDURE GetOverdueTasksByProject
    @ProjectID INT
AS
BEGIN
    BEGIN TRY
        SELECT t.TaskID, t.TaskName, t.TaskDeadline
        FROM Tasks t
        WHERE t.ProjectID = @ProjectID AND t.TaskDeadline < GETDATE();
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO
