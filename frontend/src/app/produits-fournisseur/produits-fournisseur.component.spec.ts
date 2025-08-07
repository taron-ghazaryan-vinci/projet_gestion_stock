import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProduitsFournisseurComponent } from './produits-fournisseur.component';

describe('ProduitsFournisseurComponent', () => {
  let component: ProduitsFournisseurComponent;
  let fixture: ComponentFixture<ProduitsFournisseurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProduitsFournisseurComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProduitsFournisseurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
