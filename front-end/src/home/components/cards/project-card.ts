import { CtLit, customElement, html, property, state } from "@conectate/ct-lit";
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
        taskStatusID: 0,
        userID: 0,
        projectID: 0,
        isActive: true
    };
    

    @property({ type: String}) action: string = "";


    @property() handleTaskAction = (taskID: number, projectID: number) => {};
    @property({ type: Boolean }) visible: boolean = false;
    
    render() {

        interface PointClassMap {
            [key: number]: string;
        }

        const getPointClass = (points: number): string => {
            const pointClassMap: PointClassMap = {
            5: 'simple',
            10: 'medium',
            15: 'hard',
            };

            return pointClassMap[points] || '';
        };
    
        return html`
            <div class="project-card" ?disabled=${this.task?.isActive}>
                <h3>${this.task.taskName} <span class="points ${getPointClass(this.task.taskPointID)}">${this.task.taskPointID}</span></h3>
                <p class="task-description">${this.task.taskDescription.slice(0, 95)}...</p>
                <p class="due-date">Due: ${formatDate(this.task.taskCreatedAt)}</p>
    
                ${this.action && this.visible ? html`<button @click=${() => this.handleTaskAction(this.task.taskID, this.task.projectID)} class="card-btn">${this.action}</button>` : ''}
            </div>
        `;
    }
    }
