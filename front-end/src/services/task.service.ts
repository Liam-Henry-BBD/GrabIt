import sendRequest from "./requests"

export const grabTask = async (taskID: number, projectID: number) => {
    try {
        const response = await sendRequest(`/tasks/${taskID}/project/${projectID}`, {
            method: "POST"
        });
        return response;
    } catch (error) {
        return null;
    }
}

export const requestTaskReview = async (taskID: number) => {
    try {
        const response = await sendRequest(`/tasks/${taskID}/status/3`, {
            method: "PUT"
        });
        return response;
    } catch (error) {
        return null;
    }
}

export const rejectTaskReview = async (taskID: number) => {
    try {
        const response = await sendRequest(`/tasks/${taskID}/status/2`, {
            method: "PUT"
        });
        return response;
    } catch (error) {
        return null;
    }
}

export const completeTask = async (taskID: number) => {
    try {
        const response = await sendRequest(`/tasks/${taskID}/status/4`, {
            method: "PUT"
        });
        return response;
    } catch (error) {
        return null;
    }
    
}