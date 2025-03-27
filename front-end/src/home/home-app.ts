import { html, LitElement, TemplateResult } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import '../components/header';
import '../auth/activities/auth-router';
import './components/cards/project-card';
import './components/empty-app';
import { homeStyles } from './home.styles';

interface Project {
	projectID: number;
	projectName: string;
	collaboratorRole: number;
}

interface ProjectOrganizer {
	"my projects": TemplateResult[];
	collaborating: TemplateResult[];
}

@customElement('home-app')
export class DashboardComponent extends LitElement {
	static styles = homeStyles;

	@state() private data: Project[] = [];
	@state() private filteredProjects: Project[] = [];
	@state() private projectOrganiser: ProjectOrganizer = { "my projects": [], collaborating: [] };
	@state() private isSidebarOpen: boolean = false;

	@state() private urls = {
		getProjects: 'http://localhost:8081/api/projects',
		getProjectTasks: (projectID: number) => `http://localhost:8081/api/projects/${projectID}/tasks`,
		getProjectDetails: (projectID: number) => `http://localhost:8081/api/projects/${projectID}`,
		createProject: 'http://localhost:8000/create-project'
	};

	connectedCallback() {
		super.connectedCallback();
		this.apiRequest(this.urls.getProjects, 'GET', (data: any) => {
			this.data = data;
			this.filteredProjects = [...data];
			this.createProjectGroupByRoleComponent(data);
		});
	}

	async apiRequest<T>(url: string, method: string, callback: (data: T) => void): Promise<void> {
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
			const data: T = await response.json();
			if (callback) callback(data);
		} catch (error) {
			console.error('Error fetching data: ', error);
		}
	}

	private toggleSidebar(): void {
		this.isSidebarOpen = !this.isSidebarOpen;
		console.log(this.isSidebarOpen);
	}

	private createProjectGroupByRoleComponent(response: Project[]): void {
		const createProjectComponent = (project: Project): TemplateResult => {
			return html`
				<li class="project-item">
					<span class="project-icon">📁</span>
					<a href=${'/home/' + project.projectID}>${project.projectName}</a>
				</li>
			`;
		};

		this.projectOrganiser = {
			"my projects": response.filter(project => project.collaboratorRole === 1).map(createProjectComponent),
			collaborating: response.filter(project => project.collaboratorRole === 2).map(createProjectComponent)
		};
	}

	private formatDate(dateString: string): string {
		if (!dateString) return 'Not set';
		const date = new Date(dateString);
		return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
	}

	private handleFilterProjects(event: Event): void {
		const searchTerm = (event.target as HTMLInputElement).value.toLowerCase();

		if (searchTerm === '') {
			this.filteredProjects = [...this.data];
		} else {
			this.filteredProjects = this.data.filter(project => project.projectName.toLowerCase().includes(searchTerm));
		}

		this.createProjectGroupByRoleComponent(this.filteredProjects);
	}

	render() {
		return html`
			<auth-router>
				<header-app> </header-app>
				<button class="burger-menu" @click=${() => this.toggleSidebar()}>☰</button>
				<section class="dashboard">
					<nav class="sidebar ${this.isSidebarOpen ? 'open' : 'closed'}">
						<section class="sidebar-header">
							<input type="search" placeholder="Find a project..." class="sidebar-search" @input=${this.handleFilterProjects} />
							<a href=${this.urls.createProject} class="new-project">+ New Project</a>
						</section>
						<hr class="separator" />

						${this.data &&
						(Object.keys(this.projectOrganiser) as (keyof ProjectOrganizer)[]).map(group => {
							return html`
								<h2 class="project-header">
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
										<path d="M20 20a2 2 0 0 0 2-2V8a2 2 0 0 0-2-2h-7.9a2 2 0 0 1-1.69-.9L9.6 3.9A2 2 0 0 0 7.93 3H4a2 2 0 0 0-2 2v13a2 2 0 0 0 2 2Z"></path>
									</svg>
									${group}
								</h2>
								<ul>
									${this.projectOrganiser[group]}
								</ul>
							`;
						})}
					</nav>
					<main>
						<slot></slot>
					</main>
				</section>
			</auth-router>
		`;
	}
}
