# GrabIt

A gamified task management system

The GrabIt system is a project management tool designed for teams to manage tasks within projects. It allows team leads to create projects, set tasks, assign points, and track task completion with deadlines. Team members can grab tasks from the available list and work on them. Points are assigned based on the difficulty of the task.

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
   The team lead adds deadlines to each task.

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

## Technologies

This system could be implemented with the following technologies:

- **Backend**: Java (Spring boot)
- **Database**: MS SQL Server
- **Authentication**: Google OAuth2

## Future Features

- **Task Discussion**: Ability for team members to comment or discuss tasks.
- **Advanced Leaderboard**: With filters for showing points per project or per user.
- **Notifications**: Alerts for approaching deadlines or task updates.

---

## Installation

To set up the Task Grab system locally, follow these steps:

1. **Clone the repository:**

```bash
   HTTPS: git clone https://github.com/Liam-Henry-BBD/GrabIt.git
   SSH: git clone git@github.com:Liam-Henry-BBD/GrabIt.git
```

## Dependencies

This project utilizes several key dependencies to provide functionality and support various features. Below is a comprehensive list of the dependencies used in the GrabIt project, along with their purpose and documentation.

**Spring Boot Dependencies**
```sh
spring-boot-starter-parent
Group ID: org.springframework.boot
Artifact ID: spring-boot-starter-parent
Version: 3.4.2
Purpose: Provides default configurations for Spring Boot applications, including dependency management and plugin configurations.
```

```sh
spring-boot-starter-security
Group ID: org.springframework.boot
Artifact ID: spring-boot-starter-security
Purpose: Provides security features for Spring Boot applications, including authentication and authorization.
```

```sh
spring-boot-starter-oauth2-client
Group ID: org.springframework.boot
Artifact ID: spring-boot-starter-oauth2-client
Purpose: Supports OAuth2 client features, allowing the application to authenticate users via OAuth2 providers like GitHub.
```

```sh
spring-boot-starter-data-jpa
Group ID: org.springframework.boot
Artifact ID: spring-boot-starter-data-jpa
Purpose: Provides support for JPA using Spring Data JPA, enabling interaction with relational databases.
```

```sh
spring-boot-devtools
Group ID: org.springframework.boot
Artifact ID: spring-boot-devtools
Scope: runtime
Optional: true
Purpose: Offers development tools for Spring Boot applications, including automatic restarts and live reload.
```

```sh
spring-boot-starter-test
Group ID: org.springframework.boot
Artifact ID: spring-boot-starter-test
Scope: test
Purpose: Provides testing support for Spring Boot applications, including JUnit, Hamcrest, and Mockito.
```

```sh
spring-boot-starter-validation
Group ID: org.springframework.boot
Artifact ID: spring-boot-starter-validation
Purpose: Supports bean validation using Hibernate Validator.
```

```sh
spring-boot-starter-web
Group ID: org.springframework.boot
Artifact ID: spring-boot-starter-web
Purpose: Supports building web applications using Spring MVC, including RESTful web services.

**Database Dependencies**
```sh
mssql-jdbc
Group ID: com.microsoft.sqlserver
Artifact ID: mssql-jdbc
Purpose: Provides the JDBC driver for Microsoft SQL Server, enabling the application to connect to SQL Server databases.
```

**Utility Dependencies**
```sh
lombok
Group ID: org.projectlombok
Artifact ID: lombok
Optional: true
Scope: provided
Purpose: Reduces boilerplate code in Java through annotations for getters, setters, and constructors.
```

**Build Plugins**
```sh
maven-compiler-plugin
Group ID: org.apache.maven.plugins
Artifact ID: maven-compiler-plugin
Purpose: Configures the Maven compiler plugin to use Lombok for annotation processing.
```

```sh
spring-boot-maven-plugin
Group ID: org.springframework.boot
Artifact ID: spring-boot-maven-plugin
Purpose: Provides support for packaging and running Spring Boot applications as executable JARs or WARs.
```

## Environmental Variables 
You can set environment variables directly on your operating system or in your deployment environment.

**Linux/macOS:**
```sh
sh
export DB_URI=grabit_db_uri
export DB_PORT=grabit_db_port
export DB_NAME=grabit_db_name
export DB_USER=grabit_db_user
export DB_PASSWORD=grabit_db_password
export CLIENT_ID=grabit_client_id
export CLIENT_SECRET=grabit_client_secret
```

**Windows:**
```sh
cmd
setx DB_URI "grabit_db_ur"
setx DB_PORT "grabit_db_port"
setx DB_NAME "grabit_db_name"
setx DB_USER "grabit_db_user"
setx DB_PASSWORD "grabit_db_password"
setx CLIENT_ID "grabit_client_id"
setx CLIENT_SECRET "grabit_client_secret"
```

## Running the Project's API Endpoints

1. **Prerequisites:**

- Ensure that you have Java Development Kit (JDK) installed.
- Install Maven for project building and dependency management.
- Set up a Spring Boot application.
- Ensure you have an instance of your schema and tables is running.

2. **Configure the Application:**

- Setup environmental variables to match the database configuration (application.properties file).

```sh
application.properties
spring.datasource.url=jdbc:sqlserver://the_grabit_uri:grabit_port:grabit:database:grabit_database_name
spring.datasource.username=grabit_username
spring.datasource.password=grabit_password
```

3. **Build the Project:**

- Open a terminal and navigate to the project's root directory.
- Run the following command to build the project:

```sh
mvn clean install
```

4. **Run the Application:**

- Run the Spring Boot application using the following command:

```sh
mvn spring-boot:run
```

5. **Using the Endpoints:**

- You can use tools like Postman or cURL to interact with the API endpoints. The base URL should be `http://localhost:8081`.


