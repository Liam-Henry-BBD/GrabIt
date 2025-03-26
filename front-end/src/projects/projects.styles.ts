import { css } from 'lit';

export const createProjectStyles = css`
	:host {
		display: block;
		max-width: 600px;
		margin: auto;
		padding: 20px;
		font-family: sans-serif;
		background-color: #242423;
		border-radius: 10px;
	}

	.create-project-header {
			display: flex;
			justify-content: space-between;
			align-items: center;
			background-color: #242423;
			width: 100vw;
			max-width: 100%;
			position: fixed;
			top: 0;
			right: 0;
			height: 5rem;
			z-index: 1000;
			overflow: hidden;
		}

		.create-project-header img {
			margin-top: 1rem;
			height: 10rem;
			width: 10rem;
		}

	h1 {
		color: #f9a03f;
		margin-top: 5rem;
		text-align: center;
	}

	h2 {
		font-size: 1rem;
		color: #f7f0f0;
		padding-top: 20px;
	}

	input,
	textarea {
		width: calc(100% - 20px);
		margin-top: 10px;
		padding: 10px;
		border-radius: 5px;
		border: 1px solid #508991;
		background-color: #242423;
		color: #f7f0f0;
	}

	button,
	a {
		background-color: #f9a03f;
		color: #242423;
		cursor: pointer;
		border: none;
		padding: 10px 20px;
		border-radius: 5px;
		margin-top: 10px;
		text-decoration: none;
	}

	button:hover {
		background-color: #508991;
	}

	.collaborator-container {
		display: flex;
		width: calc(100% - 2px);
		gap: 10px;
		align-items: center;
		margin-top: 10px;
		margin-bottom: 20px;
	}

	.collaborator-list {
		background-color: #508991;
		color: #f7f0f0;
		border-radius: 5px;
		margin-top: 10px;
		padding: 10px;
	}
`;

export const projectOverviewStyles = css`
	:host {
		display: block;
		max-width: 800px;
		margin: auto;
		padding: 20px;
		font-family: sans-serif;
		background-color: #242423;
		color: #f7f0f0;
		border-radius: 8px;
	}

	.header {
			display: flex;
			justify-content: space-between;
			align-items: center;
			background-color: #242423;
			width: 100vw;
			max-width: 100%;
			position: fixed;
			top: 0;
			right: 0;
			height: 5rem;
			z-index: 1000;
			overflow: hidden;
		}

	.header img {
			left: 2rem;
			height: 10rem;
			width: 10rem;
	}

	.project-header {
		background-color: #50899120;
		padding: 20px;
		border-radius: 8px;
		margin-top: calc(4rem + 20px); 
	}

	.project-header h1 {
		font-size: 2rem;
		color: #f9a03f;
	}

	.project-header p {
		color: #f7f0f0;
		margin-top: 8px;
	}

	.dates-container {
		display: flex;
		justify-content: space-between;
		margin-top: 30px;
	}

	.collaborators-container {
		display: flex;
		flex-wrap: wrap;
		gap: 10px;
		margin-top: 15px;
	}

	.collaborator-badge {
		width: 40px;
		height: 40px;
		display: flex;
		align-items: center;
		justify-content: center;
		border-radius: 50%;
		background-color: #508991;
		color: #f7f0f0;
		font-weight: bold;
		text-transform: uppercase;
		font-size: 14px;
		cursor: default;
	}

	.collaborator-badge:hover {
		background-color: #f9a03f;
		color: #242423;
	}

	.task-list-container {
		margin-top: 20px;
	}

	.task-list-title {
		font-size: 1.5rem;
		font-weight: bold;
		margin-bottom: 10px;
	}

	.task-list {
		display: flex;
		flex-direction: column;
		gap: 10px;
	}

	.task {
		border-radius: 8px;
		padding: 15px;
		background-color: rgba(80, 137, 145, 0.1);
		display: flex;
		flex-direction: column;
	}

	.task .header {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.task .remove-btn {
		background-color: #f9a03f;
		color: #fff;
		border: none;
		padding: 6px 12px;
		border-radius: 4px;
		cursor: pointer;
		align-self: flex-end;
	}

	.task .remove-btn:hover {
		background-color: #508991;
	}

	button {
		background-color: #f9a03f;
		color: #f7f0f0;
		border: none;
		border-radius: 5px;
		padding: 8px 16px;
		cursor: pointer;
		margin-bottom: 10px;
	}

	button:hover {
		background-color: #508991;
	}

	.no-tasks {
		text-align: center;
		color: #f7f0f0;
		padding: 60px;
		border: 2px dashed #f9a03fcc;
		border-radius: 8px;
		background-color: rgba(80, 137, 145, 0.1);
	}

	.task-list-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.task-list-title {
		margin: 0;
		margin-bottom: 10px;
	}
`;
