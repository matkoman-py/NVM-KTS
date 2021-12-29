import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { MenubarModule } from 'primeng/menubar';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ArticlesComponent } from './articles/articles.component';
import { EmployeesComponent } from './employees/employees.component';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ToastModule } from 'primeng/toast';
import {CheckboxModule} from 'primeng/checkbox';
import {AutoCompleteModule} from 'primeng/autocomplete';
import { RippleModule } from 'primeng/ripple';
import {RadioButtonModule} from 'primeng/radiobutton';
import { CreateArticleComponent } from './create-article/create-article.component';
import {MultiSelectModule} from 'primeng/multiselect';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {InputNumberModule} from 'primeng/inputnumber';
import { NotificationBarComponent } from './notification-bar/notification-bar.component';
import { SidebarModule } from 'primeng/sidebar';
import { BadgeModule } from 'primeng/badge';
import { UpdateArticleComponent } from './update-article/update-article.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ArticlesComponent,
    NotificationBarComponent,
    CreateArticleComponent,
    UpdateArticleComponent,
    EmployeesComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MenubarModule,
    InputTextModule,
    ButtonModule,
    TableModule,
    FormsModule,
    HttpClientModule,
    DropdownModule,
    ToastModule,
    RippleModule,
    CheckboxModule,
    RadioButtonModule,
    AutoCompleteModule,
    MultiSelectModule,
    MessageModule,
    MessagesModule,
    InputNumberModule,
    SidebarModule,
    BadgeModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}