import { css } from 'lit';

export const createTaskStyles = css`
	:host {
		display: block;
	}
	.modal {
		position: fixed;
		inset: 0;
		z-index: 10;
		background-color: rgba(0, 0, 0, 0.5);
		display: flex;
		justify-content: center;
		align-items: center;
		padding: 1rem;
	}
	.modal-content {
		background-color: #242423;
		color: #f7f0f0;
		max-width: 400px;
		width: 100%;
		border-radius: 8px;
		box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	}
	.header {
		display: flex;
		justify-content: space-between;
		padding: 1rem;
		border-bottom: 1px solid #f9a03f;
	}
	h2 {
		color: #f9a03f;
	}
	button {
		background: none;
		border: none;
		cursor: pointer;
		color: #f7f0f0;
		padding: 15px;
		background-color: rgba(80, 137, 145, 0.1);
	}
	form {
		padding: 1rem;
		display: flex;
		flex-direction: column;
		gap: 2rem;
	}
	input,
	textarea,
	select {
		width: 90%;
		padding: 0.5rem;
		padding-bottom: 0.5rem;
		border-radius: 4px;
		border: 1px solid #508991;
		background-color: rgba(171, 233, 241, 0.1);
		margin-top: 10px;
		color: #f7f0f0;
	}
	.difficulty-buttons {
		display: flex;
		gap: 0.5rem;
	}
	.difficulty-buttons button {
		flex: 1;
		border-radius: 4px;
		border: 1px solid transparent;
	}
	.difficulty-buttons .selected {
		background-color: #f9a03f;
		color: #f7f0f0;
	}
	.difficulty-buttons button:hover {
		background-color: #508991;
	}
`;
