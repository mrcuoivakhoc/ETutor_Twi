import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BlogComponent } from './blog/blog.component';
import { RouterModule, Routes } from '@angular/router';
import { UserComponent } from './user.component';
import { AuthGuard } from 'src/app/guards/auth.guard';
import { FormsModule } from '@angular/forms';  // ✅ Import FormsModule
import { ReactiveFormsModule } from '@angular/forms'; // Thêm import này


const routes: Routes = [

  {
    path: 'user',
    component: UserComponent,
    canActivate: [AuthGuard],
    data: { role: ['TUTOR','STUDENT'] }, 
    children :[      
      {path: 'blog', component: BlogComponent},
      { path: '', redirectTo: '/user/blog', pathMatch: 'full' },
    ]
  },

];


@NgModule({
  declarations: [
    BlogComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    CommonModule,
    FormsModule,
    ReactiveFormsModule 
  ]
})
export class UserModule { }
