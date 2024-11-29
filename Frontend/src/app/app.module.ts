import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateNewListComponent } from './create-new-list/create-new-list.component';
import { ListComponent } from './list/list.component';
import { ListViewComponent } from './list-view/list-view.component';
import { NavbarComponent } from './navbar/navbar.component';
import { NewListComponent } from './new-list/new-list.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateNewListComponent,
    ListComponent,
    ListViewComponent,
    NavbarComponent,
    NewListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
