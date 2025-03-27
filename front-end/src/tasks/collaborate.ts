import { LitElement, html, css } from 'lit';
import { customElement, property, state } from 'lit/decorators.js';
import {createTaskStyles} from './create-task.styles';
import sendRequest from '../services/requests';

@customElement('collaborate-task')
export class CollaborateTask extends LitElement {
    @state() name: string = '';
    
    static styles = createTaskStyles;

    @state() isOpen: boolean = false;

    @property( { type: Number}) taskID: number =0;

    handleInput(e: Event): void {
        const target = e.target as HTMLInputElement;

        this.name = target.value;
        
    }

    async handleSubmit(e: Event) {
        e.preventDefault();

        const taskCollaborator = {
            "user": {
              "userID": this.name
            },
            "project": 68,
            "role": {
              "roleID": 4
            },
            "task": {
              "taskID": this.taskID
            },
            "JoinedAt": "2025-02-27T15:30:00",
            "isActive": 1
          }
          
        try {
            const response = await sendRequest("/task-collaborators", {
                body: JSON.stringify(taskCollaborator),
                method: "POST"
            });
            console.log(response);
        } catch (error) {
            console.log(error);
        }

        this.resetForm();
        this.closeModal();
    }

    closeModal(): void {
        this.isOpen = false;
        this.dispatchEvent(new CustomEvent('modal-close', { bubbles: true, composed: true }));
    }

    resetForm(): void {
        this.name = '';
    }

    render() {
        if (!this.isOpen) return html``;

        return html`
            <div class="modal">
                <div class="modal-content">
                    <div class="header">
                        <h2>Add Task Collaborator</h2>
                        <button @click=${this.closeModal}>✕</button>
                    </div>

                    <form @submit=${this.handleSubmit}>
                        <label>
                            Username:
                            <input type="text" name="name" .value=${this.name} @input=${this.handleInput} required />
                        </label>
                        
                        <button type="submit">Add task collaborator</button>
                    </form>
                </div>
            </div>
        `;
    }
}
