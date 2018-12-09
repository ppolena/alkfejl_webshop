import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';

export interface selectable {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-aruk-filter',
  templateUrl: './aruk-filter.component.html',
  styleUrls: ['./aruk-filter.component.css']
})





export class ArukFilterComponent implements OnInit {
  
  selectables: selectable[] = [
    {value: '', viewValue: '-'},
    {value: 'type', viewValue: 'Típus'},
    {value: 'manufacturer', viewValue: 'Gyártó'},
    {value: 'price', viewValue: 'Maximum ár'}
  ];

  mire: string = '';
  mi: string = '';
  
  @Output()
  filterSubmit: EventEmitter<String> = new EventEmitter();
  @Output()
  filterSubmit2: EventEmitter<String> = new EventEmitter();

  @Input()
  selectName: string;
  @Input()
  filterName: string;

  constructor() { }

  ngOnInit() {
  }

  filter() {
    this.filterSubmit.emit(this.mire);
    this.filterSubmit2.emit(this.mi);
  }

}


