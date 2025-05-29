import { Component } from '@angular/core';

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrl: './stock.component.css',
  standalone: false
})
export class StockComponent {
  products: any[] = [];
  filteredStocks: any[] = [];
  searchTerm: string = '';
  isAdding: boolean = false;
  quantity: number = 0;
  stocks: any[] = [];

  newProduct: any = {
    name: '',
    type: '',
    description: ''
  };

  editingProductId: number | null = null;
  editProductData: any = { name: '', type: '', description: '', quantity: 0 };

  idSupplier: number = 0;

  ngOnInit(): void {
    this.idSupplier = this.getSupplierId();
    if (!this.idSupplier) return;
    this.fetchProductsAndStocks();
  }

  getSupplierId(): number {
    const id = localStorage.getItem('idUser') || sessionStorage.getItem('idUser');
    return id ? parseInt(id, 10) : 0;
  }

  fetchProductsAndStocks(): void {
    Promise.all([
      fetch(`http://localhost:8084/products/getAllBySupplier/${this.idSupplier}`).then(res => res.json()),
      fetch(`http://localhost:8087/stocks/getStocksBySupplier/${this.idSupplier}`).then(res => res.json())
    ])
    .then(([products, stocks]) => {
      this.products = products;
      this.stocks = stocks;
      this.mergeProductQuantities();
    })
    .catch(err => console.error("Erreur lors de la récupération des données :", err));
  }

  mergeProductQuantities(): void {
    this.products.forEach(prod => {
      const stock = this.stocks.find(s => s.idProduct === prod.id);
      prod.quantity = stock ? stock.quantity : 0;
    });
    this.filteredStocks = [...this.products].filter(p => p.active);
  }

  filterStocks(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredStocks = this.products.filter(product =>
      product.name.toLowerCase().includes(term) ||
      product.type.toLowerCase().includes(term) ||
      (product.description || '').toLowerCase().includes(term)
    );
  }

  addProduct(): void {
    const body = {
      name: this.newProduct.name.trim(),
      type: this.newProduct.type.trim(),
      description: this.newProduct.description?.trim() || null,
      idSupplier: this.idSupplier
    };

    if (!body.name || !body.type || !this.idSupplier || this.quantity == null) {
      alert("Tous les champs sont requis.");
      return;
    }

    fetch('http://localhost:8084/products', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ ...body })
    })
      .then(res => {
        if (!res.ok) throw new Error("Erreur lors de l'ajout du produit");
        return res.json();
      })
      .then((addedProduct) => {
        const stock = {
          idProduct: addedProduct.id,
          ownerType: "supplier",
          idOwner: this.idSupplier,
          quantity: this.quantity
        };

        return fetch('http://localhost:8087/stocks', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(stock)
        }).then(res => {
          if (!res.ok) throw new Error("Erreur lors de l'ajout du stock");
          return res.json();
        }).then((stock) => {
          this.products.push({ ...addedProduct, quantity: this.quantity });
          this.filteredStocks.push({ ...addedProduct, quantity: this.quantity });
          this.stocks.push(stock);
          this.isAdding = false;
          this.newProduct = { name: '', type: '', description: '' };
          this.quantity = 0;
        });
      })
      .catch(err => alert(err.message));
  }

  async deleteProduct(product: any) {
    try {
      const stock = this.stocks.find(s => s.idProduct === product.id);
      if (!stock) {
        alert("Stock introuvable pour ce produit.");
        return;
      }

      const response = await fetch(`http://localhost:8084/products/updateActive/${product.id}/${stock.id}`, {
        method: 'PATCH',
      });

      if (!response.ok) {
        throw new Error("Erreur lors de la suppression du produit");
      }

      this.products = this.products.filter(p => p.id !== product.id);
      this.filteredStocks = this.filteredStocks.filter(p => p.id !== product.id);
      this.stocks = this.stocks.filter(s => s.idProduct !== product.id);
    } catch (err: any) {
      console.error("Erreur suppression :", err);
      alert(err.message || "Une erreur est survenue");
    }
  }

  startEdit(product: any): void {
    this.editingProductId = product.id;
    this.editProductData = { ...product };
  }

  cancelEdit(): void {
    this.editingProductId = null;
    this.editProductData = { name: '', type: '', description: '', quantity: 0 };
  }

  saveEdit(): void {
  fetch(`http://localhost:8084/products/updateProduct/${this.editProductData.id}`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(this.editProductData)
  })
    .then(res => {
      if (!res.ok) throw new Error("Erreur lors de la modification du produit");
      return res.json();
    })
    .then((updatedProduct) => {
      // Met à jour dans this.products
      const index = this.products.findIndex(p => p.id === updatedProduct.id);
      if (index !== -1) {
        const oldQuantity = this.products[index].quantity;
        this.products[index] = { ...updatedProduct, quantity: oldQuantity };
      }

      // Recalcule filteredStocks avec filtre actif
      this.filteredStocks = this.products.filter(p => p.active);

      // Reset formulaire
      this.cancelEdit();
    })
    .catch(err => alert(err.message));
}

}
