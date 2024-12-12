import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllListsComponent } from './view-all-lists.component';

describe('ViewAllListsComponent', () => {
  let component: ViewAllListsComponent;
  let fixture: ComponentFixture<ViewAllListsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewAllListsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAllListsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
