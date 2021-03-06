import { AuthGuard } from './auth.guard';
import { Routes } from '@angular/router';
import { ArticlesComponent } from '../articles/articles.component';
import { EmployeesComponent } from '../employees/employees.component';
import { LoginComponent } from '../login/components/login.component';
import { CreateArticleComponent } from '../create-article/create-article.component';
import { UpdateArticleComponent } from '../update-article/update-article.component';
import { OrdersComponent } from '../orders/orders.component';
import { ViewOrderComponent } from '../view-order/view-order.component';
import { IngredientsComponent } from '../ingredients/ingredients.component';
import { ReportsComponent } from '../reports/reports.component';
import { TableLayoutComponent } from '../table-layout/table-layout.component';
import { EditTableLayoutComponent } from '../edit-table-layout/edit-table-layout.component';
import { LogoutComponent } from '../logout/component/logout.component';
import { ViewOrderWaiterComponent } from '../view-order-waiter/view-order-waiter.component';

export const routes: Routes = [
  {
    path: 'logout',
    component: LogoutComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_MANAGER', 'COOK', 'BARMAN', 'WAITER'],
    },
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'table-layout',
    component: TableLayoutComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['WAITER'],
    },
  },
  {
    path: 'edit-table-layout',
    component: EditTableLayoutComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_MANAGER'],
    },
  },
  {
    path: 'articles',
    component: ArticlesComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_MANAGER'],
    },
  },
  {
    path: 'employees',
    component: EmployeesComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_MANAGER'],
    },
  },
  {
    path: 'create-article',
    component: CreateArticleComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_MANAGER'],
    },
  },
  {
    path: 'update-article/:id',
    component: UpdateArticleComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_MANAGER'],
    },
  },
  {
    path: 'active-orders',
    component: OrdersComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['COOK', 'BARMAN'],
    },
  },
  {
    path: 'view-order-kitchen/:id',
    component: ViewOrderComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['COOK', 'BARMAN'],
    },
  },
  {
    path: 'ingredients',
    component: IngredientsComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_MANAGER'],
    },
  },
  {
    path: 'reports',
    component: ReportsComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['ROLE_MANAGER'],
    },
  },
  {
    path: 'view-order-waiter/:id',
    component: ViewOrderWaiterComponent,
    canActivate: [AuthGuard],
    data: {
      expectedRoles: ['WAITER'],
    },
  },
  {
    path: '**',
    canActivate: [AuthGuard],
    component: LoginComponent,
    data: {
      expectedRoles: [],
    },
  },
];
