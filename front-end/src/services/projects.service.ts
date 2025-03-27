import sendRequest from "./requests";

export const getProjectTasks = async (projectID: number) => {
    try {
        const data = await sendRequest(`/projects/${projectID}/tasks`);
        return data;
    } catch (error) {
        console.error('Error fetching projects:', error);
        return null; 
    }
}

export const getMyProjectTasks = async (projectID: number) => {
    try {
        const data = await sendRequest(`/projects/${projectID}/my-tasks`);
        return data;
    } catch (error) {
        console.error('Error fetching projects:', error);
        return null; 
    }
}

export const getProject = async (projectID: number) => {
    try {
        const data = await sendRequest(`/projects/${projectID}`);
        return data;
    } catch (error) {
        return null;
    }
} 