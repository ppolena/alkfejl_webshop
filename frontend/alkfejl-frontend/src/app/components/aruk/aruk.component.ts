import { Component, OnInit } from '@angular/core';
import { WaresService } from '../../wares.service';
import { Ware } from '../../model/Ware';

@Component({
  selector: 'app-aruk',
  templateUrl: './aruk.component.html',
  styleUrls: ['./aruk.component.css']
})
export class ArukComponent implements OnInit {
  
  id: string;
  name: string;
  type: string;
  manufacturer: string;
  price: number;
  stock: number;
  description: string;




  warExample: Ware[] = [
    {id: '1', name: '1',type:'123', manufacturer:'aaa',price:21,stock:131,description:'123'},
    {id: '2', name: '2',type:'123', manufacturer:'aaa',price:201,stock:11,description:'123'},
    {id: '3', name: '3',type:'a', manufacturer:'s',price:22,stock:11,description:'123'},
    {id: '4', name: '4',type:'s', manufacturer:'s',price:211,stock:11,description:'123'},
    {id: '5', name: '5',type:'d', manufacturer:'f',price:201,stock:141,description:'123'}
  ];

  constructor(
    private waresService: WaresService
  ) { }

  private mire: string = "";
  private mi: string = "";


  addMire(s:string) {
	  this.mire = s;
  }
  addMi(s:string) {
	  this.mi = s;
  }



  private sajt2()
  {
  console.log(this.waresService.getWares());
  return this.waresService.getWares();
  }

  ngOnInit() {
	  this.waresService.reqWares();
  }


  filter(filterMire: string,filterMi: string) {
    console.log(this.waresService.getFilteredWares(filterMire,filterMi));
    return this.waresService.getFilteredWares(filterMire,filterMi);
  }



}