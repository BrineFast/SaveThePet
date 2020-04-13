import { NgModule } from '@angular/core';
import { Routes, RouterModule, Router } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { LoginAuthGuard } from "./login-auth.guard"
import {RegisterComponent} from './components/register/register.component';

const routes: Routes = [
  {
    path: "login", 
    component: LoginComponent,
    canActivate: [LoginAuthGuard]
  },
  /*{
    path: "forgot",
    //component: ForgotComponent,
    //canActivate: [LoginAuthGuard]
  },*/
  {
    path: "register",
    component: RegisterComponent,
    canActivate: [LoginAuthGuard]
  },
  {
    path: "",
    redirectTo: "/login",
    pathMatch: "full"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
  constructor(private router: Router) {
  }

  /*public menuIsHidden() {
    const location = this.router.url;
    return location === "/login"
        || location === "/register"
        || location === "/forgot"
  }*/
}
