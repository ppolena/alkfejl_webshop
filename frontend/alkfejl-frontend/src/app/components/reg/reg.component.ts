import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../auth.service';
import { addAllToArray } from '@angular/core/src/render3/util';

@Component({
  selector: 'app-reg',
  templateUrl: './reg.component.html',
  styleUrls: ['./reg.component.css']
})
export class RegComponent implements OnInit {

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  form = this.fb.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    mail: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });
  hidePassword = true;
  message: string;

  get firstName() { return this.form.get('firstName'); }
  get lastName() { return this.form.get('lastName'); }
  get mail() { return this.form.get('mail'); }
  get password() { return this.form.get('password'); }

  ngOnInit() {
  }

  async onSubmit() {
    try {
      await this.authService.reg(this.firstName.value, this.lastName.value, this.mail.value, this.password.value);
      if (this.authService.redirectUrl) {
        this.router.navigate([this.authService.redirectUrl]);
      } else {
        this.router.navigate(['/']);
      }
    }
    catch(e) {
      this.message = 'FUCK!'
    }  
  }
}