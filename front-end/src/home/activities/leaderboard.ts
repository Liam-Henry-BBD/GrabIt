import { html, css, LitElement } from 'lit';
import { customElement, state } from 'lit/decorators.js';
import { Award, ChevronLeft, ChevronRight, Trophy, Users } from 'lucide-literal';

const leaderboardData = {
  weekly: [
    { id: 1, name: "Alex Johnson", avatar: "/placeholder.svg?height=40&width=40", score: 87, completedTasks: 12, role: "Developer" },
    { id: 2, name: "Jamie Smith", avatar: "/placeholder.svg?height=40&width=40", score: 76, completedTasks: 9, role: "Designer" },
    { id: 3, name: "Taylor Brown", avatar: "/placeholder.svg?height=40&width=40", score: 68, completedTasks: 8, role: "Product Manager" },
    { id: 4, name: "Casey Wilson", avatar: "/placeholder.svg?height=40&width=40", score: 62, completedTasks: 7, role: "Developer" },
    { id: 5, name: "Morgan Lee", avatar: "/placeholder.svg?height=40&width=40", score: 54, completedTasks: 6, role: "QA Engineer" },
  ],
  monthly: [
    { id: 2, name: "Jamie Smith", avatar: "/placeholder.svg?height=40&width=40", score: 342, completedTasks: 41, role: "Designer" },
    { id: 1, name: "Alex Johnson", avatar: "/placeholder.svg?height=40&width=40", score: 315, completedTasks: 38, role: "Developer" },
    { id: 5, name: "Morgan Lee", avatar: "/placeholder.svg?height=40&width=40", score: 287, completedTasks: 34, role: "QA Engineer" },
    { id: 3, name: "Taylor Brown", avatar: "/placeholder.svg?height=40&width=40", score: 256, completedTasks: 31, role: "Product Manager" },
    { id: 4, name: "Casey Wilson", avatar: "/placeholder.svg?height=40&width=40", score: 234, completedTasks: 28, role: "Developer" },
  ],
  allTime: [
    { id: 1, name: "Alex Johnson", avatar: "/placeholder.svg?height=40&width=40", score: 1245, completedTasks: 149, role: "Developer" },
    { id: 2, name: "Jamie Smith", avatar: "/placeholder.svg?height=40&width=40", score: 1187, completedTasks: 142, role: "Designer" },
    { id: 3, name: "Taylor Brown", avatar: "/placeholder.svg?height=40&width=40", score: 956, completedTasks: 115, role: "Product Manager" },
    { id: 5, name: "Morgan Lee", avatar: "/placeholder.svg?height=40&width=40", score: 823, completedTasks: 98, role: "QA Engineer" },
    { id: 4, name: "Casey Wilson", avatar: "/placeholder.svg?height=40&width=40", score: 764, completedTasks: 92, role: "Developer" },
  ]
};

@customElement('leaderboard-sidebar')
export class LeaderboardSidebar extends LitElement {
  @state() isOpen = false;
  @state() activeTab = "weekly";

  static styles = css`
	.leaderboard-sidebar {`;

  render() {
    return html`
      <div class="leaderboard-sidebar">
        <!-- Toggle button -->
        <button class="toggle-button" @click="${this.toggleSidebar}">
          ${this.isOpen
            ? html`<${ChevronRight} style="width: 1.25rem; height: 1.25rem;" />`
            : html`<${ChevronLeft} style="width: 1.25rem; height: 1.25rem;" />`}
        </button>

        <!-- Sidebar content -->
        <div class="sidebar-container">
          <div class="sidebar-inner">
            <div class="sidebar-header">
              <div class="sidebar-title">
                <${Trophy} style="width: 1.25rem; height: 1.25rem; color: var(--primary);" />
                <h2 class="sidebar-title-text">Leaderboard</h2>
              </div>
            </div>

            <div class="tabs">
              <div class="tabs-header">
                <div class="tabs-list">
                  <button class="tab" ?active="${this.activeTab === 'weekly'}" @click="${() => this.setActiveTab('weekly')}">
                    Weekly
                  </button>
                  <button class="tab" ?active="${this.activeTab === 'monthly'}" @click="${() => this.setActiveTab('monthly')}">
                    Monthly
                  </button>
                  <button class="tab" ?active="${this.activeTab === 'allTime'}" @click="${() => this.setActiveTab('allTime')}">
                    All Time
                  </button>
                </div>
              </div>

              <div class="tab-content">
                ${this.renderLeaderboardList(leaderboardData[this.activeTab])}
              </div>
            </div>

            <div class="sidebar-footer">
              <div class="footer-content">
                <div class="team-info">
                  <${Users} style="width: 1rem; height: 1rem;" />
                  <span>5 Team Members</span>
                </div>
                <button class="view-all-button">View All</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    `;
  }

  toggleSidebar() {
    this.isOpen = !this.isOpen;
  }

  setActiveTab(tab: string) {
    this.activeTab = tab;
  }

  renderLeaderboardList(data: any[]) {
    const maxScore = Math.max(...data.map((item) => item.score));

    return html`
      <div class="leaderboard-list">
        ${data.map(
          (item, index) => html`
            <div key="${item.id}" class="leaderboard-item ${index === 0 ? 'top' : ''}">
              ${index < 3
                ? html`
                    <div class="rank-indicator">
                      <${Award}
                        style="width: 1.25rem; height: 1.25rem; color: ${
                          index === 0
                            ? 'var(--primary)'
                            : index === 1
                            ? 'rgba(247, 240, 240, 0.8)'
                            : 'rgba(249, 160, 63, 0.6)'
                        }"
                      />
                    </div>
                  `
                : ''}
              <div class="user-info">
                <div class="rank-number">${index + 1}</div>
                <div class="user-avatar">
                  <img src="${item.avatar || '/placeholder.svg'}" alt="${item.name}" />
                </div>
                <div class="user-details">
                  <div class="user-name">${item.name}</div>
                  <div class="user-role">${item.role}</div>
                </div>
              </div>

              <div class="score-info">
                <div class="score-label">
                  <span>Score</span>
                  <span>${item.score} pts</span>
                </div>
                <div class="score-bar">
                  <div
                    class="score-indicator"
                    style="width: ${(item.score / maxScore) * 100}%"
                  ></div>
                </div>
                <div class="tasks-completed">${item.completedTasks} tasks completed</div>
              </div>
            </div>
          `
        )}
      </div>
    `;
  }
}
