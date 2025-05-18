import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-supplier',
  standalone: false,
  templateUrl: './supplier.component.html',
  styleUrl: './supplier.component.css'
})
export class SupplierComponent {

  suppliers: any[] = [];
  filteredSuppliers: any[] = [];
  searchTerm: string = '';
  favoriteSupplierIds: number[] = []; 
  idEnterprise: number = 0;

  newSupplier = {
    name: '',
    email: '',
    phoneNumber: ''
  };
  router: any;

  public constructor(router : Router){
    this.router = router;
  }

  ngOnInit(): void {
    this.idEnterprise = this.getEnterpriseId();
    if (!this.idEnterprise) return;

    fetch(`http://localhost:8082/users/getAllSuppliers`)
      .then(res => res.json())
      .then(data => {
        this.suppliers = data;
        this.filteredSuppliers = data;
      })
      .catch(err => console.error('Erreur de récupération des fournisseurs', err));

    fetch(`http://localhost:8082/users/favoriteSuppliers/${this.idEnterprise}`)
      .then(res => res.json())
      .then(data => {
        this.favoriteSupplierIds = data.map((supplier: any) => supplier.id);
      })
      .catch(err => console.error('Erreur de récupération des favoris', err));
  }

  getEnterpriseId(): number {
    const id = localStorage.getItem('idEnterprise') || sessionStorage.getItem('idEnterprise');
    return id ? parseInt(id, 10) : 0;
  }

  filterSuppliers() {
    const term = this.searchTerm.toLowerCase();
    this.filteredSuppliers = this.suppliers.filter(supplier =>
      supplier.name.toLowerCase().includes(term) ||
      supplier.email.toLowerCase().includes(term) ||
      supplier.phoneNumber.includes(term)
    );
  }

  isFavorite(supplier: any): boolean {
    return this.favoriteSupplierIds.includes(supplier.id);
  }

  toggleFavorite(supplier: any) {
  const idEnterprise = this.idEnterprise;
  const idSupplier = supplier.id;

  const isAlreadyFavorite = this.favoriteSupplierIds.includes(idSupplier);

  if (isAlreadyFavorite) {
    fetch(`http://localhost:8082/users/favoriteSuppliers/deleteOne`, {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ idEnterprise, idSupplier })
    })
      .then(() => {
        this.favoriteSupplierIds = this.favoriteSupplierIds.filter(id => id !== idSupplier);
      })
      .catch(err => console.error("Erreur suppression favori", err));
  } else {
    fetch('http://localhost:8082/users/favoriteSuppliers/addOne', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ idEnterprise, idSupplier })
    })
      .then(() => {
        this.favoriteSupplierIds.push(idSupplier);
      })
      .catch(err => console.error("Erreur ajout favori", err));
  }
}

goFavsPage(){
  this.router.navigate(["/favorite-suppliers"])
}


}
