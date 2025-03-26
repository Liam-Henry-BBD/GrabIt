export interface Task {
    taskID: number
    taskName: string
    taskDescription: string
    taskCreatedAt: string
    taskPointID: number
    taskStatusID: number
    taskReviewRequestedAt: any
  }
  

export interface Project {
    projectID: number
    projectName: string
    projectDescription: string
    createdAt: string
    updatedAt: string
    active: boolean
}
