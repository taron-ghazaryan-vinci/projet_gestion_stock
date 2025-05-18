import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  localStorage: boolean = false;

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token') || !!sessionStorage.getItem('token');
  }

  loginLocal(token: string, user: any): void {
    localStorage.setItem('token', token);
    if(user.role!=="supplier"){
      localStorage.setItem('idEnterprise', user.idEnterprise.toString());
    }
    localStorage.setItem('role', user.role.toString());
    

  }

  logoutLocal(): void {
    localStorage.removeItem('token');
  }

  loginSession(token: string, user: any): void {
    sessionStorage.setItem('token', token);
    if(user.role!=="supplier"){
      sessionStorage.setItem('idEnterprise', user.idEnterprise.toString());
    }
    sessionStorage.setItem('role', user.role.toString());
  }

  logoutSession(): void {
    sessionStorage.removeItem('token');
  }
}
