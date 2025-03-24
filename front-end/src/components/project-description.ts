import { LitElement, html, css } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import './create-task-modal';

interface Task {
	id: string;
	projectId: string;
	name: string;
	description: string;
	deadline?: Date;
	createdAt: Date;
	difficulty: 'easy' | 'medium' | 'hard';
}

@customElement('project-description')
export class ProjectDetails extends LitElement {
	@state() isModalOpen: boolean = false;
	@state() tasks: Task[] = [];
	@state() project = {
		id: '12345',
		name: 'Name me',
		description: 'Describe me',
		createdAt: new Date(),
		updatedAt: new Date(),
		collaborators: ['me1@example.com', 'me2@example.com']
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

	handleCreateTask(taskData: Omit<Task, 'id' | 'createdAt'>): void {
		const newTask: Task = {
			...taskData,
			id: Date.now().toString(),
			createdAt: new Date()
		};
		this.tasks = [...this.tasks, newTask];
		this.isModalOpen = false;
	}

	handleRemoveTask(taskId: string): void {
		this.tasks = this.tasks.filter(task => task.id !== taskId);
	}

	toggleModal(): void {
		this.isModalOpen = !this.isModalOpen;
	}

	getDifficultyStyle(difficulty: Task['difficulty']) {
		switch (difficulty) {
			case 'easy':
				return 'color: #00ff00';
			case 'medium':
				return 'color: #ffff00';
			case 'hard':
				return 'color: #ff0000';
			default:
				return 'color: #F7F0F0';
		}
	}

	render() {
		return html`
			<div class="project-header">
				<h1>${this.project.name}</h1>
				<p>${this.project.description}</p>
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
						return html` <div class="collaborator-badge" title=${collaborator}>${initials}</div> `;
					})}
				</div>
			</div>

			${this.isModalOpen
				? html`
						<create-task-modal
							.isOpen=${this.isModalOpen}
							.projectId=${this.project.id}
							@task-submit=${(e: CustomEvent) => this.handleCreateTask(e.detail)}
							@modal-close=${this.toggleModal}
						></create-task-modal>
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
									<h3>${task.name}</h3>
								</div>
								<p>${task.description}</p>
								${task.deadline ? html`<p>Deadline: ${task.deadline.toLocaleDateString()}</p>` : ''}
								<p>Created: ${task.createdAt.toLocaleDateString()}</p>
								<p style="${this.getDifficultyStyle(task.difficulty)}">Difficulty: ${task.difficulty}</p>
								<button class="remove-btn" @click=${() => this.handleRemoveTask(task.id)}>Remove</button>
							</div>
						`
					)}
				</div>
			</div>
		`;
	}
}
