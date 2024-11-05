import { Component, ViewChild } from "@angular/core";
import { NgForm } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserService } from "../../services/user.service";
import { RegisterDto } from "../../../dtos/user/register.dto";


@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrl: './register.component.css'
})
export class RegisterComponent {
    @ViewChild('registerForm') registerForm!: NgForm;

    phoneNumber: String;
    password: String;
    retypePassword: String;
    fullName: String;
    address: String;
    isAgree: boolean;

    constructor(private router: Router, private userService: UserService) {
        this.phoneNumber = '';
        this.password = '';
        this.retypePassword = '';
        this.fullName = '';
        this.address = '';
        this.isAgree = true;
    }

    onPhoneChange() {
        console.log(this.phoneNumber);
    }


    register() {
        const message = `phone: ${this.phoneNumber} \n`
            + `password: ${this.password} \n`
            + `retype Password: ${this.retypePassword} \n`
            + `full name: ${this.fullName} \n`
            + `address: ${this.address} \n`;
        // alert(message)

        debugger

        const registerDto:RegisterDto =
        {
            "full_name": this.fullName,
            "phone_number": this.phoneNumber,
            "address": this.address,
            "password": this.password,
            "retype_password": this.retypePassword,
            "date_of_birth": "",
            "facebook_account_id": 0,
            "google_account_id": 0,
            "role_id": 2
        }
        this.userService.register(registerDto).subscribe(
            {
                next: (response: any) => {
                    debugger
                    this.router.navigate(['/login']);
                },
                complete: () => {
                    debugger
                },
                error: (error: any) => {
                    debugger
                    alert(`Can not register, error: ${error.error}`);
                }
            }
        )

    }
}
