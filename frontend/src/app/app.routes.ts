import { Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserComponent } from './user/user.component';
import { SupplierComponent } from './supplier/supplier.component';

export const routes: Routes = [
    { path: '', component: HomepageComponent },
    { path: "login", component : LoginComponent},
    { path: "register", component : RegisterComponent },
    { path: "user", component : UserComponent},
    { path: "supplier", component : SupplierComponent}
]