import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListViewComponent } from './list-view/list-view.component';
import { ListComponent } from './list/list.component';
import { NewListComponent } from './new-list/new-list.component';

const routes: Routes = [
  {path:'list', component: ListComponent},
  {path:'',redirectTo:'/liste',pathMatch: 'full'},
  {path:'listView',component: ListViewComponent},
  {path:'newList', component: NewListComponent},
  {path:'send', redirectTo:'/list', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
