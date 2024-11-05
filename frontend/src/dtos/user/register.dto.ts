import {
    IsString, 
    IsNotEmpty, 
    IsPhoneNumber, 
    IsDate,
} from 'class-validator';

export class RegisterDto {
    @IsString()
    full_name: String;

    @IsPhoneNumber()
    phone_number: String;

    @IsString()
    @IsNotEmpty()
    address: String;

    @IsString()
    @IsNotEmpty()
    password: String;

    @IsString()
    @IsNotEmpty()
    retype_password: String;

    @IsDate()
    date_of_birth: String;

    facebook_account_id: number = 0;
    google_account_id: number = 0;
    role_id: number = 2;

    constructor(data: any) {
        this.full_name = data.full_name;
        this.phone_number = data.phone_number;
        this.address = data.address;
        this.password = data.password;
        this.retype_password = data.retype_password;
        this.date_of_birth = data.date_of_birth;
        this.facebook_account_id = data.facebook_account_id || 0;
        this.google_account_id = data.google_account_id || 0;
        this.role_id = data.role_id || 2;
    }
}
