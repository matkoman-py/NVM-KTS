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
@NgModule({
  declarations: [AppComponent, NavbarComponent, ArticlesComponent, CreateArticleComponent],
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
    MultiSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
