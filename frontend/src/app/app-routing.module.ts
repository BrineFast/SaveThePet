import { NgModule } from '@angular/core';
import { Routes, RouterModule,Router } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';


const routes: Routes = [
  {path:'login',component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: '**', redirectTo: "login",pathMatch:"full"}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

  constructor(private roter: Router){

  }
}
