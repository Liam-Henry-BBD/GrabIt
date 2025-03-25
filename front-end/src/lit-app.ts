import { CtLit, customElement, html, css } from '@conectate/ct-lit';
import "./base/app-router"
import './base/app-router';

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


	render() {
		return html`
		  <main>
			<!-- <header>GrabIt</header> -->
			<app-router></app-router>
		  </main>
			<main>
				 <app-router></app-router>
			</main>
		`;
	}
}
