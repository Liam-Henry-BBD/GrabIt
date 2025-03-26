import { LitElement, html, css } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import '../tasks/create-task-modal';

interface Task {
	taskID: any;
	id: string;
	projectID: string;
	projectName: string;
	projectDescription: string;
	taskName: string;
	taskDescription: string;
	taskDeadline?: Date;
	createdAt: Date;
	difficulty: 5 | 10 | 15;
}

@customElement('project-overview')
export class ProjectOverview extends LitElement {
	@state() isModalOpen: boolean = false;
	@state() tasks: Task[] = [];
	@state() project = {
		projectID: '',
		projectName: '',
		projectDescription: '',
		createdAt: new Date(),
		updatedAt: new Date(),
		collaborators: [] as string[]
	};

	static styles = css`
		:host {
			display: block;
			max-width: 800px;
			margin: auto;
			padding: 20px;
			font-family: sans-serif;
			background-color: #242423;
			color: #f7f0f0;
			border-radius: 8px;
		}

		.project-header {
			background-color: #50899120;
			padding: 20px;
			border-radius: 8px;
			margin-bottom: 20px;
		}

		.project-header h1 {
			font-size: 2rem;
			color: #f9a03f;
		}

		.project-header p {
			color: #f7f0f0;
			margin-top: 8px;
		}

		.dates-container {
			display: flex;
			justify-content: space-between;
			margin-top: 30px;
		}

		.collaborators-container {
			display: flex;
			flex-wrap: wrap;
			gap: 10px;
			margin-top: 15px;
		}

		.collaborator-badge {
			width: 40px;
			height: 40px;
			display: flex;
			align-items: center;
			justify-content: center;
			border-radius: 50%;
			background-color: #508991;
			color: #f7f0f0;
			font-weight: bold;
			text-transform: uppercase;
			font-size: 14px;
			cursor: default;
		}

		.collaborator-badge:hover {
			background-color: #f9a03f;
			color: #242423;
		}

		.task-list-container {
			margin-top: 20px;
		}

		.task-list-title {
			font-size: 1.5rem;
			font-weight: bold;
			margin-bottom: 10px;
		}

		.task-list {
			display: flex;
			flex-direction: column;
			gap: 10px;
		}

		.task {
			border-radius: 8px;
			padding: 15px;
			background-color: rgba(80, 137, 145, 0.1);
			display: flex;
			flex-direction: column;
		}

		.task .header {
			display: flex;
			justify-content: space-between;
			align-items: center;
		}

		.task .remove-btn {
			background-color: #f9a03f;
			color: #fff;
			border: none;
			padding: 6px 12px;
			border-radius: 4px;
			cursor: pointer;
			align-self: flex-end;
		}

		.task .remove-btn:hover {
			background-color: #508991;
		}

		button {
			background-color: #f9a03f;
			color: #f7f0f0;
			border: none;
			border-radius: 5px;
			padding: 8px 16px;
			cursor: pointer;
			margin-bottom: 10px;
		}

		button:hover {
			background-color: #508991;
		}

		.no-tasks {
			text-align: center;
			color: #f7f0f0;
			padding: 60px;
			border: 2px dashed #f9a03fcc;
			border-radius: 8px;
			background-color: rgba(80, 137, 145, 0.1);
		}

		.task-list-header {
			display: flex;
			justify-content: space-between;
			align-items: center;
		}

		.task-list-title {
			margin: 0;
			margin-bottom: 10px;
		}
	`;

	connectedCallback() {
		super.connectedCallback();
		this.fetchProjectData();
	}

