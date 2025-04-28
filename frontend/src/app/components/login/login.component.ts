import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Router} from '@angular/router';

import { User } from 'src/app/common/user';
import { AuthService } from 'src/app/services/auth.service';
import { JwtHelperService } from 'src/app/services/jwt-helper.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string = '';
  
  users: User[] =[];

  constructor(private fb: FormBuilder, 
              private authService: AuthService, 
              private userService: UserService,
              private jwtHelperService: JwtHelperService,
              private router: Router) { }

  ngOnInit(): void {
    this.createForm();
    this.listUser();
  }


  listUser() {
    this.userService.getUserList().subscribe(
      data =>{
        this.users = data;
      }
    )
  }

  createForm() {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      if(this.users.some(user => user.username === this.loginForm.value.username) && 
        this.users.some(user => user.password === this.loginForm.value.password) ){

        const { username, password} = this.loginForm.value;
        
        this.authService.login(username, password).subscribe({
          next: (response) => {
            this.authService.saveToken(response);
            console.log('Token:', this.authService.getToken());
            if(this.jwtHelperService.getUserFromToken(response).role === 'ADMIN'){
              this.router.navigate(['/admin']);
            }else{
              this.router.navigate(['/user']);
            }
            alert('Đăng nhập thành công!');
          },
          error: (err) => {
            this.errorMessage = 'Đăng nhập thất bại. Vui lòng thử lại!';
            console.error(err);
          }
        });
      }else{
        console.log(this.loginForm.value.username);
        console.log(this.loginForm.value.password);
        alert('Wrong username or password!');
      } 
    }else{
      alert('Please enter username and password!');
    }
  }

}
