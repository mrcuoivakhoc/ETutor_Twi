import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Blog } from 'src/app/common/blog';
import { Classroom } from 'src/app/common/classroom';
import { Major } from 'src/app/common/major';
import { Schedule } from 'src/app/common/schedule';
import { Student } from 'src/app/common/student';
import { Tutor } from 'src/app/common/tutor';
import { User } from 'src/app/common/user';
import { AuthService } from 'src/app/services/auth.service';
import { BlogService } from 'src/app/services/blog.service';
import { ClassroomService } from 'src/app/services/classroom.service';
import { JwtHelperService } from 'src/app/services/jwt-helper.service';
import { MajorService } from 'src/app/services/major.service';
import { ScheduleService } from 'src/app/services/schedule.service';
import { StudentService } from 'src/app/services/student.service';
import { TutorService } from 'src/app/services/tutor.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {

@ViewChild('myModalUpdateUser') modalUpdateUser: ElementRef | undefined;
@ViewChild('myModalBookSchedule') modalBookSchedule: ElementRef | undefined;

showNotification = false;
showListStudentsOfTutor = false;
selectedFile!: File;


  token: string = '';

  userHold = {username:'',role:'',name:'',birthday:new Date(),imageFile:'',id:0};

  students: Student[] =[];
  studentsOfTutor: Student[] =[];

  tutors: Tutor[] =[];
  users: User[] =[];
  majors: Major[] =[];
  classrooms: Classroom[] = []

  blogs: Blog[] =[];

  content: string = '';  
  
  majorOfUser: Major | undefined;


  userFormUpdate!: FormGroup;
  scheduleForm!: FormGroup;





  constructor(private jwtHelperService: JwtHelperService,
              private authService: AuthService,
              private studentService: StudentService,
              private tutorService: TutorService,
              private blogService: BlogService,
              private userService: UserService,
              private formBuilder: FormBuilder,
              private majorService: MajorService,
              private scheduleService: ScheduleService,
              private classroomService: ClassroomService
              
  ) { }

  ngOnInit(): void {

    this.userFormUpdate = this.formBuilder.group({
      nameUpdate: ['', [Validators.required, Validators.minLength(3)]],
      birthdayUpdate: ['', [Validators.required, Validators.minLength(3)]],
      majorUpdate: ['', [Validators.required]]
    });

    this.scheduleForm = this.formBuilder.group({
      studentDtoId: ['', [Validators.required]],
      startTime: ['', [Validators.required]],
      endTime: ['', [Validators.required]]
    });


    this.token = this.authService.getToken()!;
    this.userHold = this.jwtHelperService.getUserFromToken(this.token)
    this.loadAllData()
  }




  loadAllData() {
    forkJoin({
      tutors: this.tutorService.getTutorList(),          // Lấy tutors
      students: this.studentService.getStudentList(),    // Lấy students
      blogs: this.blogService.getBlogList(),
      users: this.userService.getUserList(),
      majors: this.majorService.getMajorList(),
      classrooms: this.classroomService.getClassroomList()
    }).subscribe(({ tutors, students, users, blogs, majors, classrooms }) => {
      this.tutors = tutors;
      this.students = students;
      this.blogs = blogs
      this.users = users
      this.majors = majors
      this.classrooms = classrooms
      this.getInforOfUser();
      console.log(students)
    });
    
  }

  closeModalBookSchedule(){
    if (this.modalBookSchedule) {
      this.modalBookSchedule.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
  }

  openModalBookSchedule() {
      const modalElement = document.getElementById('myModalBookSchedule');
      if (modalElement) {
        modalElement.style.display = 'block';
      }
  }

  closeModalUpdate(){
    if (this.modalUpdateUser) {
      this.modalUpdateUser.nativeElement.style.display = 'none';
    }else{
    console.log(123)
    }
  }

  openModalUpdate() {
      const modalElement = document.getElementById('myModalUpdateUser');
      if (modalElement) {
        modalElement.style.display = 'block';
      }
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onSubmitUpdate(){
    
    if(this.userFormUpdate.valid == false){
      this.showNotification = true;
      setTimeout(() => {
        this.showNotification = false;
      }, 3000);
      console.log('Form không hợp lệ!', this.userFormUpdate.controls);
    }else{
      const definedUserFormData = new FormData();
      definedUserFormData.append('id', this.userHold.id! + "");
      
      definedUserFormData.append('name', this.userFormUpdate.value.nameUpdate);
      definedUserFormData.append('birthday', this.userFormUpdate.value.birthdayUpdate);
      definedUserFormData.append('imageFile', this.userHold.imageFile!);
      definedUserFormData.append('username', this.userHold.username!);
      definedUserFormData.append('majorDtoId', this.userFormUpdate.value.majorUpdate);
      definedUserFormData.append('file', this.selectedFile);

      if(this.userHold.role === 'STUDENT'){
        this.studentService.updateStudent(definedUserFormData,this.userHold.id!).subscribe(
          data =>{
            this.loadAllData();
            console.log(data)
          }
        ) 
      }else{
        this.tutorService.updateTutor(definedUserFormData,this.userHold.id!).subscribe(
          data =>{
            this.loadAllData();
            console.log(data)
          }
        ) 
      }

      this.closeModalUpdate();  
    }
  }

    
  getInforOfUser(){
    if(this.userHold.role === 'STUDENT'){
      const student = this.students.filter(student => student.username === this.userHold.username)
      this.userHold.name = student[0].name!
      this.userHold.birthday = student[0].birthday!
      this.userHold.imageFile = student[0].imageFile!
      this.userHold.id = student[0].id!
      this.majorOfUser = student[0].majorDto
    }else{
      const tutor = this.tutors.filter(tutor => tutor.username === this.userHold.username)
      this.userHold.name = tutor[0].name!
      this.userHold.birthday = tutor[0].birthday!
      this.userHold.imageFile = tutor[0].imageFile!
      this.userHold.id = tutor[0].id!
      this.majorOfUser = tutor[0].majorDto
    }
  }


  saveBlog(){
    const user = this.users.filter(user => user.username === this.userHold.username)
    const blog = new Blog(null,this.content,user[0],null,null);

    this.blogService.saveBlog(blog).subscribe(
      data =>{
        this.content = ''
        console.log(data)
        this.loadAllData();
      }
    ) 
  }


  findObjectByUser(user: User):any{
    if(user.role === 'STUDENT'){
     return this.students.filter(student => student.username === user.username)[0]
    }
    if(user.role === 'TUTOR'){
      return this.tutors.filter(tutor => tutor.username === user.username)[0]
    }

  }

  updateUser() {
    this.userFormUpdate.patchValue  ({
      nameUpdate: this.userHold.name,
      birthdayUpdate: this.userHold.birthday,
      majorUpdate: this.majorOfUser?.id
    });
    this.openModalUpdate()
  }

  bookSchedule() {
    this.studentsOfTutor = []
    const classroomOfTutor = this.classrooms.filter(classroom => classroom.tutorId === this.userHold.id)
    if(classroomOfTutor.length == 0){
      this.showListStudentsOfTutor = false;
    }else{
      this.showListStudentsOfTutor = true;
      for (let i = 0; i < classroomOfTutor[0].studentsId.length; i++) {
        this.studentsOfTutor.push(this.students.filter(student => student.id === classroomOfTutor[0].studentsId[i])[0])
        console.log(this.studentsOfTutor[i])
      }
      
    }
    this.openModalBookSchedule();
  }

  saveSchedule(){
    const tutor = this.tutors.filter(tutor => tutor.username === this.userHold.username)[0];
    const student = this.students.filter(student => student.id === Number(this.scheduleForm.value.studentDtoId))[0];
    console.log(this.scheduleForm.value.studentDtoId + '  ád')
    console.log(student)

    const startTime = this.scheduleForm.value.startTime
    const endTime = this.scheduleForm.value.endTime

    console.log(new Schedule(null,student,tutor,startTime,endTime))

    this.scheduleService.bookSchedule(new Schedule(null,student,tutor,startTime,endTime)).subscribe(
      data =>{
        console.log(data)
        this.loadAllData();
        this.closeModalBookSchedule();

      }
    ) 

  }

}





