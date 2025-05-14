import { Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserComponent } from './user/user.component';
import { CreateUserComponent } from './create-user/create-user.component';

export const routes: Routes = [
    { path: '', component: HomepageComponent },
    { path: "login", component : LoginComponent},
    { path: "register", component : RegisterComponent },
    { path: "user", component : UserComponent},
    { path: "create-user", component : CreateUserComponent}
]