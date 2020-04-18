import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { map, catchError } from 'rxjs/operators';
import { HttpService } from '../http.service'
import { HttpClient, HttpEventType } from '@angular/common/http';
import { User } from '../../user'
import { NgForm } from '@angular/forms'
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent /*implements OnInit*/ {

  constructor(private fb: FormBuilder,
    private http: HttpService,
    private router: Router,
    ) { }


  profileForm = this.fb.group({
    login: ['', Validators.required],
    password: ['', Validators.required],
  });

  ngOnInit(): void {
    

  }

  onSubmit(e) {
    this.http.loginin(this.profileForm.value.login, this.profileForm.value.password).subscribe(
      response =>
      {
        console.log(response);
        console.log(response.status);
        if (response.status == 200)
        this.router.navigate(["/home"]);
      },
      err =>
      {
        console.log(err.status);
      }
    );
  }
}
