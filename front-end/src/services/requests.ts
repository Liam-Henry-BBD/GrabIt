async function sendRequest(endpoint: string, options: RequestInit = {}) {
    const token = localStorage.getItem('token');
    if (!token && window.location.pathname) {
        window.location.href = '/login';
        localStorage.removeItem('token');
        return;
    }
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
        ...options.headers
    };

    const url = 'http://localhost:8081/api' + endpoint;
    const response = await fetch(url, {
        ...options,
        headers
    });

    if (!response.ok) {
        // window.location.href = '/login';
        // localStorage.removeItem('token');
        return;
    }

    return response.json();
}

export default sendRequest;