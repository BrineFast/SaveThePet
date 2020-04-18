import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpRequest, HttpEventType } from '@angular/common/http';
import {Observable, from} from 'rxjs';
import { HttpResponse } from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(
    private http: HttpClient,
    private router: Router,
    ){ }

  loginin(login, password){
    const body = new HttpParams()
    .set('username', login)
    .set('password', password);
    const req = new HttpRequest('POST', 'http://localhost:8080/login', body);
    req.headers.append("observe", "response");

    return this.http.request(req);
  }

  login(login, password) {
    const body = new HttpParams()
    .set('username', login)
    .set('password', password);

  return this.http.post('http://localhost:8080/login',
    body.toString(),
    {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded')
        , observe: 'response'
    }
  )
  }
    

  register(login,fullname,password)
{
  const request = {
      email: login,
      name: fullname, 
      password: password,
     };
     return this.http.post(`http://localhost:8080/registration`, request/*, {headers:{'Content-Type': 'application/json'}}*/);
    }

}  

  




   


