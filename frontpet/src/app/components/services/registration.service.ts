import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private URL: string = "/v1/register"

  constructor(private httpClient: HttpClient) { }

  signUp(regParams) {
    regParams.birthDate = regParams.birthDate.split("-").reverse().join("/");
    return this.httpClient.post(this.URL, regParams);
  }
}
