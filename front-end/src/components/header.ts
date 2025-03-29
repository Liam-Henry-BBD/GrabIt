import { css, customElement, html, LitElement, state } from "@conectate/ct-lit";
import { getUser } from "../services/user.service";


@customElement("header-app")
export class HeaderApp extends LitElement {

	@state() picture: string | null = null;

	async connectedCallback() {
		super.connectedCallback();
		const user = await getUser()
		this.picture = user.picture;
	}

	render() {
		return html`
			<header class="header">
				<img id="logo" width="140" src="/src/home/home_images/GI_logo-white.png" alt="Logo" @click=${() => window.location.href = 'http://localhost:8000'}>
				<span class="profile-container">
					<img id="profile-icon" src=${this.picture ? this.picture : ""} alt="Profile Icon" />
					<a href="http://localhost:8000" class="logout-link">Logout</a>
				</span>
			</header>
		`;
	}
	

	static styles = css`
		.logo {
			display: flex;
			justify-content: center;
			align-items: center;
		}


		.logout-link {
			background-color: #f9a03f;
			color: #242423;
			padding: 0.5rem 0.5rem;
			text-decoration: none;
			font-size: 0.9rem;
			font-weight: bold;
			border-radius: 0.7rem;
			margin: 0; 
			position: absolute; 
			top: 50%; 
			right: 0%; 
			transform: translate(-50%, -50%); 
		}

		.logout-link:hover {
			border-radius: 0.7rem;
			background-color: #242423;
			color: #f9a03f;
			text-decoration: underline;
		}

		@media (max-width: 1024px) {
			.header {
				justify-content: center;
			}
			#logo {
				margin: 0 auto;
			}
			#profile-icon {
				position: fixed;
				right: 0;
			}
		}
		
		.logo button {
			border: none;
			padding-inline: 20px;
			color: #ff9800;
			padding: 1rem;
			margin-left: 3px;
			height: 2.5rem;
			width: 2.5rem;
			border-radius: 50%;
			background-color: inherit;
			text-decoration: underline;
		}
		
		.logo a {
			margin-top: 7px;
		}
		
		.header {
			display: flex;
			justify-content: space-between;
			align-items: center;
			background-color: #242423;
			width: 100vw;
			max-width: 100%;
			position: fixed;
			top: 0;
			right: 0;
			height: 5rem;
			z-index: 1000;
			overflow: hidden;
			border-bottom: 1px solid rgba(255, 255, 255, 0.1);
		}

		#profile-icon {
			margin-right: 10rem;
			margin-left: 3rem;
			height: 3rem;
			width: 3rem;
			border-radius: 50%;
		}
		.header input {
			padding: 0.8rem;
			border-radius: 1rem;
			background-color: #555;
			color: #fff;
			outline: none;
			border: none;
			width: 30rem;
			right: 0;
			margin-left: 1rem;
		}

		.header-icon img {
			width: 10%;
			height: auto;
			right: 0;
			margin-left: 30rem;
		}

		@media (max-width: 1024px) {
			.header {
				justify-content: center;
			}
			#logo {
				margin: 0 auto;
			}
			#profile-icon {
				position: fixed;
				right: 0;
			}
		}
	`;
}