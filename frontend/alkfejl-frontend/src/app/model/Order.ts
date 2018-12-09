import { Item } from "./Item";

export class Order {
    orderDate: Date;
    orderStatus: string;
    customer: string;
    items: Item[];
}