import { Route } from '@vaadin/router';

export const routes: Route[] = [
    {
      path: '/',
      component: 'home-app',
      action: async () => {
        await import('../home/home-app');
        document.title = 'Dashboard';
      }
    },
    {
      path: '/project/:projectID/leaderboard',
      component: 'leaderboard-app',
      action: async () => {
        await import('../board/leaderboard');
        document.title = 'Leaderboard';
      }
    },
    {
      path: '/login',
      component: 'app-login',
      action: async () => {
        await import('../login/activities/app-login');
        document.title = 'Login';
      }
    },
    {
      path: '/projects',
      component: 'projects-app',
      action: async () => {
        await import('../home/activities/projects/activities/projects-app');
        document.title = 'Projects';
      }
    },
    {
      path: '/redirect',
      component: 'redirect-app',
      action: async () => {
        await import('../redirect/activities/redirect-app');
        document.title = 'Redirect';
      }
    },
    {
      path: '/create-project',
      component: 'create-project',
      action: async () => {
        await import('../components/create-project');
        document.title = 'Create Project';
      }
    },
    {
      path: '/project/:id',
      component: 'project-description',
      action: async () => {
        await import('../components/project-description');
        document.title = '• created-project.com';
      }
    },
    {
      path: '/tutorials',
      component: 'tutorials-page',
      action: async () => {
        await import('../components/tutorials-page');
        document.title = '• tutorial-page.com';
      }
    }
  ]
