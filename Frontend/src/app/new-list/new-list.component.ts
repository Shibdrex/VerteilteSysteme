import { Component } from '@angular/core';

@Component({
  selector: 'app-new-list',
  templateUrl: './new-list.component.html',
  styleUrl: './new-list.component.css'
})
export class NewListComponent {
  
  titel: HTMLElement | null;
  toDo: HTMLElement | null;

  constructor() {
    this.titel = null;
    this.toDo = null;
  }

  

  neuerInput() {
    this.titel = document.getElementById('titel');
    this.toDo = document.getElementById('input');

  }

}
