# GrabIt
A gamified task management system


The GrabIt system is a project management tool designed for teams to manage tasks within projects. It allows team leads to create projects, set tasks, assign points, and track task completion with deadlines. Team members can grab tasks from the available list and work on them. Points are assigned based on the difficulty of the task, and rewards are given for completing tasks on time.

## Workflow Overview

1. **Team Lead Logs In**  
   The team lead logs in to the system and sets up a new project.
   
2. **Set Up Project and Tasks**  
   The team lead creates tasks within the project, providing task names, descriptions, and setting deadlines. Each task can be assigned a point value based on its difficulty.

3. **Invite Team Members**  
   The team lead invites team members to join the project.

4. **Team Members Task Grab**  
   Team members browse the list of available tasks. When they select a task to work on, it is removed from the "Available Tasks" list and transferred to the "Tasks Grabbed" list.

   **Trajectory of a Task**
   Available
   Grabbed
   Reviewed
   Complete

5. **Leader Assigns Task Points**  
   The team lead assigns a point value to each task, based on its complexity. Tasks can have one of three point values:
   
   - **Simple (Green)**: 5 points
   - **Medium (Yellow)**: 10 points
   - **Hard (Red)**: 15 points

6. **Set Task Deadlines**  
   The team lead adds deadlines to each task. If a task deadline is approaching, the system provides a reward for the team member who completes the task on time.

## Task Tracking

Tasks in the system follow a status workflow. There are three main statuses:

- **Available**: Tasks that are available for team members to grab.
- **Grabbed**: Tasks that have been grabbed by a team member.
- **Reviewed**: Tasks that are waiting to be reviewed by the team lead.
- **Complete**: Tasks that have been finished and completed (these are greyed out once finished).

## Point System (Color Coded)

The points for each task are assigned based on its difficulty, which is color-coded for easier identification:

- **Green (5 points)**: Simple tasks
- **Yellow (10 points)**: Medium complexity tasks
- **Red (15 points)**: Hard tasks

## Deadlines & Rewards

Each task can have a deadline set by the team lead.

## Score Tracking

The system provides tracking of scores for team members and the entire project:

- **Leaderboard**: A ranking of team members based on their accumulated points.
- **Accumulated Points per Project**: The total number of points accumulated by each team member within a specific project.
- **Team Total Points**: The total number of points accumulated by the team as a whole.

## Features

- **Login System**: Every user logs into the system using GitHub Authentication.
- **Project Setup**: Team leads can set up new projects, add tasks, and manage team members.
- **Task Grab**: Team members can grab available tasks and track progress.
- **Task Assignment**: Tasks can be assigned difficulty points (simple, medium, hard).
- **Deadlines & Rewards**: The team lead can set deadlines, and members can earn rewards for completing tasks on time.
- **Task Tracking**: Tasks are tracked through different stages: available, taken, and complete.
- **Leaderboard**: Tracks accumulated points by team members and the team as a whole.

## Example Flow

1. **Team Lead** logs into the system and sets up a new project called "Website Development".
2. **Team Lead** adds tasks to the project, such as:
   - Design Homepage (Medium, 10 points)
   - Develop API (Hard, 15 points)
   - QA Testing (Simple, 5 points)
3. **Team Members** are invited to join the project and can "grab" a task from the available list.
4. **Team Lead** sets a deadline for each task, e.g., the "Develop API" task has a deadline of 5 days.
5. As team members complete tasks, their points are tracked and updated in the **Leaderboard**.
6. The system awards a reward to any member who completes their task before the deadline.

## Technologies

This system could be implemented with the following technologies:

- **Backend**: Java
- **Frontend**: React, HTML, CSS and JavaScript
- **Database**: SQL Server
- **Authentication**: OAuth

## Future Features

- **Task Discussion**: Ability for team members to comment or discuss tasks.
- **Advanced Leaderboard**: With filters for showing points per project or per user.
- **Notifications**: Alerts for approaching deadlines or task updates.

---

## Installation

To set up the Task Grab system locally, follow these steps:

1. Clone the repository:

   ```bash
   HTTPS: git clone https://github.com/Liam-Henry-BBD/GrabIt.git
   SSH: git clone git@github.com:Liam-Henry-BBD/GrabIt.git

   