## Endpoints for Project

| **Method** | **Endpoint**                              | **Description**                                   |
| ---------- | ----------------------------------------- | ------------------------------------------------- |
| POST       | `/api/projects`                           | Create or update a project.                       |
| GET        | `/api/projects`                           | Retrieve all projects.                            |
| GET        | `/api/projects/{projectID}`               | Retrieve a project by its ID.                     |
| DELETE     | `/api/projects/{projectID}`               | Close a project by its ID.                        |
| GET        | `/api/projects/{projectID}/tasks`         | Get all tasks associated with a specific project. |
| GET        | `/api/projects/{projectID}/collaborators` | Get all collaborators for a specific project.     |
| GET        | `/api/projects/{projectID}/leaderboard`   | Get the leaderboard for a specific project.       |
| PUT        | `/api/projects/{projectID}`               | Update a project by its ID.                       |

**POST Sample Request**

```
{
    "projectName": "Updated Website Development",
    "projectDescription": "Updated project description with new features.",
}
```

**PUT Sample Request**

```
{
        "projectDescription": "A project to develop a new company website."
}
```

## Endpoints for Task

| **Method** | **Endpoint**                         | **Description**                             |
| ---------- | -------------------------------------|---------------------------------------------|
| POST       | `/api/tasks`                         | Create a new task.                          |
| GET        | `/api/tasks/{taskID}`                | Retrieve a task by itsID.                   |
| PUT        | `/api/tasks/{taskID}`                | Update a task by its ID.                    |
| DELETE     | `/api/tasks/{taskID}`                | Delete a task by its ID.                    |
| GET        | `/api/tasks/{taskID}/collaborators`  | Get all collaborators for a specific task.  |
| PUT        | `/api/tasks/{taskID}/status/{taskStatusID}`| Update the status of a task.          |
| GET        | `/api/projects/{projectID}/tasks`    | Retrieve all tasks associated with a specific project.|

**POST Sample Request**

```
{
    "taskName": "Design Homepage",
    "taskDescription": "Create the homepage layout for the new website.",
    "taskDeadline": "2023-03-15",
    "project": {
        "projectID": 1
    },
    "taskPoint": {
        "taskPointID": 1
    },
    "taskStatus": {
        "taskStatusID": 1
    }
}
```

**PUT Sample Request(task)**

```
{
    "taskName": "Updated Design Homepage",
    "taskDescription": "Revise the homepage layout based on feedback.",
    "taskDeadline": "2023-03-20"
}

```

**PUT Sample Request(task status)**

```
{
    "taskStatusID": 2
}
```

## Endpoints for Project Collaborator

| Method | Endpoint                                       | Description                                        |
| ------ | ---------------------------------------------- | -------------------------------------------------- |
| GET    | `/api/project-collaborators/{projectCollabID}` | Retrieve a project collaborator by their ID.       |
| DELETE | `/api/project-collaborators/{projectCollabID}` | Deactivate a project collaborator by their ID.     |
| GET    | `/api/projects/{projectID}/collaborators`      | Retrieve all collaborators for a specific project. |

## Endpoints for Task Collaborator

| Method | Endpoint                                          | Description                                     |
| ------ | ------------------------------------------------- | ----------------------------------------------- |
| GET    | `/api/task-collaborators`                         | Retrieve all task collaborators.                |
| GET    | `/api/task-collaborators/{taskCollabID}`          | Retrieve a task collaborator by their ID.       |
| DELETE | `/api/task-collaborators/{taskCollabID}`          | Deactivate a task collaborator by their ID.     |
| PUT    | `/api/task-collaborators/{taskCollabID}/activate` | Activate a task collaborator by their ID.       |
| GET    | `/api/task-collaborators/task/{taskID}`           | Retrieve all collaborators for a specific task. |
