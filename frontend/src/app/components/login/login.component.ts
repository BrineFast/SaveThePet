import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Validators } from '@angular/forms';
import { HttpService } from '../http.service'
import { HttpEventType, HttpEvent } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent {

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
    this.http.join(this.profileForm.value.login, this.profileForm.value.password).subscribe(
      (progressEvent: HttpEvent<any>) => {
        switch (progressEvent.type) {
          case HttpEventType.Response:
            console.log('ОК');
            this.router.navigate(["/home"]);
            break;
          case HttpEventType.Sent:
            console.log('Sent, but not OK');
            break;
        }
      }
    );
  }
}
