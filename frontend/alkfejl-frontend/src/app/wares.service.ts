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
  
  reqWares()
  {
    this.httpClient
    .get<Ware[]>(`/api/wares`, this.authService.getOptions())
    .toPromise()
		.then(wares => this.wares = wares);
  }
}
