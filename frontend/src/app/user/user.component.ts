import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css',
  standalone: false
})
export class UserComponent implements OnInit {
  searchTerm: string = '';
  users: any[] = [];
  filteredUsers: any[] = [];
  editingUserId: number | null = null;
  photoFile: File | null = null;
  isAdding : boolean = false;
  
  newUser = {
    firstname: '',
    lastname: '',
    email: '',
    phoneNumber: '',
    password: '',
    role: 'user',
  }

  constructor(private router: Router) {}

  ngOnInit(): void {
    const id = localStorage.getItem('idEnterprise') || sessionStorage.getItem('idEnterprise');
    if (!id) return;

    fetch(`http://localhost:8082/users/getAllByEnterprise/${id}`)
    .then(res => res.json())
    .then(data => {
      this.users = data;
      this.filteredUsers = data; 
    })
      .catch(err => console.error('Erreur de fetch', err));
  }

  filterUsers() {
  const term = this.searchTerm.toLowerCase();
  this.filteredUsers = this.users.filter(user =>
    user.firstname.toLowerCase().includes(term) ||
    user.lastname.toLowerCase().includes(term)
    );
  }

  editUser(user: any) {
    this.editingUserId = user.id;
  }

getEnterpriseId(): number {
  const id = localStorage.getItem('idEnterprise') || sessionStorage.getItem('idEnterprise');
  return id ? parseInt(id, 10) : 0;
}


  async addUser() {
  const entrepriseId = this.getEnterpriseId();
  const userToCreate = {
    ...this.newUser,
    idEnterprise: entrepriseId
  };

  try {
    const res = await fetch('http://localhost:8081/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userToCreate)
    });

    if (!res.ok) throw new Error(await res.text());

    const created = await res.json();
    this.users.push(created);  // Optionnel : mise à jour directe
    this.filteredUsers = [...this.users];
    this.newUser = { firstname: '', lastname: '', email: '', phoneNumber: '', password: '', role: 'user' };
    this.isAdding = false;
  } catch (error: any) {
    alert("Erreur d'ajout : " + error.message);
  }
}


  cancelEdit() {
    this.editingUserId = null;
    this.photoFile = null;
  }

  onPhotoSelected(event: any) {
    this.photoFile = event.target.files[0];
    console.log('Photo sélectionnée :', this.photoFile?.name);
  }

  saveUser(user: any) {
  const formData = new FormData();
  formData.append("user", JSON.stringify(user));
  if (this.photoFile) {
    formData.append("photo", this.photoFile);
  }

  fetch(`http://localhost:8082/users/updateWithPhoto`, {
    method: 'PATCH',
    body: formData
  })
    .then(response => {
      if (!response.ok) throw new Error('Erreur lors de la mise à jour');
      console.log('Utilisateur mis à jour avec succès');
      this.editingUserId = null;
      this.photoFile = null;
      return response.json();
    })
    .then(updatedUser => {
      // Mets à jour l'utilisateur dans le tableau
      const index = this.users.findIndex(u => u.id === updatedUser.id);
      if (index !== -1) this.users[index] = updatedUser;
    })
    .catch(error => alert(error.message));
}


}
