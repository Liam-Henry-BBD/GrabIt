import { html, css, LitElement } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import '../components/header';
import '../auth/activities/auth-router';

@customElement('home-app')
export class DashboardComponent extends LitElement {
	connectedCallback() {
		super.connectedCallback();
		this.apiRequest(this.urls.getProjects, 'GET', (data: any) => this.createProjectGroupByRoleComponent(data));
	}

	@state() private data: any;
	@state() private currentProject: any;
	@state() private projectOrganiser: any = { owned: [], collaborating: [] };
	@state() private tasks: any = { available: [], grabbed: [], inReview: [], complete: [] };
	@state() private currentProjectRole: any = null;
	@state() private urls = {
		getProjects: 'http://localhost:8081/api/projects',
		getProjectTasks: (projectID: any) => `http://localhost:8081/api/projects/${projectID}/tasks`,
		getProjectDetails: (projectID: any) => `http://localhost:8081/api/projects/${projectID}`
	};

	async apiRequest(url: string, method: string, callback: Function) {
		try {
			const token = localStorage.getItem('token');
			const response = await fetch(url, {
				method,
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}`
				}
			});
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			const data = await response.json();
			if (callback) callback(data);
		} catch (error) {
			console.error('Error fetching data: ', error);
		}
	}

	private createProjectGroupByRoleComponent(response: any) {
		const createProjectComponent = (project: any) => {
			return html`
				<li
					class="project-item"
					@click=${() => {
						this.apiRequest(this.urls.getProjectDetails(project.projectID), 'GET', (data: any) => (this.currentProject = data));
						this.currentProjectRole = project.collaboratorRole;
						return this.apiRequest(this.urls.getProjectTasks(project.projectID), 'GET', (data: any) => this.createTaskComponents(data));
					}}
				>
					<span class="project-icon">📁</span>
					${project.projectName}
				</li>
			`;
		};
		this.data = response;
		this.projectOrganiser = {
			owned: this.data.filter((project: any) => project.collaboratorRole == 1).map(createProjectComponent),
			collaborating: this.data.filter((project: any) => project.collaboratorRole == 2).map(createProjectComponent)
		};
	}
	private createTaskComponents(response: any) {
		const formatTask = (task: any, status: String) => {
			return html` <article class="project-card">
				<h3>${task.taskName}</h3>
				<p class="task-description">${task.description}</p>
				<p class="due-date">Due date: ${this.formatDate(task.dueDate)}</p>
				<section class="points">${task.taskPointID} pts</section>
				${status == 'available' && this.currentProjectRole == 2
					? html`<button class="card-btn">Grab Task</button>`
					: status == 'grabbed' && this.currentProjectRole == 2
						? html`<button class="card-btn">request review</button>`
						: status == 'inReview' && this.currentProjectRole == 2
							? html`<button class="card-btn">cancel reveiw</button>`
							: html``}
			</article>`;
		};
		['available', 'grabbed', 'inReview', 'complete'].map(
			(columnName, idx) => (this.tasks[columnName] = response.filter((task: any) => task.taskStatusID == idx + 1).map((task: any) => formatTask(task, columnName)))
		);
	}

	private createBoardComponent() {
		return html` <section class="columns">
			${Object.keys(this.tasks).map((columnName: any) => {
				return html`<article class="column">
					<h2>${columnName} <span class="task-count">${this.tasks[columnName].length}</span></h2>
					<section class="card-container">${this.tasks[columnName]}</section>
				</article>`;
			})}
		</section>`;
	}

	private formatDate(dateString: string) {
		if (!dateString) return 'Not set';
		const date = new Date(dateString);
		return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
	}

	render() {
		return html`
			<auth-router>
				<header-app> </header-app>
				<section class="dashboard">
					<nav class="sidebar">
						<!-- Search and New Project Button Section -->
						<section class="sidebar-header">
							<input type="search" placeholder="Find a project..." class="sidebar-search" />
							<button class="new-project">+ New Project</button>
						</section>
						<hr class="separator" />

						<!-- Project Groups -->
						${this.data &&
						Object.keys(this.projectOrganiser).map((group: any) => {
							return html`
								<h2>${group}</h2>
								<ul>
									${this.projectOrganiser[group]}
								</ul>
							`;
						})}
					</nav>

					<main>
						<h1>${this.currentProject ? this.currentProject.projectName : 'Create or select a project'}</h1>
						<p>${this.currentProject ? this.currentProject.projectDescription : 'Your current open projects details will be available here'}</p>

						<article>
							<section class="article-buttons">
								<div>
									<button class="new-project-body">+ New Task</button>
									${this.currentProjectRole == 1 ? html`<button class="new-collaborator">Update Project</button>` : html``}
								</div>
								<button class="leaderboard-button">Leaderboard</button>
							</section>
						</article>

						<section class="tab-content">
							${this.tasks.available || this.tasks.grabbed || this.tasks.inReview || this.tasks.complete
								? this.createBoardComponent()
								: html`<p class="no-tasks">No Tasks have been created yet</p>`}
						</section>
					</main>
				</section>
			</auth-router>
		`;
	}

	static styles = css`
		:host {
			font-family: 'Arial', sans-serif;
			background-color: #1e1e1e;
			color: #fff;
			display: flex;
			height: 100vh;
			width: 100%;
			overflow: hidden;
		}

		.dashboard {
			margin-top: 5rem; /* Adjust for header spacing */
			height: calc(100vh - 5rem);
			width: 100vw;
			overflow: hidden;
			display: flex;
		}

		/* Sidebar Styling */
		.sidebar {
			display: flex;
			flex-direction: column;
			background-color: #2c2c2c;
			width: 17rem;
			padding: 1rem;
			height: 100vh;
			border-right: 1px solid rgba(255, 255, 255, 0.1);
			transition: transform 0.3s ease-in-out;
			overflow-y: auto;
			box-sizing: border-box;
		}

		/* Sidebar Headings */
		.sidebar h2 {
			place-self: center;
			text-align: center;
			font-size: 1.3rem;
			font-weight: bold;
			margin-bottom: 1rem;
			color: #ffffff;
		}

		/* Sidebar Header */
		.sidebar-header {
			display: flex;
			flex-direction: column;
			gap: 1rem;
		}

		/* Search Input */
		.sidebar-search {
			width: 100%;
			padding: 0.75rem;
			font-size: 1rem;
			border: none;
			border-radius: 5px;
			background-color: transparent;
			border: 1px solid rgba(80, 137, 145, 0.2);
			color: #fff;
			margin-bottom: 0.75rem;
		}
		/* New Project Button */
		.new-project {
			width: 100%;
			padding: 0.75rem;
			font-size: 1rem;
			font-weight: bold;
			border: none;
			border-radius: 5px;
			background-color: #ff9800; /* Corrected color */
			color: #ffffff;
			cursor: pointer;
			transition: background-color 0.2s ease-in-out;
			margin-bottom: 1rem; /* Added margin for separation */
		}

		.new-project:hover {
			background-color: #e68900;
		}

		/* Separator */
		.separator {
			width: 100%;
			height: 1px;
			background: rgba(255, 255, 255, 0.2);
			margin: 1rem 0;
			border: none;
		}

		/* Project List */
		ul {
			list-style-type: none;
			padding: 0;
			margin: 0;
		}

		/* Project Items */
		.project-item {
			display: flex;
			align-items: center;
			gap: 0.75rem;
			padding: 0.75rem;
			font-size: 1.1rem;
			font-weight: 500;
			border-radius: 5px;
			cursor: pointer;
			transition: background-color 0.2s;
			color: #ffffff;
			margin-bottom: 0.5rem; /* Spacing between list items */
		}

		.project-item:hover {
			background-color: rgba(255, 255, 255, 0.1);
		}

		/* Project Icons */
		.project-item .project-icon {
			font-size: 1.2rem;
		}
		/* Sidebar Collapsible on Smaller Screens */
		@media (max-width: 1024px) {
			.sidebar {
				position: absolute;
				transform: translateX(-100%);
				transition: transform 0.3s ease-in-out;
			}

			.sidebar.open {
				transform: translateX(0);
			}
		}

		/* --- */
		/* Main Section Styling */
		main {
			flex: 1;
			padding: 2rem 1rem; /* Added padding to the content */
			background-color: #2e2e2e; /* Lighter background for the content area */
			color: #ffffff; /* Default text color */
			overflow-y: hidden;
			box-sizing: border-box;
		}

		/* Main Section Header */
		h1 {
			font-size: 2rem;
			color: #ff9800; /* Primary color for the header */
			margin-bottom: 1rem;
		}

		/* Paragraph Styling */
		p {
			font-size: 1.2rem;
			color: #b3b3b3; /* Muted text color */
			margin-bottom: 2rem;
		}

		/* Task Board Columns */
		.columns {
			display: flex;
			gap: 1rem;
			margin-bottom: 2rem;
		}

		.column {
			flex: 1;
			/* Light background for each task column */
			padding: 1rem;
			border-radius: 5px;
			height: 28rem; /* Set a consistent height (you can adjust the value) */
			overflow: hidden; /* Enable scrolling if content exceeds the column height */
		}

		/* Optional: Add a max-height to prevent the column from growing too tall */
		.column {
			max-height: 80vh; /* Optional: set a max-height based on viewport height */
		}

		.column h2 {
			font-size: 1.3rem;
			font-weight: bold;
			color: #ffffff; /* Text color for the column headers */
			place-self: center;
			background-color: #2c2c2c; /* Darker background for the column headers */
			width: 100%;
			height: 2rem;
			padding: 0.3rem;
			text-align: center;
			border: 1px solid rgba(80, 137, 145, 0.2);
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 0 1rem;
		}
		.column h2 > span {
			background-color: #508991;
			width: 1.5rem;
			height: 1.5rem;
			place-content: center;
			border-radius: 50%; /* Primary color for the task count */
		}
		.column .task-count {
			font-size: 1.1rem;
			color: #b3b3b3; /* Light color for the task count */
		}
		.column > section {
			height: 100%;
			width: 100%;
			overflow-y: auto;
			/* background-color: #3a3a3a; */
			-ms-overflow-style: none; /* For Internet Explorer 10+ */
			scrollbar-width: none; /* For Firefox */
		}

		.column .cards-container::-webkit-scrollbar {
			display: none; /* Hide scrollbar for Webkit browsers (Chrome, Safari, Edge) */
		}
		/* No Tasks Message */
		.no-tasks {
			color: #cccccc; /* Very light color for the "No tasks" message */
			font-size: 1.1rem;
			text-align: center;
		}

		/* Task Card Styles */
		.project-card {
			background-color: #1e1e1e; /* Darker background for task cards */
			color: #ffffff; /* Default text color */
			padding: 1rem;
			border-radius: 5px;
			height:12rem;
			margin-bottom: 1rem;
			border: 1px solid rgba(80, 137, 145, 0.2);
			box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); /* Subtle shadow effect */
		}

		.project-card h3 {
			font-size: 1.4rem;
			color: #ff9800; /* Primary color for the task title */
			margin-bottom: 0.5rem;
		}

		.task-description,
		.due-date,
		.points {
			font-size: 1rem;
			color: #b3b3b3; /* Muted color for the description and due date */
		}

		.points {
			font-weight: bold;
			color: #ff9800; /* Primary color for task points */
		}

		/* New Project, Collaborators, and Leaderboard Button Placement */
		.article-buttons {
			display: flex;
			gap: 1rem;
			margin-bottom: 2rem; /* Space below the buttons */
			justify-content: space-between; /* Distribute buttons between left and right */
			align-items: center; /* Vertically center the buttons */
		}

		/* Button Styles for Main Section */
		.new-project-body,
		.new-collaborator,
		.leaderboard-button {
			padding: 1rem 2rem;
			font-size: 1rem;
			font-weight: bold;
			border: none;
			border-radius: 5px;
			cursor: pointer;
			transition: background-color 0.2s;
		}

		/* New Task Button */
		.new-project-body {
			background-color: #ff9800; /* Primary button color */
			color: #ffffff;
		}

		.new-project-body:hover {
			background-color: #e68900; /* Hover effect */
		}

		/* Collaborator Button */
		.new-collaborator {
			background-color: #2c2c2c; /* Secondary button color */
			color: #ffffff;
		}

		.new-collaborator:hover {
			background-color: #444444; /* Hover effect */
		}

		/* Leaderboard Button */
		.leaderboard-button {
			background-color: #508991; /* Accent color for the leaderboard button */
			color: #ffffff;
		}

		.leaderboard-button:hover {
			background-color: #388e3c; /* Hover effect */
		}
		@media (max-width: 1024px) {
			main {
				overflow-y: auto;
			}
			.columns {
				flex-direction: column;
				gap: 1rem;
			}
			.column > section {
				height: 100%;
				width: 100%;
				overflow-y: hidden;
				overflow-x: auto;
				display: flex;
				gap: 1rem;
				flex-direction: row;
			}
			.project-card {
				background-color: #1e1e1e; /* Darker background for task cards */
				color: #ffffff; /* Default text color */
				padding: 1rem;
				border-radius: 5px;
				margin-bottom: 1rem;
				min-width: 12rem;
				box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); /* Subtle shadow effect */
			}
		}
	`;
}