import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  standalone : false
})
export class RegisterComponent {
  enterprise = {
    name: '',
    email: '',
    phoneNumber: '',
    address: ''
  };

  user = {
    firstname: '',
    lastname: '',
    email: '',
    phoneNumber: '',
    password: '',
    role: 'admin'
  };

  confirmPassword: string = '';

  constructor(private router: Router, public authService: AuthService) {}

  async onSubmit() {
    if (this.user.password !== this.confirmPassword) {
      alert("Les mots de passe ne correspondent pas.");
      return;
    }

    try {
      // 1. Créer l'entreprise
      const resEnterprise = await fetch('http://localhost:8083/enterprises', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(this.enterprise)
      });

      if (!resEnterprise.ok) throw new Error(await resEnterprise.text());

      const createdEnterprise = await resEnterprise.json();
      const enterpriseId = createdEnterprise.id;
      
      // 2. Créer l'utilisateur lié à l'entreprise
      const userWithEnterprise = {
        ...this.user,
        idEnterprise: enterpriseId
      };

      const resUser = await fetch('http://localhost:8081/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userWithEnterprise)
      });

      if (!resUser.ok) throw new Error(await resUser.text());

      console.log("Inscription réussite")
      this.router.navigate(['/login']);

    } catch (error: any) {
      console.error('Erreur:', error.message);
      alert('Erreur : ' + error.message);
    }
  }
}
