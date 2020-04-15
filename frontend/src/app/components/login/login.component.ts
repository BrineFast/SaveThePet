import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { map, catchError } from 'rxjs/operators';
import { HttpService } from '../../http.service'
import { User } from '../../user'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})


export class LoginComponent implements OnInit {

  ngOnInit(): void {
  }

  constructor(
  ) { }
}
