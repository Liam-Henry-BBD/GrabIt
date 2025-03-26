import { LitElement, html, css } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import {createTaskStyles} from './create-task.styles';

@customElement('create-task')
export class CreateTask extends LitElement {
	@state() name: string = '';
	@state() description: string = '';
	@state() deadline: string = '';
	@state() difficulty: 'easy' | 'medium' | 'hard' = 'easy';
	@state() isOpen: boolean = false;
	@state() projectId: string = '';

	static styles = createTaskStyles;

	handleInput(e: Event): void {
		const target = e.target as HTMLInputElement;
		const name = target.name as keyof this;

		if (name === 'difficulty' && (target.value === 'easy' || target.value === 'medium' || target.value === 'hard')) {
			this.difficulty = target.value as 'easy' | 'medium' | 'hard';
		} else {
			(this[name] as string) = target.value;
		}
	}

	handleDifficulty(level: 'easy' | 'medium' | 'hard'): void {
		this.difficulty = level;
	}

	handleSubmit(e: Event): void {
		e.preventDefault();

		const taskPoints = {
			easy: 5,
			medium: 10,
			hard: 15
		};

		const task = {
			project: this.projectId,
			taskName: this.name,
			taskDescription: this.description,
			taskDeadline: this.deadline ? new Date(this.deadline) : null,
			difficulty: this.difficulty,
			taskPointID: taskPoints[this.difficulty],
			taskStatusID: 1
		};

		this.dispatchEvent(new CustomEvent('task-submit', { detail: task, bubbles: true, composed: true }));

		this.resetForm();
		this.closeModal();
	}

	closeModal(): void {
		this.isOpen = false;
		this.dispatchEvent(new CustomEvent('modal-close', { bubbles: true, composed: true }));
	}

	resetForm(): void {
		this.name = '';
		this.description = '';
		this.deadline = '';
		this.difficulty = 'medium';
	}

	render() {
		if (!this.isOpen) return html``;

		return html`
			<div class="modal">
				<div class="modal-content">
					<div class="header">
						<h2>Create New Task</h2>
						<button @click=${this.closeModal}>✕</button>
					</div>

					<form @submit=${this.handleSubmit}>
						<label>
							Task Name:
							<input type="text" name="name" .value=${this.name} @input=${this.handleInput} required />
						</label>

						<label>
							Description:
							<textarea name="description" .value=${this.description} @input=${this.handleInput} required></textarea>
						</label>

						<label>
							Deadline (Optional):
							<input type="datetime-local" name="deadline" .value=${this.deadline} @input=${this.handleInput} />
						</label>

						<label>Difficulty:</label>
						<div class="difficulty-buttons">
							${(['easy', 'medium', 'hard'] as Array<'easy' | 'medium' | 'hard'>).map(
								level => html` <button type="button" class=${this.difficulty === level ? 'selected' : ''} @click=${() => this.handleDifficulty(level)}>${level}</button> `
							)}
						</div>
						<button type="submit">Create Task</button>
					</form>
				</div>
			</div>
		`;
	}
}
