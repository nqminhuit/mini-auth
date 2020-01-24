import { Component, OnInit } from '@angular/core';

export interface CustomerData {
  id: number;
  username: string;
  role: string;
}
const DATA: CustomerData[] = [
  { "id": 0, "username": "batman", "role": "admin" },
  { "id": 1, "username": "joker", "role": "user" },
  { "id": 2, "username": "catwoman", "role": "user" },
  { "id": 4, "username": "robin", "role": "user" }
];

@Component({
  selector: 'app-customer-table',
  templateUrl: './customer-table.component.html',
  styleUrls: ['./customer-table.component.css']
})
export class CustomerTableComponent implements OnInit {
  ngOnInit(): void {
  }
  displayedColumns: string[] = ['id', 'username', 'role'];
  dataSource = DATA;
}
