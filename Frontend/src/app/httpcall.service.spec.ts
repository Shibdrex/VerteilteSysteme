import { TestBed } from '@angular/core/testing';

import { HTTPCallService } from './httpcall.service';

describe('HTTPCallService', () => {
  let service: HTTPCallService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HTTPCallService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
