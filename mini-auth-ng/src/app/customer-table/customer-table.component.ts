import { Component, ViewChild } from '@angular/core';
import { CustomerService } from '../customer.service';
import { MatTable, MatTableDataSource } from '@angular/material/table';

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

  @ViewChild(MatTable, { static: true }) table: MatTable<any>;

  constructor(private customerService: CustomerService) { }

  displayedColumns: string[] = ['id', 'username', 'role'];

  dataSource = DATA;

  getAllCustomers() {
    this.customerService.getAllCustomer().subscribe(data => this.dataSource = data);
  }

  addCustomer(newUsername: string, newRole: string) {
    this.dataSource.push({ "id": 2, "username": newUsername, "role": newRole });
    this.table.renderRows();
  }

}
