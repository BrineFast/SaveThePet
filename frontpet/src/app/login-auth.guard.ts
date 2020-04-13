import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { AuthorizationService } from './components/services/authorization.service';
import { map, catchError, take } from 'rxjs/operators';

@Injectable({
	providedIn: 'root'
})
export class LoginAuthGuard implements CanActivate {
	constructor(private auth: AuthorizationService, private router: Router) {
	}

	canActivate(
		route: ActivatedRouteSnapshot,
		state: RouterStateSnapshot
	): Observable<boolean> {
		return this.auth.isAuthenticated()
			.pipe(
				take(1),
				map(isAuth => {
					if (isAuth) {
						this.router.navigate(['/tracks/browse']);
						return false;
					} else {
						return true;
					}
				})
			);
	}
}
