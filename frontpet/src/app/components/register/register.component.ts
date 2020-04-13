import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { RegistrationService } from '../services/registration.service';
import MustMatch from './mustMatch';
import { AuthorizationService } from '../services/authorization.service';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  private isError: boolean = false;
  private errorMessage: string;

  profileForm = this.formBuilder.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    birthDate: ['', Validators.required],
    email: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(7)]],
    rePassword: ['', Validators.required],
  }, {
    validator: MustMatch('password', 'rePassword')
  });

  constructor(
    private formBuilder: FormBuilder,
    private regService: RegistrationService,
    private authService: AuthorizationService,
    private router: Router) { }

  ngOnInit() {
  }

  onSubmit(e) {
    if (this.profileForm.valid) {
      const descriptor = this.regService.signUp(this.profileForm.value).subscribe(
        res => {
          const descriptor = this.authService.login(this.profileForm.value.email, this.profileForm.value.password)
            .pipe(
              map(res => {
                if (res) {
                  this.router.navigate(["/tracks/browse"]);
                } else {
                  this.isError = true;
                  console.log(res);
                }
              })
            ).subscribe(res => { }, () => { descriptor.unsubscribe() });
        },
        err => {
          this.isError = true;
          console.log(err.error.validationErrors);
          let errMessage: string = "";
          for(let error in err.error.validationErrors) {
            errMessage += err.error.validationErrors[error] + ", ";
          }
          this.errorMessage = errMessage.slice(0, -2);
        },
        () => {
          descriptor.unsubscribe();
        });
    }
  }
}
