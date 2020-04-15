import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Validators, FormBuilder } from '@angular/forms';

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
  })

  constructor(
    private formBuilder: FormBuilder,
    private router : Router
    
  ) { }

  ngOnInit(): void {
  }

}
