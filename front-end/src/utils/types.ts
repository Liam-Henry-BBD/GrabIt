export interface Task {
    taskID: number
    taskName: string
    taskDescription: string
    taskCreatedAt: string
    taskPointID: number
    taskStatusID: number
    taskReviewRequestedAt: any;
    userID: number;
    projectID: number;
    isActive?: boolean;
  }
  

export interface Project {
    projectID: number
    projectName: string
    projectDescription: string
    createdAt: string
    updatedAt: string
    active: boolean
    collaboratorRole: number
}


export interface User {
    email: string
    fullName: string
    picture: string
    verified: boolean
    userID: number;
  }
  