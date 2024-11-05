import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { LoginDto } from '../../../dtos/user/login.dto';
import { LoginResponse } from '../../responses/user/login.response';
import { TokenService } from '../../services/token.service';
import { Role } from '../../models/role';
import { RoleService } from '../../services/role.service';

@Component({
	selector: 'app-login',
	templateUrl: './login.component.html',
	styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

	phoneNumber: String;
	password: String;
	roles: Role[];
	rememberMe: boolean;
	selectedRole: Role | undefined;

	constructor(
		private router: Router,
		private userService: UserService,
		private tokenService: TokenService,
		private roleService: RoleService
	) {
		this.phoneNumber = '';
		this.password = '';
		this.roles = [
			// {id: 1, name: "admin"},
			// {id: 2, name: "user"}
		];
		this.rememberMe = true;
	}

	ngOnInit() {
		console.log("Init chua zay");

		debugger

		this.roleService.getRoles().subscribe({
			next: (roles: Role[]) => {
				debugger
				this.roles = roles;
				this.selectedRole = roles.length > 0 ? roles[0] : undefined;
			},
			error: (error: any) => {
				debugger
				console.error('Error getting roles:', error);
			}
		});
	}

	login() {
		const message = `phone: ${this.phoneNumber} \n`
			+ `password: ${this.password} \n`;
		// alert(message);

		debugger

		const loginDto: LoginDto =
		{
			phone_number: this.phoneNumber,
			password: this.password,
			role_id: this.selectedRole?.id ?? 1,
		}

		this.userService.login(loginDto).subscribe(
			{
				next: (response: LoginResponse) => {
					debugger
					console.log("login success")

					const { token } = response
					this.tokenService.setToken(token);


					this.router.navigate(['/home'])
				},
				complete: () => {
					debugger
				},
				error: (error: any) => {
					debugger
					alert(`Can not login, error: ${error.error}`);
				}
			}
		)
	}
}
