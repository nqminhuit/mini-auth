import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from './customer';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  backendUrl = "http://localhost:8080/";

  constructor(private http: HttpClient) { }

  login(customer: Customer): Observable<string> {
    return this.http.post<string>(
      this.backendUrl + "api/login",
      JSON.stringify(customer),
      { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) });
  }

}
