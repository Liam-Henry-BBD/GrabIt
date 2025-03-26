import sendRequest from "./requests";

export async function getLeaderboard(projectID: number) {
    const data = await sendRequest(`/projects/${projectID}/leaderboard`);
    return data; 
}