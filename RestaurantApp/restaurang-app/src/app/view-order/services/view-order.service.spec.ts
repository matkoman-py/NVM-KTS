import { TestBed } from '@angular/core/testing';
import { ViewOrderService } from './view-order.service';

describe('OrdersService', () => {
  let service: ViewOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ViewOrderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
