import { LitElement, html, css } from 'lit';
import { customElement, state } from 'lit/decorators.js';

@customElement('update-project')
export class UpdateProject extends LitElement {
	@state() projectName: string = '';
	@state() projectDescription: string = '';
	@state() isOpen: boolean = false;
	@state() projectID: string = '';
	@state() collaboratorEmail: string = '';
	@state() collaborators: { gitHubID: string; userID: number }[] = [];

	static styles = css`
		:host {
			display: block;
		}
		.modal {
			position: fixed;
			inset: 0;
			background-color: rgba(0, 0, 0, 0.5);
			display: flex;
			justify-content: center;
			align-items: center;
			padding: 1rem;
		}
		.modal-content {
			background-color: #242423;
			color: #f7f0f0;
			max-width: 400px;
			width: 100%;
			border-radius: 8px;
			box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
		}
		.header {
			display: flex;
			justify-content: space-between;
			padding: 1rem;
			border-bottom: 1px solid #f9a03f;
		}
		h2 {
			color: #f9a03f;
		}
		.form {
			margin-top: 20px;
		}
		.form label {
			display: block;
			margin-bottom: 8px;
		}
		.form input,
		.form textarea {
			width: 100%;
			margin-bottom: 15px;
			padding: 8px;
			border: 1px solid #ccc;
			border-radius: 4px;
		}
		button {
			background: none;
			border: none;
			cursor: pointer;
			color: #f7f0f0;
			padding: 15px;
			background-color: rgba(80, 137, 145, 0.1);
		}
		form {
			padding: 1rem;
			display: flex;
			flex-direction: column;
			gap: 2rem;
		}

		.form input,
		.form textarea,
		select {
			width: 90%;
			padding: 0.5rem;
			padding-bottom: 0.5rem;
			border-radius: 4px;
			border: 1px solid #508991;
			background-color: rgba(171, 233, 241, 0.1);
			margin-top: 10px;
			color: #f7f0f0;
		}
		.colab-list-item {
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 10px 14px;
			margin-bottom: 8px;
			background-color: #508991;
			color: #f7f0f0;
			border-radius: 5px;
			box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
			transition:
				background 0.2s ease-in-out,
				transform 0.1s ease-in-out;
		}

		.colab-list-item:hover {
			background-color: #f9a03f;
			color: #242423;
			transform: translateY(-2px);
		}

		.colab-list-item .remove {
			background-color: #f7f0f0;
			color: #508991;
			border: none;
			padding: 6px 12px;
			border-radius: 4px;
			margin-top: auto;
			margin-bottom: auto;
			cursor: pointer;
			transition:
				background 0.2s ease-in-out,
				color 0.2s ease-in-out;
		}

		.colab-list-item .remove:hover {
			background-color: #508991;
			color: #f7f0f0;
		}
		.dropdown {
			position: absolute;
			background: white;
			border: 1px solid #ccc;
			border-radius: 6px;
			width: fit-content;
			max-height: 250px;
			overflow-y: auto;
			z-index: 1000;
			box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
			animation: fadeIn 0.2s ease-in-out;
		}

		.dropdown-item {
			padding: 10px 14px;
			cursor: pointer;
			font-size: 14px;
			color: #333;
			transition: background 0.2s ease-in-out;
			border-bottom: 1px solid #eee;
		}

		.dropdown-item:last-child {
			border-bottom: none;
		}

		.dropdown-item:hover {
			background: #f8f8f8;
		}

		.dropdown-item:active {
			background: #e0e0e0;
		}

		.dropdown::-webkit-scrollbar {
			width: 6px;
		}

		.dropdown::-webkit-scrollbar-thumb {
			background: #ccc;
			border-radius: 3px;
		}

		.dropdown::-webkit-scrollbar-thumb:hover {
			background: #aaa;
		}
		@keyframes fadeIn {
			from {
				opacity: 0;
				transform: translateY(-5px);
			}
			to {
				opacity: 1;
				transform: translateY(0);
			}
		}
		.collaborators-container {
			display: flex;
			flex-direction: row;
			flex-wrap: wrap;
			gap: 10px;
			margin-top: 15px;
		}
	`;

	closeModal() {
		this.dispatchEvent(new CustomEvent('modal-close', { bubbles: true, composed: true }));
	}
	handleInput(e: Event) {
		const target = e.target as HTMLInputElement;
		const name = target.name as keyof this;
		(this[name] as string) = target.value;
	}

	async handleAddCollaborator() {
		try {
			const searchUrl = `http://localhost:8081/api/user/search?query=${this.collaboratorEmail}`;
			const results = await this.apiRequest(searchUrl, 'GET');
			if (!results.length) {
				alert('No users found with that name.');
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
	async handleSubmit(e: Event) {
		e.preventDefault();

		const updatedProject = {
			projectName: this.projectName,
			projectDescription: this.projectDescription
		};

		try {
			const token = localStorage.getItem('token');
			if (!token) throw new Error('No authentication token found.');

			this.apiRequest(
				`http://localhost:8081/api/project-collaborators/list`,
				'POST',
				this.collaborators.map(collab => {
					const collaborator: any = { ...collab };
					collaborator.joinedAt = new Date().toISOString();
					collaborator.projectID = this.projectID;
					collaborator.isActive = true;
					collaborator.roleID = 2;
					return collaborator;
				})
			);
			const res = await fetch(`http://localhost:8081/api/projects/${this.projectID}`, {
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				},
				body: JSON.stringify(updatedProject)
			});
			if (res.ok) {
				const updatedData = await res.json();
				this.dispatchEvent(new CustomEvent('project-updated', { detail: updatedData, bubbles: true, composed: true }));
				this.closeModal();
			} else {
				console.error('Failed to update project');
			}
		} catch (error) {
			console.error('Error updating project:', error);
		}
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
		if (!this.isOpen) return html``;

		return html`
			<div class="modal">
				<div class="modal-content">
					<div class="header">
						<h2>Update Project</h2>
						<button @click=${this.closeModal}>✕</button>
					</div>
					<form class="form" @submit=${this.handleSubmit}>
						<label>
							Project Name:
							<input type="text" .value=${this.projectName} @input=${(e: Event) => (this.projectName = (e.target as HTMLInputElement).value)} required />
						</label>
						<label>
							Project Description:
							<textarea .value=${this.projectDescription} @input=${(e: Event) => (this.projectDescription = (e.target as HTMLTextAreaElement).value)} required></textarea>
						</label>
						<h2>Add Collaborators</h2>
						<div class="collaborator-container">
							<input type="text" name="collaboratorEmail" .value=${this.collaboratorEmail} @input=${this.handleInput} placeholder="Enter the collaborator's email" />
							<button type="button" @click=${this.handleAddCollaborator}>+</button>
						</div>
						${this.renderCollaboratorList()}
						<button type="submit">Update Project</button>
					</form>
				</div>
			</div>
		`;
	}
}
