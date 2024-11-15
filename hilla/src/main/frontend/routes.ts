import type { Route } from '@vaadin/router';
import './views/messages/messages-view';

export type ViewRoute = Route & {
  title?: string;
  icon?: string;
  children?: ViewRoute[];
};

export const views: ViewRoute[] = [
  // Place routes below (more info https://hilla.dev/docs/routing)
  {
    path: '',
    component: 'messages-view',
    icon: 'file',
    title: 'Empty',
  },
];
export const routes: ViewRoute[] = [...views];
