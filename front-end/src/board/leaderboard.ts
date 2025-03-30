import { CtLit, customElement, html, state } from '@conectate/ct-lit';
import '../components/header';
import { leaderboardStyles } from './leaderboard.styles';
import { getLeaderboard } from '../services/leaderboard.service';
import { Router, RouterLocation } from '@vaadin/router';
import sendRequest from '../services/requests';

@customElement('leaderboard-app')
export class Leaderboard extends CtLit {
	@state() projectID: number | null = null;
	@state() leaderboardData: any[] = [];
	@state() projectDetails: any;
	@state() searchValue: string = '';

	onBeforeEnter(location: RouterLocation) {
		this.projectID = Number(location.params['projectID']);
	}

	static styles = leaderboardStyles;

	connectedCallback(): void {
		super.connectedCallback();
		if (this.projectID) {
			getLeaderboard(this.projectID).then(data => {
				this.leaderboardData = data; 
			});
			sendRequest(`/projects/${this.projectID}`).then(data => (this.projectDetails = data));
		}
	}

	get filteredLeaderboard() {
		if (!this.searchValue) return [];
		return this.leaderboardData.filter(user => user.githubID.toLowerCase().includes(this.searchValue.toLowerCase()));
	}

    getSVG() {
        return html`<svg
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
                class="lucide lucide-award h-5 w-5 text-[#F9A03F]"
            >
                <path d="m15.477 12.89 1.515 8.526a.5.5 0 0 1-.81.47l-3.58-2.687a1 1 0 0 0-1.197 0l-3.586 2.686a.5.5 0 0 1-.81-.469l1.514-8.526"></path>
                <circle cx="12" cy="8" r="6"></circle>
            </svg>`;
    }

	createPositionCardComponents(listOfColaboratorsWithScores: any[]) {
		if (listOfColaboratorsWithScores.length === 0) return html`<p>No results found</p>`;

		const firstPlaceScore = listOfColaboratorsWithScores[0]?.totalScore || 1;
		
		return listOfColaboratorsWithScores.map(user => {
			const progressValue = (user.totalScore / firstPlaceScore) * 100;
			return html`
				<article class="leaderboard__user">
					<h3>
						<span class="position">${user.position}.</span>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							width="24"
							height="24"
							viewBox="0 0 24 24"
							fill="none"
							stroke="currentColor"
							stroke-width="2"
							stroke-linecap="round"
							stroke-linejoin="round"
							class="lucide lucide-award h-5 w-5 text-[#F9A03F]"
						>
							<path d="m15.477 12.89 1.515 8.526a.5.5 0 0 1-.81.47l-3.58-2.687a1 1 0 0 0-1.197 0l-3.586 2.686a.5.5 0 0 1-.81-.469l1.514-8.526"></path>
							<circle cx="12" cy="8" r="6"></circle>
						</svg>
						<span class="user_name"><em>${user.githubID}</em></span>
					</h3>
					<div>
						<p>Score: ${user.totalScore}</p>
						<progress value=${user.totalScore} max=${firstPlaceScore}>${progressValue}%</progress>
					</div>
				</article>
			`;
		});
	}

	render() {
		return html`
		    <header>
				<section class="header">
				<img id="logo" src="/src/home/home_images/GI_logo-white.png" alt="Logo" @click=${() => window.location.href = 'http://localhost:8000/home'}>
				${this.leaderboardData.length > 0 ? html`<input type="text" placeholder="Search user" @input=${(e: Event) => (this.searchValue = (e.target as HTMLInputElement).value)} />` : html``}

			</section>
            </header>

			<auth-router>
				<main class="leaderboard__container">
                    <section class="main-content">
                    <a href= 'http://localhost:8000/home/${this.projectID}'> Back</a>

                    <h1> Leaderboard</h1>


                    <h2>${this.projectDetails?.projectName || 'Loading...'}</h2>
                    <article>
                        <p>${this.projectDetails?.projectDescription || 'Loading...'}</p>
                    </article>
                    </section>

                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>Pos</th>
                                <th>Name</th>
                                <th>Score</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${
                                this.filteredLeaderboard.map(board => {
                                    return html`
                                    <tr>
                                        <td> <section>${board.position === 1 ? html`🏆` : this.getSVG()} ${board.position}</section> </td>
                                        <td>${board.githubID}</td>
                                        <td>${board.totalScore}</td>
                                     </tr>`;
                                    })
                                }
                        </tbody>
                    </table>

					<!-- <section class="leaderboard">${this.createPositionCardComponents(this.filteredLeaderboard)}</section> -->
				</main>
			</auth-router>
		`;
	}
}
