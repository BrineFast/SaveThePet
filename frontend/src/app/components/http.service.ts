import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) { }

  createAccount(login, password) {
    const request = {
      email: login,
      password: password,
      name: "Секслер"
    };
    return this.http.post(`http://localhost:8080/registration`, request, {headers:{'Content-Type': 'application/json'}});
  }

}
