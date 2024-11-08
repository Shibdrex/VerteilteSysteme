import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-listeneu',
  templateUrl: './listeneu.component.html',
  styleUrls: ['./listeneu.component.css']
})
export class ListeneuComponent {
  
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
