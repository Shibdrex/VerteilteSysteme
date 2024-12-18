import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ListComponent } from './list/list.component';
import { ListViewComponent } from './list-view/list-view.component';
import { NewListComponent } from './new-list/new-list.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {path:'list', component: ListComponent},
  {path: 'login', component: LoginComponent},
  { path: 'registrieren', component: RegisterComponent },
  {path:'',redirectTo:'/list',pathMatch: 'full'},
  {path:'listView',component: ListViewComponent},
  {path:'newList', component: NewListComponent},
  {path:'send', redirectTo:'/list', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
