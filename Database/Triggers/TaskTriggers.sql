

-- AFTER Task Updates
CREATE TRIGGER trgAfterUpdateTasks ON [grabit].[Tasks]
AFTER UPDATE
AS
BEGIN
	DECLARE @CompletedID INT

	SELECT @CompletedID = TaskStatusID
	FROM TaskStatus
	WHERE StatusName = 'complete';

	DECLARE @TaskID INT

	SELECT @TaskID = TaskID
	FROM INSERTED;

	UPDATE [grabit].[Tasks]
	SET TaskCompletedAt = GETDATE()
	FROM INSERTED new
	JOIN DELETED del ON del.TaskID = new.TaskID
	WHERE new.TaskStatusID = @CompletedID
		AND [grabit].[Tasks].TaskID = new.TaskID
		AND [grabit].[Tasks].TaskCompletedAt IS NULL
		AND del.TaskStatusID <> @CompletedID

	UPDATE [grabit].[Tasks]
	SET TaskUpdatedAt = GETDATE(),
		TaskName = new.TaskName,
		TaskDescription = new.TaskDescription,
		TaskPointID = new.TaskPointID,
		TaskStatusID = new.TaskStatusID,
		TaskReviewRequestedAt = new.TaskReviewRequestedAt
	FROM INSERTED new
	WHERE [grabit].[Tasks].TaskID = @TaskID
END
GO

