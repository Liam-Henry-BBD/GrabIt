import { css, customElement, html, LitElement, state } from '@conectate/ct-lit';
import { getUser } from '../services/user.service';

@customElement('header-app')
export class HeaderApp extends LitElement {
	@state() picture: string | null = null;

	async connectedCallback() {
		super.connectedCallback();
		const user = await getUser();
		this.picture = user.picture;
	}

	logout() {
		localStorage.removeItem("token");
		window.location.href = "/";
	}

	@state()
	isToggle: boolean = false;

	toggleMenu() {
		this.isToggle = !this.isToggle;
	}


	render() {
		return html`
			<header class="header">
				<img id="logo" width="140" src="/src/home/home_images/GI_logo-white.png" alt="Logo" @click=${() => (window.location.href = 'http://localhost:8000')} />
				<span class="profile-container">
					<img id="profile-icon" @click=${this.toggleMenu} src=${this.picture ? this.picture : ""} alt="Profile Icon" />
					${
						this.isToggle ? 
							html`<section class="icon-menu">
								<button>Change theme</button>
								<button @click=${this.logout}>Logout</button>
							</section>` : ''
					}
				</span>
			</header>
		`;
	}

	static styles = css`

		.profile-container {
			position: relative;
			width: 150px;
		}

		.icon-menu {
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			position: absolute;
			background-color: #f9a03f;
			width: 130px;
			/* border-radius: 10px; */
		}

		.icon-menu button {
			width: 90%;
			border: none;
			background-color: #131313 ;
			border-radius: 5px;
			padding: .4em .2em;
			margin: .2em .2em;
			color: #fff;
		}

		.logo {
			display: flex;
			justify-content: center;
			align-items: center;
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
				margin-left: auto;
				place-self: end;
				top: 1rem;
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
			/* overflow: hidden; */
			border-bottom: 1px solid rgba(255, 255, 255, 0.1);
		}

		#profile-icon {
			margin-right: 3rem;
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
