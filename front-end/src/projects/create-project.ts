import { LitElement, html, css } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import { Router } from '@vaadin/router';

@customElement('create-project')
export class CreateProject extends LitElement {
	@state() name: string = '';
	@state() description: string = '';
	@state() collaboratorEmail: string = '';
	@state() collaborators: string[] = [];
	@state() projects: any[] = [];

	private urls = {
		getProjects: 'http://localhost:8081/api/projects',
		createProject: 'http://localhost:8081/api/projects'
	};

	static styles = css`
		:host {
			display: block;
			max-width: 600px;
			margin: auto;
			padding: 20px;
			font-family: sans-serif;
			background-color: #242423;
			border-radius: 10px;
		}

		h1 {
			color: #f9a03f;
		}

		h2 {
			font-size: 1rem;
			color: #f7f0f0;
			padding-top: 20px;
		}

		input,
		textarea {
			width: calc(100% - 20px);
			margin-top: 10px;
			padding: 10px;
			border-radius: 5px;
			border: 1px solid #508991;
			background-color: #242423;
			color: #f7f0f0;
		}

		button {
			background-color: #f9a03f;
			color: #f7f0f0;
			cursor: pointer;
			border: none;
			padding: 10px 20px;
			border-radius: 5px;
			margin-top: 10px;
		}

		button:hover {
			background-color: #508991;
		}

		.collaborator-container {
			display: flex;
			width: calc(100% - 2px);
			gap: 10px;
			align-items: center;
			margin-top: 10px;
			margin-bottom: 20px;
		}

		.collaborator-list {
			background-color: #508991;
			color: #f7f0f0;
			border-radius: 5px;
			margin-top: 10px;
			padding: 10px;
		}
	`;

	connectedCallback() {
		super.connectedCallback();
		this.apiRequest(this.urls.getProjects, 'GET')
			.then(data => {
				this.projects = data;
			})
			.catch(error => {
				console.error('Error fetching projects:', error);
			});
	}

	async apiRequest(url: string, method: string, body?: any) {
		try {
			const token = localStorage.getItem('token');
			if (!token) throw new Error('No authentication token found.');

			const options: RequestInit = {
				method,
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			};
			if (body) options.body = JSON.stringify(body);

			const response = await fetch(url, {
				method,
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				},
				...options
			});
			if (!response.ok) throw new Error(`Network response was not ok. Status: ${response.status}`);
			return await response.json();
		} catch (error) {
			console.error('Error during API request:', error);
			throw error;
		}
	}

	handleInput(e: Event) {
		const target = e.target as HTMLInputElement;
		const name = target.name as keyof this;
		(this[name] as string) = target.value;
	}

	async handleSubmit(e: Event) {
		e.preventDefault();
		if (!this.name || !this.description) return;

		const project = {
			projectName: this.name,
			projectDescription: this.description,
			collaborators: this.collaborators
		};

		this.apiRequest(this.urls.createProject, 'POST', project)

			.then(newProject => {
				window.location.href = `/project/${newProject.projectId}`;
			})
			.catch(error => {
				console.error('Error creating project:', error);
			});
	}

	handleAddCollaborator() {
		if (this.collaboratorEmail && !this.collaborators.includes(this.collaboratorEmail)) {
			this.collaborators = [...this.collaborators, this.collaboratorEmail];
			this.collaboratorEmail = '';
		}
	}

	cancel() {
		Router.go('/');
	}

	renderCollaboratorList() {
		if (!this.collaborators.length) return '';
		return html` <div class="collaborator-list">${this.collaborators.map(collaborator => html`<div>${collaborator}</div>`)}</div> `;
	}

	render() {
		return html`
			<h1>Create New Project</h1>
			<form @submit=${this.handleSubmit}>
				<h2>Project Name</h2>
				<input type="text" name="name" .value=${this.name} @input=${this.handleInput} placeholder="Write your project name here" required />
				<h2>Project Description</h2>
				<textarea name="description" .value=${this.description} @input=${this.handleInput} placeholder="Write your project description here" required></textarea>
				<h2>Add Collaborators</h2>
				<div class="collaborator-container">
					<input type="email" name="collaboratorEmail" .value=${this.collaboratorEmail} @input=${this.handleInput} placeholder="Enter the collaborator's email" />
					<button type="button" @click=${this.handleAddCollaborator}>+</button>
				</div>
				${this.renderCollaboratorList()}
				<button type="submit">Create Project</button>
				<button class="cancel-btn" @click="${this.cancel}">Cancel</button>
			</form>
		`;
	}
}
