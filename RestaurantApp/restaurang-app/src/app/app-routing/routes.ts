import { Routes } from '@angular/router';
import { ArticlesComponent } from '../articles/articles.component';
import { HomeComponent } from '../home/home.component';
import { LoginComponent } from '../login/login.component';
import { CreateArticleComponent } from '../create-article/create-article.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'articles', component: ArticlesComponent },
  { path: 'create-article', component: CreateArticleComponent}
];
