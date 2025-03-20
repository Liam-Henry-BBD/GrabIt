import { html, css, LitElement } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import { LeaderboardSidebar } from './leaderboard';

@customElement('home-app')
export class DashboardComponent extends LitElement {
	@state() private data = {
		available: [
			{
				id: 'project-1',
				title: 'E-commerce Platform',
				description: 'Build a modern e-commerce platform with React and Node.js',
				progress: 0,
				priority: 'High',
				assignees: ['alex', 'jamie'],
				dueDate: '2023-12-15'
			},
			{
				id: 'project-2',
				title: 'Mobile App Redesign',
				description: 'Redesign the mobile app interface for better user experience',
				progress: 0,
				priority: 'Medium',
				assignees: ['taylor'],
				dueDate: '2023-12-20'
			}
		],
		grabbed: [
			{
				id: 'project-3',
				title: 'API Integration',
				description: 'Integrate payment gateway APIs into the platform',
				progress: 25,
				priority: 'High',
				assignees: ['alex', 'casey'],
				dueDate: '2023-12-10'
			}
		],
		inReview: [
			{
				id: 'project-4',
				title: 'User Authentication',
				description: 'Implement secure user authentication and authorization',
				progress: 60,
				priority: 'High',
				assignees: ['jamie'],
				dueDate: '2023-12-05'
			}
		],
		complete: [
			{
				id: 'project-5',
				title: 'Landing Page',
				description: 'Design and develop the company landing page',
				progress: 100,
				priority: 'Medium',
				assignees: ['taylor', 'casey'],
				dueDate: '2023-11-30'
			}
		]
	};

	private formatDate(dateString: string) {
		const date = new Date(dateString);
		return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
	}

	private getPriorityColor(priority: string) {
		switch (priority) {
			case 'High':
				return '#d32f2f'; // Red
			case 'Medium':
				return '#f57c00'; // Orange
			case 'Low':
				return '#388e3c'; // Green
			default:
				return '#616161'; // Gray
		}
	}

	render() {
		return html`
			<div class="dashboard">
				<header class="header">
					<div class="logo">
						<img src="/placeholder.svg" alt="Logo" />
						<span>Grabit</span>
					</div>
					<input type="search" placeholder="Search projects..." />
					<div class="header-actions">
						<button class="sign-in">Sign In</button>
						<button class="new-project">New Project</button>
						<button class="filter-project">Filter Projects</button>
					</div>
				</header>

				<main>
					<h1>Projects Dashboard</h1>

					<!-- Leaderboard Section -->
					<leaderboard-sidebar></leaderboard-sidebar>

					<div class="tabs">
						<button @click="${() => this._onTabClick('kanban')}">Kanban</button>
						<button @click="${() => this._onTabClick('list')}">List</button>
						<button @click="${() => this._onTabClick('calendar')}">Calendar</button>
					</div>

					<div class="tab-content">
						${this.data
							? html`
									<div class="columns">
										<div class="column">
											<h2>Available</h2>
											${this.data.available.map(
												project => html`
													<div class="project-card" style="border-left: 5px solid ${this.getPriorityColor(project.priority)};">
														<h3>${project.title}</h3>
														<p>${project.description}</p>
														<div class="progress-bar" style="width: ${project.progress}%;">${project.progress}%</div>
														<p class="due-date">${this.formatDate(project.dueDate)}</p>
													</div>
												`
											)}
										</div>

										<div class="column">
											<h2>Grabbed</h2>
											${this.data.grabbed.map(
												project => html`
													<div class="project-card" style="border-left: 5px solid ${this.getPriorityColor(project.priority)};">
														<h3>${project.title}</h3>
														<p>${project.description}</p>
														<div class="progress-bar" style="width: ${project.progress}%;">${project.progress}%</div>
														<p class="due-date">${this.formatDate(project.dueDate)}</p>
													</div>
												`
											)}
										</div>

										<div class="column">
											<h2>In Review</h2>
											${this.data.inReview.map(
												project => html`
													<div class="project-card" style="border-left: 5px solid ${this.getPriorityColor(project.priority)};">
														<h3>${project.title}</h3>
														<p>${project.description}</p>
														<div class="progress-bar" style="width: ${project.progress}%;">${project.progress}%</div>
														<p class="due-date">${this.formatDate(project.dueDate)}</p>
													</div>
												`
											)}
										</div>

										<div class="column">
											<h2>Complete</h2>
											${this.data.complete.map(
												project => html`
													<div class="project-card" style="border-left: 5px solid ${this.getPriorityColor(project.priority)};">
														<h3>${project.title}</h3>
														<p>${project.description}</p>
														<div class="progress-bar" style="width: ${project.progress}%;">${project.progress}%</div>
														<p class="due-date">${this.formatDate(project.dueDate)}</p>
													</div>
												`
											)}
										</div>
									</div>
								`
							: ''}
					</div>
				</main>
			</div>
		`;
	}

