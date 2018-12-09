import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Order } from './model/order';


@Injectable()
export class OrderService {

  constructor(
	private httpClient: HttpClient,
    private authService: AuthService
  ) { }
  
  private orders: Order[] = [];
  
  geOrders()
  {
	  return this.orders;
  }
  
  reqOrders()
  {
    this.httpClient
    .get<Order[]>(`/api/orders`, this.authService.getOptions())
    .toPromise()
		.then(wares => this.orders = wares);
  }



  filter(filterText: string): Order[] {
    const filteredOrderItems: Order[] = [];
    for (let order of this.orders) {
    if (order.customer == filterText) {
        filteredOrderItems.push(order);
      }
    }
    return filteredOrderItems;
  }

}
