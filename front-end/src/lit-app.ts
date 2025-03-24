import { CtLit, customElement, html, css } from '@conectate/ct-lit';
import { initRouter } from './base/app-router';

@customElement('lit-app')
export class LitApp extends CtLit {
	static styles = css`
		:host {
			display: block;
		}
		main {
			min-height: 100vh;
			background-color: #242423;
			padding: 1rem;
		}
	`;

	firstUpdated() {
		const outlet = this.shadowRoot!.getElementById('router-outlet') as HTMLElement;
		initRouter(outlet);
	}

	render() {
		return html`
			<main>
				<div id="router-outlet"></div>
			</main>
		`;
	}
}
