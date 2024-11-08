import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeueListeComponent } from './neue-liste.component';

describe('NeueListeComponent', () => {
  let component: NeueListeComponent;
  let fixture: ComponentFixture<NeueListeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NeueListeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NeueListeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
