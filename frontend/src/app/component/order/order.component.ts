import { Component } from '@angular/core';

@Component({
    selector: 'app-order',
    templateUrl: './order.component.html',
    styleUrl: './order.component.css'
})
export class OrderComponent {
    fullName: String;
    email: String;
    phoneNumber: String;
    coupon: String;

    constructor() {
        this.fullName = '';
        this.email = '';
        this.phoneNumber = '';
        this.coupon = '';
    }

    checkCoupon(){
        
    }

    order() {
        alert(
            `fullName: ${this.fullName} \n` +
            `email: ${this.email} \n` +
            `phoneNumber: ${this.phoneNumber} \n` +
            `coupon: ${this.coupon} \n` 

        )
    }
}
