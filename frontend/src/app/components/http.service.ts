import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) { }

  login(login, password) {
    const request = {
      email: login,
      password: password,
     };
     return this.http.post(`http://localhost:8080/registration`, request, {headers:{'Content-Type': 'multipart/form-data'}});
  }
    

  register(login,fullname,password)
{
  const request = {
      email: login,
      name: fullname, 
      password: password,
     };
     return this.http.post(`http://localhost:8080/registration`, request, {headers:{'Content-Type': 'application/json'}});
    }

}  

  




   


