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
			<section class="dashboard">
				<header class="header">
					<figure class="logo">
						<img src="src/home/activities/white_logo.png" alt="Logo" />
					</figure>
					<input type="search" placeholder="Search tasks..." />
					<figure class="header-icon">
						<img src="src/home/activities/icon2.png" alt="Logo" />
					</figure>
				</header>
	
				<nav class="sidebar">
					<input type="search" placeholder="Find a project..." class="sidebar-search" />
					<button class="new-project">+ New Project</button>
					<h2>Your Projects</h2>
					<ul>
						<li class="project-item">
							<span class="project-icon">📁</span>
							E-commerce Platform
						</li>
						<li class="project-item">
							<span class="project-icon">📁</span>
							Mobile App Redesign
						</li>
					</ul>
					<h2>Starred</h2>
					<ul>
						<li class="project-item">
							<span class="project-icon">⭐</span>
							Your Projects
						</li>
					</ul>
					<h2>Collaborating</h2>
					<ul>
						<li class="project-item">
							<span class="project-icon">👥</span>
							API Documentation <span class="team-label">techteam</span>
						</li>
						<li class="project-item">
							<span class="project-icon">👥</span>
							Design System <span class="team-label">designteam</span>
						</li>
						<li class="project-item">
							<span class="project-icon">👥</span>
							Product Roadmap <span class="team-label">techteam</span>
						</li>
						<li class="project-item">
							<span class="project-icon">👥</span>
							Analytics Platform <span class="team-label">datateam</span>
						</li>
					</ul>
					<h2>Teams</h2>
					<ul>
						<li class="team-item">
							<span class="team-icon">DE</span>
							Design Team <span class="team-count">12</span>
						</li>
						<li class="team-item">
							<span class="team-icon">DE</span>
							Development <span class="team-count">8</span>
						</li>
					</ul>
					<aside class="user-info">
						<p>John Doe</p>
						<p>Pro Plan</p>
					</aside>
				</nav>
	
				<main>
					<h1>E-commerce Platform</h1>
					<p>Build a modern e-commerce platform with React and Node.js. This project aims to create a fully functional online store with product listings, shopping cart, checkout process, and admin dashboard.</p>
					
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
						${this.data
							? html`
									<section class="columns">
									<article class="column">
										<h2>Available <span class="task-count">${this.data.available.length}</span></h2>
										${this.data.available.map(
											project => html`
												<article class="project-card">
													<h3>${project.title}</h3>
													<p>${project.description}</p>
													<p class="due-date">Due ${this.formatDate(project.dueDate)}</p>
													<section class="points">${project.priority === 'High' ? 10 : project.priority === 'Medium' ? 5 : 2} pts</section>
												</article>
											`
										)}
									</article>
	
									<article class="column">
										<h2>Grabbed <span class="task-count">${this.data.grabbed.length}</span></h2>
										${this.data.grabbed.map(
											project => html`
												<article class="project-card">
													<h3>${project.title}</h3>
													<p>${project.description}</p>
													<p class="due-date">Due ${this.formatDate(project.dueDate)}</p>
													<section class="points">${project.priority === 'High' ? 15 : project.priority === 'Medium' ? 10 : 5} pts</section>
												</article>
											`
										)}
									</article>
	
									<article class="column">
										<h2>In Review <span class="task-count">${this.data.inReview.length}</span></h2>
										${this.data.inReview.map(
											project => html`
												<article class="project-card">
													<h3>${project.title}</h3>
													<p>${project.description}</p>
													<p class="due-date">Due ${this.formatDate(project.dueDate)}</p>
													<section class="points">${project.priority === 'High' ? 10 : project.priority === 'Medium' ? 5 : 2} pts</section>
												</article>
											`
										)}
									</article>
	
									<article class="column">
										<h2>Complete <span class="task-count">${this.data.complete.length}</span></h2>
										${this.data.complete.map(
											project => html`
												<article class="project-card">
													<h3>${project.title}</h3>
													<p>${project.description}</p>
													<p class="due-date">Due ${this.formatDate(project.dueDate)}</p>
													<section class="points">${project.priority === 'High' ? 5 : project.priority === 'Medium' ? 3 : 1} pts</section>
												</article>
											`
										)}
									</article>
									</section>
								`
							: ''}
					</section>
				</main>
			</section>
		`;
	}

	private _onTabClick(tabName: string) {
		console.log(`Selected Tab: ${tabName}`);
	}

	static styles = css`
	.dashboard {
		font-family: 'Arial', sans-serif;
		background-color: #1e1e1e;
		color: #fff;
		display: flex;
		min-height: 100vh; 
		padding: 2.5rem;
		overflow: hidden; 
	}

	.header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		background-color: #1e1e1e;
		box-shadow: 0 4px 10px rgba(0, 0, 0, 1);
		width: 78rem; 
		max-width: 100%;
		position: fixed; 
		top: 0;
		right: 0; 
		height: 5rem; 
		z-index: 1000;
	}

	.logo {
		display: flex;
		align-items: center;
	}

	.logo img {
		height: 9rem;
		margin-right: 10px;
	}

	.logo span {
		font-size: 20px;
		font-weight: bold;
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
		top: 0; /* Adjust to be at the top */
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
