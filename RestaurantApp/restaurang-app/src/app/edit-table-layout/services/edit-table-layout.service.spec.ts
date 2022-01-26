import { TestBed } from '@angular/core/testing';

import { EditTableLayoutService } from './edit-table-layout.service';

describe('EditTableLayoutService', () => {
  let service: EditTableLayoutService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditTableLayoutService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
