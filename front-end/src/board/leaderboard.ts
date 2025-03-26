import { CtLit, customElement, html, state } from '@conectate/ct-lit';

//Components
import '../components/header';

//CSS
import { leaderboardStyles } from './leaderboard.styles';
import { getLeaderboard } from '../services/leaderboard.service';
import { Router, RouterLocation } from '@vaadin/router';
import sendRequest from '../services/requests';

@customElement('leaderboard-app')
export class Leaderboard extends CtLit {
	@state() projectID: number | null = null;
	@state() listOfUserPositionCard: any;
	@state() projectDetails: any;

	onBeforeEnter(location: RouterLocation) {
		this.projectID = Number(location.params['projectID']);
	}

	static styles = leaderboardStyles;

	connectedCallback(): void {
		super.connectedCallback();
		if (this.projectID) {
			getLeaderboard(this.projectID).then(data => {
				this.listOfUserPositionCard = this.createPositionCardComponents(data);
			});
			sendRequest(`/projects/${this.projectID}`).then(data => (this.projectDetails = data));
		}
	}

	createPositionCardComponents(listOfColaboratorsWithScores: any[]) {
		const firstPlaceScore = listOfColaboratorsWithScores[0].totalScore;
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
		return html`<auth-router>
			<main class="leaderboard__container">
				<header-app> </header-app>
				<button @click=${() => (window.location.href = '/')}>back to Project</button>
				<h1>
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
						class="lucide lucide-trophy h-5 w-5 text-[#b6660f]"
					>
						<path d="M6 9H4.5a2.5 2.5 0 0 1 0-5H6"></path>
						<path d="M18 9h1.5a2.5 2.5 0 0 0 0-5H18"></path>
						<path d="M4 22h16"></path>
						<path d="M10 14.66V17c0 .55-.47.98-.97 1.21C7.85 18.75 7 20.24 7 22"></path>
						<path d="M14 14.66V17c0 .55.47.98.97 1.21C16.15 18.75 17 20.24 17 22"></path>
						<path d="M18 2H6v7a6 6 0 0 0 12 0V2Z"></path>
					</svg>
					Leaderboard
				</h1>
				<h2>Project name: <em>${this.projectDetails.projectName}</em></h2>
				<p>Project description: <em>${this.projectDetails.projectDescription}</em></p>

				<section class="leaderboard">${this.listOfUserPositionCard}</section>
			</main>
		</auth-router> `;
	}
}
