import { Component } from '@angular/core';
import { CustomerService } from '../customer.service';

export interface CustomerData {
  id: number;
  username: string;
  role: string;
}

const DATA: CustomerData[] = [
  { "id": 0, "username": "batman", "role": "admin" },
  { "id": 2, "username": "catwoman", "role": "user" }
];

@Component({
  selector: 'app-customer-table',
  templateUrl: './customer-table.component.html',
  styleUrls: ['./customer-table.component.css']
})
export class CustomerTableComponent {

  constructor(private customerService: CustomerService) { }

  displayedColumns: string[] = ['id', 'username', 'role'];

  dataSource = DATA;

  getAllCustomers() {
    this.customerService.getAllCustomer().subscribe(data => this.dataSource = data);
  }

}
