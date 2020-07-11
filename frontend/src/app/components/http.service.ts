import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpRequest} from '@angular/common/http';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(
    private http: HttpClient,
    private router: Router,
    ){ }

  join(login, password){
    const body = new HttpParams()
    .set('username', login)
    .set('password', password);
    const req = new HttpRequest('POST', 'http://localhost:8080/login', body, {withCredentials: true});
    let headers = new Headers();
    req.headers.append("observe", "response");
    req.headers.append('Content-Type', 'application/json');
    req.headers.append("Access-Control-Allow-Credentials", "true")

    return this.http.request(req);
  }

  register(login,fullname,password)
{
  const request = {
      email: login,
      name: fullname, 
      password: password,
     };
     return this.http.post(`http://localhost:8080/registration`, request);
    }

}  

  




   


