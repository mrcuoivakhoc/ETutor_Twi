import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { StudentsComponent } from './students/students.component';
import { AdminComponent } from './admin.component';
import { TutorsComponent } from './tutors/tutors.component';
import { MajorsComponent } from './majors/majors.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { UserAccountComponent } from './user-account/user-account.component';
import { ClassroomComponent } from './classroom/classroom.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';
import { AuthGuard } from 'src/app/guards/auth.guard';


const routes: Routes = [

  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard],
    data: { role: ['ADMIN'] }, 
    children :[
      {path: 'students', component: StudentsComponent},
      {path: 'tutors', component: TutorsComponent},
      {path: 'majors', component: MajorsComponent},
      {path: 'usersAccount', component: UserAccountComponent},
      {path: 'classroom', component: ClassroomComponent},


      { path: '', redirectTo: '/admin/students', pathMatch: 'full' },

    ]
  },
// { path: '', redirectTo: 'admin/students', pathMatch: 'full' },
// { path: '**', redirectTo: 'admin/students', pathMatch: 'full' }


];



@NgModule({
  declarations: [
    UserAccountComponent,
    StudentsComponent,
    TutorsComponent,
    MajorsComponent,
    ClassroomComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxPaginationModule,
    FormsModule

    


  ]
})
export class AdminModule { }
