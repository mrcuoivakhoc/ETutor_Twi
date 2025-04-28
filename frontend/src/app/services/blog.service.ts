import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable } from 'rxjs';
import { Blog } from '../common/blog';


@Injectable({
  providedIn: 'root'
})
export class BlogService {

  private blogUrl = 'http://localhost:8080/api/blog'; // URL backend

  constructor(private HttpClient: HttpClient) { }


  getBlogList(): Observable<any> {
    return this.HttpClient.get<any>(this.blogUrl);
  }

  saveBlog(blog: Blog): Observable<any> {
    const saveBlog = `${this.blogUrl}/save_blog`;
    return this.HttpClient.post(saveBlog, blog);
  }



}
