import { CtLit, html, property, customElement, css } from '@conectate/ct-lit';

@customElement('landing-app')
export class LandingApp extends CtLit {
	static styles = css`
		@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

		body {
			font-family: 'Poppins', sans-serif;
			line-height: 1.6;
			background-color: white;
		}

		/* this is the actual header */
		nav {
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 1rem 1rem;
			background-color: #242423;
			box-shadow: 0 2px 10px rgba(0, 0, 0, 1);
			position: fixed;
			width: 100vw;
			height: 3rem;
			z-index: 1000;
		}

		#logo {
			height: 9rem;
			width: 9rem;
			position: absolute;
			bottom: 1;
			left: 1rem;
		}

		nav ul {
			display: flex;
			list-style: none;
			gap: 4rem;
			text-align: center;
			margin: 0rem auto;
		}

		nav button {
			border: none;
			background: transparent;
			color: white;
			font-weight: 500;
			font-size: 1.2rem;
			text-decoration: none;
			text-align: center;
		}

		nav button:hover {
			color: #f9a03f;
		}

		@media (max-width: 1024px) {
			nav {
				padding: 1rem 5%;
			}

			/* Center logo in mobile view */
			#logo {
				position: absolute;
				left: 50%;
				transform: translateX(-50%);
			}

			nav ul {
				display: none; /* Hide the menu by default */
				flex-direction: column;
				position: absolute;
				top: 6rem;
				left: 0;
				right: 0;
				background-color: #242423;
				text-align: center;
				padding: 1rem 0;
			}

			nav ul.active {
				display: flex; /* Show the menu when active */
			}

			nav ul li {
				padding: 1rem;
			}

			nav ul li a {
				font-size: 1rem;
			}

			.hamburger {
				display: block;
				cursor: pointer;
				z-index: 1100;
				position: absolute;
				right: 1rem; /* Hamburger on the left */
				top: 1rem;
				font-size: 2rem;
				color: white;
			}
		}

		/* ---------------------------------------- */
		.auth-links {
			display: flex;
			color: wheat;
			gap: 1.2rem;
			left: 10rem;
		}

		.auth-links .sign-up {
			background-color: #242423;
			color: #f7f0f0;
			border: none;
			padding: 0.5rem 1rem;
			font-size: 1em;
			font-weight: 500;
			cursor: pointer;
		}

		.auth-links .sign-up:hover {
			color: #f9a03f;
		}

		.auth-links .sign-in {
			background-color: #f9a03f;
			color: #242423;
			border: none;
			padding: 0.5rem 1rem;
			font-size: 1rem;
			font-weight: 500;
			cursor: pointer;
			border-radius: 0.5rem;
		}

		.auth-links .sign-in:hover {
			background-color: #f9a03f;
			color: #242423;
		}

		/* ---------------------------------------- */

		/* where we land */

		.hero {
			display: flex;
			flex-direction: row-reverse;
			justify-content: space-evenly;
			align-items: center;
			padding: 8rem 1rem 6rem;
			background: linear-gradient(to bottom, white 20%, #242423 80%);
			background-color: white;
			height: 90vh;
			padding-inline: 10%;
			box-shadow: 0 2px 10px rgb(47, 46, 46);
		}

		.hero section {
			width: 90%;
			height: 100%;
			display: flex;
			flex-direction: column;
			align-items: center;
		}

		.hero h1 {
			font-size: 5rem;
			font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
			color: #242423;
			letter-spacing: 0.9px;
			line-height: 1.1;
			margin-top: 2.5rem;
			margin-bottom: 1.5rem;
			align-items: center;
			text-align: center;
		}

		.hero p {
			font-size: 1.3rem;
			font-weight: 500;
			font-family: 'Poppins', sans-serif;
			color: #242423;
			margin-bottom: 1rem;
			text-align: center;
		}

		.get-started {
			background-color: #f9a03f;
			color: #242423;
			border: none;
			padding: 1rem;
			font-size: 1rem;
			font-weight: 500;
			border-radius: 0.5rem;
			cursor: pointer;
			text-decoration: none;
		}

		.get-started:hover {
			background-color: #242423;
			color: #f9a03f;
		}

		@media (max-width: 1024px) {
			.hero {
				flex-direction: column;
				padding: 6rem 2rem 4rem;
			}

			.hero h1 {
				font-size: 3rem;
				line-height: 1.3;
			}

			.hero p {
				font-size: 1.2rem;
				margin-top: 1.5rem;
			}

			.get-started {
				padding: 0.5rem 1.5rem;
				font-size: 1.1rem;
			}
		}

		/* ---------------------------------------- */

		.features {
			height: calc(100vh - 3rem);
			padding-top: 3rem;
			margin-top: 3rem;
		}

		.features h2 {
			font-size: 3rem;
			margin-top: -2rem;
			text-align: center;
			color: white;
			width: 100%;
			font-weight: bold;
			place-self: center;
			grid-column: 1 / -1;
			grid-row: 1/2;
		}

		.features p {
			font-size: 1rem;
			color: white;
			text-align: center;
			margin-top: 0.1rem;
		}

		.card h3 {
			font-size: 1.5rem;
			color: white;
		}

		.card p {
			font-size: clamp(0.85rem, 2vw, 0.95rem);
			word-wrap: break-word;
		}

		.card-container {
			display: flex;
			flex-wrap: wrap;
			justify-content: center;
			gap: 1rem;
			list-style: none;
			background-color: #242423;
			padding: 4em;
		}

		.card {
			position: relative;
			width: 20em;
			min-height: 15em;
			border: 0.2rem solid #508991;
			border-radius: 1rem;
			display: flex;
			flex-direction: column;
			justify-content: space-evenly;
			text-align: center;
			padding: 1em;
			margin-left: 1rem;
			margin-top: -2rem;
			transition:
				transform 0.3s ease,
				box-shadow 0.3s ease;
			box-shadow: 0 4px 10px rgba(0, 0, 0, 1);
		}

		@media (max-width: 768px) {
			.features {
				height: auto;
				margin-top: 3rem;
			}

			.features h2 {
				font-size: clamp(2rem, 8vw, 2.5rem);
			}

			.features p {
				font-size: clamp(0.8rem, 4vw, 1rem);
			}

			.card-container {
				padding: 1em;
			}

			.card {
				width: 90%;
				margin-left: 0;
				margin-top: 1rem;
			}
		}

		@media (max-width: 480px) {
			.features {
				margin-top: 2rem;
			}

			.features h2 {
				font-size: clamp(1.8rem, 9vw, 2.2rem);
			}

			.card {
				width: 100%;
				margin-left: 0;
				margin-top: 1rem;
			}
		}

		/* ---------------------------------------- */

		.workflow {
			padding: 6rem 10%;
			background-color: #242423;
			height: 100vh;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
		}

		.workflow h2 {
			font-size: 3rem;
			margin-bottom: 3rem;
			text-align: center;
			color: white;
			font-weight: bold;
		}

		.workflow-steps {
			display: grid;
			grid-template-columns: repeat(4, 1fr);
			gap: 3rem;
			margin-bottom: 7rem;
			max-width: 1000px;
		}

		.step {
			text-align: center;
			padding: 1rem;
			background: white;
			border: 0.2rem solid #508991;
			border-radius: 12px;
			transition:
				transform 0.3s ease,
				background-color 0.3s ease;
		}

		.step.active {
			background-color: #f0f9ff;
		}

		.step h3 {
			font-size: 1.5rem;
			margin-bottom: 1rem;
			color: #333;
		}

		.step p {
			font-size: 1rem;
			color: #666;
		}

		.step-icon {
			font-size: 3rem;
			margin-bottom: 1rem;
			color: #3b82f6;
		}

		.workflow img {
			position: absolute;
			z-index: 1;
			width: 100%;
			height: 25rem;
			width: 50rem;
			margin-bottom: 30rem;
			margin-left: 30%;
			opacity: 1; /* Adjust opacity as needed */
		}

		@media (max-width: 768px) {
			.workflow {
				padding: 4rem 5%;
			}

			.workflow-steps {
				grid-template-columns: 1fr 1fr; /* On smaller screens, make it 2 columns */
			}
		}

		/* ---------------------------------------- */

		.Dashboard {
			padding: 6rem 10%;
			background-color: #f9a03f;
			height: 100vh;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
		}

		.Dashboard h2 {
			font-size: 4rem;
			margin-bottom: 3rem;
			text-align: center;
			color: #333;
			font-weight: bold;
		}

		.Dashboard img {
			align-items: center;
			display: flex;
			justify-content: center;
			border-radius: 1rem;
			box-shadow: 0 4px 10px rgba(0, 0, 0, 2);
			height: 40rem;
			width: 80rem;
		}

		/* tablet */
		@media (max-width: 1024px) {
			.Dashboard {
				padding: 4rem 5%;
			}

			.Dashboard h2 {
				font-size: 3rem;
			}

			.Dashboard img {
				height: 25rem;
				width: 50rem;
				margin-left: 0;
			}
		}

		/* phone */
		@media (max-width: 768px) {
			.Dashboard {
				padding: 4rem 3%;
			}

			.Dashboard h2 {
				font-size: 2.5rem;
			}

			.Dashboard img {
				height: 20rem;
				width: 30rem;
				margin-left: 0;
				margin-top: 4rem;
			}
		}

		/* -------------------------------------------------------- */

		.cta-button {
			display: inline-block;
			padding: 0.8rem 2rem;
			color: white;
			text-decoration: none;
			background-color: #508991;
			/* accent colour */
		}

		footer {
			background: #242423;
			padding: 5rem 10% 2rem;
		}

		.footer-content {
			color: white;

			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
			gap: 3rem;
			margin-bottom: 3rem;
		}

		.footer-section h3 {
			font-size: 1.5rem;
			margin-bottom: 1.5rem;
		}

		.footer-section p {
			margin-bottom: 0.5rem;
		}

		.footer-bottom {
			text-align: center;
			padding-top: 2rem;
			border-top: 1px solid rgba(255, 255, 255, 0.1);
			color: #242423;
		}
	`;
	scrollToSection(sectionId: string) {
		const section = this.shadowRoot?.querySelector(`#${sectionId}`);
		if (section) {
			section.scrollIntoView({ behavior: 'smooth' });
		}
	}
	render() {
		return html`
            <header>
            <nav>
                <img  id="logo" src="/src/home/home_images/GI_logo-white.png" alt="Logo">
                <ul>
                    <li><button @click=${() => this.scrollToSection('our-features')} >Features</button></li>
                    <li><button @click=${() => this.scrollToSection('workflow')} >Workflow</button></li>
                    <li><button @click=${() => this.scrollToSection('Dashboard')}>Dashboard</button></li>
                </ul>
                <div class="hamburger" @click="${this.toggleMenu}">&#9776;</div>
            </nav>
            </header>

            <section class="hero">
            <section>
                <h1>Boost team productivity with seamless task management and progress tracking</h1>
                <p>Join the most effective platform in managing projects</p>
                <a class="get-started" href = 'http://localhost:8000/home'>Get Started</a></li>
            </section>
            </section>

            <main>
            <section id="our-features" class="features">
                <h2>Powerful Features</h2>
                <p>Everything you need to manage projects effectively and keep your team motivated.</p>

            <ul id="feature-cards" class="card-container">
                <li>
                    <article class="card">
                    <div class="step-icon">📋</div>
                        <h3>Task Management</h3>
                        <p>
                            Create, assign, and track tasks with ease. Set priorities, deadlines, and point values based
                            on
                            task difficulty.
                        </p>
                    </article>
                </li>

                <li>
                    <article class="card">
                    <div class="step-icon">🔄</div>
                        <h3>Gamified Workflow</h3>
                        <p>
                            Turn work into a game with points, leaderboards, and competitive nature. Keep your team
                            engaged
                            and
                            motivated.
                        </p>
                    </article>
                </li>
                <li>
                    <article class="card">
                    <div class="step-icon">🤲</div>
                        <h3>Task Grabbing</h3>
                        <p>
                            Team members can "grab" available tasks, moving them from the available pool to their
                            personal
                            workspace.
                        </p>
                    </article>
                </li>

                <li>
                    <article class="card">
                    <div class="step-icon">🔢</div>
                        <h3>Point System</h3>
                        <p>
                            Assign point values to tasks based on difficulty: Simple (5 points), Medium (10 points), and
                            Hard
                            (15 points).
                        </p>
                    </article>
                </li>

                <li>
                    <article class="card">
                    <div class="step-icon">🏆</div>
                        <h3>Leaderboards</h3>
                        <p>
                            Track individual and team performance with leaderboards showing accumulated points and
                            completed
                            tasks.
                        </p>
                    </article>
                </li>
                <li>
                    <article class="card">
                    <div class="step-icon">🌐</div>
                        <h3>Google Authentication</h3>
                        <p>
                            Secure login using Google authentication, making it easy for development teams to get
                            started.
                        </p>
                    </article>
                </li>
            </ul>
        </section>
            <section id="workflow" class="workflow">
                <h2>Workflow</h2>
                <section class="workflow-steps">
                <section class="step active">
                    <div class="step-icon">🔧</div>
                    <h3>1. Project Setup</h3>
                    <p>Team leads create projects and invite members</p>
                </section>

                <section class="step">
                    <div class="step-icon">📝</div>
                    <h3>2. Task Creation</h3>
                    <p>Add tasks with descriptions and deadlines</p>
                </section>

                <section class="step">
                    <div class="step-icon">🎯</div>
                    <h3>3. Task Assignment</h3>
                    <p>Team members grab available tasks</p>
                </section>

                <section class="step">
                    <div class="step-icon">📊</div>
                    <h3>4. Track Progress</h3>
                    <p>Monitor completion and review tasks</p>
                </section>
                </section>
            </section>

            <section id="Dashboard" class="Dashboard">
                <h2>Dashboard</h2>
                <img src="/src/home/home_images/dash.png" alt="Dashboard Image" class="dashboard-image">
            </section>
            </main>

            <footer>
            <section class="footer-content">
                <section class="footer-section">
                <h3>GrabIt</h3>
                <p>Gamified task management for modern teams</p>
                </section>
                <section class="footer-section">
                <h3>Contact</h3>
                <p>Email: nullidentifiers@grabit.co.za</p>
                <p>Phone: 011 484 7548</p>
                </section>
            </section>
            <section class="footer-bottom">
                <p>&copy; 2025 GrabIt. All rights reserved.</p>
            </section>


            </footer>
        `;
	}

	toggleMenu() {
		const menu = this.shadowRoot?.querySelector('nav ul');
		if (menu) {
			menu.classList.toggle('active');
		}
	}
}
