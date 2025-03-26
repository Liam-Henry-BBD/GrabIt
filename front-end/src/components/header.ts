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
			<img src="/src/home/home_images/GI_logo-white.png" alt="logo" />
				<input type="search" placeholder="Search tasks..." />
				<img id="profile-icon" src=${this.picture ? this.picture : ""} alt="Logo" />
			</header>
        `;
	}

	static styles = css`
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

		.header img {
			left: 2rem;
			height: 10rem;
			width: 10rem;
		}

		#profile-icon {
			margin-right: 3rem;
			margin-left: 3rem;
			height: 2.5rem;
			width: 2.5rem;
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

    `;
}