-- BEFORE Update Tasks
CREATE TRIGGER trgBeforeUpdateTasks ON [grabit].[Tasks]
INSTEAD OF UPDATE
AS
BEGIN
	--CANNOT MOVE TASK BACK AFTER COMPLETION
	IF (
			SELECT new.TaskID
			FROM INSERTED new
			JOIN DELETED del ON new.TaskID = del.TaskID
			WHERE new.TaskStatusID = 1
				AND del.TaskStatusID = 4
			) IS NOT NULL
	BEGIN
		RAISERROR (
				'Cannot move task backwards after completion',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	-- CANNOT JUMP TO COMPLETION BEFORE REVIEW
	IF (
			SELECT new.TaskID
			FROM INSERTED new
			JOIN DELETED del ON new.TaskID = del.TaskID
			WHERE del.TaskStatusID <> 3
				AND new.TaskStatusID = 4
			) IS NOT NULL
	BEGIN
		RAISERROR (
				'Cannot jump task to completion before review',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	-- CANNOT CHANGE COMPLETION DATE
	IF (
			SELECT new.TaskID
			FROM INSERTED new
			JOIN DELETED del ON new.TaskID = del.TaskID
			WHERE new.TaskCompletedAt IS NOT NULL
				AND del.TaskStatusID <> 4
			) IS NOT NULL
	BEGIN
		RAISERROR (
				'Cannot change completion date before finishing task',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	-- CANNOT CHANGE DEADLINE AFTER COMPLETION
	IF EXISTS (
			SELECT 1
			FROM INSERTED new
			JOIN DELETED old ON old.TaskID = new.TaskID
			WHERE new.TaskDeadline <> old.TaskDeadline
				AND old.TaskStatusID = 4
			)
	BEGIN
		RAISERROR (
				'Cannot change deadline date after finishing task',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	-- DEADLINE CANNOT BE IN THE PAST
	IF EXISTS (
			SELECT 1
			FROM INSERTED new
			JOIN DELETED old ON new.TaskID = old.TaskID
			WHERE new.TaskDeadline < old.TaskCreatedAt
			)
	BEGIN
		RAISERROR (
				'Deadline cannot precede CreatedAt Date.',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	UPDATE [grabit].[Tasks]
	SET TaskUpdatedAt = GETDATE(),
		TaskName = new.TaskName,
		TaskDescription = new.TaskDescription,
		TaskPointID = new.TaskPointID,
		TaskStatusID = new.TaskStatusID,
		TaskReviewRequestedAt = new.TaskReviewRequestedAt,
		TaskDeadline = new.TaskDeadline
	FROM INSERTED new
	WHERE [grabit].[Tasks].TaskID = new.TaskID
END
GO

-- 
CREATE TRIGGER trgBeforeInsertTaskCollaborators ON [grabit].[TaskCollaborators]
INSTEAD OF INSERT
AS
BEGIN
	DECLARE @TaskID INT;
	DECLARE @UserID INT;

	SELECT @TaskID = TaskID,
		@UserID = UserID
	FROM INSERTED;

	-- CANNOT COLLABORATE RATE MORE THAN ONCE ON A TASK
	IF (
			SELECT COUNT([grabit].[TaskCollaborators].TaskID)
			FROM [grabit].[TaskCollaborators]
			WHERE [grabit].[TaskCollaborators].TaskID = @TaskID
				AND @UserID = [grabit].[TaskCollaborators].UserID
			) > 1
	BEGIN
		RAISERROR (
				'Cannot be a collaborator more than once on a single task',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	-- DECLINE LATE JOINS
	IF EXISTS (
			SELECT new.TaskCollaboratorID
			FROM INSERTED new
			JOIN [grabit].[Tasks] task ON task.TaskID = new.TaskID
			WHERE task.TaskDeadline < GETDATE()
				AND new.JoinedAt > task.TaskDeadline
			)
	BEGIN
		RAISERROR (
				'Cannot join a task after a deadline.',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	-- CANNOT JOIN AFTER COMPLETION
	IF EXISTS (
			SELECT new.TaskCollaboratorID
			FROM INSERTED new
			JOIN [grabit].[Tasks] task ON task.TaskID = new.TaskID
			WHERE task.TaskStatusID = 4
			)
	BEGIN
		RAISERROR (
				'Cannot join a task after completion.',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	-- CANNOT JOIN AFTER COMPLETION
	IF EXISTS (
			SELECT new.TaskCollaboratorID
			FROM INSERTED new
			JOIN [grabit].[Tasks] task ON task.TaskID = new.TaskID
			WHERE task.TaskStatusID = 4
			)
	BEGIN
		RAISERROR (
				'Cannot join a task after completion.',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	--CANNOT MAKE ROLE AS LEAD OR MEMBER
	IF EXISTS (
			SELECT 1
			FROM [grabit].[TaskCollaborators]
			JOIN INSERTED new ON new.TaskCollaboratorID = [grabit].[TaskCollaborators].TaskCollaboratorID
			WHERE (
					new.RoleID = 1
					OR new.RoleID = 2
					)
			)
	BEGIN
		RAISERROR (
				'Cannot be a lead or member on a task.',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	INSERT [grabit].[TaskCollaborators]
	SELECT UserID,
		RoleID,
		TaskID,
		JoinedAt,
		isActive
	FROM INSERTED
END
GO

-- 
CREATE TRIGGER trgBeforeUpdateTaskCollaborators ON [grabit].[TaskCollaborators]
INSTEAD OF UPDATE
AS
BEGIN
	DECLARE @TaskID INT;
	DECLARE @UserID INT;
	DECLARE @OldDate DATETIME;

	SELECT @TaskID = TaskID,
		@UserID = UserID
	FROM INSERTED;

	SELECT @OldDate = JoinedAt
	FROM DELETED;

	IF EXISTS (
			SELECT *
			FROM INSERTED new
			WHERE new.UserID IS NULL
				AND DATEDIFF(DAY, @OldDate, GETDATE()) > 1
			)
	BEGIN
		RAISERROR (
				'Cannot remove user as collaborator after a day',
				16,
				1
				)

		ROLLBACK TRANSACTION;

		RETURN
	END

	--
	IF EXISTS (
			SELECT *
			FROM INSERTED new
			JOIN [grabit].[TaskCollaborators] ON [grabit].[TaskCollaborators].TaskID = new.TaskID
			JOIN [grabit].[Tasks] ON [grabit].[Tasks].TaskID = [grabit].[TaskCollaborators].TaskID
			WHERE new.UserID IS NULL
				AND (
					[grabit].[Tasks].TaskStatusID = 4
					OR [grabit].[Tasks].TaskStatusID = 3
					)
			)
	BEGIN
		RAISERROR (
				'Cannot remove user as collaborator after a task is in review or complete',
				16,
				1
				)

		ROLLBACK TRANSACTION;

		RETURN
	END

	-- CANNOT CHANGE COLLABORATE AFTER COMPLETION
	IF EXISTS (
			SELECT new.TaskCollaboratorID
			FROM INSERTED new
			JOIN DELETED old ON old.TaskCollaboratorID = new.TaskCollaboratorID
			JOIN [grabit].[Tasks] task ON task.TaskID = old.TaskID
			WHERE task.TaskStatusID = 4
				AND new.UserID <> old.UserID
			)
	BEGIN
		RAISERROR (
				'Cannot change a collaborator after a task has been completed.',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	-- CANNOT CHANGE WHEN YOU JOINED A TASK
	IF EXISTS (
			SELECT new.TaskCollaboratorID
			FROM [grabit].[TaskCollaborators]
			JOIN INSERTED new ON new.TaskCollaboratorID = [grabit].[TaskCollaborators].TaskCollaboratorID
			JOIN DELETED old ON old.TaskID = new.TaskID
			WHERE [grabit].[TaskCollaborators].JoinedAt <> new.JoinedAt
			)
	BEGIN
		RAISERROR (
				'Cannot change when a user joined a task.',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	--CANNOT MAKE ROLE AS LEAD OR MEMBER
	IF EXISTS (
			SELECT 1
			FROM [grabit].[TaskCollaborators]
			JOIN INSERTED new ON new.TaskCollaboratorID = [grabit].[TaskCollaborators].TaskCollaboratorID
			WHERE (
					new.RoleID = 1
					OR new.RoleID = 2
					)
			)
	BEGIN
		RAISERROR (
				'Cannot be a lead or member on a task.',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	UPDATE [grabit].[TaskCollaborators]
	SET UserID = new.UserID,
		RoleID = new.RoleID,
		isActive = new.IsActive
	FROM INSERTED new
	WHERE [grabit].[TaskCollaborators].TaskCollaboratorID = new.TaskCollaboratorID
END
GO

-- BEFORE DELETION
CREATE TRIGGER trgBeforeDeleteTaskCollaborators ON [grabit].[TaskCollaborators]
INSTEAD OF DELETE
AS
BEGIN
	UPDATE [grabit].[TaskCollaborators]
	SET isActive = 0
	FROM DELETED
	WHERE DELETED.TaskCollaboratorID = [grabit].[TaskCollaborators].TaskCollaboratorID
END
GO

CREATE TRIGGER trgBeforeDeleteTasks ON [grabit].[Tasks]
INSTEAD OF DELETE
AS
BEGIN
	UPDATE [grabit].[TaskCollaborators]
	SET isActive = 0,
		RoleID = [grabit].[TaskCollaborators].RoleID
	FROM DELETED old
	WHERE [grabit].[TaskCollaborators].TaskID = old.TaskID
END
