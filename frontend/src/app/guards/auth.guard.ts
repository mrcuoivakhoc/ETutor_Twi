import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { JwtHelperService } from '../services/jwt-helper.service';

@Injectable({
  providedIn: 'root'
})

@Injectable({
  providedIn: 'root' // ADDED providedIn root here.
})
export class AuthGuard implements CanActivate {


  canActivate(route: any): boolean {
    const token = this.authService.getToken();
    console.log(token)
    console.log('AuthGuard is running'); // ✅ In ra log để xác minh


    if (!token || this.jwtHelper.isTokenExpired(token)) {
      alert('Bạn chưa đăng nhập!');
      console.log('loi 1'); 

      this.router.navigate(['/login']); // Chuyển về trang đăng nhập
      return false;
    }

    const requiredRoles = route.data['role'];
    const userRole  = this.jwtHelper.getUserFromToken(token!).role;
    console.log(userRole )
    console.log(requiredRoles )

    if  (!requiredRoles.includes(userRole)) {
      alert('Bạn không có quyền truy cập!');
      console.log('loi 2'); 

      this.router.navigate(['/login']); // Chuyển về trang đăng nhập
      return false;
    }

    return true;
  }

  constructor(private authService: AuthService, private router: Router, private jwtHelper: JwtHelperService) {}

  
}
