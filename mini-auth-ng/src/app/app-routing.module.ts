import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { CustomerTableComponent } from './customer-table/customer-table.component';
import { PageNotFoundComponent } from './page-not-found.component';

const routes: Routes = [
  { path: 'login', component: LoginFormComponent },
  { path: 'customer', component: CustomerTableComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
