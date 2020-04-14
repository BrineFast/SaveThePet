import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http';
import { User } from './user'




@Injectable()
export class HttpService {
  constructor(private http: HttpClient) {}

  
  postData(user: User) {
    const body = { name: user.name, age: user.age }
    return this.http.post('http://localhost:8000', body)
  }
}