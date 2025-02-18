USE grabit;
GO

-- BEFORE CREATING PROJECT
CREATE TRIGGER trgBeforeInsertProjectCollaborators ON ProjectCollaborators
INSTEAD OF INSERT
AS
BEGIN
	-- CANNOT RE-COLLABORATE ON A PROJECT
	IF (
			SELECT COUNT(ProjectCollaborators.ProjectCollaboratorID)
			FROM ProjectCollaborators
			JOIN INSERTED new ON new.ProjectCollaboratorID = ProjectCollaborators.ProjectCollaboratorID
			WHERE new.UserID = ProjectCollaborators.UserID
			) > 1
	BEGIN
		RAISERROR (
				'Cannot be a collaborator more than once',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	INSERT INTO ProjectCollaborators
	SELECT ProjectCollaboratorID,
		UserID,
		ProjectID,
		RoleID,
		JoinedAt = CURRENT_TIMESTAMP,
		isActive = 1
	FROM INSERTED
END
GO

CREATE TRIGGER trgBeforeUpdateProjectCollaborators ON ProjectCollaborators
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
		RAISERROR (
				'Cannot change joined at',
				16,
				1
				)

		ROLLBACK TRANSACTION

		RETURN
	END

	-- CANNOT CHANGE ROLE TO GRABBER OR TASK COLLABORATE
	IF EXISTS (
			SELECT 1
			FROM ProjectCollaborators
			JOIN INSERTED new ON ProjectCollaborators.ProjectCollaboratorID = new.ProjectCollaboratorID
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
	UPDATE ProjectCollaborators
	SET RoleID = new.RoleID,
		isActive = new.isActive
	FROM INSERTED new
	WHERE ProjectCollaborators.ProjectCollaboratorID = new.ProjectCollaboratorID
END
GO

-- ON DELETE
CREATE TRIGGER trgBeforeDeleteProjectCollaborators ON ProjectCollaborators
INSTEAD OF DELETE
AS
BEGIN
	UPDATE ProjectCollaborators
	SET isActive = 0
	FROM DELETED
	WHERE DELETED.ProjectCollaboratorID = ProjectCollaborators.ProjectCollaboratorID
END
GO

CREATE TRIGGER trgBeforeDeleteProject ON Projects
INSTEAD OF DELETE
AS
BEGIN
	UPDATE ProjectCollaborators
	SET isActive = 0
	FROM DELETED old
	WHERE old.ProjectID = ProjectCollaborators.ProjectID
		AND ProjectCollaborators.RoleID = 1
END
GO


