import { CtLit, html, property, customElement, css } from '@conectate/ct-lit';

@customElement('landing-app')
export class LandingApp extends CtLit{
 
    static styles = css`
        @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

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
                padding: 1rem 10%;
                background-color: #242423;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 1);
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                height: 5rem;
                z-index:1000;
            }

            img {
                height: 9rem;
                width: 9rem;
                position: absolute;
                bottom: 1;
                left: 1rem;
            }

            /* ---------------------------------------- */

            nav ul {
                display: flex;
                list-style: none;
                gap: 4rem;
            }

            nav a {
                color: white;
                font-weight: 500;
                font-size: 1.2rem;
                text-decoration: none;
            }

            nav a:hover {
                color: #F9A03F;
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
                color: #F7F0F0;
                border: none;
                padding: 0.5rem 1rem;
                font-size: 1em;
                font-weight: 500;
                cursor: pointer;
            }

            .auth-links .sign-up:hover {
                color: #F9A03F;
            }

            .auth-links .sign-in {
                background-color: #F9A03F;
                color: #242423;
                border: none;
                padding: 0.5rem 1rem;
                font-size: 1rem;
                font-weight: 500;
                cursor: pointer;
                border-radius: 0.5rem;
            }

            .auth-links .sign-in:hover {
                background-color: #F9A03F;
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
                background: linear-gradient(to bottom, white, #242423);
                height: 100vh;
                padding-inline: 10%;
                box-shadow: 0 2px 10px rgb(47, 46, 46);
            }

            .hero section {
                width: 90%;
                height: 100%;
                display:flex;
                flex-direction: column;
                align-items: center;

            }

            .hero h1 {
                font-size: 5rem;
                font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
                color: #242423;
                letter-spacing: 0.9px;
                line-height: 1.1;
                margin-top: 1.5rem;
                align-items: center;
                text-align: center;
            }

            .hero p {
                font-size: 1.3rem;
                font-weight: 500;
                font-family: 'Poppins', sans-serif;
                color: #242423;
                margin-top: 1rem;
                text-align: center;
            }

            .get-started {
                background-color: #F9A03F;
                color: #242423;
                border: none;
                padding: 0.5rem 1rem;
                font-size: 1rem;
                font-weight: 500;
                cursor: pointer;
                border-radius: 0.5rem;

            }

            /* ---------------------------------------- */

            .features h2 {
                font-size: 3rem;
                font-weight: bold;
                margin-bottom: 1rem;
                text-align: center;
                color: #242423;

            }

            .features {
                padding: 4rem;
                background: linear-gradient(to bottom, white, white); 
                height: 90vh;
                grid-template-columns: repeat(2, 2fr); 
                margin-bottom: 3rem ;
            }

            .feature-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
                gap: 2rem;
                margin-top: 1rem;
                margin-left: 28rem;
            }

            .feature-card {
                padding: 1rem;
                border: 0.2rem solid #508991;
                width: 15rem;
                
            }

            .feature-card h3 {
                color: #242423;
                font-size: 1.5rem;
                margin-bottom: 1rem;
            }



            .features-image {
                max-width: 100%; 
                display: flex; 
                justify-content: center; 
                align-items: center; 
                margin: 0; 
            }

            .features-image img {
                width: 100%; 
                height: 25rem;
                width: 25rem;
                margin-bottom: 35rem;
                margin-left: 6%;
                background: inherit; 
                filter: blur(5%); 
                z-index: 1; 
                mask-image: radial-gradient(circle, white 70%, transparent 100%);
            }
            
            @media (max-width: 1024px) {
            .features-image img {
                display: none;
            }
        }

            @media (max-width: 768px) {
                .features {
                    padding: 4rem 5%;
                }

                .feature-grid {
                    grid-template-columns: 1fr 1fr; /* On smaller screens, make it 2 columns */
                }
            }

            /* ---------------------------------------- */

            .workflow {
                padding: 6rem 10%;
                background: linear-gradient(to bottom, white, white);
                height: 80vh; 
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
            }

            .workflow h2 {
                font-size: 3rem;
                margin-bottom: 8rem;
                text-align: center;
                color: #333;
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
                transition: transform 0.3s ease, background-color 0.3s ease;
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
                background: linear-gradient(to bottom, #F9A03F, white);
                height: 100vh;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
            }

            .Dashboard h2 {
                font-size: 4rem;
                margin-bottom: 35rem;
                text-align: center;
                color: #333;
                font-weight: bold;
            }



            .Dashboard img {
                height: 30rem;
                width: 60rem;
                margin-top: 8rem;
                margin-bottom: 5rem;
                margin-left: 18%;
                align-items: center;
                display: flex;
                justify-content: center;
                border-radius: 1rem;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 2);

            }

            /* -------------------------------------------------------- */

            .cta-button {
                display: inline-block;
                padding: .8rem 2rem;
                color: white;
                text-decoration: none;
                background-color: #508991;
                /* accent colour */
            }

            footer {
                background: linear-gradient(to bottom, white, #F9A03F);
                padding: 5rem 10% 2rem;
            }

            .footer-content {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 3rem;
                margin-bottom: 3rem;
            }

            .footer-section h3 {
                color: #242423;
                font-size: 1.5rem;
                margin-bottom: 1.5rem;
            }

            .footer-section p {
                color: #242423;
                margin-bottom: 0.5rem;
            }

            .footer-bottom {
                text-align: center;
                padding-top: 2rem;
                border-top: 1px solid rgba(255, 255, 255, 0.1);
                color: #242423;
            }
             `;
        

    render() {
        return html`
            <header>
            <nav>
                <img src="/src/home/home_images/GI_logo-white.png" alt="Logo">
                <ul>
                <li><a href="#features">Features</a></li>
                <li><a href="#workflow">Workflow</a></li>
                <li><a href="#points">Dashboard</a></li>
                <li><a href="#contact">Contact</a></li>
                </ul>
                <ul class="auth-links">
                <li><button class="sign-up">Sign Up</button></li>
                <li><button class="sign-in" @click="${this.navigateToLogin}">Sign In</button></li>
                </ul>
            </nav>
            </header>

            <section class="hero">
            <section>
                <h1>Boost team productivity with seamless task management and progress tracking</h1>
                <p>Join the most effective platform in managing projects</p>
                <li><button class="get-started" @click="${this.navigateToLogin}">Get Started</button></li>
            </section>
            </section>

            <main>
            <section id="features" class="features">
                <h2>Key Features</h2>
                <div class="feature-grid">
                <article class="feature-card">
                    <h3>Task Management</h3>
                    <p>Create, assign, and track tasks with ease. Monitor progress in real-time.</p>
                </article>

                <article class="feature-card">
                    <h3>Point System</h3>
                    <p>Gamified approach with points based on task difficulty. Keep team members engaged.</p>
                </article>

                <article class="feature-card">
                    <h3>Team Leaderboard</h3>
                    <p>Track performance with individual and team scores. Foster healthy competition.</p>
                </article>

                <article class="feature-card">
                    <h3>GitHub Authentication</h3>
                    <p>Seamless authentication through GitHub. Quick and secure access.</p>
                </article>

                <figure class="features-image">
                    <img src="./src/home/home_images/777.jpg" alt="Features Image" class="features-image">
                </figure>
                </div>
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

        private navigateToLogin() {
        window.location.href = 'http://localhost:3004/login';
        }
        }

    
            