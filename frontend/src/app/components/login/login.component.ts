import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { map, catchError } from 'rxjs/operators';
import { HttpService } from '../http.service'
import { User } from '../../user'
import { NgForm } from '@angular/forms'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent /*implements OnInit*/ {

  constructor(private fb: FormBuilder,
    private http: HttpService) { }

  profileForm = this.fb.group({
    login: ['', Validators.required],
    password: ['', Validators.required],
  });

  ngOnInit(): void {
    console.log(12);

  }

  onSubmit(e) {
    console.log(123);
    this.http.createAccount(this.profileForm.value.login, this.profileForm.value.password).subscribe();
  }
}
