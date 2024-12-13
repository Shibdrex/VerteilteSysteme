import { TestBed } from '@angular/core/testing';

import { PostUserServiceService } from './post-user-service.service';

describe('PostUserServiceService', () => {
  let service: PostUserServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PostUserServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
