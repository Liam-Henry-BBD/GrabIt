import { LitElement, html, css } from 'lit';
import { customElement, state } from 'lit/decorators.js';

@customElement('tutorials-page')
export class Tutorials extends LitElement {
	@state() activeStep: string | null = null;

	static styles = css`
		:host {
			display: block;
			background-color: #242423;
			color: #f7f0f0;
			font-family: 'Arial', sans-serif;
			padding: 20px;
			text-align: center;
		}

		h1 {
			color: #f9a03f;
		}

		.flow-container {
			display: flex;
			justify-content: center;
			align-items: center;
			margin-top: 30px;
		}

		svg {
			max-width: 100%;
			height: auto;
		}

		.step {
			cursor: pointer;
			fill: #508991;
			transition: fill 0.3s ease;
		}

		.step:hover {
			fill: #f9a03f;
		}

		.details {
			margin-top: 20px;
			padding: 15px;
			background: #333;
			border-radius: 8px;
			display: none;
			opacity: 0;
			transition: opacity 0.3s ease;
		}

		.details.active {
			display: block;
			opacity: 1;
		}

		.close-btn {
			background: #f9a03f;
			color: #242423;
			border: none;
			padding: 8px 12px;
			border-radius: 5px;
			cursor: pointer;
		}

		.close-btn:hover {
			background: #f9a03fcc;
		}
	`;

	toggleDetails(step: string) {
		this.activeStep = this.activeStep === step ? null : step;
	}

	render() {
		return html`
			<h1>How Grabit Works</h1>
			<p>Click on each step to learn more!</p>

			<div class="flow-container">
				<svg width="1200" height="350" viewBox="0 0 500 350">
					<circle class="step" cx="50" cy="50" r="30" @click=${() => this.toggleDetails('start')} />
					<text x="30" y="55" fill="#F7F0F0" font-size="14">Start</text>

					<circle class="step" cx="150" cy="100" r="30" @click=${() => this.toggleDetails('create-project')} />
					<text x="120" y="105" fill="#F7F0F0" font-size="14">Create Project</text>

					<circle class="step" cx="250" cy="150" r="30" @click=${() => this.toggleDetails('add-task')} />
					<text x="230" y="155" fill="#F7F0F0" font-size="14">Add Task</text>

					<circle class="step" cx="350" cy="200" r="30" @click=${() => this.toggleDetails('assign-collaborators')} />
					<text x="310" y="205" fill="#F7F0F0" font-size="14">Assign Collaborators</text>

					<circle class="step" cx="450" cy="250" r="30" @click=${() => this.toggleDetails('complete')} />
					<text x="420" y="255" fill="#F7F0F0" font-size="14">Complete!</text>

					<line x1="80" y1="60" x2="120" y2="90" stroke="#F9A03F" stroke-width="3" />
					<line x1="180" y1="110" x2="220" y2="140" stroke="#F9A03F" stroke-width="3" />
					<line x1="280" y1="160" x2="320" y2="190" stroke="#F9A03F" stroke-width="3" />
					<line x1="380" y1="210" x2="420" y2="240" stroke="#F9A03F" stroke-width="3" />
				</svg>
			</div>

			<div class="details ${this.activeStep === 'start' ? 'active' : ''}">
				<p><strong>Start:</strong> Welcome to Grabit! Your productivity hub.</p>
				<button class="close-btn" @click=${() => this.toggleDetails('start')}>Close</button>
			</div>

			<div class="details ${this.activeStep === 'create-project' ? 'active' : ''}">
				<p><strong>Create Project:</strong> Start by creating a project with a name and description.</p>
				<button class="close-btn" @click=${() => this.toggleDetails('create-project')}>Close</button>
			</div>

			<div class="details ${this.activeStep === 'add-task' ? 'active' : ''}">
				<p><strong>Add Task:</strong> Add tasks with details and difficulty level.</p>
				<button class="close-btn" @click=${() => this.toggleDetails('add-task')}>Close</button>
			</div>

			<div class="details ${this.activeStep === 'assign-collaborators' ? 'active' : ''}">
				<p><strong>Assign Collaborators:</strong> Invite teammates to work on tasks.</p>
				<button class="close-btn" @click=${() => this.toggleDetails('assign-collaborators')}>Close</button>
			</div>

			<div class="details ${this.activeStep === 'complete' ? 'active' : ''}">
				<p><strong>Complete:</strong> Track progress and mark tasks as done!</p>
				<button class="close-btn" @click=${() => this.toggleDetails('complete')}>Close</button>
			</div>
		`;
	}
}
