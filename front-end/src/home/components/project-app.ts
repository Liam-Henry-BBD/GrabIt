import { getMyProjectTasks, getProject, getProjectTasks } from '../../services/projects.service';
import { RouterLocation } from '@vaadin/router';
import { css, CtLit, html } from '@conectate/ct-lit';
import { customElement, property, state } from 'lit/decorators.js';
import { Project, Task } from '../../utils/types';
import { projectAppStyles } from './project-app.styles';
import { grabTask } from '../../services/task.service';

@customElement('project-app')
export class ProjectApp extends CtLit {
	@state()
	private tasks: Task[] = [];

	@state()
	private project: Project = {
		projectID: 0,
		projectName: '',
		projectDescription: '',
		createdAt: '',
		updatedAt: '',
		active: false
	};

	@state()
	private projectID: number = 0;

	@state() private grabbed: number = 0;
	@state() private available: number = 0;
	@state() private review: number = 0;
	@state() private complete: number = 0;

	@property({ type: Number }) currentProjectRole = 0;

	onBeforeEnter(location: RouterLocation) {
		this.projectID = location.params['projectID'] as number;
	}

	connectedCallback(): void {
		super.connectedCallback();
		this.fetchData();
	}

	async fetchData() {
		const response = await getProjectTasks(this.projectID);
		const myTasks = await getMyProjectTasks(this.projectID);
		const project = await getProject(this.projectID);
		console.log(myTasks);
		this.project = project;
		this.tasks = response;
		console.log(this.tasks);

		this.available = this.tasks.filter(task => task.taskStatusID == 1).length;
		this.grabbed = this.tasks.filter(task => task.taskStatusID == 2).length;
		this.review = this.tasks.filter(task => task.taskStatusID == 3).length;
		this.complete = this.tasks.filter(task => task.taskStatusID == 4).length;
	}

	async handleGrabTask(taskID: number) {
		console.log(taskID, this.projectID);
		// const response = await grabTask(taskID, this.projectID);
		// console.log(response);
	}

	async requestReview() {}

	async completeTask() {}

	static styles = projectAppStyles;

	render() {
		return html`<main>
			<section class="project-head">
				<article class="project-details">
					<h1>${this.project ? this.project.projectName : 'Create or select a project'}</h1>
					<p class="project-desc">${this.project ? this.project.projectDescription : 'Your current open projects details will be available here'}</p>
				</article>
				<article>
					<section class="article-buttons">
						<div>
							<a href="http://localhost:8000/project/${this.projectID}" class="new-project-body">Manage Tasks</a>
							<!-- ${this.currentProjectRole == 1 ? html`<button class="new-collaborator">Update Project</button>` : html``} -->
						</div>
						<a href="http://localhost:8000/project/${this.projectID}/leaderboard" class="leaderboard-button">Leaderboard</a>
					</section>
				</article>
			</section>

			<section class="tab-content">
				<section class="columns">
					<article class="column">
						<div class="column-space"><span>Available</span> <span class="task-count">${this.available}</span></div>
						${this.tasks
							.filter(task => task.taskStatusID == 1)
							.map(task => {
								return html`<project-card .handleTaskAction=${this.handleGrabTask} .task=${task} .action=${'Grab task'}></project-card>`;
							})}
					</article>

					<article class="column">
						<div class="column-space"><span>Grabbed</span> <span class="task-count">${this.grabbed}</span></div>
						${this.tasks
							.filter(task => task.taskStatusID == 2)
							.map(task => {
								return html`<project-card .handleTaskAction=${this.handleGrabTask} .task=${task} .action=${'Request review'}></project-card>`;
							})}
					</article>

					<article class="column">
						<div class="column-space"><span>In Review</span> <span class="task-count">${this.review}</span></div>
						${this.tasks
							.filter(task => task.taskStatusID == 3)
							.map(task => {
								return html`<project-card .handleTaskAction=${this.handleGrabTask} .task=${task} .action=${'Complete'}></project-card>`;
							})}
					</article>

					<article class="column">
						<div class="column-space"><span>Complete</span> <span class="task-count">${this.complete}</span></div>
						${this.tasks
							.filter(task => task.taskStatusID == 4)
							.map(task => {
								return html`<project-card .handleTaskAction=${this.handleGrabTask} .task=${task}></project-card>`;
							})}
					</article>
				</section>
			</section>
		</main> `;
	}
}
