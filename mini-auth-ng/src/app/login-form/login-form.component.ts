import { Component, OnInit } from '@angular/core';
import { Customer } from "../customer";
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  customer = new Customer();

  constructor(private loginService: LoginService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.loginService.login(this.customer)
      .subscribe(data => {
        localStorage.setItem("username", this.customer.username);
        localStorage.setItem("jwt", "Bearer " + data['jwt']);
      });
  }

}
