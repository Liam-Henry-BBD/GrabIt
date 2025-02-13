---------- stored Procudure practice queries ----------

-- Create a stored procedure to allow a user to grab a task, update its status to "grabbed," and add the user as a collaborator.--

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

-- Create a stored procedure to change the status of a task to "review" after it has been grabbed and is ready for review.--

CREATE PROCEDURE ReviewTask
    @TaskID INT
AS
BEGIN
    UPDATE Tasks
    SET TaskStatusID = 3
    WHERE TaskID = @TaskID;
END;

-- Create a stored procedure to mark a task as "complete," update its status, and record the completion date. --

CREATE PROCEDURE CompleteTask
    @TaskID INT
AS

BEGIN
    UPDATE Tasks
    SET TaskStatusID = 4, TaskCompletedAt = GETDATE()
    WHERE TaskID = @TaskID;
END;

-- Create a stored procedure to assign a user to a task and update the task status to "grabbed" if the task is available. --

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

-- Create a stored procedure to remove a user as a collaborator from a task and revert the task's status if necessary. --

CREATE PROCEDURE RemoveUserFromTask
    @UserID INT,
    @TaskID INT
AS
BEGIN
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
END;