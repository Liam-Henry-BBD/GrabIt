
-- UPDATE TASKS TASKCOMPLETEDAT
ALTER TRIGGER trgTaskStatusUpdate 
ON Tasks
AFTER UPDATE 
AS
BEGIN

	DECLARE @CompletedID INT 
	SELECT @CompletedID=TaskStatusID FROM TaskStatus WHERE StatusName = 'complete';

	DECLARE @TaskID INT
	SELECT @TaskID = TaskID FROM INSERTED;

	UPDATE Tasks
	SET TaskCompletedAt = GETDATE()
	FROM INSERTED new
	JOIN DELETED del
	ON del.TaskID = new.TaskID
	WHERE new.TaskStatusID = @CompletedID
		AND Tasks.TaskID = new.TaskID
		AND Tasks.TaskCompletedAt IS NULL
		AND del.TaskStatusID <> @CompletedID

	UPDATE Tasks
	SET TaskUpdatedAt = GETDATE()
	WHERE Tasks.TaskID = @TaskID

END
GO

-- PREVENT TASK CHANGE FROM COMPLETE TO GRAB
CREATE TRIGGER trgPreventChangeCompleted
ON Tasks
INSTEAD OF UPDATE
AS
BEGIN
	IF (
		SELECT new.TaskID 
		FROM INSERTED new 
		JOIN DELETED del 
		ON new.TaskID = del.TaskID
		WHERE new.TaskStatusID = 1
		AND del.TaskStatusID = 4
	) IS NOT NULL
	BEGIN
		RAISERROR('Cannot move task backwards after completion', 16, 1)
		ROLLBACK TRANSACTION
	END

	--
	IF (
		SELECT new.TaskID 
		FROM INSERTED new
		JOIN DELETED del 
		ON new.TaskID = del.TaskID
		WHERE new.TaskCreatedAt IS NOT NULL
		AND del.TaskStatusID <> 4
	) IS NOT NULL
	BEGIN
		RAISERROR('Cannot change completion date before finishing task', 16, 1)
		ROLLBACK TRANSACTION
	END
END
GO

-- Cannot collaborate twice on a task
ALTER TRIGGER trgPreventReCollaborateOnTask
ON TaskCollaborators
INSTEAD OF INSERT
AS
BEGIN
	DECLARE @TaskID INT;
    DECLARE @UserID INT;

    SELECT @TaskID = TaskID, @UserID = UserID FROM INSERTED;
	IF (
		SELECT COUNT(TaskCollaborators.TaskID)
		FROM TaskCollaborators
		WHERE TaskCollaborators.TaskID = @TaskID
		AND TaskCollaborators.UserID = TaskCollaborators.UserID
	) > 1
	BEGIN
		RAISERROR('Cannot be a collaborator more than once on a single task', 16, 1)
		ROLLBACK TRANSACTION
	END
END
GO

-- Cannot remove task collaborator after a day
ALTER TRIGGER trgCannotRemoveCollaboratorAfterOneDay
ON TaskCollaborators
INSTEAD OF UPDATE
AS
BEGIN
	DECLARE @TaskID INT;
	DECLARE @UserID INT;

	DECLARE @OldDate DATETIME;

	SELECT @TaskID = TaskID, @UserID = UserID FROM INSERTED;
	SELECT @OldDate = JoinedAt FROM DELETED;
	IF EXISTS (
		SELECT *
		FROM INSERTED new
		WHERE new.UserID IS NULL
		AND DATEDIFF(DAY, @OldDate, GETDATE()) > 1
	)
	BEGIN
		RAISERROR('Cannot remove user as collaborator after a day', 16, 1)
		ROLLBACK TRANSACTION;
	END
	--

	IF EXISTS (
		SELECT *
		FROM INSERTED new
		JOIN Tasks
		ON Tasks.TaskID = new.TaskID
		WHERE new.UserID IS NULL
		AND (Tasks.TaskStatusID = 4 OR Tasks.TaskStatusID = 3)
	)
	BEGIN
		RAISERROR('Cannot remove user as collaborator after a task is in review or complete', 16, 1)
		ROLLBACK TRANSACTION;
	END



END
GO






UPDATE Tasks
SET TaskName = 'mmmmm'
WHERE TaskID = 2


SELECT * FROM Tasks WHERE TaskID = 2;