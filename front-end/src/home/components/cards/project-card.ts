import { CtLit, customElement, html, property } from "@conectate/ct-lit";
import { cardStyles } from "./project-card.styles";
import { Task } from "../../../utils/types";
import { formatDate } from "../../../utils/app";



@customElement("project-card")
export class ProjectCard extends CtLit {

    static styles = cardStyles;

    @property({ type: Object }) task: Task = {
        taskCreatedAt: "",
        taskDescription: "",
        taskID: 0,
        taskName: "",
        taskPointID: 0,
        taskReviewRequestedAt: "",
        taskStatusID: 0
    };

    @property({ type: String}) action: string = "";

    @property() handleTaskAction = (taskID: number) => {};

    
    render() {
        return html`
           <div class="project-card">
				<h3>${this.task.taskName} <span class="points">${this.task.taskPointID} pts</span></h3>
				<p class="task-description">${this.task.taskDescription.slice(0, 95)}...</p>
				<p class="due-date">Due: ${formatDate(this.task.taskCreatedAt)}</p>
				${this.action ? html`<button @click=${() => this.handleTaskAction(this.task.taskID)} class="card-btn">${this.action} </button>` : ''}
			</div>`;
    }
}
