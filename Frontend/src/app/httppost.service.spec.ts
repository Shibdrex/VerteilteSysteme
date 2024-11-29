import { TestBed } from '@angular/core/testing';

import { HTTPPostService } from './httppost.service';

describe('HTTPPostService', () => {
  let service: HTTPPostService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HTTPPostService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
