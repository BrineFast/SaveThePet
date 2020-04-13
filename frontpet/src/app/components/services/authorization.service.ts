import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {
  private isAuth: boolean;

  constructor(private httpClient: HttpClient) { }

  login(login, password): Observable<boolean> {
    const formData = new FormData();
    formData.append("email", login);
    formData.append("password", password);

    return this.httpClient.post<boolean>("/v1/login", formData).pipe(
      map(res => {
        this.isAuth = true;
        return this.isAuth;
      }),
      catchError(err => {
        this.isAuth = false;
        return of(false)
      })
    );
  }

  logout() {
    this.isAuth = false;
    return this.httpClient.post("/v1/logout", {});
  }

  isAuthenticated() {
    if(this.isAuth === undefined) {
       return this.httpClient.get("/v1/me")
        .pipe(
          map(res => {
            this.isAuth = true;
            return this.isAuth;
          }),
          catchError(err => {
            this.isAuth = false;
            return of(this.isAuth);
          })
        )
    } else {
      return of(this.isAuth)
    }
  }
}
