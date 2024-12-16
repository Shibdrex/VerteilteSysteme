import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateNewListComponent } from './create-new-list/create-new-list.component';
import { ListComponent } from './list/list.component';
import { ListViewComponent } from './list-view/list-view.component';
import { NavbarComponent } from './navbar/navbar.component';
import { NewListComponent } from './new-list/new-list.component';
import { ViewAllListsComponent } from './view-all-lists/view-all-lists.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateNewListComponent,
    ListComponent,
    ListViewComponent,
    NavbarComponent,
    NewListComponent,
    ViewAllListsComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
