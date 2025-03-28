import { getProject, getProjectTasks } from '../../services/projects.service';
import { RouterLocation } from '@vaadin/router';
import { CtLit, html } from '@conectate/ct-lit';
import { customElement, property, state } from 'lit/decorators.js';
import { Project, Task, User } from '../../utils/types';
import { projectAppStyles } from './project-app.styles';
import { filterTasks } from '../../utils/app';
import { getUser } from '../../services/user.service';
import { completeTask, grabTask, rejectTaskReview, requestTaskReview } from '../../services/task.service';

import '../components/cards/review-card';
import '../components/cards/grab-card';

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
		active: false,
		collaboratorRole: 0
	};

	@state()
	private currentUser: User = {
		email: '',
		fullName: '',
		picture: '',
		verified: false,
		userID: 0
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

		const project = await getProject(this.projectID);

		this.currentUser = (await getUser()) as User;

		this.project = project;
		this.tasks = response;
		this.tasks = filterTasks(this.tasks, this.currentUser.userID);

		this.available = this.tasks.filter(task => task.taskStatusID == 1).length;
		this.grabbed = this.tasks.filter(task => task.taskStatusID == 2).length;
		this.review = this.tasks.filter(task => task.taskStatusID == 3).length;
		this.complete = this.tasks.filter(task => task.taskStatusID == 4).length;
	}

	async handleGrabTask(taskID: number, projectID: number) {
		const grabbedTask = (await grabTask(taskID, projectID)) as Task;
		if (grabbedTask != null) {
			window.location.reload();
		}
	}

	async handleRequestReview(taskID: number, _: number) {
		const requestReview = (await requestTaskReview(taskID)) as Task;
		if (requestReview != null) {
			window.location.reload();
		}
	}

	async handleCancelReview(taskID: number) {
		const requestReview = (await rejectTaskReview(taskID)) as Task;
		if (requestReview != null) {
			window.location.reload();
		}
	}

	async handleCompleteTask(taskID: number) {
		const requestReview = (await completeTask(taskID)) as Task;
		if (requestReview != null) {
			window.location.reload();
		}
	}

    

	static styles = projectAppStyles;

	render() {
		return html`<main>
			<section class="project-head">
				<article class="project-details">
					<h1>${this.project.projectName ? this.project.projectName : 'Loading...'}</h1>
					<p class="project-desc">${this.project.projectDescription ? this.project.projectDescription : '...'}</p>
				</article>
				<article>
					<section class="article-buttons">
						${this.project.collaboratorRole == 1 ?  html`<a href="/project/${this.projectID}" class="new-project-body">Manage Tasks</a>`: ''}
						<a href="/project/${this.projectID}/leaderboard" class="leaderboard-button">Leaderboard</a>
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
								const allowedToGrab = this.project.collaboratorRole != 1;
								return html`<project-card .visible=${allowedToGrab} .handleTaskAction=${this.handleGrabTask} .task=${task} .action=${'Grab task'}></project-card>`;
							})}
					</article>

					<article class="column">
						<div class="column-space"><span>Grabbed</span> <span class="task-count">${this.grabbed}</span></div>
						${this.tasks
							.filter(task => task.taskStatusID == 2)
							.map(task => {
								const allowedToGrab = this.project.collaboratorRole != 1 && task.userID == this.currentUser.userID;
                                const canCollab = this.project.collaboratorRole == 1 || task.userID == this.currentUser.userID;
								return html`<grab-card .collab=${canCollab} .visible=${allowedToGrab} .handleTaskAction=${this.handleRequestReview} .task=${task} .action=${'Request review'}></grab-card>`;
							})}
					</article>

					<article class="column">
						<div class="column-space"><span>In Review</span> <span class="task-count">${this.review}</span></div>
						${this.tasks
							.filter(task => task.taskStatusID == 3)
							.map(task => {
								const allowedToComplete = this.project.collaboratorRole == 1;
								const allowedToCancel = task.userID == this.currentUser.userID || this.project.collaboratorRole == 1;
								return html`<review-card
									.handleCancelAction=${this.handleCancelReview}
									.cancel=${allowedToCancel}
									.visible=${allowedToComplete}
									.handleTaskAction=${this.handleCompleteTask}
									.task=${task}
									.action=${'Complete'}
								>
								</review-card>`;
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
