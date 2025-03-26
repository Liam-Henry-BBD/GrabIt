import { CtLit, html, customElement, css } from '@conectate/ct-lit';
import { Page } from '@conectate/ct-router';
import '@conectate/ct-router';


@customElement('app-router')
export class AppRouter extends CtLit {
	static styles = [
		css`
			:host {
				display: block;
			}
		`
	];


	static pages: Page[] = [
		{
			path: '/',
			element: html`<home-app></home-app>`,
			from: () => import('./src/home/home-app'),
			auth: false,
			title: () => `Dashboard`
		},
		{
			path: '/project/:projectID/leaderboard',
			element: html`<leaderboard-app></leaderboard-app>`,
			from: () => import('./src/board/leaderboard'),
			auth: false,
			title: () => `Leaderboard`
		},
		// {
		//     path: '/404',
		//     element: html`<app-404></app-404>`,
		//     from: () => import('./app-404'),
		//     auth: false,
		//     title: () => `404 • Example.com`
		// },
		{
			path: '/login',
			element: html`<app-login></app-login>`,
			from: () => import('./src/login/activities/app-login'),
			auth: false,
			title: () => `Login`
		},
		{
			path: '/projects',
			element: html`<projects-app></projects-app>`,
			from: () => import('./src/home/activities/projects/activities/projects-app'),
			auth: false,
			title: () => `Login`
		},
		{
			path: '/redirect',
			element: html`<redirect-app></redirect-app>`,
			from: () => import('./src/redirect/activities/redirect-app'),
			auth: false,
			title: () => `Login`
		},
		{
			path: '/create-project',
			element: html`<create-project></create-project>`,
			from: () => import('./src/projects/create-project'),
			auth: false,
			title: () => `create-project`
		},
		{
			path: '/project/:id',
			element: html`<project-description></project-description>`,
			from: () => import('./src/projects/project-overview'),
			auth: false,
			title: () => `• created-project.com`
		},
		{
			path: '/tutorials',
			element: html`<tutorials-page></tutorials-page>`,
			from:  () => import("./src/tutorial/tutorials"),
			auth: false,
			title: () => `• tutorial-page.com`
		}
	];

	render() {
		return html` <ct-router loginFallback="/login" loginFallback="/404" .pages=${AppRouter.pages}> </ct-router>`;
	}
}
