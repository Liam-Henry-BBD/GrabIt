import { LitElement, html } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import { projectOverviewStyles } from './projects.styles';
import { formatDate } from '../utils/app';
import '../tasks/create-task-modal';
import './update-project-modal';
import '../auth/activities/auth-router';

interface Task {
	taskID: any;
	id: string;
	projectID: string;
	projectName: string;
	projectDescription: string;
	taskName: string;
	taskDescription: string;
	difficulty: 'easy' | 'medium' | 'hard';
	taskDeadline?: any;
	createdAt: any;
	taskCreatedAt: any;
	taskPointID: 5 | 10 | 15;
	active: boolean;
}

@customElement('project-overview')
export class ProjectOverview extends LitElement {
	@state() isModalOpen: boolean = false;
	@state() isUpdateModalOpen: boolean = false;
	@state() tasks: Task[] = [];
	@state() project = {
		projectID: '',
		projectName: '',
		projectDescription: '',
		createdAt: Date(),
		taskCreatedAt: Date(),
		updatedAt: Date(),
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
					taskCreatedAt: formatDate(projectData.taskCreatedAt),
					createdAt: formatDate(projectData.createdAt),
					updatedAt: formatDate(projectData.updatedAt),
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
					.filter((task: any) => task.active != false)
					.map((task: any) => ({
						...task,
						difficulty: task.difficulty,
						createdAt: formatDate(task.createdAt),
						deadline: formatDate(task.taskDeadline)
					}));
				console.log('Filtered & Mapped Tasks:', this.tasks, tasks.taskDifficulty);
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
			project: { projectID: this.project.projectID },
			taskPoint: { taskPointID: difficultyMapping[taskData.difficulty] },
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
		const confirmDelete = window.confirm('Are you sure you want to delete this task?');
		if (!confirmDelete) return;

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

	getDifficultyColor(difficulty: Task['taskPointID']) {
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
		<auth-router>
			<section class="header">
				<img id="logo" src="/src/home/home_images/GI_logo-white.png" alt="Logo" @click=${() => (window.location.href = 'http://localhost:8000/home')} />
			</section>
			<div class="project-header">
				<div class="back">
					<a  href= 'http://localhost:8000/home/${this.project.projectID}'> ← Back to Project</a>
				</div>
				<h1>${this.project.projectName}</h1>
				<p>${this.project.projectDescription}</p>
				<div class="dates-container">
					<p>Created: ${formatDate(this.project.createdAt)}</p>
					<p>Updated: ${formatDate(this.project.updatedAt)}</p>
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
				<button class="update-btn" @click=${() => (this.isUpdateModalOpen = true)}>Update Project</button>
			</div>

			${this.isModalOpen
				? html`
						<create-task
							.isOpen=${this.isModalOpen}
							.projectId=${this.project.projectID}
							@task-submit=${(e: CustomEvent) => this.handleCreateTask(e.detail)}
							@modal-close=${() => (this.isModalOpen = false)}
						></create-task>
					`
				: ''}
			${this.isUpdateModalOpen
				? html`
						<update-project
							.isOpen=${this.isUpdateModalOpen}
							.projectID=${this.project.projectID}
							.projectName=${this.project.projectName}
							.projectDescription=${this.project.projectDescription}
							@modal-close=${() => (this.isUpdateModalOpen = false)}
							@project-updated=${this.fetchProjectData}
						></update-project>
					`
				: ''}

			<div class="task-list-container">
				<div class="task-list-header">
					<h2 class="task-list-title">Tasks</h2>
					<button @click=${() => (this.isModalOpen = true)}>Add Task</button>
				</div>

				<div class="no-tasks" ?hidden=${this.tasks.length > 0}>
					<p>No tasks yet. Click "Add Task" to create one.</p>
				</div>
				<div class="task-list">
					${this.tasks.map(
						task => html`
							<div class="task">
								<div class="task-header">
									<h3>${task.taskName}</h3>
								</div>
								<p>${task.taskDescription}</p>
								${task.taskDeadline ? html`<p>Deadline: ${formatDate(task.taskDeadline)}</p>` : ''}
								<p>Created: ${formatDate(task.taskCreatedAt)}</p>
								<p style="color: ${this.getDifficultyColor(task.taskPointID)}">Difficulty: ${task.taskPointID}</p>
								<button class="remove-btn" @click=${() => this.handleRemoveTask(task.taskID)}>Remove</button>
							</div>
						`
					)}
				</div>
			</div>
			</auth-router>
		`;
	}
}
