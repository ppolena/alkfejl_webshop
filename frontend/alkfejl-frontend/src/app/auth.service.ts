import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './model/User';

export const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': ''
  })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isLoggedIn: boolean = false;
  redirectUrl: string;
  user: User;
  token: string;

  private authUrl = '/api/users';

  constructor(
    private http: HttpClient
  ) {
    this.token = btoa(`guest:`);
    httpOptions.headers = httpOptions.headers.set('Authorization', `Basic ${this.token}`);
	}

  async login(username: string, password: string): Promise<User> {
    try {
      this.token = btoa(`${username}:${password}`);
      httpOptions.headers = httpOptions.headers.set('Authorization', `Basic ${this.token}`);
      this.user = await this.http.get<User>(`${this.authUrl}/by-email/${username}`, httpOptions).toPromise();
      this.isLoggedIn = true;
      console.log(this.user);
      return this.user;
    }
    catch (e) {
      console.log(e);
      throw e;
    }
  }

  async reg(FN: string, LN: string, mail: string, password: string){
    try {
      const newUser = await this.http.post<User>(`${this.authUrl}`,
      JSON.stringify({firstName:FN, lastName:LN, email:mail, password:password, accessRight:"ROLE_CUSTOMER"}),
      httpOptions).toPromise();
      console.log(newUser);
    }
    catch (e) {
      console.log(e);
      throw e;
    }
  }

  async update(FN: string, LN: string, mail: string, password: string){
    try {
      const newUser = await this.http.put<User>(`${this.authUrl}/by-id/${this.user.id}`,
      JSON.stringify({firstName:FN, lastName:LN, email:mail, password:password, accessRight:"ROLE_CUSTOMER"}),
      httpOptions).toPromise();
      console.log(newUser);
    }
    catch (e) {
      console.log(e);
      throw e;
    }
  }

  getOptions()
  {
	  return httpOptions;
  }
  
  logout() {
    this.token = btoa(`guest:`);
    httpOptions.headers = httpOptions.headers.set('Authorization', `Basic ${this.token}`);
    this.isLoggedIn = false;
    this.user = null;
    this.token = null;
  }

}
