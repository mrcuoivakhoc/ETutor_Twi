import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { StudentsComponent } from './students/students.component';
import { AdminComponent } from './admin.component';
import { TutorsComponent } from './tutors/tutors.component';
import { MajorsComponent } from './majors/majors.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';



const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children :[
      {path: 'students', component: StudentsComponent},
      {path: 'tutors', component: TutorsComponent},
      {path: 'majors', component: MajorsComponent},


      { path: '', redirectTo: '/students', pathMatch: 'full' },

    ]

  }
];



@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes),
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule


  ]
})
export class AdminModule { }
