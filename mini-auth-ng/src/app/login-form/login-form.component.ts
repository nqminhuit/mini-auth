import { Component, OnInit } from '@angular/core';
import { Customer } from "../customer";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  customer = new Customer();

  constructor() { }

  ngOnInit() {
  }

  onSubmit() {
    console.log(this.customer);
  }

}
