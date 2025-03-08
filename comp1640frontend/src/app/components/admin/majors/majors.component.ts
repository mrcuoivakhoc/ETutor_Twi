import { Component, OnInit } from '@angular/core';
import { ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Major } from 'src/app/common/major';
import { MajorService } from 'src/app/services/major.service';

@Component({
  selector: 'app-majors',
  templateUrl: './majors.component.html',
  styleUrls: ['./majors.component.css']
})
export class MajorsComponent implements OnInit {

  @ViewChild('myModal') model: ElementRef | undefined;

  majorForm!: FormGroup;
  majors: Major[] =[];


  constructor(private formBuilder: FormBuilder,
              private majorService: MajorService,
              private router: Router
  ) { }

  ngOnInit(): void {
    this.majorForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(3)]]
    });
    this.listProduct();
  }


  openModal() {
    const modalElement = document.getElementById('myModal');
    if (modalElement) {
      modalElement.style.display = 'block';
    }
  }

  closeModal() {
    if (this.model) {
      this.model.nativeElement.style.display = 'none';
    }
  }


  onSubmit(){

    const newMajor: Major = {
      name: this.majorForm.value.name,
      description: this.majorForm.value.description
    };
      this.majorService.saveMajor(newMajor).subscribe(
        data =>{
          this.listProduct();
        }
      ) 
      this.closeModal();  
      
    }

  listProduct() {
    this.majorService.getMajorList().subscribe(
      data =>{
        this.majors = data;
      }
    )
  }

}
