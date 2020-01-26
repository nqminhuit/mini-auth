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
    columns: {
      id: { title: "ID", editable: false },
      username: { title: "Username" },
      role: { title: "Role" }
    }
  }

  getAllCustomers() {
    this.customerService.getAllCustomer().subscribe(data => this.dataSource = data);
  }

  createCustomer(event): void {
    if (window.confirm("create?")) {
      this.customerService.createCustomer(event.newData).subscribe();
      event.confirm.resolve(event.newData);
    }
    else {
      event.confirm.reject();
    }
  }

  editCustomer(event): void {
    if (window.confirm("edit?")) {
      this.customerService.updateCustomer(event.data.id, event.newData).subscribe();
      event.confirm.resolve(event.newData);
    }
    else {
      event.confirm.reject();
    }
  }

  deleteCustomer(event): void {
    if (window.confirm("del?")) {
      this.customerService.deleteCustomer(event.data['id']).subscribe();
      event.confirm.resolve();
    }
    else {
      event.confirm.reject();
    }
  }

}
