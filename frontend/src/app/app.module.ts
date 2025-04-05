import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { AdminComponent } from './components/admin/admin.component';
import { UserComponent } from './components/user/user.component';
import { AdminModule } from './components/admin/admin.module';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { UserModule } from './components/user/user.module';
import { LoginComponent } from './components/login/login.component';



const routes: Routes = [
{ path: 'login', component: LoginComponent },
{ path: 'admin', component: AdminComponent },
{ path: 'user', component: UserComponent },
{ path: '', redirectTo: '/login', pathMatch: 'full' },

];


@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    UserComponent,
    LoginComponent,
    
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    AdminModule,
    UserModule,
    RouterModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
