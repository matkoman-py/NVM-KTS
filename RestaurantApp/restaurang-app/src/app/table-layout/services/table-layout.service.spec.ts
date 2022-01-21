import { TestBed } from '@angular/core/testing';

import { TableLayoutService } from './table-layout.service';

describe('TableLayoutService', () => {
  let service: TableLayoutService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TableLayoutService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
