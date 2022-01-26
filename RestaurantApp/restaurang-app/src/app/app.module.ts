import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { MenubarModule } from 'primeng/menubar';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/components/login.component';
import { PasswordModule } from 'primeng/password';
import { TabViewModule } from 'primeng/tabview';
import { LoginService } from './login/services/login.service';
import { ArticlesComponent } from './articles/articles.component';
import { EmployeesComponent } from './employees/employees.component';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ToastModule } from 'primeng/toast';
import { CheckboxModule } from 'primeng/checkbox';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { RippleModule } from 'primeng/ripple';
import { RadioButtonModule } from 'primeng/radiobutton';
import { CreateArticleComponent } from './create-article/create-article.component';
import { MultiSelectModule } from 'primeng/multiselect';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { InputNumberModule } from 'primeng/inputnumber';
import { NotificationBarComponent } from './notification-bar/notification-bar.component';
import { SidebarModule } from 'primeng/sidebar';
import { BadgeModule } from 'primeng/badge';
import { UpdateArticleComponent } from './update-article/update-article.component';
import { OrdersComponent } from './orders/orders.component';
import { ViewOrderComponent } from './view-order/view-order.component';
import { CardModule } from 'primeng/card';
import { DialogModule } from 'primeng/dialog';
import { IngredientsComponent } from './ingredients/ingredients.component';
import { FileUploadModule } from 'primeng/fileupload';
import { ReportsComponent } from './reports/reports.component';
import { ChartModule } from 'primeng/chart';
import { TableLayoutComponent } from './table-layout/table-layout.component';
import { EditTableLayoutComponent } from './edit-table-layout/edit-table-layout.component';
import { CalendarModule } from 'primeng/calendar';
import { LogoutComponent } from './logout/component/logout.component';
import { ViewOrderWaiterComponent } from './view-order-waiter/view-order-waiter.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ArticlesComponent,
    NotificationBarComponent,
    CreateArticleComponent,
    UpdateArticleComponent,
    EmployeesComponent,
    LoginComponent,
    OrdersComponent,
    ViewOrderComponent,
    IngredientsComponent,
    ReportsComponent,
    TableLayoutComponent,
    EditTableLayoutComponent,
    LogoutComponent,
    ViewOrderWaiterComponent,
  ],
  imports: [
    CalendarModule,
    RadioButtonModule,
    ChartModule,
    CardModule,
    BrowserModule,
    BrowserAnimationsModule,
    MenubarModule,
    InputTextModule,
    ButtonModule,
    FileUploadModule,
    ReactiveFormsModule,
    PasswordModule,
    DialogModule,
    TabViewModule,
    TableModule,
    FormsModule,
    HttpClientModule,
    DropdownModule,
    ToastModule,
    RippleModule,
    CheckboxModule,
    AutoCompleteModule,
    MultiSelectModule,
    MessageModule,
    MessagesModule,
    InputNumberModule,
    SidebarModule,
    BadgeModule,
    AppRoutingModule,
  ],
  providers: [LoginService],
  bootstrap: [AppComponent],
})
export class AppModule {}
