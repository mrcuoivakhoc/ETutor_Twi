import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Major } from '../common/major';

@Injectable({
  providedIn: 'root'
})
export class MajorService {


  private majorUrl = 'http://localhost:8080/api/major';


  constructor(private HttpClient: HttpClient) { }


  saveMajor(major: Major): Observable<any> {
    const saveMajor = `${this.majorUrl}/save_major`;
    return this.HttpClient.post(saveMajor, major);
  }

  getMajorList(): Observable<any> {
    return this.HttpClient.get<any>(this.majorUrl);
  }

  show(){
    console.log(123);
  }

}
