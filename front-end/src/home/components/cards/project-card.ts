import { CtLit, customElement, html, property, state } from "@conectate/ct-lit";
import { cardStyles } from "./project-card.styles";
import { Task } from "../../../utils/types";
import { formatDate } from "../../../utils/app";
import { RouterLocation } from "@vaadin/router";



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
        taskStatusID: 0,
        userID: 0,
        projectID: 0,
    };

    @property({ type: String}) action: string = "";


    @property() handleTaskAction = (taskID: number, projectID: number) => {};
    @property({ type: Boolean }) visible: boolean = false;
    
    render() {
        return html`
           <div class="project-card ${this.getTaskCategory()}">
                    <h3>${this.task.taskName} <span class="points">${this.task.taskPointID} pts</span></h3>
                    <p class="task-description">${this.task.taskDescription.slice(0, 95)}...</p>
                    <p class="due-date">Due: ${formatDate(this.task.taskCreatedAt)}</p>
            
                    ${this.action && this.visible ? html`<button @click=${() => this.handleTaskAction(this.task.taskID, this.task.projectID)} class="card-btn">${this.action} </button>` : ''}
                </div>`;
        }

        private getTaskCategory(): string {
        if (this.task.taskPointID === 5) {
            return 'simple';
        } else if (this.task.taskPointID === 10) {
            return 'medium';
        } else if (this.task.taskPointID === 15) {
            return 'hard';
        }
        return '';
        }
}

