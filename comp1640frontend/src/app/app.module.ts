import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { StudentsComponent } from './components/admin/students/students.component';
import { AdminComponent } from './components/admin/admin.component';
import { UserComponent } from './components/user/user.component';
import { TutorsComponent } from './components/admin/tutors/tutors.component';
import { MajorsComponent } from './components/admin/majors/majors.component';
import { AdminModule } from './components/admin/admin.module';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'; 



@NgModule({
  declarations: [
    AppComponent,
    StudentsComponent,
    TutorsComponent,
    MajorsComponent,
    AdminComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AdminModule,
    RouterModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
