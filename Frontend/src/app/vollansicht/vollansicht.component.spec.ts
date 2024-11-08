import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VollansichtComponent } from './vollansicht.component';

describe('VollansichtComponent', () => {
  let component: VollansichtComponent;
  let fixture: ComponentFixture<VollansichtComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VollansichtComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VollansichtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
