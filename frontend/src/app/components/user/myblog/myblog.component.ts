import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Blog } from 'src/app/common/blog';
import { Student } from 'src/app/common/student';
import { Tutor } from 'src/app/common/tutor';
import { User } from 'src/app/common/user';
import { AuthService } from 'src/app/services/auth.service';
import { BlogService } from 'src/app/services/blog.service';
import { JwtHelperService } from 'src/app/services/jwt-helper.service';
import { StudentService } from 'src/app/services/student.service';
import { TutorService } from 'src/app/services/tutor.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-myblog',
  templateUrl: './myblog.component.html',
  styleUrls: ['./myblog.component.css']
})
export class MyblogComponent implements OnInit {


  @ViewChild('myModalUpdateBlog') myModalUpdateBlog: ElementRef | undefined;
  @ViewChild('fileInput') fileInput!: ElementRef;


  users: User[] = [];
  tutors: Tutor[] = [];
  students: Student[] = [];

  blogs: Blog[] =[];
  myBlogs: Blog[] = [];
  userHold = {username:'',role:'',idStudentOrTutor:0,idUser:0};
  token = this.authService.getToken()!;
  selectedFile: File | null = null;

  blogUpdate?: Blog;
  constructor(private blogService: BlogService,
              private authService: AuthService,
              private jwtHelperService: JwtHelperService,
              private userService: UserService,
              private tutorService: TutorService,
              private studentService: StudentService
  ) { }

  ngOnInit(): void {
    this.loadAllData();




  }


  loadAllData() {
    forkJoin({
      blogs: this.blogService.getBlogList(),
      users: this.userService.getUserList(),
      tutors: this.tutorService.getTutorList(),
      students: this.studentService.getStudentList(),

    }).subscribe(({blogs,users,tutors,students}) => {
      this.blogs = blogs
      this.users = users
      this.tutors = tutors
      this.students = students
      this.setUserHold()
      this.findMyBlogs()
    });
    
  }

  findMyBlogs(){
    this.myBlogs = this.blogs.filter(blog => blog.userDto?.id === this.userHold.idUser)
  }

  findObjectByUser(user: User):any{
    if(user.role === 'STUDENT'){
     return this.students.filter(student => student.username === user.username)[0]
    }
    if(user.role === 'TUTOR'){
      return this.tutors.filter(tutor => tutor.username === user.username)[0]
    }

  }

  setUserHold(){

    this.userHold.username = this.jwtHelperService.getUserFromToken(this.token).username
    this.userHold.role = this.jwtHelperService.getUserFromToken(this.token).role
    this.userHold.idUser = this.users.filter(user => user.username === this.userHold.username)[0].id!

    if(this.userHold.role === 'TUTOR'){
      const userIdTutorOrStudent = this.tutors.filter(tutor => tutor.username === this.userHold.username)[0].id
      this.userHold.idStudentOrTutor = userIdTutorOrStudent!
    }
    if(this.userHold.role === 'STUDENT'){
      const userIdTutorOrStudent = this.students.filter(student => student.username === this.userHold.username)[0].id
      this.userHold.idStudentOrTutor = userIdTutorOrStudent!
    }
  }

  deleteBlog(idBlog: number){
    this.blogService.deleteBlog(idBlog).subscribe(
      data =>{
        // console.log(data);
        this.loadAllData()
      }
    ) 
  }


  closeModalUpdate(){
    if (this.myModalUpdateBlog) {
      this.myModalUpdateBlog.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
    this.loadAllData()
    this.selectedFile = null
    this.fileInput.nativeElement.value = ''; // reset input file


  }

  openModalUpdate() {
      const modalElement = document.getElementById('myModalUpdateBlog');
      if (modalElement) {
        modalElement.style.display = 'block';
      }
  }

  setDataForUpdateBlog(blog: Blog){
    this.blogUpdate = blog
    this.openModalUpdate();
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file && file.size > 512 * 1024 * 1024) { // 512MB
      alert("File quá lớn! Giới hạn là 512MB.");
      return;
    }
    this.selectedFile = file;
    this.blogUpdate!.fileName = file.name;
  }

  removeFile(){
    this.blogUpdate!.fileName = '';
  }

  onSubmitUpdateBlog(){
    if(this.blogUpdate?.content === ''){
      alert('Please enter something')
      return;
    }else{
      const blogFormDataUpdate = new FormData();
      blogFormDataUpdate.append('content', this.blogUpdate!.content!);
      blogFormDataUpdate.append('fileName', this.blogUpdate!.fileName!);
      blogFormDataUpdate.append('file', this.selectedFile!);

      blogFormDataUpdate.forEach((value, key) => {
        console.log(key, value);
      });

      this.blogService.updateBlog(blogFormDataUpdate,this.blogUpdate!.id!).subscribe(
        data =>{
          console.log(data);
          this.loadAllData();
          this.closeModalUpdate();
        }
      ) 
    }

  }

}
