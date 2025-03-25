import sendRequest from "./requests";

export async function getLeaderboard(projectID: string) {
    const data = await sendRequest(`/api/projects/${projectID}/leaderboard`);
    return data; 
}