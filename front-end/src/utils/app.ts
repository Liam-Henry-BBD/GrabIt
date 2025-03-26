import { Task } from './types';

export function formatDate(dateString: string) {
	// localStorage.getItem('token');
	if (!dateString) return 'Not set';
	const date = new Date(dateString);
	return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
}

export function filterTasks(tasks: Task[], targetUserID: number) {
	const taskMap = new Map();

	tasks.forEach(task => {
		const { taskID, userID } = task;

		if (!taskMap.has(taskID)) {
			taskMap.set(taskID, task);
		} else {
			if (userID === targetUserID) {
				taskMap.set(taskID, task);
			}
		}
	});

	return Array.from(taskMap.values());
}
