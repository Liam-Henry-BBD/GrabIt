USE GrabIt
GO

-- View of available tasks
CREATE VIEW vwAvaliableTasks
AS
SELECT 
    t.TaskID,
    t.TaskName,
    t.TaskDescription,
    t.TaskPointID
FROM 
    Tasks t
WHERE 
    t.TaskStatusID = 1;
GO

-- View of grabbed tasks
CREATE VIEW vwGrabbedTasks
AS
SELECT 
    t.TaskID,
    t.TaskName,
    t.TaskDescription,
    t.TaskPointID
FROM 
    Tasks t
WHERE 
    t.TaskStatusID = 2;
GO

-- View of review tasks
CREATE VIEW vwReviewTasks
AS
SELECT 
    t.TaskID,
    t.TaskName,
    t.TaskDescription,
    t.TaskPointID
FROM 
    Tasks t
WHERE  
    t.TaskStatusID = 3;
GO

-- View of completed tasks
CREATE VIEW vwCompletedTasks
AS
SELECT 
    t.TaskID,
    t.TaskName,
    t.TaskDescription,
    t.TaskPointID,
    t.TaskCompletedAt
FROM 
    Tasks t
WHERE 
    t.TaskStatusID = 4;
GO

-- View of user project roles
CREATE VIEW vwUserProjectRoles
AS
SELECT 
    pc.UserID,
    u.GitHubID, 
    pc.ProjectID,
    p.ProjectName,
    r.RoleTitle,
    pc.JoinedAt 
FROM 
    ProjectCollaborators pc
JOIN 
    Roles r ON pc.RoleID = r.RoleID 
JOIN 
    Users u ON pc.UserID = u.UserID 
JOIN 
    Projects p ON pc.ProjectID = p.ProjectID;
GO

-- View of project completion rate
CREATE VIEW vwProjectCompletionRate
AS
SELECT 
    t.ProjectID,
    COUNT(t.TaskID) AS TotalTasks,
    COUNT(CASE WHEN t.TaskCompletedAt IS NOT NULL THEN t.TaskID END) AS TasksCompleted,
    CONVERT(
        DECIMAL(5,2), 
        COUNT(
            CASE 
                WHEN t.TaskCompletedAt IS NOT NULL 
                THEN t.TaskID 
            END
            ) * 100.0 / COUNT(t.TaskID)) 
    AS 
        CompletionRate  
FROM 
    Tasks t
GROUP BY 
    t.ProjectID;
GO

-- View of Total Points for all Projects
CREATE VIEW vwProjectLeaderBoards AS
SELECT 
    u.UserID, 
    u.GitHubID, 
    SUM(COALESCE(tp.PointValue, 0)) AS TotalScore
FROM 
    (
        SELECT DISTINCT 
            tc.UserID, 
            tc.TaskID
        FROM 
            TaskCollaborators tc
        JOIN 
            Tasks t ON t.TaskID = tc.TaskID
        WHERE 
            tc.isActive = 1  -- Filter active users 
    ) AS distinct_user_tasks
JOIN 
    Users u ON u.UserID = distinct_user_tasks.UserID
JOIN 
    Tasks t ON t.TaskID = distinct_user_tasks.TaskID
JOIN 
    TaskPoints tp ON tp.TaskPointID = t.TaskPointID
where 
    t.TaskStatusID=1 
GROUP BY 
    u.UserID, u.GitHubID
GO

-- view of users and associated tasks
CREATE VIEW vwUserTasks AS
SELECT 
    tc.UserID, 
    t.TaskID, 
    t.TaskName, 
    t.ProjectID, 
    p.ProjectName
FROM 
    TaskCollaborators tc
JOIN 
    Tasks t ON tc.TaskID = t.TaskID
JOIN 
    Projects p ON t.ProjectID = p.ProjectID;
GO

-- view of users that are inactive on a project
CREATE VIEW vwInactiveProjectUsers
AS
SELECT DISTINCT u.UserID, u.GitHubID, t.ProjectID
FROM 
    Users u
JOIN 
    ProjectCollaborators pc ON u.UserID = pc.UserID
JOIN 
    Tasks t ON pc.ProjectID = t.ProjectID
WHERE 
    pc.isActive = 0;
GO

-- view of which users are inactive on a task
CREATE VIEW vwInactiveTaskUsers
AS
SELECT DISTINCT u.UserID, u.GitHubID, tc.TaskID, t.ProjectID
FROM 
    Users u
JOIN 
    TaskCollaborators tc ON u.UserID = tc.UserID
JOIN 
    Tasks t ON tc.TaskID = t.TaskID
WHERE
    tc.isActive = 0;
GO

---view of all easy tasks
CREATE VIEW vwEasyTasks
AS
select 
    * 
from 
    tasks 
where 
    tasks.TaskPointID = 'Easy'
GO

-- view of all medium tasks
CREATE VIEW vwMediumTasks
AS
select 
    * 
from
    tasks 
where 
    tasks.TaskPointID = 'Medium'
GO

-- view of all hard tasks
CREATE VIEW vwHardTasks
AS
select 
    * 
from 
    tasks 
where 
    tasks.TaskPointID = 'Hard'
GO

-- view of the number of collaborators on each project
CREATE VIEW vwProjectCollaboratorCount AS
SELECT 
    pc.ProjectID, 
    COUNT(pc.UserID) AS CollaboratorCount
FROM 
    ProjectCollaborators pc
GROUP BY 
    pc.ProjectID;
GO

-- view of the number of collaborators on each task
CREATE VIEW vwTaskCollaboratorCount AS
SELECT 
    tc.TaskID, 
    COUNT(tc.UserID) AS CollaboratorCount
FROM 
    TaskCollaborators tc
GROUP BY 
    tc.TaskID;
GO

-- view of users collaboration counts (total number of projects and tasks)
CREATE VIEW vwUserCollaborationCount AS
SELECT 
    u.UserID, 
    u.GitHubID, 
    COUNT(DISTINCT tc.TaskID) AS TaskCount, 
    COUNT(DISTINCT pc.ProjectID) AS ProjectCount
FROM 
    Users u
LEFT JOIN 
    TaskCollaborators tc ON u.UserID = tc.UserID
LEFT JOIN 
    ProjectCollaborators pc ON u.UserID = pc.UserID
GROUP BY 
    u.UserID, u.GitHubID;
GO