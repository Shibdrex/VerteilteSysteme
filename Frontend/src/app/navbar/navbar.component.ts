import { Component, OnInit } from '@angular/core';
import { PostUserServiceService } from '../post-user-service.service';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  data: any;
  userEmail: string | null = null;  // Variable to hold the email

  constructor(
    private dataService: PostUserServiceService,
    private sessionService: SessionService  // Injecting SessionService
  ) { }

  ngOnInit(): void {
    // Fetch email from session via SessionService
    const session = this.sessionService.getSession();
    if (session) {
      this.userEmail = session.email;  // Retrieve email from session
      console.log('User email:', this.userEmail);
    } else {
      console.log('No active session.');
    }

    // Fetch other data as before
    this.dataService.getData().subscribe((result) => {
      this.data = result;
    });
  }
}



  