	private _onTabClick(tabName: string) {
		console.log(`Selected Tab: ${tabName}`);
	}

	static styles = css`
		.dashboard {
			font-family: 'Arial', sans-serif;
			background-color: #2e2e2e;
			color: #fff;
			padding: 20px;
		}

		.header {
			display: flex;
			justify-content: space-between;
			align-items: center;
			background-color: #383838;
			padding: 15px;
			border-radius: 8px;
			box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
		}

		.logo {
			display: flex;
			align-items: center;
		}

		.logo img {
			height: 40px;
			margin-right: 10px;
		}

		.logo span {
			font-size: 20px;
			font-weight: bold;
		}

		.header input {
			padding: 10px;
			border-radius: 5px;
			background-color: #555;
			color: #fff;
			border: none;
			width: 250px;
		}

		.header-actions button {
			background-color: #508991;
			color: white;
			padding: 12px 20px;
			border-radius: 8px;
			cursor: pointer;
			margin-left: 10px;
			border: none;
			transition: background-color 0.3s ease;
		}

		.header-actions button:hover {
			background-color: #406c73;
		}

		h1 {
			text-align: center;
			margin-top: 20px;
			color: #f9a03f;
		}

		.leaderboard {
			background-color: #333;
			padding: 20px;
			border-radius: 8px;
			box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
			margin-bottom: 20px;
		}

		.leaderboard h2 {
			text-align: center;
			color: #f9a03f;
		}

		.leaderboard-list {
			margin-top: 10px;
			font-size: 16px;
			color: #ccc;
		}

		.leaderboard-item {
			margin: 5px 0;
		}

		.tabs {
			display: flex;
			justify-content: center;
			gap: 30px;
			margin-bottom: 20px;
		}

		.tabs button {
			background-color: #508991;
			color: white;
			padding: 12px 20px;
			border-radius: 8px;
			cursor: pointer;
			transition: background-color 0.3s ease;
			border: none;
		}

		.tabs button:hover {
			background-color: #406c73;
		}

		.columns {
			display: flex;
			gap: 20px;
			margin-top: 30px;
		}

		.column {
			flex: 1;
			background-color: #333;
			padding: 20px;
			border-radius: 8px;
			box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
		}

		.column h2 {
			text-align: center;
			color: #f9a03f;
		}

		.project-card {
			background-color: #444;
			padding: 20px;
			margin-bottom: 20px;
			border-radius: 8px;
			box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
		}

		.project-card h3 {
			font-size: 18px;
			margin: 0;
		}

		.project-card p {
			margin: 10px 0;
			color: #ccc;
		}

		.progress-bar {
			height: 8px;
			background-color: #333;
			border-radius: 4px;
			overflow: hidden;
		}

		.progress-bar {
			background-color: #4caf50;
			height: 8px;
			border-radius: 4px;
		}

		.due-date {
			font-size: 14px;
			color: #ccc;
			margin-top: 10px;
		}
	`;
}
