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
			background-color: #242423;
			padding: 2rem;
			border-radius: 8px;
			width: 300px;
			text-align: center;
			box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
			animation: popupAnimation 0.3s ease-out;
			border: 0.2rem solid rgba(80, 137, 145, 5);
		}
		button {
			margin: 10px;
			background-color: rgb(255, 87, 51);
			color: rgb(36, 36, 35);
			font-weight: bold;
			border: none;
			padding: 0.5rem 1.5rem;
			border-radius: 1rem;
			cursor: pointer;
			transition: background-color 0.3s;
			font-size: 1rem;
		}
		.no-btn {
			background-color: #94d891;
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
					<button @click=${this.cancel} class="no-btn">No</button>
				</div>
			</div>
		`;
	}
}

customElements.define('confirm-dialog', ConfirmDialog);
