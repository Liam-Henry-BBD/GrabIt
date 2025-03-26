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
		/* background-color: #508991; */
		color: #f7f0f0;
		border-radius: 5px;
		margin-top: 10px;
		padding: 10px;
	}
	.dropdown {
		position: absolute;
		background: white;
		border: 1px solid #ccc;
		border-radius: 6px;
		width: fit-content;
		max-height: 250px;
		overflow-y: auto;
		z-index: 1000;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
		animation: fadeIn 0.2s ease-in-out;
	}

	/* Dropdown Item */
	.dropdown-item {
		padding: 10px 14px;
		cursor: pointer;
		font-size: 14px;
		color: #333;
		transition: background 0.2s ease-in-out;
		border-bottom: 1px solid #eee;
	}

	/* Last item shouldn't have a border */
	.dropdown-item:last-child {
		border-bottom: none;
	}

	/* Hover & Click Effects */
	.dropdown-item:hover {
		background: #f8f8f8;
	}

	.dropdown-item:active {
		background: #e0e0e0;
	}

	/* Scrollbar Styling */
	.dropdown::-webkit-scrollbar {
		width: 6px;
	}

	.dropdown::-webkit-scrollbar-thumb {
		background: #ccc;
		border-radius: 3px;
	}

	.dropdown::-webkit-scrollbar-thumb:hover {
		background: #aaa;
	}
	@keyframes fadeIn {
		from {
			opacity: 0;
			transform: translateY(-5px);
		}
		to {
			opacity: 1;
			transform: translateY(0);
		}
	}
	.colab-list-item {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 10px 14px;
		margin-bottom: 8px;
		background-color: #508991;
		color: #f7f0f0;
		border-radius: 5px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		transition:
			background 0.2s ease-in-out,
			transform 0.1s ease-in-out;
	}

	.colab-list-item:hover {
		background-color: #f9a03f;
		color: #242423;
		transform: translateY(-2px); /* Slight lift on hover */
	}

	.colab-list-item .remove {
		background-color: #f7f0f0;
		color: #508991;
		border: none;
		padding: 6px 12px;
		border-radius: 4px;
		margin-top:auto;
		margin-bottom:auto;
		cursor: pointer;
		transition:
			background 0.2s ease-in-out,
			color 0.2s ease-in-out;
	}

	.colab-list-item .remove:hover {
		background-color: #508991;
		color: #f7f0f0;
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
