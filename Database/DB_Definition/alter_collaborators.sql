
ALTER TABLE [grabit].ProjectCollaborators 
DROP CONSTRAINT FK__ProjectCo__isAct__45DE573A 
GO

ALTER TABLE [grabit].TaskCollaborators 
DROP CONSTRAINT FK__TaskColla__isAct__4B973090 
GO

ALTER TABLE [grabit].ProjectCollaborators
ADD CONSTRAINT FK_ProjectCollaborators_Project_User
FOREIGN KEY (UserID) REFERENCES grabit.Users(UserID)
GO

ALTER TABLE [grabit].TaskCollaborators
ADD CONSTRAINT FK_TaskCollaborators_Task_User
FOREIGN KEY (UserID) REFERENCES grabit.Users(UserID)
GO

ALTER TABLE [grabit].ProjectCollaborators
ADD CONSTRAINT UQ_ProjectCollaborators_Project_User UNIQUE (ProjectID, UserID);
GO

ALTER TABLE [grabit].TaskCollaborators
ADD CONSTRAINT UQ_TaskCollaborators_Task_User UNIQUE (TaskID, UserID);
GO