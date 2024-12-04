import { Component, OnInit } from '@angular/core';
import { PostUserServiceService } from '../post-user-service.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'

})
export class NavbarComponent implements OnInit{

  data: any;

  constructor(private dataService: PostUserServiceService) { }

  ngOnInit(): void {
    this.dataService.getData().subscribe((result)=> {
      this.data = result;
      console.log(this.data);
    });
  }


  


}
