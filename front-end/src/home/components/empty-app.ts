
import { CtLit, html, css } from "@conectate/ct-lit";
import { customElement, } from 'lit/decorators.js';


@customElement("empty-app")
export class EmptyApp extends CtLit {

    static styles = css`
    .placeholder-container {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            padding: 20px;
            max-width: 500px;
            margin: 0 auto;
            height: 100%;
            color: #fff;
        }
        
        .placeholder-illustration {
            width: 200px;
            height: 200px;
            margin-bottom: 30px;
            background-color: #e5e7eb;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 60px;
        }
        
        .placeholder-title {
            font-size: 24px;
            font-weight: 600;
            margin-bottom: 16px;
        }
        
        .placeholder-description {
            font-size: 16px;
            line-height: 1.5;
            color: #6b7280;
            margin-bottom: 30px;
        }
        
        .select-project-btn {
            color: white;
            border: none;
            padding: 12px 24px;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.2s;
        }
        
        .select-project-btn:hover {
            background-color: #4338ca;
        }
        
        .empty-project-icon {
            width: 100px;
            height: 100px;
            stroke: #9ca3af;
            fill: none;
            stroke-width: 1.5;
        }`


    render() {
        return html`<div class="placeholder-container">
            
            <h2 class="placeholder-title">No Project Selected</h2>
            <p class="placeholder-description">
                Select a project from the sidebar to view its dashboard, or create a new project to get started.
            </p>
        </div>`;
    }
}