import { Router } from '@vaadin/router';

export function initRouter(outlet: HTMLElement) {
	const router = new Router(outlet);

	router.setRoutes([
		{ path: '/', redirect: '/create-project' },
		{
			path: '/create-project',
			component: 'create-project',
			action: async () => {
				await import('../components/create-project');
			}
		},
		{
			path: '/project/:id',
			component: 'project-description',
			action: async () => {
				await import('../components/project-description');
			}
		},
		{
			path: '/project/:id/create-task',
			component: 'create-task-modal',
			action: async () => {
				await import('../components/create-task-modal');
			}
		}
	]);
}