	async fetchProjectData() {
		const projectId = window.location.pathname.split('/').pop();
		if (!projectId) {
			console.error('Project ID is missing from the URL');
			return;
		}

		try {
			const token = localStorage.getItem('token');
			if (!token) {
				throw new Error('No authentication token found. Please log in.');
			}

			const res = await fetch(`http://localhost:8081/api/projects/${projectId}`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			});

			if (res.ok) {
				const projectData = await res.json();

				this.project = {
					projectID: projectData.projectID,
					projectName: projectData.projectName,
					projectDescription: projectData.projectDescription,
					createdAt: new Date(projectData.createdAt),
					updatedAt: new Date(projectData.updatedAt),
					collaborators: projectData.collaborators || []
				};
				this.fetchTasks();
			} else {
				console.error(`Failed to fetch project data. Status: ${res.status}`);
			}
		} catch (error) {
			console.log(error);
			console.error('Error fetching project data:', error);
		}
	}

	async fetchTasks() {
		const projectId = this.project.projectID;
		if (!projectId) {
			console.error('Project ID is undefined. Tasks cannot be fetched.');
			return;
		}

		try {
			const token = localStorage.getItem('token');
			if (!token) {
				throw new Error('No authentication token found. Please log in.');
			}

			const res = await fetch(`http://localhost:8081/api/projects/${projectId}/tasks`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			});

			if (res.ok) {
				const tasks = await res.json();

				this.tasks = tasks
					.filter((task: any) => task.isActive !== 0)
					.map((task: any) => ({
						...task,
						createdAt: task.createdAt ? new Date(task.createdAt) : null,
						deadline: task.deadline ? new Date(task.deadline) : null
					}));
			} else {
				console.error(`Failed to fetch tasks. Status: ${res.status}`);
			}
		} catch (error) {
			console.error('Error fetching tasks:', error);
		}
	}
	async handleCreateTask(taskData: Omit<Task, 'taskID' | 'createdAt'>) {
		const difficultyMapping = {
			easy: 5,
			medium: 10,
			hard: 15
		};

		const newTask = {
			...taskData,
			difficulty: difficultyMapping[taskData.difficulty as unknown as 'easy' | 'medium' | 'hard'],
			project: { projectID: this.project.projectID },
			taskPoint: { taskPointID: difficultyMapping[taskData.difficulty as unknown as 'easy' | 'medium' | 'hard'] },
			taskStatus: { taskStatusID: 1 }
		};

		try {
			const token = localStorage.getItem('token');
			if (!token) {
				throw new Error('No authentication token found. Please log in.');
			}

			const res = await fetch('http://localhost:8081/api/tasks', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				},
				body: JSON.stringify(newTask)
			});

			if (res.ok) {
				const createdTask = await res.json();
				this.tasks = [...this.tasks, createdTask];
				this.isModalOpen = false;
			} else {
				console.error('Failed to create task.');
			}
		} catch (error) {
			console.error('Error creating task:', error);
		}
	}

	async handleRemoveTask(taskID: string) {
		try {
			const token = localStorage.getItem('token');
			if (!token) {
				throw new Error('No authentication token found. Please log in.');
			}

			const res = await fetch(`http://localhost:8081/api/tasks/${taskID}`, {
				method: 'DELETE',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			});

			if (res.ok) {
				this.tasks = this.tasks.filter(task => task.taskID !== taskID);
			} else {
				console.error('Failed to remove task');
			}
		} catch (error) {
			console.error('Error removing task:', error);
		}
	}

	getDifficultyColor(difficulty: Task['difficulty']) {
		switch (difficulty) {
			case 5:
				return 'green';
			case 10:
				return 'yellow';
			case 15:
				return 'red';
		}
	}

	toggleModal(): void {
		this.isModalOpen = !this.isModalOpen;
	}

	render() {
		return html`
			<div class="project-header">
				<h1>${this.project.projectName}</h1>
				<p>${this.project.projectDescription}</p>
				<div class="dates-container">
					<p>Created: ${this.project.createdAt.toLocaleDateString()}</p>
					<p>Updated: ${this.project.updatedAt.toLocaleDateString()}</p>
				</div>
				<h3>Collaborators:</h3>
				<div class="collaborators-container">
					${this.project.collaborators.map(collaborator => {
						const initials = collaborator
							.split('@')[0]
							.split('.')
							.map(namePart => namePart[0])
							.join('')
							.toUpperCase();
						return html`<div class="collaborator-badge" title=${collaborator}>${initials}</div>`;
					})}
				</div>
			</div>

			${this.isModalOpen
				? html`
						<create-task
							.isOpen=${this.isModalOpen}
							.projectId=${this.project.projectID}
							@task-submit=${(e: CustomEvent) => this.handleCreateTask(e.detail)}
							@modal-close=${this.toggleModal}
						></create-task>
					`
				: ''}

			<div class="task-list-container">
				<div class="task-list-header">
					<h2 class="task-list-title">Tasks</h2>
					<button @click=${this.toggleModal}>Add Task</button>
				</div>

				<div class="no-tasks" ?hidden=${this.tasks.length > 0}>
					<p>No tasks yet. Click "Add Task" to create one.</p>
				</div>
				<div class="task-list">
					${this.tasks.map(
						task => html`
							<div class="task">
								<div class="header">
									<h3>${task.taskName}</h3>
								</div>
								<p>${task.taskDescription}</p>
								${task.taskDeadline ? html`<p>Deadline: ${task.taskDeadline.toLocaleDateString()}</p>` : ''}
								<p>Created: ${task.createdAt ? task.createdAt.toLocaleDateString() : 'Unknown'}</p>
								<p style="color: ${this.getDifficultyColor(task.difficulty)}">Difficulty: ${task.difficulty}</p>
								<button class="remove-btn" @click=${() => this.handleRemoveTask(task.taskID)}>Remove</button>
							</div>
						`
					)}
				</div>
			</div>
		`;
	}
}
