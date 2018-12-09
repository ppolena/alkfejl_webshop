import { Order } from "./Order";

export class User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    address: string;
    accessRight: string;
    orders: Order[];
}