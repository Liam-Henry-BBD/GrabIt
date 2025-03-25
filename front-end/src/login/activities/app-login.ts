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

		img {
			text-align: center;
			height: 15rem;
			width: 15rem;
			z-index: 1000;
		}

		.card {
			width: 40rem;
			height: 20rem;
			background-color: #242423;
			border: 1px solid rgba(80, 137, 145, 1);
			box-shadow: 0 4px 8px rgba(0, 0, 0, 1);
			margin-bottom: 10rem;
			border-radius: 0.55rem;
		}

		.card-title {
			text-align: center;
			font-size: 2.5rem;
			color: #f7f0f0;
			font-weight: 500;
		}
		
		.card-description {
			color: #f7f0f0;
			text-align: center;
			font-size: 1.3rem;
		}

		.card-content {
			padding: 2rem;
		}

		.button {
			text-decoration: none;
			display: flex;
			align-items: center;
			justify-content: center;
			background-color: #242423;
			color: white;
			padding: 0.75rem 1rem;
			border-radius: 0.55rem;
			border: 1px solid rgba(80, 137, 145, 1);
		}

		.button:hover {
			background-color: #508991;
			color: #242423;
		}
		
		.button svg {
			margin-right: 0.5rem;
			width: 1.28rem;
			height: 1.28rem;
		}
		.terms {
			text-decoration: none;
			font-size: 0.85rem;
			text-align: center;
			color: rgba(247, 240, 240, 0.7);
		}
		.terms a {
			text-decoration: underline;
			transition: color 0.2s ease-in-out;
			color: #508991;
		}
		.terms a:hover {
			color: #f9a03f;
		}

	`;

	render() {
		return html`
			<img src="/src/home/home_images/GI_logo-white.png" alt="logo" />
			<article class="card">
				<div class="card-header">
					<h2 class="card-title">Welcome</h2>
					<p class="card-description">Sign in with your Google account to continue</p>
				</div>
				<article class="card-content">
					<div>
						<a class="button" href="http://localhost:8081/oauth2/authorization/google">
							<svg viewBox="0 0 24 24">
								<path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4" />
								<path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853" />
								<path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" fill="#FBBC05" />
								<path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335" />
								<path d="M1 1h22v22H1z" fill="none" />
							</svg>
							Continue with Google
						
						</a>

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
