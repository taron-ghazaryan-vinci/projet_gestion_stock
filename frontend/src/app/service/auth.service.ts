import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  localStorage: boolean = false;

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token') || !!sessionStorage.getItem('token');
  }

  loginLocal(token: string, idEnterprise: number): void {
    localStorage.setItem('token', token);
    localStorage.setItem('idEnterprise', idEnterprise.toString());
  }

  logoutLocal(): void {
    localStorage.removeItem('token');
  }

  loginSession(token: string, idEnterprise: number): void {
    sessionStorage.setItem('token', token);
    sessionStorage.setItem('idEnterprise', idEnterprise.toString());
  }

  logoutSession(): void {
    sessionStorage.removeItem('token');
  }
}
