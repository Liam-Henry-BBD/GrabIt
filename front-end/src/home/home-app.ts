import { html, LitElement } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import '../components/header';
import '../auth/activities/auth-router';
import './components/cards/project-card';
import './components/empty-app';
import { homeStyles } from './home.styles';

@customElement('home-app')
export class DashboardComponent extends LitElement {
	static styles = homeStyles;



	connectedCallback() {
		super.connectedCallback();
		this.apiRequest(this.urls.getProjects, 'GET', (data: any) => this.createProjectGroupByRoleComponent(data));
	}

	@state() private data: any;
	@state() private projectOrganiser: any = { owned: [], collaborating: [] };
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
				<li class="project-item">
					<span class="project-icon">📁</span>
					<a href=${'/home/' + project.projectID}>${project.projectName}</a>
				</li>
			`;
		};
		this.data = response;
		this.projectOrganiser = {
			'my projects': this.data.filter((project: any) => project.collaboratorRole == 1).map(createProjectComponent),
			collaborating: this.data.filter((project: any) => project.collaboratorRole == 2).map(createProjectComponent)
		};
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
							<a href="http://localhost:8000/create-project" class="new-project">+ New Project</a>
						</section>
						<hr class="separator" />

						<!-- Project Groups -->
						${
							this.data &&
							Object.keys(this.projectOrganiser).map((group: any) => {
								return html`<h2 class="project-header">
										<svg
											xmlns="http://www.w3.org/2000/svg"
											width="24"
											height="24"
											viewBox="0 0 24 24"
											fill="none"
											stroke="currentColor"
											stroke-width="2"
											stroke-linecap="round"
											stroke-linejoin="round"
											class="lucide lucide-folder-plus mr-2 h-4 w-4 text-[#F7F0F0]/70"
										>
											<path d="M12 10v6"></path>
											<path d="M9 13h6"></path>
											<path d="M20 20a2 2 0 0 0 2-2V8a2 2 0 0 0-2-2h-7.9a2 2 0 0 1-1.69-.9L9.6 3.9A2 2 0 0 0 7.93 3H4a2 2 0 0 0-2 2v13a2 2 0 0 0 2 2Z"></path></svg>${group}
									</h2>
									<ul>
										${this.projectOrganiser[group]}
									</ul>`;
						})
					}
					</nav>
					<main>
						<slot></slot>
					</main>
				</section>
			</auth-router>
		`;
	}
}
