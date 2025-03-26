import { LitElement, html, css } from 'lit';
import { customElement, state } from 'lit/decorators.js';

@customElement('create-task')
export class CreateTask extends LitElement {
  @state() name: string = '';
  @state() description: string = '';
  @state() deadline: string = '';
  @state() difficulty: 'easy' | 'medium' | 'hard' = 'easy';
  @state() isOpen: boolean = false;
  @state() projectId: string = '';

  static styles = css`
    :host {
      display: block;
    }
    .modal {
      position: fixed;
      inset: 0;
      background-color: rgba(0, 0, 0, 0.5);
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 1rem;
    }
    .modal-content {
      background-color: #242423;
      color: #F7F0F0;
      max-width: 400px;
      width: 100%;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }
    .header {
      display: flex;
      justify-content: space-between;
      padding: 1rem;
      border-bottom: 1px solid #F9A03F;
    }
    h2 {
      color: #F9A03F;
    }
    button {
      background: none;
      border: none;
      cursor: pointer;
      color: #F7F0F0;
      padding: 15px;
      background-color: rgba(80, 137, 145, 0.1);
    }
    form {
      padding: 1rem;
      display: flex;
      flex-direction: column;
      gap: 2rem;
    }
    input,
    textarea,
    select {
      width: 90%;
      padding: 0.5rem;
      padding-bottom: 0.5rem;
      border-radius: 4px;
      border: 1px solid #508991;
      background-color: rgba(171, 233, 241, 0.1); 
      margin-top: 10px;
      color: #F7F0F0; 
    }
    .difficulty-buttons {
      display: flex;
      gap: 0.5rem;
    }
    .difficulty-buttons button {
      flex: 1;
      border-radius: 4px;
      border: 1px solid transparent;
    }
    .difficulty-buttons .selected {
      background-color: #F9A03F;
      color: #F7F0F0;
    }
    .difficulty-buttons button:hover {
      background-color: #508991;
    }
  `;

  handleInput(e: Event): void {
    const target = e.target as HTMLInputElement;
    const name = target.name as keyof this;

    if (
      name === 'difficulty' &&
      (target.value === 'easy' || target.value === 'medium' || target.value === 'hard')
    ) {
      this.difficulty = target.value as 'easy' | 'medium' | 'hard';
    } else {
      (this[name] as string) = target.value;
    }
  }

  handleDifficulty(level: 'easy' | 'medium' | 'hard'): void {
    this.difficulty = level;
  }

  handleSubmit(e: Event): void {
    e.preventDefault();
    if (!this.name.trim() || !this.description.trim()) return;

    const task = {
      projectId: this.projectId,
      name: this.name,
      description: this.description,
      deadline: this.deadline ? new Date(this.deadline) : undefined,
      difficulty: this.difficulty,
    };

    this.dispatchEvent(
      new CustomEvent('task-submit', { detail: task, bubbles: true, composed: true })
    );

    this.resetForm();
    this.closeModal();
  }

  closeModal(): void {
    this.isOpen = false; 
    this.dispatchEvent(new CustomEvent('modal-close', { bubbles: true, composed: true }));
  }

  resetForm(): void {
    this.name = '';
    this.description = '';
    this.deadline = '';
    this.difficulty = 'medium';
  }

  render() {
    if (!this.isOpen) return html``;

    return html`
      <div class="modal">
        <div class="modal-content">
          <div class="header">
            <h2>Create New Task</h2>
            <button @click=${this.closeModal}>✕</button>
          </div>

          <form @submit=${this.handleSubmit}>
            <label>
              Task Name:
              <input
                type="text"
                name="name"
                .value=${this.name}
                @input=${this.handleInput}
                required
              />
            </label>

            <label>
              Description:
              <textarea
                name="description"
                .value=${this.description}
                @input=${this.handleInput}
                required
              ></textarea>
            </label>

            <label>
              Deadline (Optional):
              <input
                type="datetime-local"
                name="deadline"
                .value=${this.deadline}
                @input=${this.handleInput}
              />
            </label>

            <label>Difficulty:</label>
            <div class="difficulty-buttons">
              ${(['easy', 'medium', 'hard'] as Array<'easy' | 'medium' | 'hard'>).map(
                (level) => html`
                  <button
                    type="button"
                    class=${this.difficulty === level ? 'selected' : ''}
                    @click=${() => this.handleDifficulty(level)}
                  >
                    ${level}
                  </button>
                `
              )}
            </div>

            <button type="submit">Create Task</button>
          </form>
        </div>
      </div>
    `;
  }
}
