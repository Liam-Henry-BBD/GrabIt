import { LitElement, html, css } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import { projectOverviewStyles } from './projects.styles';
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

	static styles = projectOverviewStyles;

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
			<section class="header">
				<img id="logo" src="/src/home/home_images/GI_logo-white.png" alt="Logo" @click=${() => window.location.href = 'http://localhost:8000/home'}>
			</section>
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
