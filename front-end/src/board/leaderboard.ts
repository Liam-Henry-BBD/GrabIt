import { CtLit, customElement, html, state } from "@conectate/ct-lit";

//Components
import "../components/header";

//CSS
import { leaderboardStyles } from "./leaderboard.styles"
import { getLeaderboard } from "../services/leaderboard.service";
import { Router, RouterLocation } from '@vaadin/router';



@customElement("leaderboard-app")
export class Leaderboard extends CtLit {

    @state() projectID: number | null = null;

    onBeforeEnter(location: RouterLocation) {
        this.projectID = Number(location.params["projectID"]);
    }

    static styles = leaderboardStyles;

    connectedCallback(): void {
        super.connectedCallback();
    }

   

    render() {

        return html`<auth-router>
            <main class="leaderboard__container">
                <header-app>
                </header-app>

                <h1>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-trophy h-5 w-5 text-[#b6660f]"><path d="M6 9H4.5a2.5 2.5 0 0 1 0-5H6"></path><path d="M18 9h1.5a2.5 2.5 0 0 0 0-5H18"></path><path d="M4 22h16"></path><path d="M10 14.66V17c0 .55-.47.98-.97 1.21C7.85 18.75 7 20.24 7 22"></path><path d="M14 14.66V17c0 .55.47.98.97 1.21C16.15 18.75 17 20.24 17 22"></path><path d="M18 2H6v7a6 6 0 0 0 12 0V2Z"></path></svg>
                    Leaderboard
                </h1>
                <h2>Project name: Grabit</h2>
                <p>Some text comes that gives description to what this project is...</p>

                <section class="leaderboard">
                    <article class="leaderboard__user">
                        <h3>
                            <span class="position">1.</span>
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-award h-5 w-5 text-[#F9A03F]"><path d="m15.477 12.89 1.515 8.526a.5.5 0 0 1-.81.47l-3.58-2.687a1 1 0 0 0-1.197 0l-3.586 2.686a.5.5 0 0 1-.81-.469l1.514-8.526"></path><circle cx="12" cy="8" r="6"></circle></svg>
                            <span class="user_name">Tadima Monama</span>
                        </h3>
                        <div>
                            <p>Score: 45</p>
                            <progress value="87" max="100"> 87% </progress>
                        </div>
                    </article>
                    <article class="leaderboard__user">
                        <h3>
                            <span class="position">2.</span>
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-award h-5 w-5 text-[#F9A03F]"><path d="m15.477 12.89 1.515 8.526a.5.5 0 0 1-.81.47l-3.58-2.687a1 1 0 0 0-1.197 0l-3.586 2.686a.5.5 0 0 1-.81-.469l1.514-8.526"></path><circle cx="12" cy="8" r="6"></circle></svg>
                            <span class="user_name">Liam Henry</span>
                        </h3>
                        <div>
                            <p>Score: 45</p>
                            <progress value="87" max="100"> 87% </progress>
                        </div>
                    </article>

                </section>


                
            </main>
        </auth-router> `
    }
}