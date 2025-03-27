import sendRequest from "./requests";

export async function getUser() {
    try {
        const response = await sendRequest('/user/validate');
        return response;
    } catch (error) {
        console.error('Error validating token:', error);
    }
}