import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerData } from './customer-table/customer-table.component';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  CUSTOMER_API: string = "http://localhost:8080/api/customer";

  constructor(private http: HttpClient) { }

  getAllCustomer(): Observable<CustomerData[]> {
    return this.http.get<CustomerData[]>(this.CUSTOMER_API + "/all",
      { headers: new HttpHeaders({ "Authorization": localStorage.getItem("jwt") }) });
  }

  createCustomer(newCustomer: CustomerData): Observable<CustomerData> {
    console.log("create customer: " + newCustomer);
    return this.http.post<CustomerData>(this.CUSTOMER_API + "/create", newCustomer,
      {
        headers: new HttpHeaders({
          "Authorization": localStorage.getItem("jwt"),
          "Content-Type": "application/json"
        })
      });
  }

  updateCustomer(customerId: number, newCustomer: CustomerData): Observable<CustomerData> {
    return this.http.put<CustomerData>(this.CUSTOMER_API + "/" + customerId, newCustomer,
      {
        headers: new HttpHeaders({
          "Authorization": localStorage.getItem("jwt"),
          "Content-Type": "application/json"
        })
      });
  }

  deleteCustomer(customerId: number): Observable<string> {
    return this.http.delete<string>(this.CUSTOMER_API + "/" + customerId,
      { headers: new HttpHeaders({ "Authorization": localStorage.getItem("jwt") }) });
  }

}
