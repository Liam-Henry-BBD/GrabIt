import { html, css, LitElement } from 'lit';
import { customElement, state } from 'lit/decorators.js';

@customElement('home-app')
export class DashboardComponent extends LitElement {
	connectedCallback() {
		super.connectedCallback();
		this.apiRequest(this.urls.getProjects, 'GET', (data: any) => this.setProjectOrganiser(data));
	}

	@state() private data: any;
	@state() private currentProject: any;
	@state() private projectTasks: any;
	@state() private projectOrganiser: any = { owned: [], collaborating: [] };
	@state() private tasks: any;
	@state() private urls = {
		getProjects: 'http://localhost:8081/api/projects',
		getProjectTasks: (projectID: any) => `http://localhost:8081/api/projects/${projectID}/tasks`,
		getProjectDetails: (projectID: any) => `http://localhost:8081/api/projects/${projectID}`
	};

	async apiRequest(url: string, method: string, callback: Function) {
		try {
			const token =
				'eyJhbGciOiJSUzI1NiIsImtpZCI6ImVlMTkzZDQ2NDdhYjRhMzU4NWFhOWIyYjNiNDg0YTg3YWE2OGJiNDIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1NTkzNDQyMjc5ODQtdTEzbzRyNWwzOGw4cHVkN3FvbXFpaWxxZDNibzczdWwuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1NTkzNDQyMjc5ODQtdTEzbzRyNWwzOGw4cHVkN3FvbXFpaWxxZDNibzczdWwuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDY1OTc3MDI2NTEyNzYzNjQ5MzYiLCJoZCI6InVtdXppLm9yZyIsImVtYWlsIjoibGlhbS5oZW5yeUB1bXV6aS5vcmciLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6IjdtWUV4REh0RFItQ09hblRFaDlHc3ciLCJub25jZSI6IjV5akwxUzRQUGdQTUl6NW84T0RaMnhKd2tXRXl6MFVfRlpkZXlwOTA0WmsiLCJuYW1lIjoiTGlhbSBIZW5yeSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NLMzB6djNzSXhQNlJ2dkJXMS1NZEVrSWlITVdENUp5Q2xyb0c2WGxGSk92M1k0TkVBPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IkxpYW0iLCJmYW1pbHlfbmFtZSI6IkhlbnJ5IiwiaWF0IjoxNzQyODIzOTYwLCJleHAiOjE3NDI4Mjc1NjB9.Kuro3jep7Zoms7aTfOClRH7xpVThYq_EnLiA36V0de3szU1E7pwhvp1ynMnh0kkE6TxoyTPzF8VEcHfd7SOLluuWvKhxvBP4uX1Ufs4sk5spUDRNH67y-oqQNvHIp4mBRK3h9389OeD37VsGIvaWkFBIE1RVyvCoBt5I4Gr6JXayatFl4qYGWo2SEGRuLK_dfdIlyq8l_Um7LC79wZjDEGBA7y56MuIOh_7N5cS0Wx3xGuHwjuh-QM1ICqXifnD0hTPksLKWLGv_Sac33GkKAzHw-09L1Q7WYlWWKfC4Tlfk_SM9gbV0HJEKQm6kReg_fnWs7ZC2yS9ADQnP_eBoZQ';
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

	setProjectOrganiser(response: any) {
		this.data = response;
		console.log(this.data);
		this.projectOrganiser.owned = this.data.filter((project: any) => project.collaboratorRole == 1);
		this.projectOrganiser.collaborating = this.data.filter((project: any) => project.collaboratorRole == 2);
	}

	setProjectTasks(response: any) {
		this.projectTasks = response;
		this.mapTasksToColumns(this.projectTasks);
	}

	setProjectDetails(response: any) {
		this.currentProject = response;
	}

	mapTasksToColumns(projectTasks: any) {
		console.log(projectTasks);
		const formatTask = (task: any) => {
			return {
				id: task.taskID,
				title: task.taskName,
				description: task.taskDescription,
				priority: task.taskPointID,
				reviewRequestedAt: task.reviewRequestedAt,
				createAt: task.createdAt,
				dueDate: task.dueDate
			};
		};
		this.tasks = {
			available: projectTasks.filter((task: any) => task.taskStatusID == 1).map(formatTask),
			grabbed: projectTasks.filter((task: any) => task.taskStatusID == 2).map(formatTask),
			inReview: projectTasks.filter((task: any) => task.taskStatusID == 3).map(formatTask),
			complete: projectTasks.filter((task: any) => task.taskStatusID == 4).map(formatTask)
		};
	}

	private formatDate(dateString: string) {
		if (!dateString) return 'Not set';
		const date = new Date(dateString);
		return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
	}

	render() {
		return html`
			<section class="dashboard">
				<header class="header">
					<img id="logo" src="src/home/activities/white_logo.png" alt="Logo" />
					<input type="search" placeholder="Search tasks..." />
					<img id="profile-icon" src="src/home/activities/icon2.png" alt="Logo" />
				</header>

				<nav class="sidebar">
					<input type="search" placeholder="Find a project..." class="sidebar-search" />
					<button class="new-project">+ New Project</button>

					${this.data &&
					Object.keys(this.projectOrganiser).map((group: any) => {
						return html`<h2>${group}</h2>
							<ul>
								${this.projectOrganiser[group].map(
									(project: any) => html`
										<li
											class="project-item"
											@click=${() => {
												this.apiRequest(this.urls.getProjectDetails(project.projectID), 'GET', (data: any) => this.setProjectDetails(data));
												return this.apiRequest(this.urls.getProjectTasks(project.projectID), 'GET', (data: any) => this.setProjectTasks(data));
											}}
										>
											<span class="project-icon">📁</span>
											${project.projectName}
										</li>
									`
								)}
							</ul>`;
					})}
				</nav>

				<main>
					<h1>${this.currentProject ? this.currentProject.projectName : 'Create or select a project'}</h1>
					<p>${this.currentProject ? this.currentProject.projectDescription : 'Your current open projects details will be avaliable here'}</p>

					<article>
						<button class="new-project-body">+ New Task</button>
						<button class="new-collaborator">+ Collaborators</button>
						<a href="leaderboard.">
							<button class="leaderboard-button">Leaderboard</button>
						</a>
					</article>

					<section class="collaborators">
						<span>Collaborators:</span>
						<section class="collaborator-icons">
							<img src="src/home/activities/icon.png" alt="Collaborator 1" />
							<img src="src/home/activities/icon.png" alt="Collaborator 2" />
							<img src="src/home/activities/icon.png" alt="Collaborator 3" />
							<img src="src/home/activities/icon.png" alt="Collaborator 4" />
						</section>
					</section>

					<section class="tab-content">
						${this.tasks
							? html`
									<section class="columns">
										${Object.keys(this.tasks).map((columnName: any) => {
											return html`<article class="column">
												<h2>${columnName} <span class="task-count">${this.tasks[columnName].length}</span></h2>
												${this.tasks[columnName].map(
													(project: any) => html`
														<article class="project-card">
															<h3>${project.title}</h3>
															<p class="task-description">${project.description}</p>
															<p class="due-date">Due date: ${this.formatDate(project.dueDate)}</p>
															<section class="points">${project.priority} pts</section>
														</article>
													`
												)}
											</article>`;
										})}
									</section>
								`
							: html`<p class="no-tasks">No Tasks have been created yet</p>`}
					</section>
				</main>
			</section>
		`;
	}

	static styles = css`
		.dashboard {
			font-family: 'Arial', sans-serif;
			background-color: #1e1e1e;
			color: #fff;
			display: flex;
			min-height: 100vh;
			width: 100vw;
			overflow: hidden;
		}

		.header {
			display: flex;
			justify-content: space-evenly;
			align-items: center;
			background-color: #1e1e1e;
			box-shadow: 0 4px 10px rgba(0, 0, 0, 1);
			width: 100vw;
			max-width: 100%;
			position: fixed;
			top: 0;
			right: 0;
			height: 5rem;
			z-index: 1000;
			overflow: hidden;
		}

		#logo {
			height: 3rem;
			margin-left: 3rem;
			margin-right: auto;
		}
		#profile-icon {
			margin-right: 3rem;
			margin-left: auto;
			height: 3rem;
		}
		.header input {
			padding: 0.5rem;
			border-radius: 1rem;
			background-color: #555;
			color: #fff;
			border: none;
			width: 30rem;
			right: 0;
			margin-left: 1rem;
		}

		.header-icon img {
			width: 10%;
			height: auto;
			right: 0;
			margin-left: 30rem;
		}

		.sidebar {
			width: 17rem;
			background-color: #1e1e1e;
			box-shadow: 0 4px 10px rgba(0, 0, 0, 1);
			padding: 10px;
			position: fixed;
			top: 5rem; /* Adjust to be at the top */
			left: 0;
			height: 100vh; /* Adjust height to cover the full height */
			overflow-y: hidden; /* Remove vertical scrollbar */
		}

		.sidebar-search {
			width: 90%;
			padding: 0.5rem;
			border-radius: 0.5rem;
			background-color: #555;
			color: #fff;
			border: none;
			margin-bottom: 20px;
			margin-left: 0.5rem;
		}

		.new-project {
			background-color: #f9a03f;
			color: white;
			padding: 0.5rem;
			border-radius: 0.5rem;
			cursor: pointer;
			border: none;
			width: 90%;
			margin-bottom: 20px;
			margin-left: 0.5rem;
		}

		.new-project-body {
			background-color: #f9a03f;
			color: white;
			padding: 0.5rem;
			border-radius: 0.5rem;
			cursor: pointer;
			border: none;
			width: 15%;
			margin-bottom: 20px;
		}

		.new-collaborator {
			background-color: #f5f4f3;
			color: black;
			padding: 0.5rem;
			border-radius: 0.5rem;
			cursor: pointer;
			border: none;
			width: 15%;
			margin-bottom: 20px;
		}

		.new-project:hover {
			background-color: #d88a2d;
		}

		.sidebar h2 {
			font-size: 1.2em;
			color: #f9a03f;
			margin-bottom: 10px;
		}

		.sidebar ul {
			list-style-type: none;
			padding: 0;
			margin: 0 0 20px 0;
		}

		.project-item,
		.team-item {
			display: flex;
			align-items: center;
			padding: 8px 0;
			color: #ccc;
			cursor: pointer;
		}

		.project-item:hover,
		.team-item:hover {
			color: #fff;
		}

		.project-icon,
		.team-icon {
			margin-right: 10px;
		}

		.team-label {
			background-color: #333;
			color: #fff;
			padding: 2px 5px;
			border-radius: 5px;
			font-size: 0.8em;
			margin-left: auto;
		}

		.team-count {
			background-color: #333;
			color: #fff;
			padding: 2px 5px;
			border-radius: 5px;
			font-size: 0.8em;
			margin-left: auto;
		}

		.user-info {
			margin-top: 20px;
			border-top: 1px solid #ccc;
			padding-top: 10px;
			color: #ccc;
		}

		main {
			margin-top: 5rem; /* Adjust to be below the header */
			padding-left: 270px; /* Adjust to be beside the sidebar */
			flex: 1;
			overflow-y: auto;
		}

		h1 {
			text-align: left;
			margin-top: 20px;
			color: #f9a03f;
		}

		.collaborators {
			display: flex;
			align-items: center;
			margin-top: 10px;
		}

		.collaborators span {
			margin-right: 10px;
		}

		.collaborator-icons img {
			height: 30px;
			margin-right: 5px;
		}

		.columns {
			display: flex;
			gap: 20px;
			margin-top: 30px;
		}
		.column {
			flex: 1;
		}
		.column h2 {
			text-align: middle;
			display: flex;
			justify-content: space-between;
			align-items: center;
			color: #fff;
			background-color: rgb(80 137 145 / 0.1);
			padding: 0.5rem;
		}

		.task-count {
			background-color: #555;
			color: #fff;
			padding: 5px 10px;
			border-radius: 50%;
			font-size: 0.8em;
		}

		.project-card {
			background-color: rgba(255, 255, 255, 0.1); /* Semi-transparent background */
			padding: 20px;
			border-radius: 8px;
			box-shadow: 0 2px 8px rgba(0, 0, 0, 0.5); /* Softer shadow */
			margin-bottom: 20px;
			position: relative;
			border: 1px solid rgba(255, 255, 255, 0.2); /* Border to match the design */
			max-width: 90%;
		}

		.project-card h3 {
			font-size: 18px;
			margin: 0;
			color: #fff;
		}

		.project-card p {
			margin: 10px 0;
			color: #ccc;
		}
		.task-description {
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
		}
		.due-date {
			font-size: 14px;
			color: #ccc;
			margin-top: 10px;
			right: 0;
		}

		.points {
			position: absolute;
			top: 10px;
			right: 10px;
			background-color: #f9a03f;
			color: #0d0d0d;
			padding: 5px 10px;
			border-radius: 10%;
			font-size: 0.8em;
		}

		p {
			padding: 1rem;
			max-width: 40rem;
			font-family: Georgia, Times, 'Times New Roman', serif;
			font-size: 1.2rem;
		}
		.no-tasks {
			place-self: center;
		}

		.leaderboard-button {
			background-color: #f9a03f;
			color: white;
			padding: 0.5rem;
			border-radius: 0.5rem;
			cursor: pointer;
			border: none;
			width: 10%;
			margin-left: 40rem;
		}
	`;
}
