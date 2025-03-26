import { LitElement, html } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import { createProjectStyles } from './projects.styles';

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

	static styles = createProjectStyles;

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

	handleAddCollaborator() {
		const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		if (this.collaboratorEmail && emailPattern.test(this.collaboratorEmail) && !this.collaborators.includes(this.collaboratorEmail)) {
			this.collaborators = [...this.collaborators, this.collaboratorEmail];
			this.collaboratorEmail = '';
		} else {
			alert('Please enter a valid email address.');
		}
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

	renderCollaboratorList() {
		if (!this.collaborators.length) return '';
		return html`<div class="collaborator-list">${this.collaborators.map(collaborator => html`<div>${collaborator}</div>`)}</div>`;
	}

	render() {
		return html`
		<header class="create-project-header">
			<img  id="logo" src="/src/home/home_images/GI_logo-white.png" alt="Logo">
		</header>
		<h1> Create New Project</h1>
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
				<a href="http://localhost:8000/home" class="cancel-btn">Cancel</a>
			</form>
		`;
	}
}
