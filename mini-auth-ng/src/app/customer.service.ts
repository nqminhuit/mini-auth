import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomerData } from './customer-table/customer-table.component';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  getAllCustomer(): Observable<CustomerData[]> {
    return this.http.get<CustomerData[]>("http://localhost:8080/api/customer/all",
      { headers: new HttpHeaders({ "Authorization": localStorage.getItem("jwt") }) });
  }

  createCustomer(): Observable<string> {
    return null;
  }

  updateCustomer(): Observable<CustomerData> {
    return null;
  }

  deleteCustomer(): Observable<string> {
    return null;
  }

}
