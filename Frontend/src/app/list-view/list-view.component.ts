import { Component, Input } from '@angular/core';


@Component({
  selector: 'app-list-view',
  templateUrl: './list-view.component.html',

  styleUrl: './list-view.component.scss'
})


export class ListViewComponent {

  @Input() item: any;

  


  Add(){

  }
  
  Remove(){
   console.log(this.item)
  
  }

  ChangeNotFav(){

  }

  ChangeFav(){

  }

}
