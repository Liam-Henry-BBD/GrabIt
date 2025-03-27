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

interface Task {
	taskID: number;
	taskName: string;
	description: string;
	dueDate: string;
	taskPointID: number;
	taskStatusID: number;
}

interface ProjectOrganizer {
	owned: TemplateResult[];
	collaborating: TemplateResult[];
}

interface TaskOrganizer {
	available: TemplateResult[];
	grabbed: TemplateResult[];
	inReview: TemplateResult[];
	complete: TemplateResult[];
}

@customElement('home-app')
export class DashboardComponent extends LitElement {
	static styles = homeStyles;



	connectedCallback() {
		super.connectedCallback();
		this.apiRequest(this.urls.getProjects, 'GET', (data: any) => this.createProjectGroupByRoleComponent(data));
	}

	@state() private data: Project[] = [];
	@state() private currentProject: Project | null = null;
	@state() private projectOrganiser: ProjectOrganizer = { owned: [], collaborating: [] };
	@state() private tasks: TaskOrganizer = { available: [], grabbed: [], inReview: [], complete: [] };
	@state() private currentProjectRole: number | null = null;
	@state() private urls = {
		getProjects: 'http://localhost:8081/api/projects',
		getProjectTasks: (projectID: number) => `http://localhost:8081/api/projects/${projectID}/tasks`,
		getProjectDetails: (projectID: number) => `http://localhost:8081/api/projects/${projectID}`,
		createProject: 'http://localhost:8000/create-project'
	};

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

	private createProjectGroupByRoleComponent(response: Project[]): void {
		const createProjectComponent = (project: Project): TemplateResult => {
			return html`
				<li class="project-item">
					<span class="project-icon">📁</span>
					<a href=${'/home/' + project.projectID}>${project.projectName}</a>
				</li>
			`;
		};

		this.data = response;
		this.projectOrganiser = {
			owned: this.data.filter(project => project.collaboratorRole === 1).map(createProjectComponent),
			collaborating: this.data.filter(project => project.collaboratorRole === 2).map(createProjectComponent)
		};
	}

	private formatDate(dateString: string): string {
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
						<section class="sidebar-header">
							<input type="search" placeholder="Find a project..." class="sidebar-search" />
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
