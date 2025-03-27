import { css } from 'lit';

export const projectAppStyles = css`
	.project-head {
		display: flex;
		flex-direction: row;
	}

	/* New Task Button */
	.new-project-body {
		background-color: #F9A03F;
		color: #242423	;
		width: 100%;
	}

	.new-project-body:hover {
		background-color: #e68900;
	}

	/* Collaborator Button */
	.new-collaborator {
		background-color: #2c2c2c;
		color: #ffffff;
	}

	.new-collaborator:hover {
		background-color: #444444;
	}

	/* Leaderboard Button */
	.leaderboard-button {
		background-color: #4a676a75;
		color: #ffffff;
	}

	.leaderboard-button:hover {
		background-color: #F9A03F;
	}
	a {
		text-decoration: none;
		text-align: center;
	}
	/* Task Board Columns */
	.columns {
		display: flex;
		gap: 1rem;
		margin-bottom: 2rem;
	}

	/* Button Styles for Main Section */
	.new-project-body,
	.new-collaborator,
	.leaderboard-button {
		padding: 1rem 0.5rem;
		font-size: 0.8rem;
		width: 150px;
		border: none;
		border-radius: 5px;
		cursor: pointer;
		word-wrap: nowrap;
		transition: background-color 0.2s;
	}

	/* New Project, Collaborators, and Leaderboard Button Placement */
	.article-buttons {
		padding: 1rem;
		display: flex;
		gap: 1rem;
		align-items: center;
	}

	.column {
		flex: 1;
		border-radius: 5px;
		height: 28rem;
		/* overflow: hidden;  */
	}

	/* Optional: Add a max-height to prevent the column from growing too tall */
	.column {
		max-height: 80vh;
	}

	.column .column-space {
		margin-block: 1rem;
		font-size: 1rem;
		color: #ffffff;
		background-color: #68666626;
		width: 100%;
		border-radius: 5px;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	.project-desc {
		width: 60%;
	}
	.column-space span {
		margin: 0.5rem;
		font-weight: bold;
	}

    @media (max-width: 1024px) {
			main {
				overflow-y: auto;
			}
			.columns {
				flex-direction: column;
				gap: 1rem;
			}
			.column > section {
				height: 100%;
				width: 100%;
				overflow-y: hidden;
				overflow-x: auto;
				display: flex;
				gap: 1rem;
				flex-direction: row;
			}
			.project-card {
				background-color: #1e1e1e; 
				color: #ffffff; 
				padding: 1rem;
				border-radius: 5px;
				margin-bottom: 1rem;
				min-width: 12rem;
				box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
			}
            .project-head {
                flex-direction: column;
            }

            .project-details {
                width: 100%;
            }
            .project-details * {
                width: 100%;
            }
		}

	.column .column-space .task-count {
		background-color: #4a9191a4;
		padding: 0.5rem;
		height: 1rem;
		width: 1rem;
		display: flex;
		justify-content: center;
		align-items: center;
		border-radius: 50%;
	}
	.column .task-count {
		font-size: 1.1rem;
		color: #ffffff;
	}
	.column > section {
		height: 100%;
		width: 100%;
		overflow-y: auto;
		-ms-overflow-style: none;
		scrollbar-width: none;
	}

	.project-details {
		display: flex;
		flex-direction: column;
		width: 80%;
	}

	.project-details h1 {
		font-size: 2rem;
		margin: 0;
		margin-top: 1rem;
	}

	.column .cards-container::-webkit-scrollbar {
		display: none;
	}
`;
