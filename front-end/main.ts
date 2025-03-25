import { Router } from '@vaadin/router';
import { routes } from './src/base/main.router';

export const router = new Router(document.querySelector('#outlet')); 

router.setRoutes(routes);