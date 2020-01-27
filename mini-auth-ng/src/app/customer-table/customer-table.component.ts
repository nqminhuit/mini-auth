import { Component } from '@angular/core';
import { CustomerService } from '../customer.service';

export interface CustomerData {
  id: number;
  username: string;
  role: string;
}

@Component({
  selector: 'app-customer-table',
  templateUrl: './customer-table.component.html',
  styleUrls: ['./customer-table.component.css']
})
export class CustomerTableComponent {

  constructor(private customerService: CustomerService) { }

  dataSource: CustomerData[];

  tableSettings = {
    delete: { confirmDelete: true },
    add: { confirmCreate: true },
    edit: { confirmSave: true },
    actions: { position: 'right' },
    pager: { display: false },
    columns: {
      id: { title: "ID", editable: false },
      username: { title: "Username" },
      role: { title: "Role" }
    }
  }

  getAllCustomers() {
    this.customerService.getAllCustomer().subscribe(
      data => {
        this.dataSource = data
      },
      _error => window.alert("You are not authorized to access this resource!")
    );
  }

  createCustomer(event): void {
    this.customerService.createCustomer(event.newData).subscribe(
      data => event.confirm.resolve(data),
      _error => {
        if (_error.error.message.includes("org.hibernate.exception.ConstraintViolationException")) {
          window.alert("Cannot create duplicate username!");
        }
      });
  }

  editCustomer(event): void {
    this.customerService.updateCustomer(event.data.id, event.newData).subscribe(
      data => event.confirm.resolve(data),
      _error => {
        if (_error.error.message.includes("org.hibernate.exception.ConstraintViolationException")) {
          window.alert("Cannot edit to a duplicate username!");
        }
      }
    );
  }

  deleteCustomer(event): void {
    this.customerService.deleteCustomer(event.data['id']).subscribe();
    event.confirm.resolve();
  }

}
