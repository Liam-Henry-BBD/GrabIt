import { CtLit, html, css, property, customElement } from '@conectate/ct-lit';

@customElement('leaderboard-sidebar')
export class LeaderboardSidebar extends CtLit {
	// Track the active tab as a string (weekly, monthly, allTime)
	@property({ type: String }) activeTab = 'weekly';
	@property({ type: Boolean }) isOpen = true;
	leaderboardData = {
		weekly: [
			{ id: 1, name: 'Alex Johnson', avatar: '/placeholder.svg', score: 87, role: 'Developer' },
			{ id: 2, name: 'Jamie Smith', avatar: '/placeholder.svg', score: 76, role: 'Designer' },
			{ id: 3, name: 'Taylor Brown', avatar: '/placeholder.svg', score: 68, role: 'Product Manager' },
			{ id: 4, name: 'Casey Wilson', avatar: '/placeholder.svg', score: 62, role: 'Developer' },
			{ id: 5, name: 'Morgan Lee', avatar: '/placeholder.svg', score: 54, role: 'QA Engineer' }
		],
		monthly: [
			{ id: 1, name: 'Alex Johnson', avatar: '/placeholder.svg', score: 315, role: 'Developer' },
			{ id: 2, name: 'Jamie Smith', avatar: '/placeholder.svg', score: 342, role: 'Designer' },
			{ id: 3, name: 'Morgan Lee', avatar: '/placeholder.svg', score: 287, role: 'QA Engineer' },
			{ id: 4, name: 'Taylor Brown', avatar: '/placeholder.svg', score: 256, role: 'Product Manager' },
			{ id: 5, name: 'Casey Wilson', avatar: '/placeholder.svg', score: 234, role: 'Developer' }
		],
		allTime: [
			{ id: 1, name: 'Alex Johnson', avatar: '/placeholder.svg', score: 1245, role: 'Developer' },
			{ id: 2, name: 'Jamie Smith', avatar: '/placeholder.svg', score: 1187, role: 'Designer' },
			{ id: 3, name: 'Taylor Brown', avatar: '/placeholder.svg', score: 956, role: 'Product Manager' },
			{ id: 4, name: 'Morgan Lee', avatar: '/placeholder.svg', score: 823, role: 'QA Engineer' },
			{ id: 5, name: 'Casey Wilson', avatar: '/placeholder.svg', score: 764, role: 'Developer' }
		]
	};

	static styles = css`
		:host {
			position: fixed;
			top: 4rem;
			right: 0;
			width: 0;
			height: calc(100vh - 4rem);
			overflow: hidden;
			background-color: #242423;
			transition: width 0.3s;
		}
		:host([open]) {
			width: 20rem;
		}
		.sidebar {
			display: flex;
			flex-direction: column;
			height: 100%;
		}
		.toggle-button {
			position: absolute;
			top: 1rem;
			left: -2.5rem;
			background-color: #242423;
			border: none;
			color: #f7f0f0;
			font-size: 1.5rem;
			cursor: pointer;
			z-index: 10;
		}
		.header {
			display: flex;
			align-items: center;
			gap: 1rem;
			padding: 1rem;
			border-bottom: 1px solid rgba(80, 137, 145, 0.3);
			background-color: #242423;
			color: #f7f0f0;
		}
		.tabs {
			display: flex;
			gap: 1rem;
			border-bottom: 1px solid rgba(80, 137, 145, 0.3);
			background-color: #242423;
		}
		.tab {
			color: #f7f0f0;
			padding: 0.5rem;
			cursor: pointer;
			border: none;
			background: none;
		}
		.tab.active {
			color: #f9a03f;
		}
		.list {
			padding: 1rem;
			overflow-y: auto;
			flex-grow: 1;
		}
		.item {
			display: flex;
			gap: 1rem;
			align-items: center;
			padding: 0.5rem;
			background-color: #242423;
			margin-bottom: 0.5rem;
			border-radius: 0.5rem;
		}
		.avatar {
			width: 2rem;
			height: 2rem;
			border-radius: 50%;
			background-color: #508991;
			text-align: center;
			display: flex;
			justify-content: center;
			align-items: center;
			color: #f7f0f0;
		}
		.rank {
			font-size: 1.5rem;
			color: #f7f0f0;
		}
		.progress-bar {
			width: 100%;
			background-color: #508991;
			height: 5px;
			border-radius: 5px;
		}
		.progress {
			height: 100%;
			background-color: #f9a03f;
		}
		.footer {
			padding: 1rem;
			border-top: 1px solid rgba(80, 137, 145, 0.3);
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
	`;

	toggleSidebar() {
		this.isOpen = !this.isOpen;
	}

	// Handle tab selection change
	selectTab(tab: string) {
		this.activeTab = tab;
	}

	render() {
		return html`
			<button class="toggle-button" @click="${this.toggleSidebar}">
				${this.isOpen
					? html`<svg
							xmlns="http://www.w3.org/2000/svg"
							width="24"
							height="24"
							fill="none"
							stroke="currentColor"
							stroke-width="2"
							viewBox="0 0 24 24"
							class="feather feather-chevron-right"
						>
							<path d="M9 18l6-6-6-6"></path>
						</svg>`
					: html`<svg
							xmlns="http://www.w3.org/2000/svg"
							width="24"
							height="24"
							fill="none"
							stroke="currentColor"
							stroke-width="2"
							viewBox="0 0 24 24"
							class="feather feather-chevron-left"
						>
							<path d="M15 18l-6-6 6-6"></path>
						</svg>`}
			</button>
			<div class="sidebar ${this.isOpen ? 'open' : ''}">
				<div class="header">
					<!-- Trophy Icon -->
					<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24" class="feather feather-trophy">
						<path d="M12 2v4M4 10h16M7 18h10M8 22h8M5 10v7a5 5 0 0 0 10 0V10"></path>
					</svg>
					<h2>Leaderboard</h2>
				</div>
				<div class="tabs">
					<button class="tab ${this.activeTab === 'weekly' ? 'active' : ''}" @click="${() => this.selectTab('weekly')}">Weekly</button>
					<button class="tab ${this.activeTab === 'monthly' ? 'active' : ''}" @click="${() => this.selectTab('monthly')}">Monthly</button>
					<button class="tab ${this.activeTab === 'allTime' ? 'active' : ''}" @click="${() => this.selectTab('allTime')}">All Time</button>
				</div>
				<div class="list">${this.renderLeaderboardList()}</div>
				<div class="footer">
					<!-- Users Icon -->
					<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24" class="feather feather-users">
						<path d="M12 14c3.866 0 7 2.686 7 6H5c0-3.314 3.134-6 7-6zM12 12c2.209 0 4-1.791 4-4S14.209 4 12 4s-4 1.791-4 4 1.791 4 4 4z"></path>
					</svg>
					<span>5 Team Members</span>
				</div>
			</div>
		`;
	}

	renderLeaderboardList() {
		const data = this.leaderboardData[this.activeTab] || this.leaderboardData['weekly']; // default to weekly if not selected
		const maxScore = Math.max(...data.map(item => item.score));
		return html`
			${data.map(
				(item, index) => html`
					<div class="item">
						<div class="rank">${index + 1}</div>
						<div class="avatar">${item.name[0]}</div>
						<div class="text">
							<div>${item.name}</div>
							<div class="text-xs">${item.role}</div>
						</div>
						<div class="progress-bar">
							<div class="progress" style="width: ${(item.score / maxScore) * 100}%"></div>
						</div>
						<div class="score">${item.score} pts</div>
					</div>
				`
			)}
		`;
	}
}
