import { Component } from '@angular/core';

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
  editingSupplierId: number | null = null;
  isAdding: boolean = false;

  newSupplier = {
    name: '',
    email: '',
    phoneNumber: ''
  };

  ngOnInit(): void {
    const id = this.getEnterpriseId();
    if (!id) return;

    fetch(`http://localhost:8086/suppliers/getAllSuppliers/${id}`)
      .then(res => res.json())
      .then(data => {
        this.suppliers = data;
        this.filteredSuppliers = data;
      })
      .catch(err => console.error('Erreur de récupération des fournisseurs', err));
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

  editSupplier(supplier: any) {
    this.editingSupplierId = supplier.id;
  }

  cancelEdit() {
    this.editingSupplierId = null;
  }

  async addSupplier() {
    const enterpriseId = this.getEnterpriseId();
    const supplierToCreate = {
      ...this.newSupplier,
      idEnterprise: enterpriseId
    };

    try {
      const res = await fetch('http://localhost:8086/suppliers', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(supplierToCreate)
      });

      if (!res.ok) throw new Error(await res.text());

      const created = await res.json();
      this.suppliers.push(created);
      this.filteredSuppliers = [...this.suppliers];
      this.newSupplier = { name: '', email: '', phoneNumber: '' };
      this.isAdding = false;
    } catch (error: any) {
      alert("Erreur d'ajout : " + error.message);
    }
  }

  async saveSupplier(supplier: any) {
    try {
      const res = await fetch(`http://localhost:8086/suppliers/${supplier.id}`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(supplier)
      });

      if (!res.ok) throw new Error('Erreur lors de la mise à jour');

      const updated = await res.json();
      const index = this.suppliers.findIndex(s => s.id === updated.id);
      if (index !== -1) this.suppliers[index] = updated;
      this.filteredSuppliers = [...this.suppliers];
      this.editingSupplierId = null;
    } catch (error: any) {
      alert(error.message);
    }
  }
}