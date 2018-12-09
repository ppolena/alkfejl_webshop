import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Ware } from './model/Ware';

@Injectable()
export class WaresService {

  constructor(
	private httpClient: HttpClient,
    private authService: AuthService
  ) { }
  
  private wares: Ware[] = [];
  
  getWares()
  {
	  return this.wares;
  }


  getFilteredWares(filterWhat: string, filterWalue: string)
  {
    const filteredWares: Ware[] = [];
    if(filterWhat == "") {  
      return this.wares;
    }
    if(filterWhat == "type") {  
      for (let item of this.wares) {
        if (item.type.startsWith(filterWalue)) {
  
          filteredWares.push(item);
  
        }
      }
    }
    if(filterWhat == "price") {  
      for (let item of this.wares) {
        if (Number(item.price)<=Number(filterWalue)) {
  
          filteredWares.push(item);
  
        }
      }
    }
    if(filterWhat == "manufacturer") {  
      for (let item of this.wares) {
        if (item.manufacturer.startsWith(filterWalue)) {
  
          filteredWares.push(item);
  
        }
      }
    }
	  return filteredWares;
  }
  
  reqWares()
  {
    this.httpClient
    .get<Ware[]>(`/api/wares`, this.authService.getOptions())
    .toPromise()
		.then(wares => this.wares = wares);
  }
}
