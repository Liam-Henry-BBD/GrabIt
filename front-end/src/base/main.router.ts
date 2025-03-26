import { Route } from '@vaadin/router';

export const routes: Route[] = [
    {
      path: '/home',
      component: 'home-app',
      action: async () => {
        await import('../home/home-app');
        document.title = 'Dashboard';
      }
    },
    {
      path: '/',
      component: 'landing-app',
      action: async () => {
        await import('../landing/activities/landing-app');
        document.title = 'welcome to grabit';
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
        await import('../projects/create-project');
        document.title = '• created-project.com';
      }
    },
    {
      path: '/project/:id',
      component: 'project-overview',
      action: async () => {
        await import('../projects/project-overview');
        document.title = '• project-overview.com';
      }
    },
    {
      path: '/tutorials',
      component: 'tutorials-page',
      action: async () => {
        await import('../tutorial/tutorials');
        document.title = '• tutorial-page.com';
      }
    }
  ]
