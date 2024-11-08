import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeneuComponent } from './listeneu.component';

describe('ListeneuComponent', () => {
  let component: ListeneuComponent;
  let fixture: ComponentFixture<ListeneuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ListeneuComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListeneuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
