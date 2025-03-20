import { CtLit, html, property, customElement, css } from '@conectate/ct-lit';

@customElement('app-login')
export class AppLogin extends CtLit {
	@property({ type: Boolean }) isLoading = false;

	handleGoogleAuth() {
		this.isLoading = true;
		setTimeout(() => {
			this.isLoading = false;
		}, 1500);
	}

	static styles = css`
		:host {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			min-height: 100vh;
			height: 100vh;
			place-items: center;
			background-color: #242423;
			padding: 1rem;
		}
		.logo {
			display: flex;
			align-items: center;
			gap: 0.5rem;
			margin-bottom: 2rem;
		}
		.logo span {
			font-size: 2rem;
			font-weight: bold;
			color: #f7f0f0;
		}
		.card {
			max-width: 400px;
			width: 100%;
			background-color: #242423;
			border: 1px solid rgba(80, 137, 145, 0.2);
			box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
		}
		.card-header {
			text-align: center;
			padding: 1rem;
		}
		.card-title {
			font-size: 1.5rem;
			color: #f7f0f0;
			font-weight: bold;
		}
		.card-description {
			color: rgba(247, 240, 240, 0.7);
		}
		.card-content {
			padding: 2rem;
		}
		.button {
			width: 100%;
			padding: 0.75rem;
			background-color: #242423;
			color: #f7f0f0;
			border: 1px solid rgba(80, 137, 145, 0.3);
			display: flex;
			align-items: center;
			justify-content: center;
			cursor: pointer;
		}
		.button:hover {
			background-color: rgba(80, 137, 145, 0.1);
		}
		.button svg {
			margin-right: 0.5rem;
			width: 1.25rem;
			height: 1.25rem;
		}
		.terms {
			font-size: 0.75rem;
			text-align: center;
			color: rgba(247, 240, 240, 0.7);
		}
		.terms a {
			text-decoration: underline;
			transition: color 0.2s ease-in-out;
		}
		.terms a:hover {
			color: #f9a03f;
		}
	`;

	render() {
		return html`
			<div class="logo">
				<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" width="32" height="32" style="color: #f9a03f;">
					<path
						d="M12 0C5.372 0 0 5.372 0 12c0 5.303 3.438 9.8 8.207 11.388.6.112.803-.26.803-.578v-2.25c-3.338.73-4.04-1.611-4.04-1.611-.544-1.379-1.33-1.744-1.33-1.744-1.086-.742.082-.726.082-.726 1.2.084 1.832 1.234 1.832 1.234 1.064 1.82 2.8 1.29 3.496.987.107-.772.416-1.29.757-1.59-2.666-.304-5.468-1.334-5.468-5.933 0-1.313.468-2.384 1.236-3.223-.124-.304-.535-1.525.115-3.176 0 0 1.008-.322 3.303 1.253 1.86-.514 3.79-.514 5.65 0 2.295-1.576 3.303-1.253 3.303-1.253.651 1.65.239 2.872.115 3.176.769.839 1.236 1.91 1.236 3.223 0 4.613-2.802 5.629-5.476 5.933.428.369.826 1.099.826 2.221v3.293c0 .317.203.693.812.578 4.77-1.588 8.208-6.085 8.208-11.388 0-6.628-5.372-12-12-12z"
						fill="#f9a03f"
					/>
				</svg>
				<span>Grabit</span>
			</div>

			<article class="card">
				<div class="card-header">
					<h2 class="card-title">Welcome to Grabit</h2>
					<p class="card-description">Sign in with your Google account to continue</p>
				</div>
				<article class="card-content">
					<div>
						<button class="button" @click="${this.handleGoogleAuth}" ?disabled="${this.isLoading}">
							<svg viewBox="0 0 24 24">
								<path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4" />
								<path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853" />
								<path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" fill="#FBBC05" />
								<path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335" />
								<path d="M1 1h22v22H1z" fill="none" />
							</svg>
							${this.isLoading ? 'Signing in...' : 'Sign in with Google'}
						</button>

						<p class="terms">
							By signing in, you agree to our
							<a href="#">Terms of Service</a> and
							<a href="#">Privacy Policy</a>
						</p>
					</div>
				</article>
			</article>
		`;
	}
}
