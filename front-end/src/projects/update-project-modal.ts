import { LitElement, html, css } from 'lit';
import { customElement, state } from 'lit/decorators.js';

@customElement('update-project')
export class UpdateProject extends LitElement {
	@state() projectName: string = '';
	@state() projectDescription: string = '';
	@state() isOpen: boolean = false;
	@state() projectID: string = '';

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
	`;

	closeModal() {
		this.dispatchEvent(new CustomEvent('modal-close', { bubbles: true, composed: true }));
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
						<button type="submit">Update Project</button>
					</form>
				</div>
			</div>
		`;
	}
}
