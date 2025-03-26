import { css } from 'lit';

export const homeStyles = css`
		:host {
			background-color: #242423;
			color: #fff;
			display: flex;
			height: 100vh;
			width: 100%;
			overflow: hidden;
		}

		.dashboard {
			margin-top: 5rem; 
			height: calc(100vh - 5rem);
			width: 100vw;
			overflow: hidden;
			display: flex;
		}

		/* Sidebar Styling */
		.sidebar {
			display: flex;
			flex-direction: column;
            justify-content: start;
			background-color: #242423;
			width: 17rem;
			padding: 2rem 1rem;
			height: 100vh;
			border-right: 1px solid rgba(255, 255, 255, 0.1);
			transition: transform 0.3s ease-in-out;
			overflow-y: auto;
			box-sizing: border-box;

		}



	/* Sidebar Headings */
	.sidebar h2 {
		font-weight: 500;
		font-size: 1.2rem;
		text-transform: capitalize;
		color: #ffffff;
		padding: 0 0.75rem;
	}

	/* Sidebar Header */
	.sidebar-header {
		display: flex;
		flex-direction: column;
		gap: 1rem;
	}

		/* Search Input */
		.sidebar-search {
			width: 100%;
			padding: 0.75rem;
			font-size: 1rem;
			border: none;
			border-radius: 5px;
			background-color: transparent;
			border: 1px solid rgba(80, 137, 145, 0.2);
			color: #fff;
			margin-bottom: 0.15rem;
		}
		/* New Project Button */
		.new-project {
			width: 100%;
			padding: 0.75rem;
			font-size: 1rem;
			font-weight: bold;
			border: none;
			border-radius: 5px;
			background-color: #F9A03F; /* Corrected color */
			color: #ffffff;
			cursor: pointer;
			transition: background-color 0.2s ease-in-out;
			margin-bottom: .5rem; /* Added margin for separation */
		}

	.project-header {
		display: flex;
		align-items: center;
		gap: 5px;
	}

	.project-header svg {
		color: #7c7c7b;
		height: 15px;
	}

	.new-project:hover {
		background-color: #e68900;
	}

	/* Separator */
	.separator {
		width: 100%;
		height: 1px;
		background: rgba(255, 255, 255, 0.2);
		margin: 1rem 0;
		border: none;
	}

	/* Project List */
	ul {
		list-style-type: none;
		padding: 0;
		margin: 0;
	}

	/* Project Items */
	.project-item {
		display: flex;
		align-items: center;
		gap: 0.75rem;
		padding: 0.3rem 0.75rem;
		font-size: 1rem;
		font-weight: 500;
		border-radius: 5px;
		cursor: pointer;
		transition: background-color 0.2s;
		color: #ffffff;
	}

	.project-item:hover {
		background-color: rgba(255, 255, 255, 0.1);
	}

	/* Project Icons */
	.project-item .project-icon {
		font-size: 1.2rem;
	}
	/* Sidebar Collapsible on Smaller Screens */
	@media (max-width: 1024px) {
		.sidebar {
			position: absolute;
			transform: translateX(-100%);
			transition: transform 0.3s ease-in-out;
		}

		.sidebar.open {
			transform: translateX(0);
		}
	}

	main {
		flex: 1;
		padding: 0rem 1rem;
		background-color: #2e2e2e;
		color: #ffffff;
		overflow-y: scroll;
		box-sizing: border-box;
	}

	/* Main Section Header */
	h1 {
		font-size: 2rem;
		color: #ff9800;
		margin-bottom: 1rem;
	}

	/* Paragraph Styling */
	p {
		font-size: 1.2rem;
		color: #b3b3b3;
		margin-bottom: 2rem;
	}

	/* Task Board Columns */
	.columns {
		display: flex;
		gap: 1rem;
		margin-bottom: 2rem;
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

	.column-space span {
		margin: 0.5rem;
		font-weight: bold;
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

	.column .cards-container::-webkit-scrollbar {
		display: none;
	}
	/* No Tasks Message */
	.no-tasks {
		color: #cccccc;
		font-size: 1.1rem;
		text-align: center;
	}

	.task-description,
	.due-date,
	.points {
		font-size: 1rem;
		color: #b3b3b3;
	}

	.points {
		font-weight: bold;
		color: #ff9800;
	}

	.project-item a {
		text-decoration: none;
		color: #ffffff;
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
	}
`;
