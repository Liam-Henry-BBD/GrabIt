import { LitElement, html, css } from 'lit';
import { customElement, property, state } from 'lit/decorators.js';
import {createTaskStyles} from './create-task.styles';
import sendRequest from '../services/requests';
import { debounce } from '../utils/app';

@customElement('collaborate-task')
export class CollaborateTask extends LitElement {
    @state() name: string = '';
    
    static styles = createTaskStyles;

    @state() userID: string = "";

    @state() isOpen: boolean = false;

    @state() users: [] = [];

    @property( { type: Number}) taskID: number = 0;

    handleInput(e: Event): void {
        const target = e.target as HTMLInputElement;
        this.name = target.value;
    }

    debounce(func: Function, delay: number) {
        let timer: ReturnType<typeof setTimeout>;
        return (...args: any[]) => {
          clearTimeout(timer);
          timer = setTimeout(() => func(...args), delay);
        };
    }

    async fetchUsernames(name: string) {
        try {
            const response = await sendRequest("/user/search?query=" + name);
            this.users = response;
        } catch (error) {
            console.log(error);
        }
    }

    @property({type: Number}) roleID: number = 4;

    search = debounce(() => this.fetchUsernames(this.name), 500);

    handleSearch (event: Event) {
        const target = event.target as HTMLInputElement;
        this.name = target.value;
        if (this.name.length < 2) {
            return;
        }
        this.search()
    }


    async handleSubmit(e: Event) {
        e.preventDefault();

        const taskCollaborator = {
            "user": {
              "userID": this.userID
            },
            "project": 68,
            "role": {
              "roleID": this.roleID
            },
            "task": {
              "taskID": this.taskID
            },
            "JoinedAt": "2025-02-27T15:30:00",
            "isActive": 1
          }
          
        try {
            await sendRequest("/task-collaborators", {
                body: JSON.stringify(taskCollaborator),
                method: "POST"
            });
        } catch (error) {
            console.log("The error", error);
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

    selectUser(user: any) {
        this.userID = user?.userID;
        this.name = user?.gitHubID;

        this.users = []
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
                            <input type="text" .value=${this.name} id="username"  @input=${this.handleSearch} required />   
                            <section>
                                ${this.users.map((user: any) => {
                                    return html`<p @click=${() => this.selectUser(user)} class="matcher">${user?.gitHubID}</p>`;
                                })}
                            </section>                  
                        </label>
                        <button type="submit">Add task collaborator</button>
                    </form>
                </div>
            </div>
        `;
    }
}
