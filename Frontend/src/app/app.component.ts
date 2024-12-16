import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { SessionService } from './session.service';  // Import SessionService

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'Angular App'; // You may modify the title as needed
  session: boolean = false;  // Initialize session as false
  register: number = 0;
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private subscriptions: Subscription = new Subscription();
  
  constructor(private sessionService: SessionService) {}  // Inject SessionService

  ngOnInit(): void {
    // Check if session is active when the component initializes
    this.session = this.sessionService.isSessionActive();  // Get the session status

    // Beobachte Änderungen der URL-Parameter, speziell des 'session'-Parameters
    const sessionSub = this.route.queryParamMap.subscribe(params => {
      const sessionParam = params.get('session');
      this.session = sessionParam === 'true';
    });

    const registerSub = this.route.queryParamMap.subscribe(params => {
      const registerParam = params.get('registriert');
      if (registerParam === '1') {
        this.router.navigate(['/registrieren']);
      }
    });

    // Füge die Subscriptions hinzu, um sie später zu löschen
    this.subscriptions.add(sessionSub);
    this.subscriptions.add(registerSub);
  }

  ngOnDestroy(): void {
    // Bereinige Subscriptions, um Memory-Leaks zu vermeiden
    this.subscriptions.unsubscribe();
  }
}