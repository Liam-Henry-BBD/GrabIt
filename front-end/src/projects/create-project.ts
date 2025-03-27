import { LitElement, html } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import { createProjectStyles } from './projects.styles';
import '../auth/activities/auth-router';


@customElement('create-project')
export class CreateProject extends LitElement {
	@state() name: string = '';
	@state() description: string = '';
	@state() collaboratorEmail: string = '';
	@state() collaborators: { gitHubID: string; userID: number }[] = [];
	@state() projects: any[] = [];

	private urls = {
		getProjects: 'http://localhost:8081/api/projects',
		createProject: 'http://localhost:8081/api/projects',
		searchForCollaborator: 'http://localhost:8081/api/user/search?query=',
		addCollaborators: 'http://localhost:8081/api/project-collaborators/list'
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

	async handleAddCollaborator() {
		try {
			const searchUrl = `${this.urls.searchForCollaborator}${this.collaboratorEmail}`;
			const results = await this.apiRequest(searchUrl, 'GET');
			if (!results.length) {
				alert('No users found with that email address.');
				return;
			}
			this.renderDropdown(results);
		} catch (error) {
			console.error('Error fetching collaborators:', error);
			alert('Failed to fetch collaborators. Please try again.');
		}
	}

	renderDropdown(results: { gitHubID: string; userID: number }[]) {
		const container = this.shadowRoot?.querySelector('.collaborator-container');
		if (!container) return;

		const existingDropdown = container.querySelector('.dropdown');
		if (existingDropdown) existingDropdown.remove();

		const dropdown = document.createElement('div');
		dropdown.className = 'dropdown';

		results.forEach(result => {
			const item = document.createElement('article');
			item.className = 'dropdown-item';
			item.textContent = result.gitHubID;

			item.addEventListener('click', () => {
				if (!this.collaborators.some(c => c.userID === result.userID)) {
					this.collaborators = [...this.collaborators, result];
					this.collaboratorEmail = '';
					this.requestUpdate();
				}
				closeDropdown();
			});

			dropdown.appendChild(item);
		});

		container.appendChild(dropdown);

		const closeDropdown = () => {
			dropdown.remove();
			document.removeEventListener('click', handleClickOutside);
			document.removeEventListener('keydown', handleKeyDown);
		};

		const handleClickOutside = (event: MouseEvent) => {
			if (!dropdown.contains(event.target as Node)) {
				closeDropdown();
			}
		};

		const handleKeyDown = (event: KeyboardEvent) => {
			if (event.key === 'Escape') {
				closeDropdown();
			}
		};

		document.addEventListener('click', handleClickOutside);
		document.addEventListener('keydown', handleKeyDown);
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
				console.log(newProject);
				this.apiRequest(
					this.urls.addCollaborators,
					'POST',
					 this.collaborators.map(collab => {
						const collaborator: any = { ...collab };
						collaborator.joinedAt = new Date().toISOString();
						collaborator.projectID = newProject.projectId;
						collaborator.isActive = true;
						collaborator.roleID = 2;
						return collaborator;
					})
				).then(res => console.log(res));
				window.location.href = `/project/${newProject.projectId}`;
			})
			.catch(error => {
				console.error('Error creating project:', error);
			});
	}

	renderCollaboratorList() {
		if (!this.collaborators.length) return '';
		return html`<div class="collaborator-list">
			${this.collaborators.map(
				collaborator =>
					html`<div class="colab-list-item">
						${collaborator.gitHubID}<button
							type="button"
							@click=${() => (this.collaborators = this.collaborators.filter(collab => collaborator.gitHubID != collab.gitHubID))}
							class="remove"
						>
							remove
						</button>
					</div>`
			)}
		</div>`;
	}

	render() {
		return html`
			<auth-router>
			<header class="create-project-header">
				<img id="logo" src="/src/home/home_images/GI_logo-white.png" alt="Logo" />
			</header>
			<h1>Create New Project</h1>
			<form @submit=${this.handleSubmit}>
				<h2>Project Name</h2>
				<input type="text" name="name" .value=${this.name} @input=${this.handleInput} placeholder="Write your project name here" required />
				<h2>Project Description</h2>
				<textarea name="description" .value=${this.description} @input=${this.handleInput} placeholder="Write your project description here" required></textarea>
				<h2>Add Collaborators</h2>
				<div class="collaborator-container">
					<input type="text" name="collaboratorEmail" .value=${this.collaboratorEmail} @input=${this.handleInput} placeholder="Enter the collaborator's email" />
					<button type="button" @click=${this.handleAddCollaborator}>+</button>
				</div>
				${this.renderCollaboratorList()}
				<button type="submit">Create Project</button>
				<a href="http://localhost:8000/home" class="cancel-btn">Cancel</a>
			</form>
			</auth-router>
		`;
	}
}
