import { LitElement, html, css } from 'lit';
import { property } from 'lit/decorators.js';

export class ConfirmDialog extends LitElement {
  @property({ type: Boolean }) open = false;
  @property({ type: String }) message = 'Are you sure?';

  static styles = css`
    .modal {
      display: flex;
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.5);
      align-items: center;
      justify-content: center;
    }
    .content {
      background-color: rgba(80, 137, 145, 0.1);
      padding: 20px;
      border-radius: 8px;
      text-align: center;
    }
    button {
      margin: 10px;
      padding: 8px 16px;
      cursor: pointer;
    }
  `;

  private confirm() {
    this.dispatchEvent(new CustomEvent('confirm', { bubbles: true, composed: true }));
    this.open = false;
  }

  private cancel() {
    this.dispatchEvent(new CustomEvent('cancel', { bubbles: true, composed: true }));
    this.open = false;
  }

  render() {
    if (!this.open) return null;
    return html`
      <div class="modal">
        <div class="content">
          <p>${this.message}</p>
          <button @click=${this.confirm}>Yes</button>
          <button @click=${this.cancel}>No</button>
        </div>
      </div>
    `;
  }
}

customElements.define('confirm-dialog', ConfirmDialog);
