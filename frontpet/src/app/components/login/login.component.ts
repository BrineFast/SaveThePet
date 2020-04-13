import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { AuthorizationService } from '../services/authorization.service';
import { Router } from '@angular/router';
import { map, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  isError: boolean = false;

  profileForm = this.fb.group({
    login: ['', Validators.required],
    password: ['', Validators.required],
  });

  constructor(
    private fb: FormBuilder,
    private auth: AuthorizationService,
    private router: Router
  ) { }

  onSubmit(e) {
    if (this.profileForm.valid) {
      const descriptor = this.auth.login(this.profileForm.value.login, this.profileForm.value.password)
        .pipe(
          map(res => {
            if (res) {
              this.router.navigate(["/tracks/browse"]);
            } else {
              this.isError = true;
            }
          })
        ).subscribe(res => { }, () => { descriptor.unsubscribe() });
    }
  }
}