import { Routes } from '@angular/router';
import { ArticlesComponent } from '../articles/articles.component';
import { EmployeesComponent } from '../employees/employees.component';
import { HomeComponent } from '../home/home.component';
import { LoginComponent } from '../login/components/login.component';
import { CreateArticleComponent } from '../create-article/create-article.component';
import { UpdateArticleComponent } from '../update-article/update-article.component';
import { OrdersComponent } from '../orders/orders.component';
import { ViewOrderComponent } from '../view-order/view-order.component';
import { IngredientsComponent } from '../ingredients/ingredients.component';
import { ReportsComponent } from '../reports/reports.component';
import { TableLayoutComponent } from '../table-layout/table-layout.component';
import { EditTableLayoutComponent } from '../edit-table-layout/edit-table-layout.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'articles', component: ArticlesComponent },
  { path: 'employees', component: EmployeesComponent },
  { path: 'create-article', component: CreateArticleComponent},
  { path: 'update-article/:id', component: UpdateArticleComponent},
  { path: 'active-orders', component: OrdersComponent},
  { path: 'view-order-kitchen/:id', component: ViewOrderComponent},
  { path: 'ingredients', component: IngredientsComponent},
  { path: 'reports', component: ReportsComponent},
  { path: 'table-layout', component: TableLayoutComponent },
  { path: 'edit-table-layout', component: EditTableLayoutComponent },
];
