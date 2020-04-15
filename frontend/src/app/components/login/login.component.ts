import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { map, catchError } from 'rxjs/operators';
import { HttpService } from '../http.service'
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
    private router: Router
    ) { }


  profileForm = this.fb.group({
    login: ['', Validators.required],
    password: ['', Validators.required],
  });

  ngOnInit(): void {
    

  }

  onSubmit(e) {
    console.log("соси хуй");
    this.http.login(this.profileForm.value.login, this.profileForm.value.password).subscribe();
  }
}
