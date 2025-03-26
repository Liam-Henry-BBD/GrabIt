import { css } from 'lit';

export const leaderboardStyles = css`
	:host {
		display: block;
	}

	header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		background-color: #242423;
		border-color: wheat;
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		height: 5rem;
		z-index: 1000;
		padding: 0 2rem; 
		border-bottom: 1px solid rgba(255, 255, 255, 0.1);
	
	}

	.header {
		display: flex;
		width: 100%;
		justify-content: center; 
		align-items: center;
	}

	#logo {
		position: absolute;
		left: 0;
		height: 10rem;
		width: 10rem;
	}

	h1 {
		text-align: center;
		margin: 0; 
		color: white;
	}

	/* -------------------------------------------------- */

	input {
		padding: 0.5rem;
		border-radius: 1rem;
		background-color: transparent;
		border-color:#f9a03f;
		width: 18rem;
		right: 0;
		margin-left: auto;
		align-self: end;
	}

	.leaderboard__container {
		min-height: 100vh;
		padding: 1.5rem;
		color: #fff;
		margin-top: 5rem;
		width: 65%;
		margin-inline: auto;
		background: rgba(255, 255, 255, 0.05);
		border-radius: 1rem;
	}

	.leaderboard__container a{
		background-color: #f9a03f;
		color: #242423;
		padding: 0.5rem;
		border-radius: 1rem;
		text-decoration: none;


	}


	article {
		display: flex;
		flex-direction: row;
		margin: 0.5rem;
	}
	h1,
	h2 {
		text-align: center;
	}

	h1 {
		font-size: 2rem;
		display: flex;
		align-items: center;
		justify-content: center;
		gap: 0.5rem;
	}

	h2 {
		font-size: 1.6rem;
		margin-top: 0.5rem;
		font-weight: 500;
	}

	.leaderboard__user {
		background: rgba(80, 137, 145, 0.2);
		padding: 1rem 1.2rem;
		display: flex;
		flex-direction: column;
		border-radius: 0.8rem;
		margin-bottom: 1rem;
		transition:
			transform 0.3s ease,
			background 0.3s ease;
		box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
	}

	.leaderboard__user:hover {
		background: rgba(80, 137, 145, 0.3);
		transform: scale(1.02);
	}

	h3 {
		font-size: 1.5rem;
		display: flex;
		align-items: center;
		gap: 1rem;
	}

	.user_name {
		font-size: 1.6rem;
		font-weight: 600;
		color: #f9a03f;
	}

	.avatar {
		width: 2.5rem;
		height: 2.5rem;
		border-radius: 50%;
		background: #f99f3fc8;
		padding: 0.5rem;
		font-size: 1rem;
		display: flex;
		justify-content: center;
		align-items: center;
		font-weight: bold;
		color: #fff;
	}

	progress {
		width: 100%;
		height: 1rem;
		border-radius: 1rem;
		overflow: hidden;
	}

	progress::-webkit-progress-value {
		background-color: #aed319;
		border-radius: 10px;
		transition: width 0.4s ease-in-out;
	}

	progress::-webkit-progress-bar {
		background-color: #464443;
		border-radius: 1rem;
		width: 100%;
		height: 1rem;
	}

	@media screen and (max-width: 768px) {
		.leaderboard__container {
			width: 90%;
		}

		h1 {
			font-size: 1.8rem;
		}

		h2 {
			font-size: 1.4rem;
		}
	}
	/* a {
		display: inline-flex;
		align-items: center;
		justify-content: center;
		gap: 0.5rem;
		background: linear-gradient(135deg, #f9a03f, #ff7b00);
		color: white;
		border: none;
		padding: 0.3rem 1rem;
		font-size: 1.8em;
		font-weight: 600;
		border-radius: 0.5rem;
		cursor: pointer;
		transition:
			background 0.3s ease,
			transform 0.2s ease,
			box-shadow 0.2s ease;
		box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
		margin-top: 1rem;
		align-self: center;
		text-transform: uppercase;
		letter-spacing: 0.5px;
		min-width: 120px;
		text-decoration: none;
	} */

	button:hover {
		background: linear-gradient(135deg, #ff7b00, #f96f00);
		transform: scale(1.05);
		box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.25);
	}

	button:active {
		transform: scale(0.97);
		box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.15);
	}

	button:focus {
		outline: 3px solid rgba(255, 123, 0, 0.8);
		outline-offset: 2px;
	}

	progress::-webkit-progress-value {
		background: linear-gradient(90deg, #aed319, #92c81a);
		border-radius: 10px;
		transition: width 0.4s ease-in-out;
	}
`;
