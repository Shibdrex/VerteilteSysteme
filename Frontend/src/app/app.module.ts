import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ListeComponent } from './liste/liste.component';
import { NeueListeComponent } from './neue-liste/neue-liste.component';
import { VollansichtComponent } from './vollansicht/vollansicht.component';
import { ListeneuComponent } from './listeneu/listeneu.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ListeComponent,
    NeueListeComponent,
    VollansichtComponent,
    ListeneuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
