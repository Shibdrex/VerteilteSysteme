import { TestBed } from '@angular/core/testing';

import { ElementServiceService } from './element-service.service';

describe('ElementServiceService', () => {
  let service: ElementServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElementServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
