import { CtLit, customElement, html, css } from '@conectate/ct-lit';
import "../app-router"

@customElement('lit-app')
export class LitApp extends CtLit {
	static styles = css`
		
		:host {
			display: block;
			font-family: "Poppins", sans-serif;
  			font-weight: 400;
  			font-style: normal;
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
				 <app-router></app-router>
			</main>
		`;
	}
}
