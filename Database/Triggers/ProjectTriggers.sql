USE grabit;
GO

-- BEFORE CREATING PROJECT
CREATE TRIGGER trgBeforeInsertProjectCollaborators ON [grabit].[ProjectCollaborators]
INSTEAD OF INSERT
AS
BEGIN
	-- CANNOT RE-COLLABORATE ON A PROJECT
	IF (
			SELECT COUNT([grabit].[ProjectCollaborators].ProjectCollaboratorID)
			FROM [grabit].[ProjectCollaborators]
			JOIN INSERTED new ON new.ProjectCollaboratorID = [grabit].[ProjectCollaborators].ProjectCollaboratorID
			WHERE new.UserID = [grabit].[ProjectCollaborators].UserID
			) > 1
	BEGIN
		RAISERROR ('Cannot be a collaborator more than once',16,1)
		ROLLBACK TRANSACTION
		RETURN
	END

	INSERT INTO [grabit].[ProjectCollaborators]
	SELECT ProjectCollaboratorID,
		UserID,
		ProjectID,
		RoleID,
		JoinedAt = CURRENT_TIMESTAMP,
		isActive = 1
	FROM INSERTED
END
GO

CREATE TRIGGER trgBeforeUpdateProjectCollaborators ON [grabit].[ProjectCollaborators]
INSTEAD OF UPDATE
AS
BEGIN
	-- CANNOT CHANGE JOINED AT
	IF EXISTS (
			SELECT 1
			FROM DELETED old
			JOIN INSERTED new ON new.ProjectCollaboratorID = old.ProjectCollaboratorID
			WHERE new.JoinedAt <> old.JoinedAt
			)
	BEGIN
		RAISERROR ('Cannot change joined at',16,1)
		ROLLBACK TRANSACTION
		RETURN
	END

	-- CANNOT CHANGE ROLE TO GRABBER OR TASK COLLABORATE
	IF EXISTS (
			SELECT 1
			FROM [grabit].[ProjectCollaborators]
			JOIN INSERTED new ON [grabit].[ProjectCollaborators].ProjectCollaboratorID = new.ProjectCollaboratorID
			WHERE (
					new.RoleID = 3
					OR new.RoleID = 4
					)
			)
	BEGIN
		RAISERROR (
				'Cannot change role to grabber or task collaborator',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	--FINALLY
	UPDATE [grabit].[ProjectCollaborators]
	SET RoleID = new.RoleID,
		isActive = new.isActive
	FROM INSERTED new
	WHERE [grabit].[ProjectCollaborators].ProjectCollaboratorID = new.ProjectCollaboratorID
END
GO

-- ON DELETE
CREATE TRIGGER trgBeforeDeleteProjectCollaborators ON [grabit].[ProjectCollaborators]
INSTEAD OF DELETE
AS
BEGIN
	UPDATE [grabit].[ProjectCollaborators]
	SET isActive = 0
	FROM DELETED
	WHERE DELETED.ProjectCollaboratorID = [grabit].[ProjectCollaborators].ProjectCollaboratorID
END
GO

CREATE TRIGGER trgBeforeDeleteProject ON [grabit].[Projects]
INSTEAD OF DELETE
AS
BEGIN
	UPDATE [grabit].[ProjectCollaborators]
	SET isActive = 0
	FROM DELETED old
	WHERE old.ProjectID = ProjectCollaborators.ProjectID
		AND [grabit].[ProjectCollaborators].RoleID = 1
END
GO


