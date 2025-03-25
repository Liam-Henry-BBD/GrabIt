import { css } from "lit";


export const leaderboardStyles = css`
    :host {
        display: block;
    }
    .leaderboard__container {
        min-height: 100vh;
        padding: 1rem;
        color: #fff;
        margin-top: 5rem;
        width: 70%;
        margin-inline: auto;
    }

    .leaderboard__container svg {
        color: #f9a03f;
    }


    .leaderboard__user {
        background: rgb(80 137 145 / 0.1);
        padding: 1rem 1rem;
        display: flex;
        justify-content: space-between;
        flex-direction: column;
        padding-bottom: 2rem;
        border-radius: .7rem;
        margin-bottom: 1rem;

    }

    h3 {
        font-size: 1.5rem;
        display: flex;
        flex-direction: row;
        justify-content: start;
        gap: 1rem;
        align-items: center;
    }

    .user_name {
        font-size: 1.6rem;
        font-weight: 600;      
    }

    .avatar {
        width: 2.5rem;
        height: 2.5rem;
        border-radius: 50%;
        background: #f99f3fc8;
        padding: .5rem;
        font-size: 1rem;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    progress {
        width: 100%;
        height: 1rem;
        border-radius: 1rem;
    }
    
    progress::-webkit-progress-value {
      background-color: #aed31935;
      border-radius: 10px;
      transition: width 0.4s ease-in-out;
    }

    progress::-webkit-progress-bar {
      background-color: #46444328;
      border-radius: 1rem;
      width: 100%;
      height: 1rem;
    }

    @media screen and (max-width: 768px) {
        .leaderboard__container {
            width: 90%;
        }

        .leaderboard__container h2 {
            font-size: 1.4rem;
        }
    }

`;