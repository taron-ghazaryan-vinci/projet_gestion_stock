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
    localStorage.setItem('idUser', user.id);

    if(user.role!=="supplier"){
      localStorage.setItem('idEnterprise', user.idEnterprise.toString());
    }else{
      localStorage.setItem('role', user.role.toString());
    }

  }

  logoutLocal(): void {
    localStorage.removeItem('token');
  }

  loginSession(token: string, user: any): void {
    sessionStorage.setItem('token', token);
    sessionStorage.setItem('idUser', user.id);
    if(user.role!=="supplier"){
      sessionStorage.setItem('idEnterprise', user.idEnterprise.toString());
    }
    else{
      sessionStorage.setItem('role', user.role.toString());
    }
  }

  logoutSession(): void {
    sessionStorage.removeItem('token');
  }
}
