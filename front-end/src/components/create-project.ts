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

	static styles = css`
		:host {
			display: block;
			max-width: 600px;
			margin: auto;
			padding: 20px;
			font-family: sans-serif;
			background-color: #242423;
			color: #f7f0f0;
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

		div button {
			background-color: #f9a03f;
			color: #f7f0f0;
			cursor: pointer;
			width: 40px;
			height: 40px;
			font-size: 15px;
			display: flex;
			align-items: center;
			justify-content: center;
			border: none;
			border-radius: 50%;
		}

		div button:hover {
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

		.collaborator-item {
			padding: 5px 10px;
			border-bottom: 2px solid rgba(255, 255, 255, 0.2);
		}

		.collaborator-item:last-child {
			border-bottom: none;
		}
	`;

	handleInput(e: Event) {
		const target = e.target as HTMLInputElement;
		const name = target.name as keyof this;
		(this[name] as string) = target.value;
	}

	handleSubmit(e: Event) {
		e.preventDefault();
		if (!this.name || !this.description) return;
		const project = {
			id: Date.now().toString(),
			name: this.name,
			description: this.description,
			collaborators: this.collaborators,
			createdAt: new Date().toLocaleString(),
			updatedAt: new Date().toLocaleString(),
			tasks: []
		};
		this.projects = [...this.projects, project];
		this.name = '';
		this.description = '';
		this.collaborators = [];
		const projectId = Date.now().toString();
		Router.go(`/project/${projectId}`);
	}

	handleAddCollaborator() {
		if (this.collaboratorEmail && !this.collaborators.includes(this.collaboratorEmail)) {
			this.collaborators = [...this.collaborators, this.collaboratorEmail];
			this.collaboratorEmail = '';
		}
	}

	renderCollaboratorList() {
		if (!this.collaborators.length) return '';
		return html` <div class="collaborator-list">${this.collaborators.map(collaborator => html` <div class="collaborator-item">${collaborator}</div> `)}</div> `;
	}

	render() {
		return html`
			<h1>Create New Project</h1>
			<form @submit=${this.handleSubmit}>
				<h2>Project Name</h2>
				<input type="text" name="name" .value=${this.name} @input=${this.handleInput} placeholder="Write the project name here" required />
				<h2>Project Description</h2>
				<textarea name="description" .value=${this.description} @input=${this.handleInput} placeholder="Write the project description here" required></textarea>
				<h2>Add Collaborators</h2>
				<div class="collaborator-container">
					<input type="email" name="collaboratorEmail" .value=${this.collaboratorEmail} @input=${this.handleInput} placeholder="Enter collaborator email" />
					<button type="button" @click=${this.handleAddCollaborator}>+</button>
				</div>
				${this.renderCollaboratorList()}
				<button type="submit">Create Project</button>
			</form>
		`;
	}
}
