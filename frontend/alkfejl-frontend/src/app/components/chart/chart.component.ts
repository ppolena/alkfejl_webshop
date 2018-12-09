import { Component, OnInit } from '@angular/core';
import { Item } from '../../model/Item';
import { Ware } from '../../model/Ware';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {
  chart:Item[] = [];


  constructor() { }

  ngOnInit() {
  }

  addItem(ware:Ware,amount:number) {
    this.chart.push({ware,amount})
  }

  resetItem() {
    this.chart = [];
  }

}
