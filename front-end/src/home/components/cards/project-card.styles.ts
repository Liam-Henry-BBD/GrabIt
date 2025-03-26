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
}

.task-description {
    font-size: .8rem;
    color: #ebe9e9cf;
    margin-bottom: 0.5rem;
}

.project-card h3 {
    font-size: 1.2rem;
    color: #fff; 
    margin-bottom: 0.5rem;
}

.points {
    font-size: 1rem;
    color: #f9a03f;
    font-weight: 600;
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