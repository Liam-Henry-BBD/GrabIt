import { css } from "lit";

/* Task Card Styles */
export const cardStyles = css`

    .project-card {
        background-color: #1e1e1e3e; 
        color: #ffffff;
        border-radius: 5px;
        padding: .4rem 1rem;
        margin-bottom: 1rem;
        border: 1px solid #b9b9b929;
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); 
        position: relative; 
    }

    .task-description {
        font-size: .8rem;
        color: #ebe9e9cf;
        margin-bottom: 0.5rem;
    }

    .project-card h3 {
        font-size: 1rem;
        color: white; 
        margin-bottom: 0.5rem;
    }

    .points {
        font-size: 0.9rem;
        padding: 0.1rem;
        position: absolute;
        top: 0.5rem;
        right: 0.5rem; 
        width: 2rem;
        height: 2rem;
        border-radius: 50%; 
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #f9a03f; 
        color: #fff; 
    }

    .simple {
        color: green; 
    }

    .medium {
        color: yellow; 
    }

    .hard {
        color: #242423;
        background-color: #c91f1f; 
    }

    .card-btn {
        border: 1px solid #f99f3f26;
        background-color: inherit;
        color: #f9a03f;
        padding: 0.5rem 1rem;
        border-radius: 5px;
        cursor: pointer;
    }

    @media (max-width: 1024px) {
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
`