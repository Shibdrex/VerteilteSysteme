import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VollansichtComponent } from './vollansicht/vollansicht.component';
import { ListeComponent } from './liste/liste.component';
import { ListeneuComponent } from './listeneu/listeneu.component';

const routes: Routes = [
  {path:'liste', component: ListeComponent},
  {path:'',redirectTo:'/liste',pathMatch: 'full'},
  {path:'vollansicht',component: VollansichtComponent},
  {path:'listeneu', component: ListeneuComponent},
  {path:'absenden', redirectTo:'/liste', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
