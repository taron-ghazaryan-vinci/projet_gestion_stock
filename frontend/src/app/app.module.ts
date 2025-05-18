import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { NavbarComponent } from './navbar/navbar.component';  // Ton composant Navbar
import { RouterModule } from '@angular/router';
import { routes } from './app.routes';
import { AppComponent } from './app/app.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { HomepageComponent } from './homepage/homepage.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from '@angular/forms';
import { UserComponent } from './user/user.component';
import { SupplierComponent } from './supplier/supplier.component';
import { SupplierFavoriteComponent } from './supplier-favorite/supplier-favorite.component';
import { StockComponent } from './stock/stock.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    RegisterComponent,
    LoginComponent,
    HomepageComponent,
    UserComponent,
    SupplierComponent,
    SupplierFavoriteComponent,
    StockComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    FormsModule,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

